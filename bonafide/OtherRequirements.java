/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OtherRequirements.java
 *
 * Created on Jul 26, 2013, 12:09:50 PM
 */
package bonafide;

/**
 *
 * @author ishant0
 */
public class OtherRequirements extends javax.swing.JFrame {

    /** Creates new form OtherRequirements */
    public OtherRequirements() {
        initComponents();
        need1_label.setText("uiui"); 
    }

    public OtherRequirements(SecondClass sc, AllRequiredClass arc) {
        initComponents();
        need1_label.setText(sc.getOther_requirement1());
        this.sc = sc;
        this.arc = arc;
    }
    
    public OtherRequirements(SecondClass sc) {
        initComponents();
        this.sc = sc;
        need1_label.setText(sc.getOther_requirement1()); 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        need1_label = new javax.swing.JLabel();
        need1_tf = new javax.swing.JTextField();
        create_button = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        need1_label.setText("need1");

        create_button.setText("Create");
        create_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(need1_label, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(need1_tf, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(create_button)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(need1_tf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(need1_label, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                .addComponent(create_button)
                .addGap(43, 43, 43))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void create_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_buttonActionPerformed
    by_replace1 = need1_tf.getText();
    if(arc == null){
         arc = new AllRequiredClass(by_replace1);
    }
 else{
    arc.setBy_replace1(by_replace1);
 }
    new CreateCertificate().create(sc, arc);
}//GEN-LAST:event_create_buttonActionPerformed


/*private void getToReplace1(){
        Connect c = new Connect();
        java.sql.Connection con = null;
        java.sql.PreparedStatement ps = null;
        java.sql.ResultSet rs = null;
        try{
        con = c.getConnection();        
        ps = con.prepareStatement("select other_requirement1 from requirements where typeName = '"+sc.getCert_type()+"'");        
        System.out.println("gstCertTYpe = "+sc.getCert_type()); 
        rs = ps.executeQuery();
        rs.next();
        to_replace1 = rs.getString("other_requirement1");
        System.out.println("to_replace inside getToReplace "+to_replace1);
        c.closeConnection(con, ps, rs); 
   }catch(Exception e){javax.swing.JOptionPane.showMessageDialog(null, "Problem in getting replacement one!"+e);}
   finally{c.closeConnection(con, ps, rs); }
    }
 * 
 */


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
            java.util.logging.Logger.getLogger(OtherRequirements.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OtherRequirements.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OtherRequirements.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OtherRequirements.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new OtherRequirements().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton create_button;
    private javax.swing.JLabel need1_label;
    private javax.swing.JTextField need1_tf;
    // End of variables declaration//GEN-END:variables

    SecondClass sc;
    AllRequiredClass arc;
    private String to_replace1;
    private String by_replace1;
}