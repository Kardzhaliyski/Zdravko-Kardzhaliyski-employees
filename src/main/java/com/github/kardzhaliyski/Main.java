package com.github.kardzhaliyski;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("src/main/resources/sample.csv");
        CSVFormat build = CSVFormat
                .Builder
                .create(CSVFormat.DEFAULT)
                .setHeader()
                .setTrim(true)
                .setSkipHeaderRecord(true)
                .build();


        CSVParser parse = build.parse(fileReader);

        List<EmployeeRecord> records = new ArrayList<>();
        for (CSVRecord record : parse) {
            records.add(new EmployeeRecord(record));
        }

        DateParser dateParser = new DateParser(records);
        Map<Integer, Employee> employeesMap = parseEmployees(records, dateParser);
        CollaborationInfo collaborationInfo = findMostCollaborationTimePair(employeesMap);
        System.out.println("Collaboration between: " + collaborationInfo.employee1.id + " & " + collaborationInfo.employee2.id + " On project: " + collaborationInfo.projectId + " For days: " + collaborationInfo.days);
    }

    private static CollaborationInfo findMostCollaborationTimePair(Map<Integer, Employee> employeesMap) {
        Employee[] employees = employeesMap.values().toArray(Employee[]::new);
        int maxCollaborationDays = -1;
        CollaborationInfo maxCollaborationInfo = null;
        for (int i = 0; i < employees.length - 1; i++) {
            for (int j = i + 1; j < employees.length; j++) {
                CollaborationInfo collaborationInfo = employees[i].getCollaborationTime(employees[j]);
                if (collaborationInfo.days > maxCollaborationDays) {
                    maxCollaborationDays = collaborationInfo.days;
                    maxCollaborationInfo = collaborationInfo;
                }
            }
        }
        return maxCollaborationInfo;
    }

    private static Map<Integer, Employee> parseEmployees(List<EmployeeRecord> records, DateParser dateParser) {
        Map<Integer, Employee> employees = new HashMap<>();
        for (EmployeeRecord record : records) {
            int empID = Integer.parseInt(record.empID);
            Employee employee = employees.get(empID);
            if (employee == null) {
                employee = new Employee(empID);
                employees.put(empID, employee);
            }

            int projectId = Integer.parseInt(record.projectID);
            LocalDate from = dateParser.parse(record.dateFrom);
            LocalDate to = record.dateTo.equalsIgnoreCase("null") ? LocalDate.now() : dateParser.parse(record.dateTo);
            employee.addProject(projectId, from, to);
        }
        return employees;
    }
}