package br.edu.ifam.umlhelper.model;

import br.edu.ifam.umlhelper.model.EditorActions.ColorAction;
import br.edu.ifam.umlhelper.model.EditorActions.HistoryAction;
import br.edu.ifam.umlhelper.model.EditorActions.NewAction;
import br.edu.ifam.umlhelper.model.EditorActions.OpenAction;
import br.edu.ifam.umlhelper.model.EditorActions.PrintAction;
import br.edu.ifam.umlhelper.model.EditorActions.SaveAction;
import br.edu.ifam.umlhelper.view.CriaTelaAjuda;
import br.edu.ifam.umlhelper.view.TelaCriarDiagramaCasoDeUso;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.TransferHandler;


import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxGraphActions;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxResources;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxGraphView;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class EditorToolBar extends JToolBar
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8015443128436394471L;

	/**
	 * 
	 * @param frame
	 * @param orientation
	 */
	private boolean ignoreZoomChange = false;
        private TelaCriarDiagramaCasoDeUso tela;
        private MouseListener jButtonToolBarNovoListener;
        private MouseListener jButtonToolBarAbrirListener;
        private MouseListener jButtonToolBarSalvarListener;
        private MouseListener jButtonToolBarImprimirListener;
        private MouseListener jButtonToolBarApagarListener;
        private MouseListener jButtonToolBarDesfazerListener;
        private MouseListener jButtonToolBarFonteListener;
        private MouseListener jButtonToolBarTamanhoListener;
        private MouseListener jButtonToolBarCorListener;
        private MouseListener jButtonToolBarAmpliarListener;
        
	/**
	 * 
	 */

        
	public EditorToolBar(final Editor editor, int orientation, TelaCriarDiagramaCasoDeUso tela)
	{  
                
		super(orientation);
                this.tela = tela;
                initToolBarListener();
		setBorder(BorderFactory.createCompoundBorder(BorderFactory
				.createEmptyBorder(3, 3, 3, 3), getBorder()));
		setFloatable(false);
                
                
		add(editor.bind("New", new NewAction(),
				"/images/new.gif")).addMouseListener(jButtonToolBarNovoListener);
                addSeparator();
		add(editor.bind("Open", new OpenAction(),
				"/images/open.gif")).addMouseListener(jButtonToolBarAbrirListener);
                addSeparator();
		add(editor.bind("Save", new SaveAction(false),
				"/images/save.gif")).addMouseListener(jButtonToolBarSalvarListener);

		addSeparator();

		add(editor.bind("Print", new PrintAction(),
				"/images/print.gif")).addMouseListener(jButtonToolBarImprimirListener);

		addSeparator();
                add(editor.bind("Delete", mxGraphActions.getDeleteAction(),
				"/images/delete.gif")).addMouseListener(jButtonToolBarApagarListener);

		addSeparator();

		add(editor.bind("Undo", new HistoryAction(true),
				"/images/undo.gif")).addMouseListener(jButtonToolBarDesfazerListener);
                addSeparator();
/*
		add(editor.bind("Cut", TransferHandler.getCutAction(),
				"/images/cut.gif"));
		add(editor.bind("Copy", TransferHandler.getCopyAction(),
				"/images/copy.gif"));
		add(editor.bind("Paste", TransferHandler.getPasteAction(),
				"/images/paste.gif"));

		addSeparator();

		add(editor.bind("Delete", mxGraphActions.getDeleteAction(),
				"/images/delete.gif"));

		addSeparator();

		add(editor.bind("Undo", new HistoryAction(true),
				"/images/undo.gif"));
		add(editor.bind("Redo", new HistoryAction(false),
				"/images/redo.gif"));

		addSeparator();
                */

		// Gets the list of available fonts from the local graphics environment
		// and adds some frequently used fonts at the beginning of the list
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		List<String> fonts = new ArrayList<String>();
		fonts.addAll(Arrays.asList(new String[] { "Helvetica", "Verdana",
				"Times New Roman", "Garamond", "Courier New", "-" }));
		fonts.addAll(Arrays.asList(env.getAvailableFontFamilyNames()));

		final JComboBox fontCombo = new JComboBox(fonts.toArray());
		fontCombo.setEditable(true);
		fontCombo.setMinimumSize(new Dimension(120, 0));
		fontCombo.setPreferredSize(new Dimension(120, 0));
		fontCombo.setMaximumSize(new Dimension(120, 100));
		add(fontCombo).addMouseListener(jButtonToolBarFonteListener);
                addSeparator();
		fontCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				String font = fontCombo.getSelectedItem().toString();

				if (font != null && !font.equals("-"))
				{
					mxGraph graph = editor.getGraphComponent().getGraph();
					graph.setCellStyles(mxConstants.STYLE_FONTFAMILY, font);
				}
			}
		});

		final JComboBox sizeCombo = new JComboBox(new Object[] { "6pt", "8pt",
				"9pt", "10pt", "12pt", "14pt", "18pt", "24pt", "30pt", "36pt",
				"48pt", "60pt" });
		sizeCombo.setEditable(true);
		sizeCombo.setMinimumSize(new Dimension(65, 0));
		sizeCombo.setPreferredSize(new Dimension(65, 0));
		sizeCombo.setMaximumSize(new Dimension(65, 100));
		add(sizeCombo).addMouseListener(jButtonToolBarTamanhoListener);

		sizeCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				mxGraph graph = editor.getGraphComponent().getGraph();
				graph.setCellStyles(mxConstants.STYLE_FONTSIZE, sizeCombo
						.getSelectedItem().toString().replace("pt", ""));
			}
		});

		addSeparator();
                /*
		add(editor.bind("Bold", new FontStyleAction(true),
				"/images/bold.gif"));
		add(editor.bind("Italic", new FontStyleAction(false),
				"/images/italic.gif"));

		addSeparator();

		add(editor.bind("Left", new KeyValueAction(mxConstants.STYLE_ALIGN,
				mxConstants.ALIGN_LEFT),
				"/images/left.gif"));
		add(editor.bind("Center", new KeyValueAction(mxConstants.STYLE_ALIGN,
				mxConstants.ALIGN_CENTER),
				"/images/center.gif"));
		add(editor.bind("Right", new KeyValueAction(mxConstants.STYLE_ALIGN,
				mxConstants.ALIGN_RIGHT),
				"/images/right.gif"));

		addSeparator();

		add(editor.bind("Font", new ColorAction("Font",
				mxConstants.STYLE_FONTCOLOR),
				"/images/fontcolor.gif"));
                
		add(editor.bind("Stroke", new ColorAction("Stroke",
				mxConstants.STYLE_STROKECOLOR),
				"/images/linecolor.gif"));
              */  
		add(editor.bind("Fill", new ColorAction("Fill",
				mxConstants.STYLE_FILLCOLOR),
				"/images/fillcolor.gif")).addMouseListener(jButtonToolBarCorListener);

		addSeparator();
                
		final mxGraphView view = editor.getGraphComponent().getGraph()
				.getView();
		final JComboBox zoomCombo = new JComboBox(new Object[] { "400%",
				"200%", "150%", "100%", "75%", "50%", mxResources.get("page"),
				mxResources.get("width"), mxResources.get("actualSize") });
		zoomCombo.setEditable(true);
		zoomCombo.setMinimumSize(new Dimension(75, 0));
		zoomCombo.setPreferredSize(new Dimension(75, 0));
		zoomCombo.setMaximumSize(new Dimension(75, 100));
		zoomCombo.setMaximumRowCount(9);
		add(zoomCombo).addMouseListener(jButtonToolBarAmpliarListener);

		// Sets the zoom in the zoom combo the current value
		mxIEventListener scaleTracker = new mxIEventListener()
		{
			/**
			 * 
			 */
			public void invoke(Object sender, mxEventObject evt)
			{
				ignoreZoomChange = true;

				try
				{
					zoomCombo.setSelectedItem((int) Math.round(100 * view
							.getScale())
							+ "%");
				}
				finally
				{
					ignoreZoomChange = false;
				}
			}
		};

		// Installs the scale tracker to update the value in the combo box
		// if the zoom is changed from outside the combo box
		view.getGraph().getView().addListener(mxEvent.SCALE, scaleTracker);
		view.getGraph().getView().addListener(mxEvent.SCALE_AND_TRANSLATE,
				scaleTracker);

		// Invokes once to sync with the actual zoom value
		scaleTracker.invoke(null, null);

		zoomCombo.addActionListener(new ActionListener()
		{
			/**
			 * 
			 */
			public void actionPerformed(ActionEvent e)
			{
				mxGraphComponent graphComponent = editor.getGraphComponent();

				// Zoomcombo is changed when the scale is changed in the diagram
				// but the change is ignored here
				if (!ignoreZoomChange)
				{
					String zoom = zoomCombo.getSelectedItem().toString();

					if (zoom.equals(mxResources.get("page")))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_PAGE);
					}
					else if (zoom.equals(mxResources.get("width")))
					{
						graphComponent.setPageVisible(true);
						graphComponent
								.setZoomPolicy(mxGraphComponent.ZOOM_POLICY_WIDTH);
					}
					else if (zoom.equals(mxResources.get("actualSize")))
					{
						graphComponent.zoomActual();
					}
					else
					{
						try
						{
							zoom = zoom.replace("%", "");
							double scale = Math.min(16, Math.max(0.01,
									Double.parseDouble(zoom) / 100));
							graphComponent.zoomTo(scale, graphComponent
									.isCenterZoom());
						}
						catch (Exception ex)
						{
							JOptionPane.showMessageDialog(editor, ex
									.getMessage());
						}
					}
				}
			}
		});
	}
        private void initToolBarListener(){
            jButtonToolBarNovoListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Novo"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            
            jButtonToolBarAbrirListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Abrir"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarSalvarListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Salvar"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarImprimirListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Imprimir"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarApagarListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Apagar"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarDesfazerListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Desfazer"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarFonteListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Fonte"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarTamanhoListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Tamanho"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarCorListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Cor"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            jButtonToolBarAmpliarListener = new MouseListener() {
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
                    tela.atualizarPainelAcessivel(new CriaTelaAjuda().getLabelLibras("Ampliar"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    tela.limparPainelAcessivel();
                }
            };
            
        }
}
