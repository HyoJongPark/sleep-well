package com.sleepwell.coupon.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.domain.DiscountType;
import com.sleepwell.coupon.domain.ExpiryType;
import com.sleepwell.coupon.repository.CouponRepository;
import com.sleepwell.coupon.repository.IssuedCouponRepository;
import com.sleepwell.user.domain.SocialType;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

@SpringBootTest
@ActiveProfiles("test")
class IssuedCouponIntegrationTest {

	@Autowired
	IssuedCouponService issuedCouponService;

	@Autowired
	IssuedCouponRepository issuedCouponRepository;

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	UserRepository userRepository;

	@Nested
	@DisplayName("쿠폰 정합성 테스트")
	class CouponConsistencyTest {

		//TODO: 각 테스트 전/후 rollback 등을 통한 격리 필요

		@Test
		@DisplayName("동시에 쿠폰을 발급 시, 재고 만큼의 수량만 발급한다.")
		void couponIssueInSynchronousEnvironment() throws InterruptedException {
			// given
			int memberCount = 30;
			int couponAmount = 10;
			String couponCode = "COUPON_TEST";
			User[] users = new User[memberCount];

			Coupon coupon = couponRepository.save(
				new Coupon("쿠폰", "상세 설명", couponCode, DiscountType.FIXED, 10, ExpiryType.FIXED,
					LocalDateTime.now().plusDays(1), couponAmount, LocalDateTime.now(),
					LocalDateTime.now().plusDays(1)));
			for (int i = 0; i < memberCount; i++) {
				User user = userRepository.save(new User("socialId", SocialType.GOOGLE, "nickname", "test_url"));
				users[i] = user;
			}

			userRepository.flush();
			ExecutorService executorService = Executors.newFixedThreadPool(memberCount);
			CountDownLatch latch = new CountDownLatch(memberCount);

			AtomicInteger successCount = new AtomicInteger();

			// when
			for (int i = 0; i < memberCount; i++) {
				User currentUser = users[i];

				executorService.submit(() -> {
					try {
						issuedCouponService.issueCoupon(currentUser.getId(), couponCode);
						successCount.incrementAndGet();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					} finally {
						latch.countDown();
					}
				});
			}

			latch.await();
			issuedCouponRepository.flush();
			long reservationCount = issuedCouponRepository.count();

			// then
			assertEquals(couponAmount, successCount.get());
			assertEquals(couponAmount, reservationCount);
		}

		@Test
		@DisplayName("100건의 쿠폰 발급 테스트")
		void CouponIssuanceTestWith100User() throws InterruptedException {
			// given
			int targetCount = 100;
			String couponCode = "COUPON_SPEED_TEST";
			User[] users = new User[targetCount];

			Coupon coupon = couponRepository.save(
				new Coupon("쿠폰", "상세 설명", couponCode, DiscountType.FIXED, 10, ExpiryType.FIXED,
					LocalDateTime.now().plusDays(1), targetCount, LocalDateTime.now(),
					LocalDateTime.now().plusDays(1)));
			for (int i = 0; i < targetCount; i++) {
				User user = userRepository.save(new User("socialId", SocialType.GOOGLE, "nickname", "test_url"));
				users[i] = user;
			}

			ExecutorService executorService = Executors.newFixedThreadPool(32);
			CountDownLatch latch = new CountDownLatch(targetCount);
			AtomicInteger successCount = new AtomicInteger();

			// when
			for (int i = 0; i < targetCount; i++) {
				User currentUser = users[i];

				executorService.submit(() -> {
					try {
						issuedCouponService.issueCoupon(currentUser.getId(), couponCode);
						successCount.incrementAndGet();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					} finally {
						latch.countDown();
					}
				});
			}

			latch.await();
			long reservationCount = issuedCouponRepository.count();

			// then
			assertEquals(targetCount, successCount.get());
		}
	}
}
