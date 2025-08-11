package com.bankapp.ui;
import javax.swing.*;
import java.sql.*;
import com.bankapp.util.DBConnection;

public class Withdraw extends JFrame {
    public Withdraw() {
        setTitle("Withdraw");
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

        JButton submit = new JButton("Withdraw");
        submit.setBounds(100, 110, 100, 30);
        submit.addActionListener(e -> {
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement check = con.prepareStatement("SELECT balance FROM accounts WHERE account_no=?");
                check.setInt(1, Integer.parseInt(accField.getText()));
                ResultSet rs = check.executeQuery();
                if (rs.next()) {
                    double bal = rs.getDouble(1);
                    double amt = Double.parseDouble(amtField.getText());
                    if (amt <= bal) {
                        PreparedStatement ps = con.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_no=?");
                        ps.setDouble(1, amt);
                        ps.setInt(2, Integer.parseInt(accField.getText()));
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Withdrawal Successful");
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient Balance");
                    }
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
