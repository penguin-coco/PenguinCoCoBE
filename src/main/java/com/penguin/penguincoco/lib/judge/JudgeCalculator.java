package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeProblemResult;
import com.penguin.penguincoco.lib.model.JudgeSymbol;
import com.penguin.penguincoco.lib.model.Language;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class JudgeCalculator {

    public static boolean isProcessTimeout(Process process) {
        try {
            if (process.waitFor(3, TimeUnit.SECONDS)){
                return false;
            }
            else {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static String readOutput(BufferedReader reader) {
        StringBuilder output = new StringBuilder();
        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line);
                output.append("\n");
            }
            reader.close();
            if (output.length() != 0) {
                output.deleteCharAt(output.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void writePara(BufferedWriter writer, String[] paras, Language language) {
        try {
            // 只有一個參數，不用多輸入空格
            if (paras.length == 1) {
                writer.write(paras[0]);
            }
            else {
                for (String para : paras) {
                    if (!para.equals("\n")) {
                        writer.write(para);
                        switch (language){
                            case JAVA:
                                writer.write(" ");
                                break;
                            case PYTHON:
                                writer.newLine();
                                break;
                        }
                    }
                    else {
                        writer.newLine();
                    }
                    writer.flush();
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double calculateJudgeTimeForMs(double startTime, double endTime) {
        return (endTime - startTime);
    }

    public static JudgeProblemResult compareOutputAndAnswer(String output, String answer, String error,
                                                            double startTime, double endTime) {
        JudgeProblemResult problemResult;
        double judgeTime = calculateJudgeTimeForMs(startTime, endTime);
        if (output.equals(answer)) {
            problemResult = new JudgeProblemResult(100, output, judgeTime, JudgeSymbol.AC, "");
        }
        else {
            if (!error.equals("")) {
                problemResult = new JudgeProblemResult(0, output, judgeTime, JudgeSymbol.RE, error);
            }
            else {
                problemResult = new JudgeProblemResult(0, output, judgeTime, JudgeSymbol.WA, "");
            }
        }
        return problemResult;
    }

    public static JudgeProblemResult compileTimeoutError() {
        JudgeProblemResult problemResult = new JudgeProblemResult(0, "", 0, JudgeSymbol.TLE, "Compile Timeout Error!");
        return problemResult;
    }

    public static JudgeProblemResult compileError(String message) {
        JudgeProblemResult problemResult = new JudgeProblemResult(0, "", 0, JudgeSymbol.CE, message);
        return problemResult;
    }

    public static JudgeProblemResult compileSuccess() {
        JudgeProblemResult problemResult = new JudgeProblemResult(0, "", 0, JudgeSymbol.AC, "");
        return problemResult;
    }

    public static JudgeProblemResult executeTimeoutError() {
        JudgeProblemResult problemResult = new JudgeProblemResult(0, "", 0, JudgeSymbol.TLE, "Execute Timeout Error!");
        return problemResult;
    }
}
