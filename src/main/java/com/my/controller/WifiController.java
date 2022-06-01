package com.my.controller;

import com.my.model.UserRecordDto;
import com.my.model.WifiDto;
import com.my.service.UserService;
import com.my.service.UserServiceimpl;
import com.my.service.WifiService;
import com.my.service.WifiServiceimpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/zero/*")
public class WifiController extends HttpServlet {

    private static final WifiService wifiService = new WifiServiceimpl();
    private static final UserService userService = new UserServiceimpl();
    final String servletMapping = "/zero/";
    RequestDispatcher dispatcher = null;
    String path = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String requestMapping = uri.split(servletMapping)[1];
        path = null;

        // 인덱스 페이지 이동
        if (requestMapping.equals("index") || requestMapping.equals("")) {
            path = "/index.jsp";
            
            // 근처 와이파이 정보 가져오기
        } else if (requestMapping.equals("wifis")) {
            path = "/index.jsp";
            String x = request.getParameter("x");
            String y = request.getParameter("y");
            try {
                userService.saveUserRecord(new UserRecordDto(x, y));
                List<WifiDto> list = wifiService.selectWifiInfo(x, y);
                request.setAttribute("wifiList", list);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            // 유저 검색 기록 페이지 이동
        } else if (requestMapping.equals("userRecord")) {
            path = "/userRecord.jsp";
            try {
                List<UserRecordDto> list = userService.selectAll();
                request.setAttribute("userRecordList", list);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            // 와이파이 정보 Api db 저장
        } else if (requestMapping.equals("wifi")) {
            path = "/index.jsp";
            int count = 0;
            try {
                count = wifiService.saveWifi();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            request.setAttribute("alert", count);
            
            // 사용자 검색 기록 삭제
        } else if (requestMapping.equals("deleteUserRecord")) {
            int num = Integer.parseInt(request.getParameter("num"));
            try {
                userService.removeUserRecord(num);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            path = "/zero/userRecord";
        }

        dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }


}
