package edu.kkbdv.controller;

import edu.kkbdv.common.util.RedisOperator;
import edu.kkbdv.pojo.Users;
import edu.kkbdv.pojo.vo.UsersVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 定义一个工具controller
 */
@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;

    public static final String USER_REDIS_SESSION = "user-redis-session";

    // ffmpeg directory
    public static final String FFMEG_EXE = "D:\\ffmpeg\\bin\\ffmpeg.exe";
    // 5 datas per page
    public static final Integer PAGE_SIZE = 5;

    //the resource path
    public static final String RESOURCE_PATH = "G:/kkbdv_dev/";
}
