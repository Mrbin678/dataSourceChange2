package com.sccl.data_source_change.slave.mapper;


import com.sccl.data_source_change.slave.domain.User;

import java.util.List;

/**
 * Create by wangbin
 * 2019-11-21-18:20
 */
public interface UserMapper {
    int addUser(User user);
    List<User> getAllUsers();
}
