# 일정 관리 앱 만들기
Spring Framework를 활용해 간단한 일정 관리 애플리케이션을 개발하는 프로젝트이다. 

이 과제를 통해 Spring의 주요 기능을 학습하고, RESTful API 설계 및 데이터베이스 연동 경험을 쌓는 것을 목표로 한다.

<br>

## 개발 환경
- **언어**: Java 17
- **프레임워크**:  Spring (Spring Boot 3.1.0)
- **IDE**: IntelliJ IDEA
- **빌드 도구**: Gradle
- **데이터베이스**: MySQL

<br>

## 프로젝트 구조
```
src/
├── main/
│   └── java/com/example/scheduler/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── exception/
│   │   ├── repository/
│   │   ├── service/
│   │   └── SchedulerApplication.java
│   └── resources/
│   │   └── application.properties
``` 

<br>

## 프로젝트 실행 방법
### 1. GitHub에서 프로젝트 클론
터미널에서 아래 명령어를 통해 프로젝트를 클론한다.
```bash
git clone https://github.com/iboyeon0816/scheduler.git
cd scheduler
```

### 2. 데이터베이스 연결 정보 설정
`src/main/resources` 디렉토리에 `application-SECRET.properties` 파일을 생성하고, 아래 내용을 추가한다.
```properties
spring.datasource.password=your_db_password
```

### 3. 데이터베이스 생성
MySQL에서 `schedule.sql` 내 스크립트를 실행하여 필요한 데이터베이스 및 테이블을 생성한다.
- `schedule.sql` 파일은 프로젝트 루트에 위치한다.

### 4. 애플리케이션 실행
IntelliJ에서 프로젝트를 열고, `SchedulerApplication.java` 파일을 찾아 메인 메소드를 실행한다.
- 우클릭 > Run 'SchedulerApplication'을 클릭하여 애플리케이션을 실행한다.
- 애플리케이션이 실행되면 `http://localhost:8080`에서 API를 확인할 수 있다.

<br>

## 필수 과제 기능 요구사항
### 1. 일정 작성하기
설명: 사용자는 새로운 일정을 추가할 수 있어야 한다.
- 일정은 반드시 `할 일`, `작성자명`, `비밀번호`, `작성일`, `수정일`을 포함해야 한다.
- `작성일`과 `수정일`은 날짜와 시간을 모두 포함해야 한다.
- 각 일정의 `ID`는 자동으로 생성하여 관리해야 한다.
- 작성 완료 후, `비밀번호`를 제외한 일정 정보를 반환해야 한다.

### 2. 전체 일정 조회하기
설명: 사용자는 저장된 일정을 모두 조회할 수 있어야 한다.
- 전체 일정을 조회하거나, `수정일` 또는 `작성자명`으로 필터링 할 수 있다.
- 결과는 `수정일`을 기준으로 내림차순으로 정렬되어야 한다.
- 조회 시 `비밀번호`를 제외한 일정 정보를 반환해야 한다.

### 3. 일정 단건 조회하기
설명: 사용자는 선택한 일정 단건의 정보를 조회할 수 있어야 한다.
- `ID`를 이용하여 조회할 일정을 선택해야 한다.
- 조회 시 `비밀번호`를 제외한 일정 정보를 반환해야 한다.

### 4. 일정 수정하기
설명: 사용자는 선택한 일정을 수정할 수 있어야 한다.
- `ID`를 이용하여 수정할 일정을 선택해야 한다.
- 일정의 `할 일`과 `작성자명`만 수정이 가능하다.
- 일정 수정 요청 시 비밀번호를 함께 전달해야 한다.
- 수정 완료 후, `비밀번호`를 제외한 일정 정보를 반환해야 한다.

### 5. 일정 삭제하기
설명: 사용자는 저장된 일정을 삭제할 수 있어야 한다.
- 일정 삭제 요청 시 비밀번호를 함께 전달해야 한다.

<br>

## 도전 과제 기능 요구사항
### 1. 작성자 정보 관리
- 작성자는 `이름`, `이메일`, `등록일`, `수정일`을 포함해야 한다.
- 고유 식별자(ID)를 이용해 작성자를 식별한다.

### 2. 페이지네이션
- 일정 목록 조회에 페이지네이션을 적용한다.

### 3. 예외 발생 처리
- 필요에 따라 사용자 정의 예외 클래스를 생성한다.
- `@ExceptionHandler`를 활용해 공통 예외 처리를 구현한다.
- 예외가 발생한 경우 적절한 HTTP 상태 코드와 함께 메시지를 전달한다.

### 4. 검증
설명: 다음 조건에 대한 검증을 수행한다.
- `할 일`은 필수값이며, 최대 200자 이내로 제한한다.
- `비밀번호`는 필수값이다.
- `이메일`은 이메일 형식을 따라야 한다.

<br>

