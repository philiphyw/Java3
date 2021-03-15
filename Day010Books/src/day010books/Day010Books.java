/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day010books;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phili
 */
public class Day010Books extends javax.swing.JFrame {

    DefaultListModel<Book> bookListModel = new DefaultListModel<>();
    Database db; // need to create an instance on the init process, or the codes won't work
    BufferedImage currentBuffImage;
    Book currentEditedBook = null;
    JFileChooser fileChooserImage = new JFileChooser();
    JFileChooser fileChooserCsv = new JFileChooser();

    public Day010Books() {
        initComponents();
        try {
            db = new Database();
            fileChooserImage.setFileFilter(new FileNameExtensionFilter("Images (*.gif,*.png,*.jpg,*.jpeg)", "gif", "png", "jpg", "jpeg"));
            fileChooserCsv.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
            reloadDatabase();
        } catch (SQLException e) {
            showSQLException(e.getMessage());
        }
    }
    


    private void showSQLException(String msg) {
        JOptionPane.showMessageDialog(this,
                "Error found while communicating with database: " + msg,
                "SQLException",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showNullPointerException(String msg) {
        JOptionPane.showMessageDialog(this,
                "No book is selected for the action: " + msg,
                "NullPointerException",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showIOException(String msg){
        JOptionPane.showMessageDialog(this,
                "Error while accessing the file: " + msg,
                "IOException",
                JOptionPane.ERROR_MESSAGE
        );
    }

    
    private void reloadDatabase() {

        try {
            ArrayList<Book> bookList = db.getAllBooks();
            bookList.forEach(b -> bookListModel.addElement(b));
        } catch (SQLException ex) {
            showSQLException(ex.getMessage());
        }

    }

    //START - Code blocks to do the conversion between image and byte[]
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        // FIXME: keep original width/height ratio
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private BufferedImage byteArrayToBufferedImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }

    byte[] bufferedImageToByteArray(BufferedImage bi) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (!ImageIO.write(bi, "png", baos)) {
            throw new IOException("Error creating png data");
        }
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }
    //END - Code blocks to do the conversion between image and byte[]

    //Initialize the dialog window dlgAE
    private void showDlgAE(Book book) {

        if (book == null) {
            dlgAE.setTitle("New Book");

        } else {
            dlgAE.setTitle("Editing the Book:" + book.titleAndAuthor);
            dlgAE_lblId.setText(book.id + "");
            dlgAE_tfIsbn.setText(book.isbn);
            dlgAE_tfTitleAndAuthor.setText(book.titleAndAuthor);
            //get byte[] data from currentEditedBook and convert to a BufferedImage, then declare an ImageIcon to show it on the label

            if (book.coverImage == null) {
                dlgAE_lblcoverImage.setIcon(null);
                currentBuffImage = null;
            } else {
                try {
                    currentBuffImage = byteArrayToBufferedImage(book.coverImage);
                    // FIXME: use constants or ask JLabel
                    currentBuffImage = resize(currentBuffImage, 150, 150);
                    Icon icon = new ImageIcon(currentBuffImage);
                    dlgAE_lblcoverImage.setIcon(icon);
                } catch (IOException ex) {
                    showSQLException(ex.getMessage());
                }
            }

        }
        //Below lines need to be at the end of the fucntion to make it work normally, otherwise the diglog window will show up beofre assigning value to the fields
        dlgAE.pack();//need this code to tell swing to measure the size of the dialog window and display properly, otherwise will show in minimum size
        dlgAE.setModal(true);//set to true to prevent use switch back to the parent window while the dialog window is opening
        dlgAE.setLocationRelativeTo(this);//dialog shows on top of current window
        dlgAE.setVisible(true);

    }

    private void addBook(Book book) {

        if (book == null) {
            showNullPointerException("There's no book to add");
        } else if (book.id != 0) {//should never happen
            showSQLException("This book already exists in the database");
        } else {
            //validate the format of the book
            if (isValidatedBook(book) == false) {
                return;
            }
            try {
                db.addBook(book);
            } catch (SQLException e) {
                showSQLException(e.getMessage());
            }

        }

    }

    private boolean isValidatedBook(Book book) {
        ArrayList<String> errorList = new ArrayList<>();
        String isbn = book.isbn;
        String titleAndAuthor = book.titleAndAuthor;

        //validate isbn: 13 or 10, with hyphens, require numbers and hyphens only, last character may be X (uppercase)
        if (!isbn.matches("^[0-9 -]{10,13}X?$")) {
            errorList.add("isbn must be 13 or 10, with hyphens, last character may be X (uppercase)");
        }

        //validate titleAndAuthor:2 - 200 character
        if (titleAndAuthor.length() <= 1 || titleAndAuthor.length() >= 200) {
            errorList.add("titleAndAuthor must be 2 - 200 character");
        }
        //
        if (!errorList.isEmpty()) {
            JOptionPane.showMessageDialog(this, String.join("\n", errorList),
                    "Input error(s)", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgAE = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dlgAE_lblId = new javax.swing.JLabel();
        dlgAE_tfIsbn = new javax.swing.JTextField();
        dlgAE_tfTitleAndAuthor = new javax.swing.JTextField();
        dlgAE_lblcoverImage = new javax.swing.JLabel();
        dlgAE_lblStatus = new javax.swing.JLabel();
        dlgAE_btRemoveCover = new javax.swing.JButton();
        dlgAE_btSave = new javax.swing.JButton();
        dlgAE_btCance = new javax.swing.JButton();
        pmRightClick = new javax.swing.JPopupMenu();
        pmRightClick_miEdit = new javax.swing.JMenuItem();
        pmRightClick_miDelete = new javax.swing.JMenuItem();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstBooks = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        mnEdit_miAdd = new javax.swing.JMenuItem();
        mnEdit_miEdit = new javax.swing.JMenuItem();

        dlgAE.setResizable(false);

        jLabel1.setText("ID:");

        jLabel2.setText("ISBN:");

        jLabel3.setText("Title and Author:");

        dlgAE_lblId.setText("-");

        dlgAE_lblcoverImage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dlgAE_lblcoverImage.setMaximumSize(new java.awt.Dimension(160, 160));
        dlgAE_lblcoverImage.setMinimumSize(new java.awt.Dimension(160, 160));
        dlgAE_lblcoverImage.setName(""); // NOI18N
        dlgAE_lblcoverImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dlgAE_lblcoverImageMouseClicked(evt);
            }
        });

        dlgAE_lblStatus.setText("...");

        dlgAE_btRemoveCover.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        dlgAE_btRemoveCover.setText("Remove Cover");
        dlgAE_btRemoveCover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAE_btRemoveCoverActionPerformed(evt);
            }
        });

        dlgAE_btSave.setText("Save");
        dlgAE_btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAE_btSaveActionPerformed(evt);
            }
        });

        dlgAE_btCance.setText("Cancel");
        dlgAE_btCance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAE_btCanceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dlgAELayout = new javax.swing.GroupLayout(dlgAE.getContentPane());
        dlgAE.getContentPane().setLayout(dlgAELayout);
        dlgAELayout.setHorizontalGroup(
            dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dlgAE_lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(dlgAELayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dlgAE_lblcoverImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dlgAELayout.createSequentialGroup()
                        .addComponent(dlgAE_btRemoveCover, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dlgAE_btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dlgAE_btCance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dlgAELayout.createSequentialGroup()
                        .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dlgAELayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dlgAE_lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dlgAELayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dlgAE_tfIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(dlgAELayout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dlgAE_tfTitleAndAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        dlgAELayout.setVerticalGroup(
            dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAELayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dlgAE_lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dlgAE_tfIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dlgAE_tfTitleAndAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(dlgAE_lblcoverImage, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dlgAE_btRemoveCover)
                    .addComponent(dlgAE_btSave)
                    .addComponent(dlgAE_btCance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dlgAE_lblStatus))
        );

        pmRightClick_miEdit.setText("Edit");
        pmRightClick_miEdit.setToolTipText("");
        pmRightClick.add(pmRightClick_miEdit);

        pmRightClick_miDelete.setText("Delete");
        pmRightClick_miDelete.setToolTipText("");
        pmRightClick.add(pmRightClick_miDelete);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 300));

        lblStatus.setText("...");
        getContentPane().add(lblStatus, java.awt.BorderLayout.PAGE_END);

        lstBooks.setModel(bookListModel);
        lstBooks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lstBooksMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstBooks);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        mnEdit_miAdd.setText("Add");
        mnEdit_miAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEdit_miAddActionPerformed(evt);
            }
        });
        jMenu2.add(mnEdit_miAdd);

        mnEdit_miEdit.setText("Edit");
        mnEdit_miEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnEdit_miEditActionPerformed(evt);
            }
        });
        jMenu2.add(mnEdit_miEdit);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dlgAE_btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAE_btSaveActionPerformed
        dlgAE_lblStatus.setText("");

        //get the values from the fields
        String id = dlgAE_lblId.getText();
        String isbn = dlgAE_tfIsbn.getText();
        String titleAndAuthor = dlgAE_tfTitleAndAuthor.getText();
         byte[] coverImage = null;
        if (currentBuffImage != null) {
            try {
                coverImage = bufferedImageToByteArray(currentBuffImage);
            } catch (IOException ex) {
                showIOException(ex.getMessage());
            }
        }
       
                
        if (isbn.equals("") || titleAndAuthor.equals("")) {
            dlgAE_lblStatus.setText("Please input isbn and titleAndAuthor ");
            return;
        }

        if (dlgAE_lblId.getText().equals("-")) {

            Book newBook = new Book(0, isbn, titleAndAuthor, coverImage);

            //validate the fields
            if (isValidatedBook(newBook)) {
                addBook(newBook);
                dlgAE_lblStatus.setText("Added Book");

            }

        } else {
            Book EditedBook = new Book(Integer.parseInt(id), isbn, titleAndAuthor, coverImage);
            //validate the fields
            if (isValidatedBook(EditedBook)) {
                try {
                    db.updateBook(EditedBook);
                    dlgAE_lblStatus.setText("Edited book");
                } catch (SQLException ex) {
                    showSQLException(ex.getMessage());
                }

            }
        }

        //initialize the filed values for new record
        dlgAE_tfIsbn.setText("");
        dlgAE_tfTitleAndAuthor.setText("");

    }//GEN-LAST:event_dlgAE_btSaveActionPerformed

    private void dlgAE_btCanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAE_btCanceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dlgAE_btCanceActionPerformed

    private void dlgAE_btRemoveCoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAE_btRemoveCoverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dlgAE_btRemoveCoverActionPerformed

    //Click to select image and show on the label in the dialog window
    private void dlgAE_lblcoverImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlgAE_lblcoverImageMouseClicked

        if (fileChooserImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File chosenFile = fileChooserImage.getSelectedFile();
                currentBuffImage = ImageIO.read(chosenFile);
                // FIXME: use constants or ask JLabel
                currentBuffImage = resize(currentBuffImage, 150, 150);
                Icon icon = new ImageIcon(currentBuffImage);
                dlgAE_lblcoverImage.setIcon(icon);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "File error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }//GEN-LAST:event_dlgAE_lblcoverImageMouseClicked

    private void mnEdit_miAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEdit_miAddActionPerformed
        showDlgAE(null);
    }//GEN-LAST:event_mnEdit_miAddActionPerformed

    private void mnEdit_miEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnEdit_miEditActionPerformed
        try {
            Book selectedBook = lstBooks.getSelectedValue();
            if (selectedBook == null) {
                JOptionPane.showMessageDialog(this, "Please select a book to edit.");
                return;
            }
            int targetId = lstBooks.getSelectedValue().id;
            Book book = db.getBookById(targetId);
            if (book == null) {//should never happen
                JOptionPane.showMessageDialog(this, "Can not find this book in the database.");
                return;
            }
            showDlgAE(book);
        } catch (SQLException | NullPointerException ex) {
            showSQLException(ex.getMessage());
        }
    }//GEN-LAST:event_mnEdit_miEditActionPerformed

    //handle the right-click above the Jlist lstBooks
    private void lstBooksMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstBooksMouseReleased
        try {
            if (evt.isPopupTrigger()) {
                pmRightClick.show(this, evt.getX(), evt.getY());
            }
            int row = lstBooks.locationToIndex(evt.getPoint());
            lstBooks.setSelectedIndex(row);

            int targetId = lstBooks.getSelectedValue().id;

            //can NOT just use currentEidtedBook = lstBooks.getSelectedValue, since the books on the lstBooks are generated by getAllBooks() method, they don't have value on the coverImage field
            currentEditedBook = db.getBookById(targetId);//use getBookByID method to get the coverImage data for a book
        } catch (SQLException ex) {
            showSQLException(ex.getMessage());
        }
    }//GEN-LAST:event_lstBooksMouseReleased

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
            java.util.logging.Logger.getLogger(Day010Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day010Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day010Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day010Books.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day010Books().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog dlgAE;
    private javax.swing.JButton dlgAE_btCance;
    private javax.swing.JButton dlgAE_btRemoveCover;
    private javax.swing.JButton dlgAE_btSave;
    private javax.swing.JLabel dlgAE_lblId;
    private javax.swing.JLabel dlgAE_lblStatus;
    private javax.swing.JLabel dlgAE_lblcoverImage;
    private javax.swing.JTextField dlgAE_tfIsbn;
    private javax.swing.JTextField dlgAE_tfTitleAndAuthor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<Book> lstBooks;
    private javax.swing.JMenuItem mnEdit_miAdd;
    private javax.swing.JMenuItem mnEdit_miEdit;
    private javax.swing.JPopupMenu pmRightClick;
    private javax.swing.JMenuItem pmRightClick_miDelete;
    private javax.swing.JMenuItem pmRightClick_miEdit;
    // End of variables declaration//GEN-END:variables
}
