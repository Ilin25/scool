package ru.ilin.school.domain;

public class Group {

    private int id;
    private String name;

    public Group() {
    }

    public Group(int group_id, String group_name) {
    }

    public int getId() {
        return id;
    }

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }
}
