package com.restmicro.cards.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


import com.restmicro.cards.dto.AccountsDto;


@FeignClient("accounts")
public interface AccountsFeignClient {
	 	@GetMapping(value = "/api/fetch",consumes = "application/json")
	 	public ResponseEntity<AccountsDto> fetchAccountDetails(@RequestHeader("easybank-correlation-id")
	    String correlationId,@RequestParam String mobileNumber);
}	 	

