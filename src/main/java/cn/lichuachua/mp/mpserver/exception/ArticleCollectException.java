package cn.lichuachua.mp.mpserver.exception;

import cn.lichuachua.mp.mpserver.enums.ErrorCodeEnum;

/**
 * @author 李歘歘
 */
public class ArticleCollectException extends BaseException {
    public ArticleCollectException(int code, String message) {super(code, message);}

    public ArticleCollectException(ErrorCodeEnum codeEnum, Object... args){super(codeEnum, args);}
}
