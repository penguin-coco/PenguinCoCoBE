package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.TeamManager;
import com.penguin.penguincoco.service.TeamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Api(value = "TeamApi", description = "分組的相關Api")
@RequestMapping("/api/team")
@RestController
public class TeamApi extends BaseApi {

    private TeamService teamService;
    private TeamManager teamManager;

    @Autowired
    public TeamApi(TeamService teamService,
                   TeamManager teamManager) {
        this.teamService = teamService;
        this.teamManager = teamManager;
    }

    @ApiOperation(value = "建立討論隊伍",
            notes = "取得problemId、pair(批改與被批改者名單)")
    @PostMapping(value = "/createTeam")
    public Message createTeam(@RequestBody Map<String, Object> map) {
        String problemId = (String) map.get("problemId");
        List<Map<String, String>> pairs = (List<Map<String, String>>) map.get("pairs");
        Message message;
        try {
            teamService.createTeam(problemId, pairs);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CREATE_TEAM_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得此學生要批改的對象",
            notes = "取得problemId、account，來取得此學生要批改的對象")
    @GetMapping(value = "/correctStuds")
    public Message correctStuds(String problemId, HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            List<Map<String, String>> result = teamManager.correctStuds(problemId, account);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CORRECT_STUDS_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得此學生是否已經完成互評",
            notes = "取得problemId、account，來取得此學生是否已經完成互評")
    @GetMapping(value = "/checkCorrectStatus")
    private Message checkCorrectStatus(String problemId, HttpSession session, HttpServletRequest request) {
        String account = getUserAccount(session);
        Message message;
        try {
            Map<String, Boolean> result = teamManager.checkCorrectStatus(problemId, account);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CHECK_CORRECT_STATUS_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得此學生是否已經被互評完成",
            notes = "取得problemId、account，來取得此學生是否已經被互評完成")
    @GetMapping(value = "/checkCorrectedStatus")
    public Message checkCorrectedStatus(String problemId, HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            Map<String, Boolean> result = teamManager.checkCorrectedStatus(problemId, account);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CHECK_CORRECTED_STATUS_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得此學生批改對方的資訊",
            notes = "取得problemId、account，來取得此學生批改對方的資訊")
    @GetMapping(value = "/correctInfo")
    public Message correctInfo(String problemId, HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            List<Map<String, Object>> result = teamManager.correctInfo(problemId, account);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CORRECTED_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得此學生被批改的資訊",
            notes = "取得problemId、account，來取得此學生被批改的資訊")
    @GetMapping(value = "/correctedInfo")
    public Message correctedInfo(String problemId, HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            List<Map<String, Object>> result = teamManager.correctedInfo(problemId, account);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CORRECT_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "送出評分資訊",
            notes = "取得problemId、account、correctedList，來送出評分資訊")
    @PostMapping(value = "/submitCorrect")
    public Message submitCorrect(@RequestBody Map<String, Object> map, HttpSession session) {
        String problemId = (String) map.get("problemId");
        String account = getUserAccount(session);
        List<Map<String, Object>> correctedList = (List<Map<String, Object>>) map.get("correctedList");
        Message message;
        try {
            teamManager.submitCorrect(problemId, account, correctedList);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.SUBMIT_CORRECT_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得互評成績",
            notes = "取得互評成績")
    @GetMapping(value = "/discussScore")
    public Message correctedInfo(String problemId) {
        Message message;
        try {
            List<Map<String, Object>> result = teamManager.discussScore(problemId);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DISCUSS_SCORE_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "老師取得此討論題學生的批改資訊",
            notes = "老師取得此討論題學生的批改資訊")
    @GetMapping(value = "/teacher/correctInfo")
    public Message teacherCorrectInfo(String problemId) {
        Message message;
        try {
            List<Map<String, Object>> result = teamManager.teacherCorrectInfo(problemId);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DISCUSS_SCORE_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "老師取得此討論題學生的批改資訊",
            notes = "老師取得此討論題學生的批改資訊")
    @PostMapping(value = "/teacher/submitCorrect")
    public Message teacherSubmitCorrect(@RequestBody Map<String, Object> map, HttpSession session) {
        String teacherAccount = getUserAccount(session);
        String problemId = map.get("problemId").toString();
        Message message;
        try {
            teamManager.teacherSubmitCorrect(teacherAccount, problemId, map);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DISCUSS_SCORE_ERROR, "");
        }
        return message;
    }
}
