package com.github.kardzhaliyski;

import java.time.LocalDate;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {
    private static final Pattern PATTERN = Pattern.compile("\\D*(\\d{1,4})\\D*(\\d{1,4})\\D*(\\d{1,4})\\D*");
    private int dayIndex = -1;
    private int monthIndex = -1;
    private int yearIndex = -1;

    public DateParser(Collection<EmployeeRecord> records) {
        extractIndexes(records);
    }

    public LocalDate parse(String date) {
        Matcher matcher = PATTERN.matcher(date);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid date format. For: " + date);
        }

        int year = Integer.parseInt(matcher.group(yearIndex));
        int month = Integer.parseInt(matcher.group(monthIndex));
        int day = Integer.parseInt(matcher.group(dayIndex));

        return LocalDate.of(year, month, day);
    }

    private void extractIndexes(Collection<EmployeeRecord> records) {
        for (EmployeeRecord record : records) {
            Matcher matcher = PATTERN.matcher(record.dateFrom);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid date format. For: " + record.dateFrom);
            }

            for (int i = 1; i <= matcher.groupCount(); i++) {
                if (i == yearIndex) {
                    continue;
                }

                if (yearIndex == -1 && matcher.group(i).length() > 2) {
                    yearIndex = i;
                    continue;
                }

                int num = Integer.parseInt(matcher.group(i));
                if (num > 31) {
                    yearIndex = i;
                    continue;
                }

                if (num > 12) {
                    dayIndex = i;
                    monthIndex = 6 - yearIndex - dayIndex;
                    return;
                }
            }
        }

        throw new IllegalStateException("Could not determinate date pattern.");
    }
}
