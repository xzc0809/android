package com.xzc.car.beans;
import java.io.Serializable;
import java.util.Date;
/***
*   
*/
public class Car implements Serializable {
        //主键，车辆id
            private Integer carId;
        //车辆类型
            private String carType;
        //车辆号码牌
            private String carNumber;
        //开始时间
            private Date carFreetimestart;
        //结束时间
            private Date carFreetimeend;
        //预约时间
            private Date carReservetime;
        //预览图url
            private String carPreview;
        //车辆状态
            private Integer carState;
        //车辆所属用户id
            private Integer userId;
        //租车用户id
            private Integer renUserId;
        //get set 方法
            public void setCarId (Integer  carId){
                this.carId=carId;
            }
            public  Integer getCarId(){
                return this.carId;
            }
            public void setCarType (String  carType){
                this.carType=carType;
            }
            public  String getCarType(){
                return this.carType;
            }
            public void setCarNumber (String  carNumber){
                this.carNumber=carNumber;
            }
            public  String getCarNumber(){
                return this.carNumber;
            }
            public void setCarFreetimestart (Date  carFreetimestart){
                this.carFreetimestart=carFreetimestart;
            }
            public  Date getCarFreetimestart(){
                return this.carFreetimestart;
            }
            public void setCarFreetimeend (Date  carFreetimeend){
                this.carFreetimeend=carFreetimeend;
            }
            public  Date getCarFreetimeend(){
                return this.carFreetimeend;
            }
            public void setCarReservetime (Date  carReservetime){
                this.carReservetime=carReservetime;
            }
            public  Date getCarReservetime(){
                return this.carReservetime;
            }
            public void setCarPreview (String  carPreview){
                this.carPreview=carPreview;
            }
            public  String getCarPreview(){
                return this.carPreview;
            }
            public void setCarState (Integer  carState){
                this.carState=carState;
            }
            public  Integer getCarState(){
                return this.carState;
            }
            public void setUserId (Integer  userId){
                this.userId=userId;
            }
            public  Integer getUserId(){
                return this.userId;
            }
            public void setRenUserId (Integer  renUserId){
                this.renUserId=renUserId;
            }
            public  Integer getRenUserId(){
                return this.renUserId;
            }
}
