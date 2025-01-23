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

## API 명세서
[API 명세서](https://documenter.getpostman.com/view/28485807/2sAYQdkAQf#759d8a7c-d6a8-4a25-9784-d93099e1dca3)

<br> 

## ERD
[ERD](https://www.erdcloud.com/d/eiEEnREJrdEdLYD2D)
