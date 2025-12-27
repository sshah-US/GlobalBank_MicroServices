package com.restmicro.cards.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor

public class Cards extends BaseEntity {
	
	@Column(name="customer_id")
    private Long customerId;

    @Column(name="mobile_number")
    private Long mobileNumber;
    
    @Column(name="card_number")
    @Id
    private Long cardNumber;

    @Column(name="card_type")
    private String cardType;
    
    @Column(name="total_limit")
    private Long totalLimit;
    
    @Column(name="amount_used")
    private Long amountUsed;
    
    @Column(name="available_amount")
    private Long availableAmount;

   
}
