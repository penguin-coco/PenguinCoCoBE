package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.api.base.BaseMethod;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.CourseManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Api(value = "AssistantApi", description = "助教的相關Api")
@RequestMapping("/api/assistant")
@RestController
public class AssistantApi extends BaseApi {

    private CourseManager courseManager;

    @Autowired
    public AssistantApi(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @ApiOperation(value = "將學生列表加入課程",
            notes = "取得courseId、accountList，並加入課程")
    @PostMapping(value = "/addStudentList")
    private Message addStudentList(@RequestBody Map<String, Object> map) {
        return BaseMethod.addStudentList(map, courseManager);
    }

    @ApiOperation(value = "將學生列表退出課程",
            notes = "取得courseId、accountList，並退出課程")
    @PostMapping(value = "/deleteStudentList")
    private Message deleteStudentList(@RequestBody Map<String, Object> map) {
        return BaseMethod.deleteStudentList(map, courseManager);
    }

    @ApiOperation(value = "取得助教的所有課程",
            notes = "取得account，並取得助教的所有課程")
    @GetMapping(value = "/courseList")
    private Message courseList(HttpSession session) {
        String account = getUserAccount(session);
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, courseManager.getCoursesInfoByAssistantAccount(account));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.AS_GET_COURSE_LIST_ERROR, "");
        }
        return message;
    }
}
