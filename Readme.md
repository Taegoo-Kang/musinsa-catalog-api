# 무신사(상품카탈로그개발팀) Backend 과제

---
- Java 버전: 21
- 프로젝트 빌드 툴: Gradle
- port: 8080

## 구현 범위
 - 과제 API : 1~4 모두 구현 완료
   - ControllerAdvice를 사용해 에러 발생 시 실패 사유를 응답값에 포함하도록 처리했습니다. 
 - 테스트 : UtilityClass, Repository, Service, Exception
 - Frontend : thymeleaf와 bootstrap 이용해서 구현했습니다.
   - 과제 1,2,3 조회
   - 과제 4
     - 브랜드 조회/추가/업데이트/삭제
     - 상품 조회 구현 (추가/업데이트/삭제는 시간이 부족하여 구현하지 못했습니다.)

## 빌드 및 실행 (IntelliJ 기준)
  - 모듈 설정 - 프로젝트 - SDK : Java21 버전 선택
  - IntelliJ 설정에서 '어노테이션 처리 활성화' 체크
  - MusinsaCatalogApiApplication > 실행 (QueryDsl 문제로 빌드 실패 할 경우 한 번 더 실행)
  - port: 8080

## 확인 방법 (어플리케이션 실행 후)
  - DB : http://localhost:8080/h2-console/
    - jdbc - h2:mem:catalog;MODE=MySQL
    - User Name - sa
    - Password - 없음
  - API : http://localhost:8080/swagger-ui/index.html
  - FE : http://localhost:8080