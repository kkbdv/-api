package edu.kkbdv.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.kkbdv.common.util.PagedResult;
import edu.kkbdv.common.util.TimeAgoUtils;
import edu.kkbdv.dao.*;
import edu.kkbdv.pojo.Comments;
import edu.kkbdv.pojo.SearchRecords;
import edu.kkbdv.pojo.UserLikeVideo;
import edu.kkbdv.pojo.Videos;
import edu.kkbdv.pojo.vo.CommentsVo;
import edu.kkbdv.pojo.vo.VideosVo;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VideoSerivceImpl implements VideoSerivce{

    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private Sid  sid;
    @Autowired
    private SearchRecordsMapper searchRecordsMapper;
    @Autowired
    private UserLikeVideoMapper userLikeVideoMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public void saveVideo(Videos videos) {
        videos.setId(sid.nextShort());
        videos.setLikeCounts((long)0);
        videosMapper.insert(videos);
    }

    @Override
    public PagedResult getAllVideos(Integer page, Integer pageSize,Videos video,Integer isSaveRecord) {
        //the search thing from user
        String desc = video.getVideoDesc();
        if(isSaveRecord != null && isSaveRecord>0 ){
            SearchRecords searchRecords = new SearchRecords();
            searchRecords.setId(sid.nextShort());
            searchRecords.setContent(desc);
            searchRecordsMapper.insert(searchRecords);
        }

        PageHelper.startPage(page,pageSize);
        List<VideosVo> videosVos = videosMapper.queryAllVideos(desc,null);

        PageInfo<VideosVo> pageInfo = new PageInfo(videosVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(videosVos);

        return pagedResult;
    }

    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.selectAllContents();
    }


    @Transactional
    @Override
    public void userAddVideoLike(String userId, String videoId,String createUserId) {
        //insert a row into userLikeVideos
        UserLikeVideo userLikeVideo = new UserLikeVideo();
        userLikeVideo.setId(sid.nextShort());
        userLikeVideo.setUserId(userId);
        userLikeVideo.setVideoId(videoId);
        userLikeVideoMapper.insert(userLikeVideo);
        // add the counts of videos
        videosMapper.addVideoLike(videoId);
        // add the counts of users
        usersMapper.addLikeCounts(createUserId);

    }
    @Transactional
    @Override
    public void userReduceVideoLike(String userId, String videoId,String createUserId) {
        //delete a row into userLikeVideos
        userLikeVideoMapper.deleteByUserIdAndVideoId(userId,videoId);
        // reduce the counts of videos
       videosMapper.reduceVideoLike(videoId);
        // reduce the counts of users
        usersMapper.reduceLikeCounts(createUserId);

    }

    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        UserLikeVideo userLikeVideo = userLikeVideoMapper.selectByUserIdAndVideoId(userId, videoId);
        return userLikeVideo==null?false:true;

    }

    @Override
    public PagedResult getMyVideos(Integer page, String userId, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VideosVo> videosVos = videosMapper.queryAllVideos(null, userId);

        PageInfo<VideosVo> pageInfo = new PageInfo(videosVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(videosVos);
        return pagedResult;
    }

    @Override
    public PagedResult queryMyCollection(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<VideosVo> videosVos = userLikeVideoMapper.queryMyCollectionByUserId(userId);
        PageInfo<VideosVo> pageInfo = new PageInfo(videosVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRecords(pageInfo.getTotal());
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRows(videosVos);
        return pagedResult;
    }

    @Override
    public void saveComments(Comments comments) {
        comments.setId(sid.nextShort());
        comments.setCreateTime(new Date());
        commentsMapper.insert(comments);
    }

    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<CommentsVo> commentsVos = commentsMapper.queryComments(videoId);
        for(CommentsVo c: commentsVos){
            c.setTimeAgoStr(TimeAgoUtils.format(c.getCreateTime()));
        }
        PageInfo<CommentsVo> pageInfo = new PageInfo(commentsVos);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(commentsVos);
        pagedResult.setPage(page);
        pagedResult.setTotal(pageInfo.getPages());
        pagedResult.setRecords(pageInfo.getTotal());
        return pagedResult;
    }

    @Transactional
    @Override
    public void deleteVideo(String videoId,VideosVo videosVo) {
        //删除视频中的评论
        commentsMapper.deleteByVideoId(videoId);

        if(videosMapper.deleteByPrimaryKey(videoId)>0){
            File videoFile = new File(videosVo.getVideoPath());
            File coverFile = new File(videosVo.getCoverPath());
            deleteMethod(videoFile);
            deleteMethod(coverFile);
        }


    }

    private void deleteMethod(File file){
        if(file.exists()){
            file.delete();
            System.out.println(file.getAbsolutePath()+"  文件已被删除");
        }
    }
}
