//Tech: Jframe, MySQL, GUI Database
package day009peopledb;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author phili
 */
public class Day009PeopleDB extends javax.swing.JFrame {

    DefaultListModel<Person> peopleListModel = new DefaultListModel<>();
    Database db;
    Person selectedPerson;

    private void reloadDatabase() {
        try {
            //fetch data from the database to a ArrayList
            ArrayList<Person> personList = db.getAllPerople();

            peopleListModel.clear();//remove any items that may be in model

            for (Person p : personList) {
                peopleListModel.addElement(p);
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

    private void addPersonToDatabase() {
        try {
            //get name from tfName and validate
            String name = tfName.getText();
            if (name.length() <= 2 || name.length() > 45) {
                JOptionPane.showMessageDialog(this,
                        "Name must be 2 - 45 characters",
                        "Invalid data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            
            
            //get age from spAge and validate
            int age = (int) spAge.getValue();
            if (name.length() <= 1 || name.length() > 150) {
                JOptionPane.showMessageDialog(this,
                        "Age must be 1 - 150 ",
                        "Invalid data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Create new Person instance
            Person person = new Person(0, name, age);
            db.AddPerson(person);

            //reload database to show result on the lstPerson
            reloadDatabase();

            //reset user input fields for new record
            tfName.setText("");
            spAge.setValue(18);

            lblStatus.setText("Added new record: " + name + "," + age);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to add data " + e.getMessage(),
                    "Error in adding",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void UpdatePersonInDatabase() {

        try{
            int id = selectedPerson.id;
            
            //get name from tfName and validate
            String name = tfName.getText();
            if (name.length() <= 2 || name.length() > 45) {
                JOptionPane.showMessageDialog(this,
                        "Name must be 2 - 45 characters",
                        "Invalid data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            //get age from spAge and validate
            int age = (int) spAge.getValue();
            if (name.length() <= 1 || name.length() > 150) {
                JOptionPane.showMessageDialog(this,
                        "Age must be 1 - 150 ",
                        "Invalid data",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
           
           

            Person updatedPerson = new Person(id,name,age);
            db.UpdatePerson(updatedPerson);
            
            
        }catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to connect " + e.getMessage(),
                    "Database error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); //FATAL ERROR, EXIT PROGRAM

        }

        }
    
    
    

    

    public Day009PeopleDB() {
        try {
            initComponents();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btAddPerson1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstPeople = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lblId = new javax.swing.JLabel();
        spAge = new javax.swing.JSpinner();
        btUpdatePerson = new javax.swing.JButton();
        btDeletePerson = new javax.swing.JButton();
        btAddPerson3 = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        btAddPerson1.setText("Add Person");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstPeople.setModel(peopleListModel);
        lstPeople.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstPeopleValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstPeople);

        jLabel1.setText("ID:");

        jLabel2.setText("Name:");

        jLabel3.setText("Age:");

        lblId.setText("-");

        spAge.setModel(new javax.swing.SpinnerNumberModel(18, 1, 150, 1));

        btUpdatePerson.setText("Update Person");
        btUpdatePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdatePersonActionPerformed(evt);
            }
        });

        btDeletePerson.setText("Delete Person");

        btAddPerson3.setText("Add Person");
        btAddPerson3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddPerson3ActionPerformed(evt);
            }
        });

        lblStatus.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btDeletePerson, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(spAge)))
                            .addComponent(btAddPerson3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btUpdatePerson, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 43, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(spAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(btAddPerson3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btUpdatePerson)
                        .addGap(18, 18, 18)
                        .addComponent(btDeletePerson))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addComponent(lblStatus)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAddPerson3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddPerson3ActionPerformed
        // TODO add your handling code here:
        addPersonToDatabase();
    }//GEN-LAST:event_btAddPerson3ActionPerformed

    private void btUpdatePersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdatePersonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btUpdatePersonActionPerformed

    private void lstPeopleValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstPeopleValueChanged
        // TODO add your handling code here:
       selectedPerson = lstPeople.getSelectedValue();
        if (selectedPerson==null) {
            lblStatus.setText("No person is selected");
        return;
        }
       lblId.setText(String.valueOf(selectedPerson.id));
       tfName.setText(String.valueOf(selectedPerson.name));
       spAge.setValue(selectedPerson.age);
       
       
    }//GEN-LAST:event_lstPeopleValueChanged

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
            java.util.logging.Logger.getLogger(Day009PeopleDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day009PeopleDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day009PeopleDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day009PeopleDB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day009PeopleDB().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddPerson1;
    private javax.swing.JButton btAddPerson3;
    private javax.swing.JButton btDeletePerson;
    private javax.swing.JButton btUpdatePerson;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<Person> lstPeople;
    private javax.swing.JSpinner spAge;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
