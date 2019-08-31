package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.Language;

public class JudgerFactory {

    public static Judger createJudger(Language language, JudgeData judgeData) {
        switch (language) {
            case JAVA:
                return new JavaJudger(judgeData);
            case PYTHON:
                return new PythonJudger(judgeData);
            default:
                return null;
        }
    }
}
