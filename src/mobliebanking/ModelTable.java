/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobliebanking;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class ModelTable {
    
    String FirstName, LastName, Email, UserName, Gender, Country ,PhoneNumber ,Address, Reference, Password ,SecurityQues;
    Date BirthDate;
    double Credit;
    ModelTable(String FirstName, String LastName, String Email, String UserName, String Gender, String Country, String PhoneNumber, Date BirthDate, String Address, String Reference, String Password, double Credit, String SecurityQues) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Email = Email;
        this.UserName = UserName;
        this.Gender = Gender;
        this.Country = Country;
        this.PhoneNumber = PhoneNumber;
        this.BirthDate = BirthDate;
        this.Address = Address;
        this.Reference = Reference;
        this.Password = Password;
        this.Credit = Credit;
        this.SecurityQues=SecurityQues;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getReference() {
        return Reference;
    }

    public void setReference(String Reference) {
        this.Reference = Reference;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSecurityQues() {
        return SecurityQues;
    }

    public void setSecurityQues(String SecurityQues) {
        this.SecurityQues = SecurityQues;
    }

    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date BirthDate) {
        this.BirthDate = BirthDate;
    }

    public double getCredit() {
        return Credit;
    }

    public void setCredit(double Credit) {
        this.Credit = Credit;
    }

     
}
