package edu.kkbdv.service;

import edu.kkbdv.pojo.Users;

public interface UserService {
    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 保存用户对象(用户注册)
     * @param users
     */
    public void saveUser(Users users);

    /**
     * 通过username 查找对象
     * @param username
     * @return
     */
    public Users selUsersByUsername(String username);

    /**
     * 用户修改信息
     * @param user
     */
    public void updateUserInfo(Users user);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public Users queryUserInfo(String userId);
}
