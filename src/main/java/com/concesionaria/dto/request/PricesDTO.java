package com.concesionaria.dto.request;

import org.springframework.web.bind.annotation.RequestParam;

public class PricesDTO {
    private Integer since;
    private Integer to;
    private String currency;

    public Integer getSince() {
        return since;
    }

    public void setSince(Integer since) {
        this.since = since;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}