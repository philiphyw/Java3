//Tech Tags: JFileChooser, File Chooser, DocumentListener, Document Listener
package day007notepad;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;

/**
 *
 * @author phili
 */
public class Day007NotePad extends javax.swing.JFrame {

    final JFileChooser fc = new JFileChooser();
    boolean hasModified = true;
    private String currentFile;

    private void initFileChooser() {

        //Set the fc selection mode, there're 3 modes:FILES_ONLY,DIRECTORIES_ONLY, FILES_AND_DIRECTORIES
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //Disable the Accept all filter to limit user can only choose .txt files
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(".\\"));

    }

    //instead of directly tracking the change on the taDocument component, should add a DocumentListener to it for easy tracking.
    private void initDocumentListerner() {
        taDocument.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                hasModified = true;
                lblStatus.setText("Inserting...");
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hasModified = true;
                lblStatus.setText("Removing");
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //plain text like txt won't trigger this event.
            }
        });

    }

    private String getFileName() {
        //Show dialog and create new file
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getName();

            //check if user has input a file name, if NOT, abort the create new file process
            if (fileName == null) {
                lblStatus.setText("Cancel file creation");
                return "";
            }

            //check if user input file name has the ".txt" extension, if NOT, then add the .txt extension at the end of the file name
            //or just use regex expression: if(fileName.matches(".*\\.[^.]{1,10}"))  -- start with a "." follow by anything but not inc
            if (fileName.length() <= 5 || !fileName.substring(fileName.length() - 4, fileName.length()).toLowerCase().equals(".txt")) {
                //may need to find out if it's worthy to use a new String Builder here         
                fileName += ".txt";
            }
            return fileName;
        }
        return "";
    }

    private void writeDataToFile(String fileName) {

        //need to add code to verify if the file exists in current folder
        //if it exists, ask user to choose another name, abort the create new file process
        try (PrintWriter pw = new PrintWriter(new File(fileName))) {

            //Check it there's any content to write, if yes, write to the file
            String content = taDocument.getText();
            if (content != null) {
                pw.println(content);
            }
            currentFile = fileName;
            this.setTitle(currentFile);
            hasModified = false;
        } catch (FileNotFoundException ex) {
            lblStatus.setText("Failed to create the file");
        }
    }

    public Day007NotePad() {
        initComponents();
        initFileChooser();
        initDocumentListerner();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        miFile = new javax.swing.JButton();
        fcFileChooser = new javax.swing.JFileChooser();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taDocument = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        mbFileExit = new javax.swing.JMenu();
        miFileNew = new javax.swing.JMenuItem();
        miFileOpen = new javax.swing.JMenuItem();
        miFileSave = new javax.swing.JMenuItem();
        miFileSaveAs = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miFileExit = new javax.swing.JMenuItem();

        miFile.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 480));

        lblStatus.setText("...");
        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(lblStatus, java.awt.BorderLayout.PAGE_END);

        taDocument.setColumns(20);
        taDocument.setRows(5);
        jScrollPane1.setViewportView(taDocument);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        mbFileExit.setText("File");

        miFileNew.setText("New");
        miFileNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFileNewActionPerformed(evt);
            }
        });
        mbFileExit.add(miFileNew);

        miFileOpen.setText("Open...");
        miFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFileOpenActionPerformed(evt);
            }
        });
        mbFileExit.add(miFileOpen);

        miFileSave.setText("Save");
        miFileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFileSaveActionPerformed(evt);
            }
        });
        mbFileExit.add(miFileSave);

        miFileSaveAs.setText("Save As...");
        miFileSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFileSaveAsActionPerformed(evt);
            }
        });
        mbFileExit.add(miFileSaveAs);
        mbFileExit.add(jSeparator1);

        miFileExit.setText("Exit");
        miFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miFileExitActionPerformed(evt);
            }
        });
        mbFileExit.add(miFileExit);

        jMenuBar1.add(mbFileExit);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void miFileSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFileSaveAsActionPerformed
        // TODO add your handling code here:
        String fileName = getFileName();
        writeDataToFile(fileName);

    }//GEN-LAST:event_miFileSaveAsActionPerformed


    private void miFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFileOpenActionPerformed
        // TODO add your handling code here:
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //taDocument.setText(fc.getSelectedFile().getAbsolutePath());
            File file = fc.getSelectedFile();
            StringBuilder sbContent = new StringBuilder();

            try (Scanner inputFile = new Scanner(file)) {
                while (inputFile.hasNextLine()) {
                    String line = inputFile.nextLine();
                    sbContent.append(line + "\n");
                }
                String content = sbContent.toString();
                taDocument.setText(content);
                currentFile = file.getName();
                this.setTitle(currentFile);
            } catch (FileNotFoundException ex) {
                lblStatus.setText("File not found");
            }
        }
    }//GEN-LAST:event_miFileOpenActionPerformed

    private void miFileNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFileNewActionPerformed
        // TODO add your handling code here:
        String fileName = getFileName();
        writeDataToFile(fileName);
    }//GEN-LAST:event_miFileNewActionPerformed

    private void miFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFileExitActionPerformed
        // TODO add your handling code here:
        if (hasModified == true) {
            int returnVal = JOptionPane.showConfirmDialog(this, "Do you want to save the change?", "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);//yes:0, No:1
            switch (returnVal) {
                case 2:
                    return;
                case 1:
                    this.dispose();
                    break;
                case 0:
                    if (currentFile != null) {
                        String fileName = currentFile;
                        writeDataToFile(fileName);
                    } else {
                        String fileName = getFileName();
                        writeDataToFile(fileName);
                    }
                    this.dispose();
                    break;
                default:
                    break;
            }
        } else {
            this.dispose();
        }

    }//GEN-LAST:event_miFileExitActionPerformed

    private void miFileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miFileSaveActionPerformed
        // TODO add your handling code here:
        if (currentFile != null) {
            String fileName = currentFile;
            writeDataToFile(fileName);
        } else {
            String fileName = getFileName();
            writeDataToFile(fileName);
        }
    }//GEN-LAST:event_miFileSaveActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Day007NotePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day007NotePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day007NotePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day007NotePad.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day007NotePad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fcFileChooser;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JMenu mbFileExit;
    private javax.swing.JButton miFile;
    private javax.swing.JMenuItem miFileExit;
    private javax.swing.JMenuItem miFileNew;
    private javax.swing.JMenuItem miFileOpen;
    private javax.swing.JMenuItem miFileSave;
    private javax.swing.JMenuItem miFileSaveAs;
    private javax.swing.JTextArea taDocument;
    // End of variables declaration//GEN-END:variables
}
