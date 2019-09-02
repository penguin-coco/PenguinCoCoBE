package com.penguin.penguincoco.model.problem;


public enum Language {
    JAVA("java"),
    PYTHON("python");

    private String description;

    Language(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
