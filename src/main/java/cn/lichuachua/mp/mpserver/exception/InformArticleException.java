package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class InformArticleException extends BaseException {
    public InformArticleException(int code, String message) {super(code, message);}

    public InformArticleException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
