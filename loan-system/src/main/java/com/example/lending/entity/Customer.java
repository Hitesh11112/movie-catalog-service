package com.example.lending.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	
	//	Technical Identity
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//	Business Identity
	@NotBlank(message = "Customer name is required")
	@Column(nullable = false, unique = true, length = 20)
	private String customerNumber;
	
	//	Personal Information
	@NotBlank(message = "First name is required")
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	private String lastName;
	
	@NotNull(message = "Date of birth is required")
	@Past(message = "Date of birth must be in the past")
	private LocalDate dateOfBirth;
	
	@NotBlank(message = "Email is required")
	@Email(message = "Email must be a valid address")
	private String email;
	
	@NotBlank(message = "Phone number is required")
	@Pattern(regexp = "^[0-9]{10}$",
			message = "Phone number must be 10 Digits long")
	private String phoneNumber;
	
	//	 Financial / Employment Information
	@NotBlank(message = "Employment type is required")
	@Pattern(regexp = "SALARIED|SELF_EMPLOYED|UNEMPLOYED|RETIRED",
	         message = "Employment type must be one of: SALARIED, SELF_EMPLOYED, UNEMPLOYED, RETIRED")
	private String employmentType;
	
	@NotNull(message = "Annual income is required")
	@Min(0)
	private BigDecimal annualIncome;
	
	//	KYC & Customer Life-cycle
	@NotBlank(message = "KYC status is required")
	@Pattern(regexp = "PENDING|VERIFIED|REJECTED",
    			message = "KYC status must be one of: PENDING, VERIFIED, REJECTED")
	private String kycStatus;
	
	@NotBlank(message = "Customer status is required")
	@Pattern(regexp = "ACTIVE|INACTIVE|SUSPENDED|CLOSED",
	         message = "Customer status must be one of: ACTIVE, INACTIVE, SUSPENDED, CLOSED")
	private String customerStatus;
	
	//  Auditing
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
