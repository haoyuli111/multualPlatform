package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class TeamResourceException extends BaseException {
    public TeamResourceException(int code, String message){super(code,message);}
    public TeamResourceException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
