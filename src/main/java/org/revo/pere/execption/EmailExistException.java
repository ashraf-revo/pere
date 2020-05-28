package org.revo.pere.execption;

public class EmailExistException extends RuntimeException {
    private String value;
    private String filedName;

    public EmailExistException(String message, String filedName, String value) {
        super(message);
        this.value = value;
        this.filedName = filedName;
    }

    public String getFiledName() {
        return filedName;
    }

    public String getValue() {
        return value;
    }
}
