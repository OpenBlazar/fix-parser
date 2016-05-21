package com.blazarquant.bfp.web.bean.parser;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.FileUploadEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "fileParserBean")
@ViewScoped
public class FileParserBean extends ParserBean {

    public void handleFileUpload(FileUploadEvent event) {
        doParse(new String(
                event.getFile().getContents(),
                Charset.defaultCharset()));
    }

}
