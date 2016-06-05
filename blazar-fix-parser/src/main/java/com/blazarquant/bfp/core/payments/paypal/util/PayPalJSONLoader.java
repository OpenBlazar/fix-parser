package com.blazarquant.bfp.core.payments.paypal.util;

import com.paypal.base.rest.JSONFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
public class PayPalJSONLoader {

    public <T> T load(String jsonFile, Class<T> clazz) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(jsonFile)));
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
