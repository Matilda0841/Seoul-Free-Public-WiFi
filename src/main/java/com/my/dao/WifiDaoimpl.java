package com.my.dao;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.my.config.MariaDbConn;
import com.my.model.WifiDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiDaoimpl implements WifiDao {

    private final MariaDbConn dbConn = new MariaDbConn();

    @Override
    public void deleteWrongLot() throws SQLException, ClassNotFoundException {
        Connection conn = dbConn.getConn();
        String sql = "delete from wifi_info where ST_Y(coordinate) > 90 or ST_Y(coordinate) < -90 or ST_X(coordinate) > 180 or ST_X(coordinate) < - 180";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }

    @Override
    public List<WifiDto> selectWifiInfo(String x, String y) throws SQLException, ClassNotFoundException {
        Connection conn = dbConn.getConn();
        List<WifiDto> wifiList = new ArrayList<>();
        String sql =
                "select CONCAT(distance / 1000) as distance, control_number, borough, wifi_name, address1, address2, floor, type, install_agency, service_type, network_type, install_year, in_out, connect_environment ,CONCAT(X(coordinate), SPACE(1),Y(coordinate)) as coordinate, work_date " +
                "from (select *, ST_Distance_Sphere(Point(?, ?), coordinate) as distance from wifi_info) as wifi_info " +
                "where distance < 3000 " +
                "order by distance " +
                "limit 20";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, x);
        pstmt.setString(2, y);
        ResultSet rs = pstmt.executeQuery();
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
                    rs.getString("distance")
            );
            wifiList.add(wifiDto);
        }
        rs.close();
        pstmt.close();
        conn.close();
        return wifiList;
    }

    @Override
    public int insertWifiInfo(JsonElement jsonElement) throws SQLException, ClassNotFoundException {
        int count = 0;
        Connection conn = dbConn.getConn();
        PreparedStatement pstmt = null;
        jsonElement = jsonElement.getAsJsonObject().get("TbPublicWifiInfo");
        JsonArray jsonArray = jsonElement.getAsJsonObject().get("row").getAsJsonArray();
        StringBuilder coordinate = new StringBuilder();

        for (int i = 0; i < jsonArray.size(); i++) {
            coordinate.setLength(0);
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
            String install_year = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_CNSTC_YEAR").getAsString();
            String in_out = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_INOUT_DOOR").getAsString();
            String connect_environment = jsonArray.get(i).getAsJsonObject().get("X_SWIFI_REMARS3").getAsString();
            String lat = jsonArray.get(i).getAsJsonObject().get("LAT").getAsString();
            String lnt = jsonArray.get(i).getAsJsonObject().get("LNT").getAsString();
            coordinate.append("POINT(").append(lat).append(" ").append(lnt).append(")");
            Timestamp work_date = Timestamp.valueOf(jsonArray.get(i).getAsJsonObject().get("WORK_DTTM").getAsString());

            String sql = "insert into wifi_info " +
                    "(control_number, borough, wifi_name, address1, address2, floor, type, install_agency, service_type, network_type, install_year, in_out, connect_environment, coordinate, work_date)"+
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ST_GeomFromText(?), ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,control_number);
            pstmt.setString(2, borough);
            pstmt.setString(3,wifi_name);
            pstmt.setString(4, address1);
            pstmt.setString(5,address2);
            pstmt.setString(6,floor);
            pstmt.setString(7, type);
            pstmt.setString(8, install_type);
            pstmt.setString(9, service_type);
            pstmt.setString(10, network_type);
            pstmt.setString(11, install_year);
            pstmt.setString(12, in_out);
            pstmt.setString(13, connect_environment);
            pstmt.setString(14, coordinate.toString());
            pstmt.setTimestamp(15, work_date);
            count += pstmt.executeUpdate();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        conn.close();
        return count;
    }

    @Override
    public void deleteAllWifiDate() throws SQLException, ClassNotFoundException {
        Connection conn = dbConn.getConn();
        String sql = "delete from wifi_info;";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
        pstmt.close();
        conn.close();
    }
}
