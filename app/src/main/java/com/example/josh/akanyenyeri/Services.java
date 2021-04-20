package com.example.josh.akanyenyeri;

import java.io.Serializable;

/**
 * Created by JOSH on 5/10/2017.
 */
public class Services implements Serializable {

    int ID_SERVICE;
    String CODE_SERVICE,NAME_SERVICE,CATEGORIE;

    public Services(int ID_SERVICE, String CODE_SERVICE, String NAME_SERVICE,String CATEGORIE) {
        this.ID_SERVICE = ID_SERVICE;
        this.CODE_SERVICE = CODE_SERVICE;
        this.NAME_SERVICE = NAME_SERVICE;
        this.CATEGORIE = CATEGORIE;
    }


    public int getID_SERVICE() {
        return ID_SERVICE;
    }

    public void setID_SERVICE(int ID_SERVICE) {
        this.ID_SERVICE = ID_SERVICE;
    }

    public String getCODE_SERVICE() {
        return CODE_SERVICE;
    }

    public void setCODE_SERVICE(String CODE_SERVICE) {
        this.CODE_SERVICE = CODE_SERVICE;
    }

    public String getNAME_SERVICE() {
        return NAME_SERVICE;
    }

    public void setNAME_SERVICE(String NAME_SERVICE) {
        this.NAME_SERVICE = NAME_SERVICE;
    }

    public String  toString()
    {
        return NAME_SERVICE;
    }
}
