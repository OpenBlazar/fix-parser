package com.blazarquant.bfp.core.mail.util;

/**
 * @author Wojciech Zankowski
 */
public class MailMessageFactory {

    public static final String CONFIRMATION_MESSAGE_SUBJECT = "BlazarQuant - Register confirmation!";

    public static String confirmationMessage(String confirmationKey) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<!-- General styles, not used by all email clients -->\n" +
                "<style type=\"text/css\" media=\"all\">\n" +
                "a {\n" +
                "\ttext-decoration: none;\n" +
                "\tcolor: #0088cc;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<!-- KEEP THE TAMPLE SIMPLE: THOSE ARE SERVICE MESSAGES. -->\n" +
                "<body style=\"margin: 0; background-color: #eee;\">\n" +
                "\t<table align=\"center\" style=\"width: 600px;\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"left\" style=\"padding-top: 25px;\"><img src=\"http://i.imgur.com/lUdrsHq.png\"/></td>\n" +
                "\t\t</tr>\n" +
                "\t</table>\n" +
                "\n" +
                "\t<!-- Main table 100% wide with background color #eee -->\n" +
                "\t<table style=\"width: 100%;\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td align=\"center\">\n" +
                "\t\t\t\t<!-- Content table with backgdound color #fff, width 500px -->\n" +
                "\t\t\t\t<table\n" +
                "\t\t\t\t\tstyle=\"background-color: #fff; width: 600px; border: 1px solid #ddd;\">\n" +
                "\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t<td\n" +
                "\t\t\t\t\t\t\tstyle=\"padding: 25px; font-size: 16px; font-family: sans-serif\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t<p style=\"font-size: 22px;\">Hello!</p>\n" +
                "<p>Welcome to the BlazarQuant Fix Parser. To confirm your registration, please click link below: </p>" +
                "<p><a href=\"http://www.blazarquant.com/confirmation?key=" + confirmationKey + "\">http://www.blazarquant.com/confirmation?key=" + confirmationKey + "</a>" +
                "<p>We hope you will stay with us.</p>" +
                "<p>BlazarQuant</p>" +
                "\n" +
                "\t\t\t\t\t\t</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</table>\n" +
                "\n" +
                "\t\t\t</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>\n" +
                "\n" +
                "\n" +
                "\t<table cellpadding=\"5\" align=\"center\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<td style=\"text-align: center\">2016 © BlazarQuant</td>\n" +
                "\t\t</tr>\n" +
                "\t</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }

}
