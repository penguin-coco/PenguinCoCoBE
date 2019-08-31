package com.penguin.penguincoco.manager.impl;

import com.penguin.penguincoco.common.exception.EntityNotFoundException;
import com.penguin.penguincoco.dao.domain.copy.Copy;
import com.penguin.penguincoco.dao.domain.course.Course;
import com.penguin.penguincoco.dao.domain.judge.HistoryCode;
import com.penguin.penguincoco.dao.domain.judge.Judge;
import com.penguin.penguincoco.dao.domain.problem.Problem;
import com.penguin.penguincoco.dao.domain.problem.TestCase;
import com.penguin.penguincoco.dao.domain.student.BestCodeRank;
import com.penguin.penguincoco.dao.domain.student.CorrectRank;
import com.penguin.penguincoco.dao.domain.student.Student;
import com.penguin.penguincoco.lib.judge.Judger;
import com.penguin.penguincoco.lib.judge.JudgerFactory;
import com.penguin.penguincoco.lib.model.*;
import com.penguin.penguincoco.manager.JudgeManager;
import com.penguin.penguincoco.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class JudgeManagerImpl implements JudgeManager {

    private JudgeService judgeService;
    private ProblemService problemService;
    private StudentService studentService;
    private CopyService copyService;
    private CourseService courseService;

    @Autowired
    public JudgeManagerImpl(JudgeService judgeService,
                            ProblemService problemService,
                            StudentService studentService,
                            CopyService copyService,
                            CourseService courseService) {
        this.judgeService = judgeService;
        this.problemService = problemService;
        this.studentService = studentService;
        this.copyService = copyService;
        this.courseService = courseService;
    }

    // 取得題目Id、學生送出代碼等的相關資訊，進行自動化批改
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void judgeCode(Long problemId, String code, String language, String account) throws EntityNotFoundException {
        Problem problem = problemService.findById(problemId);
        Student student = studentService.findByAccount(account);
        // 取得輸入輸出範本
        List<TestCase> testCases = problem.getTestCases();
        List<ProblemCase> problemCases = new ArrayList<>();
        for (TestCase testCase : testCases) {
            String inputSample = testCase.getInputSample();
            String outputSample = testCase.getOutputSample();
            ProblemCase problemCase = new ProblemCase(inputSample, outputSample);
            problemCases.add(problemCase);
        }
        // 選擇程式語言
        Language programLanguage = chooseLanguage(language);
        // 選擇題目的批改方式
        String category = problem.getCategory();
        JudgeBehavior judgeBehavior = chooseBehavior(category);

        // 建立批改所需的data、judger進行批改，並取得report
        JudgeData judgeData = new JudgeData(account, code, problemCases, judgeBehavior);
        Judger judger = JudgerFactory.createJudger(programLanguage, judgeData);
        assert judger != null;
        JudgeReport report = judger.performJudge();
        double avgRunTime = report.getAverageJudgeTimeForRoundOff2nd();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String handDate = df.format(new Date());
        double avgScore = report.getAverageScore();
        double correctRate = 0;
        int correctNum = problem.getCorrectNum();
        int incorrectNum = problem.getIncorrectNum();

        // 確認是否是第一次judge，如果是，則新增一筆Judge to db，否則拿取舊的judge
        boolean isNotFirstJudge = judgeService.existByProblemAndStudent(problem, student);
        List<HistoryCode> historyCodes;
        Judge judge;
        double oriScore = 0;
        if (!isNotFirstJudge) {
            historyCodes = new ArrayList<>();
            judge = new Judge(problem, student, 0, historyCodes);
        }
        else {
            judge = judgeService.findByProblemAndStudent(problem, student);
            historyCodes = judge.getHistoryCodes();
            oriScore = judge.getHistoryCodes().get(judge.getHistoryCodes().size() - 1).getScore();
        }

        // 取得批改後的相關資訊，並加入到歷史代碼中
        List<String> output = new ArrayList<>();
        List<String> symbol = new ArrayList<>();
        List<String> errorMessage = new ArrayList<>();
        for (JudgeProblemResult problemResult : report.getResults()) {
            output.add(problemResult.getOutput());
            symbol.add(problemResult.getSymbol().getDescription());
            errorMessage.add(problemResult.getMessage());
        }
        HistoryCode historyCode = new HistoryCode(handDate, code, avgRunTime,
                output, symbol, errorMessage, avgScore);
        historyCodes.add(historyCode);
        judge.setHistoryCodes(historyCodes);

        // 更改題目的答對答錯率
        if (avgScore == 100) {
            if (!isNotFirstJudge) {
                correctNum++;
            }
            else {
                if (oriScore < 100) {
                    correctNum++;
                    incorrectNum--;
                }
            }
            // 比對最佳代碼是否需要更換，兩種情況:還沒有最佳代碼、已經有最佳代碼
            if (!problem.getBestStudentAccount().equals("")) {
                Student bestStudent = studentService.findByAccount(problem.getBestStudentAccount());
                Judge bestJudge = judgeService.findByProblemAndStudent(problem, bestStudent);
                double bestAvgRunTime = bestJudge.getHistoryCodes().get(bestJudge.getHistoryCodes().size() - 1).getRunTime();
                if (bestAvgRunTime > avgRunTime) {
                    problem.setBestStudentAccount(account);
                }
            }
            else {
                problem.setBestStudentAccount(account);
            }
        }
        else {
            if (!isNotFirstJudge) {
                incorrectNum++;
            }
            else {
                if (oriScore == 100) {
                    correctNum--;
                    incorrectNum++;
                }
            }
            // 比對最佳代碼是否需要更換，自己是最佳代碼的持有者的情況
            if (!problem.getBestStudentAccount().equals("")) {
                if (problem.getBestStudentAccount().equals(account)) {
                    List<Judge> judges = judgeService.findByProblem(problem);
                    double bestRunTime = Double.MAX_VALUE;
                    String anotherBestAccount = "";
                    for (Judge tempJudge : judges) {
                        // 找出下一位最佳代碼持有者
                        if (!tempJudge.getStudent().getAccount().equals(problem.getBestStudentAccount())
                                && tempJudge.getHistoryCodes().get(tempJudge.getHistoryCodes().size() - 1).getScore() == 100) {
                            double tempRunTime = tempJudge.getHistoryCodes().get(tempJudge.getHistoryCodes().size() - 1).getRunTime();
                            if (tempRunTime < bestRunTime) {
                                bestRunTime = tempRunTime;
                                anotherBestAccount = tempJudge.getStudent().getAccount();
                            }
                        }
                    }
                    problem.setBestStudentAccount(anotherBestAccount);
                }
            }
        }
        correctRate = (double) correctNum / (correctNum + incorrectNum);
        // 設定答對數、答錯數、答對率
        problem.setCorrectNum(correctNum);
        problem.setIncorrectNum(incorrectNum);
        problem.setCorrectRate(correctRate);
        // 儲存Problem、Judge，更新後的資訊
        problemService.save(problem);
        judgeService.save(judge);
    }

    // 利用題目Id、學生account取得學生座學生做該題的judge資訊
    @Override
    public Judge findByProblemIdAndStudentAccount(Long problemId, String account) throws EntityNotFoundException {
        Problem problem = problemService.findById(problemId);
        Student student = studentService.findByAccount(account);
        return judgeService.findByProblemAndStudent(problem, student);
    }

    // 利用題目Id、學生account來確認是否做過此題目，如果沒有不存在judge資訊
    @Override
    public boolean existByProblemIdAndStudentAccount(Long problemId, String account) throws EntityNotFoundException {
        Problem problem = problemService.findById(problemId);
        Student student = studentService.findByAccount(account);
        return judgeService.existByProblemAndStudent(problem, student);
    }

    // 利用題目Id，對該題進行抄襲計算，取得每個做過該題的學生的代碼進行計算
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void judgeCopy(Long problemId) throws EntityNotFoundException {
        Problem problem = problemService.findById(problemId);
        Language language = chooseLanguage(problem.getTag()[0]);
        List<Judge> judges = judgeService.findByProblem(problem);
        int number = judges.size();
        List<Copy> copies = new ArrayList<>();
        // 每一位學生的代碼與其他所有學生進行比對，相似度>0.9的話，則視為抄襲
        for (int i = 0; i < number; i++) {
            Judge sourceJudge = judges.get(i);
            String sourceAccount = sourceJudge.getStudent().getAccount();
            String sourceCode = sourceJudge.getHistoryCodes().get(sourceJudge.getHistoryCodes().size() - 1).getCode();

            for (Judge destJudge : judges) {
                if (!sourceJudge.getStudent().getAccount().equals(destJudge.getStudent().getAccount())) {
                    String destAccount = destJudge.getStudent().getAccount();
                    String destCode = destJudge.getHistoryCodes().get(destJudge.getHistoryCodes().size() - 1).getCode();

                    CodeSimilarity codeSimilarity = new CodeSimilarity(language, new LCSAlgorithm());
                    double similarity = codeSimilarity.get(sourceCode, destCode);
                    if (similarity >= 0.9) {
                        Copy copy = new Copy(problem, sourceAccount, destAccount, similarity);
                        copies.add(copy);
                    }
                }
            }
        }
        copyService.saveAll(copies);
    }

    // 更新學生對於已做答的題目的評分
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateJudgeRateByProblemIdAndAccount(double rate, Long problemId, String account) throws EntityNotFoundException {
        Problem problem = problemService.findById(problemId);
        Student student = studentService.findByAccount(account);
        int code = judgeService.updateRateByProblemAndStudent(rate, problem, student);
        if (code == 1) {
            double avgRate = judgeService.getAvgRateByProblem(problem);
            problemService.updateRateByProblemId(problemId, avgRate);
            return code;
        }
        else {
            return -1;
        }
    }

    // 利用課程id、學生account取得曾作答的成績資訊
    @Override
    public List<Map<String, Object>> getStudentHistoryScoreInfo(Long courseId, String account) throws EntityNotFoundException {
        Course course = courseService.findById(courseId);
        List<Problem> problems = problemService.findByCourse(course);
        Student student = studentService.findByAccount(account);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Problem problem : problems) {
            Map<String, Object> problemResult = new HashMap<>();
            boolean isJudge = judgeService.existByProblemAndStudent(problem, student);
            if (isJudge) {
                Judge judge = judgeService.findByProblemAndStudent(problem, student);
                String problemId = String.valueOf(problem.getId());
                String problemName = problem.getName();
                String type = problem.getType();
                List<HistoryCode> historyCode = judge.getHistoryCodes();
                String rate = String.valueOf(problem.getRate());
                String correctRate = String.valueOf(problem.getCorrectRate() * 100);
                boolean isBestCode = false;
                if (problem.getBestStudentAccount().equals(account)) {
                    isBestCode = true;
                }
                problemResult.put("problemId", problemId);
                problemResult.put("problemName", problemName);
                problemResult.put("type", type);
                problemResult.put("historyCode", historyCode);
                problemResult.put("rate", rate);
                problemResult.put("correctRate", correctRate);
                problemResult.put("isBestCode", isBestCode);

                List<Copy> copies = copyService.findByProblemAndStudentTwoAccount(problem, account);
                List<Map<String, String>> copyResultList = new ArrayList<>();
                for (Copy copy : copies) {
                    Map<String, String> copyResult = new HashMap<>();
                    copyResult.put("account", copy.getStudentOneAccount());
                    copyResult.put("similarity", String.valueOf(copy.getSimilarity()));
                    copyResultList.add(copyResult);
                }
                problemResult.put("copys", copyResultList);
                result.add(problemResult);
            }
        }
        return result;
    }

    // 利用課程id，取得所有judge，並排序出學生正確解題的順序
    @Override
    public List<CorrectRank> getCorrectRank(Long courseId) throws EntityNotFoundException {
        Course course = courseService.findById(courseId);
        List<Student> students = course.getStudents();

        Map<Student, Integer> original = new HashMap<>();
        for (Student student : students) {
            List<Judge> judges = judgeService.findByCourseAndStudent(course, student);
            int count = 0;
            for (Judge judge : judges) {
                int lastIndex = judge.getHistoryCodes().size() - 1;
                if (judge.getHistoryCodes().get(lastIndex).getScore() == 100) {
                    count++;
                }
            }
            original.put(student, count);
        }
        List<CorrectRank> sorted = original.entrySet().stream()
                .sorted(Map.Entry.<Student, Integer>
                        comparingByValue().reversed())
                .filter(e -> e.getValue() != 0)
                .limit(5)
                .map(e -> new CorrectRank(
                        e.getKey().getAccount(),
                        e.getKey().getName(),
                        String.valueOf(e.getValue()),
                        "0"))
                .collect(toList());

        for (int i = 0; i < sorted.size(); i++) {
            if (i == 0) {
                CorrectRank CorrectRank = sorted.get(i);
                CorrectRank.setRank("1");
            }
            else {
                CorrectRank CorrectRank = sorted.get(i);
                if (CorrectRank.getCorrectNum().equals(sorted.get(i - 1).getCorrectNum())) {
                    CorrectRank.setRank(sorted.get(i - 1).getRank());
                }
                else {
                    CorrectRank.setRank(String.valueOf(i + 1));
                }
            }
        }
        return sorted;
    }

    // 利用課程id，取得所有judge，並排序出最佳解學生的順序
    @Override
    public List<BestCodeRank> getBestCodeRank(Long courseId) throws EntityNotFoundException {
        Course course = courseService.findById(courseId);
        List<Student> students = course.getStudents();

        Map<Student, Integer> original = new HashMap<>();
        for (Student student : students) {
            int count = problemService.countByBestStudentAccountAndCourse(student.getAccount(), course);
            original.put(student, count);
        }

        List<BestCodeRank> sorted = original.entrySet().stream()
                .sorted(Map.Entry.<Student, Integer>
                        comparingByValue().reversed())
                .filter(e -> e.getValue() != 0)
                .limit(5)
                .map(e -> new BestCodeRank(
                        e.getKey().getAccount(),
                        e.getKey().getName(),
                        String.valueOf(e.getValue()),
                        "0"))
                .collect(toList());

        for (int i = 0; i < sorted.size(); i++) {
            if (i == 0) {
                BestCodeRank bestCodeRank = sorted.get(i);
                bestCodeRank.setRank("1");
            }
            else {
                BestCodeRank bestCodeRank = sorted.get(i);
                if (bestCodeRank.getBestCodeNum().equals(sorted.get(i - 1).getBestCodeNum())) {
                    bestCodeRank.setRank(sorted.get(i - 1).getRank());
                }
                else {
                    bestCodeRank.setRank(String.valueOf(i + 1));
                }
            }
        }
        return sorted;
    }

    // 利用課程Id，取得課程下的所有題目相關資訊
    @Override
    public List<Map<String, Object>> getProblems(Long courseId) throws EntityNotFoundException {
        Course course = courseService.findById(courseId);
        List<Problem> problems = course.getProblems();
        List<Student> students = course.getStudents();

        List<Map<String, Object>> result = new ArrayList<>();
        for (Problem problem : problems) {
            Map<String, Object> problemResult = new HashMap<>();
            String problemId = String.valueOf(problem.getId());
            String name = problem.getName();
            String type = problem.getType();
            String deadline = new SimpleDateFormat("yyyy-MM-dd").format(problem.getDeadline());
            String category = problem.getCategory();
            String[] tag = problem.getTag();
            String[] pattern = problem.getPattern();
            String status = decideDeadlineStatus(problem.getDeadline());
            int doneStudentNum = judgeService.countByProblem(problem);
            int undoStudentNum = students.size() - doneStudentNum;
            String rate = String.valueOf(problem.getRate());
            String correctRate = String.valueOf(problem.getCorrectRate());
            String bestStudentId = problem.getBestStudentAccount();
            String bestStudentName = "";
            String bestRunTime = "";
            if (!bestStudentId.equals("")) {
                Student student = studentService.findByAccount(bestStudentId);
                Judge judge = judgeService.findByProblemAndStudent(problem, student);
                bestStudentName = student.getName();
                bestRunTime = String.valueOf(judge.getHistoryCodes().get(judge.getHistoryCodes().size() - 1).getRunTime());
            }
            problemResult.put("deadline", deadline);
            problemResult.put("problemId", problemId);
            problemResult.put("name", name);
            problemResult.put("type", type);
            problemResult.put("category", category);
            problemResult.put("tag", tag);
            problemResult.put("pattern", pattern);
            problemResult.put("status", status);
            problemResult.put("undoStudentNum", undoStudentNum);
            problemResult.put("doneStudentNum", doneStudentNum);
            problemResult.put("rate", rate);
            problemResult.put("correctRate", correctRate);
            problemResult.put("bestStudentName", bestStudentName);
            problemResult.put("bestRunTime", bestRunTime);

            List<Copy> copies = copyService.findByProblem(problem);
            List<Map<String, String>> copyResultList = new ArrayList<>();
            for (Copy copy : copies) {
                Map<String, String> copyResult = new HashMap<>();
                String studentOneId = copy.getStudentOneAccount();
                String studentOneName = studentService.findByAccount(studentOneId).getName();
                String studentTwoId = copy.getStudentTwoAccount();
                String studentTwoName = studentService.findByAccount(studentTwoId).getName();
                String similarity = String.valueOf(copy.getSimilarity());
                copyResult.put("studentOneId", studentOneId);
                copyResult.put("studentOneName", studentOneName);
                copyResult.put("studentTwoId", studentTwoId);
                copyResult.put("studentTwoName", studentTwoName);
                copyResult.put("similarity", similarity);
                copyResultList.add(copyResult);
            }
            problemResult.put("detectCopyResult", copyResultList);
            result.add(problemResult);
        }
        return result;
    }

    @Override
    public Map<String, Object> getJudgeInfo(Long problemId, String account) throws EntityNotFoundException {
        Problem problem = problemService.findById(problemId);
        Student student = studentService.findByAccount(account);
        Judge judge = judgeService.findByProblemAndStudent(problem, student);

        List<HistoryCode> historyCodes = judge.getHistoryCodes();
        int lastIndex = historyCodes.size() - 1;
        HistoryCode lastHistoryCode = historyCodes.get(lastIndex);

        Map<String, Object> result = new HashMap<>();

        String headDate = lastHistoryCode.getHandDate();
        String score = String.valueOf(lastHistoryCode.getScore());
        String runTime = String.valueOf(lastHistoryCode.getRunTime());
        String code = lastHistoryCode.getCode();
        List<String> symbol = lastHistoryCode.getSymbol();
        List<String> errorInfo = lastHistoryCode.getErrorMessage();
        List<String> output = lastHistoryCode.getOutput();

        boolean isBest = false;
        if (problem.getBestStudentAccount().equals(account)) {
            isBest = true;
        }
        result.put("handDate", headDate);
        result.put("score", score);
        result.put("runTime", runTime);
        result.put("code", code);
        result.put("symbol", symbol);
        result.put("errorInfo", errorInfo);
        result.put("output", output);
        result.put("best", isBest);

        List<Copy> copies = copyService.findByProblemAndStudentTwoAccount(problem, account);
        List<Map<String, String>> copyResultList = new ArrayList<>();
        for (Copy copy : copies) {
            Map<String, String> copyResult = new HashMap<>();
            String anotherStudentId = copy.getStudentOneAccount();
            String similarity = String.valueOf(copy.getSimilarity());
            copyResult.put("anotherStudentId", anotherStudentId);
            copyResult.put("similarity", similarity);
            copyResultList.add(copyResult);
        }
        result.put("copyResult", copyResultList);

        return result;
    }

    private Language chooseLanguage(String language) {
        switch (language) {
            case "Java":
                return Language.JAVA;
            case "Python":
                return Language.PYTHON;
            default:
                return null;
        }
    }

    private JudgeBehavior chooseBehavior(String category) {
        switch (category) {
            case "輸入輸出":
                return JudgeBehavior.ReadAndPrint;
            case "輸入寫檔":
                return JudgeBehavior.ReadAndWriteFile;
            case "讀檔輸出":
                return JudgeBehavior.ReadFileAndPrint;
            case "讀檔寫檔":
                return JudgeBehavior.ReadFileAndWriteFile;
            default:
                return null;
        }
    }

    private String decideDeadlineStatus(Date date) {
        Calendar now = Calendar.getInstance();
        Calendar deadline = Calendar.getInstance();
        deadline.setTime(date);
        // 避免今天日期跟截止日期同一天算作逾期的問題
        deadline.add(Calendar.DATE, 1);

        if (now.before(deadline)) {
            return "可作答";
        }
        else {
            return "已關閉";
        }
    }
}
