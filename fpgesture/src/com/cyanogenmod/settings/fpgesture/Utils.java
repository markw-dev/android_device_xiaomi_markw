/*
 * Copyright (c) 2015 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.settings.fpgesture;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

public final class Utils {

    private static final String TAG = "FPGesture_Utils";
    private static final boolean DEBUG = false;

    private static Context mContext;
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;

    protected static final String FP_AS_HOME_KEY = "fp_as_home";

    protected static boolean isFpAsHomeEnabled(Context context) {
        mPreferences = context.getSharedPreferences("fpgesture", 0);
        return mPreferences.getInt("fp_as_home", 0) != 0; 
    }

    protected static void enableFpAsHome(boolean enable, Context context) {
        if (DEBUG) Log.d(TAG, "enable fingerprint as Home");
        mPreferences = context.getSharedPreferences("fpgesture", 0);
        mEditor = mPreferences.edit();
        mEditor.putInt("fp_as_home", enable ? 1 : 0);
        mEditor.commit();
    }

    protected static void sendBroadCast(boolean enable, Context context) {
        Intent intent=new Intent();
        if (enable) {
            intent.setAction("ENABLE_FP_AS_HOME");
        } else {
            intent.setAction("DISABLE_FP_AS_HOME");
        }
		context.sendBroadcast(intent);
	}
}
