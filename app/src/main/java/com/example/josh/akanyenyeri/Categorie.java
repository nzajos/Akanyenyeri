package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/22/2017.
 */
public class Categorie {
    int ID_CATEGORIE;
    String NAME_CATEGORIE,HINT_CATEGORIE;
    int DIGIT_NUMBER_CATEGORIE;

    public Categorie(int ID_CATEGORIE, String NAME_CATEGORIE, String HINT_CATEGORIE,int DIGIT_NUMBER_CATEGORIE) {
        this.ID_CATEGORIE = ID_CATEGORIE;
        this.NAME_CATEGORIE = NAME_CATEGORIE;
        this.HINT_CATEGORIE = HINT_CATEGORIE;
        this.DIGIT_NUMBER_CATEGORIE = DIGIT_NUMBER_CATEGORIE;
    }

    public String toString()
    {
        return NAME_CATEGORIE;
    }
}
