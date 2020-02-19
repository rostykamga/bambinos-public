package com.lesbambinos.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "employees")
public class Employee implements AbstractEntity {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username")
    private String userName;
    @Column(name = "employeeRole")
    private String employeeRole;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "lastconnection")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastconnection;
    
    public Employee(){
    
    }

    public Employee(String firstName, String lastName, String userName, String employeeRole, String password, String phone, String address, Date lastconnection) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.employeeRole = employeeRole;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.lastconnection = lastconnection;
    }

    public Employee(Long id, String firstName, String lastName, String userName, String employeeRole, String password, String phone, String address, Date lastconnection) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.employeeRole = employeeRole;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.lastconnection = lastconnection;
    }
    
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }


    public Date getLastconnection() {
        return lastconnection;
    }

    public void setLastconnection(Date lastconnection) {
        this.lastconnection = lastconnection;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Employee other = (Employee) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String asString() {
        return "Employee{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", role=" + employeeRole + ", password=" + password + ", phone=" + phone + ", address=" + address + ", lastconnection=" + lastconnection + '}';
    }
    
}
