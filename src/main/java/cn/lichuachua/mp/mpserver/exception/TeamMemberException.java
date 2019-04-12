package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class TeamMemberException extends BaseException {
    public TeamMemberException(int code, String message){super(code,message);}
    public TeamMemberException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
