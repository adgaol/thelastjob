package fmxl;




import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.Arrays;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class CadenaEntrada {
    mxGraphComponent graphComponent;
    mxGraph graph;
    Object parent;
    List<Object> elementosInsertados;//cada uno de los elementos de la cadena de entrada
    MiApp app;
    Object finalFichero;
    public CadenaEntrada(Configuracion lectConf) {
        
        elementosInsertados= new ArrayList<>();
         graph = new mxGraph(){
               
                
                 @Override
                public String getToolTipForCell(Object cell)
                                {

                                        if (model.isVertex(cell))
                                        {
                                             Map<String, Object> m=graph.getCellStyle(cell);
                                            Object opacidad=m.get("opacity");
                                            if(!opacidad.equals(0))
                                            return "Paso que procesa el elemento: "+graph.getLabel(cell);
                                        }
                                        //return super.getToolTipForCell(cell);
                                        return null;
                                        
                                }

                                };
     //Estilos
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 80);     
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        //style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadLeido().getRGB()));
        style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorLeido(),16)));
        //style.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena);
        style.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraCadena());
        //style.put(mxConstants.STYLE_ALIGN,"ALIGN_RIGHT");
       
        stylesheet.putCellStyle("LEIDO", style);
        
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 10);
        style2.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
       // style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadPendiente().getRGB()));
        style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorPend(),16)));
       // style2.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        style2.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraCadena());
        style2.put(mxConstants.STYLE_VERTICAL_ALIGN,"middle");
        style2.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION,"middle");
        stylesheet.putCellStyle("PENDIENTE", style2);
        
         Hashtable<String, Object> style10 = new Hashtable<String, Object>();
        style10.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style10.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
        style10.put(mxConstants.STYLE_FONTCOLOR, "#000000");  
        style10.put(mxConstants.STYLE_FONTSIZE, 30);
        style10.put(mxConstants.STYLE_ROUNDED,"1");
        style10.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style10.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style10.put(mxConstants.STYLE_SPACING_RIGHT,0);  
        style10.put(mxConstants.STYLE_FONTSTYLE,mxConstants.FONT_ITALIC); 
        style10.put(mxConstants.STYLE_FONTFAMILY,"times new roman"); 
        stylesheet.putCellStyle("TITULO", style10);
         Object parent = this.graph.getDefaultParent();
        Object modelo=this.graph.getModel();
        this.graphComponent = new mxGraphComponent(this.graph);  
        
        this.graphComponent.getViewport().setOpaque(true);
        this.graphComponent.getViewport().setBackground(Color.WHITE);
         
    }
    /**
     * initialize the entry string
     * @param app 
     * app
     */
    public CadenaEntrada(MiApp app) {
        this.app=app;
        elementosInsertados= new ArrayList<>();
         graph = new mxGraph(){
               
                
                 @Override
                public String getToolTipForCell(Object cell)
                                {

                                        if (model.isVertex(cell))
                                        {
                                             Map<String, Object> m=graph.getCellStyle(cell);
                                            Object opacidad=m.get("opacity");
                                            if(!opacidad.equals(0))
                                            return "Paso que procesa el elemento: "+graph.getLabel(cell);
                                        }
                                        //return super.getToolTipForCell(cell);
                                        return null;
                                        
                                }

                                };
     //Estilos
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 80);     
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadLeido().getRGB()));
        style.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        //style.put(mxConstants.STYLE_ALIGN,"ALIGN_RIGHT");
       
        stylesheet.putCellStyle("LEIDO", style);
        
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 10);
        style2.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadPendiente().getRGB()));
        style2.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        style2.put(mxConstants.STYLE_VERTICAL_ALIGN,"middle");
        style2.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION,"middle");
        stylesheet.putCellStyle("PENDIENTE", style2);
        
         Hashtable<String, Object> style10 = new Hashtable<String, Object>();
        style10.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style10.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
        style10.put(mxConstants.STYLE_FONTCOLOR, "#000000");  
        style10.put(mxConstants.STYLE_FONTSIZE, 30);
        style10.put(mxConstants.STYLE_ROUNDED,"1");
        style10.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style10.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style10.put(mxConstants.STYLE_SPACING_RIGHT,0);  
        style10.put(mxConstants.STYLE_FONTSTYLE,mxConstants.FONT_ITALIC); 
        style10.put(mxConstants.STYLE_FONTFAMILY,"times new roman"); 
        stylesheet.putCellStyle("TITULO", style10);
         Object parent = this.graph.getDefaultParent();
        Object modelo=this.graph.getModel();
        this.graphComponent = new mxGraphComponent(this.graph);  
        
        this.graphComponent.getViewport().setOpaque(true);
        this.graphComponent.getViewport().setBackground(Color.WHITE);
         
    }
    /**
     * build the string show in the interface
     * @param elementos 
     * elements of the string
     */
    public void construirCadena(List<String> elementos){
        
        String color="PENDIENTE";
            int pos=15; 
            int y;
            int t= 30;
           // Dimension d=app.getSize();
            y=100;
           graph.getModel().beginUpdate();
		try
		{              
                   for(String s: elementos){
                       
                       Object r = graph.insertVertex(parent, null, s,
		        pos, y, 40, 40,color);
                       elementosInsertados.add(r);
                        if(pos==0){
                            pos=pos+Grafo.desReglaInicial;
                            t=20;
                        }
                       else
                           pos=pos+Grafo.desRegla;  
                         
                   }
                  finalFichero=graph.insertVertex(parent, null, new Simbolo("EOF",true),
		   pos, y, 40, 40,color);
                  elementosInsertados.add(finalFichero);
		}
                
		finally
		{
		   graph.getModel().endUpdate();                   
		}
		
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
    }
        /**
     * update the string in the interface
     * @param s 
     * terminal to update
     */
    public void actualizar(String s,FicheroXML ejemplo,int contador){
        System.out.println(s);
        String[] leidoNoLeido =s.split("pend");
        String[] leido=leidoNoLeido[0].split(" ");
        List<Object> listaLeidos= new ArrayList<>();
        int i=0;
        /*
        todos los elementos leídos cambian de color
        */
        if(!leido[0].equals(""))
            for(i=0;i<leido.length;i++){           
                listaLeidos.add(this.elementosInsertados.get(i));

            }
         graph.setCellStyle("LEIDO", listaLeidos.toArray());
         
         List<Object> listaPendientes= new ArrayList<>();
         for(int j=i;j<this.elementosInsertados.size();j++){
            listaPendientes.add(this.elementosInsertados.get(j));
           
        }Object[] arrayAux= new Object[1];
         arrayAux[0]=this.finalFichero;
         graph.setCellStyle("PENDIENTE", listaPendientes.toArray());
         if(contador==ejemplo.getNumNodos()){             
            graph.setCellStyle("LEIDO", arrayAux);
         }
         else
             graph.setCellStyle("PENDIENTE", arrayAux);
        
    }
    /**
     * update the string in the interface
     * @param s 
     * terminal to update
     */
    public void actualizar(String s){
        System.out.println(s);
        String[] leidoNoLeido =s.split("pend");
        String[] leido=leidoNoLeido[0].split(" ");
        List<Object> listaLeidos= new ArrayList<>();
        int i=0;
        /*
        todos los elementos leídos cambian de color
        */
        if(!leido[0].equals(""))
            for(i=0;i<leido.length;i++){           
                listaLeidos.add(this.elementosInsertados.get(i));

            }
         graph.setCellStyle("LEIDO", listaLeidos.toArray());
         
         List<Object> listaPendientes= new ArrayList<>();
         for(int j=i;j<this.elementosInsertados.size();j++){
            listaPendientes.add(this.elementosInsertados.get(j));
           
        }Object[] arrayAux= new Object[1];
         arrayAux[0]=this.finalFichero;
         graph.setCellStyle("PENDIENTE", listaPendientes.toArray());
         if(app.getContador()==app.getFicheroXml().getNumNodos()){             
            graph.setCellStyle("LEIDO", arrayAux);
         }
         else
             graph.setCellStyle("PENDIENTE", arrayAux);
        
    }
  /**
   * activate the listener to change the step when click the string
   */  
     public void activarListener(){
           graphComponent.setToolTips(true);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
                                app.getMenu().getBarraMenu().requestFocusInWindow();
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				String leido;
                                int numeroPaso=0;
                                Character caracter;
				if (cell != null)
				{
                                    List<Informacion> listaPasos=app.getListaPasos();
                                    
                                    for(Informacion info:listaPasos){ 
                                         numeroPaso++;
                                        leido=info.getLeido();
                                        if(!leido.equals("")){
                                            caracter=leido.charAt(leido.length()-1);
                                            if(graph.getLabel(cell).equals(caracter.toString())){

                                                break;
                                            }
                                        }
                                    }
                                   System.out.print(cell);
                                  
                                  irPaso(app.getContador(),numeroPaso);
                                        
                                        
				}
                                
                                    
			}
                        /*
                        public void mouseMoved(MouseEvent e)
                                {
                                    System.out.println("Entrando.....");
                                };
                        
                       */ 
                    
		});
      }
      /**
   * activate the listener to change the step when click the string
   */  
     public void activarListener(List<Informacion> listaPasosI,int contador,FXMLDocumentController app){
           graphComponent.setToolTips(true);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
                                //app.getMenu().getBarraMenu().requestFocusInWindow();
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				String leido;
                                int numeroPaso=0;
                                Character caracter;
				if (cell != null){
                                    List<Informacion> listaPasos=listaPasosI;
                                    for(Informacion info:listaPasos){ 
                                         numeroPaso++;
                                        leido=info.getLeido();
                                        if(!leido.equals("")){
                                            caracter=leido.charAt(leido.length()-1);
                                            if(graph.getLabel(cell).equals(caracter.toString())){

                                                break;
                                            }
                                        }
                                    }
                                    System.out.print(cell);
                                  
                                  app.irPaso(app.contador,numeroPaso);
				}         
			}                    
		});
      }
    
