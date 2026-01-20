package com.avishkar.megaproject.constants;

import android.content.Context;
import android.content.Intent;

public class Global {

    public static void navigate(Context context,Class<?> secondActivity){
        Intent i = new Intent(context,secondActivity);
        context.startActivity(i);
    }
}
