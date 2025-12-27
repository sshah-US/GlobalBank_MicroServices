package com.restmicro.cards.dto;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cards")
public record CardsContactInfoDTO(
        String message,
        Map<String,String> contactDetails,
        String env
) {}
