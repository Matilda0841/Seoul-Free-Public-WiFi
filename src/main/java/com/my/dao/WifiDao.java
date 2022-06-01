package com.my.dao;

import com.google.gson.JsonElement;
import com.my.model.WifiDto;

import java.sql.SQLException;
import java.util.List;

public interface WifiDao {

    int insertWifiInfo(JsonElement jsonElement) throws SQLException, ClassNotFoundException;

    void deleteAllWifiDate() throws SQLException, ClassNotFoundException;

    void deleteWrongLot() throws SQLException, ClassNotFoundException;

    List<WifiDto> selectWifiInfo(String x, String y) throws SQLException, ClassNotFoundException;
}
