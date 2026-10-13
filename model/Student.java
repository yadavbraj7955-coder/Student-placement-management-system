package model;

public class Student {

    public int id;
    public String name;
    public String branch;
    public double cgpa;
    public String skills;

    public Student(
            int id,
            String name,
            String branch,
            double cgpa,
            String skills) {

        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
        this.skills = skills;
    }

    public void display() {

        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Branch: " + branch);
        System.out.println("CGPA: " + cgpa);
        System.out.println("Skills: " + skills);

        System.out.println("--------------------");
    }
}