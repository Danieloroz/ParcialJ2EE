package org.bmx.parcial;

public class Competitor {
    private static int idCounter = 1; // ID auto-incremental para los competidores
    private int id;
    private String firstName;
    private String lastName;
    private String country;
    private int r1;
    private int r2;
    private int r3;
    private int totalScore;

    public Competitor(String firstName, String lastName, String country, int r1, int r2, int r3) {
        this.id = idCounter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        updateTotalScore();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public int getTotalScore() {
        return r1 + r2 + r3;
    }
    void updateTotalScore() {
        this.totalScore = this.r1 + this.r2 + this.r3;
    }



    @Override
    public String toString() {
        return "Competitor [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", country=" + country
                + ", r1=" + r1 + ", r2=" + r2 + ", r3=" + r3 + ", totalScore=" + getTotalScore() + "]";
    }

}