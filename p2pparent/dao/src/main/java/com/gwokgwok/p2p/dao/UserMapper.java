package com.gwokgwok.p2p.dao;

import com.gwokgwok.p2p.entry.User;
import org.apache.ibatis.annotations.Param;



public interface UserMapper {
	/**
	 * add User
	 * @param user
	 */
	int insert(User user);
	/**
	 * select User by using username and password
	 * @param username
	 * @param password
	 * @return
	 */
	User selectUserByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
	/**
	 * update user infomation by using Id
	 * @param user
	 * @return
	 */
	int updateUserById( User user);

	User selectByUsername(String username);


	/**
	 * 分页获取用户的认证信息
	 * @return
	 */
//	List<UserInfoPojo> getUserautStatus();
//
//	UserDetailVo getUserDetail(Integer id);
//	User findUserByUserName(String username);
}
