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
    public LoginBean login(@RequestParam(value = "id", required = true) String userName,
                           @RequestParam(value = "passwords", required = true) String password) {
//        userName = httpServletRequest.getParameter("id");
//        password = httpServletRequest.getParameter("passwords");
        LoginBean loginBean = new LoginBean();
        String sql = "SELECT password,category FROM worker where id = " + userName + ";";
        try {
            DBManager dbManager = new DBManager(sql);
            ResultSet result;
            String DBpassword = null;
            int category = 4;
            result = dbManager.preparedStatement.executeQuery();
            while (result.next()) {
                DBpassword = result.getString("password");
                category = Integer.parseInt(result.getString("category"));
            }
            if (DBpassword.equals(password)) {
                result.close();
                dbManager.close();
                loginBean.setResult(true);
                loginBean.setStatus(getStatus(category));
                return loginBean;
            }
        } catch (Exception e) {
        }
        loginBean.setResult(false);
        loginBean.setStatus("密码错误");
        return loginBean;
    }

    private String getStatus(int number) {
        switch (number) {
            case 0:
                return "staff";
            case 1:
                return "department_manager";
            case 2:
                return "deputy_general_manage";
            case 3:
                return "general_manager";
            default:
                return "error";
        }
    }
}

class LoginBean {
    private boolean result;
    private String status;

    public void setResult(boolean result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean getResult() {
        return result;
    }

}

