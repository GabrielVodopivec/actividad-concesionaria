package com.concesionaria.dto.response;

import java.util.ArrayList;
import java.util.List;

public class ResultListResponseDTO<T> {
    private int status;
    private String message;
    private int size;
    private List<? extends T> result;

    public ResultListResponseDTO() {
        result = new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<? extends T> getResult() {
        return result;
    }

    public void setResult(List<? extends T> result) {
        this.result = result;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}