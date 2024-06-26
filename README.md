# 프로젝트 소개: 넓고 얕은 관계를 지향하는 SNS형 개인 블로그 플랫폼 티슈인맥!
![logo](https://github.com/himedi3DHS/TN-project/blob/master/logo.png)
## 소개
2030 세대를 위한 개인 블로그 서비스, 티슈인맥은 SNS의 피로감을 줄이고 개인의 사생활 보호를 강화하면서도 소통할 수 있는 플랫폼을 제공합니다.
이 플랫폼은 사용자들이 얕고 넓은 관계를 형성하고 유지할 수 있는 SNS의 요소를 결합하였습니다.
"얕고 넓은 관계"는 많은 사람들과 연결되어 있으면서도, 깊이 있는 친목 위주의 관계보다는 각자의 관심사나 주제를 공유하고 소통하는 활동에 초점을 맞춘 것을 의미합니다. 
사용자는 자유롭게 자신의 생각과 일상을 공유할 수 있으며, 필요에 따라 관계의 깊이를 조절할 수 있습니다. 
이 서비스는 SNS의 동적인 상호작용과 전통적인 블로그의 정적인 글쓰기를 조화롭게 결합하였습니다.

## 주요 기능
- **개인 블로그**: 각 사용자는 자신의 개인 블로그를 운영할 수 있으며, 이를 통해 자신의 이야기를 공유할 수 있습니다.
- **게시물 공유**: 사용자는 자신의 블로그에 글을 작성하고, 다른 사용자와 게시물을 공유할 수 있습니다.
- **관심사 탐색**: 사용자는 다른 사용자의 블로그를 방문하고, 관심 있는 주제나 이야기를 찾아볼 수 있습니다.
- **커뮤니티 기능**: 다른 사용자와의 상호작용을 위해 댓글, 좋아요, 공유 등의 기능을 제공합니다.
- **타임라인** : 다른 사람이 작성한 글을 일일히 찾아보지 않아도 쉽게 둘러볼 수 있도록 제공합니다.
- **관리자 기능** : 회원 및 게시물 관리를 위한 다양한 관리자 기능을 제공합니다.

## 목표
우리의 목표는 사용자들이 자유롭게 자신의 이야기를 공유하고, 다양한 사람들과 연결되어 새로운 아이디어를 발견하고 소통할 수 있는 플랫폼을 제공하는 것입니다. 
다른 이용자와의 교류를 통해 사용자들이 서로에게 영감을 주고 받을 수 있도록 지원하고자 합니다.

## 기대효과
우리의 프로젝트가 성공한다면, 개인 블로그와 SNS의 장점을 결합한 혁신적인 플랫폼을 제공함으로써 사용자들의 온라인 경험을 향상시킬 수 있을 것입니다. 
또한, 얕고 넓은 관계를 통해 다양한 아이디어와 지식을 공유함으로써 사회적 연결성을 증진시킬 수 있을 것으로 기대됩니다.

## 개발자
- **조한결** : 팀장, 타임라인, 검색, 프로젝트 초기구성 등

- **최호정** : 관리자

- **김다솔** : 블로그, 폴더, 화면 레이아웃 구성

- **조형기** : 회원, 댓글

- **이소율** : 결제, 관심도

## 기술 스택
- Backend: Java 17, Spring Boot, MyBatis
- Frontend: HTML, CSS, JavaScript, jQuery
- Database: MySQL
- Tools: IntelliJ IDEA, Gradle
- APIs: 아임포트(Iamport) 결제 시스템, 포트원(PortOne)
- Additional Libraries: Apache Lucene, jsoup

## 디렉토리 구조
![2024-04-12 15 22 06](https://github.com/himedi3DHS/TN-project/blob/master/2024-04-12%2015%2022%2006.jpg)

## 설치 및 실행 방법
1. 프로젝트 클론
```
git clone https://github.com/himedi3DHS/TN-project.git
```

2. 데이터베이스 설정
  - MySQL에서 tissuenetworkdb 데이터베이스 생성
  - 제공된 SQL 스크립트를 실행하여 필요한 테이블 생성

3. 환경 설정
  - application.yml 파일을 통해 데이터베이스 및 기타 필수 구성 요소 설정

4. 애플리케이션 실행
```
./gradlew bootRun
```

## 사용 방법
웹 브라우저를 통해 http://localhost:8080 접속 후, 인터페이스에 따라 블로그 작성, 조회 및 소통 가능

## 기여 방법
1. 프로젝트 포크
   - 프로젝트를 자신의 계정으로 포크(fork)하고, 로컬에 클론합니다.
     
2. 새 브런치 생성
```
git checkout -b your-branch-name
```

3. 변경사항 커밋 후 푸시
```
git commit -am 'Add some feature'
git push origin your-branch-name
```
   
4. 풀 리퀘스트 생성
   - 원본 저장소에 대한 풀 리퀘스트를 생성하고 설명과 함께 제출합니다.

## 이슈
- 첫 로그인 한 회원의 경우에는 추천글이 등록되지 않는 버그가 있음
- 간헐적으로 좋아요 버튼의 숫자가 음수가 되는 경우가 있음
- 로그인 실패시 실패 패이지로 이동하지 않음
- 로그인 실패시 다음번 로그인이 재대로 이루어지지 않음

## 문의
  - 이슈 트래커를 통해 문의사항을 남겨주세요.
---
