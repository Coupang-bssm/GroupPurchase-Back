package co.kr.grouppurchace.global.exception;

public class ConflictException extends BaseException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}
