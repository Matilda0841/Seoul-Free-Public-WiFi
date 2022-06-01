package com.my.dao;

import com.my.config.MariaDbConn;
import com.my.model.UserRecordDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserRecordDaoimpl implements UserRecordDao{

    final static MariaDbConn dbConn = new MariaDbConn();

    @Override
    public void insertUserRecord(UserRecordDto userRecordDto) throws SQLException, ClassNotFoundException {
        Connection conn = dbConn.getConn();
        String sql = "insert into user_record (lat, lnt) " +
                "values (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userRecordDto.getLat());
        pstmt.setString(2, userRecordDto.getLnt());
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    @Override
    public List<UserRecordDto> selectAll() throws SQLException, ClassNotFoundException {
        List<UserRecordDto> list = new ArrayList<>();
        Connection conn = dbConn.getConn();
        String sql = "select * from user_record order by num desc";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        while (rs.next()) {

            String date = simpleDateFormat.format(rs.getTimestamp("reg_date"));
            UserRecordDto userRecordDto = new UserRecordDto(
                    rs.getInt("num"),
                    rs.getString("lat"),
                    rs.getString("lnt"),
                    LocalDateTime.parse(date, dateTimeFormatter)
            );
            list.add(userRecordDto);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return list;
    }

    @Override
    public void deleteUserRecord(int num) throws SQLException, ClassNotFoundException {
        Connection conn = dbConn.getConn();
        String sql = "delete from user_record where num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, num);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
