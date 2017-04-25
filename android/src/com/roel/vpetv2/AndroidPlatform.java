package com.roel.vpetv2;
import android.app.Activity;
import android.widget.Toast;

import com.roel.vpetv2.*;
/**
 * Created by roel on 4/23/17.
 */

public class AndroidPlatform implements NativePlatform {

    private Activity context;

    public AndroidPlatform(Activity context)
    {
        this.context = context;
    }


    @Override
    public void showWarning(final boolean x) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(x)
                    Toast.makeText(context,"Pet name is not allowed!",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context,"Pet name is too long!",Toast.LENGTH_LONG).show();

            }
        });
    }
}
