package edu.kkbdv.common;

import lombok.Getter;
import lombok.Setter;

/**
 *  定义后端返回到前端的类
 *  参照:layui的返回值
 *  code: 0正常 200错误
 */

@Getter
@Setter
public class R{

    //状态值
    private int code;
    //返回信息
    private String message="";
    //返回数据
    private Object data=null;

    /**
     * 正常返回内容
     */
    public R(){
        this.code=0;
    }
    public R(String msg,int code){
        this.message=msg;
        this.code=code;
    }

    /**
     * 带有返回内容的返回
     * @param data
     */
    public R(Object data){
        this();
        this.data=data;
    }

    public static R error(String msg){
        return new R(msg,200);
    }
    public static R ok(String msg){
        return new R(msg,0);
    }
    public static R ok(Object data){
        return new R(data);
    }

    public static R ok(Object data,String msg){
        R r = new R();
        r.setData(data);
        r.setMessage(msg);
        return r;
    }

}
