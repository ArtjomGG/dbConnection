package com.bta.domain;

public class Book {

    private Long id;
    private String name;
    private String description;
    private Integer releasYear;

    public Book(Long id, String name, String description, Integer releasYear) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releasYear = releasYear;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleasYear() {
        return releasYear;
    }

    public void setReleasYear(Integer releasYear) {
        this.releasYear = releasYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releasYear=" + releasYear +
                '}';
    }
}
