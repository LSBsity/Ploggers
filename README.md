# 🌏지구를 지키는 플로깅🌏
# Ploggers (2024 GLOW HACKATHON)
### 지역사회에 지속 가능한 발전을 위한 지역 사회 문제 해결을 위한 아이디어 : 플로거 커뮤니티

#### 지구와 환경을 함께 지킬 수 있는 플로깅은 다음과 같은 뜻을 가지고 있습니다.
#### '이삭을 줍는다'라는 뜻인 스웨덴어 plocka upp과 영어 단어 jogging이 합쳐져 생긴 합성어입니다. 이는 조깅을 하면서 쓰레기를 줍는 행동입니다.
#### 2016년 스웨덴에서 처음 시작해 북유럽을 중심으로 확산되다가 현재는 국내를 비롯하여 전 세계적인 운동 트렌드로 자리매김하고 있습니다.

### 그래서 저희 코드루키 팀은 플로깅을 하는 이들을 위한 커뮤니티를 만들었습니다.
#### Plogger들이 서로 교류하고 소통하며 즐겁고 활발한 활동을 하게 돕고, 이로 인한 환경 보호 효과에 조금이나마 도움이 되고 싶습니다.
-----
## 팀원
### [BE이승빈] (https://github.com/LSBsity)
### [BE오지우] (https://github.com/5hjiwoo)
### [FE박신영] (https://github.com/parknew0)
### [FE김건호] (https://github.com/kimgho)
-----
## 동기
### 모든 인원이 웹 어플리케이션 개발 공부를 시작한지 얼마 되지 않았고, 모두 처음 참여하는 해커톤이었기에 기본적인 CRUD에 익숙해지기 위해 커뮤니티 프로젝트를 해보자 마음먹었습니다. 남들처럼 거창한 프로젝트를 해보고 싶다는 마음도 있었지만 이번 기회에는 기본적인 부분부터 차근차근 경험해보자는 마음가짐과, 비록 지금은 소규모로 시작하지만 동네부터 온 지구까지 플로깅 활동을 넓히고 싶은 마음가짐으로 시작하였습니다.
-----
## ERD

![](https://velog.velcdn.com/images/sity51/post/391b0c38-216a-4f4d-b45d-6d94fae8e01a/image.png)

-----

## 사용 기술
### BACKEND
- Spring Boot
- Spring Data JPA, QueryDsl
- Mysql
- Amazon Web Service EC2, RDS
### FRONTEND
- React
- Zustand
- Vite
- TypeScript
-----
## 기본 기능 (Swagger -> http://52.79.216.37:8080/swagger-ui/index.html#/)
- 회원가입 (BCryptPasswordEncoder 암호화)
- 로그인 (JWT Token 이용)
- 게시글 CRUD (사진, 동영상 기능 추가 예정)
- 댓글 CRUD
- 좋아요
- 프로필 사진 (사진 기능 추가 예정)
- 회원 닉네임 수정
- 회원 비밀번호 수정
- 회원 탈퇴
-----
## 추후 개선 예정
- AWS S3를 이용한 사진 기능 추가
- OAuth기능 추가
- UI, UX 개선
- 팀 그룹 기능 추가
- 친구 기능 추가
