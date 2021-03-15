/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day010students;


import com.opencsv.CSVWriter;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phili
 */
public class Day010Students extends javax.swing.JFrame {

    DefaultListModel<Student> studentListModel = new DefaultListModel<>();

    Database db;

    Student selectedStudent;
    
    protected 


              
     boolean unsavedEdit;
    
   // Code blocks to handle blob field
   // DB -> Jframe : byte[] - > image->Icon.set(image)
  // Jframe-> Icon.get(image)->image/   currentBufferImage -> image->byte[]
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

    BufferedImage currentBuffImage;
     
     
     
     
     
     
    private void initFileChooser() {

        //Set the fc selection mode, there're 3 modes:FILES_ONLY,DIRECTORIES_ONLY, FILES_AND_DIRECTORIES
        fcImage.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fcCSV.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //Disable the Accept all filter to limit user can only choose .txt files
         fcImage.setAcceptAllFileFilterUsed(false);
        fcCSV.setAcceptAllFileFilterUsed(false);

        fcImage.setFileFilter(new FileNameExtensionFilter("Images (*.gif,*.png,*.jpg,*.jpeg)", "gif", "png", "jpg", "jpeg"));
        fcCSV.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        
        fcImage.setCurrentDirectory(new File(".\\"));
        fcCSV.setCurrentDirectory(new File(".\\"));

    }
    
    
    private String getFileName(String extension) {
        JFileChooser fc = new JFileChooser();
        String fileExtension="";
        if(extension==null){
        return "";
        }else if(extension.toUpperCase()=="CSV"){
        fc = fcCSV;
        fileExtension = ".csv";
        }else if(extension.toUpperCase()=="IMAGE"){
        fc = fcImage;
         fileExtension = ".jpg";
        }else{
            return "";
        }
        
        //Show dialog and create new file
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getName();

            //check if user has input a file name, if NOT, abort the create new file process
            if (fileName == null) {
                lblStatus.setText("Cancel file creation");
                return "";
            }

            //check if user input file name has extension, if NOT, then add extension at the end of the file name
            if (!fileName.matches(".*\\.[^.]{1,10}$")) {
                fileName +=fileExtension;
            }
            return fileName;
        }
        return "";
    }
    
     private void exportToCSV() throws FileNotFoundException {

         String fileName = getFileName("CSV");
         
         if (fileName == null) {
             lblStatus.setText("Cancel exporting");
             return;
         }
         
        //need to add code to verify if the file exists in current folder
        //if it exists, ask user to choose another name, abort the create new file process
        try (PrintWriter pw = new PrintWriter(new File(fileName))) {
            ArrayList<Student> studentList = (ArrayList)lstStudents.getSelectedValuesList();
            
            //Check it there's any content to write, if yes, write to the file
            if (studentList.size()>0) {
                studentList.forEach(s -> {
                    pw.println(s.toString());
                     lblStatus.setText("Exported selected records");
                  lstStudents.clearSelection();
                });
            }else{
                lblStatus.setText("No records are selected");
            }
       
        } catch (FileNotFoundException ex) {
            lblStatus.setText("Failed to create the file");
        }

    }
     
     //use the the csvwriter from opencsv libirary to handle special characters
     private void exportToCSVByOpenCSVWriter() throws FileNotFoundException, IOException {

         String fileName = getFileName("CSV");
         
         if (fileName == null) {
             lblStatus.setText("Cancel exporting");
             return;
         }
         
        //need to add code to verify if the file exists in current folder
        //if it exists, ask user to choose another name, abort the create new file process
        PrintWriter pw = new PrintWriter(new File(fileName));
        try (CSVWriter csvWriter = new CSVWriter(pw)) {
            ArrayList<Student> studentList = (ArrayList)lstStudents.getSelectedValuesList();
            String[] recordHeader = {"Id","Name"};
            csvWriter.writeNext(recordHeader);
            
            
            //Check it there's any content to write, if yes, write to the file
            if (studentList.size()>0) {
                studentList.forEach(s -> {
                   csvWriter.writeNext(new String[]{s.id + "",s.name});
                     lblStatus.setText("Exported selected records");
                  lstStudents.clearSelection();
                });
            }else{
                lblStatus.setText("No records are selected");
            }
       
        } catch (FileNotFoundException ex) {
            lblStatus.setText("Failed to create the file");
        }

    }
    
    
    
     private void showPmContext(){
    
        pmContext.pack();//need this code to tell swing to measure the size of the dialog window and display properly, otherwise will show in minimum size
       
        pmContext.setVisible(true);
        
    }
    
    
    
    
    
    private void showDlgAddEdit(Book book){
        
        
        
        dlgAddEdit.pack();//need this code to tell swing to measure the size of the dialog window and display properly, otherwise will show in minimum size
        dlgAddEdit.setModal(true);//set to true to prevent use switch back to the parent window while the dialog window is opening
        dlgAddEdit.setLocationRelativeTo(this);//dialog shows on top of current window
        dlgAddEdit.setVisible(true);
        
    }
    
    
    private void reloadDatabase() {
        try {
            //fetch data from the database to a ArrayList
            ArrayList<Student> studentList = db.getAllStudents();

            studentListModel.clear();//remove any items that may be in model

            for (Student s : studentList) {
                studentListModel.addElement(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to connect " + e.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); //FATAL ERROR, EXIT PROGRAM

        }

    }

    
    private Student getStudentById(int id) throws SQLException {
        //ResultSet get the the result of the SQL query
        Connection conn = db.conn;
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM students where id=?");
        statement.setInt(1, id);
        try ( ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                byte[] image = resultSet.getBytes("image");
                return new Student(id, name, image);
                
            } else {
                throw new SQLException("Record not found");
            }
        }

    }
    
    
    private void editStudent(Student student) {
              try{
             if (student !=null) {   
                dlgAddEdit.setTitle("Editing the file of " + student.name);
                dlgAddEdit_lblID.setText(student.id+"");
                dlgAddEdit_tfName.setText(student.name);
                
                //convert the byte[] to BufferedImage
                BufferedImage image = byteArrayToBufferedImage(student.image);
                //convert image to icon
                Icon icon = new ImageIcon(currentBuffImage);
                dlgAddEdit_lblPhoto.setIcon(icon);
             }else{
             dlgAddEdit.setTitle("Adding new student");
             
             }
             
             showDlgAddEdit();
              }catch(IOException e){
                  System.out.println(e.getMessage());
              }
                  
    }

    public Day010Students() {

        try {
            initComponents();
             JFileChooser fcImage= new JFileChooser();
            JFileChooser fcCSV= new JFileChooser();
            initFileChooser();

            db = new Database();
            reloadDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to connect " + e.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); //FATAL ERROR, EXIT PROGRAM
        }
    }

    private void sortStudent() {
        /*
        if use try(Connection conn) will return a SQLException "No operations allowed after connection closed" after the first sorting.
        because the conn has been defined as a global variable and got cloesd at the end of the first sorting, so the 2nd sort will try to execute a select query on a closed connection which causes an Exception
        
        
        */

       try{
        Connection conn = db.conn;
        String criteria = btgSort.getSelection().getActionCommand();


        String sortKey = null;
        lblStatus.setText("");
        switch (criteria.toUpperCase()) {
            case "ID":
                sortKey = "id";
                break;
            case "NAME":
                sortKey = "name";
                break;
            default:
                lblStatus.setText("Unsupported sorting critera.");
                break;

        }

        if(sortKey==null){
            return;
        }
        String sqlString = "SELECT id, name FROM students ORDER BY " + sortKey;// limit the value of sortKey to be either id or name by above switch statement

        
        PreparedStatement statement = conn.prepareStatement(sqlString);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Student> studentList = new ArrayList<>();

        while(resultSet.next()){
            String name = resultSet.getString("name");
            int id = resultSet.getInt("id");
            studentList.add(new Student(id,name,null));
        }

            //System.out.println(studentList);
            studentListModel.clear();//remove any items that may be in model

            for (Student s : studentList) {
                studentListModel.addElement(s);
            }
        lblStatus.setText("Sorted by " + sortKey);
        }catch(SQLException e){
        lblStatus.setText("Failed to sort data " + e.getMessage());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dlgAddEdit = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        dlgAddEdit_lblID = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        dlgAddEdit_tfName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        dlgAddEdit_lblPhoto = new javax.swing.JLabel();
        dlgAddEdit_btAddEdit = new javax.swing.JButton();
        dlgAddEdit_btCancel = new javax.swing.JButton();
        dlgAddEdit_RemoveImage = new javax.swing.JButton();
        fcImage = new javax.swing.JFileChooser();
        fcCSV = new javax.swing.JFileChooser();
        btgSort = new javax.swing.ButtonGroup();
        jMenuItem1 = new javax.swing.JMenuItem();
        pmContext = new javax.swing.JPopupMenu();
        pmContext_Edit = new javax.swing.JMenuItem();
        pmContext_Delete = new javax.swing.JMenuItem();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstStudents = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        mnFileExportCSV = new javax.swing.JMenuItem();
        mnFileExit = new javax.swing.JMenuItem();
        mnEdit = new javax.swing.JMenu();
        miEdit_AddStudent = new javax.swing.JMenuItem();
        miEdit_EditStudent = new javax.swing.JMenuItem();
        mnSort = new javax.swing.JMenu();
        mnSort_byId = new javax.swing.JRadioButtonMenuItem();
        mnSort_ByName = new javax.swing.JRadioButtonMenuItem();

        dlgAddEdit.setResizable(false);

        jLabel1.setText("Id:");

        dlgAddEdit_lblID.setText("-");

        jLabel3.setText("Name:");

        jLabel4.setText("Photo(click)");

        dlgAddEdit_lblPhoto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        dlgAddEdit_lblPhoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dlgAddEdit_lblPhotoMouseClicked(evt);
            }
        });

        dlgAddEdit_btAddEdit.setText("Add/Edit");
        dlgAddEdit_btAddEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAddEdit_btAddEditActionPerformed(evt);
            }
        });

        dlgAddEdit_btCancel.setText("Cancel");
        dlgAddEdit_btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAddEdit_btCancelActionPerformed(evt);
            }
        });

        dlgAddEdit_RemoveImage.setText("Remove");
        dlgAddEdit_RemoveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAddEdit_RemoveImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dlgAddEditLayout = new javax.swing.GroupLayout(dlgAddEdit.getContentPane());
        dlgAddEdit.getContentPane().setLayout(dlgAddEditLayout);
        dlgAddEditLayout.setHorizontalGroup(
            dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAddEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dlgAddEditLayout.createSequentialGroup()
                        .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dlgAddEdit_tfName, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(dlgAddEdit_lblID, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(dlgAddEdit_lblPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dlgAddEditLayout.createSequentialGroup()
                        .addComponent(dlgAddEdit_RemoveImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(dlgAddEdit_btAddEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(dlgAddEdit_btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))))
        );
        dlgAddEditLayout.setVerticalGroup(
            dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAddEditLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dlgAddEdit_lblID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dlgAddEdit_tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dlgAddEdit_lblPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(dlgAddEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dlgAddEdit_btAddEdit)
                    .addComponent(dlgAddEdit_btCancel)
                    .addComponent(dlgAddEdit_RemoveImage))
                .addGap(27, 27, 27))
        );

        jMenuItem1.setText("jMenuItem1");

        pmContext.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pmContext.setPopupSize(null);

        pmContext_Edit.setText("Edit");
        pmContext_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmContext_EditActionPerformed(evt);
            }
        });
        pmContext.add(pmContext_Edit);

        pmContext_Delete.setText("Delete");
        pmContext.add(pmContext_Delete);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(720, 480));

        lblStatus.setText("...");
        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 204), new java.awt.Color(204, 255, 102)));
        getContentPane().add(lblStatus, java.awt.BorderLayout.PAGE_END);

        lstStudents.setModel(studentListModel);
        lstStudents.setMinimumSize(new java.awt.Dimension(480, 720));
        lstStudents.setPreferredSize(new java.awt.Dimension(240, 240));
        lstStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstStudentsMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lstStudentsMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstStudents);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        mnFile.setText("File");

        mnFileExportCSV.setText("Export Selection to CSV");
        mnFileExportCSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFileExportCSVActionPerformed(evt);
            }
        });
        mnFile.add(mnFileExportCSV);

        mnFileExit.setText("Exit");
        mnFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFileExitActionPerformed(evt);
            }
        });
        mnFile.add(mnFileExit);

        jMenuBar1.add(mnFile);

        mnEdit.setText("Edit");

        miEdit_AddStudent.setText("Add Student");
        miEdit_AddStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEdit_AddStudentActionPerformed(evt);
            }
        });
        mnEdit.add(miEdit_AddStudent);

        miEdit_EditStudent.setText("Edit Student");
        miEdit_EditStudent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miEdit_EditStudentActionPerformed(evt);
            }
        });
        mnEdit.add(miEdit_EditStudent);

        jMenuBar1.add(mnEdit);

        mnSort.setText("Sort");

        btgSort.add(mnSort_byId);
        mnSort_byId.setSelected(true);
        mnSort_byId.setText("By ID");
        mnSort_byId.setActionCommand("id");
        mnSort_byId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSort_byIdActionPerformed(evt);
            }
        });
        mnSort.add(mnSort_byId);

        btgSort.add(mnSort_ByName);
        mnSort_ByName.setText("By Name");
        mnSort_ByName.setActionCommand("name");
        mnSort_ByName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSort_ByNameActionPerformed(evt);
            }
        });
        mnSort.add(mnSort_ByName);

        jMenuBar1.add(mnSort);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void miEdit_AddStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEdit_AddStudentActionPerformed
        editStudent(null);
        
    }//GEN-LAST:event_miEdit_AddStudentActionPerformed
