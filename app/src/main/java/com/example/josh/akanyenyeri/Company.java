package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/9/2017.
 */
public class Company {
    int ID_COMPANY;
    String CODE_COMPANY,NAME_COMPANY,LOGO,COUNTRY,MONEY_SYSTEM,SHORT_CODE;

    public Company(int ID_COMPANY, String CODE_COMPANY, String NAME_COMPANY, String LOGO, String COUNTRY,String MONEY_SYSTEM,String SHORT_CODE) {
        this.ID_COMPANY = ID_COMPANY;
        this.CODE_COMPANY = CODE_COMPANY;
        this.NAME_COMPANY = NAME_COMPANY;
        this.LOGO = LOGO;
        this.COUNTRY = COUNTRY;
        this.MONEY_SYSTEM = MONEY_SYSTEM;
        this.SHORT_CODE = SHORT_CODE;
    }

    public String getMONEY_SYSTEM() {
        return MONEY_SYSTEM;
    }

    public void setMONEY_SYSTEM(String MONEY_SYSTEM) {
        this.MONEY_SYSTEM = MONEY_SYSTEM;
    }

    public String getSHORT_CODE() {
        return SHORT_CODE;
    }

    public void setSHORT_CODE(String SHORT_CODE) {
        this.SHORT_CODE = SHORT_CODE;
    }

    public int getID_COMPANY() {
        return ID_COMPANY;
    }

    public String getCODE_COMPANY() {
        return CODE_COMPANY;
    }

    public String getNAME_COMPANY() {
        return NAME_COMPANY;
    }

    public String getLOGO() {
        return LOGO;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }


    public void setID_COMPANY(int ID_COMPANY) {
        this.ID_COMPANY = ID_COMPANY;
    }

    public void setCODE_COMPANY(String CODE_COMPANY) {
        this.CODE_COMPANY = CODE_COMPANY;
    }

    public void setNAME_COMPANY(String NAME_COMPANY) {
        this.NAME_COMPANY = NAME_COMPANY;
    }

    public void setLOGO(String LOGO) {
        this.LOGO = LOGO;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }
}
