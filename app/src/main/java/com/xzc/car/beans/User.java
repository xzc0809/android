package com.xzc.car.beans;


import java.io.Serializable;
import java.util.Date;
/***
*   
*/

public class User implements Serializable {
        //主键，用户id
            private Integer userId;
        //用户名
            private String userName;
        //用户密码
            private String userPassword;
        //用户电话号码
            private Integer userTel;
        //用户头像
            private String userHead;
        //get set 方法
            public void setUserId (Integer  userId){
                this.userId=userId;
            }
            public  Integer getUserId(){
                return this.userId;
            }
            public void setUserName (String  userName){
                this.userName=userName;
            }
            public  String getUserName(){
                return this.userName;
            }
            public void setUserPassword (String  userPassword){
                this.userPassword=userPassword;
            }
            public  String getUserPassword(){
                return this.userPassword;
            }
            public void setUserTel (Integer  userTel){
                this.userTel=userTel;
            }
            public  Integer getUserTel(){
                return this.userTel;
            }
            public void setUserHead (String  userHead){
                this.userHead=userHead;
            }
            public  String getUserHead(){
                return this.userHead;
            }
}
