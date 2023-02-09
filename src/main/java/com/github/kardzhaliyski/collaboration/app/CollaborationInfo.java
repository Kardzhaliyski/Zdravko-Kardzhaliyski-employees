package com.github.kardzhaliyski.collaboration.app;

public class CollaborationInfo {
    Employee employee1;
    Employee employee2;
    int projectId;
    int days;

    public CollaborationInfo(Employee employee1, Employee employee2, int projectId, int days) {
        this.employee1 = employee1;
        this.employee2 = employee2;
        this.projectId = projectId;
        this.days = days;
    }

    public Employee getEmployee1() {
        return employee1;
    }

    public Employee getEmployee2() {
        return employee2;
    }

    public int getProjectId() {
        return projectId;
    }

    public int getDays() {
        return days;
    }
}
