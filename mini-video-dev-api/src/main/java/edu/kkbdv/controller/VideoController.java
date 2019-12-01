package edu.kkbdv.controller;

import edu.kkbdv.common.R;
import edu.kkbdv.common.util.FetchVideoCover;
import edu.kkbdv.common.util.PagedResult;
import edu.kkbdv.enums.VideoStatusEnum;
import edu.kkbdv.pojo.Comments;
import edu.kkbdv.pojo.Users;
import edu.kkbdv.pojo.Videos;
import edu.kkbdv.pojo.vo.VideosVo;
import edu.kkbdv.service.VideoSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/video")
public class VideoController extends BasicController{

    @Autowired
    private VideoSerivce videoSerivce;

    @ApiOperation(value = "上传视频" )
    @PostMapping("/upload")
    public R uploadVideo(String userId, double videoSeconds,
                         int videoHeight, int videoWidth, String desc, MultipartFile file)throws IOException,InterruptedException {

        //校验用户id是否为空
        if(StringUtils.isBlank(userId)){
            return R.error("用户id不能为空");
        }
        //define the file space
        String fileSpace = "G:/kkbdv_dev";
        //the file space to database
        String uploadPathDB ="/"+userId+"/video";
        String coverPathDB ="/"+userId+"/video";
        //the final path for using
        String finalVideoPath = "";

        if(file!=null){
            String fileName = file.getOriginalFilename();
            String fileNamePrefix = "";
            String[] arrFileName = fileName.split("\\.");
            for(int i =0;i<arrFileName.length-1;i++){
                fileNamePrefix+=arrFileName[i];
            }
            if(StringUtils.isNoneBlank(fileName)){
                finalVideoPath = fileSpace+uploadPathDB+"/"+fileName;
                uploadPathDB+=("/"+fileName);
                coverPathDB=coverPathDB+"/"+fileNamePrefix+".jpg";
                File outFile =  new File(finalVideoPath);
                if(outFile.getParentFile()!=null||!outFile.getParentFile().isDirectory()){//后面这个表示同名的文件
                    outFile.getParentFile().mkdirs();
                }
                //存到硬盘
                file.transferTo(outFile);
                //generate the cover of video
                FetchVideoCover videoInfo = new FetchVideoCover(FFMEG_EXE);
                videoInfo.getCover(finalVideoPath,fileSpace+coverPathDB);

                //save video in database
                Videos video = new Videos();
                video.setCoverPath(coverPathDB);
                video.setVideoPath(uploadPathDB);
                video.setUserId(userId);
                video.setVideoSeconds((float)videoSeconds);
                video.setVideoHeight(videoHeight);
                video.setVideoWidth(videoWidth);
                video.setVideoDesc(desc);
                video.setStatus(VideoStatusEnum.SUCCESS.value);
                video.setCreateTime(new Date());
                videoSerivce.saveVideo(video);


            }else{
                return R.error("上传出错");
            }
        }

        return R.ok("success");

    }
    @ApiOperation(value = "分页查找视频和记录搜索关键字" )
    @PostMapping("/showAll")
    public R showAll(@RequestParam(defaultValue ="1") Integer page, @RequestBody Videos video, Integer isSaveRecord, Integer pageSize){
        String userId = video.getUserId();
        if(StringUtils.isNotBlank(userId)){
            PagedResult myVideos = videoSerivce.getMyVideos(page, userId, pageSize);
            return R.ok(myVideos);
        }else {

            PagedResult allVideos = videoSerivce.getAllVideos(page, PAGE_SIZE,video,isSaveRecord);
            return R.ok(allVideos);
        }
    }

    @ApiOperation(value = "获得热搜词" )
    @PostMapping("/hot")
    public R hot(){
        return R.ok(videoSerivce.getHotWords());
    }

    @PostMapping("/userLike")
    public R userLike(String userId,String videoId,String videoCreaterId){
        videoSerivce.userAddVideoLike(userId,videoId,videoCreaterId);
        return new R();
    }

    @PostMapping("/userUnLike")
    public R userUnLike(String userId,String videoId,String videoCreaterId){
        videoSerivce.userReduceVideoLike(userId,videoId,videoCreaterId);
        return new R();
    }

    @PostMapping("/showMyLike")
    public R showMyLike(String userId,@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "6") Integer pageSize){
        if(StringUtils.isBlank(userId)){
            return R.error("请登陆");
        }else{
            PagedResult pagedResult = videoSerivce.queryMyCollection(userId, page, pageSize);
            return R.ok(pagedResult);
        }
    }

    @PostMapping("/showMyFollow")
    public R showMyFollow(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "6") Integer pageSize,String userId){
        if(StringUtils.isBlank(userId)){
            return R.error("请登陆");
        }else {
            PagedResult pagedResult = videoSerivce.queryMyCollection(userId, page, pageSize);
            return R.ok(pagedResult);
        }
    }
    @PostMapping("/saveComment")
    public R saveComment(@RequestBody Comments comments,String fatherCommentId,String toUserId){
        comments.setFatherCommentId(fatherCommentId);
        comments.setToUserId(toUserId);
        videoSerivce.saveComments(comments);
        return new R();
    }

    @PostMapping("/getVideoComments")
    public R getVideoComments(String videoId,Integer page,Integer pageSize){
        PagedResult allComments = videoSerivce.getAllComments(videoId, page, pageSize);
        return R.ok(allComments);
    }

    @PostMapping("/delete")
    public R deleteVideo(String videoId, @RequestBody VideosVo videosVo){
        videosVo.setVideoPath(RESOURCE_PATH+videosVo.getVideoPath());
        videosVo.setCoverPath(RESOURCE_PATH+videosVo.getCoverPath());
        videoSerivce.deleteVideo(videoId,videosVo);
        return new R();
    }


}
