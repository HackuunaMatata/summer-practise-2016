package com.venedikttsulenev.ScheduleManager;

public class SMWrongArgcException extends Exception {
    private int argc;
    SMWrongArgcException(int expectedArgc) {
        this.argc = expectedArgc;
    }
    public String getMessage() {
        return Integer.toString(argc) + " arguments expected";
    }
}
