/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package day006aesenioicecream;

import javax.swing.JCheckBox;

/**
 *
 * @author phili
 */
public class Day006AesenioIceCream extends javax.swing.JFrame {

    static String getCheckboxValue(JCheckBox b) {
        if (b.isSelected()) {
            return b.getActionCommand();
        }
        return "";
    }

    /**
     * Creates new form Day006AesenioIceCream
     */
    public Day006AesenioIceCream() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrpFlavour = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbSprinkles = new javax.swing.JCheckBox();
        cbPeanuts = new javax.swing.JCheckBox();
        cbFudge = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        rbFlavourVanilla1 = new javax.swing.JRadioButton();
        rbFlavourStrawberry1 = new javax.swing.JRadioButton();
        rbFlavourChocolate1 = new javax.swing.JRadioButton();
        btReset = new javax.swing.JButton();
        btCalculate = new javax.swing.JButton();
        btExit = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTax = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Radio Button");
        setMaximumSize(new java.awt.Dimension(100, 100));
        setMinimumSize(new java.awt.Dimension(100, 100));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 36)); // NOI18N
        jLabel1.setText("Arsenio's Ice Cream");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        cbSprinkles.setText("Sprinkles $ .25");
        cbSprinkles.setActionCommand("0.25");

        cbPeanuts.setText("Peanuts $ .50");
        cbPeanuts.setActionCommand(".5");

        cbFudge.setText("Fudge $ .75");
        cbFudge.setActionCommand(".75");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbSprinkles)
                    .addComponent(cbPeanuts)
                    .addComponent(cbFudge))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(cbSprinkles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbPeanuts)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbFudge)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnGrpFlavour.add(rbFlavourVanilla1);
        rbFlavourVanilla1.setSelected(true);
        rbFlavourVanilla1.setText("Vanilla $ 1.00");
        rbFlavourVanilla1.setActionCommand("1");

        btnGrpFlavour.add(rbFlavourStrawberry1);
        rbFlavourStrawberry1.setText("Strawberry $ 1.50");
        rbFlavourStrawberry1.setActionCommand("1.5");

        btnGrpFlavour.add(rbFlavourChocolate1);
        rbFlavourChocolate1.setText("Chocolate $ 2.00");
        rbFlavourChocolate1.setActionCommand("2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbFlavourVanilla1)
                    .addComponent(rbFlavourStrawberry1)
                    .addComponent(rbFlavourChocolate1))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(rbFlavourVanilla1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbFlavourStrawberry1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rbFlavourChocolate1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btReset.setText("Reset Form");
        btReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btResetActionPerformed(evt);
            }
        });

        btCalculate.setText("Calculate");
        btCalculate.setSelected(true);
        btCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCalculateActionPerformed(evt);
            }
        });

        btExit.setText("Exit");
        btExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExitActionPerformed(evt);
            }
        });

        jLabel2.setText("Subtotal");

        lblSubtotal.setForeground(new java.awt.Color(0, 102, 0));
        lblSubtotal.setEnabled(false);
        lblSubtotal.setMaximumSize(new java.awt.Dimension(100, 100));
        lblSubtotal.setMinimumSize(new java.awt.Dimension(100, 100));

        jLabel3.setText("Tax");

        lblTax.setForeground(new java.awt.Color(0, 102, 0));
        lblTax.setEnabled(false);
        lblTax.setMaximumSize(new java.awt.Dimension(100, 100));
        lblTax.setMinimumSize(new java.awt.Dimension(100, 100));

        jLabel4.setText("Total");

        lblTotal.setForeground(new java.awt.Color(0, 102, 0));
        lblTotal.setEnabled(false);
        lblTotal.setMaximumSize(new java.awt.Dimension(100, 100));
        lblTotal.setMinimumSize(new java.awt.Dimension(100, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(btCalculate)
                .addGap(18, 18, 18)
                .addComponent(btReset)
                .addGap(18, 18, 18)
                .addComponent(btExit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTax, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(502, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btCalculate, btExit, btReset});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lblSubtotal, lblTax, lblTotal});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btReset)
                    .addComponent(btExit)
                    .addComponent(jLabel3)
                    .addComponent(lblTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCalculate))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(82, 82, 82)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(250, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btCalculate, btExit, btReset});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {lblSubtotal, lblTax, lblTotal});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCalculateActionPerformed
        // TODO add your handling code here:

        //get the a single price from the radio button group btnGrpFlavours
        String flavour = btnGrpFlavour.getSelection().getActionCommand();
        //parse the value to double
        double flavPrice = Double.parseDouble(flavour);

        //get the sum of topping price
        double toppingPrice = 0;

        toppingPrice += (!getCheckboxValue(cbSprinkles).isEmpty()) ? Double.parseDouble(getCheckboxValue(cbSprinkles)) : 0;
        toppingPrice += (!getCheckboxValue(cbPeanuts).isEmpty()) ? Double.parseDouble(getCheckboxValue(cbPeanuts)) : 0;
        toppingPrice += (!getCheckboxValue(cbFudge).isEmpty()) ? Double.parseDouble(getCheckboxValue(cbFudge)) : 0;

        //Calculate subtotal, tax and Total, and show it on corresponding label
        double subtotal = flavPrice + toppingPrice;
        lblSubtotal.setText(String.format("%.2f", subtotal));

        double tax = subtotal * 0.15;
        lblTax.setText(String.format("%.2f", tax));

        double total = subtotal + tax;
        lblTotal.setText(String.format("%.2f", total));
    }//GEN-LAST:event_btCalculateActionPerformed

    private void btExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_btExitActionPerformed

    private void btResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btResetActionPerformed
        // TODO add your handling code here:

        //Dispose used Jframe and display a new one
        this.dispose();
        new Day006AesenioIceCream().setVisible(true);
        
    }//GEN-LAST:event_btResetActionPerformed

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
            java.util.logging.Logger.getLogger(Day006AesenioIceCream.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Day006AesenioIceCream.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Day006AesenioIceCream.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Day006AesenioIceCream.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Day006AesenioIceCream().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCalculate;
    private javax.swing.JButton btExit;
    private javax.swing.JButton btReset;
    private javax.swing.ButtonGroup btnGrpFlavour;
    private javax.swing.JCheckBox cbFudge;
    private javax.swing.JCheckBox cbPeanuts;
    private javax.swing.JCheckBox cbSprinkles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTax;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JRadioButton rbFlavourChocolate1;
    private javax.swing.JRadioButton rbFlavourStrawberry1;
    private javax.swing.JRadioButton rbFlavourVanilla1;
    // End of variables declaration//GEN-END:variables
}
