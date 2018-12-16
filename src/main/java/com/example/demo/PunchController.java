package com.example.demo;

import com.example.demo.tool.DBManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.ResultSet;

@RestController
public class PunchController {

    @RequestMapping(value = "/punch", method = RequestMethod.POST)
    public PunchBean Punch(@RequestParam(value = "id", required = true) String id,
                           @RequestParam(value = "function", required = true) String function,
                           @RequestParam(value = "status", required = true) String status,
                           @RequestParam(value = "punch_date", required = true) Date punchDate) {
        String sql = "Insert into attendance(wid) values (" + id + ");";
        PunchBean punchBean = new PunchBean();
        boolean bool = true;
        try {
            DBManager dbManager = new DBManager(sql);
            dbManager.preparedStatement.executeUpdate();
            dbManager.close();
            bool = true;
        } catch (Exception e) {
            bool = false;
        }
        punchBean.setResult(bool);
        return punchBean;
    }
}

class PunchBean {
    private boolean result;

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean getResult() {
        return result;
    }
}

