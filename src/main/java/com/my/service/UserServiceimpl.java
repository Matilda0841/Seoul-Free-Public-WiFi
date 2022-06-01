package com.my.service;

import com.my.dao.UserRecordDao;
import com.my.dao.UserRecordDaoimpl;
import com.my.model.UserRecordDto;

import java.sql.SQLException;
import java.util.List;

public class UserServiceimpl implements UserService {

    final static UserRecordDao userRecordDao = new UserRecordDaoimpl();

    @Override
    public void saveUserRecord(UserRecordDto userRecordDto) throws SQLException, ClassNotFoundException {
        userRecordDao.insertUserRecord(userRecordDto);
    }

    @Override
    public List<UserRecordDto> selectAll() throws SQLException, ClassNotFoundException {
        return userRecordDao.selectAll();
    }

    @Override
    public void removeUserRecord(int num) throws SQLException, ClassNotFoundException {
        userRecordDao.deleteUserRecord(num);
    }
}
