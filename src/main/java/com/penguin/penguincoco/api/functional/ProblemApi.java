package com.penguin.penguincoco.api.functional;


import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.dao.domain.problem.ProblemInfo;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.manager.CourseManager;
import com.penguin.penguincoco.manager.JudgeManager;
import com.penguin.penguincoco.manager.ProblemManager;
import com.penguin.penguincoco.service.ProblemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(value = "ProblemApi", description = "題目的相關Api")
@RequestMapping("/api/problem")
@RestController
public class ProblemApi extends BaseApi {

    private ProblemManager problemManager;
    private ProblemService problemService;
    private CourseManager courseManager;
    private JudgeManager judgeManager;

    @Autowired
    public ProblemApi(ProblemManager problemManager,
                      ProblemService problemService,
                      CourseManager courseManager,
                      JudgeManager judgeManager) {
        this.problemManager = problemManager;
        this.problemService = problemService;
        this.courseManager = courseManager;
        this.judgeManager = judgeManager;
    }

    @ApiOperation(value = "取得題目資訊",
            notes = "取得題目資訊")
    @GetMapping(value = "/getInfo")
    private Message getInfo(String problemId) {
        Message message;
        try {
            ProblemInfo problemInfo = problemService.getInfo(Long.parseLong(problemId));
            message = new Message(ApiMessageCode.SUCCESS_STATUS, problemInfo);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_PROBLEM_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得課程下所有題目資訊",
            notes = "取得courseId，來獲取課程下所有題目資訊")
    @GetMapping(value = "/getProblems")
    private Message getProblems(String courseId) {
        Message message;
        try {
            List<Map<String, Object>> result = judgeManager.getProblems(Long.parseLong(courseId));
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_PROBLEMS_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "在課程中建立題目",
            notes = "取得題目資訊，並建立題目")
    @PostMapping(value = "/addProblem")
    private Message addProblem(@RequestBody Map<String, Object> map) throws ParseException {
        Message message;
        String courseId = map.get("courseId").toString();
        String name = map.get("name").toString();
        String type = map.get("type").toString();
        String category = map.get("category").toString();
        List<String> tagList = (List<String>) map.get("tag");
        List<String> patternList = (List<String>) map.get("pattern");
        String[] tag = tagList.toArray(new String[tagList.size()]);
        String[] pattern = patternList.toArray(new String[patternList.size()]);
        String description = map.get("description").toString();
        String inputDesc = map.get("inputDesc").toString();
        String outputDesc = map.get("outputDesc").toString();
        List<TestCase> testCases = (List<TestCase>) map.get("testCases");
        String deadlineStr = map.get("deadline").toString();
        System.out.println(deadlineStr);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date deadline = df.parse(deadlineStr);
        System.out.println(deadline.toString());
        try {
            Map<String, String> result = courseManager.createProblem(Long.parseLong(courseId), name, type, category,
                    tag, description, inputDesc,
                    outputDesc, pattern,
                    testCases, deadline);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.ADD_PROBLEM_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "在課程中編輯題目",
            notes = "取得題目資訊，並更新題目")
    @PostMapping(value = "/editProblem")
    private Message editProblem(@RequestBody Map<String, Object> map) throws ParseException {
        Message message;
        String problemId = map.get("problemId").toString();
        String name = map.get("name").toString();
        String type = map.get("type").toString();
        String category = map.get("category").toString();
        List<String> tagList = (List<String>) map.get("tag");
        List<String> patternList = (List<String>) map.get("pattern");
        String[] tag = tagList.toArray(new String[tagList.size()]);
        String[] pattern = patternList.toArray(new String[patternList.size()]);
        String description = map.get("description").toString();
        String inputDesc = map.get("inputDesc").toString();
        String outputDesc = map.get("outputDesc").toString();
        List<TestCase> testCases = (List<TestCase>) map.get("testCases");
        String deadlineStr = map.get("deadline").toString();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date deadline = df.parse(deadlineStr);
        try {
            problemService.update(Long.parseLong(problemId), name,
                    type, category, tag, description,
                    inputDesc, outputDesc, pattern, testCases, deadline);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.EDIT_PROBLEM_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "在課程中刪除題目",
            notes = "取得problemId，並刪除題目")
    @PostMapping(value = "/deleteProblem")
    private Message deleteProblem(@RequestBody Map<String, String> map) {
        Message message;
        String problemId = map.get("problemId");
        try {
            problemService.delete(Long.parseLong(problemId));
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DELETE_PROBLEM_ERROR, "");
        }
        return message;
    }
}
