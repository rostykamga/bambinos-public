package com.lesbambinos.controller.caisse;


import com.lesbambinos.auth.BambinosSecurityManager;
import com.lesbambinos.entity.Employee;
import com.lesbambinos.entity.Installment;
import com.lesbambinos.entity.InstallmentPerLevel;
import com.lesbambinos.entity.Invoice;
import com.lesbambinos.entity.InvoiceData;
import com.lesbambinos.entity.Payment;
import com.lesbambinos.entity.PaymentPK;
import com.lesbambinos.model.InvoiceModel;
import com.lesbambinos.model.PaymentModel;
import com.lesbambinos.model.VoucherModel;
import com.lesbambinos.util.AppUtils;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class InvoiceController implements Initializable {
    
    @FXML protected TextField totalAmountField, paidAmountField, retailField;
    protected PaymentModel paymentModel;
    protected InvoiceModel invoiceModel;
    protected VoucherModel voucherModel;
    
    protected InvoiceData invoiceData;
    

    protected double xOffset = 0;
    protected double yOffset = 0;
    protected boolean cancelled=false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        invoiceModel = new InvoiceModel();
        voucherModel = new VoucherModel();
        paymentModel = new PaymentModel();
    }
    
    public void setData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
        totalAmountField.setText(String.valueOf(this.invoiceData.getNetpayable()));
    }
    
    protected String generateInvoiceId(){
        Employee emp = BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        StringBuilder sb = new StringBuilder(formatter.format(LocalDateTime.now()));
        sb.append(String.format("%03d", emp.getId()));
        return sb.toString();
    }
    
    @FXML
    protected void paidAmountTyped(KeyEvent event){
        if(AppUtils.isValidPositiveDouble(paidAmountField.getText())){
            double retail =  Double.parseDouble(paidAmountField.getText()) - Double.parseDouble(totalAmountField.getText());
            retailField.setText(String.valueOf(retail));
        }
        else
            retailField.setText("N/A");
    }
    
    @FXML
    public void closeAction(ActionEvent event) {
        cancelled = true;
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    
    public boolean windowClosed(){
        return cancelled;
    }
    
    @FXML
    public void confirmAction(ActionEvent event) throws Exception {

        if (validateInput()) {

            Employee employee = BambinosSecurityManager.getAuthenticatedEmployee().getEmployeeEntity();
            String invoiceId = generateInvoiceId();

            Invoice invoice = new Invoice(
                    invoiceId, 
                    invoiceData.getTotal(),
                    invoiceData.getNetpayable(),
                    Double.parseDouble(paidAmountField.getText()),
                    Double.parseDouble(retailField.getText()),
                    invoiceData.getVoucher() == null? "" : invoiceData.getVoucher().getCode(),
                    invoiceData.getVoucher() == null? 0.0 : invoiceData.getVoucher().getVoucherValue(),
                    true,
                    employee,
                    new Date()
                    );
                   
            invoiceModel.save(invoice);
            
            // update the voucher
            if(invoiceData.getVoucher() != null){
                invoiceData.getVoucher().setVoucherValue(0.0);
                voucherModel.update(invoiceData.getVoucher());
            }

            for (InstallmentPerLevel i : invoiceData.getInstallementsList()) {

                Installment inst = i.getInstPk().getInstallment();
                
                Payment payment = new Payment(
                        new PaymentPK(invoiceData.getStudent(), inst),
                        i.getAmount(),
                        employee,
                        invoice,
                        new Date()
                );

                paymentModel.save(payment);
            }
            cancelled= false;
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }

    }

    private boolean validateInput() {

        String errorMessage = "";

        double payable = Double.parseDouble(totalAmountField.getText());
        if (!AppUtils.isValidPositiveDouble(paidAmountField.getText())) {
            errorMessage += "Entrée invalide!\n";
        } 
        else if (Double.parseDouble(paidAmountField.getText()) < payable) {
            errorMessage += "Montant insuffisant!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText("Entrée Invalide");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            paidAmountField.setText("");

            return false;
        }
    }    
}
