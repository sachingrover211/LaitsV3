package edu.asu.laits.gui;


import edu.asu.laits.editor.ApplicationContext;
import edu.asu.laits.model.Vertex;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.*;
import org.apache.log4j.Logger;


// This is the class that controls the cell rendering so that each node shows its slide image next to it
/**
 *
 * @author Andrew Williamson
 */
public class InstructionPanel extends javax.swing.JPanel implements java.beans.Customizer, ActionListener {
    
    private int currentSlideIndex = 0;
   
    private final int TOTAL_SLIDES = 75;
    // creates the images that go under the slide
    
    public Image partInit = getImageFromURL("/resources/icons/INIT.jpg");
    public Image partBasics = getImageFromURL("/resources/icons/BASICS.jpg");
    public Image partConstruct = getImageFromURL("/resources/icons/CONSTRUCT.jpg");
    public Image partFix = getImageFromURL("/resources/icons/FIX.jpg");
    public Image partCreate = getImageFromURL("/resources/icons/CREATE.jpg");
    public Image partEnd = getImageFromURL("/resources/icons/END.jpg");
    
    DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("Slide List"); 
    
    DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot); 
    
    private static final int BASIC_SLIDE_END = 13;
    private static final int CONSTRUCT_SLIDE_END = 56;
    private static final int FIX_SLIDE_END = 65;
    private static final int CREATE_SLIDE_END = 74;
    
    private static Logger logs = Logger.getLogger(InstructionPanel.class);
    
    // ArrayList to Hold all the slides
    private List<Slide> allSlides;
    private MainWindow mainWindow;

    /**
     * Constructor
     */
    public InstructionPanel(MainWindow mw) {
        super();
        this.mainWindow = mw;
        createSlides();
        initComponents();
        initTree();
     
        setVisible(true);
    }

    private Image getImageFromURL(String url){
        if(url == null || url.length() == 0)
            return null;
        
        return java.awt.Toolkit.getDefaultToolkit().createImage(
                InstructionPanel.class.getResource(url));
    }
    /**
     * Called from: Constructor
     *
     * Creates the tree
     */
    public void initTree() {
        // Ugly code - needs refactoring
        DefaultMutableTreeNode basicRoot = new DefaultMutableTreeNode("Basic:");
        DefaultMutableTreeNode constructRoot = new DefaultMutableTreeNode("Construct:");
        DefaultMutableTreeNode fixRoot = new DefaultMutableTreeNode("Fix:");
        DefaultMutableTreeNode designRoot = new DefaultMutableTreeNode("Design:");

        allSlides.get(2).setjTreeName("System");
        basicRoot.add(new DefaultMutableTreeNode(allSlides.get(2)));

        allSlides.get(5).setjTreeName("First example");
        basicRoot.add(new DefaultMutableTreeNode(allSlides.get(5)));

        allSlides.get(8).setjTreeName("Notation");
        basicRoot.add(new DefaultMutableTreeNode(allSlides.get(8)));

        allSlides.get(13).setjTreeName("Types Nodes");
        basicRoot.add(new DefaultMutableTreeNode(allSlides.get(13)));

        allSlides.get(18).setjTreeName("Fixed value");
        constructRoot.add(new DefaultMutableTreeNode(allSlides.get(18)));

        allSlides.get(38).setjTreeName("Function");
        constructRoot.add(new DefaultMutableTreeNode(allSlides.get(38)));

        allSlides.get(48).setjTreeName("Accumulator");
        constructRoot.add(new DefaultMutableTreeNode(allSlides.get(48)));

        allSlides.get(58).setjTreeName("What To Fix");
        fixRoot.add(new DefaultMutableTreeNode(allSlides.get(58)));

        allSlides.get(61).setjTreeName("Fixing Notation");
        fixRoot.add(new DefaultMutableTreeNode(allSlides.get(61)));

        allSlides.get(66).setjTreeName("Design");
        designRoot.add(new DefaultMutableTreeNode(allSlides.get(66)));

        treeRoot.add(basicRoot);
        treeRoot.add(constructRoot);
        treeRoot.add(fixRoot);
        treeRoot.add(designRoot);

        treeController.setModel(new javax.swing.tree.DefaultTreeModel(treeRoot));
        treeController.setCellRenderer(new SlideNodeCellRenderer());
        treeController.putClientProperty("JTree.lineStyle", "Angled");

        initTreeSelectionListener(); // initilize the selection listener
    }

    public void initTreeSelectionListener() {
        treeController.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); // only allow one selection

        // The controls for the mouse listener
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = treeController.getRowForLocation(e.getX(), e.getY()); 
                if(e.getClickCount() == 2 && selRow != -1){
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) 
                            treeController.getLastSelectedPathComponent();
                    
                    if(node == null) return;
                    
                    Slide selectedSlide = (Slide) node.getUserObject();
                    currentSlideIndex = selectedSlide.getSlideNumber();
                    System.out.println(""+currentSlideIndex);
                    repaint();
                }                
            }
        };
        treeController.addMouseListener(ml); 
    }

    public void makeNodeSelected() 
    {
        Enumeration enumeration = treeRoot.breadthFirstEnumeration(); 
        while (enumeration.hasMoreElements()) 
          // while it has more elements
        { 
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement(); 
          Object ow = node.getUserObject(); 
          if (ow instanceof Slide) 
          {
            Slide slideNode = (Slide) ow; // cast the object
            if (slideNode.equals(allSlides.get(currentSlideIndex))) 
              
            { 
              TreeNode[] nodes = treeModel.getPathToRoot(node);
              TreePath path = new TreePath(nodes); 
              
              treeController.setSelectionPath(path); 
            }
          }
        }
    }

  
    public void createSlides() {
        allSlides = new ArrayList<Slide> ();
        
        for(int i=0; i<TOTAL_SLIDES; i++){
            Slide slide = new Slide(i);
            allSlides.add(slide);
        }                
    }
    
    private void setProgressImage(){
        if(currentSlideIndex == 0)
            progressLabel.setIcon(new ImageIcon(partInit));
        
        else if(currentSlideIndex <= BASIC_SLIDE_END)
            progressLabel.setIcon(new ImageIcon(partBasics));
        
        else if(currentSlideIndex <= CONSTRUCT_SLIDE_END)
            progressLabel.setIcon(new ImageIcon(partConstruct));
        
        else if(currentSlideIndex <= FIX_SLIDE_END)
            progressLabel.setIcon(new ImageIcon(partFix));
        
        else if(currentSlideIndex <= CREATE_SLIDE_END)
            progressLabel.setIcon(new ImageIcon(partCreate));
        
        else 
            progressLabel.setIcon(new ImageIcon(partEnd));
    }
    
    private boolean isSlideAllowed(int slideNumber){
        if(allSlides.get(currentSlideIndex).getHasBeenViewed())
            return true;
        
        else if(slideNumber >= 21 && slideNumber <= 38)
            return handleProblem1(slideNumber);
        
        else if(slideNumber >= 39 && slideNumber <= 47)
            return handleProblem2(slideNumber);
        
        else if(slideNumber >= 48 && slideNumber <= 56)
            return handleProblem3(slideNumber);
        
        else if(slideNumber >= 57 && slideNumber <= 66)
            return handleProblem4(slideNumber);
        
        return true;
    }
    
    private boolean handleProblem1(int slideNumber){      
        Set<Vertex> vertices = mainWindow.getGraphEditorPane().getModelGraph().vertexSet();
        
        if(slideNumber == 21 && vertices.size() < 1){
            JOptionPane.showMessageDialog(this, "Please create a node before proceeding");
            return false;
        }
        Vertex v = vertices.iterator().next();
        
        if(slideNumber == 25 && (v.getDescriptionStatus().equals(Vertex.DescriptionStatus.INCORRECT)
                || (v.getDescriptionStatus().equals(Vertex.DescriptionStatus.UNDEFINED)))){
            JOptionPane.showMessageDialog(this, "Please complete the Description tab before proceeding");
            return false;
        }
       
        if(slideNumber == 28 && (v.getPlanStatus().equals(Vertex.PlanStatus.INCORRECT)
                || (v.getPlanStatus().equals(Vertex.PlanStatus.UNDEFINED)))){
            JOptionPane.showMessageDialog(this, "Please complete the Plan tab before proceeding");
            return false;
        }
        
        if(slideNumber == 31 && (v.getInputsStatus().equals(Vertex.InputsStatus.INCORRECT)
                || (v.getInputsStatus().equals(Vertex.InputsStatus.UNDEFINED)))){
            JOptionPane.showMessageDialog(this, "Please complete the Inputs tab before proceeding");
            return false;
        }
        
        if(slideNumber == 33 && (v.getCalculationsStatus().equals(Vertex.CalculationsStatus.INCORRECT)
                || (v.getCalculationsStatus().equals(Vertex.CalculationsStatus.UNDEFINED)))){
            JOptionPane.showMessageDialog(this, "Please complete the Calculations tab before proceeding");
            return false;
        }
        
        if(slideNumber == 35 && (v.getGraphsStatus().equals(Vertex.GraphsStatus.INCORRECT)
                || (v.getGraphsStatus().equals(Vertex.GraphsStatus.UNDEFINED)))){
            JOptionPane.showMessageDialog(this, "Please Run the Model");
            return false;
        }
        
        if(slideNumber == 38 && ApplicationContext.getCurrentTaskID().equals("105")){
            JOptionPane.showMessageDialog(this, "Please Press Done button to move to the next problem");
            return false;
        }
        
        return true;
    }
    
    private boolean handleProblem2(int slideNumber){
        return true;
    }
    
    private boolean handleProblem3(int slideNumber){
        return true;
    }
    
    private boolean handleProblem4(int slideNumber){
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) 
    {    
        super.paintComponent(g);
        setProgressImage();    
        slideLabel.setIcon(new ImageIcon(allSlides.get(currentSlideIndex).getImage()));
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the FormEditor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        progressView = new javax.swing.JPanel();
        progressLabel = new javax.swing.JLabel();
        slideView = new javax.swing.JPanel();
        slideLabel = new javax.swing.JLabel();
        treeView = new javax.swing.JScrollPane();
        treeController = new javax.swing.JTree();
        buttonPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        allBackButton = new javax.swing.JButton();
        forwardButton = new javax.swing.JButton();
        allForwardButton = new javax.swing.JButton();

        setToolTipText("");

        contentPanel.setBackground(new java.awt.Color(255, 255, 255));
        contentPanel.setForeground(new java.awt.Color(0, 51, 51));
        contentPanel.setOpaque(false);

        progressView.setBackground(new java.awt.Color(255, 255, 255));
        progressView.setForeground(new java.awt.Color(255, 255, 255));

        progressLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        progressView.add(progressLabel);

        slideView.setBackground(new java.awt.Color(255, 255, 255));
        slideView.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        slideView.setPreferredSize(new java.awt.Dimension(305, 5));

        slideLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        slideLabel.setMinimumSize(new java.awt.Dimension(599, 6));
        slideView.add(slideLabel);

        treeView.setMinimumSize(new java.awt.Dimension(400, 400));

        treeController.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        treeController.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        treeController.setToolTipText("Double Click To Select!");
        treeController.setMaximumSize(new java.awt.Dimension(200, 200));
        treeController.setMinimumSize(new java.awt.Dimension(200, 200));
        treeController.setPreferredSize(new java.awt.Dimension(200, 200));
        treeController.setRowHeight(25);
        treeView.setViewportView(treeController);

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/navigate_left_icon.png"))); // NOI18N
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        allBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/navigate_beginning_icon.png"))); // NOI18N
        allBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allBackButtonActionPerformed(evt);
            }
        });

        forwardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/navigate_right_icon.png"))); // NOI18N
        forwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });

        allForwardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icons/navigate_end_icon.png"))); // NOI18N
        allForwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allForwardButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout buttonPanelLayout = new org.jdesktop.layout.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(allBackButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(backButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(forwardButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(allForwardButton)
                .add(2, 2, 2))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, forwardButton)
                    .add(buttonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(backButton)
                        .add(allBackButton)
                        .add(allForwardButton)))
                .add(33, 33, 33))
        );

        org.jdesktop.layout.GroupLayout contentPanelLayout = new org.jdesktop.layout.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPanelLayout.createSequentialGroup()
                .add(contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(contentPanelLayout.createSequentialGroup()
                        .add(238, 238, 238)
                        .add(buttonPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(contentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(slideView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, contentPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(progressView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(treeView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 255, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPanelLayout.createSequentialGroup()
                .add(buttonPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(contentPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(treeView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 544, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(contentPanelLayout.createSequentialGroup()
                        .add(slideView, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 456, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(progressView, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(contentPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

  private void allBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allBackButtonActionPerformed
      currentSlideIndex = 0;
      makeNodeSelected();
      repaint();
  }//GEN-LAST:event_allBackButtonActionPerformed

  private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
      if(currentSlideIndex == 0)
          return;
      
      currentSlideIndex -= 1;
      makeNodeSelected();
      repaint();      
  }//GEN-LAST:event_backButtonActionPerformed

  private void allForwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allForwardButtonActionPerformed
      if(!isSlideAllowed(TOTAL_SLIDES - 1)){
          return;
      }
      
      currentSlideIndex = TOTAL_SLIDES - 1;
      allSlides.get(currentSlideIndex).setHasBeenViewed(true);
      makeNodeSelected();
      repaint();
  }//GEN-LAST:event_allForwardButtonActionPerformed

  private void forwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forwardButtonActionPerformed
      if(currentSlideIndex == TOTAL_SLIDES - 1)
          return;
      
      if(!isSlideAllowed(currentSlideIndex+1)){
          return;
      }
      
      currentSlideIndex += 1;
      makeNodeSelected();
      repaint();

 }//GEN-LAST:event_forwardButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allBackButton;
    private javax.swing.JButton allForwardButton;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton forwardButton;
    private javax.swing.JLabel progressLabel;
    private javax.swing.JPanel progressView;
    private javax.swing.JLabel slideLabel;
    private javax.swing.JPanel slideView;
    private javax.swing.JTree treeController;
    private javax.swing.JScrollPane treeView;
    // End of variables declaration//GEN-END:variables

    /**
     * Auto generated
     *
     * @param o
     */
    public void setObject(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Auto generated
     *
     * @param ae
     */
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

class SlideNodeCellRenderer extends DefaultTreeCellRenderer {

    private String nodeText = "";
    Font nodeFont = null;
    FontMetrics fontMetrics = null;
    private boolean isSelected = false;

    SlideNodeCellRenderer() {
        nodeFont = new Font("Arial", Font.PLAIN, 14);
        fontMetrics = this.getFontMetrics(nodeFont);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        Object ow = ((DefaultMutableTreeNode) value).getUserObject(); // get the object out of the node
        if (ow instanceof Slide) {
            Slide slideNode = (Slide) ow; // cast it to student
            nodeText = slideNode.getjTreeName();
            int width = fontMetrics.stringWidth(nodeText);

            this.setText(nodeText); // the label text equals the slideNode's name
            isSelected = false;

            this.setMinimumSize(new Dimension(width + 20, 25));
            this.setPreferredSize(new Dimension(width + 20, 25));
            this.setSize(new Dimension(width + 20, 25));

            if (leaf) {
                this.setIcon(leafIcon);
                this.setDisabledIcon(leafIcon);
            }

            if (!slideNode.getHasBeenViewed()) {
                this.setEnabled(false);
                this.setToolTipText("View this slide before selecting");
            } else {
                this.setEnabled(true);
                this.setToolTipText("Double click to select!");
            }
            if (selected) {
                isSelected = true;
            }

        } else {
            this.setText("" + value);
        }
        return this;
    }
}

// This class represents the slide
class Slide {

    private int number; 
    private String path; 
    private Image slideImage; 
    
    private boolean hasBeenViewed = false; 
    private int solvingProblem = -1;
    private String jTreeName; 
   

    Slide(int slideNumber) {
        this.number = slideNumber; 
        setPath(slideNumber); 
    }

    public void setPath(int slideNumber) {
        if (slideNumber == 0) {
            this.path = "/resources/Slides/SlideLaits.jpg"; // LAITS Slide path
        } else if (slideNumber <= 9) {
            this.path = "/resources/Slides/Slide0" + slideNumber + ".jpg"; 
        } else {
            this.path = "/resources/Slides/Slide" + slideNumber + ".jpg"; 
        }
        
        slideImage = java.awt.Toolkit.getDefaultToolkit().createImage(InstructionPanel.class.getResource(path)); 
    }

    public void setHasBeenViewed(boolean bool) {
        hasBeenViewed = bool;
    }

    public boolean getHasBeenViewed() {
        return hasBeenViewed;
    }

    public Image getImage() {
        return slideImage;
    }

    public int getSlideNumber() {
        return number;
    }

    public String getPath() {
        return path;
    }

    public String getjTreeName() {
        return jTreeName;
    }

    public void setjTreeName(String jTreeName) {
        this.jTreeName = jTreeName;
    }

  
    public int getsolvingProblem() {
        return this.solvingProblem;
    }

    public void setsolvingProblem(int solvingProblem) {
        this.solvingProblem = solvingProblem;
    }

}