## ERD
<img width="700" alt="ERD" src="https://github.com/user-attachments/assets/4786c3ce-f674-4123-b702-18082213dda4" />

<br>
<br>

## API 명세서
### 에러 응답 형식
- **Description**: 에러 응답은 다음 형식으로 반환된다.
- **Response Format**:
  ```json
  {
    "status": 400,
    "message": "Validation Failed",
    "result": {
      "fieldName": "Error Message",
      "anotherField": "Another Error Message"
    }
  }
  ```
  - `status`: HTTP 상태 코드 (예: 400, 404)
  - `message`: 에러에 대한 간략한 설명
  - `result`: 추가 정보가 필요한 경우에 포함되며, 에러 필드와 메시지가 키-값 형태로 반환됨. 값이 없는 경우 생략

<br>

### 작성자 관리 API
#### **POST** `/authors` 작성자 생성
- **Description**: 새로운 작성자를 생성한다.
- **Request Body**:
  ```json
  {
    "name": "temp",
    "email": "temp@gmail.com"
  }
  ```
- **Response**:
  - **Status**: `201 Created`
  - **Body**:
  ```json
  {
    "authorId": 1,
    "name": "temp",
    "email": "temp@gmail.com",
    "createdAt": "2025-01-27T10:41:19",
    "updatedAt": "2025-01-27T10:41:19"
  }
  ```

<br>

### 일정 관리 API
#### **POST** `/schedules` 일정 생성
- **Description**: 새로운 일정을 생성한다.

- **Request Body**:
  ```json
  {
    "authorId": 1,
    "password": "pwd1234",
    "task": "해야할 일"
  }
  ```

- **Response**:
  - **Status**: `201 Created`
  - **Body**:
  ```json
  {
    "scheduleId": 1,
    "authorId": 1,
    "authorName": "temp"
    "task": "해야할 일"
    "createdAt": "2025-01-27T10:41:19",
    "updatedAt": "2025-01-27T10:41:19"
  }
  ```

#### **GET** `/schedules` 일정 전체 조회
- **Description**: 일정 목록을 조회한다. 일정은 선택적으로 `updatedAt`, `authorId` 필터로 조회할 수 있으며, 페이지네이션 기능을 지원한다.

- **Request Parameters**:
  - `updatedAt` (optional): 특정 날짜에 업데이트된 일정을 필터링한다.
  - `authorId` (optional): 특정 작성자의 일정을 필터링한다.
  - `page` (optional): 조회할 페이지 번호 (1 이상, 기본값:1)
  - `size` (optional): 페이지당 조회할 일정의 개수 (1 이상, 기본값:4)
 
- **Response**:
  - **Status**: `200 OK`
  - **Body**:
  ```json
  [
    {
      "scheduleId": 1,
      "authorId": 1,
      "authorName": "temp"
      "task": "해야할 일"
      "createdAt": "2025-01-27T10:41:19",
      "updatedAt": "2025-01-27T10:41:19"
    },
    {
        "scheduleId": 2,
        "authorId": 1,
        "authorName": "temp"
        "task": "해야할 일2"
        "createdAt": "2025-01-27T10:41:19",
        "updatedAt": "2025-01-27T10:41:19"
      }
  ]
  ```

#### **GET** `/schedules/{scheduleId}` 일정 단 건 조회
- **Description**: 특정 ID를 가진 일정을 조회한다.

- **Path Variable**:
  - `scheduleId` (required): 조회할 일정의 ID
    
- **Response**:
  - **Status**: `200 OK`
  - **Body**:
  ```json
  {
    "scheduleId": 1,
    "authorId": 1,
    "authorName": "temp"
    "task": "해야할 일"
    "createdAt": "2025-01-27T10:41:19",
    "updatedAt": "2025-01-27T10:41:19"
  }
  ```

#### **PATCH** `/schedules/{scheduleId}` 일정 수정
- **Description**: 특정 ID를 가진 일정을 수정한다.

- **Path Variable**:
  - `scheduleId` (required): 수정할 일정의 ID

- **Request Body**:
  ```json
  {
    "password": "pwd1234",
    "task": "수정된 할 일"
  }
  ```
  
- **Response**:
  - **Status**: `200 OK`
  - **Body**:
  ```json
  {
    "scheduleId": 1,
    "authorId": 1,
    "authorName": "temp"
    "task": "수정된 할 일"
    "createdAt": "2025-01-27T10:41:19",
    "updatedAt": "2025-01-28T10:41:19"
  }
  ```

#### **DELETE** `/schedules/{scheduleId}` 일정 삭제
- **Description**: 특정 ID를 가진 일정을 삭제한다.

- **Path Variable**:
  - `scheduleId` (required): 삭제할 일정의 ID

- **Request Body**:
  ```json
  {
    "password": "pwd1234"
  }
  ```
  
- **Response**:
  - **Status**: `204 No Content`
