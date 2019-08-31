package com.penguin.penguincoco.lib.judge;


import com.penguin.penguincoco.lib.model.JudgeBehavior;

public class JudgeBehaviorFactory {

    public Behavior createJudgeBehavior(JudgeBehavior behavior) {
        switch (behavior) {
            case ReadAndPrint:
                return new ReadAndPrint();
            case ReadAndWriteFile:
                return new ReadAndWriteFile();
            case ReadFileAndPrint:
                return new ReadFileAndPrint();
            case ReadFileAndWriteFile:
                return new ReadFileAndWriteFile();
            default:
                return null;
        }
    }
}
