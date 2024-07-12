# 나만의무럭이-나무(Namoo)
![image](https://github.com/user-attachments/assets/0bbfbf72-6772-4922-bae5-7b5032c3241a)

## 서비스링크
- https://www.namoo.store
## 상세설명
![image](https://github.com/Isonade2/MoramMoram-back/assets/67320022/583b3e87-db11-4d05-9b26-70403d397f36)

식물을 키우는 데에 도움을 얻을 수 있는 스마트 맞춤형 식물 관리 서비스 입니다.

주요 기능

- 맞춤형 식물 관리 정보 제공: 사용자의 식물에 맞는 맞춤형 관리 정보를 제공합니다.
- 실시간 모니터링 및 기록: 센서를 이용하여 식물 상태를 실시간으로 모니터링하고 기록합니다.
- 퀘스트 및 알림 기능: 식물 관리에 필요한 퀘스트와 알림을 통해 사용자가 식물을 안정적으로 관리할 수 있도록 도와줍니다.
- 식물과의 대화: 실제 식물 데이터를 기반으로 학습된 AI와의 대화를 통해 사용자에게 식물과 상호작용하는 재미를 제공합니다.

⇒ 식물과 상호작용하는 즐거움을 느끼고, 식물을 지속적으로 키울 수 있도록 도와줍니다.
## 기획 계기

식물을 키울 때 다양한 어려움이 발생합니다. 현재 나의 식물 상태가 궁금한데 알 방법이 없고, 처음엔 그렇게 관심을 많이 줬는데 시간이 흐르면 물 주는 걸 깜빡하거나 방치하게 되는 일을 겪으신 분들이 있을 겁니다.
그래서 이러한 문제점들을 해결하고자 웹 애플리케이션, 인공지능, 아두이노를 활용하여 식물을 스마트하고 재밌게 키울 수 있는 서비스를 기획하게 되었습니다.

## 개발기간
- 2024-02-15 ~ 2024-05-29

## 팀 구성
<table>
  <tr >
    <td align="center" >
      <a href="https://github.com/sanghee01"><img src="https://avatars.githubusercontent.com/u/80993302?v=4"/></a>
    </td>
        <td align="center" >
      <a href="https://github.com/dlrhdns75177"><img src="https://avatars.githubusercontent.com/u/67143120?v=4"/></a>
    </td>
      <td align="center" >
      <a href="https://github.com/nemokoala"><img src="https://avatars.githubusercontent.com/u/109515854?v=4"/></a>
    </td>
    <td align="center" >
      <a href="https://github.com/Isonade2"><img src="https://avatars.githubusercontent.com/u/67320022?v=4"/></a>
    </td>
    <td align="center" >
      <a href="https://github.com/maybeaj"><img src="https://avatars.githubusercontent.com/u/112530022?v=4"/></a>
    </td>
    <td align="center" >
      <a href="https://github.com/leeyeonju02"><img src="https://avatars.githubusercontent.com/u/85239317?v=4"/></a>
    </td>
  </tr>
  <tr>
    <td align="center" >
      <a href="https://github.com/sanghee01/"><strong>이상희</strong></a><br>Fronted
    </td>
    <td align="center" >
      <a href="https://github.com/dlrhdns75177/"><strong>이고운</strong></a><br>Frontend
    </td>
    <td align="center" >
      <a href="https://github.com/nemokoala/"><strong>박재연</strong></a><br>FullStack
    </td>
    <td align="center" >
      <a href="https://github.com/Isonade2/"><strong>전준영</strong></a><br>Backend
    </td>
    <td align="center" >
      <a href="https://github.com/maybeaj/"><strong>이효진</strong></a><br>Backend
    </td>
    <td align="center" >
      <a href="https://github.com/leeyeonju02/"><strong>이연주</strong></a><br>Backend
    </td>
  </tr>
</table>

## 기술스택
![image](https://github.com/Isonade2/MoramMoram-back/assets/67320022/6c04c600-b9bf-4154-9b4e-73b1857e5ba9)

## 아키텍쳐
![image](https://github.com/Isonade2/MoramMoram-back/assets/67320022/adad10d6-ec18-46f9-998e-ad1f7be7d837)
![image](https://github.com/Isonade2/smart_plant_back/assets/67320022/9a012776-0a9a-4b0a-abfb-d4b035f10b52)

클라이언트
- 클라이언트는 리액트로 만든 정적 웹 페이지를 Vercel 웹서버를 통해 제공받습니다.
  
서버
-  대부분의 API 통신은 AWS EC2에 배포된 Spring WAS 서버를 통해 이루어지며, 이때 데이터는 MySQL 데이터베이스에 저장됩니다.
   
하드웨어
- 식물 기록 측정을 위한 하드웨어인 아두이노는 WiFi를 통해 원격으로 Spring 서버에 센서 측정 값을 전송합니다. 이 정보 역시 MySQL 데이터베이스에 저장되고 웹을 통해 언제든지 확인이 가능합니다.
  
인공지능
- 질병 분류 기능: 클라이언트에서 이미지를 플라스크 서버로 바로 전송을 하면 이미 학습시킨 모델을 통해 이미지를 분석하여 질병 분류 결과를 반환해줍니다.
- 챗봇 기능: 식물과 대화할 수 있는 챗봇 기능은 클라이언트에서 채팅을 시작하면, Flask 서버는 Spring 서버에 현재 식물의 상태를 요청하여 정보를 받아온 후, 이를 바탕으로 상황에 맞게 답변을 생성하여 클라이언트로 반환합니다.
  

## 주요 기능

### 시작하기
<table>
  <tr>
    <th align="center">회원가입</th>
    <th align="center">메일 인증</th>
    <th align="center">로그인 & 로그아웃</th>
  </tr>
  <tr>
    <th align="center">
      <img src="https://github.com/user-attachments/assets/7e4503c7-f34d-4a5e-8a11-310985f65743" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/5d965f3b-d373-4f47-9de3-5c8a3b587659" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/33d42c17-df38-4435-8f23-48d21b038180" style="width: 300px; height: auto;">
    </td>    
  </tr>
</table>



### 식물 등록
<img src="https://github.com/user-attachments/assets/5bde7c62-58cf-47a6-8601-50bdf79c1b6e" style="width: 300px; height: auto;">



### 홈
<table>
    <tr>
    <th align="center">식물 터치 이벤트</th>
    <th align="center">알림</th>
    <th align="center">퀘스트</th>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/59309cae-64cc-4820-ba62-e434a28cd35d" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/baa5ab01-5ebb-43c3-b862-10d594418987" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/187addb3-45dd-4a93-9013-e16fe1e5d4b2" style="width: 300px; height: auto;">
    </td>    
  </tr>
</table>



### 원격 수분 공급 기능
<table>
    <tr>
    <th align="center">물주기 버튼 클릭 시</th>
    <th align="center">원격 수분 공급</th>
  </tr>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/0b2de7a9-4041-4411-a2d2-66ec4f76d96e" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/10169e1f-355c-440e-af8e-e8e762387a4b" style="width: 300px; height: auto;">
    </td>  
  </tr>
</table>



### 식물 히스토리
<img width="814" alt="history" src="https://github.com/user-attachments/assets/89f98653-cebd-474c-a4f6-3aaf8126aa3b">



### 식물 페이지
<table>
  <tr>
    <th align="center">식물 프로필 & 출석 체크</th>
    <th align="center">식물 도감</th>
    <th align="center">도전 과제</th>
  </tr>
  <tr>
    <th align="center">
      <img src="https://github.com/user-attachments/assets/df1759c8-b79a-4568-b50b-2f0bdec409f2" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/fd42d2f7-19f4-4c33-a3ca-8701a1821c84" style="width: 300px; height: auto;">
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/e9a688c8-1989-42f3-acaf-45385a14ac4c" style="width: 300px; height: auto;">
    </td>    
  </tr>
</table>

### AI
- 질병 감지

![ai_resnet](https://github.com/user-attachments/assets/6a02e8eb-8d46-40f3-b95e-d0494fbbc1aa)

- 챗봇
  
![ai_lang](https://github.com/user-attachments/assets/0b58db27-cd01-414d-8608-0231017d561b)







