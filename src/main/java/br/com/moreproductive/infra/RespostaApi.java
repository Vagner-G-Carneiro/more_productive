package br.com.moreproductive.infra;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RestPadraoRespostaApi {
    private String type;
    private boolean successfully;
    private String title;
    private int status;
    private String detail;
    private String method;
    private String instance;
    private Map<String, String> errors = new HashMap<>();
    private LocalDateTime timeStamp;

    RestPadraoRespostaApi(){}

    public RestPadraoRespostaApi(String type, boolean successfully, String title, int status, String detail, String method,
                                 String instance, Map<String, String> errors, LocalDateTime timeStamp) {
        this.type = type;
        this.successfully = successfully;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.method = method;
        this.instance = instance;
        this.errors = errors;
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccessfully() {
        return successfully;
    }

    public void setSuccessfully(boolean successfully) {
        this.successfully = successfully;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
