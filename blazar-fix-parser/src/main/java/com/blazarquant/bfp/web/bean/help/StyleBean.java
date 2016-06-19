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
package com.blazarquant.bfp.web.bean.help;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.data.FixPair;
import com.blazarquant.bfp.fix.parser.util.FixUtilities;
import com.blazarquant.bfp.web.util.StyleUtilities;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "styleBean")
@ApplicationScoped
public class StyleBean {

    public String getStyleForMsgType(FixPair msgType) {
        return StyleUtilities.getStyleForMsgType(msgType);
    }

    public String getStyleForField(int tag) {
        return StyleUtilities.getStyleForField(tag);
    }

    public String getStyleForOrdStatus(FixMessage message) {
        return StyleUtilities.getStyleForOrdStatus(FixUtilities.getOrdStatus(message));
    }

}
