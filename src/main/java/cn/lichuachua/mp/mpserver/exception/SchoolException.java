package cn.lichuachua.mp.mpserver.exception;


import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class SchoolException extends BaseException {
    public SchoolException(int code, String message){super(code,message);}
    public SchoolException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
