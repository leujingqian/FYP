package com.example.myapplication1.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Classes {

    @SerializedName("admins")
    @Expose
    private List<Admin> admins = null;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("tutorial_group")
    @Expose
    private String tutorialGroup;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public List<Admin> getAdmins() {
        return admins;
    }

    public void setAdmins(List<Admin> admins) {
        this.admins = admins;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTutorialGroup() {
        return tutorialGroup;
    }

    public void setTutorialGroup(String tutorialGroup) {
        this.tutorialGroup = tutorialGroup;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Classes(String classId) {
        this.classId = classId;
    }
}
