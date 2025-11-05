package com.example.tp5.Bean;
public class User {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String major;

    // Constructeur vide (nécessaire pour certaines utilisations)
    public User() {
    }

    // Constructeur paramétré
    public User(int id, String firstName, String lastName, int age, String major) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.major = major;
    }

    // --- Getters ---

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getMajor() {
        return major;
    }

    // --- Setters ---

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
