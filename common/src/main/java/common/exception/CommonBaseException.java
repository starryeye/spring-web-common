package common.exception;

import common.response.CommonErrorCode;
import lombok.Getter;

@Getter
public class CommonBaseException extends RuntimeException{

    protected CommonErrorCode errorCode;

    public CommonBaseException() {}

    public CommonBaseException(CommonErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    public CommonBaseException(String message, CommonErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CommonBaseException(String message, CommonErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
