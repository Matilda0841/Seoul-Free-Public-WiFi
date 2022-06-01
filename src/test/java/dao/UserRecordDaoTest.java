package dao;

import com.my.config.MariaDbConn;
import com.my.model.UserRecordDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserRecordDaoTest {

    MariaDbConn dbConn = new MariaDbConn();

    @DisplayName("저장했다면 리턴값으로 1을 반환")
    @Test
    void 유저_검색_기록_생성() throws SQLException, ClassNotFoundException {
        // given
        UserRecordDto userRecordDto = new UserRecordDto();
        userRecordDto.setLat("126.93953960449836");
        userRecordDto.setLnt("37.48632265580167");
        Connection conn = dbConn.getConn();
        String sql = "insert into user_record (lat, lnt) " +
                "values (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, userRecordDto.getLat());
        pstmt.setString(2, userRecordDto.getLnt());

        // when
        int result = pstmt.executeUpdate();
        pstmt.close();
        conn.close();


        // then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("읽어온 DTO가 일치해야 함")
    @Test
    void 유저_검색_기록_테이블_읽어오기() throws SQLException, ClassNotFoundException {

        //given
        List<UserRecordDto> list = new ArrayList<>();
        Connection conn = dbConn.getConn();
        String sql = "select * from user_record order by num";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // when
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

        // then
        assertThat(list.get(0)).isExactlyInstanceOf(UserRecordDto.class);
    }

    @DisplayName("반환값이 1이여야함")
    @Test
    void 유저_검색_기록_삭제() throws SQLException, ClassNotFoundException {

        // given
        int num = 4;
        Connection conn = dbConn.getConn();
        String sql = "delete from user_record where num = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, num);

        // when
        int result = pstmt.executeUpdate();
        pstmt.close();
        conn.close();

        // then
        assertThat(result).isEqualTo(1);
    }
}