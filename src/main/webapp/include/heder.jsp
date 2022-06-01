<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<script>

    function getWifis () {
        let x = document.getElementById("x").value;
        let y = document.getElementById("y").value;
        if (x == null || x === "") {
            alert("LAT값을 입력해주세요.");
            document.getElementById("x").focus();
            return;
        } else if (y == null || y === "") {
            alert("LNT값을 입력해주세요.");
            document.getElementById("y").focus();
            return;
        }
        console.log(x);
        console.log(y);
        window.location.href = "/zero/wifis?x="+x+"&y="+y;
    }

    function myLocation () {
        document.getElementById("x").value = "127.09397960000003";
        document.getElementById("y").value = "37.51107929999998";
    }

    function getApi () {

        let loading = document.getElementById("loading");
        if (loading.style.display === 'inline-block') {
            alert("이미 동작 중입니다.");
            return;
        }
        loading.style.display = 'inline-block';
        window.location.href = "/zero/wifi";
    }

</script>

<h2 style="padding: 15px 15px 15px 15px">와이파이 정보 구하기</h2>

<ul class="nav nav-tabs">
    <li class="nav-item">
        <a class="nav-link active" href="/zero/index">홈</a>
    </li>
    <li class="nav-item">
        <a class="nav-link active" href="/zero/userRecord">위치 히스토리 목록</a>
    </li>
    <li class="nav-item" id="modal">
        <a class="nav-link active" href="javascript:void(0);" onclick="getApi()">Open API 와이파이 정보 가져오기 <span class="spinner-border spinner-border-sm" style="display: none" id="loading"></span> </a>
    </li>

</ul>

<div style="padding-left: 5px" class="form-inline">
    <label >LAT :&nbsp;</label> <input type="text" class="form-control" id="x">&nbsp;
    <label>LNT :&nbsp;</label> <input type="text" class="form-control" id="y">&nbsp;
    <button class="btn btn-outline-dark" type="button" onclick="myLocation()">내 위치 가져오기</button>&nbsp;
    <button class="btn btn-outline-dark" type="button" onclick="getWifis()">근처 wifi 정보 보기</button>
</div>