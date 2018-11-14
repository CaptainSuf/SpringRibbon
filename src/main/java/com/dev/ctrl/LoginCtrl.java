package com.dev.ctrl;

import com.dev.model.TbUserLoginPO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping(path = "/ribbon")
public class LoginCtrl {

    @Autowired
    RestTemplate restTemplate;

    @ResponseBody
    @RequestMapping(value = "/login.do")
    @HystrixCommand(fallbackMethod = "loginError")
    public String login(TbUserLoginPO user){
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://springboot-server-1/login.do?userName={1}&password={2}",null,String.class,user.getUserName(),user.getPassword());
        return responseEntity.getBody();
    }

    public String loginError(TbUserLoginPO user){
        return "error";
    }

}
