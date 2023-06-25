package com.numble.reservation.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;
import com.numble.reservation.persistence.repository.businessmember.BusinessMemberRepository;
import com.numble.reservation.persistence.spec.BusinessMemberEntitySpec;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
public class BusinessMemberRepositoryTest {
	@Autowired
	private BusinessMemberRepository businessMemberRepository;

	@Test
	public void saveBusinessMemberEntity() {
		final BusinessMemberEntity businessMember = BusinessMemberEntitySpec.of();

		final BusinessMemberEntity savedBusinessMember = businessMemberRepository.save(businessMember);

		Assertions.assertNotNull(savedBusinessMember);
		Assertions.assertNotNull(savedBusinessMember.getBusinessMemberId());

		Assertions.assertEquals(businessMember.getEmail(), savedBusinessMember.getEmail());
		Assertions.assertEquals(businessMember.getPassword(), savedBusinessMember.getPassword());
		Assertions.assertEquals(businessMember.getBusinessLicense(), savedBusinessMember.getBusinessLicense());
	}
}
