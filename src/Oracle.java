

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Oracle {

    Utilities fnc = new Utilities();
    String secretKey = "JavaWebBuilderCMS";

    public Oracle() {
        JFrame dbb = new JFrame("Control Panel");
        JTable table = new JTable();
        dbb.getContentPane().setBackground(Color.white);
        Object[] columns = {"Email", "Password"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setBackground(Color.orange);

        JLabel text1 = new JLabel("Email:");
        JLabel text2 = new JLabel("Password:");


        JPasswordField password = new JPasswordField();
        JTextField email = new JTextField();
        JPasswordField oldPassword = new JPasswordField();
        JPasswordField newPassword = new JPasswordField();

        JButton btnAdd = new JButton("Add DB");
        JButton btnUpd = new JButton("Update DB ");
        JButton btnDel = new JButton("Delete");
        JButton btnSearch = new JButton("refresh");

        text1.setBounds(10, 35, 100, 20);
        text2.setBounds(10, 65, 100, 20);

        
        
        email.setBounds(100, 30, 150, 30);
        password.setBounds(100, 60, 150, 30);
        oldPassword.setBounds(400, 30, 150, 30);
        newPassword.setBounds(400, 60, 150, 30);
        

        btnDel.setBounds(10, 490, 250, 50);
        btnUpd.setBounds(285, 490, 250, 50);
        btnAdd.setBounds(555, 490, 250, 50);
        btnSearch.setBounds(600, 30, 200, 50);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(10, 110, 795, 370);

        dbb.add(btnDel);
        
        dbb.add(btnAdd);
 
        Object[] row = new Object[5];

        try {
            
        
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://hosting1993073.online.pro:3306/00286862_licenta", "00286862_licenta", "x5tBjkms");
            Statement myStat = conn.createStatement();
            ResultSet myRs = myStat.executeQuery("SELECT * FROM account");
           
            while (myRs.next()) {
              
                row[0] = myRs.getString("email");
                row[1] = myRs.getString("password");
                
                model.addRow(row);
            }

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                try{  
                   int i = table.getSelectedRow();

                   email.setText("");
                   password.setText("");

                   email.setText(model.getValueAt(i, 0).toString());
                   password.setText(model.getValueAt(i, 1).toString());

                } 
                catch (Exception IO) {
               System.out.println("Error!" + IO.getMessage());
               }
            }
        });
        } 
                catch (Exception IO) {
            System.out.println("Error!" + IO.getMessage());
        }
        
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://hosting1993073.online.pro:3306/00286862_licenta", "00286862_licenta", "x5tBjkms");
                    Statement myStat = conn.createStatement();
                    
                    String sql = "Insert into account (email,password) values (?,?)";
                    PreparedStatement pst = conn.prepareStatement(sql);

                    String user = fnc.encrypt(email.getText(), secretKey);
                    String pass = fnc.encrypt(password.getText(), secretKey);

                    pst.setString(1, user);
                    pst.setString(2, pass);
                    
                    row[0] = user;
                    row[1] = pass;
                    model.addRow(row);
                    password.setText("");
                    email.setText("");
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Add Succesfull");
                    conn.close();

                } catch (Exception IO) {
                    System.out.println("Error!" + IO.getMessage());
                }
            }
        });

        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn = DriverManager.getConnection("jdbc:mysql://hosting1993073.online.pro:3306/00286862_licenta", "00286862_licenta", "x5tBjkms");
                    Statement myStat = conn.createStatement();
                    String sql = "Delete from account where email = ?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setString(1, email.getText());
                    System.out.println(email.getText());
                    row[0] = email.getText();
                    row[1] = password.getText();

                    password.setText("");
                    email.setText("");
                 
                    int i = table.getSelectedRow();
                    model.removeRow(i);
                    JOptionPane.showMessageDialog(null, "Delete Succesfull!");
                    pst.executeUpdate();
                } catch (Exception IO) {
                    IO.getMessage();
                }
            }
        });


        dbb.add(password);
        dbb.add(email);
//        dbb.add(btnSearch);

        dbb.add(text1);
        dbb.add(text2);

                
  
        dbb.add(pane);
//Setari JFRAME

        dbb.setLayout(null); 
        dbb.setResizable(false);
        dbb.setFocusable(true);
        dbb.setLocationRelativeTo(null);
        dbb.setSize(825, 580);
        dbb.setVisible(true);
        dbb.setDefaultCloseOperation(dbb.DISPOSE_ON_CLOSE);

    }

    public static void main(String[] args) {
        new Oracle();
    }

}
