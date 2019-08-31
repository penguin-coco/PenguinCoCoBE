package com.penguin.penguincoco.service;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.feedback.Feedback;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;

public interface FeedbackService extends BaseService<Feedback, Long> {

    List<Feedback> findByCourse(Course course);
}
