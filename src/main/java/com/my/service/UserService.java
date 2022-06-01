package com.my.service;

import com.my.model.UserRecordDto;

import java.sql.SQLException;
import java.util.List;

public interface UserService {

    void saveUserRecord(UserRecordDto userRecordDto) throws SQLException, ClassNotFoundException;

    List<UserRecordDto> selectAll() throws SQLException, ClassNotFoundException;

    void removeUserRecord(int num) throws SQLException, ClassNotFoundException;
}
