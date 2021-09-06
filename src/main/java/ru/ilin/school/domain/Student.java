package ru.ilin.school.domain;

import java.util.List;

public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private Group group;
    private List<Course> courses;

    public Student(Builder builder) {
        this.id = builder.id;
        this.group = builder.group;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.courses = builder.courses;
    }

    public static Builder builder(){
        return  new Builder();
    }

    public int getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Course> getCourse() {
        return courses;
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private Group group;
        private List<Course> courses;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withGroup(Group group) {
            this.group = group;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withCourse(List<Course> course) {
            this.courses = course;
            return this;
        }

        public Student build(){
            return new Student(this);
        }
    }
}
