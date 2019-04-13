package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class TeamTypeException extends BaseException {
    public TeamTypeException(int code, String message){super(code,message);}
    public TeamTypeException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
