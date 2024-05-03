package com.sleepwell.user.domain;

import com.sleepwell.common.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	private static final String DEFAULT_IMAGE_URL = "";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String socialId;

	@Column
	@Enumerated(EnumType.STRING)
	private SocialType socialType;

	@Column
	private String nickname;

	@Column
	private String profileImage = DEFAULT_IMAGE_URL;

	public User(String socialId, SocialType socialType, String nickname, String profileImage) {
		this.socialId = socialId;
		this.socialType = socialType;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}
}
