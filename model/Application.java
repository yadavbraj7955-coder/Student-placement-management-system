package model;

public class Application {

    public int studentId;
    public int companyId;
    public String status;

    public Application(int studentId, int companyId) {

        this.studentId = studentId;
        this.companyId = companyId;
        this.status = "Applied";
    }

    public void display() {

        System.out.println("Student ID: " + studentId);
        System.out.println("Company ID: " + companyId);
        System.out.println("Status: " + status);

        System.out.println("--------------------");
    }
}