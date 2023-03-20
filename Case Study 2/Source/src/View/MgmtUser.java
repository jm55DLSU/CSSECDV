/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Main;
import Utilities.HashCrypt;
import Utilities.Validator;
import Utilities.Logger;
import Controller.SQLite;
import Model.User;
import Model.Logs;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author beepxD
 */
public class MgmtUser extends javax.swing.JPanel {

    public SQLite sqlite;
    public DefaultTableModel tableModel;
    private Validator validate;
    private HashCrypt hs;
    private Main m;
    private Logger logger;
    
    public MgmtUser(SQLite sqlite) {
        initComponents();
        this.hs = new HashCrypt();
        this.validate = new Validator();
        this.sqlite = sqlite;
        this.logger = new Logger(sqlite);
        tableModel = (DefaultTableModel)table.getModel();
        table.getTableHeader().setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
        
//        UNCOMMENT TO DISABLE BUTTONS
//        editBtn.setVisible(false);
//        deleteBtn.setVisible(false);
//        lockBtn.setVisible(false);
//        chgpassBtn.setVisible(false);
    }
    
    private void resetTable(){
        //CLEAR TABLE
        for(int nCtr = tableModel.getRowCount(); nCtr > 0; nCtr--){
            tableModel.removeRow(0);
        }
    }
    
    private void loadTable(){
        //LOAD CONTENTS
        ArrayList<User> users = this.sqlite.getUsers();
        for(int nCtr = 0; nCtr < users.size(); nCtr++){
            tableModel.addRow(new Object[]{
                users.get(nCtr).getUsername(), 
                hiddenPassword(), 
                users.get(nCtr).getRole(), 
                users.get(nCtr).getLocked()});
        }
    }
    
    private void reloadTable(){
        resetTable();
        loadTable();
    }
    
    private boolean isSameUser(final String targetUsername){
        if(targetUsername.equals(this.m.getSessionUserName())){
            errorDialog("Setting your own role is disallowed!");
            return true;
        }else
            return false;
    }
    
    private boolean confirmAdmin(String title){
        JPasswordField adminPass = new JPasswordField();
        
        designer(adminPass, "ADMIN PASSWORD");
        
        Object[] message = {
            "Confirm Admin Password: ", adminPass
        };

        int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if(result == JOptionPane.OK_OPTION){
            if(this.sqlite.authenticateUser(m.getSessionUserName(), new String(adminPass.getPassword()))){
                return true;
            }else{
                errorDialog("Incorrect Admin password,\nplease try again.");
                return false;
            }
        }else{
            return false;
        }
    }
    
