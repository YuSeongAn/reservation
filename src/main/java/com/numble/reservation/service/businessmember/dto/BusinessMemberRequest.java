package com.numble.reservation.service.businessmember.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.numble.reservation.code.BusinessMemberType;

import lombok.Data;

@Data
public class BusinessMemberRequest {
	@Email
	private String email;
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "invalid password format")
	private String password;
	private String businessLicense;
	@NotNull
	private BusinessMemberType businessMemberType;
}
