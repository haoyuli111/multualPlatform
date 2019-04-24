package cn.lichuachua.mp.mpserver.wrapper;

import lombok.Data;

/**
 * @author 李歘歘
 *      文件上传的返回值
 */
@Data
public class FileWrapper<T> {
    /**
     * 定义常量
     */
    final static Integer SUCCESS = 1;

    final static String MESSAGE_SUCCESS = "操作成功";

    final static Integer FAILURE = 0;

    final static String MESSAGE_FAILURE = "操作失败";

    /**
     * 定义变量
     */
    private String url;

    private Integer success;

    private String message;
    public FileWrapper(){

    }
    public FileWrapper(Integer success,String message, String url){
        this.success = success;
        this.message = message;
        this.url = url;
    }

    public static FileWrapper success(String url) {
        return new FileWrapper(SUCCESS,MESSAGE_SUCCESS,url);
    }
}
