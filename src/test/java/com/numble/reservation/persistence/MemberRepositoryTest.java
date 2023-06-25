package com.numble.reservation.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.numble.reservation.persistence.entity.member.MemberEntity;
import com.numble.reservation.persistence.repository.member.MemberRepository;
import com.numble.reservation.persistence.spec.MemberEntitySpec;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
public class MemberRepositoryTest {
	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void saveMemberEntity() {
		final MemberEntity member = MemberEntitySpec.of();

		final MemberEntity savedMember = memberRepository.save(member);

		Assertions.assertNotNull(savedMember);
		Assertions.assertNotNull(savedMember.getMemberId());

		Assertions.assertEquals(member.getEmail(), savedMember.getEmail());
		Assertions.assertEquals(member.getPassword(), savedMember.getPassword());
	}
}
