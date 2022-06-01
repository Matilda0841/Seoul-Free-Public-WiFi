package com.my.dao;

import com.my.model.UserRecordDto;

import java.sql.SQLException;
import java.util.List;

public interface UserRecordDao {

    void insertUserRecord(UserRecordDto userRecordDto) throws SQLException, ClassNotFoundException;

    List<UserRecordDto> selectAll() throws SQLException, ClassNotFoundException;

    void deleteUserRecord(int num) throws SQLException, ClassNotFoundException;
}
