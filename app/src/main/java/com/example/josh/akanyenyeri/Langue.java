package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/10/2017.
 */
public class Langue {
    int ID_LANGUE;
    String KINYARWANDA,KIRUNDI,FRANCAIS,ENGLISH;

    public Langue(int ID_LANGUE, String KINYARWANDA, String KIRUNDI, String FRANCAIS, String ENGLISH) {
        this.ID_LANGUE = ID_LANGUE;
        this.KINYARWANDA = KINYARWANDA;
        this.KIRUNDI = KIRUNDI;
        this.FRANCAIS = FRANCAIS;
        this.ENGLISH = ENGLISH;
    }


    public int getID_LANGUE() {
        return ID_LANGUE;
    }

    public void setID_LANGUE(int ID_LANGUE) {
        this.ID_LANGUE = ID_LANGUE;
    }

    public String getKINYARWANDA() {
        return KINYARWANDA;
    }

    public void setKINYARWANDA(String KINYARWANDA) {
        this.KINYARWANDA = KINYARWANDA;
    }

    public String getKIRUNDI() {
        return KIRUNDI;
    }

    public void setKIRUNDI(String KIRUNDI) {
        this.KIRUNDI = KIRUNDI;
    }

    public String getFRANCAIS() {
        return FRANCAIS;
    }

    public void setFRANCAIS(String FRANCAIS) {
        this.FRANCAIS = FRANCAIS;
    }

    public String getENGLISH() {
        return ENGLISH;
    }

    public void setENGLISH(String ENGLISH) {
        this.ENGLISH = ENGLISH;
    }
}
