package com.example.rubankgui;

/**
 * Enum class to denote the campus for a College Checking account.
 * @author Deshna Doshi, Haejin Song
 */
public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);

    private final int CODE;

    Campus(int code) {
        this.CODE = code;
    }

    public int getCode() {
        return CODE;
    }


}
