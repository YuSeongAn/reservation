package com.numble.reservation.persistence.repository.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.numble.reservation.persistence.entity.member.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	Optional<MemberEntity> findByEmail(String email);
}
