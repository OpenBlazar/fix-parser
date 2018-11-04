package pl.zankowski.fixparser.payment.paypal.util;

import com.paypal.base.rest.JSONFormatter;
import pl.zankowski.fixparser.core.framework.FixParserConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class PayPalJSONLoader {

    public <T> T load(String jsonFile, Class<T> clazz) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(jsonFile)),
                    FixParserConstants.DEFAULT_CHARSET
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
