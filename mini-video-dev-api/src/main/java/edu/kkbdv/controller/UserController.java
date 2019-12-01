package edu.kkbdv.controller;

import edu.kkbdv.common.util.MD5Utils;
import edu.kkbdv.common.R;
import edu.kkbdv.pojo.Users;
import edu.kkbdv.pojo.UsersReport;
import edu.kkbdv.pojo.Videos;
import edu.kkbdv.pojo.vo.QueryPublisherVo;
import edu.kkbdv.pojo.vo.UsersVo;
import edu.kkbdv.service.UserService;
import edu.kkbdv.service.VideoSerivce;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(value = "用户业务相关的接口",tags = {"service of controller"})
@Slf4j
public class UserController extends BasicController{
    @Autowired
    private UserService userService;
    @Autowired
    private VideoSerivce videoSerivce;

    @ApiOperation(value="用户注册接口", notes="返回用户对象")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "Users")
    @PostMapping("/regist")
    public R regist(@RequestBody Users user){
        //1.判断用户名密码不为空
        if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){
            return R.error("用户名和密码不能为空");
        }
        //2.判断用户名是否存在
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        //3.保存用户
        if(!usernameIsExist){
            user.setNickname(user.getUsername());//nickname和username同名
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);
            userService.saveUser(user);

        }else{
            return R.error("用户名存在，请换一个");
        }
            user.setPassword("");//清空密码
        UsersVo usersVo = setUsersVoToken(user);
        return R.ok(usersVo);
    }



    @ApiOperation(value = "用户登陆接口",notes = "返回用户对象")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true, dataType = "Users")
    @PostMapping("/login")
    public R login(@RequestBody Users user){
        //1.通过username获取对象
        if(StringUtils.isBlank(user.getUsername())||StringUtils.isBlank(user.getPassword())){//空值判断
            return R.error("账号或密码不能为空");
        }
        Users userDo = userService.selUsersByUsername(user.getUsername());

        if(userDo==null){
            //为空返回
            return R.error("账号密码错误");
        }else{
            //2.不为空判断密码 ；密码要加密再对比
            if(userDo.getPassword().equals(MD5Utils.getMD5Str(user.getPassword()))){
                userDo.setPassword("");
                UsersVo usersVo = setUsersVoToken(userDo);//登陆后把对象存到redis-session中
                return R.ok(usersVo);//3.返回对象
            }else {
                return R.error("账号密码错误");
            }
        }
    }

    @ApiOperation(value = "用户注销接口",notes = "返回用户对象")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String",paramType = "query")
    @PostMapping("/logout")
    public R logout(String userId){
        redis.del(USER_REDIS_SESSION+":"+userId);
        return R.ok("登出成功");
    }

    @ApiOperation(value = "用户上传头像",notes = "用户上传头像接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("/uploadFace")
    public R uploadFace(String userId, @RequestParam("file") MultipartFile[] files)throws Exception{

        if(StringUtils.isBlank(userId)){
            return R.error("用户id不能为空");
        }

        //文件保存的命名空间
        String fileSpace = "G:/kkbdv_dev";
        //保存到数据库中的相对路径
        String uploadPathDB = "/"+userId+"/face";

        if(files!=null && files.length>0){

            String fileName  = files[0].getOriginalFilename();
            if(!StringUtils.isBlank(fileName)){
                //文件的最终上传路径
                String finalFacePath = fileSpace+uploadPathDB+"/"+fileName;
                //设置数据库保存路径
                uploadPathDB+=("/"+fileName);
                //判断有无真实路径
                File outFile = new File(finalFacePath);
                if(outFile.getParentFile()!=null|| !outFile.getParentFile().isDirectory()){//这里有点不理解
                    outFile.getParentFile().mkdirs();
                }
                if(!outFile.exists()){
                    outFile.createNewFile();
                }
                files[0].transferTo(outFile);

                //上传成功-更新数据库
                Users user = new Users();
                user.setId(userId);
                user.setFaceImage(uploadPathDB);
                userService.updateUserInfo(user);
                return R.ok(uploadPathDB,"上传成功");

            }else{
                return R.error("上传出错");
            }

        }
        return R.error("服务器出错");
    }

    @ApiOperation(value = "用户信息获取",notes = "用户信息获取接口")
    @ApiImplicitParam(name="userId", value="用户id", required=true,
            dataType="String", paramType="query")
    @PostMapping("/query")
    public R query(String userId,String fanId){
        if(StringUtils.isBlank(userId)){
            return R.error("用户id不能为空");
        }
        //通过id 获得user
        Users user = userService.queryUserInfo(userId);
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user,usersVo);//usersVo更统一而且对密码做了处理
        //check if it has the row about userId and fanId or not
        usersVo.setFollow(userService.isExitThisFans(userId,fanId));

        return R.ok(usersVo);

    }

    public  UsersVo setUsersVoToken(Users user){
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION+":"+user.getId(),uniqueToken,1000*60*30);//过去时间30分钟

        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user,usersVo);
        usersVo.setUserToken(uniqueToken);//设置token给返回的data
        return usersVo;
    }

    @PostMapping("/queryPublisher")
    public R queryPublisher(String loginUserId,String videoId,String publishUserId){
        QueryPublisherVo queryPublisherVo = new QueryPublisherVo();
        //查出发布者信息
        Users user = userService.queryUserInfo(publishUserId);
        queryPublisherVo.setPublisher(user);
        //check the user like the video or not
        queryPublisherVo.setUserLikeVideo(videoSerivce.isUserLikeVideo(loginUserId,videoId));
        return R.ok(queryPublisherVo);
    }

    @PostMapping("/beyourfans")
    public R followMe(String userId,String fanId){
        userService.addUserFans(userId,fanId);
        return new R();
    }
    @PostMapping("/dontbeyourfans")
    public R dontFollowMe(String userId,String fanId){
        userService.reduceUserFans(userId,fanId);
        return new R();
    }

    @PostMapping("/reportUser")
    public R reportUser(@RequestBody UsersReport usersReport){
        userService.savReport(usersReport);
        return R.ok("举报成功！");
    }

}
