package com.penguin.penguincoco.lib.model;

public class JudgeProblemResult {

    private int grade;
    private String output;
    private double runTime;
    private JudgeSymbol symbol;
    private String message;

    public JudgeProblemResult(int grade, String output, double runTime, JudgeSymbol symbol, String message) {
        this.grade = grade;
        this.output = output;
        this.runTime = runTime;
        this.symbol = symbol;
        this.message = message;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public double getRunTime() {
        return runTime;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public JudgeSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(JudgeSymbol symbol) {
        this.symbol = symbol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
