package com.my.service;

import com.my.model.WifiDto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface WifiService {

    int saveWifi() throws SQLException, ClassNotFoundException, IOException;

    List<WifiDto> selectWifiInfo(String x, String y) throws SQLException, ClassNotFoundException;

    String getWifiApi(int start, int end) throws IOException;
}
