/*
 * Copyright (C) 2019-2022 The LineageOS Project
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

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

public class LineageVersionDetailPreferenceController extends BasePreferenceController {
    private static final String KEY_LINEAGE_VERSION_PROP = "ro.modversion";
    private static final String EVEREST_BUILDTYPE_PROPERTY = "ro.everest.buildtype";
    private static final String EVEREST_EDITION_PROPERTY = "ro.everest.edition";

    public LineageVersionDetailPreferenceController(Context context, String preferenceKey) {
        super(context, preferenceKey);
    }

    @Override
    public int getAvailabilityStatus() {
        return TextUtils.isEmpty(SystemProperties.get(KEY_LINEAGE_VERSION_PROP)) ? 
               UNSUPPORTED_ON_DEVICE : AVAILABLE;
    }

    @Override
    public CharSequence getSummary() {
        String[] properties = {
            SystemProperties.get(KEY_LINEAGE_VERSION_PROP),
            SystemProperties.get(EVEREST_BUILDTYPE_PROPERTY),
            SystemProperties.get(EVEREST_EDITION_PROPERTY)
        };

        return String.join(" | ", TextUtils.join(" | ", properties).trim());
    }
}
