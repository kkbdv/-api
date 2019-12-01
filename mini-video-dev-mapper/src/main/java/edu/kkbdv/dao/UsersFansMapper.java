package edu.kkbdv.dao;

import edu.kkbdv.pojo.UsersFans;
import java.util.List;

public interface UsersFansMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersFans record);

    UsersFans selectByPrimaryKey(String id);

    List<UsersFans> selectAll();

    int updateByPrimaryKey(UsersFans record);

    void deleteByUserIdAndFansId(String userId,String fansId);

    UsersFans selectByUserIdAndFansId(String userId ,String fansId);
}