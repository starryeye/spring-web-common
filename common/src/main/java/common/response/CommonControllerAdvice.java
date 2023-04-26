package common.response;

import common.exception.CommonBaseException;
import common.interceptor.CommonHttpRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    private static final List<CommonErrorCode> SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST = new ArrayList<>();

    /**
     * Http status : 500
     * Result : FAIL
     *
     * 시스템 이슈로 의도적 예외가 아님, 집중 모니터링 대상
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<?> onException(Exception e) {

        String requestId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);

        log.error("requestId={} ", requestId, e);
        return CommonResponse.fail(CommonErrorCode.COMMON_SYSTEM_ERROR);
    }

    /**
     * Http status : 200
     * Result : FAIL
     *
     * 시스템 이슈가 아님, 비즈니스 로직 예외
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = CommonBaseException.class)
    public CommonResponse<?> onCommonBaseException(CommonBaseException e) {

        String requestId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);

        if(SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[CommonBaseException] requestId={}, cause={}, errorMsg={}", requestId, NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[CommonBaseException] requestId={}, cause={}, errorMsg={}", requestId, NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(e.getMessage(), e.getErrorCode().name());
    }

    /**
     * Http status : 400
     * Result : FAIL
     *
     * invalid request param
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResponse<?> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String requestId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
        log.warn("[CommonBaseException] requestId={}, errorMsg={}", requestId, NestedExceptionUtils.getMostSpecificCause(e).getMessage());

        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError != null) {
            String message = "Request Error" + " " + fieldError.getField() + "=" + fieldError.getRejectedValue() + " (" + fieldError.getDefaultMessage() + ")";
            return CommonResponse.fail(message, CommonErrorCode.COMMON_INVALID_PARAMETER.name());
        } else {
            return CommonResponse.fail(CommonErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg(), CommonErrorCode.COMMON_INVALID_PARAMETER.name());
        }
    }

    /**
     * Http status : 200
     * Result : FAIL
     *
     * 모니터링 Skip 이 가능한 시스템 이슈
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {ClientAbortException.class})
    public CommonResponse<?> skipException(Exception e) {

        String requestId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);

        log.warn("[SkipException] requestId={}, cause={}, errorMsg={}", requestId, NestedExceptionUtils.getMostSpecificCause(e), NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return CommonResponse.fail(CommonErrorCode.COMMON_SYSTEM_ERROR);
    }
}
