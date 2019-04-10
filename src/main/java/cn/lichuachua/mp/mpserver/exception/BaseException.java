package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.common.enums.BaseErrorCodeEnum;
import cn.lichuachua.mp.core.exception.SysException;
import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author  李歘歘
 */

public class BaseException extends SysException {

    public BaseException(int code, String message){
        super(message);
        this.code = code;
    }
    public BaseException(BaseErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}

    public BaseException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.getMessage(), args));
        this.code = codeEnum.getCode();
    }
}
