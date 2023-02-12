package com.github.kardzhaliyski.collaboration.app;

import com.github.kardzhaliyski.collaboration.exceptions.InvalidDataException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.*;

public class CollaborationTimeApplication {
    private static final Set<String> MANDATORY_HEADERS = Set.of("EmpID", "ProjectID", "DateFrom", "DateTo");
    public static CollaborationInfo find(Reader csv) throws IOException {
        BufferedReader in = new BufferedReader(csv);
        CSVFormat build = CSVFormat
                .Builder
                .create(CSVFormat.DEFAULT)
                .setHeader()
                .setTrim(true)
                .setSkipHeaderRecord(true)
                .build();

        CSVParser parse = build.parse(in);
        validateParse(parse);

        List<EmployeeRecord> records = new ArrayList<>();
        for (CSVRecord record : parse) {
            records.add(new EmployeeRecord(record));
        }

        DateParser dateParser = new DateParser(records);
        Map<Integer, Employee> employeesMap = parseEmployees(records, dateParser);
        return findMostCollaborationTimePair(employeesMap);
    }

    private static void validateParse(CSVParser parse) {
        Map<String, Integer> headerMap = parse.getHeaderMap();
        for (String header : MANDATORY_HEADERS) {
            if (!headerMap.containsKey(header)) {
                throw new InvalidDataException("Doesn't contains mandatory headers (EmpID, ProjectID, DateFrom, DateTo)!");
            }
        }

        if (!parse.iterator().hasNext()) {
            throw new InvalidDataException("No elements found.");
        }
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