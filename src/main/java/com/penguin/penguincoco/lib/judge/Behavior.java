package com.penguin.penguincoco.lib.judge;


import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.JudgeProblemResult;
import com.penguin.penguincoco.lib.model.Language;
import com.penguin.penguincoco.lib.model.ProblemCase;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public abstract class Behavior extends Thread {

    Language language;
    ProcessBuilder processBuilder;
    Process process;
    ProblemCase problemCase;
    BufferedWriter writer;
    BufferedReader reader;
    BufferedReader error;
    StringBuilder outputBuilder = new StringBuilder();
    StringBuilder errorBuilder = new StringBuilder();
    JudgeProblemResult problemResult;

    abstract void init(JudgeData judgeData, Language language);

    abstract void execute(ProblemCase problemCase, ProcessBuilder processBuilder, String encoding);

    JudgeProblemResult getProblemResult() {
        return problemResult;
    }

    void deleteRemainedFile() {
        try {
            FileUtils.deleteDirectory(processBuilder.directory());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
