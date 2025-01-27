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
<img width="800" alt="erd" src="https://github.com/user-attachments/assets/3294bdf4-53cd-4b20-96c0-d16e17c3764a" />

<br>
<br>

## API 명세서
### Author (작성자 관리 API)
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

### Schedule (일정 관리 API)
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
