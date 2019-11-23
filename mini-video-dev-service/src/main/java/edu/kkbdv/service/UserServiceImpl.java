package edu.kkbdv.service;

import edu.kkbdv.dao.UsersMapper;
import edu.kkbdv.pojo.Users;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    //todo:了解事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users users = usersMapper.selectByUserName(username);
        return users==null?false:true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users users) {
        String userId = sid.nextShort();
        users.setId(userId);
        usersMapper.insert(users);
    }

    public Users selUsersByUsername(String username){
       return usersMapper.selectByUserName(username);
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(Users user){
        usersMapper.updateByPrimaryKeySelective(user);
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }
}
