# Whale-Hunter
- 바이낸스 거래소에서 원하는 조건의 종목을 추출하고 거대한 거래량을 포착하여 텔레그램에 알림을 보냅니다.
- 분산 서버 환경에서 일어날 수 있는 다양한 이슈를 해결하며 서버의 성능을 개선하고, 유지보수성 높은 프로그램을 작성하기 위해 고민하며 개발하였습니다

# Architecture
![image](https://user-images.githubusercontent.com/72185011/222195610-18231737-8503-4930-b1b7-c3ba0b04d40f.png)

# ERD

# 주요 목표
서비스도중 장애를 겪었던 이유를 떠올려보며 문제 해결 능력을 다각화하여 시뮬레이션하는것
- 객체 지향적으로 낮은 결합도와 높은 응집도를 가진 프로그램 설계
- 백엔드 환경에서 발생할 수 있는 성능 저하 요소 분석 및 코드 개선을 통한 성능 개선
- 단위 테스트 진행 및 CI/CD 자동화

# 기술 
- Spring Boot / Kotlin
- JPA
- Redis
- Junit
- Kafka
- WebSocket

# 기술적 이슈

# 원하는 조건
- 200 EMA 1M above 
- 150 above 200 EMA above
- 50 above those two 
- price above 50 
- price is above at least 30% 52 weeks low
- price is within 25 percent 52 week high -> close and high is better -> percentage 필요
- relative strength ranking less than 70 -> high better -> percentage 필요
