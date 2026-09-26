package com.example.lending.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Application number is required")
	@Column(nullable = false, unique = true, length = 30)
	private String applicationNumber;

	@NotNull(message = "Customer is required")
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@NotNull(message = "Loan product is required")
	@ManyToOne
	@JoinColumn(name = "loan_product_id", nullable = false)
	private LoanProduct loanProduct;

	@NotNull(message = "Requested amount is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Requested amount must be greater than 0")
	private BigDecimal requestedAmount;

	@Min(value = 1, message = "Requested tenure must be at least 1 month")
	private int requestedTenure;

	@NotBlank(message = "Purpose is required")
	@Size(max = 255, message = "Purpose cannot exceed 255 characters")
	private String purpose;

	@NotBlank(message = "Application status is required")
	@Pattern(regexp = "SUBMITTED|UNDER_REVIEW|APPROVED|REJECTED|DISBURSED|CANCELLED",
	         message = "Application status must be one of: SUBMITTED, UNDER_REVIEW, APPROVED, REJECTED, DISBURSED, CANCELLED")
	private String applicationStatus;

	//	Auditing
	private LocalDateTime submittedAt;
	private LocalDateTime updatedAt;
}