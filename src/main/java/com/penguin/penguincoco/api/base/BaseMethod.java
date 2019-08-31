package com.penguin.penguincoco.api.base;

import com.penguin.penguincoco.common.exception.EntityExistsException;
import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.CourseManager;

import java.util.List;
import java.util.Map;

public class BaseMethod {

    public static Message addStudentList(Map<String, Object> map,
                                  CourseManager courseManager) {
        Message message;
        String courseId = map.get("courseId").toString();
        List<String> accountList = (List<String>) map.get("accountList");
        try {
            courseManager.mapStudentListToCourse(Long.parseLong(courseId), accountList);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.MAP_STUDENTLIST_COURSE_ERROR_1, "");
        } catch (EntityExistsException e) {
            message = new Message(ApiMessageCode.MAP_STUDENTLIST_COURSE_ERROR_2, "");
        }
        return message;
    }

    public static Message deleteStudentList(Map<String, Object> map,
                                            CourseManager courseManager) {
        Message message;
        String courseId = map.get("courseId").toString();
        List<String> accountList = (List<String>) map.get("accountList");
        try {
            courseManager.deleteStudentListFromCourse(Long.parseLong(courseId), accountList);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DELETE_STUDENTLIST_COURSE_ERROR, "");
        }
        return message;
    }
}
