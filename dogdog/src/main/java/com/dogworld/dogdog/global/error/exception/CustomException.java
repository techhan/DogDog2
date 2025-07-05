package com.dogworld.dogdog.global.error.exception;

import com.dogworld.dogdog.global.error.code.ErrorCode;
import com.dogworld.dogdog.global.error.detail.ErrorDetail;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import org.flywaydb.core.api.ErrorDetails;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
    private final Map<String, Object> attributes;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.attributes = Collections.emptyMap();
    }

    public CustomException(ErrorCode errorCode, ErrorDetail detail) {
        this(errorCode, detail.toMap());
    }

    public CustomException(ErrorCode errorCode, Map<String, Object> attributes) {
        super(formatMessage(errorCode.getMessage(), attributes));
        this.errorCode = errorCode;
        this.attributes = attributes;
    }

    private static String formatMessage(String template, Map<String, Object> args) {
        String result = template;
        for(Map.Entry<String, Object> entry : args.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
        }
        return result;
    }

}
