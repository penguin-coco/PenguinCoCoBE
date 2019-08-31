package com.penguin.penguincoco.api.functional;

import com.penguin.penguincoco.api.base.BaseApi;
import com.penguin.penguincoco.common.message.ApiMessageCode;
import com.penguin.penguincoco.common.message.Message;
import com.penguin.penguincoco.manager.CommonManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(value = "CommonApi", description = "登入、登出、檢查登入狀態")
@RequestMapping("/api")
@RestController
public class CommonApi extends BaseApi {


    private CommonManager commonManager;

    @Autowired
    public CommonApi(CommonManager commonManager) {
        this.commonManager = commonManager;
    }

//    @ApiOperation(value = "使用者登入",
//            notes = "回傳何種身分:student、teacher、assistant、admin")
//    @PostMapping(value = "/login")
//    private Message login(@RequestBody Map<String, String> map,
//                          HttpSession session) {
//        Message message;
//        String account = map.get("account");
//        String password = map.get("password");
//
//        // 回傳使用者的身分
//        String authority = "";
//        try {
//            authority = commonManager.findUserAuthority(account, password);
//            // 不是空值則登入成功
//            if (!authority.equals("")) {
//                setUserAccount(account, session);
//                setUserType(authority, session);
//                message = new Message(ApiMessageCode.SUCCESS_STATUS, authority);
//            }
//            else {
//                message = new Message(ApiMessageCode.LOGIN_ERROR, authority);
//            }
//        } catch (EntityNotFoundException e) {
//            e.printStackTrace();
//            message = new Message(ApiMessageCode.LOGIN_ERROR, authority);
//        }
//        return message;
//    }

//    @ApiOperation(value = "使用者登出",
//            notes = "將使用者登出，清除session")
//    @PostMapping(value = "/logout")
//    private Message logout(HttpSession session) {
//        Message message;
//        if (isLogin(session)) {
//            destroySession(session);
//            message = new Message(ApiMessageCode.SUCCESS_STATUS, "");
//        }
//        else {
//            message = new Message(ApiMessageCode.LOGOUT_ERROR, "");
//        }
//        return message;
//    }

    @ApiOperation(value = "檢查使用者登入狀態",
            notes = "檢查使用者的session是否還存在")
    @GetMapping(value = "/checkLogin")
    private ResponseEntity<Message> checkLogin(HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        Message message;
        String authority = "";
        ResponseEntity<Message> response;
        if (isLogin(session)) {
            authority = getUserType(session);
            map.put("status", true);
            map.put("authority", authority);
            message = new Message(ApiMessageCode.SUCCESS_STATUS, map);
            response = new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            map.put("status", false);
            map.put("authority", authority);
            message = new Message(ApiMessageCode.CHECK_LOGIN_ERROR, map);
            response = new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

}
