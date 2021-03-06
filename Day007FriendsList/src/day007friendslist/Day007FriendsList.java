//Tech Highlight: JList,  DefaultListModel<E>
package day007friendslist;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

/**
 *
 * @author phili
 */
public class Day007FriendsList extends javax.swing.JFrame {

    DefaultListModel<String> friendsListModel = new DefaultListModel<>();
    static final String FILE_NAME = "Frinds List.txt";
    
    public Day007FriendsList() {
        initComponents();
         loadDataFromFile();
         lblStatus.setText("Program Started");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        btAddFriend = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstFriends = new javax.swing.JList<>();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel1.setText("Friends List");

        jLabel2.setText("Name");

        btAddFriend.setText("Add Friend");
        btAddFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddFriendActionPerformed(evt);
            }
        });

        lstFriends.setModel(friendsListModel);
        lstFriends.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstFriends.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstFriendsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstFriends);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btAddFriend)))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAddFriend))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatus)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btAddFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddFriendActionPerformed
        // TODO add your handling code here:
        String name = tfName.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please input a name",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        friendsListModel.addElement(name);
        tfName.setText("");
        lblStatus.setText("Name added");
    }//GEN-LAST:event_btAddFriendActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        String friendsList="";
        
        for (int i = 0; i < friendsListModel.getSize(); i++) {
           friendsList += friendsListModel.getElementAt(i);
        }
        lblStatus.setText(friendsList);
        saveDataToFile();
    }//GEN-LAST:event_formWindowClosing

    private void lstFriendsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFriendsValueChanged
        // TODO add your handling code here:
        if (lstFriends.getSelectedValue() == null) {
            lblStatus.setText("No selection");
            return;
        }
        String selectedValue = lstFriends.getSelectedValue();
        lblStatus.setText(selectedValue);
    }//GEN-LAST:event_lstFriendsValueChanged

    private void saveDataToFile() {
        try (PrintWriter pw = new PrintWriter(new File(FILE_NAME))) {
            for (int i = 0; i < friendsListModel.getSize(); i++) {
                pw.println(friendsListModel.getElementAt(i));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to write data to file " + e.getMessage(),
                    "File access error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

    }

    private void loadDataFromFile() {
        File file = new File(FILE_NAME);
        if(!file.exists()) {
            return; // if the file doesn't exist, quit the load data process. For the first time the program runs which hasn't created the file yet
        }
        try (Scanner input = new Scanner(new File(FILE_NAME))) {
            while (input.hasNextLine()) {
                String line = input.nextLine();
                friendsListModel.addElement(line);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load data from  file " + e.getMessage(),
                    "File access error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(Day007FriendsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day007FriendsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day007FriendsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day007FriendsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day007FriendsList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddFriend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JList<String> lstFriends;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
