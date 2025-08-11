package com.bankapp.ui;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import com.bankapp.util.DBConnection;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Bank Dashboard");
        setSize(400, 300);
        setLayout(null);

        JButton depositBtn = new JButton("Deposit");
        depositBtn.setBounds(50, 50, 100, 30);
        depositBtn.addActionListener(e -> new Deposit().setVisible(true));

        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(200, 50, 100, 30);
        withdrawBtn.addActionListener(e -> new Withdraw().setVisible(true));

        JButton balanceBtn = new JButton("Balance");
        balanceBtn.setBounds(125, 100, 100, 30);
        balanceBtn.addActionListener(e -> {
            String accNo = JOptionPane.showInputDialog("Enter Account No:");
            try (Connection con = DBConnection.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT balance FROM accounts WHERE account_no=?");
                ps.setInt(1, Integer.parseInt(accNo));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Balance: " + rs.getDouble(1));
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        add(depositBtn);
        add(withdrawBtn);
        add(balanceBtn);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
