/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day011finalflights;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phili
 */
public class Day011FinalFlights extends javax.swing.JFrame {

    DefaultListModel<Flight> flightListModel = new DefaultListModel<>();
    Database db; // need to create an instance on the init process, or the codes won't work
    Flight currentEditedFlight = null;
    final JFileChooser fc = new JFileChooser();
    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

    /**
     * Creates new form Day011FinalFlights
     */
    public Day011FinalFlights() {
        initComponents();
        try {
            db = new Database();
            reloadDatabase();
            initFileChooser();
        } catch (SQLException ex) {
            showException("SQLException", ex.getMessage());
        }
    }

    private void reloadDatabase() {

        try {
            flightListModel.clear();
            currentEditedFlight = null;
            ArrayList<Flight> flightList = db.getAllFlights();//SQLException
            flightList.forEach(b -> flightListModel.addElement(b)); //ParseException
            lblStatus.setText("Total Flight(s): " + flightList.size());
        } catch (SQLException ex) {
            showException("SQLException", ex.getMessage());
        } catch (ParseException ex) {
            showException("ParseException", ex.getMessage());
        }

    }

    private void initFileChooser() {

        //Set the fc selection mode, there're 3 modes:FILES_ONLY,DIRECTORIES_ONLY, FILES_AND_DIRECTORIES
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //Disable the Accept all filter to limit user can only choose .txt files
        fc.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(".\\"));

    }

    private void showException(String exceptionType, String msg) {
        JOptionPane.showMessageDialog(this,
                "Error found : " + msg,
                exceptionType,
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void showDeleteDlg() {
        currentEditedFlight = lstFlights.getSelectedValue();
        if (currentEditedFlight == null) {
            showException("No selected record", "Please select a record to delete");
            return;
        }

        int returnVal = JOptionPane.showConfirmDialog(this, "Are you sure to delete the record?", "Confirm Deletion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.CANCEL_OPTION, null);
        if (returnVal == JOptionPane.CANCEL_OPTION) {
            return;
        }

        try {
            db.deleteFlight(currentEditedFlight);//SQLException
            currentEditedFlight = null;
            reloadDatabase();
        } catch (SQLException ex) {
            showException("SQLException", ex.getMessage());
        }
    }

    private String getFileName() {
        //Show dialog and create new file
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            if (selectedFile == null) {
                return "";
            }
            String fileName = fc.getSelectedFile().getName();

            //check if user has input a file name, if NOT, abort the create new file process
            if (fileName == null || fileName.equals("")) {
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

    private void writeDataToFile() {
        lblStatus.setText("");
        List<Flight> selectedFlights = lstFlights.getSelectedValuesList();
        if (selectedFlights.isEmpty()) {
            showException("No records selected", "Please select one or more records.");
            return;
        }

        String fileName = getFileName();
        if (fileName == null || fileName.equals("")) {
            lblStatus.setText("Cancel exporting");
            return;
        }
        //need to add code to verify if the file exists in current folder
        //if it exists, ask user to choose another name, abort the create new file process
        try ( PrintWriter pw = new PrintWriter(new File(fileName))) {

            //Check it there's any content to write, if yes, write to the file
            selectedFlights.forEach(f -> {
                pw.println(f.toString());
            });

            lblStatus.setText("Exported successfully");
        } catch (FileNotFoundException ex) {
            showException("FileNotFoundException", ex.getMessage());
        }
    }

    private void showDlgAE(Flight flight) {
        //initilize the dialog window
        dlgAE.setTitle("");
            dlgAE_lblId.setText("");
            dlgAE_tfDate.setText("");
            dlgAE_tfFromCode.setText("");
            dlgAE_tfToCode.setText("");
            dlgAE_comboType.setSelectedIndex(0);
            dlgAE_slidePassengers.setValue(0);
            dlgAE_lblPassengers.setText("");
        
        
        
        if (flight == null) {
            dlgAE.setTitle("New Flight");

        } else {
            dlgAE.setTitle(String.format("Editing the Flight:%d, from %s to %s at %s", flight.id, flight.fromCode, flight.toCode, df.format(flight.onDate)));
            dlgAE_lblId.setText(flight.id + "");
            dlgAE_tfDate.setText(df.format(flight.onDate));
            dlgAE_tfFromCode.setText(flight.fromCode);
            dlgAE_tfToCode.setText(flight.toCode);
            dlgAE_comboType.setSelectedItem(flight.type.toString());
            dlgAE_slidePassengers.setValue(flight.passengers);
            dlgAE_lblPassengers.setText(flight.passengers + "");

        }
        //Below lines need to be at the end of the fucntion to make it work normally, otherwise the diglog window will show up beofre assigning value to the fields
        dlgAE.pack();//need this code to tell swing to measure the size of the dialog window and display properly, otherwise will show in minimum size
        dlgAE.setModal(true);//set to true to prevent use switch back to the parent window while the dialog window is opening
        dlgAE.setLocationRelativeTo(this);//dialog shows on top of current window
        dlgAE.setVisible(true);

    }

    private void editFlight() {
        currentEditedFlight = lstFlights.getSelectedValue();
        if (currentEditedFlight == null) {
            showException("NoFileIsSelected", "Please select a file to edit");
            return;
        }
        showDlgAE(currentEditedFlight);

    }

    private boolean isValidatedFlight() {
        ArrayList<String> errorList = new ArrayList<>();
        //validate data foramt
        try {
            Date onDate = df.parse(dlgAE_tfDate.getText());//ParseException
        } catch (ParseException ex) {
            errorList.add("Date must be input in the 'dd-mm-yyyy' format");
        }

        String fromCode = dlgAE_tfFromCode.getText();
        String toCode = dlgAE_tfToCode.getText();

        //validate fromCode, toCode
        if (!fromCode.matches("[A-Z]{3,5}")) {
            errorList.add("From code must be 3-5 letter in uppercase");
        }

        if (!toCode.matches("[A-Z]{3,5}")) {
            errorList.add("To code must be 3-5 letter in uppercase");
        }
        try {
            day011finalflights.Type type = day011finalflights.Type.valueOf(dlgAE_comboType.getSelectedItem().toString());//IllegalArgumentException
        } catch (IllegalArgumentException ex) {
            errorList.add("Please select the type of flight");
        }

        int passengers = dlgAE_slidePassengers.getValue();
        if (passengers <= 0) {
            errorList.add("Passengers must be 1 or more");
        }

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

        pmRightClick = new javax.swing.JPopupMenu();
        pmRC_miEdit = new javax.swing.JMenuItem();
        pmRC_miDelete = new javax.swing.JMenuItem();
        dlgAE = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dlgAE_lblId = new javax.swing.JLabel();
        dlgAE_tfDate = new javax.swing.JTextField();
        dlgAE_tfFromCode = new javax.swing.JTextField();
        dlgAE_tfToCode = new javax.swing.JTextField();
        dlgAE_comboType = new javax.swing.JComboBox<>();
        dlgAE_slidePassengers = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        dlgAE_lblPassengers = new javax.swing.JLabel();
        dlgAE_btCancel = new javax.swing.JButton();
        dlgAE_btSave = new javax.swing.JButton();
        dlgAE_lblStatus = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstFlights = new javax.swing.JList<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnFile_miExport = new javax.swing.JMenuItem();
        mnFile_miExit = new javax.swing.JMenuItem();
        muEdit = new javax.swing.JMenu();
        muEdit_miAdd = new javax.swing.JMenuItem();
        muEdit_miEdit = new javax.swing.JMenuItem();
        muEdit_miDelete = new javax.swing.JMenuItem();

        pmRC_miEdit.setText("Edit");
        pmRC_miEdit.setToolTipText("");
        pmRC_miEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmRC_miEditActionPerformed(evt);
            }
        });
        pmRightClick.add(pmRC_miEdit);

        pmRC_miDelete.setText("Delete");
        pmRC_miDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmRC_miDeleteActionPerformed(evt);
            }
        });
        pmRightClick.add(pmRC_miDelete);

        dlgAE.setMinimumSize(new java.awt.Dimension(300, 400));
        dlgAE.setModal(true);
        dlgAE.setResizable(false);

        jLabel1.setText("id:");

        jLabel2.setText("Date:");

        jLabel3.setText("From Code:");

        jLabel4.setText("To Code:");

        dlgAE_lblId.setText("-");

        dlgAE_tfDate.setToolTipText("dd-mm-yyyy");

        dlgAE_comboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Please select the type", "Domestic", "International", "Private" }));

        dlgAE_slidePassengers.setMajorTickSpacing(25);
        dlgAE_slidePassengers.setMaximum(200);
        dlgAE_slidePassengers.setMinorTickSpacing(25);
        dlgAE_slidePassengers.setPaintLabels(true);
        dlgAE_slidePassengers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                dlgAE_slidePassengersMouseReleased(evt);
            }
        });

        jLabel5.setText("Passengers:");

        dlgAE_btCancel.setText("Cancel");
        dlgAE_btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAE_btCancelActionPerformed(evt);
            }
        });

        dlgAE_btSave.setText("Save");
        dlgAE_btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dlgAE_btSaveActionPerformed(evt);
            }
        });

        dlgAE_lblStatus.setText("...");

        javax.swing.GroupLayout dlgAELayout = new javax.swing.GroupLayout(dlgAE.getContentPane());
        dlgAE.getContentPane().setLayout(dlgAELayout);
        dlgAELayout.setHorizontalGroup(
            dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dlgAELayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dlgAE_slidePassengers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dlgAELayout.createSequentialGroup()
                        .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(dlgAELayout.createSequentialGroup()
                                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(dlgAELayout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dlgAE_lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dlgAELayout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dlgAE_tfDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(dlgAELayout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dlgAE_tfFromCode))
                                    .addGroup(dlgAELayout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dlgAE_tfToCode))
                                    .addGroup(dlgAELayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dlgAE_lblPassengers, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 10, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dlgAELayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(dlgAE_comboType, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(dlgAELayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(dlgAE_btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(dlgAE_btSave, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addComponent(dlgAE_lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(dlgAE_tfDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dlgAE_tfFromCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dlgAE_tfToCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(dlgAE_comboType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dlgAE_slidePassengers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dlgAE_lblPassengers))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(dlgAELayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dlgAE_btCancel)
                    .addComponent(dlgAE_btSave))
                .addGap(20, 20, 20)
                .addComponent(dlgAE_lblStatus))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(400, 300));

        lblStatus.setText("...");
        getContentPane().add(lblStatus, java.awt.BorderLayout.PAGE_END);

        lstFlights.setModel(flightListModel);
        lstFlights.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lstFlightsMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lstFlightsMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lstFlights);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        mnFile_miExport.setText("Save selection...");
        mnFile_miExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFile_miExportActionPerformed(evt);
            }
        });
        jMenu1.add(mnFile_miExport);

        mnFile_miExit.setText("Exit");
        mnFile_miExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFile_miExitActionPerformed(evt);
            }
        });
        jMenu1.add(mnFile_miExit);

        jMenuBar1.add(jMenu1);

        muEdit.setText("Edit");

        muEdit_miAdd.setText("Add");
        muEdit_miAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muEdit_miAddActionPerformed(evt);
            }
        });
        muEdit.add(muEdit_miAdd);

        muEdit_miEdit.setText("Edit");
        muEdit_miEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muEdit_miEditActionPerformed(evt);
            }
        });
        muEdit.add(muEdit_miEdit);

        muEdit_miDelete.setText("Delete");
        muEdit_miDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                muEdit_miDeleteActionPerformed(evt);
            }
        });
        muEdit.add(muEdit_miDelete);

        jMenuBar1.add(muEdit);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void muEdit_miEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muEdit_miEditActionPerformed
        editFlight();
    }//GEN-LAST:event_muEdit_miEditActionPerformed

    private void lstFlightsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstFlightsMouseReleased
        if (evt.isPopupTrigger()) {
            pmRightClick.show(this, evt.getX(), evt.getY());
        }
        int row = lstFlights.locationToIndex(evt.getPoint());
        lstFlights.setSelectedIndex(row);
        int targetId = lstFlights.getSelectedValue().id;
        try {
            currentEditedFlight = db.getFlightById(targetId);//SQLException,ParseException
        } catch (SQLException ex) {
            showException("SQLException", ex.getMessage());
        } catch (ParseException ex) {
            showException("ParseException", ex.getMessage());
        }
    }//GEN-LAST:event_lstFlightsMouseReleased

    private void pmRC_miDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmRC_miDeleteActionPerformed

        showDeleteDlg();
    }//GEN-LAST:event_pmRC_miDeleteActionPerformed

    private void muEdit_miDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muEdit_miDeleteActionPerformed
        showDeleteDlg();
    }//GEN-LAST:event_muEdit_miDeleteActionPerformed

    private void mnFile_miExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFile_miExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_mnFile_miExitActionPerformed

    private void mnFile_miExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFile_miExportActionPerformed

        writeDataToFile();
    }//GEN-LAST:event_mnFile_miExportActionPerformed

    private void dlgAE_btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAE_btSaveActionPerformed
        addUpdateFlightToDb();
    }//GEN-LAST:event_dlgAE_btSaveActionPerformed

    private void addUpdateFlightToDb(){
            if (isValidatedFlight()) {
            try {
                //generate values for new/edited flight

                Date onDate = df.parse(dlgAE_tfDate.getText());//ParseException

                String fromCode = dlgAE_tfFromCode.getText();
                String toCode = dlgAE_tfToCode.getText();

                day011finalflights.Type type = day011finalflights.Type.valueOf(dlgAE_comboType.getSelectedItem().toString());//IllegalArgumentException

                int passengers = dlgAE_slidePassengers.getValue();
                //add new flight
                if (currentEditedFlight == null) {
                    Flight newFlight = new Flight(0, onDate, fromCode, toCode, type, passengers);
                    db.addFlight(newFlight);
                    dlgAE_lblStatus.setText("Added new flight");
                }else{
                // update edited flight
                        currentEditedFlight.onDate = onDate;
                        currentEditedFlight.toCode=toCode;
                        currentEditedFlight.fromCode = fromCode;
                        currentEditedFlight.type = type;
                        currentEditedFlight.passengers = passengers;
                        
                        db.updateFlight(currentEditedFlight);
                        currentEditedFlight = null;
                        dlgAE_lblStatus.setText("Updated flight");
                        
                }

                reloadDatabase();
                
            } catch (IllegalArgumentException ex) {
            } catch (ParseException ex) {
            } catch (SQLException ex) {
                showException("SQLException", "Something wrong sending record to database, "+ex.getMessage());
            }
        }
    }
    
    private void dlgAE_slidePassengersMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dlgAE_slidePassengersMouseReleased
        int value = dlgAE_slidePassengers.getValue();
        dlgAE_lblPassengers.setText(value + "");
    }//GEN-LAST:event_dlgAE_slidePassengersMouseReleased

    private void dlgAE_btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dlgAE_btCancelActionPerformed
        int returnVal = JOptionPane.showConfirmDialog(this, "Are you sure to exit?", "Exit the window", JOptionPane.OK_CANCEL_OPTION, JOptionPane.CANCEL_OPTION, null);
        if (returnVal == JOptionPane.CANCEL_OPTION) {
            return;
        }
        dlgAE.dispose();
    }//GEN-LAST:event_dlgAE_btCancelActionPerformed

    private void muEdit_miAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_muEdit_miAddActionPerformed
       currentEditedFlight = null;
       showDlgAE(null);
    }//GEN-LAST:event_muEdit_miAddActionPerformed

    private void pmRC_miEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmRC_miEditActionPerformed
       editFlight();
    }//GEN-LAST:event_pmRC_miEditActionPerformed

    private void lstFlightsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstFlightsMouseClicked
       if(evt.getClickCount()==2 && evt.getButton()==1){
            currentEditedFlight = lstFlights.getSelectedValue();
            editFlight();
            
        }        // TODO add your handling code here:
    }//GEN-LAST:event_lstFlightsMouseClicked

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
            java.util.logging.Logger.getLogger(Day011FinalFlights.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day011FinalFlights.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day011FinalFlights.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day011FinalFlights.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day011FinalFlights().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog dlgAE;
    private javax.swing.JButton dlgAE_btCancel;
    private javax.swing.JButton dlgAE_btSave;
    private javax.swing.JComboBox<String> dlgAE_comboType;
    private javax.swing.JLabel dlgAE_lblId;
    private javax.swing.JLabel dlgAE_lblPassengers;
    private javax.swing.JLabel dlgAE_lblStatus;
    private javax.swing.JSlider dlgAE_slidePassengers;
    private javax.swing.JTextField dlgAE_tfDate;
    private javax.swing.JTextField dlgAE_tfFromCode;
    private javax.swing.JTextField dlgAE_tfToCode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<Flight> lstFlights;
    private javax.swing.JMenuItem mnFile_miExit;
    private javax.swing.JMenuItem mnFile_miExport;
    private javax.swing.JMenu muEdit;
    private javax.swing.JMenuItem muEdit_miAdd;
    private javax.swing.JMenuItem muEdit_miDelete;
    private javax.swing.JMenuItem muEdit_miEdit;
    private javax.swing.JMenuItem pmRC_miDelete;
    private javax.swing.JMenuItem pmRC_miEdit;
    private javax.swing.JPopupMenu pmRightClick;
    // End of variables declaration//GEN-END:variables
}
