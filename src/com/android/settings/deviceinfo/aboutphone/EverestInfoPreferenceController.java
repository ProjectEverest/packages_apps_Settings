/*
 * Copyright (C) 2020 Wave-OS
 * Copyright (C) 2021 ShapeShiftOS
 * Copyright (C) 2024 SuperiorExtended-OS
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

package com.android.settings.deviceinfo.aboutphone;

import android.content.Context;
import android.os.SystemProperties;
import android.widget.TextView;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.utils.EverestSpecUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class EverestInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_EVEREST_INFO = "everest_info";
    private static final String KEY_STORAGE = "storage";
    private static final String KEY_CHIPSET = "chipset";
    private static final String KEY_BATTERY = "battery";
    private static final String KEY_DISPLAY = "display";

    public EverestInfoPreferenceController(Context context) {
        super(context);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final LayoutPreference everestInfoPreference = screen.findPreference(KEY_EVEREST_INFO);

        if (everestInfoPreference != null) {
            final TextView processor = everestInfoPreference.findViewById(R.id.chipset_summary);
            final TextView storageAndRAM = everestInfoPreference.findViewById(R.id.cust_storage_summary);
            final TextView battery = everestInfoPreference.findViewById(R.id.cust_battery_summary);
            final TextView infoScreen = everestInfoPreference.findViewById(R.id.cust_display_summary);

            if (processor != null && storageAndRAM != null && battery != null && infoScreen != null) {
            Context context = everestInfoPreference.getContext();

            processor.setText(EverestSpecUtils.getProcessorModel(context));
            storageAndRAM.setText(EverestSpecUtils.getStorageAndRAMInfo(context));
            battery.setText(EverestSpecUtils.getBatteryInfo(context));
            infoScreen.setText(EverestSpecUtils.getScreenRes(context));
            }
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_EVEREST_INFO;
    }
}
