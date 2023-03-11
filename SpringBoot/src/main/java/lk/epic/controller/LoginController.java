package lk.epic.controller;

import lk.epic.dto.loginDTO;
import lk.epic.service.CustomerService;
import lk.epic.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
@CrossOrigin
public class LoginController {

    @Autowired
    CustomerService customerService;


    @PostMapping
    public ResponseUtil checkLoginVerification(@RequestBody loginDTO login){
        String userName = login.getUserName();
        String userPassword = login.getUserPassword();
        if (customerService.checkLogin(userName,userPassword)) {
            return new ResponseUtil(200,"true",null);
        }else {
            return new ResponseUtil(200,"false",null);
        }
    }

}
