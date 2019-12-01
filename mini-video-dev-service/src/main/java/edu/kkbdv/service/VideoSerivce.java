package edu.kkbdv.service;

import edu.kkbdv.common.util.PagedResult;
import edu.kkbdv.dao.VideosMapper;
import edu.kkbdv.pojo.Comments;
import edu.kkbdv.pojo.Videos;
import edu.kkbdv.pojo.vo.VideosVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VideoSerivce {

    /**
     * 保存视频
     * @param videos
     */
    public void saveVideo(Videos videos);

    /**
     * 分页查询列表
     * @return
     */
    public PagedResult getAllVideos(Integer page,Integer pageSize,Videos video,Integer isSaveRecord);

    /**
     * 获取热搜词
     * @return
     */
    public List<String> getHotWords();


    /**
     * 点赞的3个业务
     * @param userId
     * @param videoId
     */
    public void userAddVideoLike(String userId,String videoId,String createUserId);

    /**
     * 取消点赞的三个业务
     * @param userId
     * @param videoId
     */
    public void userReduceVideoLike(String userId,String videoId,String createUserId);

    /**
     * 查看用户是否喜欢这个视频
     * @return
     */
    public boolean isUserLikeVideo(String userId,String videoId);

    /**
     * 通过用户id获取用户发布的视频
     * @param userId
     * @param pageSize
     * @return
     */
    public PagedResult getMyVideos(Integer page,String userId,Integer pageSize);

    /**
     * 查询我的收藏 / 查询我的关注
     * @return
     */
    public PagedResult queryMyCollection(String userId,Integer page,Integer pageSize);

    /**
     * 保存评论
     */
    public void saveComments(Comments comments);

    /**
     *  获取全部评论
     * @param videoId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedResult getAllComments(String videoId,Integer page,Integer pageSize);

    /**
     * 用主键删除id
     * @param videoId
     */
    public void deleteVideo(String videoId, VideosVo videosVo);

}
