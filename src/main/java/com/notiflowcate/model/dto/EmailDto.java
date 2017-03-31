package com.notiflowcate.model.dto;

import javax.validation.constraints.NotNull;

import java.io.File;

/**
 * Created by Dayel
 * 12/21/15.
 */
public class EmailDto {

    @NotNull(message = "You must provide a valid from address")
    private String from;

    @NotNull(message = "You must provide a valid to address")
    private String[] to;

    private String[] cc;

    private String[] bcc;

    @NotNull(message = "You must provide a valid email subject")
    private String subject;

    @NotNull(message = "You must provide a valid email body")
    private String body;

    private File file;

    @NotNull(message = "You must specify if the body is in HTML format")
    private Boolean isHtml;

    private String error;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Boolean getHtml() {
        return isHtml;
    }

    public void setHtml(Boolean html) {
        isHtml = html;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
