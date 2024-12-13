package com.snaw.exceptions;

import java.io.PrintStream;

public class DebugException extends RuntimeException {
    //For try-catch purposes...
    protected String bonusMessage;

    public DebugException(){
        super();
    }

    public DebugException(String bonusMessage){
        this.bonusMessage = bonusMessage;
    }

    @Override
    public void printStackTrace(PrintStream printStream) {
        printStream.println(bonusMessage);
        super.printStackTrace(printStream);
    }
}
