package model;

public class Company {

    public int companyId;
    public String companyName;
    public double requiredCgpa;
    public String requiredSkills;

    public Company(
            int companyId,
            String companyName,
            double requiredCgpa,
            String requiredSkills) {

        this.companyId = companyId;
        this.companyName = companyName;
        this.requiredCgpa = requiredCgpa;
        this.requiredSkills = requiredSkills;
    }

    public void display() {

        System.out.println("Company ID: " + companyId);
        System.out.println("Company Name: " + companyName);
        System.out.println("Required CGPA: " + requiredCgpa);
        System.out.println("Required Skills: " + requiredSkills);

        System.out.println("--------------------");
    }
}