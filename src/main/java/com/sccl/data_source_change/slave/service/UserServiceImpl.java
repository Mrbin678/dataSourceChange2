package com.sccl.data_source_change.slave.service;


import com.sccl.data_source_change.aspectj.annotation.DataSource;
import com.sccl.data_source_change.enumConst.DataSourceEnum;
import com.sccl.data_source_change.slave.domain.User;
import com.sccl.data_source_change.slave.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Create by wangbin
 * 2019-11-21-18:19
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @DataSource(value = DataSourceEnum.SLAVE)
    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
    @DataSource(value = DataSourceEnum.SLAVE)
    @Override
    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
