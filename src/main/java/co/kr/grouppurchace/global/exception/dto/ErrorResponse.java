package co.kr.grouppurchace.global.exception.dto;

import co.kr.grouppurchace.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private final String errorCode;
    private final String errorMessage;

    public ErrorResponse(ErrorCode errorCode) {
        this.errorCode = errorCode.name();
        this.errorMessage = errorCode.getMessage();
    }
}
