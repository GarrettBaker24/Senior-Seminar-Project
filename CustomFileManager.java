import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.Desktop;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomFileManager {
    public static void main(String[] args) {
        // Create the root node as a virtual representation
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("File System");

        // Create the tree
        JTree tree = new JTree(root);

        // Create the frame and add the tree to it
        JFrame frame = new JFrame("Basic File System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(tree));
        frame.setSize(450, 450);
        frame.setVisible(true);

        // Add a mouse listener to the tree to handle file addition and removal
        tree.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 1) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (node != null) {
                        Object[] options = { "Add File", "Delete File", "Edit File", "Open File" };
                        int choice = JOptionPane.showOptionDialog(frame,
                                "Would you like to add, delete, edit, or open a file?",
                                "File Options",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);
                        if (choice == 0) {
                            // Add file logic
                            // You can use the following code to create a new file
                            String newFileName = JOptionPane.showInputDialog("Enter the name of the new file:");
                            if (newFileName != null && !newFileName.trim().isEmpty()) {
                                File newFile = new File(newFileName);
                                try {
                                    if (newFile.createNewFile()) {
                                        System.out.println("File created: " + newFile.getName());
                                    } else {
                                        System.out.println("File already exists");
                                    }
                                } catch (IOException ex) {
                                    System.out.println("An error occurred");
                                    ex.printStackTrace();
                                }
                            }
                        } else if (choice == 1) {
                            // Delete file logic
                            // You can use the following code to delete an existing file
                            String fileNameToDelete = JOptionPane
                                    .showInputDialog("Enter the name of the file to delete:");
                            if (fileNameToDelete != null && !fileNameToDelete.trim().isEmpty()) {
                                File fileToDelete = new File(fileNameToDelete);
                                if (fileToDelete.delete()) {
                                    System.out.println("File deleted successfully");
                                } else {
                                    System.out.println("Failed to delete the file");
                                }
                            }
                        } else if (choice == 2) {
                            // Edit file logic
                            // You can use the following code to edit an existing file
                            String fileNameToEdit = JOptionPane.showInputDialog("Enter the name of the file to edit:");
                            if (fileNameToEdit != null && !fileNameToEdit.trim().isEmpty()) {
                                // Add your code here to open the file for editing, read its content, and save
                                // the changes
                            }
                        } else if (choice == 3) {
                            // Open file logic
                            String fileName = JOptionPane.showInputDialog("Enter the name of the file to open:");
                            if (fileName != null && !fileName.trim().isEmpty()) {
                                File file = new File(fileName);
                                if (file.exists()) {
                                    try {
                                        Desktop.getDesktop().open(file);
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                } else {
                                    System.out.println("File does not exist");
                                }
                            }
                        }
                    }
                }
            }
        });
        JButton browseButton = new JButton("Browse");
        // Add action listener to the button
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Here you can handle the selected file, for example, display its properties
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });
        // Add the button to the frame
        frame.add(browseButton, BorderLayout.SOUTH);
    }
}