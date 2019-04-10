package cn.lichuachua.mp.mpserver.exception;


import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class AcademyException extends BaseException {
    public AcademyException(int code, String message){super(code,message);}
    public AcademyException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
