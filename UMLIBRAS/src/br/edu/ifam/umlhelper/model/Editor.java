/* 
 * 
 * Copyright (c) 2001-2014, JGraph Ltd
 * All rights reserved.
 */

package br.edu.ifam.umlhelper.model;
import br.edu.ifam.umlhelper.view.CriaTelaAjuda;
import br.edu.ifam.umlhelper.view.TelaCriarDiagramaCasoDeUso;
import com.mxgraph.io.mxCodec;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.model.mxICell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.handler.mxGraphHandler;
import com.mxgraph.swing.handler.mxKeyboardHandler;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.mxGraphOutline;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxUndoManager;
import com.mxgraph.util.mxUndoableEdit;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.w3c.dom.Document;

/**
 *
 * @author Antonio
 */
public class Editor extends JPanel{
    private mxGraph graph;
    private mxGraphComponent graphComponent;
    private mxUndoManager undoManager;
    private mxGraphHandler graphHandler;
    private mxGraphOutline graphOutline;
    private mxKeyboardHandler keyboardHandler;
    private File currentFile;
    private boolean modified = false;
    private static TelaCriarDiagramaCasoDeUso tela;
    
    
    static
    {
            try
            {
                    mxResources.add("br/edu/ifam/umlhelper/resources/editor");
            }
            catch (Exception e)
            {
                    // ignore
            }
    }
    
    public TelaCriarDiagramaCasoDeUso getTela(){
        return this.tela;
    }
    
        
    public Editor(TelaCriarDiagramaCasoDeUso tela){
        this.tela = tela;
        setLayout(new BorderLayout());
        graphComponent = new CustomGraphComponent(new CustomGraph());
        graph = graphComponent.getGraph();
        graphHandler = new mxGraphHandler(graphComponent);
        undoManager = new mxUndoManager();
        graphComponent.setBorder(null);
        graphOutline = new mxGraphOutline(graphComponent);
        graphComponent.setToolTips(true);
        graphComponent.setBackground(Color.WHITE);
        this.add(graphComponent);
        initGraphListeners();
        graph.getModel().addListener(mxEvent.CHANGE, changeTracker);

        // Adds the command history to the model and view
        graph.getModel().addListener(mxEvent.UNDO, undoHandler);
        graph.getView().addListener(mxEvent.UNDO, undoHandler);
        
        mxEventSource.mxIEventListener undoHandler = new mxEventSource.mxIEventListener()
        {
                public void invoke(Object source, mxEventObject evt)
                {
                        List<mxUndoableEdit.mxUndoableChange> changes = ((mxUndoableEdit) evt
                                        .getProperty("edit")).getChanges();
                        graph.setSelectionCells(graph
                                        .getSelectionCellsForChanges(changes));
                }
        };
        undoManager.addListener(mxEvent.UNDO, undoHandler);
        undoManager.addListener(mxEvent.REDO, undoHandler);
        keyboardHandler = new EditorKeyboardHandler(graphComponent);
       
       
    }
    
    protected mxEventSource.mxIEventListener undoHandler = new mxEventSource.mxIEventListener()
    {
            public void invoke(Object source, mxEventObject evt)
            {
                    undoManager.undoableEditHappened((mxUndoableEdit) evt
                                    .getProperty("edit"));
            }
    };
    
    protected mxEventSource.mxIEventListener changeTracker = new mxEventSource.mxIEventListener()
    {
            public void invoke(Object source, mxEventObject evt)
            {
                    setModified(true);
            }
    };
    
    public mxGraph getGraph(){
        return graph;
    }
    
    public mxGraphComponent getGraphComponent(){
        return graphComponent;
    }
    
    public Action bind(String name, final Action action)
    {
            return bind(name, action, null);
    }

    /**
     * 
     * @param name
     * @param action
     * @return a new Action bound to the specified string name and icon
     */
    @SuppressWarnings("serial")
    public Action bind(String name, final Action action, String iconUrl)
    {
            AbstractAction newAction = new AbstractAction(name, (iconUrl != null) ? new ImageIcon(
                            getClass().getResource(iconUrl)) : null)
            {
                    public void actionPerformed(ActionEvent e)
                    {
                            action.actionPerformed(new ActionEvent(getGraphComponent(), e
                                            .getID(), e.getActionCommand()));
                            
                    }
                    
            };
            
            newAction.putValue(Action.SHORT_DESCRIPTION, name);
            
            return newAction;
    }
    
    private void initGraphListeners(){
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
        {

                /**
                 * 
                 */
                public void mousePressed(MouseEvent e)
                {
                        // Handles context menu on the Mac where the trigger is on mousepressed
                        mouseReleased(e);
                }

                /**
                 * 
                 */
                public void mouseReleased(MouseEvent e)
                {
                        if (e.isPopupTrigger())
                        {
                                showGraphPopupMenu(e);
                        }
                }

        });
        
