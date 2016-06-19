/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.web;

import com.blazarquant.bfp.web.util.FacesUtils;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import org.apache.shiro.guice.web.GuiceShiroFilter;

/**
 * @author Wojciech Zankowski
 */
public class WebModule extends ServletModule {

    @Override
    protected void configureServlets() {
        filter("/*").through(GuiceShiroFilter.class);

        binder().bind(ShiroUtils.class).in(Singleton.class);
        binder().bind(FacesUtils.class).in(Singleton.class);
    }
}
