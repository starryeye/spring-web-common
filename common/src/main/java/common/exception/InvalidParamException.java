package common.exception;

import common.response.CommonErrorCode;

public class InvalidParamException extends CommonBaseException{

    public InvalidParamException() {
        super(CommonErrorCode.COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(CommonErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidParamException(String message) {
        super(message, CommonErrorCode.COMMON_INVALID_PARAMETER);
    }

    public InvalidParamException(String message, CommonErrorCode errorCode) {
        super(message, errorCode);
    }
}
