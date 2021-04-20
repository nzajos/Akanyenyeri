package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/10/2017.
 */
public class Companydep {

    int ID_COMPANY_DEP;
    String CODE_COMPANY_DEP,NAME_COMPANY_DEP,LOGO_DEP,COUNTRY;

    public Companydep(int ID_COMPANY_DEP, String CODE_COMPANY_DEP, String NAME_COMPANY_DEP, String LOGO_DEP, String COUNTRY) {
        this.ID_COMPANY_DEP = ID_COMPANY_DEP;
        this.CODE_COMPANY_DEP = CODE_COMPANY_DEP;
        this.NAME_COMPANY_DEP = NAME_COMPANY_DEP;
        this.LOGO_DEP = LOGO_DEP;
        this.COUNTRY = COUNTRY;
    }


    public int getID_COMPANY_DEP() {
        return ID_COMPANY_DEP;
    }

    public void setID_COMPANY_DEP(int ID_COMPANY_DEP) {
        this.ID_COMPANY_DEP = ID_COMPANY_DEP;
    }

    public String getCODE_COMPANY_DEP() {
        return CODE_COMPANY_DEP;
    }

    public void setCODE_COMPANY_DEP(String CODE_COMPANY_DEP) {
        this.CODE_COMPANY_DEP = CODE_COMPANY_DEP;
    }

    public String getNAME_COMPANY_DEP() {
        return NAME_COMPANY_DEP;
    }

    public void setNAME_COMPANY_DEP(String NAME_COMPANY_DEP) {
        this.NAME_COMPANY_DEP = NAME_COMPANY_DEP;
    }

    public String getLOGO_DEP() {
        return LOGO_DEP;
    }

    public void setLOGO_DEP(String LOGO_DEP) {
        this.LOGO_DEP = LOGO_DEP;
    }

    public String getCOUNTRY() {
        return COUNTRY;
    }

    public void setCOUNTRY(String COUNTRY) {
        this.COUNTRY = COUNTRY;
    }
}
