/*
 * QAuxiliary - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2022 qwq233@qwq2333.top
 * https://github.com/cinit/QAuxiliary
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by QAuxiliary contributors.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/cinit/QAuxiliary/blob/master/LICENSE.md>.
 */

package io.github.qauxv.lifecycle;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cc.ioctl.util.HostInfo;
import io.github.qauxv.R;

public class CounterfeitActivityInfoFactory {

    @Nullable
    public static ActivityInfo makeProxyActivityInfo(@NonNull String className, int flags) {
        try {
            Context ctx = HostInfo.getApplication();
            Class<?> cl = Class.forName(className);
            try {
                ActivityInfo proto = ctx.getPackageManager().getActivityInfo(new ComponentName(
                    ctx.getPackageName(), "com.tencent.mobileqq.activity.QQSettingSettingActivity"), flags);
                // init style here, comment it out if it crashes on Android >= 10
                proto.theme = R.style.Theme_MaiTungTMDesign;
                return initCommon(proto, className);
            } catch (PackageManager.NameNotFoundException e) {
                throw new IllegalStateException(
                    "QQSettingSettingActivity not found, are we in the host?", e);
            }
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static ActivityInfo initCommon(ActivityInfo ai, String name) {
        ai.targetActivity = null;
        ai.taskAffinity = null;
        ai.descriptionRes = 0;
        ai.name = name;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ai.splitName = null;
        }
        return ai;
    }
}