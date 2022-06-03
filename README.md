# Seoul-Free-Public-WiFi 🛰 

- 경도와 위도를 이용한 주변 서울시 공공 와이파이 찾기

## 프로젝트 설명 🕵🏻‍♂️

- 서울시 내의 특정 경도, 위도를 입력하면 입력한 경도 위도를 중심으로 3km 이내의 공공 와이파이의 정보를 가져오도록 만들었습니다.
- 기능 
  - 서울시 공공와이파이 데이터를 호출해 DB에 새롭게 저장
  - 내 위치 가져오기 (클릭하면 제가 요즘 가고 싶은 롯데월드의 경도, 위도 값을 가져오도록 했습니다.)
  - 사용자 검색 기록 자동 저장, 삭제


## 회고

### 프로젝트를 하면서 가장 중점적으로 고민했던 부분

1. 스프링을 사용하지 않고 Servlet 이용해서 MVC 패턴으로 프로젝트를 만드려고 했고 **Servlet으로 어떻게 컨트롤러를 구현**해야 하는지
2. Api 호출해서 DB 저장한 데이터들을 가지고 입력 받은 좌표와 비교해 가까운 공공 와이파이를 정렬해 select 하는 것
3. 어떻게 하면 좋은 단위 테스트를 작성할 수 있을까?, 내가 현재 할 수 있는 만큼 TDD 해보자


### Servlet을 Controller로 만들기

구글링을 해보면 대부분 자바 프로젝트에서 servlet을 controller로 사용한다. 나는 해본적이 없기 때문에 구글링으로 해결을 했다.
자 그럼 어떻게 해결했냐면 !

- Servlet을 web.xml에 매핑해주는 방법

<img src="https://velog.velcdn.com/images/malslapq/post/53c2a56b-5deb-4f5e-8f10-d26c4a489067/image.png" width="50%" alt="my">

    <servlet>
        <servlet-name>내가 사용하고 싶은 임의의 servlet 이름</servlet-name>
        <servlet-class>controller로 사용할 class 명</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>위에서 내가 임의로 정한 servlet의 이름</servlet-name>
        <url-pattern>해당 controller로 받고 싶은 url</url-pattern>
    </servlet-mapping>

- @webServlet 어노테이션을 사용하는 방법 

<img src="https://velog.velcdn.com/images/malslapq/post/6087edb3-e9b4-4116-a92a-8f1c488dc387/image.png" width="50%">

`@WebServlet("/Url")` 어노테이션을 contoller로 사용할 class에 사용하고 내가 처리해줄 url을 작성해주면 된다.
web.xml에 매핑하는 것 보다 훨씬 간단하다! 하지만! web.xml에 `metadata-complete="false"`를 추가해 줘야 한다고 하는데,
나는 그런거 추가 안했는데 됐다...?~~(아 그건 잘 모르겠고~)~~ false로 설정하는 이유는 web.xml만 스캔하지 않고 메타데이터 설정이 다른곳에도 있으니 찾아보세요~ 하고 다른곳들도 스캔하도록 하는 부분!
즉 어노테이션도 스캔하게 된다.!

    그렇다면 web.xml, 어노테이션 어떤게 더 나에게 좋을까? 🤷🏻‍♀️
    당연하게도 어노테이션을 이용하는 방법이다. 왜냐면... 

1. 작성하기가 훨씬 빠르고 편하다. (Controller로 사용할 class를 만들고 web.xml과 class 파일을 들락 날락 거리면서 확인을 잘 작성했는지 확인할 시간에 어노테이션 달고 url패턴만 작성하면 끝이다. 얼마나 간단한가...)
2. 남들이 보기에도 가독성이 훨씬 좋을 것이다. (.xml 파일은 개인적인 생각으로 길어지면 길어질 수록 정말 한눈에 파악할 수가 없다고 생각한다.)
3. 협업시 web.xml 수정해 가져올 수 있는 에러를 방지할 수 있다. (지금은 혼자서 프로젝트를 만들었지만 협업을 했다고 생각한다면 한사람이 수정하고 또 공유하고 해야하는 번거로움도 있고 공유를 해놓고 사용하는 경우 동시 수정할 때의 문제라던가, 맞지 않을 경우 등등)

### 경도와 위도를 가지고 가까운 데이터 Select

