package com.penguin.penguincoco.api.functional;


import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.api.base.BaseMethod;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.CourseManager;
import com.penguin.penguincoco.service.AssistantService;
import com.penguin.penguincoco.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Api(value = "TeacherApi", description = "教師的相關Api")
@RequestMapping("/api/teacher")
@RestController
public class TeacherApi extends BaseApi {

    private CourseManager courseManager;
    private StudentService studentService;
    private AssistantService assistantService;

    @Autowired
    public TeacherApi(CourseManager courseManager,
                      StudentService studentService,
                      AssistantService assistantService) {
        this.courseManager = courseManager;
        this.studentService = studentService;
        this.assistantService = assistantService;
    }

    @ApiOperation(value = "建立課程",
                  notes = "取得courseName、semester並建立課程")
    @PostMapping(value = "/createCourse")
    private Message createCourse(@RequestBody Map<String, Object> map,
                                 HttpSession session) {
        Message message;
        String account = getUserAccount(session);
        String courseName = map.get("courseName").toString();
        String semester = map.get("semester").toString();
        String studentClass = map.get("studentClass").toString();
        List<String> taList = (List<String>) map.get("taList");
        try {
            courseManager.createCourse(account, courseName, semester, studentClass, taList);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.CREATE_COURSE_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "刪除課程",
            notes = "取得courseId，並刪除課程")
    @PostMapping(value = "/deleteCourse")
    private Message deleteCourse(@RequestBody Map<String, String> map) {
        String courseId = map.get("courseId");
        Message message;
        try {
            courseManager.deleteCourseById(Long.parseLong(courseId));
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DELETE_COURSE_ERROR, "");
        }
        return message;
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

    @ApiOperation(value = "將助教列表加入課程",
            notes = "取得courseId、accountList，並加入課程")
    @PostMapping(value = "/addAssistantList")
    private Message addAssistantList(@RequestBody Map<String, Object> map) {
        Message message;
        String courseId = map.get("courseId").toString();
        List<String> accountList = (List<String>) map.get("accountList");
        try {
            courseManager.mapAssistantListToCourse(Long.parseLong(courseId), accountList);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            message = new Message(ApiMessageCode.MAP_ASSISTANTLIST_COURSE_ERROR, "");
            e.printStackTrace();
        }
        return message;
    }

    @ApiOperation(value = "將助教列表退出課程",
            notes = "取得courseId、accountList，並退出課程")
    @PostMapping(value = "/deleteAssistantList")
    private Message deleteAssistantList(@RequestBody Map<String, Object> map) {
        Message message;
        String courseId = map.get("courseId").toString();
        List<String> accountList = (List<String>) map.get("accountList");
        try {
            courseManager.deleteAssistantListFromCourse(Long.parseLong(courseId), accountList);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DELETE_ASSISTANTLIST_COURSE_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得老師的所有課程",
            notes = "取得account，來獲得老師的所有課程")
    @GetMapping(value = "/courseList")
    private Message getCourseList(HttpSession session) {
        Message message;

        String account = getUserAccount(session);
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, courseManager.getCoursesInfo(account));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_COURSE_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得所有學生班級列表",
            notes = "回傳所有學生班級列表")
    @GetMapping(value = "/studentClassList")
    private Message getStudentClassList() {
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, studentService.findDistinctStudentClass());
        } catch (Exception e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_STUDENT_CLASS_LIST_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得所有助教資訊",
            notes = "回傳所有助教資訊")
    @GetMapping(value = "/assistantList")
    private Message getAssistantList() {
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, assistantService.getAssistantListInfo());
        } catch (Exception e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_ASSISTANT_LIST_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "取得未被指派的助教名單",
            notes = "取得未被指派的助教名單")
    @GetMapping(value = "/unassignAssistantList")
    private Message unassignAssistantList(String courseId) {
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, assistantService.findunassignAssistantInfoByCourseId(Long.parseLong(courseId)));
        } catch (Exception e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_UNASSIGNASSISTANT_LIST_ERROR, "");
        }
        return message;
    }
}
