/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.util.List;

/**
 *
 * @author Rostand
 */
public class InvoiceData {
    
    private Student student;
    private double total;
    private double netpayable;
    private List<InstallmentPerLevel> installementsList;
    private Voucher voucher;

    public InvoiceData(Student student, double total, double netpayable, List<InstallmentPerLevel> installementsList, Voucher voucher) {
        this.student = student;
        this.total = total;
        this.netpayable = netpayable;
        this.installementsList = installementsList;
        this.voucher = voucher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getNetpayable() {
        return netpayable;
    }

    public void setNetpayable(double netpayable) {
        this.netpayable = netpayable;
    }

    public List<InstallmentPerLevel> getInstallementsList() {
        return installementsList;
    }

    public void setInstallementsList(List<InstallmentPerLevel> installementsList) {
        this.installementsList = installementsList;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void setVoucher(Voucher voucher) {
        this.voucher = voucher;
    }

   
}
