package edu.kkbdv.controller;

import edu.kkbdv.common.R;
import edu.kkbdv.utils.GetOpenid;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @ApiOperation(value = "用户登陆",notes = "返回R类包含重要信息token")
    @ApiImplicitParam(name = "code",value ="小程序传入的code",required = true,dataType = "string")
    @GetMapping("/login")
    public R login(String code){
      log.info("从第三方服务器返回的数据{}", GetOpenid.get(code));
      return new R("123456");
    }
}
