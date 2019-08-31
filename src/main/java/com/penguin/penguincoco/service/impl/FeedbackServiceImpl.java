package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.feedback.Feedback;
import com.penguin.penguincoco.dao.repository.FeedbackRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.FeedbackService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl extends BaseServiceImpl<Feedback, Long> implements FeedbackService {

    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public BaseRepository<Feedback, Long> getBaseRepository() {
        return feedbackRepository;
    }

    @Override
    public List<Feedback> findByCourse(Course course) {
        return feedbackRepository.findByCourse(course);
    }

}