        /*
        graphComponent.getGraphControl().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                mxCell cell = (mxCell) graphComponent.getCellAt(e.getPoint().x, e.getPoint().y);
                if(cell != null)
                {
                    System.out.println("é cell");
                    System.out.println(cell.getStyle());
                    String palavra = "";
                    switch(cell.getStyle())
                    {
                        case "shape=actor":
                            palavra = "Ator";
                            break;
                    }
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(palavra));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mxCell cell = (mxCell) graphComponent.getCellAt(e.getPoint().x, e.getPoint().y);
                if(cell != null)
                {
                    System.out.println("Saí");
                    tela.limparPainelAcessivel();
                }
                
            }
        });*/
       
    }
    
    public void exit()
    {
            JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);

            if (frame != null)
            {
                    System.exit(0);
            }
    }
    
    public File getCurrentFile()
    {
            return currentFile;
    }
    
    public void setCurrentFile(File file)
    {
            File oldValue = currentFile;
            currentFile = file;

            firePropertyChange("currentFile", oldValue, file);

            if (oldValue != file)
            {
                    updateTitle();
            }
    }
        

    public mxUndoManager getUndoManager()
    {
            return undoManager;
    }
        
    public void setModified(boolean modified)
    {
            boolean oldValue = this.modified;
            this.modified = modified;

            firePropertyChange("modified", oldValue, modified);

            if (oldValue != modified)
            {
                    updateTitle();
            }
    }
    
    public void updateTitle()
    {
            JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);

            if (frame != null)
            {
                    String title = (currentFile != null) ? currentFile
                                    .getAbsolutePath() : mxResources.get("newDiagram");

                    if (modified)
                    {
                            title += "*";
                    }
                    tela.updateTabTitle(title);
                    //frame.setTitle(title + " - " + "newDiagram");
            }
    }
    
        public Paletas inserirPaleta(String title)
    {
            final Paletas paleta = new Paletas(this.tela);
            JTabbedPane painelUML = new JTabbedPane();
            final JScrollPane scrollPane = new JScrollPane(paleta);
            scrollPane
                            .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane
                            .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            painelUML.add(title, scrollPane);

            // Updates the widths of the palettes if the container size changes
            painelUML.addComponentListener(new ComponentAdapter()
            {
                    /**
                     * 
                     */
                    public void componentResized(ComponentEvent e)
                    {
                            int w = scrollPane.getWidth()
                                            - scrollPane.getVerticalScrollBar().getWidth();
                            paleta.setPreferredWidth(w);
                    }

            });

            return paleta;
    }

    /**
     * 
     * @return whether or not the current graph has been modified
     */
    public boolean isModified()
    {
            return modified;
    }
    
    protected void showGraphPopupMenu(MouseEvent e)
    {
            Point pt = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(),
                            graphComponent);
            EditorPopupMenu menu = new EditorPopupMenu(Editor.this);
            menu.show(graphComponent, pt.x, pt.y);

            e.consume();
    }

    
    
    public static class CustomGraphComponent extends mxGraphComponent
    {
            public CustomGraphComponent(mxGraph graph)
            {
                    super(graph);

                    // Sets switches typically used in an editor
                    setPageVisible(true);
                    setGridVisible(true);
                    setToolTips(true);
                    getConnectionHandler().setCreateTarget(true);

                    // Loads the defalt stylesheet from an external file
                    mxCodec codec = new mxCodec();
                    Document doc = mxUtils.loadDocument(Editor.class.getResource(
                                    "/br/edu/ifam/umlhelper/resources/default-style.xml")
                                    .toString());
                    codec.decode(doc.getDocumentElement(), graph.getStylesheet());

                    // Sets the background to white
                    getViewport().setOpaque(true);
                    getViewport().setBackground(Color.WHITE);
            }

            /**
             * Overrides drop behaviour to set the cell style if the target
             * is not a valid drop target and the cells are of the same
             * type (eg. both vertices or both edges). 
             */
            public Object[] importCells(Object[] cells, double dx, double dy,
                            Object target, Point location)
            {
                    if (target == null && cells.length == 1 && location != null)
                    {
                            target = getCellAt(location.x, location.y);

                            if (target instanceof mxICell && cells[0] instanceof mxICell)
                            {
                                    mxICell targetCell = (mxICell) target;
                                    mxICell dropCell = (mxICell) cells[0];

                                    if (targetCell.isVertex() == dropCell.isVertex()
                                                    || targetCell.isEdge() == dropCell.isEdge())
                                    {
                                            mxIGraphModel model = graph.getModel();
                                            model.setStyle(target, model.getStyle(cells[0]));
                                            graph.setSelectionCell(target);

                                            return null;
                                    }
                            }
                    }

                    return super.importCells(cells, dx, dy, target, location);
            }

    }  
        
	public static class CustomGraph extends mxGraph
	{
		/**
		 * Holds the edge to be used as a template for inserting new edges.
		 */
		protected Object edgeTemplate;

		/**
		 * Custom graph that defines the alternate edge style to be used when
		 * the middle control point of edges is double clicked (flipped).
		 */
		public CustomGraph()
		{
			setAlternateEdgeStyle("edgeStyle=mxEdgeStyle.ElbowConnector;elbow=vertical");
		}

		/**
		 * Sets the edge template to be used to inserting edges.
		 */
		public void setEdgeTemplate(Object template)
		{
			edgeTemplate = template;
		}

		/**
		 * Prints out some useful information about the cell in the tooltip.
		 */
		public String getToolTipForCell(Object cell)
		{
                    mxCell c = (mxCell) cell;
                   String value = c.getStyle();
                   String saida = "";
                   if(value.contains("shape=actor"))
                   {
                       saida = "Ator";
                   
                   }
                   else if(value.contains("endArrow=none"))
                   {
                       saida = "Agregação";
                   }
                   else if(value.contains("ellipse"))
                   {
                       saida = "Caso de Uso";
                   
                   }
                   else if(value.contains("align=left")){
                       saida = "Subsistema";
                   
                   }
                   else if(value.contains("straight")&& value.contains("fontSize"))
                   {
                       String decisao = c.getValue().toString();
                       if(decisao.contains("include")){
                           saida = "Incluir";
                       }
                       else{
                           saida = "Estender";
                       }
                   }
                   else if(value.contains("label"))
                   {
                       saida = "Video";
                   }
                   else{
                       saida = "Geneneralização";
                   }
                       
                   
                   if(value.contains("actor")){
                        saida = "Ator";
                   }
                    //tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras(saida));
                    return saida;
                    /*
			String tip = "<html>";
			mxGeometry geo = getModel().getGeometry(cell);
			mxCellState state = getView().getState(cell);

			if (getModel().isEdge(cell))
			{
				tip += "points={";

				if (geo != null)
				{
					List<mxPoint> points = geo.getPoints();

					if (points != null)
					{
						Iterator<mxPoint> it = points.iterator();

						while (it.hasNext())
						{
							mxPoint point = it.next();
							tip += "[x=" + numberFormat.format(point.getX())
									+ ",y=" + numberFormat.format(point.getY())
									+ "],";
						}

						tip = tip.substring(0, tip.length() - 1);
					}
				}

				tip += "}<br>";
				tip += "absPoints={";

				if (state != null)
				{

					for (int i = 0; i < state.getAbsolutePointCount(); i++)
					{
						mxPoint point = state.getAbsolutePoint(i);
						tip += "[x=" + numberFormat.format(point.getX())
								+ ",y=" + numberFormat.format(point.getY())
								+ "],";
					}

					tip = tip.substring(0, tip.length() - 1);
				}

				tip += "}";
			}
			else
			{
				tip += "geo=[";

				if (geo != null)
				{
					tip += "x=" + numberFormat.format(geo.getX()) + ",y="
							+ numberFormat.format(geo.getY()) + ",width="
							+ numberFormat.format(geo.getWidth()) + ",height="
							+ numberFormat.format(geo.getHeight());
				}

				tip += "]<br>";
				tip += "state=[";

				if (state != null)
				{
					tip += "x=" + numberFormat.format(state.getX()) + ",y="
							+ numberFormat.format(state.getY()) + ",width="
							+ numberFormat.format(state.getWidth())
							+ ",height="
							+ numberFormat.format(state.getHeight());
				}

				tip += "]";
			}

			mxPoint trans = getView().getTranslate();

			tip += "<br>scale=" + numberFormat.format(getView().getScale())
					+ ", translate=[x=" + numberFormat.format(trans.getX())
					+ ",y=" + numberFormat.format(trans.getY()) + "]";
			tip += "</html>";

			return tip;
                    */
		}

		/**
		 * Overrides the method to use the currently selected edge template for
		 * new edges.
		 * 
		 * @param graph
		 * @param parent
		 * @param id
		 * @param value
		 * @param source
		 * @param target
		 * @param style
		 * @return
		 */
		public Object createEdge(Object parent, String id, Object value,
				Object source, Object target, String style)
		{
			if (edgeTemplate != null)
			{
				mxCell edge = (mxCell) cloneCells(new Object[] { edgeTemplate })[0];
				edge.setId(id);

				return edge;
			}

			return super.createEdge(parent, id, value, source, target, style);
		}

	}        
   
    
}
