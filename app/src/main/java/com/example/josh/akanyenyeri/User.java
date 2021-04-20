package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 7/3/2017.
 */
public class User {
    int ID_USER;
    String IMEI_NUMBER,SERIAL_NUMBER,KEY_FIRE_BASE,CREATED_DATE,COUNTRY_USER,STATUS_USER;

    public User(int ID_USER, String IMEI_NUMBER, String SERIAL_NUMBER, String KEY_FIRE_BASE, String CREATED_DATE, String COUNTRY_USER, String STATUS_USER) {
        this.ID_USER = ID_USER;
        this.IMEI_NUMBER = IMEI_NUMBER;
        this.SERIAL_NUMBER = SERIAL_NUMBER;
        this.KEY_FIRE_BASE = KEY_FIRE_BASE;
        this.CREATED_DATE = CREATED_DATE;
        this.COUNTRY_USER = COUNTRY_USER;
        this.STATUS_USER = STATUS_USER;
    }
}
