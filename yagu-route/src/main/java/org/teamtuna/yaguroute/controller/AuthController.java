package org.teamtuna.yaguroute.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.teamtuna.yaguroute.common.ResponseMessage;

@Controller
public class AuthController {
    @GetMapping("/login-success")
    public ResponseEntity<ResponseMessage> loginSuccess() {
        return ResponseEntity.ok().body(new ResponseMessage(200, "로그인 성공", null));
    }
}
