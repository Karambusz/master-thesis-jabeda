package edu.agh.jabeda.server.domain.exception;

public enum ErrorCode {
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404);

    private final int codeNumber;

    ErrorCode(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCodeNumber() {
        return this.codeNumber;
    }
}
