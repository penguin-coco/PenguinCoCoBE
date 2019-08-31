package com.penguin.penguincoco.lib.judge;

import com.penguin.penguincoco.lib.model.JavaCommand;
import com.penguin.penguincoco.lib.model.JudgeData;
import com.penguin.penguincoco.lib.model.JudgeReport;
import com.penguin.penguincoco.lib.model.Language;

public class JavaJudger extends Judger {

    public JavaJudger(JudgeData judgeData) {
        this.judgeData = judgeData;
        judgeData.setCommand(new JavaCommand());
    }

    @Override
    public JudgeReport performJudge() {
        executor = new Executor(judgeData, Language.JAVA);
        return executor.compileAndExecute();
    }
}
