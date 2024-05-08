package com.sleepwell.coupon.repository;

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

import com.sleepwell.coupon.domain.Coupon;
import com.sleepwell.coupon.domain.DiscountType;
import com.sleepwell.coupon.domain.ExpiryType;
import com.sleepwell.coupon.service.IssuedCouponService;
import com.sleepwell.user.domain.SocialType;
import com.sleepwell.user.domain.User;
import com.sleepwell.user.repository.UserRepository;

@SpringBootTest
class IssuedCouponRepositoryTest {

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
	class CouponValidTest {

		@Test
		void coupon() throws InterruptedException {
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

			ExecutorService executorService = Executors.newFixedThreadPool(30);
			CountDownLatch latch = new CountDownLatch(memberCount);

			AtomicInteger successCount = new AtomicInteger();
			AtomicInteger failCount = new AtomicInteger();

			// when
			for (int i = 0; i < memberCount; i++) {
				User currentUser = users[i];

				executorService.submit(() -> {
					try {
						issuedCouponService.issueCoupon(currentUser.getId(), couponCode);
						successCount.incrementAndGet();
					} catch (Exception e) {
						System.out.println(e.getMessage());
						failCount.incrementAndGet();
					} finally {
						latch.countDown();
					}
				});
			}

			latch.await();

			System.out.println("successCount = " + successCount);
			System.out.println("failCount = " + failCount);

			// then
			long reservationCount = issuedCouponRepository.count();
			assertEquals(Math.min(memberCount, couponAmount), reservationCount);
		}
	}

}
