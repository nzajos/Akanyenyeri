package com.example.josh.akanyenyeri;

import android.content.Context;
import android.content.Intent;

/**
 * Created by JOSH-TOSH on 9/14/2015.
 */
import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {



    public static String servers3 = "imaginaryltd.com";

    // give your server registration url here
    public static final String SERVER_URL = "http://"+servers3+"/akanyenyeri/";
    // Google project id
    static final String SENDER_ID = "359889434459";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "AndroidCrime GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.example.crimeemergency.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
