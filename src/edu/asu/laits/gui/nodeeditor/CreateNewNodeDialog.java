/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.laits.gui.nodeeditor;

import edu.asu.laits.editor.ApplicationContext;
import edu.asu.laits.editor.GraphEditorPane;
import edu.asu.laits.model.TaskSolution;
import edu.asu.laits.model.Vertex;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author ramayantiwari
 */
public class CreateNewNodeDialog extends javax.swing.JDialog {
    
    private NewNodeDescPanel dPanel;
    private NodeEditorView ne;
    private Vertex currentVertex;
    private static Logger logs = Logger.getLogger("DevLogs");
    private static Logger activityLogs = Logger.getLogger("ActivityLogs");
    
    public CreateNewNodeDialog(JDialog parent, Vertex v) {
        super(parent, true);
        
        initComponents();
        ne = (NodeEditorView) parent;
        currentVertex = v;
        
        initPanel();
        prepareDisplay();
    }
    
    private void initPanel() {
        dPanel = new NewNodeDescPanel(this, currentVertex);
        descriptionPanel.setLayout(new java.awt.GridLayout(1, 1));
        descriptionPanel.add(dPanel);
        
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                close();
            }
        });
        
        
    }
    
    private void prepareDisplay() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double height = screenSize.getHeight();
        int yPositionNodeEditor = (int) (height - getPreferredSize().height);
        
        setBounds((int) currentVertex.getXPosition() + 100,
                  yPositionNodeEditor / 2,
                  getPreferredSize().width, getPreferredSize().height);
        pack();
        
        editorMsgLabel.setVisible(false);
        setVisible(true);
        setResizable(false);
    }
    
    public GraphEditorPane getGraphPane() {
        return ne.getGraphPane();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        
        descriptionPanel = new javax.swing.JPanel();
        editorMsgLabel = new javax.swing.JLabel();
        checkButton = new javax.swing.JButton();
        giveUpButton = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        descriptionPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        descriptionPanel.setFocusable(false);
        descriptionPanel.setPreferredSize(new java.awt.Dimension(506, 615));
        descriptionPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        
        editorMsgLabel.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        editorMsgLabel.setForeground(new java.awt.Color(255, 0, 0));
        editorMsgLabel.setText("jLabel1");
        
        checkButton.setText("Check");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });
        
        giveUpButton.setText("Demo");
        giveUpButton.setActionCommand("Give Up");
        giveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giveUpButtonActionPerformed(evt);
            }
        });
        
        buttonCancel.setText("Close");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });
        
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                                  layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                  .add(layout.createSequentialGroup()
                                       .addContainerGap()
                                       .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                                 .add(0, 0, Short.MAX_VALUE)
                                                 .add(descriptionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 601, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                                            .add(layout.createSequentialGroup()
                                                 .add(editorMsgLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 601, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                 .add(15, 15, 15))
                                            .add(layout.createSequentialGroup()
                                                 .add(9, 9, 9)
                                                 .add(checkButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                 .add(18, 18, 18)
                                                 .add(giveUpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                                 .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                 .add(buttonCancel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 78, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                                       .addContainerGap())
                                  );
        layout.setVerticalGroup(
                                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(layout.createSequentialGroup()
                                     .add(19, 19, 19)
                                     .add(descriptionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 506, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                     .add(editorMsgLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 21, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                     .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                     .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                          .add(checkButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                          .add(giveUpButton, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                          .add(buttonCancel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                );
        
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
        // Action for Check Button
        logs.debug("Handling Check Action");
        TaskSolution correctSolution = ApplicationContext.getCorrectSolution();
        if(ApplicationContext.isCoachedMode()){
            
            checkDescriptionPanelCoached(correctSolution);
        }else {
            checkDescriptionPanel(correctSolution);
        }
        
    }//GEN-LAST:event_checkButtonActionPerformed
    
    private void giveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giveUpButtonActionPerformed
        // Action for Giveup Button
        activityLogs.debug("Giveup button pressed for Description Panel");
        dPanel.giveUpDescriptionPanel();
        dPanel.processDescriptionPanel();
        currentVertex.setDescriptionStatus(Vertex.DescriptionStatus.GAVEUP);
        setTitle(currentVertex.getName());
        //graphPane.getMainFrame().getMainMenu().getModelMenu().addDeleteNodeMenu();
        validate();
        repaint();
        currentVertex.setDescriptionStatus(Vertex.DescriptionStatus.GAVEUP);
        dPanel.setEditableTree(false);
        this.checkButton.setEnabled(false);
        this.giveUpButton.setEnabled(false);
        
    }//GEN-LAST:event_giveUpButtonActionPerformed
    
    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        // Process Cancel Action for all the Tabs
        close();
    }//GEN-LAST:event_buttonCancelActionPerformed
    
    private void checkDescriptionPanel(TaskSolution correctSolution) {
        // Save Description Panel Information in the Vertex Object
        if (!dPanel.processDescriptionPanel()) {
            return;
        }
        
        if (correctSolution.checkNodeName(dPanel.getNodeName())) {
            currentVertex.setDescriptionStatus(Vertex.DescriptionStatus.CORRECT);
            setEditorMessage("", false);
            dPanel.setTextFieldBackground(Color.GREEN);
            checkButton.setEnabled(false);
            giveUpButton.setEnabled(false);
            activityLogs.debug("User entered correct description");
            dPanel.setEditableTree(false);
            
        } else {
            currentVertex.setDescriptionStatus(Vertex.DescriptionStatus.INCORRECT);
            dPanel.setTextFieldBackground(Color.RED);
            setEditorMessage("That quantity is not used in the correct model. Please select another description.", true);
            activityLogs.debug("User entered incorrect description");
        }
        
        setTitle(currentVertex.getName());
        validate();
        repaint();
    }
    
    private void checkDescriptionPanelCoached(TaskSolution correctSolution) {
        // Save Description Panel Information in the Vertex Object
        if (!dPanel.processDescriptionPanel()) {
            return;
        }
        
        int solutionCheck = correctSolution.checkNodeNameOrdered(dPanel.getNodeName());
        if (solutionCheck == 1) {
            currentVertex.setDescriptionStatus(Vertex.DescriptionStatus.CORRECT);
            //graphPane.getMainFrame().getMainMenu().getModelMenu().addDeleteNodeMenu();
            setEditorMessage("", false);
            dPanel.setTextFieldBackground(Color.GREEN);
            checkButton.setEnabled(false);
            giveUpButton.setEnabled(false);
            activityLogs.debug("User entered correct description");
            dPanel.setEditableTree(false);
            //ApplicationContext.nextCurrentOrder();
            //ApplicationContext.removeNextNodes(currentVertex.getName());
            ApplicationContext.setNextNodes(currentVertex.getName());
            giveUpButton.setEnabled(false);
            
            
        } else if(solutionCheck == 2){
            dPanel.setTextFieldBackground(Color.CYAN);
            setEditorMessage("Quantity is used in model, but is not ready to be defined. Please try another description.", true);
            activityLogs.debug("User entered description out of order");
        } else {
            currentVertex.setDescriptionStatus(Vertex.DescriptionStatus.INCORRECT);
            dPanel.setTextFieldBackground(Color.RED);
            setEditorMessage("That quantity is not used in the correct model. Please select another description.", true);
            activityLogs.debug("User entered incorrect description");
            
        }
    }
    
    public JPanel getDescriptionPanel() {
        return descriptionPanel;
    }
    
    private void close() {
        System.out.println("closing");
        if (currentVertex.getName().equals("")) {
            ne.getGraphPane().removeSelected();
        }
        else {
            ne.getCalculationsPanel().initPanel();
            // ne.getGraphPane().setSelectionCell(ne.getCurrentVertex());
            ne.refreshInputs();
        }
        
        ne.getCalculationsPanel().setCreateButtonEnabled();
        this.dispose();
        ne.addHelpBalloon(currentVertex.getName(), "newNodeClosed", "INPUTS");
        
    }
    
    public void setEditorMessage(String msg, boolean err) {
        editorMsgLabel.setText(msg);
        if (err) {
            editorMsgLabel.setForeground(Color.RED);
        } else {
            editorMsgLabel.setForeground(Color.BLUE);
        }
        editorMsgLabel.setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton checkButton;
    private javax.swing.JPanel descriptionPanel;
    private javax.swing.JLabel editorMsgLabel;
    private javax.swing.JButton giveUpButton;
    // End of variables declaration//GEN-END:variables
}
