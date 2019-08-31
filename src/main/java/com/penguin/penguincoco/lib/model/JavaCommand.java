package com.penguin.penguincoco.lib.model;

import java.util.ArrayList;
import java.util.List;

public class JavaCommand extends Command {

    private String codeFileName = "Main.java";

    @Override
    public List<String> getCompileCommand() {
        List<String> command = new ArrayList<>();
        command.add("javac");
        command.add(codeFileName);
        return command;
    }

    @Override
    public List<String> getExecuteCommand() {
        List<String> command = new ArrayList<>();
        command.add("java");
        command.add(codeFileName.replace(".java", ""));
        return command;
    }

    @Override
    public String getFileName() {
        return codeFileName;
    }
}
