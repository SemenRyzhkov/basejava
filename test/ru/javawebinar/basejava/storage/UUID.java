package ru.javawebinar.basejava.storage;

public enum UUID {
    UUID_1("uuid1"),
    UUID_2("uuid2"),
    UUID_3("uuid3"),
    UUID_4("uuid4");

    private String title;

    UUID(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
