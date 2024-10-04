## 커먼 Backend

## 🚀 Latest Version : 0.0.0 (현재 개발중인 서버)

## Description
커먼은 배드민턴 동호회와 같은 취미 활동을 지역 사람들이 즐기는데 겪은 어려움을 해결하기 위해 시작된 프로젝트입니다. 
많은 사람들이 초보자일지라도 다양한 취미 활동을 즐길 수 있는 세상을 만드는 것을 목표로 합니다.
메인 컨텐츠는 하루모임과 소모임이며, 채팅과 데일리 등의 기능이 있습니다.

## Tech
- Spring : 3.2.4
- JPA : 3.2.4
- Security-Crypto : 6.2.3
- MySQL : 8.3.0

## Rule
- 연관관계는 불필요한 양방향 관계 설정은 최소화하고, 최대한 단방향 관계만으로 구성될 수 있도록 설계한다.
- 삭제는 Soft Delete 를 기본으로 한다.
- DB 에 저장하는 모든 데이터는 기본 정보를 갖는 Base Entity 를 상속받는다.
- boolean 에 해당하는 값은 isDeleted 대신 deleted, isYesterday 대신 yesterday 와 같이 is 를 사용하지 않는다.
- 직접 native 쿼리를 사용하는 경우 반드시 deleted = false 를 주어 필요한 정보만 가져올 수 있도록 한다.

## Contact
Author : [YunJungHun](https://github.com/yunjunghun0116)


