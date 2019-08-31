package com.penguin.penguincoco.common.message;

public enum ApiMessageCode {

    SUCCESS_STATUS("200", "請求成功"),
    // CommonApi
    LOGIN_ERROR("404", "登入失敗"),
    LOGOUT_ERROR("404", "登出失敗"),
    CHECK_LOGIN_ERROR("401", "檢查登入失敗"),
    CHANGE_PASSWORD_ERROR("404", "更改密碼失敗"),
    NOT_LOGIN_ERROR("401", "請先登入才能進行此操作"),
    // StudentApi
    GET_COURSE_LIST_ERROR("404", "取得課程列表失敗"),
    GET_INFO_ERROR("404", "取得學生資訊失敗"),
    GET_HISTORY_SCORE_ERROR("404", "取得歷史成績失敗"),
    GET_STUDENT_PROBLEM_INFO_ERROR("404", "取得題目資訊失敗"),
    UPDATE_STUDENT_PROBLEM_RATE_ERROR("404", "更新題目的難易度失敗"),
    // AssistantApi
    AS_GET_COURSE_LIST_ERROR("404", "取得助教所有課程失敗"),
    // TeacherApi
    CREATE_COURSE_ERROR("404", "創建課程失敗"),
    DELETE_COURSE_ERROR("404", "刪除課程失敗"),
    MAP_STUDENTLIST_COURSE_ERROR_1("404", "找不到此學號的學生！"),
    MAP_STUDENTLIST_COURSE_ERROR_2("404", "新增學生錯誤，可能有已加入的學生！"),
    DELETE_STUDENTLIST_COURSE_ERROR("404", "學生列表退出課程失敗"),
    MAP_ASSISTANTLIST_COURSE_ERROR("404", "助教列表配對課程失敗"),
    DELETE_ASSISTANTLIST_COURSE_ERROR("404", "助教列表退出課程失敗"),
    GET_COURSE_INFO_ERROR("404", "取得課程列表失敗"),
    GET_STUDENT_CLASS_LIST_ERROR("404", "取得學生班級列表失敗"),
    GET_ASSISTANT_LIST_ERROR("404", "取得助教名單失敗"),
    GET_UNASSIGNASSISTANT_LIST_ERROR("404", "取得未被指派助教名單失敗"),
    // ProblemApi
    GET_PROBLEM_INFO_ERROR("404", "取得題目資訊失敗"),
    GET_PROBLEMS_INFO_ERROR("404", "取得課程下的所有題目資訊失敗"),
    ADD_PROBLEM_ERROR("404", "新增題目失敗"),
    EDIT_PROBLEM_ERROR("404", "編輯題目失敗"),
    DELETE_PROBLEM_ERROR("404", "刪除題目失敗"),
    // JudgeApi
    JUDGE_CODE_ERROR("404", "批改代碼失敗"),
    GET_JUDGED_INFO_ERROR("404", "取得已批改資訊失敗"),
    CHECK_JUDGE_ERROR("404", "檢查此題是否被批改失敗"),
    JUDGE_COPY_ERROR("404", "批改抄襲失敗"),
    // CourseApi
    GET_COURSES_INFO_ERROR("404", "取得課程資訊失敗"),
    GET_STUDENT_DATA_ERROR("404", "取得課程的所有學生成績失敗"),
    DEL_COURSE_ERROR("404", "刪除課程失敗"),
    EDIT_COURSE_ERROR("404", "編輯課程失敗"),
    ALL_STUD_ERROR("404", "取得課程下的所有學生學號失敗"),
    // FeedbackApi
    ADD_FEEDBACK_ERROR("404", "新增回饋失敗"),
    GET_COURSE_FEEDBACK_ERROR("404", "取得課程下的所有回饋失敗"),
    // RankApi
    GET_CORRECT_RANK_ERROR("404", "取得正確解題學生排行失敗"),
    GET_BEST_CODE_RANK_ERROR("404", "取得最佳解答學生排行失敗"),
    // ProblemBankApi
    ADD_PROBLEMBANK_ERROR("404", "新增題目失敗"),
    DELETE_PROBLEMBANK_ERROR("404", "刪除題目失敗"),
    EDIT_PROBLEMBANK_ERROR("404", "編輯題目失敗"),
    GET_PROBLEMBANK_INFO_ERROR("404", "取得題目資訊失敗"),
    // TeamApi
    CREATE_TEAM_ERROR("404", "創建隊伍失敗"),
    CORRECT_STUDS_ERROR("404", "取得學生要批改的對象失敗"),
    CHECK_CORRECT_STATUS_ERROR("404", "取得此學生是否已經完成互評失敗"),
    CHECK_CORRECTED_STATUS_ERROR("404", "取得此學生是否已經被互評完成失敗"),
    CORRECT_INFO_ERROR("404", "取得此學生批改對方的資訊失敗"),
    CORRECTED_INFO_ERROR("404", "取得此學生被批改的資訊失敗"),
    SUBMIT_CORRECT_ERROR("404", "送出評分資訊失敗"),
    DISCUSS_SCORE_ERROR("404", "取得互評成績失敗"),
    // Not authorized
    NOT_AUTHORIZED("403", "你的權限不足，無法進行該存取");

    private String code;
    private String desc;

    ApiMessageCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
