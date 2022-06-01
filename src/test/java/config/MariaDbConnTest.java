package config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class MariaDbConnTest {

    MariaDbConn dbConn = new MariaDbConn();

    @DisplayName("디비 커넥션 연결 테스트")
    @Test
    void Dbconn() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(dbConn.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("드라이버 찾을 수 없음");
        }
        try {
            conn = dbConn.getConn();
            pstmt = conn.prepareStatement("show tables");
            pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("db연결 실패");
        } finally {
            pstmt.close();
            conn.close();
        }

    }


}