package com.github.kardzhaliyski.collaboration.app;

import java.time.LocalDate;

public class Period {
    public LocalDate from;
    public LocalDate to;

    public Period(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }
}
