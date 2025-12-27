package com.restmicro.cards.service.impl;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.restmicro.cards.dto.CardsDto;
import com.restmicro.cards.mapper.CardsMapper;
import com.restmicro.cards.exception.CustomerAlreadyExistsException;
import com.restmicro.cards.constants.CardsConstants;
import com.restmicro.cards.dto.CustomerDto;
import com.restmicro.cards.entity.Cards;
import com.restmicro.cards.entity.Customer;
import com.restmicro.cards.exception.InvalidCardLimitException;
import com.restmicro.cards.exception.ResourceNotFoundException;
import com.restmicro.cards.mapper.CustomerMapper;
import com.restmicro.cards.repository.CardsRepository;
import com.restmicro.cards.repository.CustomerRepository;
import com.restmicro.cards.service.CardsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements CardsService {

    private CardsRepository cardsRepository;
    private CustomerRepository customerRepository;

    // --------------------------
    //  CREATE NEW CARD
    // --------------------------
    @Override
    public void createCard(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer =
                customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(
                    "Customer already registered with given mobileNumber "
                            + customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        cardsRepository.save(createNewCard(savedCustomer));
    }

    // --------------------------
    //  CREATE CARD HELPER
    // --------------------------
    private Cards createNewCard(Customer customer) {

    	Cards newCard = new Cards();

        long randomCardNumber = ThreadLocalRandom.current()
                .nextLong(1000000000000000L, 1000000000000000L + 900000000000000L);

        newCard.setCardNumber(randomCardNumber);
        newCard.setCustomerId(customer.getCustomerId());
        newCard.setMobileNumber(Long.valueOf(customer.getMobileNumber()));
        newCard.setCardType(CardsConstants.TYPE);
        newCard.setTotalLimit(100000L);
        newCard.setAmountUsed(0L);
        newCard.setAvailableAmount(100000L);

        return newCard;
    }

    // --------------------------
    //  FETCH CARD DETAILS
    // --------------------------
    @Override
    public CustomerDto fetchCard(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer", "mobileNumber", mobileNumber));

        Cards cards = cardsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Card", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setCardsDto(CardsMapper.mapToCardsDto(cards, new CardsDto()));

        return customerDto;
    }

    // --------------------------
    //  UPDATE CARD DETAILS
    // --------------------------
    @Override
    public boolean updateCard(CustomerDto customerDto) {

        boolean isUpdated = false;

        CardsDto cardsDto = customerDto.getCardsDto();

        if (cardsDto != null) {

            Cards cards = cardsRepository.findById(cardsDto.getCardNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Card", "CardNumber", cardsDto.getCardNumber().toString()));

            // --------------------------
            // VALIDATION LOGIC
            // --------------------------
            if (cardsDto.getAmountUsed() > cardsDto.getTotalLimit()) {
                throw new InvalidCardLimitException(
                        "Amount used cannot exceed total limit!");
            }

            // --------------------------
            // BUSINESS LOGIC (AUTO CALCULATION)
            // --------------------------
            Long availableAmount =
                    cardsDto.getTotalLimit() - cardsDto.getAmountUsed();
            cardsDto.setAvailableAmount(availableAmount);

            // Mapping updated values
            CardsMapper.mapToCards(cardsDto, cards);

            cardsRepository.save(cards);

            // Update Customer also
            Long customerId = cards.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Customer", "CustomerID", customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    // --------------------------
    //  DELETE CARD
    // --------------------------
    @Override
    public boolean deleteCard(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer", "mobileNumber", mobileNumber));

        cardsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }
}
