package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeProblemResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Compile extends Thread {

    private BufferedReader reader;
    private Process process;
    private StringBuilder outputBuilder = new StringBuilder();
    private JudgeProblemResult problemResult;


    public void execute(ProcessBuilder processBuilder, String encoding) {
        try {
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), encoding));
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (!JudgeCalculator.isProcessTimeout(process)) {
            outputBuilder.append(JudgeCalculator.readOutput(reader));
            if (outputBuilder.length() != 0) {
                problemResult = JudgeCalculator.compileError(outputBuilder.toString());
            }
            else {
                problemResult = JudgeCalculator.compileSuccess();
            }
        }
        else {
            problemResult = JudgeCalculator.compileTimeoutError();
        }
    }

    public JudgeProblemResult getProblemResult() {
        return problemResult;
    }
}
