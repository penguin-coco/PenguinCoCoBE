package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Executor {

    private Language language;
    private JudgeData judgeData;
    private JudgeReport judgeReport = new JudgeReport();

    public Executor(JudgeData judgeData, Language language) {
        this.judgeData = judgeData;
        this.language = language;
    }

    public JudgeReport compileAndExecute() {
        Command command = judgeData.getCommand();
        String encoding = command.getEncoding();
        String codeFileName = command.getFileName();
        String[] codes = judgeData.getCodes();

        try {
            Compile[] compiles = new Compile[codes.length];
            for (int i = 0; i < codes.length; i++) {
                String path = judgeData.getCodeFilePath()[i] + codeFileName;
                FileUtils.writeStringToFile(new File(path), codes[i], encoding);
                ProcessBuilder processBuilder = new ProcessBuilder(command.getCompileCommand());
                processBuilder.redirectErrorStream(true);
                processBuilder.directory(new File(judgeData.getCodeFilePath()[i]));
                compiles[i] = new Compile();
                compiles[i].execute(processBuilder, encoding);
            }
            for (int i = 0; i < compiles.length; i++) {
                compiles[i].join();
            }
            boolean isSuccessCompile = true;
            List<JudgeProblemResult> results = new ArrayList<>();
            for (int i = 0; i < compiles.length; i++) {
                results.add(compiles[i].getProblemResult());
                JudgeSymbol symbol = compiles[i].getProblemResult().getSymbol();
                if (symbol == JudgeSymbol.CE) {
                    isSuccessCompile = false;
                }
            }
            if (isSuccessCompile) {
                JudgeBehaviorFactory factory = new JudgeBehaviorFactory();
                Behavior[] behaviors = new Behavior[codes.length];
                for (int i = 0; i < behaviors.length; i++) {
                    behaviors[i] = factory.createJudgeBehavior(judgeData.getBehavior());
                }
                for (int i = 0; i < codes.length; i++) {
                    ProblemCase problemCase = judgeData.getProblemCases().get(i);

                    ProcessBuilder processBuilder = new ProcessBuilder(command.getExecuteCommand());
                    processBuilder.directory(new File(judgeData.getCodeFilePath()[i]));
                    behaviors[i].setName(i + "");
                    behaviors[i].init(judgeData, language);
                    behaviors[i].execute(problemCase, processBuilder, encoding);
                }
                for (int i = 0; i < behaviors.length; i++) {
                    behaviors[i].join();
                }
                List<JudgeProblemResult> problemResults = new ArrayList<>();
                for (int i = 0; i < behaviors.length; i++) {
                    problemResults.add(behaviors[i].getProblemResult());
                }
                judgeReport.setResults(problemResults);
            }
            else {
                judgeReport.setResults(results);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return judgeReport;
    }

    public JudgeReport execute() {
        Command command = judgeData.getCommand();
        String encoding = command.getEncoding();
        String codeFileName = command.getFileName();
        String[] codes = judgeData.getCodes();

        try {
            for (int i = 0; i < codes.length; i++) {
                String path = judgeData.getCodeFilePath()[i] + codeFileName;
                FileUtils.writeStringToFile(new File(path), codes[i], "UTF-8");
            }

            JudgeBehaviorFactory factory = new JudgeBehaviorFactory();
            Behavior[] behaviors = new Behavior[codes.length];
            for (int i = 0; i < behaviors.length; i++) {
                behaviors[i] = factory.createJudgeBehavior(judgeData.getBehavior());
            }

            for (int i = 0; i < codes.length; i++) {
                ProblemCase problemCase = judgeData.getProblemCases().get(i);
                ProcessBuilder processBuilder = new ProcessBuilder(command.getExecuteCommand());
                processBuilder.directory(new File(judgeData.getCodeFilePath()[i]));
                behaviors[i].setName(i + "");
                behaviors[i].init(judgeData, language);
                behaviors[i].execute(problemCase, processBuilder, encoding);
            }
            for (int i = 0; i < behaviors.length; i++) {
                behaviors[i].join();
            }
            List<JudgeProblemResult> problemResults = new ArrayList<>();
            for (int i = 0; i < behaviors.length; i++) {
                problemResults.add(behaviors[i].getProblemResult());
            }
            judgeReport.setResults(problemResults);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return judgeReport;
    }
}
