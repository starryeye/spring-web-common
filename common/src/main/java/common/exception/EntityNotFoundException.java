package common.exception;

import common.response.CommonErrorCode;

public class EntityNotFoundException extends CommonBaseException{

    public EntityNotFoundException() {
        super(CommonErrorCode.COMMON_ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message) {
        super(message, CommonErrorCode.COMMON_ENTITY_NOT_FOUND);
    }
}
