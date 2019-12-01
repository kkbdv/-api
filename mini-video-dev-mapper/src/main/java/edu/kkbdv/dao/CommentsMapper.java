package edu.kkbdv.dao;

import edu.kkbdv.pojo.Comments;
import edu.kkbdv.pojo.vo.CommentsVo;

import java.util.List;

public interface CommentsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Comments record);

    Comments selectByPrimaryKey(String id);

    List<Comments> selectAll();

    int updateByPrimaryKey(Comments record);

    List<CommentsVo> queryComments(String videoId);

    void deleteByVideoId(String videoId);
}