/**
 * 
 * @return the graph component
 */
    public mxGraphComponent getGraphComponent() {
        return graphComponent;
    }
/**
 * 
 * @param graphComponent 
 * the graph component
 */
    public void setGraphComponent(mxGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
    }
/**
 * 
 * @return the tree in the interface
 */
    public mxGraph getGraph() {
        return graph;
    }
/**
 * 
 * @param graph 
 * the tree in the interface
 */
    public void setGraph(mxGraph graph) {
        this.graph = graph;
    }
/**
 * 
 * @return app
 */
    public MiApp getApp() {
        return app;
    }
/**
 * 
 * @param app 
 * app
 */
    public void setApp(MiApp app) {
        this.app = app;
    }
/**
 * Go to the step indicate
 * @param contador
 * step
 * @param id 
 * id of the step
 */
    public void irPaso(int contador,int id){
        
        if(contador<id){
            for(int i=contador;i< id;i++){
                app.construirArbol();
            }
        }else{
                   for(int i=contador;i>id;i--){
                        app.eliminar();
                    } 
                 }
        }
 /**
  * insert a title
  * @param tit 
  * title
  */   
     public void insertarTitulo(String tit){
          Object o;
          graph.getModel().beginUpdate(); 
          int posX=0;
          int ancho=50;
          int alto=30;
		try
		{              
                  o=graph.insertVertex(parent,null,tit,400,20,ancho,alto,"TITULO");
                  
		}
                
		finally
		{
                   graphComponent.setEnabled(false);
		   graph.getModel().endUpdate();                   
		}
                
          
      }
    
     
     /**
      * remove all string
      */
     public void eliminarCadena(){ 
         
         Object[] arrayCadena=elementosInsertados.toArray();
         eliminar(arrayCadena);
         elementosInsertados=new ArrayList<>();
     }
     /**
      * remove the string
      * @param s 
      * string
      */
       public void eliminar(Object[] s){
          
         
        graph.getModel().beginUpdate();
		try
		{                    
                   //Object[] aux= new Object[1];
                    //graph.removeSelectionCell(s);   
                    //graph.removeCells(aux);
                    //graph.cellsRemoved(aux);
                    //graph.removeCells(graph.getAllEdges(aux));
                    //graph.refresh();
                    //Object elim=graph.insertVertex(parent, null, "prueba",
		     //  x+20, y+20, 50, 30);
                    //aux[0]=s;
                    graph.removeCells(s);
                  
		}
                
		finally
		{
		                  
		}
                 graph.getModel().endUpdate();   
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
              
      }
       /**
        * rebuild the string
        */
      public void reconstruirCadena(){
           List<String> auxLista;
           String[] cadenaPend=app.getCadena().get(0).split("pend");//eliminamos pend de la cadena
           auxLista=Arrays.asList(cadenaPend[1].split(" "));
           construirCadena(auxLista);
      }
    /**
        * rebuild the string
        */
      public void reconstruirCadena(List<String> cadena){
           List<String> auxLista;
           String[] cadenaPend=cadena.get(0).split("pend");//eliminamos pend de la cadena
           auxLista=Arrays.asList(cadenaPend[1].split(" "));
           construirCadena(auxLista);
      }
}
