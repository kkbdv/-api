package edu.kkbdv.service;

import edu.kkbdv.dao.UsersFansMapper;
import edu.kkbdv.dao.UsersMapper;
import edu.kkbdv.dao.UsersReportMapper;
import edu.kkbdv.pojo.Users;
import edu.kkbdv.pojo.UsersFans;
import edu.kkbdv.pojo.UsersReport;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
   private UsersMapper usersMapper;
    @Autowired
    private UsersFansMapper usersFansMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private UsersReportMapper usersReportMapper;
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

    @Transactional
    @Override
    public void addUserFans(String userId, String fansId) {
        // insert a row in user_fans
        UsersFans usersFans = new UsersFans();
        usersFans.setId(sid.nextShort());
        usersFans.setUserId(userId);
        usersFans.setFanId(fansId);
        usersFansMapper.insert(usersFans);
        //add the counts of user table 's fans_counts
        usersMapper.addFansCounts(userId);
        //add the counts of fans table's follow_counts
        usersMapper.addFollowCounts(fansId);
    }

    @Transactional
    @Override
    public void reduceUserFans(String userId, String fansId) {
        //delete
        usersFansMapper.deleteByUserIdAndFansId(userId,fansId);
        //reduce
        usersMapper.reduceFansCounts(userId);
        //reduce
        usersMapper.reduceFollowCounts(fansId);
    }

    @Override
    public boolean isExitThisFans(String userId, String fansId) {

        return usersFansMapper.selectByUserIdAndFansId(userId,fansId)==null?false:true;
    }

    @Override
    public void savReport(UsersReport usersReport) {
        usersReport.setCreateDate(new Date());
        usersReport.setId(sid.nextShort());
        usersReportMapper.insert(usersReport);
    }
}
