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
package com.blazarquant.bfp.core.payments.paypal.util;

import com.blazarquant.bfp.common.BlazarFixParserConstants;
import com.paypal.base.rest.JSONFormatter;

import java.io.*;

/**
 * @author Wojciech Zankowski
 */
public class PayPalJSONLoader {

    public <T> T load(String jsonFile, Class<T> clazz) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(jsonFile)),
                    BlazarFixParserConstants.DEFAULT_CHARSET
            ));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.getProperty("line.separator"));
                line = br.readLine();
            }
            return (T) JSONFormatter.fromJSON(sb.toString(), clazz);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
