package com.shopmax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopmax.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findByEmail(String email);
}
