package com.example.demo.common;

import com.example.demo.tool.DBManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class DealController {
    @RequestMapping(value = "/deals", method = RequestMethod.POST)
    public Boolean dealcontroll(@RequestParam(value = "did", required = true) String did,
                                @RequestParam(value = "function", required = true) int function,
                                @RequestParam(value = "status", required = true) String status,
                                @RequestParam(value = "admission", required = true) String admission,
                                @RequestParam(value = "description", required = true) String description) {
        boolean flag = false;
        //leave or out
        String loro = null;
        if(function==0){
            loro="leave_form";
        }
        try {
            String sql1="select status from "+loro+" where id = "+did+";";
            DBManager dbManager = new DBManager(sql1);
            ResultSet result;
            result = dbManager.preparedStatement.executeQuery();
            String pre_status=null;
            while(result.next()){
                pre_status=result.getString("status");
            }
            String st = null,opinion1=null,opinion2=null,opinion3=null;
            if(admission.equals(0)&&status=="department_manager"){
                st="4";//不同意按钮--对应状态4
                opinion1=description;
            }else if(admission.equals(0)&&status=="deputy_general_manager"){
                st="4";
                opinion2=description;
            }else if(admission.equals(0)&&status=="general_manager"){
                st="4";
                opinion3=description;
            }else if(admission.equals(1)&&status=="department_manager"){
                st="1";
            }else if(admission.equals(1)&&status=="deputy_general_manager"){
                st="2";
            }else if(admission.equals(1)&&status=="general_manager"){
                st="3";
            }
            //-----------下边是做第一次更改--------------------------------------
            String sql="update "+loro+" set status = "+st+" , pre_status = "+pre_status+" where id = "+did+";";
            DBManager dbManager1 = new DBManager(sql);
            //------------第一次改完了！！！！！！！！！！！！！！！！！！！！
            //------------现在我们来改第二次！！！！！！！！！！！！！！
            String sql2="select status from "+loro+" where id = "+did+";";
            DBManager dbManager2 = new DBManager(sql1);
            ResultSet result1;
            result1 = dbManager.preparedStatement.executeQuery();
            String pre=null;
            boolean bi3 = false;
            while(result.next()){
                pre=result1.getString("status");
                Date date1 = result1.getDate("stime");
                Date date2 = result1.getDate("etime");
                int days = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
                if(days>=3){
                    bi3=true;
                }
            }
            String thistate=null;
            if(pre.equals("3")||(pre.equals("2")&&(!bi3))){
                String sqlfinal = "update "+loro+" set status = 5, pre_status = "+pre+" where id = "+did+";";
                DBManager dbManager3 = new DBManager(sqlfinal);
                dbManager3.close();
            }
            dbManager.close();
            dbManager1.close();
            dbManager2.close();


            result.close();
            result1.close();
            flag=true;
        } catch (Exception e) {
            System.out.print(e);
        }
        return flag;
    }
}
