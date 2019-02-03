

package com.ssn.ssn_yrc.model;

public class User {

    private String id;
    private String name;
    private String registerNo;
    private String beOrMe;
    private String department;
    private int year;
    private String dateOfBirth;
    private String phone;
    private String bloodGroup;
    private String lastDonatedDate;
    private String member;
    private String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String id, String name, String registerNo, String beOrMe, String department,
                int year, String dateOfBirth, String phone, String bloodGroup,
                String lastDonatedDate, String member, String email) {
        this.id = id;
        this.name = name;
        this.registerNo = registerNo;
        this.beOrMe = beOrMe;
        this.department = department;
        this.year = year;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.lastDonatedDate = lastDonatedDate;
        this.member = member;
        this.email = email;
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

    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public String getBeOrMe() {
        return beOrMe;
    }

    public void setBeOrMe(String beOrMe) {
        this.beOrMe = beOrMe;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getLastDonatedDate() {
        return lastDonatedDate;
    }

    public void setLastDonatedDate(String lastDonatedDate) {
        this.lastDonatedDate = lastDonatedDate;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", registerNo='" + registerNo + '\'' +
                ", department='" + department + '\'' +
                ", year=" + year +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", phone='" + phone + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", lastDonatedDate='" + lastDonatedDate + '\'' +
                ", member=" + member +
                ", email='" + email + '\'' +
                '}';
    }
}
