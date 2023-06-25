package com.numble.reservation.service.businessmember.dto;

import lombok.Data;

@Data
public class BusinessMemberLoginRequest {
	private String email;
	private String password;
}
