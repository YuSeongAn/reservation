package com.numble.reservation.persistence.repository.businessmember;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.numble.reservation.persistence.entity.businessmember.BusinessMemberEntity;

public interface BusinessMemberRepository extends JpaRepository<BusinessMemberEntity, Long> {

	Optional<BusinessMemberEntity> findByEmail(String email);
}
