package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class TeamException extends BaseException {
    public TeamException(int code, String message){super(code,message);}
    public TeamException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
