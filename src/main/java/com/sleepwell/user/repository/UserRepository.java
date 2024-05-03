package com.sleepwell.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sleepwell.user.domain.SocialType;
import com.sleepwell.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findBySocialIdAndSocialType(String socialId, SocialType socialType);
}
