/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lesbambinos.util;

import com.lesbambinos.entity.Subsystem;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.Printer;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javax.imageio.ImageIO;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 *
 * @author rostand
 */
public class AppUtils {
    
    public static boolean isEmptyOrNullString(String s){
        if(s == null)
            return true;
        return s.trim().isEmpty();
    }
    
    public static boolean isValidPositiveDouble(String s){
        if(isEmptyOrNullString(s))
            return false;
        try{
            return Double.parseDouble(s) > 0;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }
    
    public static boolean isValidPositiveInteger(String s){
        if(isEmptyOrNullString(s))
            return false;
        try{
            return Integer.parseInt(s) > 0;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }
    
    public static boolean isValidDouble(String s){
        if(isEmptyOrNullString(s))
            return false;
        try{
            Double.parseDouble(s);
            return true;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }
    
    public static String encrypt(String plaintext) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("QitechPos");
        return textEncryptor.encrypt(plaintext);
    }
    
    public static String decrypt(String secret) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("QitechPos");
        return textEncryptor.decrypt(secret);
    }
    
    public static double centimeter2Pixel(double cm){
        Screen screen = Screen.getPrimary();
        double dpi = screen.getDpi();
        
        return (dpi*cm)/2.54;
    }
    
    public static List<String> getInstalledPrinters(){
        List result = new ArrayList();
        Printer.getAllPrinters().forEach(p->result.add(p.getName()));
        return result;
    }
    
   
    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }
    
//    public static Style fontStyle2PosStyle(FontStyle fstyle){
//        Style outcome = new Style();
//        
//        // set font
//        switch(fstyle.getFontFamily()){
//            case "Font_A":
//                outcome.setFontName(Style.FontName.Font_A_Default);
//                break;
//            case "Font_B":
//                outcome.setFontName(Style.FontName.Font_B);
//                break;
//            default:
//                outcome.setFontName(Style.FontName.Font_C);
//        }
//        
//        // set bold or not
//        outcome.setBold(fstyle.getFontWeight() == 1);
//        
//        // set font size
//        String width = "_"+fstyle.getFontWidth();
//        String height = "_"+fstyle.getFontheight();
//        outcome.setFontSize(Style.FontSize.valueOf(width), Style.FontSize.valueOf(height));
//        
//        //Justification
//        switch(fstyle.getJustification()){
//            case 0: 
//                outcome.setJustification(EscPosConst.Justification.Left_Default);
//                break;
//            case 1: 
//                outcome.setJustification(EscPosConst.Justification.Center);
//                break;
//            default:
//                outcome.setJustification(EscPosConst.Justification.Right);
//        }
//        
//        //underlining
//        switch(fstyle.getUnderlining()){
//            case 0: 
//                outcome.setUnderline(Style.Underline.None_Default);
//                break;
//            case 1: 
//                outcome.setUnderline(Style.Underline.OneDotThick);
//                break;
//            default:
//                outcome.setUnderline(Style.Underline.TwoDotThick);
//        }
//        
//        outcome.setLineSpacing(fstyle.getLinespacing());
//        
//        return outcome;
//    }
    
    public static int calculateLineLength(double pageWidth){
        
        return (int)((pageWidth * 42) /80);
    }
    
        /**
     * 
     * @param str line family|weight|size
     * @return 
     */
    public static Font parseFont(String str){
        if(isEmptyOrNullString(str))
            return Font.getDefault();
        
        String[] parts = str.split("#");
        try{
            return Font.font(parts[0], FontWeight.findByName(parts[1]), Double.parseDouble(parts[2]));
        }
        catch(Exception ex){
            return Font.getDefault();
        }
    }
    
    public static String font2String(Font f){
        return f.getFamily()+"#"+f.getStyle()+"#"+f.getSize();
    }
    
     public static byte[] imageView2ByteArray(ImageView iv){
        if(iv.getImage() == null)
            return null;
        
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(iv.getImage(), null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", s);
            byte[] res  = s.toByteArray();
            s.close(); //especially if you are using a different output stream.
            return res;
        } catch (IOException ex) {
            Logger.getLogger(AppUtils.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
     
    public static String generateRegistrationNumber(Subsystem section, Long sequence){
        String prefix = section.getRegprefix();
        String middle = ""+LocalDate.now().getYear();
        return middle.substring(2, middle.length()) + prefix  + String.format("%04d", sequence);
    }
}
