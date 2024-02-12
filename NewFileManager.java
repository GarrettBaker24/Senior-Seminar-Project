import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class NewFileManager extends JFrame {
    private JTextArea textArea;
    private JButton addButton, viewButton, removeButton, saveButton, loadButton, readButton;
    private JPasswordField passwordField;

    private String password = "NotYet";

    public NewFileManager() {
        setTitle("File Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        addButton = new JButton("Add File");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPassword = showPasswordInputDialog("Enter the password:");
                if (inputPassword != null && inputPassword.equals(password)) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        textArea.append(selectedFile.getAbsolutePath() + "\n");
                    }
                } else if (inputPassword != null) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewButton = new JButton("View Files");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPassword = showPasswordInputDialog("Enter the password:");
                if (inputPassword != null && inputPassword.equals(password)) {
                    sortFiles();
                    JOptionPane.showMessageDialog(null, textArea.getText(), "Stored Files",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if (inputPassword != null) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton = new JButton("Remove File");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedFile = textArea.getSelectedText();
                if (selectedFile != null) {
                    int startIndex = textArea.getSelectionStart();
                    int endIndex = textArea.getSelectionEnd();
                    textArea.replaceRange("", startIndex, endIndex);
                }
            }
        });

        saveButton = new JButton("Save List");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPassword = showPasswordInputDialog("Enter the password to save the file:");
                if (inputPassword != null && inputPassword.equals(password)) {
                    saveToFile();
                } else if (inputPassword != null) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadButton = new JButton("Load List");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPassword = showPasswordInputDialog("Enter the password to load the file:");
                if (inputPassword != null && inputPassword.equals(password)) {
                    loadFromFile();
                } else if (inputPassword != null) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        readButton = new JButton("Read File");
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPassword = showPasswordInputDialog("Enter the password:");
                if (inputPassword != null && inputPassword.equals(password)) {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                            StringBuilder fileContent = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                fileContent.append(line).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, fileContent.toString(), "File Content",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(null, "Error reading file!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (inputPassword != null) {
                    JOptionPane.showMessageDialog(null, "Incorrect password!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(readButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String showPasswordInputDialog(String message) {
        passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, passwordField, message,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        }
        return null;
    }

    private void sortFiles() {
        String[] fileLines = textArea.getText().split("\\n");
        Arrays.sort(fileLines);
        textArea.setText(String.join("\n", fileLines));
    }

    private void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            File outputFile = new File(selectedDirectory, "filelist.txt");
            try (PrintWriter writer = new PrintWriter(outputFile)) {
                writer.println(textArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.read(reader, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NewFileManager fileManager = new NewFileManager();
                fileManager.setVisible(true);
            }
        });
    }
}
