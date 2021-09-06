package ru.ilin.school.domain;

import java.util.List;

public class Course {

    private final int id;
    private final String name;
    private final String description;
    private final List<Student> students;

    public Course(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.students = builder.students;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Student> getStudents() {
        return students;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private int id;
        private String name;
        private String description;
        private List<Student> students;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withStudents(List<Student> students) {
            this.students = students;
            return this;
        }

        public Course build(){
            return new Course(this);
        }
    }
}
