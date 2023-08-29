package com.example.softwareproject;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    public Task(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
