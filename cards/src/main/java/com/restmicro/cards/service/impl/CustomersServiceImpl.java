
package com.restmicro.cards.service.impl;

import com.restmicro.cards.dto.AccountsDto;
import com.restmicro.cards.dto.CardsDto;
import com.restmicro.cards.dto.CustomerDetailsDto;
import com.restmicro.cards.dto.LoansDto;
import com.restmicro.cards.entity.Cards;
import com.restmicro.cards.entity.Customer;
import com.restmicro.cards.exception.ResourceNotFoundException;
import com.restmicro.cards.mapper.CardsMapper;
import com.restmicro.cards.mapper.CustomerMapper;
import com.restmicro.cards.repository.CardsRepository;
import com.restmicro.cards.repository.CustomerRepository;
import com.restmicro.cards.service.ICustomersService;
import com.restmicro.cards.service.client.AccountsFeignClient;
import com.restmicro.cards.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomersServiceImpl implements ICustomersService {

    private CardsRepository cardsRepository;
    private CustomerRepository customerRepository;
    private AccountsFeignClient accountsFeignClient;
    private LoansFeignClient loansFeignClient;

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Customer Details based on a given mobileNumber
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Cards cards = cardsRepository.findByCardNumber(customer.getCardNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Card", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetailsDto.setCardsDto(CardsMapper.mapToCardsDto(cards, new CardsDto()));

        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoanDetails(correlationId, mobileNumber);
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());

        ResponseEntity<AccountsDto> accountsDtoResponseEntity = accountsFeignClient.fetchAccountDetails(correlationId, mobileNumber);
        customerDetailsDto.setAccountsDto(accountsDtoResponseEntity.getBody());

        return customerDetailsDto;

    }
}
