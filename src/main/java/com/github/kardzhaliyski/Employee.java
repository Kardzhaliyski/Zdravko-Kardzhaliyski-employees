package com.github.kardzhaliyski;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employee {
    public int id;
    private final Map<Integer, List<Period>> projects = new HashMap<>();

    public Employee(int id) {
        this.id = id;
    }

    public void addProject(int projectId, LocalDate from, LocalDate to) {
        List<Period> periods = projects.computeIfAbsent(projectId, k -> new ArrayList<>());
        projects.putIfAbsent(projectId, new ArrayList<>());
        periods.add(new Period(from, to));
    }

    public CollaborationInfo getCollaborationTime(Employee employee) {
        int maxDays = 0;
        CollaborationInfo collabInfo = null;
        for (Map.Entry<Integer, List<Period>> project : this.projects.entrySet()) {
            Integer projectId = project.getKey();
            List<Period> employeePeriods = employee.projects.get(projectId);
            if (employeePeriods == null) {
                continue;
            }

            int days = getProjectCollaborationTime(project.getValue(), employeePeriods);
            if (days > maxDays) {
                collabInfo = new CollaborationInfo(this, employee, projectId, days);
                maxDays = days;
            }
        }

        return collabInfo;
    }

    private int getProjectCollaborationTime(List<Period> e1Periods, List<Period> e2Periods) {
        int days = 0;
        for (Period e1Period : e1Periods) {
            for (Period e2Period : e2Periods) {
                if (e1Period.from.isAfter(e2Period.to) || e2Period.from.isAfter(e1Period.to)) {
                    continue;
                }

                LocalDate collabStart = e1Period.from.isAfter(e2Period.from) ? e1Period.from : e2Period.from;
                LocalDate collabEnds = e1Period.to.isBefore(e2Period.to) ? e1Period.to : e2Period.to;

                days += collabStart.until(collabEnds, ChronoUnit.DAYS) + 1;
            }
        }

        return days;
    }
}
