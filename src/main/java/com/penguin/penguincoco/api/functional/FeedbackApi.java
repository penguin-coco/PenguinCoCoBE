package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.CourseManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Api(value = "FeedbackApi", description = "回饋的相關Api")
@RequestMapping("/api/feedback")
@RestController
public class FeedbackApi extends BaseApi {

    private CourseManager courseManager;

    @Autowired
    public FeedbackApi(CourseManager courseManager) {
        this.courseManager = courseManager;
    }

    @ApiOperation(value = "新增回饋",
            notes = "取得courseId、content，並新增回饋")
    @PostMapping(value = "/addFeedback")
    private Message addFeedback(@RequestBody Map<String, String> map, HttpSession session) {
        Message message;

        String courseId = map.get("courseId");
        String content = map.get("content");
        String account = getUserAccount(session);
        try {
            courseManager.addFeedback(Long.parseLong(courseId), account, content);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (ParseException | EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.ADD_FEEDBACK_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得課程下的所有回饋",
            notes = "取得courseId，並獲取課程下的所有回饋")
    @GetMapping(value = "/getCourseFeedbacks")
    private Message getCourseFeedbacks(String courseId) {
        Message message;
        try {
            List<Map<String, String>> result = courseManager.findFeedbacksByCourseId(Long.parseLong(courseId));
            message = new Message(ApiMessageCode.SUCCESS_STATUS, result);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_COURSE_FEEDBACK_ERROR, "");
        }
        return message;
    }
}
