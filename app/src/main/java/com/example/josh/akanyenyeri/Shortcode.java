package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/4/2017.
 */

import android.content.Intent;
import android.net.Uri;

import java.io.Serializable;
/**
 * Created by JOSH-TOSH on 12/3/2015.
 */
public class Shortcode implements Serializable{
    int ID_SHORT_CODE;
    String SHORT_CODE,EXPLANATION,CODE_COMPANY_OWNER,ID_COMPANY,INTERFACE;
    double CHARGE_AMOUNT;
    String IMAGE_PATH;

    public Shortcode(int ID_SHORT_CODE,String SHORT_CODE,String EXPLANATION,String CODE_COMPANY_OWNER,String ID_COMPANY,String INTERFACE,double CHARGE_AMOUNT,String IMAGE_PATH)
    {
        this.ID_SHORT_CODE =ID_SHORT_CODE;
        this.SHORT_CODE =SHORT_CODE;
        this.EXPLANATION =EXPLANATION;
        this.CODE_COMPANY_OWNER =CODE_COMPANY_OWNER;
        this.ID_COMPANY =ID_COMPANY;
        this.INTERFACE =INTERFACE;
        this.CHARGE_AMOUNT =CHARGE_AMOUNT;
        this.IMAGE_PATH = IMAGE_PATH;
    }


    public int getID_SHORT_CODE() {
        return ID_SHORT_CODE;
    }

    public void setID_SHORT_CODE(int ID_SHORT_CODE) {
        this.ID_SHORT_CODE = ID_SHORT_CODE;
    }

    public String getSHORT_CODE() {
        return SHORT_CODE;
    }

    public void setSHORT_CODE(String SHORT_CODE) {
        this.SHORT_CODE = SHORT_CODE;
    }

    public String getEXPLANATION() {
        return EXPLANATION;
    }

    public void setEXPLANATION(String EXPLANATION) {
        this.EXPLANATION = EXPLANATION;
    }

    public String getCODE_COMPANY_OWNER() {
        return CODE_COMPANY_OWNER;
    }

    public void setCODE_COMPANY_OWNER(String CODE_COMPANY_OWNER) {
        this.CODE_COMPANY_OWNER = CODE_COMPANY_OWNER;
    }

    public String getID_COMPANY() {
        return ID_COMPANY;
    }

    public void setID_COMPANY(String ID_COMPANY) {
        this.ID_COMPANY = ID_COMPANY;
    }

    public String getINTERFACE() {
        return INTERFACE;
    }

    public void setINTERFACE(String INTERFACE) {
        this.INTERFACE = INTERFACE;
    }

    public double getCHARGE_AMOUNT() {
        return CHARGE_AMOUNT;
    }

    public void setCHARGE_AMOUNT(double CHARGE_AMOUNT) {
        this.CHARGE_AMOUNT = CHARGE_AMOUNT;
    }


    public String getIMAGE_PATH() {
        return IMAGE_PATH;
    }

    public void setIMAGE_PATH(String IMAGE_PATH) {
        this.IMAGE_PATH = IMAGE_PATH;
    }




}
