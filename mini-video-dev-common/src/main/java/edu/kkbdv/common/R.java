package edu.kkbdv.common;

import lombok.Getter;
import lombok.Setter;

/**
 *  定义后端返回到前端的类
 *  code: 0正常 200错误
 * @param <T>
 */

@Getter
@Setter
public class R<T> {

    private int code;
    private String message="";
    private T data=null;

    /**
     * 正常返回内容
     */
    public R(){
        this.code=0;
        this.message="正常";
    }

    /**
     * 带有返回内容的返回
     * @param data
     */
    public R(T data){
        this();
        this.data=data;
    }
}
