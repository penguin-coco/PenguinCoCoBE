package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.dao.domain.problembank.ProblemBank;
import com.penguin.penguincoco.service.ProblemBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(value = "ProblemBankApi", description = "題庫的相關Api")
@RequestMapping("/api/problemBank")
@RestController
public class ProblemBankApi {

    private ProblemBankService problemBankService;

    @Autowired
    public ProblemBankApi(ProblemBankService problemBankService) {
        this.problemBankService = problemBankService;
    }

    @ApiOperation(value = "在題庫中建立題目",
            notes = "取得題目資訊，並建立題目")
    @PostMapping(value = "/addProblem")
    private Message addProblem(@RequestBody Map<String, Object> map) {
        Message message;
        String name = map.get("name").toString();
        String category = map.get("category").toString();
        List<String> tagList = (List<String>) map.get("tag");
        String[] tag = tagList.toArray(new String[tagList.size()]);
        String description = map.get("description").toString();
        String inputDesc = map.get("inputDesc").toString();
        String outputDesc = map.get("outputDesc").toString();
        List<TestCase> testCases = (List<TestCase>) map.get("testCases");

        ProblemBank problemBank = new ProblemBank(name, category, tag, description, inputDesc, outputDesc, testCases);
        problemBankService.save(problemBank);
        message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        return message;
    }

    @ApiOperation(value = "在題庫中編輯題目",
            notes = "在已存在的題目上進行編輯")
    @PostMapping(value = "/editProblem")
    private Message editProblem(@RequestBody Map<String, Object> map) {
        Message message;
        Long problemBankId = Long.parseLong(map.get("id").toString());
        String name = map.get("name").toString();
        String category = map.get("category").toString();
        List<String> tagList = (List<String>) map.get("tag");
        String[] tag = tagList.toArray(new String[tagList.size()]);
        String description = map.get("description").toString();
        String inputDesc = map.get("inputDesc").toString();
        String outputDesc = map.get("outputDesc").toString();
        List<TestCase> testCases = (List<TestCase>) map.get("testCases");
        try {
            problemBankService.update(problemBankId, name, category, tag, description, inputDesc, outputDesc, testCases);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.EDIT_PROBLEMBANK_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "在題庫中取得所有題目",
            notes = "取得所有題目資訊")
    @GetMapping(value = "/getAllProblem")
    private Message getAllProblem() {
        Message message;
        message = new Message(ApiMessageCode.SUCCESS_STATUS, problemBankService.getAllProblem());
        return message;
    }

    @ApiOperation(value = "在題庫中取得題目詳細資訊",
            notes = "取得題目詳細資訊")
    @GetMapping(value = "/getProblemInfo")
    private Message getProblemInfo(String id) {
        Message message;
        try {
            message = new Message(ApiMessageCode.SUCCESS_STATUS, problemBankService.findById(Long.parseLong(id)));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.GET_PROBLEMBANK_INFO_ERROR, "");
        }
        return message;
    }

    @ApiOperation(value = "在題庫中刪除題目",
            notes = "刪除題目")
    @PostMapping(value = "/deleteProblem")
    private Message deleteProblem(@RequestBody Map<String, String> map) {
        Message message;
        Long problemBankId = Long.parseLong(map.get("id"));
        try {
            problemBankService.delete(problemBankId);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            message = new Message(ApiMessageCode.DELETE_PROBLEMBANK_ERROR, "");
        }
        return message;
    }
}
