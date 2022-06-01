package dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.my.config.MariaDbConn;
import com.my.model.WifiDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WifiDaoimplTest {

    MariaDbConn dbConn = new MariaDbConn();

    @DisplayName("저장된 리턴값은 1이여야 한다.")
    @Test
    void API데이터_저장() throws IOException, SQLException, ClassNotFoundException {

        // given
        int result = 0;
        MariaDbConn dbConn = new MariaDbConn();
        Connection conn = dbConn.getConn();
        PreparedStatement pstmt = null;
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/" + URLEncoder.encode("4370764a516d616c38377342586e4f", "UTF-8")); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json", "UTF-8")); /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo", "UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("1", "UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.

        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
        urlBuilder.append("/" + URLEncoder.encode("20220501", "UTF-8")); /* 서비스별 추가 요청인자들*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection apiConn = (HttpURLConnection) url.openConnection();
        apiConn.setRequestMethod("GET");
        apiConn.setRequestProperty("Content-type", "application/xml");
        BufferedReader rd;

        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
        if (apiConn.getResponseCode() >= 200 && apiConn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(apiConn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(apiConn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(sb.toString());
        jsonElement = jsonElement.getAsJsonObject().get("TbPublicWifiInfo");
        JsonArray jsonArray = jsonElement.getAsJsonObject().get("row").getAsJsonArray();

        // when
        for (int i = 0; i < jsonArray.size(); i++) {
            String control_number = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_MGR_NO").getAsString();
            String borough = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_WRDOFC").getAsString();
            String wifi_name = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_MAIN_NM").getAsString();
            String address1 = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_ADRES1").getAsString();
            String address2 = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_ADRES2").getAsString();
            String floor = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_INSTL_FLOOR").getAsString();
            String type = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_INSTL_TY").getAsString();
            String install_type = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_INSTL_MBY").getAsString();
            String service_type = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_SVC_SE").getAsString();
            String network_type = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_CMCWR").getAsString();
            int install_year = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_CNSTC_YEAR").getAsInt();
            String in_out = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_INOUT_DOOR").getAsString();
            String connect_environment = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_REMARS3").getAsString();
            String lat = jsonArray.get(i).getAsJsonObject().get("LAT").getAsString();
            String lnt = jsonArray.get(i).getAsJsonObject().get("LNT").getAsString();
            String coordinate = "POINT(" + lat + " " + lnt + ")'";
            Timestamp work_date = Timestamp.valueOf(jsonArray.get(i).getAsJsonObject().get("WORK_DTTM").getAsString());
            String sql = "insert into wifi_info " +
                    "(control_number, borough, wifi_name, address1, address2, floor, type, install_agency, service_type, network_type, install_year, in_out, connect_environment, coordinate, work_date)" +
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ST_GeomFromText(?), ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, control_number);
            pstmt.setString(2, borough);
            pstmt.setString(3, wifi_name);
            pstmt.setString(4, address1);
            pstmt.setString(5, address2);
            pstmt.setString(6, floor);
            pstmt.setString(7, type);
            pstmt.setString(8, install_type);
            pstmt.setString(9, service_type);
            pstmt.setString(10, network_type);
            pstmt.setInt(11, install_year);
            pstmt.setString(12, in_out);
            pstmt.setString(13, connect_environment);
            pstmt.setString(14, coordinate);
            pstmt.setTimestamp(15, work_date);
            result += pstmt.executeUpdate();

            // then
            assertThat(result).isEqualTo(1);
        }
    }

    @DisplayName("가져온 값은 WifiDto와 같아야함")
    @Test
    void 저장된_와이파이정보_가져오기() throws SQLException, ClassNotFoundException, IOException {

        // given
        String x = "126.93953960449836";
        String y = "37.48632265580167";
        Connection conn = dbConn.getConn();
        List<WifiDto> wifiList = new ArrayList<>();
        String sql = "select CONCAT(distance / 1000) as distance, control_number, borough, wifi_name, address1, address2, floor, type, install_agency, service_type, network_type, install_year, in_out, connect_environment ,CONCAT(X(coordinate), SPACE(1),Y(coordinate)) as coordinate, work_date " +
                "from (select *, ST_Distance_Sphere(Point(?, ?), coordinate) as distance from wifi_info) as wifi_info " +
                "where distance < 1000 " +
                "order by distance " +
                "limit 20";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, x);
        pstmt.setString(2, y);
        ResultSet rs = pstmt.executeQuery();

        // when
        while (rs.next()) {
            WifiDto wifiDto = new WifiDto(
                    rs.getString("control_number"),
                    rs.getString("borough"),
                    rs.getString("wifi_name"),
                    rs.getString("address1"),
                    rs.getString("address2"),
                    rs.getString("floor"),
                    rs.getString("type"),
                    rs.getString("install_agency"),
                    rs.getString("service_type"),
                    rs.getString("network_type"),
                    rs.getString("install_year"),
                    rs.getString("in_out"),
                    rs.getString("connect_environment"),
                    rs.getString("coordinate"),
                    rs.getTimestamp("work_date"),
                    rs.getString("distance"));
            wifiList.add(wifiDto);
        }
        rs.close();
        pstmt.close();
        conn.close();

        // then
        assertThat(wifiList.get(0)).isExactlyInstanceOf(WifiDto.class);
    }

    @DisplayName("잘못된 데이터가 있을 경우 리턴값은 1이여야 한다.")
    @Test
    void 잘못된_위경도_데이터_삭제 () throws SQLException, ClassNotFoundException {

        // given
        Connection conn = dbConn.getConn();
        String sql = "delete from wifi_info " +
                "where ST_Y(coordinate) > 90 or ST_Y(coordinate) < -90 or ST_X(coordinate) > 180 or ST_X(coordinate) < - 180";
        PreparedStatement pstmt = conn.prepareStatement(sql);

        // when
        int result = pstmt.executeUpdate();
        pstmt.close();
        conn.close();

        // then
        assertThat(result).isEqualTo(0);
    }

}