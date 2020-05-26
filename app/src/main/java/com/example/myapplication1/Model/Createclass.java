package com.example.myapplication1.Model;

public class Createclass {
    private String name;
    private String section;
    private String tutorial_group;

    public Createclass(String name, String section, String tutorial_group) {
        this.name = name;
        this.section = section;
        this.tutorial_group = tutorial_group;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getTutorial_group() {
        return tutorial_group;
    }

    public void setTutorial_group(String tutorial_group) {
        this.tutorial_group = tutorial_group;
    }
}
