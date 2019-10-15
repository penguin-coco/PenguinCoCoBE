package com.penguin.penguincoco.lib.model;

import java.util.ArrayList;
import java.util.List;

public class PythonCommand extends Command {

    private String codeFileName = "main.py";

    @Override
    public List<String> getCompileCommand() {
        return null;
    }

    @Override
    public List<String> getExecuteCommand() {
        List<String> command = new ArrayList<>();
        String osName= System.getProperty("os.name");
        if (osName.equals("Linux")) {
            command.add("python3");
        }
        else {
            command.add("python");
        }
        command.add(codeFileName);
        return command;
    }

    @Override
    public String getFileName() {
        return codeFileName;
    }
}
