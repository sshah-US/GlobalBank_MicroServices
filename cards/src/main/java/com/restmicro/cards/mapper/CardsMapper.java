package com.restmicro.cards.mapper;

import com.restmicro.cards.dto.CardsDto;
import com.restmicro.cards.entity.Cards;

public class CardsMapper {
	
	 public static CardsDto mapToCardsDto(Cards Cards, CardsDto CardsDto) {
	        CardsDto.setCardNumber(Cards.getCardNumber());
	        CardsDto.setCardType(Cards.getCardType());
	        CardsDto.setTotalLimit(Cards.getTotalLimit());
	        CardsDto.setAmountUsed(Cards.getAmountUsed());
	        CardsDto.setAvailableAmount(Cards.getAvailableAmount());
	        
	        return CardsDto;
	    }

	    public static Cards mapToCards(CardsDto CardsDto, Cards Cards) {
	    	Cards.setCardNumber(CardsDto.getCardNumber());
	        Cards.setCardType(CardsDto.getCardType());
	        Cards.setTotalLimit(CardsDto.getTotalLimit());
	        Cards.setAmountUsed(CardsDto.getAmountUsed());
	        Cards.setAvailableAmount(CardsDto.getAvailableAmount());
	        
	        return Cards;
	    }

}
