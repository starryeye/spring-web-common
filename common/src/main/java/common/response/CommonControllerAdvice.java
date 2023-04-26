package common.response;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    public static final List<CommonErrorCode> SPECIFIC_ALERT_TARGET_ERROR_CODE_LIST = new ArrayList<>();
}
