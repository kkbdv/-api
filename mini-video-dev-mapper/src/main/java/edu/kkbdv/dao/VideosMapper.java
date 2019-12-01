package edu.kkbdv.dao;

import edu.kkbdv.pojo.Videos;
import edu.kkbdv.pojo.vo.VideosVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapper {
    int deleteByPrimaryKey(String id);

    int insert(Videos record);

    Videos selectByPrimaryKey(String id);

    List<Videos> selectAll();

    int updateByPrimaryKey(Videos record);

    List<VideosVo> queryAllVideos(@Param("desc") String desc,@Param("userId") String userId);

    void addVideoLike(String videoId);

    void reduceVideoLike(String videoId);


}