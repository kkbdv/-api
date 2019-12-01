package edu.kkbdv.dao;

import edu.kkbdv.pojo.UserLikeVideo;
import edu.kkbdv.pojo.vo.VideosVo;

import java.util.List;

public interface UserLikeVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLikeVideo record);

    UserLikeVideo selectByPrimaryKey(String id);

    List<UserLikeVideo> selectAll();

    int updateByPrimaryKey(UserLikeVideo record);

    void deleteByUserIdAndVideoId(String userId,String videoId);

    UserLikeVideo selectByUserIdAndVideoId(String userId,String videoId);

    List<VideosVo> queryMyCollectionByUserId(String userId);
}