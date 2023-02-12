package com.github.kardzhaliyski.collaboration.app;

import com.github.kardzhaliyski.collaboration.exceptions.InvalidDataException;
import org.apache.commons.csv.CSVRecord;

public class EmployeeRecord {
    public String empID;
    public String projectID;
    public String dateFrom;
    public String dateTo;

    public EmployeeRecord(CSVRecord record) {
        this.empID = record.get("EmpID");
        this.projectID = record.get("ProjectID");
        this.dateFrom = record.get("DateFrom");
        this.dateTo = record.get("DateTo");

        if(empID == null || projectID == null || dateFrom == null || dateTo == null) {
            throw new InvalidDataException("Invalid data row -> " + record.toString());
        }
    }
}