//TODO; Fully understand the conversion between byte[],iamge,icon
    private void dlgAddEdit_lblPhotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlgAddEdit_lblPhotoMouseClicked
        // TODO add your handling code here:
         if (fcImage.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                File chosenFile = fcImage.getSelectedFile();
                currentBuffImage = ImageIO.read(chosenFile);
                // FIXME: use constants or ask JLabel
                currentBuffImage = resize(currentBuffImage, 150, 150);
                Icon icon = new ImageIcon(currentBuffImage);
                dlgAddEdit_lblPhoto.setIcon(icon);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "File error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        
    }//GEN-LAST:event_dlgAddEdit_lblPhotoMouseClicked

    private void dlgAddEdit_btAddEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAddEdit_btAddEditActionPerformed
        try {
            boolean isNewRecord = false;
            if (selectedStudent==null) {
                isNewRecord=true;
            }
        
                    
            ArrayList<String> errorList = new ArrayList<>();
            //get and validate the name
            String name = dlgAddEdit_tfName.getText();
            if (name.length() <= 2 || name.length() > 45) {
                errorList.add("Name must be 2 - 45 characters");
            }
            
            //show errorList if it's not empty
            if (errorList.size() > 0) {
                JOptionPane.showMessageDialog(this,
                        String.join("\n", errorList),
                        "Input Errors",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            //TODO; handle image
           
           
           byte[] imageBytes = bufferedImageToByteArray(currentBuffImage);
            
            
            
                      
            //based on the value of isNewRecord, call addStudent() or updateStudent method form the Database class
            if (isNewRecord) {
                Student student = new Student(0, name, imageBytes);
                 db.addStudent(student);
            }else{
                selectedStudent.name = name;
                selectedStudent.image = imageBytes;
                db.updateStudent(selectedStudent);
            }
           
            //reload the parent window list
            reloadDatabase();

            
            
            
            
            //initialize the fields for new record input
            dlgAddEdit_tfName.setText("");
            dlgAddEdit_lblID.setText("");
            dlgAddEdit_lblPhoto.setIcon(null);

        } catch (SQLException e) {

        } catch (IOException ex) {
            Logger.getLogger(Day010Students.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_dlgAddEdit_btAddEditActionPerformed

    private void dlgAddEdit_btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAddEdit_btCancelActionPerformed

        dlgAddEdit.setVisible(false);
    }//GEN-LAST:event_dlgAddEdit_btCancelActionPerformed

    private void mnSort_byIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSort_byIdActionPerformed
        sortStudent();
    }//GEN-LAST:event_mnSort_byIdActionPerformed

    private void mnSort_ByNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSort_ByNameActionPerformed
         sortStudent();
    }//GEN-LAST:event_mnSort_ByNameActionPerformed

    private void lstStudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstStudentsMouseClicked
       
        //evt.getButton()==   : 0= nobutton, 1=leftbutton, 2=middle button, 3=right button
        if(evt.getClickCount()==2 && evt.getButton()==1){
            selectedStudent = lstStudents.getSelectedValue();
            editStudent(selectedStudent);
            
        }
    }//GEN-LAST:event_lstStudentsMouseClicked

    private void mnFileExportCSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFileExportCSVActionPerformed
        
        try {
            exportToCSV();
        } catch (FileNotFoundException ex) {
            ;
        }
    }//GEN-LAST:event_mnFileExportCSVActionPerformed

    private void mnFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFileExitActionPerformed
       int returnVal = JOptionPane.showConfirmDialog(this,
               "Are you sure to exit?",
               "Exit Program",
               JOptionPane.YES_NO_OPTION);
       
       if(returnVal == JOptionPane.YES_OPTION) 
        this.dispose();
    }//GEN-LAST:event_mnFileExitActionPerformed

    private void lstStudentsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstStudentsMouseReleased
        //show popup menu when right click
        
        if (evt.isPopupTrigger()) {
            pmContext.show(this, evt.getX(), evt.getY());
        }
        
        
            
            int row = lstStudents.locationToIndex(evt.getPoint());
            lstStudents.setSelectedIndex(row);
            
            selectedStudent = lstStudents.getSelectedValue();
                   
    }//GEN-LAST:event_lstStudentsMouseReleased

    private void pmContext_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmContext_EditActionPerformed
        if (selectedStudent!=null) {
            editStudent(selectedStudent);
        }else{
            lblStatus.setText("No students are selected" );
            return;
        }
    }//GEN-LAST:event_pmContext_EditActionPerformed

    private void miEdit_EditStudentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miEdit_EditStudentActionPerformed
        selectedStudent = lstStudents.getSelectedValue();
        if (selectedStudent==null) {
            lblStatus.setText("No students are selected" );
            return;
        }
        editStudent(selectedStudent);
    }//GEN-LAST:event_miEdit_EditStudentActionPerformed

    private void dlgAddEdit_RemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAddEdit_RemoveImageActionPerformed
        if ( dlgAddEdit_lblPhoto.getIcon()==null) {
            return;
        }
        
        if (selectedStudent != null) {
            dlgAddEdit_lblPhoto.setIcon(null);
            selectedStudent.image = null;
        }else{
             dlgAddEdit_lblPhoto.setIcon(null);
        }
        
        unsavedEdit = true;
    }//GEN-LAST:event_dlgAddEdit_RemoveImageActionPerformed

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
            java.util.logging.Logger.getLogger(Day010Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day010Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day010Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day010Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day010Students().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btgSort;
    private javax.swing.JDialog dlgAddEdit;
    private javax.swing.JButton dlgAddEdit_RemoveImage;
    private javax.swing.JButton dlgAddEdit_btAddEdit;
    private javax.swing.JButton dlgAddEdit_btCancel;
    private javax.swing.JLabel dlgAddEdit_lblID;
    private javax.swing.JLabel dlgAddEdit_lblPhoto;
    private javax.swing.JTextField dlgAddEdit_tfName;
    private javax.swing.JFileChooser fcCSV;
    private javax.swing.JFileChooser fcImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<Student> lstStudents;
    private javax.swing.JMenuItem miEdit_AddStudent;
    private javax.swing.JMenuItem miEdit_EditStudent;
    private javax.swing.JMenu mnEdit;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenuItem mnFileExit;
    private javax.swing.JMenuItem mnFileExportCSV;
    private javax.swing.JMenu mnSort;
    private javax.swing.JRadioButtonMenuItem mnSort_ByName;
    private javax.swing.JRadioButtonMenuItem mnSort_byId;
    private javax.swing.JPopupMenu pmContext;
    private javax.swing.JMenuItem pmContext_Delete;
    private javax.swing.JMenuItem pmContext_Edit;
    // End of variables declaration//GEN-END:variables
}
