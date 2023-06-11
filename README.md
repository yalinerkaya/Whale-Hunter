# whale-hunter

### 프로젝트 소개
```
- 바이낸스 거래소에서 비트코인 정보를 조회하며 거대한 거래량 포착시 텔레그램에 알림을 보냅니다.
- 분산 서버 환경에서 일어날 수 있는 이슈를 시뮬레이션하고 서버의 성능 개선, 좋은 품질의 코드를 작성하기 위해 고민했습니다.
```
<hr>

### 주요 목표
서비스도중 장애를 겪었던 이유를 떠올려보며 문제 해결 능력을 다각화하여 시뮬레이션하는것
- Microservices 아키텍처 기반의 시스템 개발
- 백엔드 환경에서 발생할 수 있는 성능 저하 요소 분석 및 코드 개선을 통한 성능 개선
- 단위 테스트 진행 및 CI/CD 자동화
- 대용량 트래픽을 처리하기 위해 NoSQL (Memcached, Redis) 전략 구상과 API 설계
<hr>


### 기술 

<table>
  <tr>
    <td>
        <img src="https://images.velog.io/images/xx0hn/post/b1987229-58c5-436a-8d28-512a7f4ab718/175E023350248C8837.jpeg" width="100px" />
    </td>
    <td>
        <img src="https://images.velog.io/images/leeseojune53/post/b6527e64-30c9-40d4-a955-ddbc647edec1/Gradle_logo.png" width="100px" />
    </td>
    <td>
        <img src="https://mblogthumb-phinf.pstatic.net/MjAxNzA4MDNfOTMg/MDAxNTAxNzYzODU4Mzc3.VFDhGiVlIJ_1-1n7eakK3HsfRDMbGyEAPVOdB6bYah4g.jFz8_8GL41JgQUiDryDYYoFl-FaqG48EKtcz-EdHANwg.PNG.scw0531/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7_2017-08-03_%EC%98%A4%ED%9B%84_9.18.37.png?type=w2" width="100px" />
    </td>
    <td>
        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNVKGVT_dyDB_b758ACxJlJTKZFc6NaJvCI6Tt6V8RE5V6UnTP9ux_Pahj4DxhH7hiHeE&usqp=CAU" width="100px" />
    </td>
    <td>  
        <img src="https://i0.wp.com/codegym.vn/wp-content/uploads/2020/06/spring-jpa-query-5.png?fit=400%2C400&ssl=1" width="100px" />
    </td>
    <td>
        <img src="https://automated-testing.info/uploads/default/original/2X/7/760cbf21278280fd7d4980c577d64da634c9fc42.png" width="100px" />
    </td>
        <td>
        <img src="https://github.com/JayFreemandev/Whale-Hunter/assets/72185011/4bf350d8-0d63-4509-bedd-07f8e7fd0e77" width="100px" />
    </td>
        <td>
        <img src="https://github.com/JayFreemandev/Whale-Hunter/assets/72185011/1fd1801a-d9f3-4000-84ca-76aa0b6af2c7" width="100px" />
    </td>
  </tr>
  <tr>
    <td><b>Java 1.8</b></td>
    <td><b>Gradle</b></td>
    <td><b>Spring Boot</b></td>
    <td><b>MySQL</b></td>
    <td><b>Srping Data JPA</b></td>
    <td><b>JUnit5 / Mockito</b></td>
    <td><b>Redis</b></td>
    <td><b>Kafka</b></td>
  </tr>
</table>

### 스트레스 테스트
<table>
  <tr>
    <td>
        <img src="https://www.perfmatrix.com/wp-content/uploads/2020/07/JMeter-Logo.png" width="100px" />
    </td>
  </tr>
  <tr>
    <td><b>JMeter</b></td>
  </tr>
  
</table>

