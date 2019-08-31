package com.penguin.penguincoco.manager;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;

import java.util.List;
import java.util.Map;

public interface ProblemManager {

    List<Map<String, Object>> getStudentProblemInfo(Long courseId, String type, boolean isJudge, String account) throws EntityNotFoundException;

    List<Map<String, Object>> getStudentsData(Long courseId) throws EntityNotFoundException;
}
