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
