/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 *
 * @author rostand
 */
@Entity
@Table(name = "ticketprintsettings")
public class TicketPrintSetting implements AbstractEntity {
    
    public static final int PORTRAY_ORIENTATION = 0;
    public static final int LANDSCAPE_ORIENTATION = 1;
    
    public static final int OUTPUT_PDF = 0;
    public static final int OUTPUT_PRINTER = 1;
    public static final int OUTPUT_BOTH = 2;
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "titleFont")
    private String titleFont;
    @Column(name = "header")
    private String header;
    @Column(name = "headerFont")
    private String headerFont;
    @Column(name = "footer")
    private String footer;
    @Column(name = "footerFont")
    private String footerFont;
    @Column(name = "printer")
    private String printer;
    @Column(name = "printOutput")
    private int printOutput;
    @Column(name = "cutOption")
    private int cutOption;
    @Column(name = "cashDrawerOption")
    private int cashDrawerOption;
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    @Column(name = "logoWidth")
    private int logoWidth;
    @Column(name = "logoHeight")
    private int logoHeight;
    @Column(name = "paperWidth")
    private double paperWidth;

    

    public TicketPrintSetting() {
       id = null;
       logo = null;
       title = "Ticket Title";
       titleFont ="";
       header = "Ticket header";
       headerFont = null;
       footer = "Ticket footer";
       footerFont = null;
       printer = null;
       cutOption = 1; // partial cut
       cashDrawerOption = 0;// no cash drawer
       printOutput = 2;
       logoWidth = 120;
       logoHeight = 80;
       paperWidth= 80.0;
    }

    public TicketPrintSetting(String title, String titleFont, String header, String headerFont, String footer, String footerFont, String printer, int printOutput, int cutOption, int cashDrawerOption, byte[] logo, int logoWidth, int logoHeight, double paperWidth) {
        this.title = title;
        this.titleFont = titleFont;
        this.header = header;
        this.headerFont = headerFont;
        this.footer = footer;
        this.footerFont = footerFont;
        this.printer = printer;
        this.printOutput = printOutput;
        this.cutOption = cutOption;
        this.cashDrawerOption = cashDrawerOption;
        this.logo = logo;
        this.logoWidth = logoWidth;
        this.logoHeight = logoHeight;
        this.paperWidth = paperWidth;
    }

    public TicketPrintSetting(Long id, String title, String titleFont, String header, String headerFont, String footer, String footerFont, String printer, int printOutput, int cutOption, int cashDrawerOption, byte[] logo, int logoWidth, int logoHeight, double paperWidth) {
        this.id = id;
        this.title = title;
        this.titleFont = titleFont;
        this.header = header;
        this.headerFont = headerFont;
        this.footer = footer;
        this.footerFont = footerFont;
        this.printer = printer;
        this.printOutput = printOutput;
        this.cutOption = cutOption;
        this.cashDrawerOption = cashDrawerOption;
        this.logo = logo;
        this.logoWidth = logoWidth;
        this.logoHeight = logoHeight;
        this.paperWidth = paperWidth;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(String titleFont) {
        this.titleFont = titleFont;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeaderFont() {
        return headerFont;
    }

    public void setHeaderFont(String headerFont) {
        this.headerFont = headerFont;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getFooterFont() {
        return footerFont;
    }

    public void setFooterFont(String footerFont) {
        this.footerFont = footerFont;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public int getPrintOutput() {
        return printOutput;
    }

    public void setPrintOutput(int printOutput) {
        this.printOutput = printOutput;
    }

    public int getCutOption() {
        return cutOption;
    }

    public void setCutOption(int cutOption) {
        this.cutOption = cutOption;
    }

    public int getCashDrawerOption() {
        return cashDrawerOption;
    }

    public void setCashDrawerOption(int cashDrawerOption) {
        this.cashDrawerOption = cashDrawerOption;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public int getLogoWidth() {
        return logoWidth;
    }

    public void setLogoWidth(int logoWidth) {
        this.logoWidth = logoWidth;
    }

    public int getLogoHeight() {
        return logoHeight;
    }

    public void setLogoHeight(int logoHeight) {
        this.logoHeight = logoHeight;
    }

    public double getPaperWidth() {
        return paperWidth;
    }

    public void setPaperWidth(double paperWidth) {
        this.paperWidth = paperWidth;
    }

    @Override
    public String asString() {
        return "TicketPrintSetting{" + "id=" + id + ", title=" + title + ", titleFont=" + titleFont + ", header=" + header + ", headerFont=" + headerFont + ", footer=" + footer + ", footerFont=" + footerFont + ", printer=" + printer + ", printOutput=" + printOutput + ", cutOption=" + cutOption + ", cashDrawerOption=" + cashDrawerOption + ", logoWidth=" + logoWidth + ", logoHeight=" + logoHeight + ", paperWidth=" + paperWidth + '}';
    }
    

}
