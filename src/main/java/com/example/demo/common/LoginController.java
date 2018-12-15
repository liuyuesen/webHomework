package com.example.demo.common;


import com.example.demo.tool.DBManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;

@RestController
public class LoginController {
    private String userName;
    private String password;

//    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
//    public LoginBean loginPage() {
//        LoginBean loginBean = new LoginBean();
//        loginBean.setPassword("123456");
//        loginBean.setUserID("liuyuesen");
//        return loginBean;
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "id", required = true) String userName,
                        @RequestParam(value = "passwords", required = true) String password) {
//        userName = httpServletRequest.getParameter("id");
//        password = httpServletRequest.getParameter("passwords");
        String error = "no error";

        String sql = "SELECT password FROM worker where id = " + userName + ";";
        try {
            DBManager dbManager = new DBManager(sql);
            ResultSet result;
            String DBpassword = null;
            result = dbManager.preparedStatement.executeQuery();
            while (result.next()) {
                DBpassword = result.getString("password");
            }
            if (DBpassword.equals(password)) {
                result.close();
                dbManager.close();

                return error;
            }
        } catch (Exception e) {
            error = e.toString();
        }

        return error;
    }
}

class LoginBean {
    private String userID;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
