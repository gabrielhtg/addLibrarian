import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class Password {
    JFrame frame = new JFrame();
    JLabel label = new JLabel();
    JPasswordField pw = new JPasswordField();
    JButton tombolOke = new JButton();


    Password() {
        frame.setTitle("Admin Verification");
        frame.setVisible(true);
        frame.setLayout(null);

        label.setText("Masukkan password admin");
        label.setBounds(20, 10, 260, 20);
        frame.add(label);

        pw.setBounds(20, 50 - 10, 242, 20);
        frame.add(pw);

        tombolOke.setBackground(new Color(50, 94, 190));
        tombolOke.setText("Ok");
        tombolOke.setForeground(Color.white);
        tombolOke.setBounds(120, 70, 50, 20);
        frame.add(tombolOke);

        pw.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tombolOke.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }
            
        }); 

        tombolOke.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(pw.getPassword()).equals("admin")) {
                    new TambahLibrarian();
                    frame.dispose();
                }

                else {
                    pw.setText("");
                    System.out.println("Wrong Password");
                }
                
            }
            
        });

        frame.setSize(300, 140);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }   
}