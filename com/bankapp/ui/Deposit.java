package com.bankapp.ui;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import com.bankapp.util.DBConnection;

public class Deposit extends JFrame {
    public Deposit() {
        setTitle("Deposit");
        setSize(300, 200);
        setLayout(null);

        JTextField accField = new JTextField();
        accField.setBounds(100, 30, 150, 25);
        add(new JLabel("Account No:")).setBounds(20, 30, 80, 25);
        add(accField);

        JTextField amtField = new JTextField();
        amtField.setBounds(100, 70, 150, 25);
        add(new JLabel("Amount:")).setBounds(20, 70, 80, 25);
        add(amtField);

        JButton submit = new JButton("Deposit");
        submit.setBounds(100, 110, 100, 30);
        submit.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_no=?");
                ps.setDouble(1, Double.parseDouble(amtField.getText()));
                ps.setInt(2, Integer.parseInt(accField.getText()));
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Deposit Successful");
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(submit);
    }
}
