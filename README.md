# SPRING ADVANCED

### 코드 기능 개선 프로젝트

#### Lv2 - JpaTodo :
+ Validate 어노테이션을 Entity에서 Dto로 이동
+ userController에서 Dto 형식 예외 처리
+ todo, userService에서 비즈니스 로직 예외처리(Global Exception Handler, Custom Exception, messages.properties 생성)

#### Lv3 - Spring-advanced :
+ AuthService signup()에서 Early Return으로 코드 개선
+ WeatherClient getTodayWeather()에서 불필요한 if-else문 제거
+ ManagerService saveManager(), deleteManager() 부분의 중복 코드를 아래 private 메서드로 생성
+ todo, user의 findById 중복 코드를 CommonValidator에서 일괄 처리

#### Lv4 - Spring-advanced :
+ PassEncoderTest, ManagerServiceTest, CommentServiceTest 테스트 코드 수정
+ aop 패키지에 Entity, Repository, Service를 생성하고, Request 값을 정상적으로 불러와 줄 RequestWrapper도 config 패키지에 생성