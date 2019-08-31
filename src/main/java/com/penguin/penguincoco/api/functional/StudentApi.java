package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.CommonManager;
import com.penguin.penguincoco.manager.CourseManager;
import com.penguin.penguincoco.manager.JudgeManager;
import com.penguin.penguincoco.manager.ProblemManager;
import com.penguin.penguincoco.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Api(value = "StudentApi", description = "學生的相關Api")
@RequestMapping("/api/student")
@RestController
public class StudentApi extends BaseApi {

    private CommonManager commonManager;
    private CourseManager courseManager;
    private JudgeManager judgeManager;
    private ProblemManager problemManager;
    private StudentService studentService;

    @Autowired
    public StudentApi(CommonManager commonManager,
                      CourseManager courseManager,
                      JudgeManager judgeManager,
                      ProblemManager problemManager,
                      StudentService studentService) {
        this.commonManager = commonManager;
        this.courseManager = courseManager;
        this.judgeManager = judgeManager;
        this.problemManager = problemManager;
        this.studentService = studentService;
    }

    @ApiOperation(value = "更改學生密碼",
            notes = "先用原本的密碼(oriPassword)驗證，驗證成功後，才更換密碼(newPassword)")
    @PostMapping(value = "/changePassword")
    private Message changePassword(@RequestBody Map<String, String> map,
                                   HttpSession session) {
        Message message;

        String account = map.get("account");
        String oriPassword = map.get("oriPassword");
        String newPassword = map.get("newPassword");
        String userType = getUserType(session);
        int code = commonManager.updateUserPassword(account, oriPassword, newPassword, userType);
        // code=-1 代表更新失敗
        if (code != -1) {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        }
        else {
            message = new Message(ApiMessageCode.CHANGE_PASSWORD_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得學生的全部課程",
            notes = "取得account，來獲得學生的全部課程")
    @GetMapping(value = "/courseList")
    private Message getCourseList(HttpSession session) {
        Message message;

        String account = getUserAccount(session);
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, courseManager.getStudentCoursesInfo(account));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_COURSE_LIST_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得學生個人資料",
            notes = "存取該學生的個人資料")
    @GetMapping(value = "/info")
    private Message getInfo(String courseId, HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, courseManager.findStudentCourseInfo(Long.parseLong(courseId), account));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得課程下的學生歷史成績及題目資訊",
            notes = "取得courseId，來獲得課程下的學生歷史成績及題目資訊")
    @GetMapping(value = "/historyScore")
    private Message getHistoryScore(String courseId, HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, judgeManager.getStudentHistoryScoreInfo(Long.parseLong(courseId), account));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_HISTORY_SCORE_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "課程下的學生所有題目資料",
            notes = "取得courseId、type、isJudge，來獲得課程下的學生所有題目資料")
    @GetMapping(value = "/problemInfo")
    private Message getProblemInfo(String courseId, String type, boolean isJudge, HttpSession session) {
        Message message;
        String account = getUserAccount(session);
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, problemManager.getStudentProblemInfo(Long.parseLong(courseId), type, isJudge, account));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_STUDENT_PROBLEM_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "課程下的學生對題目的難易度評分",
            notes = "取得problemId、rate，來對題目的難易度評分")
    @PostMapping(value = "/updateRate")
    private Message updateRate(@RequestBody Map<String, String> map, HttpSession session) {
        Message message;

        String problemId = map.get("problemId");
        String rate = map.get("rate");
        String account = getUserAccount(session);
        try {
            int code = judgeManager.updateJudgeRateByProblemIdAndAccount(Double.parseDouble(rate), Long.parseLong(problemId), account);
            if (code != -1) {
                message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
            }
            else {
                message = new Message(ApiMessageCode.UPDATE_STUDENT_PROBLEM_RATE_ERROR, "");
            }
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.UPDATE_STUDENT_PROBLEM_RATE_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "課程下的所有學生學號",
            notes = "取得courseId，來獲得課程下的所有學生學號")
    @GetMapping(value = "/allStud")
    private Message allStudent(String courseId) {
        Message message;
        message = new Message(ApiMessageCode.SUCCESS_STATUS, courseManager.findAllStudentAccountByCourseId(Long.parseLong(courseId)));
        return message;
    }

    @ApiOperation(value = "取得學號的學生資料",
            notes = "取得學號的學生資料")
    @PostMapping(value = "/accountInfo")
    private Message accountInfo(@RequestBody List<String> accounts) {
        Message message;
        message = new Message(ApiMessageCode.SUCCESS_STATUS, studentService.findByAccounts(accounts));
        return message;
    }
}
