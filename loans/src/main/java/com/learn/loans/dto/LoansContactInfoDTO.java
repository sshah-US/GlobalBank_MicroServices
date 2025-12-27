package com.learn.loans.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoansContactInfoDTO {

	private String message;
	private Map<String, String> contactDetails;
	private String env;
}
