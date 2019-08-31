package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.JudgeReport;
import com.penguin.penguincoco.lib.model.Language;
import com.penguin.penguincoco.lib.model.PythonCommand;

public class PythonJudger extends Judger {

    public PythonJudger(JudgeData judgeData) {
        this.judgeData = judgeData;
        judgeData.setCommand(new PythonCommand());
    }

    @Override
    public JudgeReport performJudge() {
        executor = new Executor(judgeData, Language.PYTHON);
        return executor.execute();
    }
}
