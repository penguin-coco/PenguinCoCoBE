package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.Language;
import com.penguin.penguincoco.lib.model.ProblemCase;
import org.apache.commons.io.FileUtils;

import java.io.*;

public class ReadFileAndWriteFile extends Behavior {

    private String readFilePath;
    private String writeFilePath;

    @Override
    void init(JudgeData judgeData, Language language) {
        this.readFilePath = judgeData.getReadFilePath()[Integer.parseInt(getName())];
        this.writeFilePath = judgeData.getWriteFilePath()[Integer.parseInt(getName())];
    }

    @Override
    void execute(ProblemCase problemCase, ProcessBuilder processBuilder, String encoding) {
        this.processBuilder = processBuilder;
        this.problemCase = problemCase;
        try {
            FileUtils.writeStringToFile(new File(readFilePath), problemCase.getInputStr(), encoding);
            process = processBuilder.start();
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
            problemResult = JudgeCalculator.executeTimeoutError();
        }
        deleteRemainedFile();
    }
}