### 문서/협업
<table>
  <tr>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922619-3d7f20d6-4066-46bd-a305-a7873a4d6102.png" width="100px" />
    </td>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922764-354c44a9-05e9-4d5c-a10c-0da6676a80a0.png" width="100px" />
    </td>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922777-83956929-35f0-4746-a51b-98c116da2651.png" width="100px" />
    </td>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922791-263bc0f1-bebc-4eee-bdb5-9954af5bbaf9.png" width="100px" />
    </td>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922794-5a47df94-fc97-4beb-a6f4-16b24e315757.png" width="100px" />
    </td>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922809-866718e0-fb19-4840-9caa-111da31795d1.png" width="100px" />
    </td>
    <td>
        <img src="https://user-images.githubusercontent.com/103566826/177922816-6888632c-b218-4635-98d5-189addb835ca.png" width="100px" />
    </td>
  </tr>
  <tr>
    <td><b>Jira</b></td>
    <td><b>Notion</b></td>
    <td><b>Slack</b></td>
    <td><b>Git</b></td>
    <td><b>GitHub</b></td>
    <td><b>RestDocs</b></td>
    <td><b>AsciiDocs</b></td>
  </tr>
</table>
<hr>

### Architecture
![Architecture](https://user-images.githubusercontent.com/72185011/222195610-18231737-8503-4930-b1b7-c3ba0b04d40f.png)
<hr>

### ERD
![image](https://github.com/JayFreemandev/Whale-Hunter/assets/72185011/367c61b2-3327-4f7b-a68b-784a1ba4ffc8)

<hr>

### STRESS TEST 
*결과요약*

|  | 예약 0% | 예약 25% | 예약 50% | 예약 75% | 예약 100% |
| --- | --- | --- | --- | --- | --- |
| 동적예약계산 | 500 / 60 / 150 | 500 / 60 / 120 | 500 / 60 / 90 | 500 / 60 / 80 | 500 / 60 / 60 |
| TPS | 1092 | 780 | 586 | 484 | 400 |

<br>

## 컨벤션 (Branch, 커밋 메시지, API)

### Branch
- `develop` , `main` , `feature/*`
```
• develop : 개발용
• main : 배포용
• feature/* : 작업용
```

### 커밋 메시지([깃모지 사용](https://gitmoji.dev))
- 깃모지와 제목 사이에 띄어쓰기 하지 않음
```
[ 예시 ]
• 바이낸스 웹소켓 기능 추가
• (생략 가능) 부연 설명
• (생략 가능) 해결: #123 / 참고: #456, #789
```

> Subject
```
• 길어지지 않도록 작성
• "~ 추가", "~ 수정", "~ 리팩토링", ...
```

> Body
```
• 부연설명이 필요한 경우 선택하여 작성
```

> Footer
```
• 부가적으로 issue tracker id를 작성할 때 작성
```
<hr>

## API Document
Spring Rest Docs를 적용, API 호출시 발생 가능한 Error Spec 따로 기술  

### Error Spec
| 오류 코드 | 오류 메시지                        | 해결 방법                   |
| ---- | ------------------------------- | ------------------------------- |
| 20001  | 비정상적인 카프카 요청입니다.   | 정상적인 카프카 요청이 필요합니다.                        |
| 30001  | 비정상적인 레디스 요청입니다.   | 정상적인 레디스 요청이 필요합니다.           |

[쿠팡 API Docs Error Spec 참조](https://developers.coupangcorp.com/hc/ko/articles/360033877853-%EC%83%81%ED%92%88-%EC%83%9D%EC%84%B1)

<hr>

### 기술적 이슈
- 분산 환경에서의 트랜잭션 관리
- Netflix Hystric를 이용하여 MSA 장애 발생 재현
- 서비스 호출(TCP 세션) 성능 향상을 위한 gRPC 도입
- MSA 환경에서 로깅과 모니터링 문제
- 커넥션풀 계산과 서버 성능 테스트를 위한 JMeter  
<hr>

### 원하는 조건
- 200 EMA 1M above 
- 150 above 200 EMA above
- 50 above those two 
- price above 50 
- price is above at least 30% 52 weeks low
- price is within 25 percent 52 week high -> close and high is better -> percentage 필요
- relative strength ranking less than 70 -> high better -> percentage 필요
