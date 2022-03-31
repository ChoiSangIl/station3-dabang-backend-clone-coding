# station3 dabang clone coding

### git clone and run  
java version : JAVASE-11
```
git clone https://github.com/ChoiSangIl/station3-dabang-backend.git

cd station3-dabang-backend
./mvnw clean install -U
./mvnw spring-boot:run

OR

ide import maven project  
spring boot run
```

### api 명세(Swagger3)
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

#### 로그인, 회원가입 token 생성
Swagger 회원관련 API /members or /members/login 호출  

##### login response data exam
```
{
  "id": 1,
  "email": {
    "value": "dabang@station3.co.kr"
  },
  "jwtToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkYWJhbmdAc3RhdGlvbjMuY28ua3IiLCJpYXQiOjE2NDc3NjgyMDUsImV4cCI6MTY0Nzc2ODUwNX0.wJj6GcJHwbgvb0jQaWBDuARYis-9K2hVfS0hkpu-PI0"
}
```
##### swagger Authorize
생성된 jwtToken으로 value값 셋팅 후 API 호출

### H2콘솔 접속 (메모리디비)
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)  
JDBC URL: jdbc:h2:mem:dabang  
사용자명 sa

## 기본 데이터 생성
프로젝트 시작시 회원 데이터 자동 등록
```
id : 1
email : dabang@station3.co.kr
password : Station3#
```
