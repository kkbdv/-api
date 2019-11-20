package edu.kkbdv.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("测试接口")
@RestController
public class HelloController {

    @ApiOperation(value="测试接口", notes="返回字符串")
    @ApiImplicitParam(name = "cid", value = "客户id", required = true, dataType = "String") //对应参数
    @RequestMapping("/hello")
    public String hello(String cid){
        return "hello world"+cid;
    }
}
