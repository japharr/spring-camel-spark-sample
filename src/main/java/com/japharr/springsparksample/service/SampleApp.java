package com.japharr.springsparksample.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SampleApp {
    private static final String smaple =
        "id,name,age,course\n1,john,12,english\n2,harry,13,physics\n3,james,14,biology";

    public static void main(String[] args) throws IOException {
        CSVFormat format = CSVFormat.Builder
            .create()
            .setHeader("id", "name", "age", "course")
            .setSkipHeaderRecord(true)
            .build();
        CSVParser parser = CSVParser.parse(smaple, format);
        List<CSVRecord> csvRecords = parser.getRecords();
        List<Student> students = new ArrayList<>();
        csvRecords.forEach(rec -> {
            students.add(new Student(
                Long.parseLong(rec.get("id")),
                rec.get("name"),
                Integer.parseInt(rec.get("age")),
                rec.get("course")
            ));
        });

        System.out.println(students);
    }
}

class Student {
    private long id;
    private String name;
    private int age;
    private String course;

    public Student(long id, String name, int age, String course) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", course='" + course + '\'' +
            '}';
    }
}
