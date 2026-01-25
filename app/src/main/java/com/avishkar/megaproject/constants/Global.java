package com.avishkar.megaproject.constants;

import android.content.Context;
import android.content.Intent;

public class Global {

    public static void navigate(Context context,Class<?> secondActivity, boolean isFlagSet){
        Intent i = new Intent(context,secondActivity);
        if (isFlagSet) {
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        context.startActivity(i);
    }
}
