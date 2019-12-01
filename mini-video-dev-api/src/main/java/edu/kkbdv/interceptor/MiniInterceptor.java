package edu.kkbdv.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import edu.kkbdv.common.R;
import edu.kkbdv.common.util.JsonUtils;
import edu.kkbdv.common.util.RedisOperator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 *  出错状态码为 code = 200
 *
 */
public class MiniInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisOperator redis;
    public static final String USER_REDIS_SESSION = "user-redis-session";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");

        if(StringUtils.isNotBlank(userId) &&StringUtils.isNotBlank(userToken)){
            String uniToken = redis.get(USER_REDIS_SESSION+":"+userId);
            if(StringUtils.isBlank(uniToken)&&StringUtils.isEmpty(uniToken)){//token无效

                returnErrorResponse(response,R.error("请登陆"));
                return false;
            }else {
                if(!userToken.equals(uniToken)){//token与redis中的不同
                    returnErrorResponse(response,R.error("账号被挤出"));
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            //请登陆
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void returnErrorResponse(HttpServletResponse response, R r)throws IOException, UnsupportedEncodingException {
        OutputStream out = null;

        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(r).getBytes("utf-8"));
            out.flush();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }
}
