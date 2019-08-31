package com.penguin.penguincoco.lib.model;

public class ProblemCase {

    private String input;
    private String output;

    public ProblemCase(String input, String output) {
        this.input = input;
        this.output = output;
    }

    public String getInputStr() {
        return input;
    }

    public String[] getInputArray() {
        // 換行區分參數
        String[] result = input.split("\n");
        return result;
    }

    public String getOutput() {
        return output;
    }
}
