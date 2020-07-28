package com.dz.blogserver.controller;

import com.dz.blogserver.util.CommonUtils;
import com.dz.blogserver.vo.basic.LoginVO;
import com.dz.blogserver.vo.result.ResultEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author dz
 * @date 2020/7/6
 * @time 10:31
 */
@RestController
public class BasicController {
    @PostMapping(value = "/sayHello")
    public ResultEntity sayHello() {
        ResultEntity resultEntity = new ResultEntity();
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        resultEntity.success();
        resultEntity.setResult(formatter.format(localDate));

        return resultEntity;
    }

    @PostMapping("/login")
    public ResultEntity login(@Valid @RequestBody LoginVO loginVO, BindingResult bindingResult) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            if (bindingResult.hasErrors()) {
                return CommonUtils.invalidData2ResultEntity(resultEntity, bindingResult);
            } else {
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(loginVO.getUserAccount(), loginVO.getPassword());
                try {
                    subject.login(token);
                } catch (AuthenticationException e) {
                    resultEntity.failed("登录失败,用户名或密码错误");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.error();
        }

        return resultEntity;
    }

    @PostMapping("/postStream")
    public void postStream(HttpServletRequest request) throws IOException {
        System.out.println("调用");
        InputStream is = request.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count = -1;
        while ((count = is.read(data, 0, 1024)) != -1)
            outStream.write(data, 0, count);

        data = null;
        System.out.println(new String(outStream.toByteArray()));
    }

    @GetMapping("/checkSession")
    public void checkSession(HttpServletRequest request, HttpServletResponse response) {

    }
}
