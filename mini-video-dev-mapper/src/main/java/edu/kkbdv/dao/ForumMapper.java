package edu.kkbdv.dao;

import edu.kkbdv.pojo.Forum;
import edu.kkbdv.pojo.vo.ForumVo;

import java.util.List;

public interface ForumMapper {
    int deleteByPrimaryKey(String id);

    int insert(Forum record);

    Forum selectByPrimaryKey(String id);

    List<Forum> selectAll();

    int updateByPrimaryKey(Forum record);

    List<ForumVo> selectAllForum();

    List<ForumVo> selectMyForum(String userId);

    ForumVo selecForumDetail(String forumId);

    void addJoinCount(String forumId);

    void redJoinCount(String forumId);

    int selectJoinCount(String forumId);
}