<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="include/lib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <script>

        function deleteUserRecord(num) {
            window.location.href = "/zero/deleteUserRecord?num="+num;
        }

    </script>
    <%@include file="include/bootstrap.jsp"%>
    <title>사용자 검색 기록</title>
</head>
<body>

    <%@include file="include/heder.jsp"%>

    <div style="padding: 5px 5px 5px 5px">
        <table class="table table-striped">
            <tr class="table-primary">
                <th>순번</th>
                <th>X 좌표</th>
                <th>Y 좌표</th>
                <th>조회 일자</th>
                <th>비고</th>
            </tr>
            <c:if test="${not empty userRecordList}">
                <c:forEach items="${userRecordList}" var="list" varStatus="status">
                    <tr>
                        <td>${status.count}</td>
                        <td>${list.lat}</td>
                        <td>${list.lnt}</td>
                        <td>${list.reg_date}</td>
                        <td><button class="btn btn-outline-dark" type="button" onclick="deleteUserRecord(${list.num})">삭제</button></td>
                    </tr>
                </c:forEach>
            </c:if>

        </table>
    </div>

</body>
</html>