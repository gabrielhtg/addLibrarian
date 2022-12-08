import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TambahLibrarian {
    JFrame frame = new JFrame();
    JTextField fieldNamaLibrarian = new JTextField();
    JLabel placeholderNamaLibrarian = new JLabel();
    JTextField fieldUsername = new JTextField();
    JLabel placeholderUsername = new JLabel();
    JTextField fieldPassword = new JTextField();
    JLabel placeholderPassword = new JLabel();
    JTextField fieldFoto = new JTextField();
    JButton tombolFoto = new JButton();
    JFileChooser pilihFoto = new JFileChooser();
    JLabel placeholderFoto = new JLabel();
    JPanel panelBawah1 = new JPanel(new FlowLayout());
    JButton tombolOke = new JButton();
    JButton tombolBatal = new JButton();
    JButton tombolDone = new JButton();
    Color merah = new Color(243, 69, 70);
    JButton tombolHapus = new JButton();
    JPanel panelBawah2 = new JPanel(new FlowLayout());
    JLabel berhasilTidak = new JLabel();
    JButton tombolEdit = new JButton();
    String usernameSementara = null;

    void tungguBentar() {
        Thread tampilkan = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }                            
                berhasilTidak.setText("");
            }
            
        });

        tampilkan.start();
    }

    void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }

    boolean tambahLibrarian (String nama, String username, String password, String foto) {
        String sql = String.format("insert into librarian(nama, username, pass, foto) values ('%s', '%s', '%s', '%s')", nama, username, password, foto);

        try {
            Database dataRaelsa = new Database();
            dataRaelsa.perintah.executeUpdate(sql);
            dataRaelsa.koneksi.close();
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            // e.printStackTrace();
            return false;
        }
    }

    boolean updateLibrarian(String nama, String username, String password, String foto, String usernameAwal) {
        String sql = String.format("update librarian set nama = '%s', username = '%s', pass = '%s', foto = '%s' where username = '%s'", nama, username, password, foto, usernameAwal);

        try {
            Database dataRaelsa = new Database();
            dataRaelsa.perintah.executeUpdate(sql);
            dataRaelsa.koneksi.close();
            return true;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            // e.printStackTrace();
            return false;
        }
    }

    boolean cekLibrarian (String username) {
        String sql = String.format("select username from librarian where username = '%s'", username);

        try {
            Database dataRaelsa = new Database();
            ResultSet rs = dataRaelsa.perintah.executeQuery(sql);
            rs.next();
            usernameSementara = rs.getString("username");
            rs.close();
            dataRaelsa.koneksi.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        } catch (NullPointerException e) {
            // e.printStackTrace();
        }

        if (usernameSementara.equals(username)) {
            return true;
        }

        else {
            return false;
        }
    }

    boolean hapusMember (String username) {
        String sql = String.format("delete from librarian where username = '%s'", username);
        String sql1 = String.format("select username from librarian where username = '%s'", username);

        try {
            Database dataRaelsa = new Database();
            ResultSet rs = dataRaelsa.perintah.executeQuery(sql1);
            rs.next();
            if (rs.getString("username").equals(username)) {
                dataRaelsa.perintah.executeUpdate(sql);
                rs.close();
                dataRaelsa.koneksi.close();
                return true;
            }
            else {
                rs.close();
                dataRaelsa.koneksi.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }

    TambahLibrarian () {
        frame.setTitle("Tambah Librarian");
        frame.setVisible(true);

        Font fontInputField = new Font("FiraCode Nerd Font Mono", Font.PLAIN, 20);

        Font fontPlaceholder = new Font("Arial", Font.PLAIN, 15);

        fieldNamaLibrarian.requestFocusInWindow();
        fieldNamaLibrarian.setPreferredSize(new Dimension(650, 50));
        fieldNamaLibrarian.setFont(fontInputField);
        frame.add(fieldNamaLibrarian);
        fieldNamaLibrarian.setLayout(new BorderLayout());

        placeholderNamaLibrarian.setText("Nama Librarian");
        placeholderNamaLibrarian.setFont(fontPlaceholder);
        placeholderNamaLibrarian.setForeground(new Color(166, 166, 166));
        fieldNamaLibrarian.add(placeholderNamaLibrarian, BorderLayout.WEST);

        fieldUsername.setLayout(new BorderLayout());
        fieldUsername.setPreferredSize(new Dimension(650, 50));
        fieldUsername.setFont(fontInputField);
        frame.add(fieldUsername);

        placeholderUsername.setText("Username Librarian");
        placeholderUsername.setFont(fontPlaceholder);
        placeholderUsername.setForeground(new Color(166, 166, 166));
        fieldUsername.add(placeholderUsername, BorderLayout.WEST);

        fieldPassword.setLayout(new BorderLayout());
        fieldPassword.setPreferredSize(new Dimension(650, 50));
        fieldPassword.setFont(fontInputField);
        frame.add(fieldPassword);

        placeholderPassword.setText("Password Librarian");
        placeholderPassword.setFont(fontPlaceholder);
        placeholderPassword.setForeground(new Color(166, 166, 166));
        fieldPassword.add(placeholderPassword, BorderLayout.WEST);

        fieldFoto.setLayout(new BorderLayout());
        fieldFoto.setPreferredSize(new Dimension(500, 50));
        fieldFoto.setFont(fontInputField);
        frame.add(fieldFoto);

        tombolFoto.setFont(new Font("Arial", Font.PLAIN, 20));
        tombolFoto.setText("Pilih Foto");
        tombolFoto.setPreferredSize(new Dimension(145, 50));
        frame.add(tombolFoto);
        tombolFoto.setFocusable(false);

        tombolFoto.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                pilihFoto.showOpenDialog(pilihFoto);
                pilihFoto.setFileSelectionMode(JFileChooser.FILES_ONLY);
                String[] namaFoto = String.valueOf(pilihFoto.getSelectedFile()).split("\\\\");
                fieldFoto.setText(namaFoto[namaFoto.length - 1]);
            }
            
        });

        placeholderFoto.setText("Lokasi Foto");
        placeholderFoto.setFont(fontPlaceholder);
        placeholderFoto.setForeground(new Color(166, 166, 166));
        fieldFoto.add(placeholderFoto, BorderLayout.WEST);

        panelBawah1.setPreferredSize(new Dimension(650, 25));
        frame.add(panelBawah1);

        tombolEdit.setBackground(new Color(50, 94, 190));
        tombolEdit.setForeground(Color.white);
        tombolEdit.setPreferredSize(new Dimension(200, 50));
        tombolEdit.setFocusable(false);
        tombolEdit.setText("Edit Librarian");
        tombolEdit.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(tombolEdit);
        
        tombolOke.setBackground(new Color(50, 94, 190));
        tombolOke.setForeground(Color.white);
        tombolOke.setPreferredSize(new Dimension(200, 50));
        tombolOke.setFocusable(false);
        tombolOke.setText("Done");
        tombolOke.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(tombolOke);

        tombolHapus.setBackground(merah);
        tombolHapus.setForeground(Color.white);
        tombolHapus.setPreferredSize(new Dimension(200, 50));
        tombolHapus.setFocusable(false);
        tombolHapus.setText("Hapus Librarian");
        tombolHapus.setFont(new Font("Arial", Font.PLAIN, 20));
        frame.add(tombolHapus);

        tombolDone.setBackground(new Color(50, 94, 190));
        tombolDone.setForeground(Color.white);
        tombolDone.setPreferredSize(new Dimension(200, 50));
        tombolDone.setFocusable(false);
        tombolDone.setText("Oke");
        tombolDone.setFont(new Font("Arial", Font.PLAIN, 20));
        tombolDone.setVisible(false);
        frame.add(tombolDone);

        tombolBatal.setBackground(merah);
        tombolBatal.setForeground(Color.white);
        tombolBatal.setPreferredSize(new Dimension(200, 50));
        tombolBatal.setFocusable(false);
        tombolBatal.setText("Batal");
        tombolBatal.setFont(new Font("Arial", Font.PLAIN, 20));
        tombolBatal.setVisible(false);        
        frame.add(tombolBatal);

        panelBawah2.setPreferredSize(new Dimension(650, 50));
        frame.add(panelBawah2);

        berhasilTidak.setFont(new Font("Arial", Font.BOLD, 20));
        panelBawah2.add(berhasilTidak);

        frame.setLayout(new FlowLayout());
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        tombolOke.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File source = new File(String.valueOf(pilihFoto.getSelectedFile()));

                String[] temp = String.valueOf(pilihFoto.getSelectedFile()).split("\\\\");
                
                try {
                    if (tambahLibrarian(fieldNamaLibrarian.getText(), fieldUsername.getText(), fieldPassword.getText(), "source\\\\" + fieldUsername.getText() + "." + temp[temp.length - 1].split("\\.")[1])) {
                        berhasilTidak.setForeground(new Color(111, 144, 84));   
                        berhasilTidak.setText("Berhasil Menambahkan Librarian Baru");
                        tungguBentar();
    
                        File destination = new File("C:\\LinWin\\projects\\java\\workspace\\TugasPerpustakaan\\source\\" + fieldUsername.getText() + "." + temp[temp.length - 1].split("\\.")[1]);
                        try {
                            copyFileUsingStream(source, destination);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
    
                    else {
                        berhasilTidak.setForeground(new Color(243, 69, 70));
                        berhasilTidak.setText("Gagal Menambahkan Librarian Baru");
                        tungguBentar();                        
                    }
                } catch (ArrayIndexOutOfBoundsException args) {
                    berhasilTidak.setForeground(new Color(243, 69, 70));
                    berhasilTidak.setText("Gagal Menambahkan Librarian Baru");
                    tungguBentar();
                }

                fieldNamaLibrarian.setText("");
                fieldUsername.setText("");
                fieldPassword.setText("");
                fieldFoto.setText("");
            }
            
        });

        tombolHapus.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (hapusMember(JOptionPane.showInputDialog("Masukkan username libarian"))) {
                    berhasilTidak.setText("Berhasil Menghapus Librarian");
                    berhasilTidak.setForeground(new Color(111, 144, 84));
                    tungguBentar();   
                }
                else {
                    berhasilTidak.setForeground(merah);
                    berhasilTidak.setText("Gagal Menghapus Librarian. Username Tidak Ditemukan");
                    tungguBentar();
                }
            }
            
        });

        tombolEdit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (cekLibrarian(JOptionPane.showInputDialog("Masukkan username librarian"))) {
                    tombolOke.setVisible(false);
                    tombolDone.setVisible(true);
                    tombolHapus.setVisible(false);
                    tombolBatal.setVisible(true);
                    tombolEdit.setVisible(false);
                    fieldNamaLibrarian.requestFocusInWindow();
                }
            }
            
        });

        tombolDone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                File source = new File(String.valueOf(pilihFoto.getSelectedFile()));

                String[] temp = String.valueOf(pilihFoto.getSelectedFile()).split("\\\\");
                
                if (updateLibrarian(fieldNamaLibrarian.getText(), fieldUsername.getText(), fieldPassword.getText(), "source\\\\" + fieldUsername.getText() + "." + temp[temp.length - 1].split("\\.")[1], usernameSementara)) {
                    berhasilTidak.setForeground(new Color(111, 144, 84));   
                    berhasilTidak.setText("Berhasil Mengedit Librarian");
                    tungguBentar();

                    File destination = new File("C:\\LinWin\\projects\\java\\workspace\\TugasPerpustakaan\\source\\" + fieldUsername.getText() + "." + temp[temp.length - 1].split("\\.")[1]);
                    try {
                        copyFileUsingStream(source, destination);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                else {
                    berhasilTidak.setForeground(new Color(243, 69, 70));
                    berhasilTidak.setText("Gagal Mengedit Librarian");
                    tungguBentar();
                }

                fieldNamaLibrarian.setText("");
                fieldUsername.setText("");
                fieldPassword.setText("");
                fieldFoto.setText("");
                tombolDone.setVisible(false);
                tombolBatal.setVisible(false);
                tombolEdit.setVisible(true);
                tombolOke.setVisible(true);
                tombolHapus.setVisible(true);
            }
            
        });

        tombolBatal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tombolDone.setVisible(false);
                tombolBatal.setVisible(false);
                tombolEdit.setVisible(true);
                tombolOke.setVisible(true);
                tombolHapus.setVisible(true);
            }
            
        });

        fieldNamaLibrarian.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fieldUsername.requestFocusInWindow();
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });

        fieldUsername.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fieldPassword.requestFocusInWindow();
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });

        fieldPassword.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        tombolFoto.doClick();
                        fieldFoto.requestFocusInWindow();
                    }
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });

        fieldFoto.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tombolOke.doClick();
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });

        fieldNamaLibrarian.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            private void warn() {
                placeholderNamaLibrarian.setVisible(false);
            }
            public void removeUpdate(DocumentEvent e) {
                if (fieldNamaLibrarian.getText().equals("")) {
                    placeholderNamaLibrarian.setVisible(true);
                }
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }
        });

        fieldUsername.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            private void warn() {
                placeholderUsername.setVisible(false);
            }
            public void removeUpdate(DocumentEvent e) {
                if (fieldUsername.getText().equals("")) {
                    placeholderUsername.setVisible(true);
                }
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }
        });


        fieldPassword.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            private void warn() {
                placeholderPassword.setVisible(false);
            }
            public void removeUpdate(DocumentEvent e) {
                if (fieldPassword.getText().equals("")) {
                    placeholderPassword.setVisible(true);
                }
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }
        });

        fieldFoto.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              warn();
            }
            private void warn() {
                placeholderFoto.setVisible(false);
            }
            public void removeUpdate(DocumentEvent e) {
                if (fieldFoto.getText().equals("")) {
                    placeholderFoto.setVisible(true);
                }
            }
            public void insertUpdate(DocumentEvent e) {
              warn();
            }
        });

    }
}
