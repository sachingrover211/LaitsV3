/**
 * LAITS Project Arizona State University (c) 2013, Arizona Board of Regents for
 * and on behalf of Arizona State University. This file is part of LAITS.
 *
 * LAITS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * LAITS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with LAITS. If not, see <http://www.gnu.org/licenses/>.
 */
package edu.asu.laits.gui.menus;

import edu.asu.laits.editor.ApplicationContext;
import edu.asu.laits.editor.GraphEditorPane;
import edu.asu.laits.editor.listeners.InsertModeChangeListener;
import edu.asu.laits.gui.GraphViewPanel;
import edu.asu.laits.gui.MainWindow;
import edu.asu.laits.gui.nodeeditor.GraphValuesDialog;
import edu.asu.laits.gui.nodeeditor.NodeEditorView;
import edu.asu.laits.model.Vertex;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import org.apache.log4j.Logger;
import org.jgraph.graph.DefaultGraphCell;

/**
 *
 * @author ramayantiwari
 */
public class VertexSelectionMenu extends JPopupMenu {

    GraphEditorPane graphPane;
    private JMenuItem deleteItem = null;
    private JMenuItem editItem = null;
    private JMenuItem graphDataItem = null;
    private JMenuItem nodeCharts = null;
    private static Logger logs = Logger.getLogger("DevLogs");
    private static Logger activityLogs = Logger.getLogger("ActivityLogs");

    /**
     * This method initializes
     *
     */
    public VertexSelectionMenu(GraphEditorPane graphPane) {
        super();
        this.graphPane = graphPane;
        initialize();
    }

    /**
     * This method initializes this
     *
     */
    private void initialize() {
        add(getEditItem());
        if(!ApplicationContext.isCoachedMode()){
           add(getDeleteItem());    
        }
        add(getGraphDataItem());        
    }

    public void setGraphDataItem() {
        DefaultGraphCell gc = (DefaultGraphCell) graphPane.getSelectionCell();
        Vertex currentVertex = (Vertex) gc.getUserObject();

        if (!currentVertex.getGraphsStatus().equals(Vertex.GraphsStatus.UNDEFINED)) {
            graphDataItem.setEnabled(true);
        } else {
            graphDataItem.setEnabled(false);
        }
    }

    /**
     * This method initializes deleteItem
     */
    private JMenuItem getDeleteItem() {
        if (deleteItem == null) {
            deleteItem = new JMenuItem();
            deleteItem.setText("Delete");
            deleteItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    graphPane.removeSelected();
                }
            });
        }
        return deleteItem;
    }

    private boolean isGraphViewEnable(ArrayList<Vertex> vertices) {
        boolean isEnable = true;

        for (Vertex v : vertices) {
            if (v.getGraphsStatus().equals(Vertex.GraphsStatus.UNDEFINED)) {
                isEnable = false;
                break;
            }
        }

        return isEnable;
    }

    /**
     * This method initializes deleteItem
     */
    private JMenuItem getEditItem() {
        if (editItem == null) {
            editItem = new JMenuItem();
            editItem.setText("Edit");
            editItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DefaultGraphCell gc = (DefaultGraphCell) graphPane.getSelectionCell();
                    Vertex v = (Vertex) gc.getUserObject();
                    new NodeEditorView(v);
                }
            });
        }
        return editItem;
    }

    /**
     * This method initializes deleteItem
     */
    private JMenuItem getGraphDataItem() {
        if (graphDataItem == null) {
            graphDataItem = new JMenuItem();
            graphDataItem.setText("Graph Values");
            graphDataItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    DefaultGraphCell gc = (DefaultGraphCell) graphPane.getSelectionCell();
                    Vertex currentVertex = (Vertex) gc.getUserObject();
                    new GraphValuesDialog(MainWindow.getInstance(), true, currentVertex);
                }
            });
        }
        return graphDataItem;
    }
}
