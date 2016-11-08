/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
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
package com.supets.pet.libreacthotfix.preloader;

import android.os.Bundle;

/**
 * {@link ReactInfo} describes a ReactActivity's necessary information.
 *
 * @author markzhai on 16/8/21
 * @version 0.31.0
 */
public class ReactInfo {

    private String mMainComponentName;
    private Bundle mLaunchOptions;

    public ReactInfo(String mainComponentName) {
        mMainComponentName = mainComponentName;
    }

    public ReactInfo(String mainComponentName, Bundle launchOptions) {
        mMainComponentName = mainComponentName;
        mLaunchOptions = launchOptions;
    }

    public Bundle getLaunchOptions() {
        return mLaunchOptions;
    }

    public String getMainComponentName() {
        return mMainComponentName;
    }
}
