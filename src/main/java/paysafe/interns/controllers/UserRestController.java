package paysafe.interns.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paysafe.interns.models.UserInfo;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserRestController extends BaseRestController{
    @RequestMapping("/me")
    public UserInfo myInfo(HttpServletRequest request) {
        return (UserInfo)request.getSession().getAttribute("user");
    }
}
