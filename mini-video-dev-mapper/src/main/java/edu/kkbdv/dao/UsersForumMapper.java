package edu.kkbdv.dao;

import edu.kkbdv.pojo.UsersForum;
import java.util.List;

public interface UsersForumMapper {
    int deleteByPrimaryKey(String id);

    int insert(UsersForum record);

    UsersForum selectByPrimaryKey(String id);

    List<UsersForum> selectAll();

    int updateByPrimaryKey(UsersForum record);

    void deleteByUserIdAndForumId(String userId,String forumId);

    UsersForum selectByUserIdAndForumId(String forumId,String userId);
}