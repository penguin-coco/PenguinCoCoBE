package com.penguin.penguincoco.service;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.assistant.Assistant;
import com.penguin.penguincoco.dao.domain.assistant.AssistantInfo;
import com.penguin.penguincoco.service.base.BaseService;

import java.util.List;

public interface AssistantService extends BaseService<Assistant, Long> {

    boolean existByAccount(String account);

    Assistant findByAccount(String account) throws EntityNotFoundException;

    int updatePasswordByAccount(String account, String oriPassword, String newPassword);

    List<AssistantInfo> getAssistantListInfo();

    List<AssistantInfo> findAssistantInfoByCourseId(Long courseId);

    List<AssistantInfo> findunassignAssistantInfoByCourseId(Long courseId);
}
