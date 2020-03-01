package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.Language;
import com.penguin.penguincoco.lib.model.ProblemCase;

import java.io.*;

public class ReadAndPrint extends Behavior {

    @Override
    void init(JudgeData judgeData, Language language) {
        this.language = language;
    }

    @Override
    void execute(ProblemCase problemCase, ProcessBuilder processBuilder, String encoding) {
        try {
            this.processBuilder = processBuilder;
            this.problemCase = problemCase;
            process = processBuilder.start();
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), encoding));
            reader = new BufferedReader(new InputStreamReader(process.getInputStream(), encoding));
            error = new BufferedReader(new InputStreamReader(process.getErrorStream(), encoding));
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double startJudgeTime = System.currentTimeMillis();
        JudgeCalculator.writePara(writer, problemCase.getInputArray(), language);
        if (!JudgeCalculator.isProcessTimeout(process)) {
            outputBuilder.append(JudgeCalculator.readOutput(reader));
            double endJudgeTime = System.currentTimeMillis();
            errorBuilder.append(JudgeCalculator.readOutput(error));
            problemResult = JudgeCalculator.compareOutputAndAnswer(outputBuilder.toString(), problemCase.getOutput(),
                    errorBuilder.toString(), startJudgeTime, endJudgeTime);
        }
        else {
            problemResult = JudgeCalculator.executeTimeoutError();
        }
        deleteRemainedFile();
    }

}
