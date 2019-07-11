package com.example.yunchebao.account;



import org.litepal.LitePal;

import java.util.List;

public class DbUtil {
    public static boolean isShouldAddToDb(MyAccount account){
        boolean isAdd=true;
        List<MyAccount> accounts= LitePal.findAll(MyAccount.class);
        for (int i = 0; i <accounts.size() ; i++) {
            MyAccount account1=accounts.get(i);
            if(account.getPhone().equals(account1.getPhone())){
                 isAdd=false;
                 break;
            }
        }
        return isAdd;
    }
}