    public void init(Main m){
        this.m = m;
        
        validate.validateSession(null, 5, this.m.getSessionRole());
        
        reloadTable();
        
        JOptionPane.showMessageDialog(this, "For user data privacy and security purposes,\nthe passwords shown on the table\nare not representative of the actual stored passwords.", "Notice", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String hiddenPassword(){
        String out = "";
        for(int i = 0; i < new Random().nextInt(8, 64); i++)
            out += "*";
        return out;
    }

    public void designer(JTextField component, String text){
        component.setSize(70, 600);
        component.setFont(new java.awt.Font("Tahoma", 0, 18));
        component.setBackground(new java.awt.Color(240, 240, 240));
        component.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        component.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), text, javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        editRoleBtn = new javax.swing.JButton();
        deleteBtn = new javax.swing.JButton();
        lockBtn = new javax.swing.JButton();
        chgpassBtn = new javax.swing.JButton();

        table.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Username", "Password", "Role", "Locked"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setRowHeight(24);
        table.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(160);
            table.getColumnModel().getColumn(1).setPreferredWidth(400);
            table.getColumnModel().getColumn(2).setPreferredWidth(100);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        editRoleBtn.setBackground(new java.awt.Color(255, 255, 255));
        editRoleBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        editRoleBtn.setText("EDIT ROLE");
        editRoleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRoleBtnActionPerformed(evt);
            }
        });

        deleteBtn.setBackground(new java.awt.Color(255, 255, 255));
        deleteBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        deleteBtn.setText("DELETE");
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        lockBtn.setBackground(new java.awt.Color(255, 255, 255));
        lockBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lockBtn.setText("LOCK/UNLOCK");
        lockBtn.setToolTipText("");
        lockBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockBtnActionPerformed(evt);
            }
        });

        chgpassBtn.setBackground(new java.awt.Color(255, 255, 255));
        chgpassBtn.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chgpassBtn.setText("CHANGE PASS");
        chgpassBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chgpassBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editRoleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(deleteBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(lockBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(0, 0, 0)
                        .addComponent(chgpassBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chgpassBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editRoleBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lockBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editRoleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRoleBtnActionPerformed
        if(table.getSelectedRow() >= 0){
            String targetUser = (String) tableModel.getValueAt(table.getSelectedRow(), 0);
            if(isSameUser(targetUser))
                return;
            if(confirmAdmin("Edit Role")){
                String[] options = {"1-DISABLED","2-CLIENT","3-STAFF","4-MANAGER","5-ADMIN"};
                JComboBox optionList = new JComboBox(options);

                optionList.setSelectedIndex((int)tableModel.getValueAt(table.getSelectedRow(), 2) - 1);

                
                String result = (String)JOptionPane.showInputDialog(null, "USER: " + targetUser, 
                    "EDIT USER ROLE", JOptionPane.QUESTION_MESSAGE, null, options, options[(int)tableModel.getValueAt(table.getSelectedRow(), 2) - 1]);

                if(result != null){
                    int targetRole = Integer.parseInt(result.split("-")[0]);
                    if(this.sqlite.changeRole(targetUser, targetRole))
                        this.sqlite.addLogs(new Logs("NOTICE", this.m.getSessionUserName(), "EDIT ROLE: " + targetUser + " ROLE=" + tableModel.getValueAt(table.getSelectedRow(), 2) + "->" + targetRole));
                    else
                        this.sqlite.addLogs(new Logs("ERROR", this.m.getSessionUserName(), "EDIT ROLE FAILED: " + targetUser + " ROLE=" + tableModel.getValueAt(table.getSelectedRow(), 2) + "->" + targetRole));
                    reloadTable();
                }
            }
        }
    }//GEN-LAST:event_editRoleBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        if(table.getSelectedRow() >= 0){
            String targetUser = (String)tableModel.getValueAt(table.getSelectedRow(), 0);
            if(isSameUser(targetUser))
                return;
            
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + targetUser + "?", "DELETE USER", JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                System.out.println(tableModel.getValueAt(table.getSelectedRow(), 0));
                
                //ADD LOGGING AFTER USER-IMPOSED DELETION
            }
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void lockBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockBtnActionPerformed
        if(table.getSelectedRow() >= 0){
            String state = "lock";
            boolean toLock = true;
            if("1".equals(tableModel.getValueAt(table.getSelectedRow(), 3) + "")){
                state = "unlock";
                toLock = false;
            }
            
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to " + state + " " + tableModel.getValueAt(table.getSelectedRow(), 0) + "?", "LOCK USER", JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                if(confirmAdmin("Lock/Unlock Account")){
                    String target = (String)tableModel.getValueAt(table.getSelectedRow(), 0);
                    if(toLock){
                        if(this.sqlite.lockUser(target))
                            this.sqlite.addLogs(new Logs("NOTICE",this.m.getSessionUserName(), "LOCK: " + target));
                        else
                            this.sqlite.addLogs(new Logs("ERROR",this.m.getSessionUserName(), "LOCK FAILED: "+ target));
                    }else{
                        if(this.sqlite.unlockUser(target))
                            this.sqlite.addLogs(new Logs("NOTICE",this.m.getSessionUserName(), "UNLOCK: " + target));
                        else
                            this.sqlite.addLogs(new Logs("ERROR",this.m.getSessionUserName(), "UNLOCK FAILED: " + target));
                    }
                }
            }
            
            reloadTable();
        }
    }//GEN-LAST:event_lockBtnActionPerformed

    private void chgpassBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chgpassBtnActionPerformed
        int row = table.getSelectedRow();
        if(row >= 0){
            String uname = ""+table.getValueAt(row, 0);
            
            JPasswordField adminPass = new JPasswordField();
            JPasswordField password = new JPasswordField();
            JPasswordField confpass = new JPasswordField();
            designer(adminPass, "ADMIN PASSWORD");
            designer(password, "PASSWORD");
            designer(confpass, "CONFIRM PASSWORD");
            
            
            Object[] message = {
                "Confirm Admin Password: ", adminPass, "Enter New Password:", password, confpass
            };

            int result = JOptionPane.showConfirmDialog(null, message, "CHANGE PASSWORD", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
            
            if (result == JOptionPane.OK_OPTION) {
                if(!validate.passwordMatches(new String(password.getPassword()), new String(confpass.getPassword()))){ //Validate Pass and ConfPass Match
                    errorDialog("Inputted passwords don't match,\nplease try again.");
                }else if(!validate.passwordWithinLimit(new String(password.getPassword()))){ //Validate Pass Not Current Pass
                    errorDialog("Password not within limit,\nplease try again.\n\nMin Character Length: " + validate.minLength + "\nMax Character Length: " + validate.maxLength);
                }else if(!validate.isValidPasswordString(new String(password.getPassword()))){ //Validate Password Allowable
                    errorDialog("Password don't comply with rules.\n" + "Invalid inputs, please try again.\n" 
                                + "Valid Username Characters: Lowercase Letters, Numbers, -, _, .\n"
                                + "Valid Password Characters: Upper and Lowercase, Numbers, Symbols (~`!@#$%^&*()_\\-+=\\{\\[\\}\\]|:\\<,>.?/)");
                }else if(this.sqlite.authenticateUser(uname, new String(password.getPassword()))){ //Same Password as Current User
                    errorDialog("Password is the same as before,\nplease try again.");
                }else{
                    if(this.sqlite.authenticateUser(m.getSessionUserName(), new String(adminPass.getPassword()))){
                        if(this.sqlite.changePassword(uname, new String(password.getPassword()))){
                            JOptionPane.showMessageDialog(this, "Password Changed Success!", "Password Change", JOptionPane.INFORMATION_MESSAGE);
                            this.sqlite.addLogs(new Logs("NOTICE", this.m.getSessionUserName(), "PW CHANGE: " + uname));
                        }else{
                            this.sqlite.addLogs(new Logs("ERROR", this.m.getSessionUserName(), "PW CHANGE FAILED: " + uname));
                            errorDialog("Error changing password!");
                        }
                    }else{
                        errorDialog("Incorrect Admin password,\nplease try again.");
                    }
                }
            }
        }
    }//GEN-LAST:event_chgpassBtnActionPerformed

    private void errorDialog(String message){
        JOptionPane.showMessageDialog(this, message, "User Management", JOptionPane.WARNING_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chgpassBtn;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JButton editRoleBtn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton lockBtn;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
