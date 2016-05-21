package com.blazarquant.bfp.web.bean.parser;

import org.primefaces.event.FileUploadEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.nio.charset.Charset;

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
