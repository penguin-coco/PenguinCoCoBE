package com.penguin.penguincoco.dao.repository;

import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.feedback.Feedback;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends BaseRepository<Feedback, Long> {

    List<Feedback> findByCourse(Course course);
}
