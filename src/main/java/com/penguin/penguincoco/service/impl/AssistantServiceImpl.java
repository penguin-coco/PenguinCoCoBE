package com.penguin.penguincoco.service.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.assistant.Assistant;
import com.penguin.penguincoco.dao.domain.assistant.AssistantInfo;
import com.penguin.penguincoco.dao.repository.AssistantRepository;
import com.penguin.penguincoco.dao.repository.base.BaseRepository;
import com.penguin.penguincoco.service.AssistantService;
import com.penguin.penguincoco.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssistantServiceImpl extends BaseServiceImpl<Assistant, Long> implements AssistantService {

    private AssistantRepository assistantRepository;

    @Autowired
    public AssistantServiceImpl(AssistantRepository assistantRepository) {
        this.assistantRepository = assistantRepository;
    }

    @Override
    public BaseRepository<Assistant, Long> getBaseRepository() {
        return assistantRepository;
    }

    @Override
    public boolean existByAccount(String account) {
        return assistantRepository.existsByAccount(account);
    }

    @Override
    public Assistant findByAccount(String account) throws EntityNotFoundException {
        return assistantRepository.findByAccount(account).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public int updatePasswordByAccount(String account, String oriPassword, String newPassword) {
        return assistantRepository.updatePasswordByAccountAndPassword(account, oriPassword, newPassword);
    }

    @Override
    public List<AssistantInfo> getAssistantListInfo() {
        List<Assistant> assistants = assistantRepository.findAll();
        List<AssistantInfo> assistantInfos = new ArrayList<>();
        for (Assistant assistant : assistants) {
            AssistantInfo assistantInfo = new AssistantInfo(assistant.getAccount(), assistant.getName());
            assistantInfos.add(assistantInfo);
        }
        return assistantInfos;
    }

    @Override
    public List<AssistantInfo> findAssistantInfoByCourseId(Long courseId) {
        return assistantRepository.findAssistantInfoByCourseId(courseId);
    }

    @Override
    public List<AssistantInfo> findunassignAssistantInfoByCourseId(Long courseId) {
        return assistantRepository.findunassignAssistantInfoByCourseId(courseId);
    }
}
