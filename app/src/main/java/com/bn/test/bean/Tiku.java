package com.bn.test.bean;

import java.io.Serializable;

/**
 * Created by JD on 2017/12/9.
 */

public class Tiku implements Serializable {
    String tiku_id;
    String tiku_leixing;
    String  A;
    String B;
    String C;
    String D;
    String E;
    String F;
    String tiku_name;
    String tiku_daan;
    public Tiku(String tiku_id,String  tiku_leixing,String tikuname,String  A,String B,String C,String D,String E,String F, String tiku_daan){
        this.tiku_daan=tiku_daan;
        this.tiku_id=tiku_id;
        this.tiku_name=tikuname;
        this.A=A;
        this.B=B;
        this.C=C;
        this.D=D;
        this.E=E;
        this.F=F;
        this.tiku_leixing=tiku_leixing;
    }
    public  String getTiku_id(){
        return tiku_id;
    }public  String getTiku_name(){
        return tiku_name;
    }
    public  String getTiku_daan(){
        return tiku_daan;
    }
    public String getTiku_leixing(){
        return tiku_leixing;
    }
    public String getA(){
        return A;

    }public String getB(){
        return B;
    }
    public String getC(){
        return C;
    }public String getD(){
        return D;
    }public String getE(){
        return  E;
    }public  String getF(){
        return F;
    }
}
