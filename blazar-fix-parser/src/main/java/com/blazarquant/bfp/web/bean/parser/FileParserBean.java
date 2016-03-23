package com.blazarquant.bfp.web.bean.parser;

import org.primefaces.event.FileUploadEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "fileParserBean")
@ViewScoped
public class FileParserBean extends ParserBean {

    public void handleFileUpload(FileUploadEvent event) {
        String input = new String(event.getFile().getContents());
        messages = parserService.parseInput(input);
        doSaveMessages(messages);
    }

}
