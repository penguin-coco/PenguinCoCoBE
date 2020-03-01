package com.penguin.penguincoco.lib.model;

import java.util.List;

public abstract class Command {

//    private String encoding = System.getProperty("sun.jnu.encoding");
    private String encoding = "UTF-8";
    public abstract List<String> getCompileCommand();

    public abstract List<String> getExecuteCommand();

    public abstract String getFileName();

    public String getEncoding() {
        return encoding;
    }
}
