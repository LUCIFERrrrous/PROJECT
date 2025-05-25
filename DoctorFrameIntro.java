package DoctorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class DoctorFrameIntro extends JFrame implements ActionListener, MouseListener {
    ImageIcon img;
    JLabel id, name, insert, search, imgLabel;
    JTextField idField, nameField, insertField, searchField;
    JButton insertBtn, insertCancelBtn, searchBtn, exitBtn;
    JPanel loginPanel, imagePanel;
    Color lightBlue = new Color(180, 220, 250);
    Font fieldFont = new Font("Cambria", Font.PLAIN, 12);
    Font buttonFont = new Font("Cambria", Font.BOLD, 12);

    // Define the data file name
    private static final String DATA_FILE = "DoctorData.txt";

    public DoctorFrameIntro() {
        super("Doctor Management System");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        // Image Panel
        img = new ImageIcon("Doc3.jpg");
        Image dimg = img.getImage().getScaledInstance(300, 450, Image.SCALE_SMOOTH);
        img = new ImageIcon(dimg);
        imgLabel = new JLabel(img);
        imgLabel.setBounds(0, 0, 300, 450);
        add(imgLabel);

        // Login Panel
        loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBackground(lightBlue);
        loginPanel.setBounds(300, 0, 400, 450);
        add(loginPanel);

        id = new JLabel("Doctor's ID:");
        id.setBounds(30, 20, 100, 20);
        loginPanel.add(id);

        idField = new JTextField();
        idField.setFont(fieldFont);
        idField.setBounds(140, 20, 200, 20);
        loginPanel.add(idField);

        name = new JLabel("Doctor's Name:");
        name.setBounds(30, 50, 100, 20);
        loginPanel.add(name);

        nameField = new JTextField();
        nameField.setFont(fieldFont);
        nameField.setBounds(140, 50, 200, 20);
        loginPanel.add(nameField);

        insert = new JLabel("Insert Your Data:");
        insert.setBounds(30, 80, 100, 20);
        loginPanel.add(insert);

        insertField = new JTextField();
        insertField.setFont(fieldFont);
        insertField.setBounds(140, 80, 200, 20);
        loginPanel.add(insertField);

        insertBtn = new JButton("Insert");
        insertBtn.setFont(buttonFont);
        insertBtn.setBounds(140, 110, 90, 25);
        insertBtn.addActionListener(this);
        loginPanel.add(insertBtn);

        insertCancelBtn = new JButton("Cancel");
        insertCancelBtn.setFont(buttonFont);
        insertCancelBtn.setBounds(250, 110, 90, 25);
        insertCancelBtn.addActionListener(this);
        loginPanel.add(insertCancelBtn);

        search = new JLabel("Search Your Data:");
        search.setBounds(30, 150, 120, 20);
        loginPanel.add(search);

        searchField = new JTextField();
        searchField.setFont(fieldFont);
        searchField.setBounds(140, 150, 200, 20);
        loginPanel.add(searchField);

        searchBtn = new JButton("Search");
        searchBtn.setFont(buttonFont);
        searchBtn.setBounds(140, 180, 90, 25);
        searchBtn.addActionListener(this);
        loginPanel.add(searchBtn);

        exitBtn = new JButton("Exit");
        exitBtn.setFont(buttonFont);
        exitBtn.setBounds(250, 180, 90, 25);
        exitBtn.addActionListener(this);
        loginPanel.add(exitBtn);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == insertBtn) {
            String userId = idField.getText().trim();
            String userName = nameField.getText().trim();
            String inserted = insertField.getText().trim();

            if (!userId.isEmpty() && !userName.isEmpty() && !inserted.isEmpty()) {
                try {
                    File file = new File(DATA_FILE);
                    // Create the file if it doesn't exist
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    try (FileWriter fw = new FileWriter(file, true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {

                        out.println("ID: " + userId + ", Name: " + userName + ", Info: " + inserted);
                        out.flush(); // Ensure data is written immediately
                        JOptionPane.showMessageDialog(this, "Data Inserted");

                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error writing to file.");
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please fill all fields before inserting.");
            }

        } else if (src == insertCancelBtn) {
            idField.setText("");
            nameField.setText("");
            insertField.setText("");
            JOptionPane.showMessageDialog(this, "Operation cancelled");

        } else if (src == searchBtn) {
            String query = searchField.getText().trim();
            boolean found = false;

            if (!query.isEmpty()) {
                File file = new File(DATA_FILE);
                // Create the file if it doesn't exist
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Error creating data file.");
                        ex.printStackTrace();
                        return;
                    }
                }

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(query)) {
                            found = true;
                            break;
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error reading from file.");
                    ex.printStackTrace();
                }

                if (found) {
                    JOptionPane.showMessageDialog(this, "Data Found");
                } else {
                    JOptionPane.showMessageDialog(this, "Data not found");
                }

                // Delay for 3 seconds before showing the search query
                Timer timer = new Timer(3000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        JOptionPane.showMessageDialog(DoctorFrameIntro.this, "Searching for: " + query);
                    }
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a search query.");
            }

        } else if (src == exitBtn) {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (response == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
            // If NO_OPTION or closed dialog, do nothing
        }
    }

    // Empty mouse listener methods
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new DoctorFrameIntro();
    }
}
