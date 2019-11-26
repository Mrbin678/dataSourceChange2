package com.sccl.data_source_change.slave.service;


import com.sccl.data_source_change.slave.domain.User;

import java.util.List;

/**
 * Create by wangbin
 * 2019-11-21-18:18
 */
public interface UserService {
    int addUser(User user);
    List<User> getAllUsers();
}
