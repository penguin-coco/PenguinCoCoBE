package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.JudgeReport;

public abstract class Judger {

    Executor executor;
    JudgeData judgeData;

    public abstract JudgeReport performJudge();
}
