package com.gwokgwok.p2p.sso.service;

import com.gwokgwok.p2p.entry.User;

public interface UserService {
    String addUser(User user) throws Exception;
    User findUserByUserNameAndPassword(String username, String password) throws Exception;
    User findUserByUserName(String username) throws Exception;
}
