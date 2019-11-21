package edu.kkbdv.utils;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

public class GetOpenid {

    /**
     * 通过官方api 获取 登陆信息，jackjson解析字符串成map
     * @param code
     * @return
     */
    public static HashMap get(String code){
        HashMap param = new HashMap();
        HashMap<String,Object> result = new HashMap<>();
        param.put("appid","wx9f445de8447b3aa0");
        param.put("secret","f7794ad26109b2f6310ed1d766de1f4f");
        param.put("js_code",code);
        param.put("grant_type","authorization_code");
        String s = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session", param);
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(s, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
