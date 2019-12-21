package edu.kkbdv.controller;

import edu.kkbdv.common.R;
import edu.kkbdv.common.util.PagedResult;
import edu.kkbdv.pojo.Forum;
import edu.kkbdv.pojo.vo.ForumVo;
import edu.kkbdv.service.ForumService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/forum")
public class ForumController extends BasicController{

    @Autowired
    private ForumService forumService;

    @PostMapping("/add")
    public R add(@RequestBody Forum forum, String userId){
        System.out.println(forum);
        forumService.saveForum(userId,forum);
        return new R();
    }
    @PostMapping("/addCover")
    public R addCover(String userId, @RequestParam("file") MultipartFile[] files) throws IOException {

        String uploadPathDB = "/"+userId+"/forum";

        if(files!=null&&files.length>0){
            String filename =files[0].getOriginalFilename();
            if(StringUtils.isNotBlank(filename)){
                String finalForumCoverPath = RESOURCE_PATH+uploadPathDB
                        +"/"+filename;
                uploadPathDB = uploadPathDB+"/"+filename;
                File outFile = new File(finalForumCoverPath);
                if(outFile.getParentFile()!=null||!outFile.getParentFile().isDirectory()){
                    outFile.getParentFile().mkdirs();
                }
                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                files[0].transferTo(outFile);
            }
        }
        return R.ok(uploadPathDB);
    }

    @PostMapping("/showAll")
    public R getAll(@RequestParam(defaultValue = "1") Integer page)throws ParseException {
        PagedResult all = forumService.getAll(page, PAGE_SIZE);
        return R.ok(all);
    }

    @PostMapping("/getMyForum")
    public R getMyForum(String userId,@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "4") Integer pageSie) throws ParseException{
        PagedResult myForum = forumService.getMyForum(userId, page, pageSie);
        return R.ok(myForum);
    }
    @PostMapping("/delete")
    public R deleteForum(String forumId,@RequestBody Forum forum){
        forum.setForumCoverpath(RESOURCE_PATH+forum.getForumCoverpath());
        forumService.deleteForum(forumId,forum);
        return new R();
    }

    @PostMapping("/detail")
    public R detail(String forumId)throws ParseException{
        ForumVo forumDetail = forumService.getForumDetail(forumId);
        return R.ok(forumDetail);
    }
    @PostMapping("/join")
    public R join(String forumId,String userId){
        forumService.saveJoin(forumId,userId);
        return new R();
    }
    @PostMapping("/unjoin")
    public R unJoin(String forumId,String userId){
        forumService.saveunJoin(forumId,userId);
        return new R();
    }
    @PostMapping("/isJoin")
    public R isJoin(String forumId,String userId){
        boolean b = forumService.checkIsJoin(forumId, userId);
        return R.ok(b);
    }

    @PostMapping("/getJoinCount")
    public R getJoinCount(String forumId){
        int countsByForumId = forumService.getCountsByForumId(forumId);
        return R.ok(countsByForumId);
    }
}
