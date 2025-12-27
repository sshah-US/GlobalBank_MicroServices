package com.restmicro.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Cards",
        description = "Schema to hold Card information"
)
public class CardsDto {
	
	@NotEmpty(message = "CardNumber can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "CardNumber must be 16 digits")
    @Schema(
            description = "Card Number of Eazy Bank account", example = "3454433243242354"
    )
    private Long cardNumber;
	
	@NotEmpty(message = "CardType can not be a null or empty")
    @Schema(
            description = "Card type of Eazy Bank account", example = "Credit"
    )
    private String cardType;
	
	@NotEmpty(message = "TotalLimit can not be a null or empty")
	private Long totalLimit;
	
	private Long amountUsed;
	private Long availableAmount;	
	
}
