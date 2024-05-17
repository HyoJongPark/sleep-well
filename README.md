# 🛌 sleep-well 개요

숙소 예약 프로젝트에 선착순 쿠폰 발행 및 사용 기능을 덫붙혀 중점적으로 다루는 프로젝트입니다.

### 🛌 무엇을 경험하려는 건가요?

- 고가용성 선착순 쿠폰 발행 및 사용 서비스를 구현
- 클린코드를 위한 꾸준한 코드 리팩토링을 진행 중입니다.
- 이유와 근거가 명확한 기술의 사용을 지향합니다.
- 단위테스트 커버리지 체크 적용 및 유지하기

### 🛌 Wiki

- [프로젝트 Use Case]()
- [프로젝트 Commit Convention]()

### 🛌 프로젝트 기술 스택

- ![Java](https://img.shields.io/badge/Java-17-007396?logo=java)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-6DB33F?logo=spring%20boot&logoColor=6DB33F)
- ![MySQL](https://img.shields.io/badge/MySQL-8.0.32-4479A1?logo=mysql&logoColor=4479A1)
- ![Redis](https://img.shields.io/badge/Redis-7.2.4-DC382D?logo=redis&logoColor=white)
- ![JPA](https://img.shields.io/badge/MyBatis-3.0.3-000000?logo=&logoColor=000000)
- ![Gradle](https://img.shields.io/badge/Gradle-8.5-02303A?logo=gradle&logoColor=02303A)
- ![JUnit](https://img.shields.io/badge/JUnit-5.8.2-25A162?logo=junit&logoColor=white)
- ![Docker](https://img.shields.io/badge/Docker-24.0.2-2496ED?logo=docker&logoColor=white)
- ![IntelliJ](https://img.shields.io/badge/IntelliJ-2023.1-000000?logo=intellijidea&logoColor=000000)
- ![JMeter](https://img.shields.io/badge/JMeter-5.6.2-D21717?logo=apache%20jmeter&logoColor=white)
- ![Github Actions](https://img.shields.io/badge/Jenkins-2.449-D24939?logo=jenkins&logoColor=white)

### 🛌 프로젝트를 통해 해결한 궁금증과 문제들

- **프로젝트의 포매팅 논쟁을 정리했습니다.**
    - [GitHub & IntelliJ 프로젝트에 코드 포멧 설정하기](https://velog.io/@phj5075/GitHub-IntelliJ-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EC%97%90-%EC%BD%94%EB%93%9C-%ED%8F%AC%EB%A9%A7-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0)
- **쿠폰 발급 로직의 동기화를 어떻게 하셨나요?**
    - [동기화가 안되는데 이거 버그에요?](https://velog.io/@phj5075/%EB%8F%99%EA%B8%B0%ED%99%94%EA%B0%80-%EC%95%88%EB%90%98%EB%8A%94%EB%8D%B0-%EC%9D%B4%EA%B1%B0-%EB%B2%84%EA%B7%B8%EC%97%90%EC%9A%94)
    - [비관적 락과 낙관적 락으로 성능 개선]()
- **DB I/O 시간 개선을 위해 어떤 시도를 할 수 있을까요?**
    - [쿼리 플랜으로 느린 쿼리 개선하기]()
    - [더 나은 개선을 위해 캐시 도입 고려]()
- **그 외 어떤 고민을 하셨나요?**
    - [blue-green 으로 무중단 배포 도입하기]()
    - [스와핑을 통한 ec2 프리티어 cpu 부하 해결하기]()
    - [[Docker] 이미지 크기를 줄이기 위한 여정]()

### Commit Convention

- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정
- style : 코드 포맷팅 설정 등 코드 스타일 관련 수정
- refactor : 코드 리팩토링(기능 변경 x)
- test : 테스트 코드
- chore : 빌드 업무 수정, 패키지 매니저 수정
