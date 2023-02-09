package com.github.kardzhaliyski;

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
}
