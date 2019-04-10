package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class ArticleException extends BaseException {
    public ArticleException(int code, String message){super(code,message);}
    public ArticleException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
