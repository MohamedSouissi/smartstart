/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartstart.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.smartstart.entities.Contract;
import com.smartstart.entities.UserSession;
import com.smartstart.entities.fos_user;
import com.smartstart.services.ContractServiceImpl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author diabl
 */
public class ContractCellController extends ListCell<Contract> {

    @FXML
    private Label titles;
    @FXML
    private Label description;
    @FXML
    private Label username;
    @FXML
    private Label sum;
    @FXML
    private Label start;
    @FXML
    private Label finish;

    private FXMLLoader mLLoader;

    final Tooltip tooltip = new Tooltip();
    @FXML
    private AnchorPane gridPane;
    @FXML
    private Button export;
    @FXML
    private Button remove;
    @FXML
    private Button payer;
    fos_user u;
    public static Contract c = new Contract();

    @Override
    protected void updateItem(Contract student, boolean empty) {

        try {
            u = UserSession.getInstance(new fos_user()).getUser();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeOppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.updateItem(student, empty);

        if (empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/com/smartstart/gui/ContractCellGui.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (u.getRoles().equals("freelancer")) {
                payer.setDisable(true);
                payer.setVisible(false);
                remove.setDisable(true);
                remove.setVisible(false);
                export.setDisable(true);
                export.setVisible(false);
            }
            if (!student.getPayment_method().equals("en ligne")) {
                payer.setDisable(true);
                payer.setVisible(false);
            }

            description.setText(student.getApplication().getOpportunity().getJob_description());
            username.setText(student.getUser().getUsername());
            sum.setText(String.valueOf(student.getSum()));

            start.setText(student.getStart_date().toString());

            finish.setText(student.getFinish_date().toString());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date firstDate;
            try {
                firstDate = sdf.parse(student.getStart_date().toString());
                Date secondDate = sdf.parse(student.getFinish_date().toString());
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                tooltip.setText("Duration of Contract :" + diff + "  days");
                titles.setTooltip(tooltip);
            } catch (ParseException ex) {
                Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
            }

            remove.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {

                    if (alertConfirmation() == true) {
                        try {
                            ContractServiceImpl cs = new ContractServiceImpl();
                            cs.removeContract(student.getId_contract());

                        } catch (SQLException ex) {
                            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Parent tableViewContract;
                        try {
                            tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
                            Scene tableViewContractScene = new Scene(tableViewContract);
                            Stage window = (Stage) ((Node) t.getSource()).getScene().getWindow();
                            window.setScene(tableViewContractScene);
                        } catch (IOException ex) {
                            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }

                }

            });
            payer.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {

                    c = student;

                    try {
                        FXMLLoader detail = new FXMLLoader(getClass().getResource("/com/smartstart/gui/PaymentGui.fxml"));
                        Parent root2 = (Parent) detail.load();
                        Stage stage1 = new Stage();
                        stage1.setScene(new Scene(root2));
                        stage1.show();

                    } catch (IOException ex) {
                        Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

            export.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    try {
                        Document document = new Document();
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("MyContract.pdf"));
                        document.open();

                        document.add(new Paragraph("Development Contract\n"
                                + "\n"
                                + "This Contract is between " + student.getApplication().getFreelancer().getName() + " " + student.getApplication().getFreelancer().getLast_name() + " and " + u.getName()+" "+u.getLast_name()+ " .\n"
                                + "\n"
                                + "The Contract is dated " + new Date().toString() + ".\n"
                                + "\n"
                                + "1. WORK AND PAYMENT.\n"
                                + "\n"
                                + "1.1 Project. The Client is hiring the Developer to do the following: " + student.getApplication().getOpportunity().getJob_description() + ".\n"
                                + "\n"
                                + "1.2 Schedule. The Developer will begin work on " + student.getStart_date().toString() + " and must finish the work by " + student.getFinish_date().toString() + ". \n"
                                + "\n"
                                + "1.3 Payment. The Client will pay the Developer a flat fee of " + String.valueOf(student.getSum()) + " (DTN). \n"
                                + "1.4 Expenses. The Client will reimburse the Developer's expenses. Expenses do not need to be pre-approved by the Client. \n"
                                + "\n"
                                + "1.5 Invoices. The Developer will invoice the Client at the end of the project. The Client agrees to pay the amount owed within 15 days of receiving the invoice. Payment after that date will incur a late fee of 0.0% per month on the outstanding amount. \n"
                                + "\n"
                                + "1.6 Support. The Developer will not provide support for any deliverable once the Client accepts it, unless otherwise agreed in writing.\n "
                                + "2. OWNERSHIP AND LICENSES.\n"
                                + "\n"
                                + "2.1 Client Owns All Work Product. As part of this job, the Developer is creating “work product” for the Client. To avoid confusion, work product is the finished product, as well as drafts, notes, materials, mockups, hardware, designs, inventions, patents, code, and anything else that the Developer works on—that is, conceives, creates, designs, develops, invents, works on, or reduces to practice—as part of this project, whether before the date of this Contract or after. The Developer hereby gives the Client this work product once the Client pays for it in full. This means the Developer is giving the Client all of its rights, titles, and interests in and to the work product (including intellectual property rights), and the Client will be the sole owner of it. The Client can use the work product however it wants or it can decide not to use the work product at all. The Client, for example, can modify, destroy, or sell it, as it sees fit. \n"
                                + "\n"
                                + "2.2 Developer’s Use Of Work Product. Once the Developer gives the work product to the Client, the Developer does not have any rights to it, except those that the Client explicitly gives the Developer here.\n"
                                + "\n"
                                + "2.3 Developer’s Help Securing Ownership. In the future, the Client may need the Developer’s help to show that the Client owns the work product or to complete the transfer. The Developer agrees to help with that. For example, the Developer may have to sign a patent application. The Client will pay any required expenses for this. If the Client can’t find the Developer, the Developer agrees that the Client can act on the Developer’s behalf to accomplish the same thing. The following language gives the Client that right: if the Client can’t find the Developer after spending reasonable effort trying to do so, the Developer hereby irrevocably designates and appoints the Client as the Developer’s agent and attorney-in-fact, which appointment is coupled with an interest, to act for the Developer and on the Developer’s behalf to execute, verify, and file the required documents and to take any other legal action to accomplish the purposes of paragraph 2.1 (Client Owns All Work Product). \n"
                                + "\n"
                                + "2.4 Developer’s IP That Is Not Work Product. During the course of this project, the Developer might use intellectual property that the Developer owns or has licensed from a third party, but that does not qualify as “work product.” This is called “background IP.” Possible examples of background IP are pre-existing code, type fonts, properly-licensed stock photos, and web application tools. The Developer is not giving the Client this background IP. But, as part of the Contract, the Developer is giving the Client a right to use and license (with the right to sublicense) the background IP to develop, market, sell, and support the Client’s products and services. The Client may use this background IP worldwide and free of charge, but it cannot transfer its rights to the background IP (except as allowed in Section 11.1 (Assignment)). The Client cannot sell or license the background IP separately from its products or services. The Developer cannot take back this grant, and this grant does not end when the Contract is over. \n"
                                + "\n"
                                + "2.5 Developer’s Right To Use Client IP. The Developer may need to use the Client’s intellectual property to do its job. For example, if the Client is hiring the Developer to build a website, the Developer may have to use the Client’s logo. The Client agrees to let the Developer use the Client’s intellectual property and other intellectual property that the Client controls to the extent reasonably necessary to do the Developer’s job. Beyond that, the Client is not giving the Developer any intellectual property rights, unless specifically stated otherwise in this Contract. \n"
                                + "\n"
                                + "3. COMPETITIVE ENGAGEMENTS. The Developer won’t work for a competitor of the Client until this Contract ends. To avoid confusion, a competitor is any third party that develops, manufactures, promotes, sells, licenses, distributes, or provides products or services that are substantially similar to the Client’s products or services. A competitor is also a third party that plans to do any of those things. The one exception to this restriction is if the Developer asks for permission beforehand and the Client agrees to it in writing. If the Developer uses employees or subcontractors, the Developer must make sure they follow the obligations in this paragraph, as well. \n"
                                + "\n"
                                + "4. NON-SOLICITATION. Until this Contract ends, the Developer won’t: (a) encourage Client employees or service providers to stop working for the Client; (b) encourage Client customers or clients to stop doing business with the Client; or (c) hire anyone who worked for the Client over the 12-month period before the Contract ended. The one exception is if the Developer puts out a general ad and someone who happened to work for the Client responds. In that case, the Developer may hire that candidate. The Developer promises that it won’t do anything in this paragraph on behalf of itself or a third party. \n"
                                + "\n"
                                + "5. REPRESENTATIONS.\n"
                                + "\n"
                                + "5.1 Overview. This section contains important promises between the parties. \n"
                                + "\n"
                                + "5.2 Authority To Sign. Each party promises to the other party that it has the authority to enter into this Contract and to perform all of its obligations under this Contract. \n"
                                + "\n"
                                + "5.3 Developer Has Right To Give Client Work Product. The Developer promises that it owns the work product, that the Developer is able to give the work product to the Client, and that no other party will claim that it owns the work product. If the Developer uses employees or subcontractors, the Developer also promises that these employees and subcontractors have signed contracts with the Developer giving the Developer any rights that the employees or subcontractors have related to the Developer’s background IP and work product. \n"
                                + "\n"
                                + "5.4 Developer Will Comply With Laws. The Developer promises that the manner it does this job, its work product, and any background IP it uses comply with applicable U.S. and foreign laws and regulations. \n"
                                + "\n"
                                + "5.5 Work Product Does Not Infringe. The Developer promises that its work product does not and will not infringe on someone else’s intellectual property rights, that the Developer has the right to let the Client use the background IP, and that this Contract does not and will not violate any contract that the Developer has entered into or will enter into with someone else. \n"
                                + "\n"
                                + "5.6 Client Will Review Work. The Client promises to review the work product, to be reasonably available to the Developer if the Developer has questions regarding this project, and to provide timely feedback and decisions. \n"
                                + "\n"
                                + "5.7 Client-Supplied Material Does Not Infringe. If the Client provides the Developer with material to incorporate into the work product, the Client promises that this material does not infringe on someone else’s intellectual property rights.\n"
                                + "\n"
                                + "6. TERM AND TERMINATION. This Contract ends on March 25, 2019, unless the Client or the Developer ends the contract before that time. Either party may end this Contract for any reason by sending an email or letter to the other party, informing the recipient that the sender is ending the Contract and that the Contract will end in 7 days. The Contract officially ends once that time has passed. The party that is ending the Contract must provide notice by taking the steps explained in Section 11.4. The Developer must immediately stop working as soon as it receives this notice, unless the notice says otherwise. The Client will pay the Developer for the work done up until when the Contract ends and will reimburse the Developer for any agreed-upon, non-cancellable expenses. The following sections don’t end even after the Contract ends: 2 (Ownership and Licenses); 3 (Competitive Engagements); 4 (Non-Solicitation); 5 (Representations); 8 (Confidential Information); 9 (Limitation of Liability); 10 (Indemnity); and 11 (General). \n"
                                + "\n"
                                + "7. INDEPENDENT CONTRACTOR. The Client is hiring the Developer as an independent contractor. The following statements accurately reflect their relationship:\n"
                                + "\n"
                                + "- The Developer will use its own equipment, tools, and material to do the work.\n"
                                + "- The Client will not control how the job is performed on a day-to-day basis. Rather, the Developer is responsible for determining when, where, and how it will carry out the work.\n"
                                + "- The Client will not provide the Developer with any training.\n"
                                + "- The Client and the Developer do not have a partnership or employer-employee relationship.\n"
                                + "- The Developer cannot enter into contracts, make promises, or act on behalf of the Client.\n"
                                + "- The Developer is not entitled to the Client’s benefits (e.g., group insurance, retirement benefits, retirement plans, vacation days).\n"
                                + "- The Developer is responsible for its own taxes.\n"
                                + "- The Client will not withhold social security and Medicare taxes or make payments for disability insurance, unemployment insurance, or workers compensation for the Developer or any of the Developer’s employees or subcontractors. \n"
                                + "\n"
                                + "8. CONFIDENTIAL INFORMATION.\n"
                                + "\n"
                                + "8.1 Overview. This Contract imposes special restrictions on how the Client and the Developer must handle confidential information. These obligations are explained in this section. \n"
                                + "\n"
                                + "8.2 The Client’s Confidential Information. While working for the Client, the Developer may come across, or be given, Client information that is confidential. This is information like customer lists, business strategies, research & development notes, statistics about a website, and other information that is private. The Developer promises to treat this information as if it is the Developer’s own confidential information. The Developer may use this information to do its job under this Contract, but not for anything else. For example, if the Client lets the Developer use a customer list to send out a newsletter, the Developer cannot use those email addresses for any other purpose. The one exception to this is if the Client gives the Developer written permission to use the information for another purpose, the Developer may use the information for that purpose, as well. When this Contract ends, the Developer must give back or destroy all confidential information, and confirm that it has done so. The Developer promises that it will not share confidential information with a third party, unless the Client gives the Developer written permission first. The Developer must continue to follow these obligations, even after the Contract ends. The Developer’s responsibilities only stop if the Developer can show any of the following: (i) that the information was already public when the Developer came across it; (ii) the information became public after the Developer came across it, but not because of anything the Developer did or didn’t do; (iii) the Developer already knew the information when the Developer came across it and the Developer didn’t have any obligation to keep it secret; (iv) a third party provided the Developer with the information without requiring that the Developer keep it a secret; or (v) the Developer created the information on its own, without using anything belonging to the Client. \n"
                                + "\n"
                                + "8.3 Third-Party Confidential Information. It’s possible the Client and the Developer each have access to confidential information that belongs to third parties. The Client and the Developer each promise that it will not share with the other party confidential information that belongs to third parties, unless it is allowed to do so. If the Client or the Developer is allowed to share confidential information with the other party and does so, the sharing party promises to tell the other party in writing of any special restrictions regarding that information. \n"
                                + "\n"
                                + "9. LIMITATION OF LIABILITY. Neither party is liable for breach-of-contract damages that the breaching party could not reasonably have foreseen when it entered this Contract. \n"
                                + "\n"
                                + "10. INDEMNITY.\n"
                                + "\n"
                                + "10.1 Overview. This section transfers certain risks between the parties if a third party sues or goes after the Client or the Developer or both. For example, if the Client gets sued for something that the Developer did, then the Developer may promise to come to the Client’s defense or to reimburse the Client for any losses. \n"
                                + "\n"
                                + "10.2 Client Indemnity. In this Contract, the Developer agrees to indemnify the Client (and its affiliates and its and their directors, officers, employees, and agents) from and against all liabilities, losses, damages, and expenses (including reasonable attorneys’ fees) related to a third-party claim or proceeding arising out of: (i) the work the Developer has done under this Contract; (ii) a breach by the Developer of its obligations under this Contract; or (iii) a breach by the Developer of the promises it is making in Section 5 (Representations). \n"
                                + "\n"
                                + "10.3 Developer Indemnity. In this Contract, the Client agrees to indemnify the Developer (and its affiliates and its and their directors, officers, employees, and agents) from and against liabilities, losses, damages, and expenses (including reasonable attorneys’ fees) related to a third-party claim or proceeding arising out of a breach by the Client of its obligations under this Contract. \n"
                                + "\n"
                                + "11. GENERAL.\n"
                                + "\n"
                                + "11.1 Assignment. This Contract applies only to the Client and the Developer. The Developer cannot assign its rights or delegate its obligations under this Contract to a third-party (other than by will or intestate), without first receiving the Client’s written permission. In contrast, the Client may assign its rights and delegate its obligations under this Contract without the Developer’s permission. This is necessary in case, for example, another Client buys out the Client or if the Client decides to sell the work product that results from this Contract. \n"
                                + "\n"
                                + "11.2 Arbitration. As the exclusive means of initiating adversarial proceedings to resolve any dispute arising under this Contract, a party may demand that the dispute be resolved by arbitration administered by the American Arbitration Association in accordance with its commercial arbitration rules. \n"
                                + "\n"
                                + "11.3 Modification; Waiver. To change anything in this Contract, the Client and the Developer must agree to that change in writing and sign a document showing their contract. Neither party can waive its rights under this Contract or release the other party from its obligations under this Contract, unless the waiving party acknowledges it is doing so in writing and signs a document that says so. \n"
                                + "\n"
                                + "11.4 Notices.\n"
                                + "\n"
                                + "(a) Over the course of this Contract, one party may need to send a notice to the other party. For the notice to be valid, it must be in writing and delivered in one of the following ways: personal delivery, email, or certified or registered mail (postage prepaid, return receipt requested). The notice must be delivered to the party’s address listed at the end of this Contract or to another address that the party has provided in writing as an appropriate address to receive notice. \n"
                                + "\n"
                                + "(b) The timing of when a notice is received can be very important. To avoid confusion, a valid notice is considered received as follows: (i) if delivered personally, it is considered received immediately; (ii) if delivered by email, it is considered received upon acknowledgement of receipt; (iii) if delivered by registered or certified mail (postage prepaid, return receipt requested), it is considered received upon receipt as indicated by the date on the signed receipt. If a party refuses to accept notice or if notice cannot be delivered because of a change in address for which no notice was given, then it is considered received when the notice is rejected or unable to be delivered. If the notice is received after 5:00pm on a business day at the location specified in the address for that party, or on a day that is not a business day, then the notice is considered received at 9:00am on the next business day. \n"
                                + "\n"
                                + "11.5 Severability. This section deals with what happens if a portion of the Contract is found to be unenforceable. If that’s the case, the unenforceable portion will be changed to the minimum extent necessary to make it enforceable, unless that change is not permitted by law, in which case the portion will be disregarded. If any portion of the Contract is changed or disregarded because it is unenforceable, the rest of the Contract is still enforceable. \n"
                                + "\n"
                                + "11.6 Signatures. The Client and the Developer must sign this document using Bonsai’s e-signing system. These electronic signatures count as originals for all purposes. \n"
                                + "\n"
                                + "11.7 Governing Law. The laws of the state of Alaska govern the rights and obligations of the Client and the Developer under this Contract, without regard to conflict of law principles of that state. \n"
                                + "\n"
                                + "11.8 Entire Contract. This Contract represents the parties’ final and complete understanding of this job and the subject matter discussed in this Contract. This Contract supersedes all other contracts (both written and oral) between the parties. \n"
                                + "\n"
                                + "THE PARTIES HERETO AGREE TO THE FOREGOING AS EVIDENCED BY THEIR SIGNATURES BELOW.\n\n\n"
                                + "                 " + student.getApplication().getFreelancer().getName() + " " + student.getApplication().getFreelancer().getLast_name() + "                 " + student.getApplication().getOpportunity().getUser()
                        ));

                        document.close();

                        Parent tableViewContract;
                        try {
                            tableViewContract = FXMLLoader.load(getClass().getResource("/com/smartstart/gui/ContractGUIInt.fxml"));
                            Scene tableViewContractScene = new Scene(tableViewContract);
                            Stage window = (Stage) ((Node) t.getSource()).getScene().getWindow();
                            window.setScene(tableViewContractScene);
                        } catch (IOException ex) {
                            Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } catch (DocumentException ex) {
                        Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ContractCellController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });
        };

        setText(null);
        setGraphic(gridPane);
    }

    private void alert(String Message) {
        Alert a1 = new Alert(Alert.AlertType.ERROR);
        a1.setTitle("Alert");
        a1.setHeaderText("Not Valid");
        a1.setContentText(Message);
        a1.showAndWait();

    }

    private boolean alertConfirmation() {
        Alert a1 = new Alert(Alert.AlertType.CONFIRMATION);
        a1.setTitle("CONFIRMATION DIALOG");
        a1.setHeaderText("SUPPRESSION CONFIRMATION");
        a1.setContentText("ARE YOU SURE THAT YOU WANT TO DELETE THIS CONTRACT ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

}