<img src="https://velog.velcdn.com/images/malslapq/post/2ea56d01-1e19-4de7-b1c3-45f753f62909/image.png" width="50%">

먼저 DB 관계형 데이터베이스, RDBMS는 mariadb를 사용했고 경도와 위도 값을 varchar로 저장해야 하나.. 고민하다 찾아보니 좌표값을 따로 저장할 수 있는 
Point 유형이 있었으나 처음 사용하는지라 어떻게 쿼리문을 날려야 하는지 수십번 시도해서 결국 해냈다.
처음에는 바로 좌표값을 넣어주면 되나 했는데 절대 아니였고, `ST_GeomFromText()`를 이용해 좌표값을 `point(좌표)` 형식으로 넣어주면 point 유형으로 데이터가 입력됐다.


처음에는 바로 넣어주면 되겠지? `pstmt.setString(2,Point(lat lnt));`실행 -> 에러 아니 외않되? 
결국 api 호출해서 받은 좌표 값과 point()를 StringBuilder에 이어붙여서 실행했더니 성공했는데 다음에는 어떻게 데이터들을 위치 비교해서 가져올까 바로 고민...

`ST_DISTANCE_SPHERE()`을 사용하자 ! 첫번째에는 사용자에게 입력 받은 Point 형식의 좌표를, 두번째에는 좌표 컬림을 넘겨주면 거리를 계산해준다! (km단위로)
~~이 부분은 한번에 성공해서 이게 맞나 싶었다.~~ 이후로는 수월하게 거리를 먼저 계산해주고 그 중에서 3키로내 데이터들을 select 해왔다.

### TDD 어떻게 하나요?

#### 근데 이제 Given-When-Then 패턴을 곁들인..

한번도 제대로 단위테스트조차 하지 않고 중요한지 몰랐었는데, 인프런을 통해 공부하면서 스프링 강의의 영한님 말씀 중 
실무에서 테스트를 어떻게 짤지 고민을 엄청하신다는 말을 듣고 충격 1차, 그런데 나는 짜본적이 없다 2차 충격 😨

할 수 있는 만큼 해보자는 마음으로 기능 구현 전에 테스트 케이스 먼저 작성하고, 
내가 생각한대로 테스트 케이스가 성공한 이후 서비스 로직과 기능을 구현했다. 
물론 제대로 해본적이 없어서 좋은 코드는 아니겠지만 꾸준히 해가면 좋아질거라 믿는다.


### 마무리

프로젝트를 완성하기 까지 오랜시간이 걸리진 않았지만 꾀나? 많은 생각들을 했다. 

- 어떻게 하면 좀 더 객체지향적으로 구조를 가져갈 수 있을까?
- 어떻게 하면 여러번 DB Connection하지 않고 최소한으로 할 수 있을까?
- api를 호출해서 가져올 때 한번에 다 가져올 수 없어서 어떻게 총 데이터 개수를 파악하고 api를 가져오고 바로 db에 저장할 건지 다 가져오고 할 건지
- select문을 사용할 때 평균적으로 어떤 쿼리문이 더 빠른지 (select를 해놓은 테이블을 다시 select하거나 처음 select 할 때 거리를 조건문으로 제한하거나 등등..)
- 그 외 패키지 구조, 중복 코드 최소화 등등

소감은 재밌었다. 역시 프로젝트 하는게 무작정 공부하는것 보다 훠어어월씬 재밋다. 모르는 부분들 삽질해가며 배우는것도 재밌고 !
아쉬운점은 여유가 없어서 페이징 처리나, 와이파이 정보를 가져올 때 거리를 select 태그를 이용해 선택할 수 있게하는 등등 보안할 점들이 많다는거? 

중요한 점은 땅을 파는데 망치를 사용하는 개발자는 되기 싫기 때문에 NoSql이나 다른 RDBMS 등 내가 사용하고자 하는 도구들에 대한 합리적인 의사 결정이 있도록 더 많이 공부해야겠다.
프로젝트 동안 내가 고민한 것들에 대한 정답은 찾았는지는 잘 모르겠지만 고민하고 또 고민하다 보면 최선을 찾아가지 않을까 생각한다. 

자 그럼 이제 다음 프로젝트를 준비하러 떠날 시간이다. 🖐
