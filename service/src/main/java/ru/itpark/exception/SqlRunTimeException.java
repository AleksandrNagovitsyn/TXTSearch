package ru.itpark.exception;

public class SqlRunTimeException extends  RuntimeException {
    public SqlRunTimeException() {
        super();
    }

    public SqlRunTimeException(String message) {
        super(message);
    }

    public SqlRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SqlRunTimeException(Throwable cause) {
        super(cause);
    }
}
