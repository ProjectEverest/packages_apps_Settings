/*
 * Copyright (C) 2024 EverestOS
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

package com.android.settings.deviceinfo.aboutphone

import android.content.Context
import android.os.SystemProperties
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceScreen
import com.android.settings.R
import com.android.settingslib.core.AbstractPreferenceController
import com.android.settingslib.widget.LayoutPreference

import com.android.settings.utils.DeviceInfoUtil

class EverestInfoPreferenceController(context: Context) : AbstractPreferenceController(context) {

    private val defaultFallback = mContext.getString(R.string.device_info_default)

    private fun getProp(propName: String): String {
        return SystemProperties.get(propName, defaultFallback)
    }

    private fun getProp(propName: String, customFallback: String): String {
        val propValue = SystemProperties.get(propName)
        return if (propValue.isNotEmpty()) propValue else SystemProperties.get(customFallback, "Unknown")
    }

    private fun getEverestChipset(): String {
        return getProp(PROP_EVEREST_CHIPSET, "ro.board.platform")
    }

    private fun getEverestBuildStatus(buildType: String): String {
        return mContext.getString(if (buildType == "official") R.string.build_is_official_title else R.string.build_is_community_title)
    }

    private fun getEverestMaintainer(buildType: String): String {
        val everestMaintainer = getProp(PROP_EVEREST_MAINTAINER)
        if (everestMaintainer.equals("Unknown", ignoreCase = true)) {
            return mContext.getString(R.string.unknown_maintainer)
        }
        return mContext.getString(R.string.maintainer_summary, everestMaintainer)
    }

    override fun displayPreference(screen: PreferenceScreen) {
        super.displayPreference(screen)

        val buildType = getProp(PROP_EVEREST_BUILDTYPE).lowercase()
        val everestMaintainer = getEverestMaintainer(buildType)
        val isOfficial = buildType == "official"

        val hwInfoPreference = screen.findPreference<LayoutPreference>(KEY_HARDWARE_INFO)!!
        val statusPreference = screen.findPreference<Preference>(KEY_BUILD_STATUS)!!

        statusPreference.setTitle(getEverestBuildStatus(buildType))
        statusPreference.setSummary(everestMaintainer)
        statusPreference.setIcon(if (isOfficial) R.drawable.verified else R.drawable.unverified)

        hwInfoPreference.apply {
            findViewById<TextView>(R.id.chipsetinfo).text = getEverestChipset()
            findViewById<TextView>(R.id.storageinfo).text =
                "${DeviceInfoUtil.getTotalRam()} | ${DeviceInfoUtil.getStorageTotal(mContext)}"
            findViewById<TextView>(R.id.batteryinfo).text = DeviceInfoUtil.getBatteryCapacity(mContext)
            findViewById<TextView>(R.id.displayinfo).text = DeviceInfoUtil.getScreenResolution(mContext)
        }
    }

    override fun isAvailable(): Boolean {
        return true
    }

    override fun getPreferenceKey(): String {
        return KEY_DEVICE_INFO
    }

    companion object {
        private const val KEY_HARDWARE_INFO = "everest_hardware_info"
        private const val KEY_DEVICE_INFO = "my_device_info_header"
        private const val KEY_BUILD_STATUS = "rom_build_status"

        private const val PROP_EVEREST_BUILDTYPE = "ro.everest.buildtype"
        private const val PROP_EVEREST_MAINTAINER = "ro.everestos.maintainer"
        private const val PROP_EVEREST_CHIPSET = "ro.everest.chipset"
    }
}
