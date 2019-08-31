package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.JudgeManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "RankApi", description = "取得正確解題、最佳解題的學生排行")
@RequestMapping("/api/rank")
@RestController
public class RankApi extends BaseApi {

    private JudgeManager judgeManager;

    @Autowired
    public RankApi(JudgeManager judgeManager) {
        this.judgeManager = judgeManager;
    }

    @ApiOperation(value = "取得正確解題的學生排行",
            notes = "取得courseId，來獲得正確解題的學生排行")
    @GetMapping(value = "/getCorrectRank")
    private Message getCorrectRank(String courseId) {
        Message message;

        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, judgeManager.getCorrectRank(Long.parseLong(courseId)));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_CORRECT_RANK_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得最佳解答的學生排行",
            notes = "取得courseId，來獲得最佳解答的學生排行")
    @GetMapping(value = "/getBestCodeRank")
    private Message getBestCodeRank(String courseId) {
        Message message;

        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, judgeManager.getBestCodeRank(Long.parseLong(courseId)));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_BEST_CODE_RANK_ERROR, "");
        }
        return message;
    }
}
