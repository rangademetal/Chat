
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zeu
 */
public class Admin_check1 {
      JTextArea textArea = new JTextArea();
      JButton exitBtn = new JButton("Exit Admin Message Panel");
      JFrame Frame = new JFrame("Admin Message Panel");
    
    protected Admin_check1(){
        Frame.setLayout(null);
        Frame.setDefaultCloseOperation(Frame.DISPOSE_ON_CLOSE);
        Frame.setSize(600,725);

        JScrollPane scroll = new JScrollPane(textArea);
        textArea.setEditable(false);
        
        exitBtn.setBounds(20, 635, 545, 30);
        scroll.setBounds(20,20, 545, 600);
        
        try{
            BufferedReader br = new BufferedReader(new FileReader("Save2.txt"));
            textArea.read(br, null);
            br.close();
        
        }
        catch(Exception e){
            System.out.println("Error !"+ e.getMessage());
        }    
        
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               Frame.dispose();    
            }
        });
        Frame.add(exitBtn);
        Frame.add(scroll);
        
        Frame.setVisible(true);
        
           
    }
    public static void main(String[] arguments){
        new Admin_check();
    }
}
