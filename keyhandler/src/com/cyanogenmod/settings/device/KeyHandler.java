/*
 * Copyright (C) 2017 The LineageOS Project
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

package com.cyanogenmod.settings.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;

import com.android.internal.os.DeviceKeyHandler;

import cyanogenmod.providers.CMSettings;

public class KeyHandler implements DeviceKeyHandler {
    private boolean fpAsHomeEnabled = false;

    private static final String TAG = KeyHandler.class.getSimpleName();

    private static final boolean DEBUG = false;

    private final Context mContext;

    private final BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("ENABLE_FP_AS_HOME")) {
                fpAsHomeEnabled = true;
            } else if (intent.getAction().equals("DISABLE_FP_AS_HOME")) {
                fpAsHomeEnabled = false;
            }
        }
    };

    public KeyHandler(Context context) {
        mContext = context;

        IntentFilter filter = new IntentFilter();
        filter.addAction("ENABLE_FP_AS_HOME");
        filter.addAction("DISABLE_FP_AS_HOME");
        mContext.registerReceiver(mUpdateReceiver, filter);
    }

    public boolean handleKeyEvent(KeyEvent event) {

        if (!hasSetupCompleted()) {
            return false;
        }

        if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
            if (event.getScanCode() == 353) {
                if (DEBUG) Log.d(TAG, "Fingerprint sensor tapped");
                return !fpAsHomeEnabled;
            }
        }
        return false;
    }

    private boolean hasSetupCompleted() {
        return CMSettings.Secure.getInt(mContext.getContentResolver(),
                CMSettings.Secure.CM_SETUP_WIZARD_COMPLETED, 0) != 0;
    }
}
