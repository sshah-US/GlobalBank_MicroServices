package com.restmicro.cards.service;

import com.restmicro.cards.dto.CustomerDto;

public interface CardsService {
	
	void createCard(CustomerDto customerDto);
	
	CustomerDto fetchCard(String mobileNumber);
	
	boolean updateCard(CustomerDto customerDto);
	
	boolean deleteCard(String mobileNumber);
	
}
