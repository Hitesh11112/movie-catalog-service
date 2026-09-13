package com.example.lending.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanProduct {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Product Code is required")
	@Column(nullable = false, unique = true, length = 20)
	private String productCode;
	
	@NotBlank(message = "Product Name is required")
	@Column(nullable = false, length = 100)
	private String productName;
	
	@NotNull(message = "Minimum amount is required")
    @DecimalMin(value = "0.01", message = "Minimum amount must be greater than 0")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal minimumAmount;

    @NotNull(message = "Maximum amount is required")
    @DecimalMin(value = "0.01", message = "Maximum amount must be greater than 0")
    @Column(nullable = false, precision = 15, scale = 2)	
    private BigDecimal maximumAmount;
	
	@NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate cannot be negative")
	@DecimalMax(value = "100", message = "Interest rate can not exceed 100%")
	private BigDecimal interestRate;
	
	@Min(value = 1,message = "Maximum tenture must in months")
	@Column(nullable = false)
	private int maximumTenure;
	 	    
	
	@NotBlank(message = "Status is required")
	@Pattern(regexp = "ACTIVE|INACTIVE|DISCONTINUED",
			message = "Status must be one of: Active, InActive, Discontinued")
	@Column(nullable = false, length = 20)	
	private String status;
		
	//Auditing
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
