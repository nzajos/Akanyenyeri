package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 7/22/2017.
 */
public class Messages {

    int ID_MESSAGE;
    String CONTENT_MESSAGE,SENDER_MESSAGE,TIME_MESSAGE,SYNC_MESSAGE_STATUS;

    public Messages(int ID_MESSAGE, String CONTENT_MESSAGE, String SENDER_MESSAGE, String TIME_MESSAGE,String SYNC_MESSAGE_STATUS) {
        this.ID_MESSAGE = ID_MESSAGE;
        this.CONTENT_MESSAGE = CONTENT_MESSAGE;
        this.SENDER_MESSAGE = SENDER_MESSAGE;
        this.TIME_MESSAGE = TIME_MESSAGE;
        this.SYNC_MESSAGE_STATUS = SYNC_MESSAGE_STATUS;
    }
}
