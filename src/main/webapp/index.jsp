<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="include/lib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="include/bootstrap.jsp"%>
    <title>메인페이지</title>
</head>
<body>
    <c:if test="${not empty alert}">
        <script>
            alert(${alert}+"개의 Wifi 정보를 등록했습니다.");
        </script>
    </c:if>

    <%@include file="include/heder.jsp"%>

    <div style="padding: 5px 5px 5px 5px">
        <table class="table table-striped">
            <tr class="table-primary">
                <th>순번</th>
                <th>거리(Km)</th>
                <th>관리 번호</th>
                <th>자치구</th>
                <th>와이파이명</th>
                <th>도로명 주소</th>
                <th>상세 주소</th>
                <th>설치 위치(층)</th>
                <th>설치 유형</th>
                <th>설치 기관</th>
                <th>서비스 구분</th>
                <th>망 종류</th>
                <th>설치년도</th>
                <th>실내외 구분</th>
                <th>Wifi 접속 환경</th>
                <th>X 좌표</th>
                <th>Y 좌표</th>
                <th>작업 일자</th>
            </tr>
<%--            <c:if test="${not empty wifiList}">--%>
            <c:if test="${not empty wifiList}">
                <c:forEach items="${wifiList}" var="list" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${list.distance}</td>
                        <td>${list.control_number}</td>
                        <td>${list.borough}</td>
                        <td>${list.wifi_name}</td>
                        <td>${list.address1}</td>
                        <td>${list.address2}</td>
                        <td>${list.floor}</td>
                        <td>${list.type}</td>
                        <td>${list.install_agency}</td>
                        <td>${list.service_type}</td>
                        <td>${list.network_type}</td>
                        <td>${list.install_year}</td>
                        <td>${list.in_out}</td>
                        <td>${list.connect_environment}</td>
                        <td>${fn:split(list.coordinate, " ")[0]}</td>
                        <td>${fn:split(list.coordinate, " ")[1]}</td>
                        <td>${list.work_date}</td>
                    </tr>
                </c:forEach>
            </c:if>

        </table>
    </div>

</body>
</html>