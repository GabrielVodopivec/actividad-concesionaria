package com.concesionaria.dto.request;

import java.time.LocalDate;

public class DatesDTO {

    private LocalDate since;
    private LocalDate to;

    public LocalDate getSince() {
        return since;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}