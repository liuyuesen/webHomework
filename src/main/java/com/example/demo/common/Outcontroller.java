package com.example.demo.common;

import com.example.demo.tool.DBManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;


@RestController
public class Outcontroller {
    private String status;
    private String password;
    private String reason;
    private Date time_start;
    private Date time_end;
    private Integer days;

    @RequestMapping(value = "/out", method = RequestMethod.POST)
    public boolean out(@RequestParam(value = "status", required = true) String status,
                        @RequestParam(value = "id", required = true) String id,
                        @RequestParam(value = "reason", required = true) String reason,
                        @RequestParam(value = "time_start", required = true) Date time_start,
                        @RequestParam(value = "time_end", required = true) Date time_end,
                         @RequestParam(value = "days", required = true) Integer days) {
//        userName = httpServletRequest.getParameter("id");
//        password = httpServletRequest.getParameter("passwords");
        String error = "no error";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String time_s = df.format(time_start);
        String time_e = df.format(time_end);
        String status_temp = null;
        if(status.equals("“department_manager")){
            status_temp = "1";
        }else if(status.equals("staff")){
            status_temp = "0";
        }else{
            status_temp = "2";
        }


        String sql = "insert into out_form(wid, stime,etime,reason,otime,status,pre_statu) values ('"+id+"','"+time_s+"','"+time_e+"','"+reason+"','"+date+"',"+status_temp+","+status_temp+")";
        System.out.println(sql);
        try {
            DBManager dbManager = new DBManager(sql);
            dbManager.preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            error = e.toString();
        }
        return false;
    }


    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    public boolean leave(@RequestParam(value = "status", required = true) String status,
                         @RequestParam(value = "id", required = true) String id,
                         @RequestParam(value = "item",required = true) String item,
                         @RequestParam(value = "reason", required = true) String reason,
                         @RequestParam(value = "time_start", required = true) Date time_start,
                         @RequestParam(value = "time_end", required = true) Date time_end,
                         @RequestParam(value = "days", required = true) Integer days) {
//        userName = httpServletRequest.getParameter("id");
//        password = httpServletRequest.getParameter("passwords");
        String error = "no error";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        String time_s = df.format(time_start);
        String time_e = df.format(time_end);
        String status_temp = null;
        if(status.equals("“department_manager")){
            status_temp = "1";
        }else if(status.equals("staff")){
            status_temp = "0";
        }else{
            status_temp = "2";
        }
        Integer type = null;
        if(item.equals("年假")){
            type = 0;
        }else if(item.equals("事假")){
            type = 1;
        }else if(item.equals("病假")){
            type = 2;
        }else if(item.equals("婚假")){
            type = 3;
        }else if(item.equals("产假")){
            type = 4;
        }

        String sql = "insert into leave_form(wid, stime,etime,description,otime,status,type,pre_statu) values ('"+id+"','"+time_s+"','"+time_e+"','"+reason+"','"+date+"',"+status_temp+","+type+","+status_temp+")";
        System.out.println(sql);
        try {
            DBManager dbManager = new DBManager(sql);
            dbManager.preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            error = e.toString();
        }
        return false;
    }

}
