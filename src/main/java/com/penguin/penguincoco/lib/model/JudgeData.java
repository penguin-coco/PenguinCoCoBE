package com.penguin.penguincoco.lib.model;

import java.util.List;

public class JudgeData {

    private String uniNumber;
    private String oriCode;
    private String[] codes;
    private List<ProblemCase> problemCases;
    private Command command;
    private String oriReadFilePath = "input.txt";
    private String oriWriteFilePath = "output.txt";
    private String[] codeFilePath;
    private String[] readFilePath;
    private String[] writeFilePath;
    private JudgeBehavior behavior;

    public JudgeData(String uniNumber, String oriCode, List<ProblemCase> problemCases, JudgeBehavior behavior) {
        this.uniNumber = uniNumber;
        this.oriCode = oriCode;
        this.problemCases = problemCases;
        this.behavior = behavior;
        divideOriCode();
        initFilePathAndAdjustCode();
    }

    private void divideOriCode() {
        codes = new String[problemCases.size()];
        for (int i = 0; i < codes.length; i++) {
            codes[i] = oriCode;
        }
    }

    private void initFilePathAndAdjustCode() {
        switch (behavior) {
            case ReadAndPrint:
                initCodeFilePath();
                break;
            case ReadAndWriteFile:
                initCodeFilePath();
                initWriteFilePath();
                //adjustCodeWriteFilePath();
                break;
            case ReadFileAndPrint:
                initCodeFilePath();
                initReadFilePath();
                //adjustCodeReadFilePath();
                break;
            case ReadFileAndWriteFile:
                initCodeFilePath();
                initReadFilePath();
                initWriteFilePath();
                //adjustCodeReadFilePath();
                //adjustCodeWriteFilePath();
                break;
        }
    }

    private void initCodeFilePath() {
        codeFilePath = new String[problemCases.size()];
        for (int i = 0; i < codeFilePath.length; i++) {
            codeFilePath[i] = "judge/" + uniNumber + "/" + i + "/";
        }
    }

    private void initReadFilePath() {
        readFilePath = new String[problemCases.size()];
        for (int i = 0; i < readFilePath.length; i++) {
            readFilePath[i] = "judge/" + uniNumber + "/" + i + "/" + oriReadFilePath;
        }
    }

    private void initWriteFilePath() {
        writeFilePath = new String[problemCases.size()];
        for (int i = 0; i < writeFilePath.length; i++) {
            writeFilePath[i] = "judge/" + uniNumber + "/" + i + "/" + oriWriteFilePath;
         }
    }

    private void adjustCodeReadFilePath() {
        for (int i = 0; i < readFilePath.length; i++) {
            codes[i] = codes[i].replace(oriReadFilePath, readFilePath[i]);
        }
    }

    private void adjustCodeWriteFilePath() {
        for (int i = 0; i < writeFilePath.length; i++) {
            codes[i] = codes[i].replace(oriWriteFilePath, writeFilePath[i]);
        }
    }

    public String[] getCodeFilePath() {
        return codeFilePath;
    }

    public String[] getReadFilePath() {
        return readFilePath;
    }

    public String[] getWriteFilePath() {
        return writeFilePath;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }

    public JudgeBehavior getBehavior() {
        return behavior;
    }

    public List<ProblemCase> getProblemCases() {
        return problemCases;
    }

    public void setProblemCases(List<ProblemCase> problemCases) {
        this.problemCases = problemCases;
    }
}
