package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.Language;
import com.penguin.penguincoco.lib.model.ProblemCase;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadFileAndPrint extends Behavior {

    private String readFilePath;
    private BufferedReader reader;

    @Override
    void init(JudgeData judgeData, Language language) {
        this.readFilePath = judgeData.getReadFilePath()[Integer.parseInt(getName())];
    }

    @Override
    void execute(ProblemCase problemCase, ProcessBuilder processBuilder, String encoding) {
        this.processBuilder = processBuilder;
        this.problemCase = problemCase;
        try {
            FileUtils.writeStringToFile(new File(readFilePath), problemCase.getInputStr(), encoding);
            process = processBuilder.start();
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
