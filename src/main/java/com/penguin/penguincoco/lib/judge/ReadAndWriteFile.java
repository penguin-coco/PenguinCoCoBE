package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.Language;
import com.penguin.penguincoco.lib.model.ProblemCase;

import java.io.*;

public class ReadAndWriteFile extends Behavior {

    private String writeFilePath;

    @Override
    void init(JudgeData judgeData, Language language) {
        this.language = language;
        this.writeFilePath = judgeData.getWriteFilePath()[Integer.parseInt(getName())];
    }

    @Override
    void execute(ProblemCase problemCase, ProcessBuilder processBuilder, String encoding) {
        try {
            this.processBuilder = processBuilder;
            this.problemCase = problemCase;
            process = processBuilder.start();
            writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), encoding));
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
            if (new File(writeFilePath).exists()) {
                try {
                    reader = new BufferedReader(new FileReader(writeFilePath));
                    outputBuilder.append(JudgeCalculator.readOutput(reader));
                    double endJudgeTime = System.currentTimeMillis();
                    errorBuilder.append(JudgeCalculator.readOutput(error));
                    problemResult = JudgeCalculator.compareOutputAndAnswer(outputBuilder.toString(), problemCase.getOutput(),
                            errorBuilder.toString(), startJudgeTime, endJudgeTime);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                double endJudgeTime = System.currentTimeMillis();
                errorBuilder.append(JudgeCalculator.readOutput(error));
                problemResult = JudgeCalculator.compareOutputAndAnswer(outputBuilder.toString(), problemCase.getOutput(),
                        errorBuilder.toString(), startJudgeTime, endJudgeTime);
            }
        }
        else {
            problemResult = JudgeCalculator.executeTimeoutError();
        }
        deleteRemainedFile();
    }
}
