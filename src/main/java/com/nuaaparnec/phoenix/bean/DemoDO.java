package com.nuaaparnec.phoenix.bean;

public class DemoDO {
    private Long id;
    private String firstname;
    private String lastname;
    private Integer age;

    public DemoDO() {
    }

    public DemoDO(Long id) {
        this.id = id;
    }

    public DemoDO(Long id, String firstname, String lastname, Integer age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
