package fmxl;


import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxCellState;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class Grafo {

        final int multiplicador; /*
    colocar la gramática
     */ //multiplicador para separar los elementos de una regla al
    //multiplicador para separar los elementos de una regla al
        int espacioPila=80;//espacio entre elementos pila
        final int yInicial=65;
        static final int desReglaInicial=65;
        static final int desRegla=50;
        int extraAlturaRect=10;
        /*constantes        
        */
        static final int anchoNodoDefecto=80;
        static final int altoNodoDefecto=30;
        static final int anchoPilaDefecto=40;
        static final int altoPilaDefecto=altoNodoDefecto;
        static final int espacioPilaDefecto=80;//espacio entre elementos pila
        static final int sizeLetraDefecto=10;
        /*
        tamaño nodo arboles
        */
        int anchoNodo=80;
        int altoNodo=30;
        int anchoPila=40;
        int altoPila=altoNodo;
        int altoSimbRegla=30;
        int anchoSimbRegla=40;
    
        MiApp app;
        mxGraph graph;
        Object parent;
        Object modelo;
        mxGraphComponent graphComponent;
        Map<String,List<Object>> rectangAccionSem= new HashMap<>(); //empareja las acciones semanticas insertadas en la gramtica con su correspondiente rectangulo
        Map<Object,Regla> rectangRegla = new HashMap<>();
        int x,y;
        Object[] objetoToolTip = new Object[1]; //Array de objetos en los que se ha pasado el cursor 
        Object marcado=null;// sirve para marcar las regla del traductor a la cual hace referencia el rectángulo del árbol
        Map<Object,String> accionAtributos= new HashMap<>();//valor de los atributos de las acciones
        Map<Object,List<Integer>> rectPos=new HashMap<>();//empareja el rectangulo con la posicion
        //Mapa para la pila que guarda la posición de sus elementos
        Map<Object,Integer> pilaPosicion= new HashMap<>();
        //lista de booleans que nos dice si esa regla se ha desplazado o no
        List<Integer> listaDesplazados;
        List<Object> miGramatica = new ArrayList<>(); //lista de la gramática
        List<Object> listaAccionesInsertadas= new ArrayList<>();
        private Map<Object,List<Object>> rectangPila= new HashMap<>();
    public Grafo(MiApp app) {
        
        multiplicador=11;
        //Ponemos el desplazamiento de todas las reglas a falso
        listaDesplazados=new ArrayList<>();
        for(int u=0; u<app.getFicheroXml().getListaGramatica().size();u++){
            listaDesplazados.add(0);
        }
        /*
        if(app.getSizeLetraTraductor()<10)
            this.multiplicador=10;
        else
            this.multiplicador = 10*(app.getSizeLetraTraductor()/10);
        */
        
        graph = new mxGraph(){
                @Override
                public String getToolTipForCell(Object cell)
                                {
                                        

                                        if (model.isVertex(cell))
                                        {
                                            Grafo gramatica=null;//comprobar marcado, si accion en gramatica app null no realizamos esta accion
                                            if(app!=null){
                                                gramatica=app.getGramatica(); 
                                                if(gramatica!=null){
                                                 if(gramatica.getMarcado()!=null){
                                                     gramatica.eliminar(gramatica.getMarcado());
                                                     gramatica.setMarcado(null);
                                                 }                                            
                                                 }else{
                                                     if(marcado!=null){
                                                         eliminar(marcado);
                                                         setMarcado(null);
                                                     }
                                                 }
                                            }
                                            System.out.println("para el tooltip");
                                             Map<String, Object> m=graph.getCellStyle(cell);
                                            Object opacidad=m.get("opacity");
                                            if(objetoToolTip[0]!=null)
                                                graph.setCellStyle("GAZUL", objetoToolTip);
                                            if(opacidad.equals(100) ){
                                                 String valor= app.getElemValor().get(cell);
                                                 return valor;
                                            }else if(opacidad.equals(0)){
                                                String atrib=accionAtributos.get(cell);
                                                return atrib;
                                            }else if(opacidad.equals(20)){
                                                return "Elemento en la pila";
                                            }
                                            else if(opacidad.equals(10)){
                                                Object[] array = new Object[1];
                                                array[0]=cell;
                                                objetoToolTip[0]=cell;                              
                                                
                                                 graph.setCellStyle("RECTTOOLTIP", array);
                                                /*
                                                 Para que el usuario sepa en que regla se insertan las acciones semánticas.
                                                 
                                                 */
                                                verRegla(graph.getLabel(cell));
                                                
                                                 
                                                 String[] cadAux=graph.getLabel(cell).split("//"); // para eliminar elementos no necesarios como  la id, mostramos solo la regla
                                                 return cadAux[0];
                                                
                                                 
                                            }

                                        }

                                        //return super.getToolTipForCell(cell);
                                        return null;
                                }
                
               
                                };
    
    this.app=app;
    String colorRectang="F6FF33";
    //Estilos
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraNoTerminales().getRGB()));
        style.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        //style.put(mxConstants.STYLE_FILLCOLOR,"#82EB20");
        style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(app.getcNoTerminales().getRGB()));
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylesheet.putCellStyle("VERDE", style);
         
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 100);
        style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraTerminales().getRGB()));
        style2.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        stylesheet.putCellStyle("ROJO", style2);
        
        
        
        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
        style3.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style3.put(mxConstants.STYLE_OPACITY, 0);
        style3.put(mxConstants.STYLE_FONTCOLOR,  Integer.toHexString(app.getLetraNoTerminales().getRGB()));
        style3.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetraTraductor());
        style3.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcNoTerminales().getRGB()));
        style3.put(mxConstants.STYLE_ROUNDED,"1");
        style3.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style3.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER"); 
        
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("GVERDE", style3);
        
        
        
        Hashtable<String, Object> style4 = new Hashtable<String, Object>();
        style4.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style4.put(mxConstants.STYLE_OPACITY, 0);
        style4.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraTerminales().getRGB()));
        style4.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetraTraductor());
        style4.put(mxConstants.STYLE_ROUNDED,"1");
        style4.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style4.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        //style4.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER"); 
        
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("GROJO", style4);
        
        Hashtable<String, Object> stylePilaN = new Hashtable<String, Object>();
        stylePilaN.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        stylePilaN.put(mxConstants.STYLE_OPACITY, 20);
        stylePilaN.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        stylePilaN.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraNoTerminales().getRGB()));
        stylePilaN.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        stylePilaN.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcNoTerminales().getRGB()));
        stylePilaN.put(mxConstants.STYLE_ROUNDED,"1");
        stylePilaN.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("VERDEPILA", stylePilaN);
        
        Hashtable<String, Object> stylePila = new Hashtable<String, Object>();
        stylePila.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        stylePila.put(mxConstants.STYLE_OPACITY, 20);
        stylePila.put(mxConstants.STYLE_TEXT_OPACITY, 40);
        stylePila.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraTerminales().getRGB()));
        stylePila.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        stylePila.put(mxConstants.STYLE_ROUNDED,"1");
        stylePila.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylePila.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("ROJOPILA", stylePila);
        
        Hashtable<String, Object> style5 = new Hashtable<String, Object>();
        style5.put(mxConstants.STYLE_FONTSIZE,0);
        style5.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style5.put(mxConstants.STYLE_OPACITY, 10);
        style5.put(mxConstants.STYLE_FONTCOLOR, "#0000FF");
        style5.put(mxConstants.STYLE_FILLCOLOR,colorRectang);
        style5.put(mxConstants.STYLE_ROUNDED,"1");
        //style5.put(mxConstants.STYLE_GRADIENTCOLOR,"#0000FF");
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("GAZUL", style5);
        
        
        Hashtable<String, Object> style6 = new Hashtable<String, Object>();
        style6.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style6.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
        style6.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorAccSem().getRGB()));  
        style6.put(mxConstants.STYLE_FONTSIZE, app.getSizeAcciones());
        style6.put(mxConstants.STYLE_ROUNDED,"1");
        style6.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style6.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style6.put(mxConstants.STYLE_SPACING_RIGHT,0);
        style6.put(mxConstants.STYLE_ALIGN,"ALIGN_LEFT");       
        style6.put(mxConstants.STYLE_FONTFAMILY,app.getTipoLetra()); 
         
        stylesheet.putCellStyle("ACCIONES", style6);
        
        Hashtable<String, Object> style7 = new Hashtable<String, Object>();
        style7.put(mxConstants.STYLE_FONTSIZE,0);
        style7.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style7.put(mxConstants.STYLE_OPACITY, 10);
        style7.put(mxConstants.STYLE_FONTCOLOR, "#0000FF");
        style7.put(mxConstants.STYLE_FILLCOLOR,colorRectang);
        style7.put(mxConstants.STYLE_ROUNDED,"1");
        //style7.put(mxConstants.STYLE_GRADIENTCOLOR,"#0000FF");
        style7.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style7.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style7.put(mxConstants.STYLE_STROKE_OPACITY, 100);
        //style7.put(mxConstants.STYLE_PERIMETER,"#000000");
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("RECTTOOLTIP", style7);
        
         Hashtable<String, Object> style8 = new Hashtable<String, Object>();
        style8.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style8.put(mxConstants.STYLE_OPACITY, 0); 
        style8.put(mxConstants.STYLE_FONTCOLOR, "#000000");  
        style8.put(mxConstants.STYLE_FONTSIZE, 12);
        style8.put(mxConstants.STYLE_ROUNDED,"1");
        style8.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style8.put(mxConstants.STYLE_FILLCOLOR,"#FFFFFF");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style8.put(mxConstants.STYLE_SPACING_RIGHT,0);         
        stylesheet.putCellStyle("ACCIONESINTERM", style8);
        
         Hashtable<String, Object> style9 = new Hashtable<String, Object>();
        style9.put(mxConstants.STYLE_FONTSIZE,0);
        style9.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style9.put(mxConstants.STYLE_OPACITY, 10);
        style9.put(mxConstants.STYLE_FONTCOLOR, "#0000FF");
        style9.put(mxConstants.STYLE_FILLCOLOR,colorRectang);
        style9.put(mxConstants.STYLE_ROUNDED,"1");
        //style7.put(mxConstants.STYLE_GRADIENTCOLOR,"#0000FF");
        style9.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style9.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style9.put(mxConstants.STYLE_STROKE_OPACITY, 100);     
        stylesheet.putCellStyle("SENALIZAR", style9);
        
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
        
        
        Hashtable<String, Object> style11 = new Hashtable<String, Object>();
        style11.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style11.put(mxConstants.STYLE_OPACITY, 80);     
        style11.put(mxConstants.STYLE_ROUNDED,"1");
        style11.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style11.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style11.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadLeido().getRGB()));
        style11.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        //style.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
       
        stylesheet.putCellStyle("LEIDO", style11);
        
        Hashtable<String, Object> style12 = new Hashtable<String, Object>();
        style12.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style12.put(mxConstants.STYLE_OPACITY, 10);
        style12.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        style12.put(mxConstants.STYLE_ROUNDED,"1");
        style12.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style12.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style12.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadPendiente().getRGB()));
        style12.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        // style2.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
        stylesheet.putCellStyle("PENDIENTE", style12);
        Object parent = graph.getDefaultParent();
        Object modelo=graph.getModel();
        this.graphComponent = new mxGraphComponent(graph);  
        x=300;
        y=75;
        
        this.graphComponent.getViewport().setOpaque(true);
        this.graphComponent.getViewport().setBackground(Color.WHITE);
        
    }    
    public Grafo(Configuracion lectConf,FicheroXML ejemplo,Grafo gramatica,Map<Object,String> elemValor,List<List<Object>> listaReglas,int sepRegla) {
        
        multiplicador=11;
        //Ponemos el desplazamiento de todas las reglas a falso
        listaDesplazados=new ArrayList<>();
        for(int u=0; u<ejemplo.getListaGramatica().size();u++){
            listaDesplazados.add(0);
        } 
        graph = new mxGraph(){
                @Override
                public String getToolTipForCell(Object cell){
                    if (model.isVertex(cell)) {
                        //comprobar marcado, si accion en gramatica app null no realizamos esta accion
                        if(gramatica!=null){
                         if(gramatica.getMarcado()!=null){
                             gramatica.eliminar(gramatica.getMarcado());
                             gramatica.setMarcado(null);
                         }                                            
                         }else{
                             if(marcado!=null){
                                 eliminar(marcado);
                                 setMarcado(null);
                             }
                         }
                        
                        System.out.println("para el tooltip");
                        Map<String, Object> m=graph.getCellStyle(cell);
                        Object opacidad=m.get("opacity");
                        if(objetoToolTip[0]!=null)
                            graph.setCellStyle("GAZUL", objetoToolTip);
                        if(opacidad.equals(100) ){
                             String valor= elemValor.get(cell);
                             return valor;
                        }else if(opacidad.equals(0)){
                            String atrib=accionAtributos.get(cell);
                            return atrib;
                        }else if(opacidad.equals(20)){
                            return "Elemento en la pila";
                        }
                        else if(opacidad.equals(10)){
                            Object[] array = new Object[1];
                            array[0]=cell;
                            objetoToolTip[0]=cell;                              
                            graph.setCellStyle("RECTTOOLTIP", array);
                            // Para que el usuario sepa en que regla se insertan las acciones semánticas.
                            verRegla(graph.getLabel(cell),gramatica,listaReglas,sepRegla,lectConf.getLetraArbol());
                            String[] cadAux=graph.getLabel(cell).split("//"); // para eliminar elementos no necesarios como  la id, mostramos solo la regla
                            return cadAux[0];
                        }
                    }
                    return null;
                } 
            };
    
    //this.app=app;
    String colorRectang="F6FF33";
    //Estilos
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 100);
       // style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraNoTerminales().getRGB()));
        style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        //style.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        style.put(mxConstants.STYLE_FONTSIZE,lectConf.getLetraArbol() );
        //style.put(mxConstants.STYLE_FILLCOLOR,"#82EB20");
        //style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(app.getcNoTerminales().getRGB()));
        style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorNoTerminal(),16)));
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylesheet.putCellStyle("VERDE", style);
         
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 100);
       // style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraTerminales().getRGB()));
         style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraTerminal(),16)));
        //style2.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        style2.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraArbol());
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        stylesheet.putCellStyle("ROJO", style2);
        
        
        
        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
        style3.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style3.put(mxConstants.STYLE_OPACITY, 0);
       // style3.put(mxConstants.STYLE_FONTCOLOR,  Integer.toHexString(app.getLetraNoTerminales().getRGB()));
        style3.put(mxConstants.STYLE_FONTCOLOR,  Integer.toHexString(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        //style3.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetraTraductor());
        style3.put(mxConstants.STYLE_FONTSIZE,lectConf.getLetraTraductor());
// style3.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcNoTerminales().getRGB()));
        style3.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorNoTerminal(),16)));
        style3.put(mxConstants.STYLE_ROUNDED,"1");
        style3.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style3.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER"); 
        
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("GVERDE", style3);
        
        
        
        Hashtable<String, Object> style4 = new Hashtable<String, Object>();
        style4.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style4.put(mxConstants.STYLE_OPACITY, 0);
        //style4.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraTerminales().getRGB()));
        style4.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraTerminal(),16)));
        //style4.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetraTraductor());
        style4.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraArbol());
        style4.put(mxConstants.STYLE_ROUNDED,"1");
        style4.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style4.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style4.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        //style4.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER"); 
        
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("GROJO", style4);
        
        Hashtable<String, Object> stylePilaN = new Hashtable<String, Object>();
        stylePilaN.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        stylePilaN.put(mxConstants.STYLE_OPACITY, 20);
        stylePilaN.put(mxConstants.STYLE_TEXT_OPACITY, 20);
       // stylePilaN.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraNoTerminales().getRGB()));
       stylePilaN.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        //stylePilaN.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        stylePilaN.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraArbol());
        //stylePilaN.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcNoTerminales().getRGB()));
        stylePilaN.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorNoTerminal(),16)));
        stylePilaN.put(mxConstants.STYLE_ROUNDED,"1");
        stylePilaN.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("VERDEPILA", stylePilaN);
        
        Hashtable<String, Object> stylePila = new Hashtable<String, Object>();
        stylePila.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        stylePila.put(mxConstants.STYLE_OPACITY, 20);
        stylePila.put(mxConstants.STYLE_TEXT_OPACITY, 40);
//        stylePila.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getLetraTerminales().getRGB()));
        stylePila.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraTerminal(),16)));
//        stylePila.put(mxConstants.STYLE_FONTSIZE, app.getSizeLetra());
        stylePila.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraArbol());
        stylePila.put(mxConstants.STYLE_ROUNDED,"1");
        stylePila.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
//        stylePila.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        stylePila.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("ROJOPILA", stylePila);
        
        Hashtable<String, Object> style5 = new Hashtable<String, Object>();
        style5.put(mxConstants.STYLE_FONTSIZE,0);
        style5.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style5.put(mxConstants.STYLE_OPACITY, 10);
        style5.put(mxConstants.STYLE_FONTCOLOR, "#0000FF");
        style5.put(mxConstants.STYLE_FILLCOLOR,colorRectang);
        style5.put(mxConstants.STYLE_ROUNDED,"1");
        //style5.put(mxConstants.STYLE_GRADIENTCOLOR,"#0000FF");
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("GAZUL", style5);
        
        
        Hashtable<String, Object> style6 = new Hashtable<String, Object>();
        style6.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style6.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
//        style6.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorAccSem().getRGB()));  
        style6.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorAccSem(),16)));  
//        style6.put(mxConstants.STYLE_FONTSIZE, app.getSizeAcciones());
         style6.put(mxConstants.STYLE_FONTSIZE, lectConf.getSizeAcciones());
        style6.put(mxConstants.STYLE_ROUNDED,"1");
        style6.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style6.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style6.put(mxConstants.STYLE_SPACING_RIGHT,0);
        style6.put(mxConstants.STYLE_ALIGN,"ALIGN_LEFT");       
//        style6.put(mxConstants.STYLE_FONTFAMILY,app.getTipoLetra()); 
        style6.put(mxConstants.STYLE_FONTFAMILY,lectConf.getTipoLetra()); 
         
        stylesheet.putCellStyle("ACCIONES", style6);
        
        Hashtable<String, Object> style7 = new Hashtable<String, Object>();
        style7.put(mxConstants.STYLE_FONTSIZE,0);
        style7.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style7.put(mxConstants.STYLE_OPACITY, 10);
        style7.put(mxConstants.STYLE_FONTCOLOR, "#0000FF");
        style7.put(mxConstants.STYLE_FILLCOLOR,colorRectang);
        style7.put(mxConstants.STYLE_ROUNDED,"1");
        //style7.put(mxConstants.STYLE_GRADIENTCOLOR,"#0000FF");
        style7.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style7.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style7.put(mxConstants.STYLE_STROKE_OPACITY, 100);
        //style7.put(mxConstants.STYLE_PERIMETER,"#000000");
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("RECTTOOLTIP", style7);
        
         Hashtable<String, Object> style8 = new Hashtable<String, Object>();
        style8.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style8.put(mxConstants.STYLE_OPACITY, 0); 
        style8.put(mxConstants.STYLE_FONTCOLOR, "#000000");  
        style8.put(mxConstants.STYLE_FONTSIZE, 12);
        style8.put(mxConstants.STYLE_ROUNDED,"1");
        style8.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style8.put(mxConstants.STYLE_FILLCOLOR,"#FFFFFF");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style8.put(mxConstants.STYLE_SPACING_RIGHT,0);         
        stylesheet.putCellStyle("ACCIONESINTERM", style8);
        
         Hashtable<String, Object> style9 = new Hashtable<String, Object>();
        style9.put(mxConstants.STYLE_FONTSIZE,0);
        style9.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style9.put(mxConstants.STYLE_OPACITY, 10);
        style9.put(mxConstants.STYLE_FONTCOLOR, "#0000FF");
        style9.put(mxConstants.STYLE_FILLCOLOR,colorRectang);
        style9.put(mxConstants.STYLE_ROUNDED,"1");
        //style7.put(mxConstants.STYLE_GRADIENTCOLOR,"#0000FF");
        style9.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style9.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style9.put(mxConstants.STYLE_STROKE_OPACITY, 100);     
        stylesheet.putCellStyle("SENALIZAR", style9);
        
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
        
        
        Hashtable<String, Object> style11 = new Hashtable<String, Object>();
        style11.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style11.put(mxConstants.STYLE_OPACITY, 80);     
        style11.put(mxConstants.STYLE_ROUNDED,"1");
        style11.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
//        style11.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style11.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
//        style11.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadLeido().getRGB()));
        style11.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorLeido(),16)));
//        style11.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        style11.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraCadena());
        //style.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
       
        stylesheet.putCellStyle("LEIDO", style11);
        
        Hashtable<String, Object> style12 = new Hashtable<String, Object>();
        style12.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style12.put(mxConstants.STYLE_OPACITY, 10);
        style12.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        style12.put(mxConstants.STYLE_ROUNDED,"1");
        style12.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
//        style12.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        style12.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
//        style12.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(app.getColorCadPendiente().getRGB()));
        style12.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorPend(),16)));
//        style12.put(mxConstants.STYLE_FONTSIZE, app.getSizeCadena());
        style12.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraCadena());
        // style2.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
        stylesheet.putCellStyle("PENDIENTE", style12);
        Object parent = graph.getDefaultParent();
        Object modelo=graph.getModel();
        this.graphComponent = new mxGraphComponent(graph);  
        x=300;
        y=75;
        
        this.graphComponent.getViewport().setOpaque(true);
        this.graphComponent.getViewport().setBackground(Color.WHITE);
        
    }
        

 /**
  * insert the children of a node
  * @param padre
  * parent
  * @param x
  * position in axis x
  * @param y
  * position in axis y
  * @param incX
  * displacement in x axis to the right brothers
  * @param hijos
  * list of children symbols
  * @return inserted vertexs
  */   
    public List<Object> insertarHijos(Object padre,int x, int y, int incX, List<Simbolo> hijos) {
        List<Object> retorno = new ArrayList<>();
        String color;
        x=x-incX; //hijo izquierdo
        y=y+75;
        Object v1;
        graph.getModel().beginUpdate();
		try
		{   
                   
                   for(int i=0; i<hijos.size();i++){
                            if(hijos.get(i).isTerminal()){
                            color = "ROJO";  
                            }else
                              color="VERDE";

                        v1 = graph.insertVertex(parent, null, hijos.get(i), x, y, 80,
                              30,color);
                        x=x+2*incX;
                         graph.insertEdge(parent, null, "", padre, v1);
                        retorno.add(v1);
                        
                   }	   
                  
		}
                
		finally
		{
		   
		}
		graph.getModel().endUpdate();
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
              return retorno;
    }
   /**
    * insert one children 
    * @param padre
    * parent
    * @param x
    * position in x axis
    * @param y
    * position in y axis
    * @param hijo
    * children to insert
    * @return inserted vertex
    */ 
    public Object insertarHijo(Object padre,int x, int y,Simbolo hijo) {
        
        String color;
       
        //y=y+75;
        Object v1;
        graph.getModel().beginUpdate();
		try
		{   
                   
                   
                            if(hijo.isTerminal()){
                            color = "ROJO";  
                            }else
                              color="VERDE";

                        v1 = graph.insertVertex(parent, null, hijo, x, y, anchoNodo,
                              altoNodo,color);
                        
                         graph.insertEdge(parent, null, "", padre, v1);
                       
                        
                   }	   
                  
		
                
		finally
		{
		   
		}
		graph.getModel().endUpdate();
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
              return v1;
    }
    
    /*
    añadir un elemento
    */
    /**
     * insert an element
     * @param s
     * symbol to insert
     * @param x
     * position in x axis
     * @param y
     * @return vertex inserted
     */
     public Object insertarElemento(Simbolo s, int x, int y) {
         Object vp = null;
         String color;
        graph.getModel().beginUpdate();
		try
		{                    
                    if(s.isTerminal()){
                      color="ROJO";  
                    }else
                      color="VERDE";
                    vp = graph.insertVertex(parent, null, s,
		         x, y, anchoNodo, altoNodo,color);
		   
                   y= y + 75;		   
                  
		}
                
		finally
		{
                                  
		}
		 graphComponent.setEnabled(false);
		   graph.getModel().endUpdate();   
            
              
              return vp;
    }
     
     /*
     Añadir un elemento con ese estilo
     */
     /**
      * insert element with a color
      * @param s
      * symbol to insert
      * @param x
      * position in x axis
      * @param y
      * position in y axis
      * @param color
      * color of the new node
      * @return vertex inserted
      */
      public Object insertarElemento(Simbolo s, int x, int y, String color) {
         Object vp = null;
         
        graph.getModel().beginUpdate();
		try
		{                    
                    
                    vp = graph.insertVertex(parent, null, s,
		         x, y, anchoNodo, altoNodo,color);
		   
                   		   
                  
		}
                
		finally
		{
                                  
		}
		 graphComponent.setEnabled(false);
		   graph.getModel().endUpdate();   
            
              
              return vp;
    }
/**
 * inert a symbol with a determinate size and color
 * @param s
 * symbol to insert
 * @param x
 * position in x axis
 * @param y
 * position in y axis
 * @param ancho
 * width of the node
 * @param alto
 * heigth of the node
 * @param color
 * color of the node
 * @return vertex inserted
 */      
       public Object insertarElemento(Simbolo s, int x, int y,int ancho, int alto, String color) {
         Object vp = null;
         
        graph.getModel().beginUpdate();
		try
		{                    
                    
                    vp = graph.insertVertex(parent, null, s,
		         x, y, ancho, alto,color);
		   
                   		   
                  
		}
                
		finally
		{
                                  
		}
		 graphComponent.setEnabled(false);
		   graph.getModel().endUpdate();   
            
              
              return vp;
    }
     
     
     /*
     Construir el árbol de abajo hacia arriba
     */
       /**
        * insert parent
        * @param padre
        * symbol to insert
        * @param x
        * position in x axis
        * @param y
        * position in y axis
        * @param hijos
        * childrens 
        * @return vertex inserted
        */
      public Object insertarPadre(Simbolo padre,int x, int y, List<Object> hijos) {
        
        Object v1 = null;
        graph.getModel().beginUpdate();
               
		try
		{               
                    
                     v1 = graph.insertVertex(parent, null, padre, x, y, anchoNodo,
                              altoNodo,"VERDE");
                   for(int i=0; i<hijos.size();i++){                        
                       graph.insertEdge(parent, null, "", v1, hijos.get(i));                      
                        
                   }	
                   
                  
                  
		}
                
		finally
		{
		   
		}
		graph.getModel().endUpdate();
              return v1;
      }
      /**
       * insert parent
       * @param padre
       * element to insert
       * @param x
       * position in x axis
       * @param y
       * position in y axis
       * @param hijos
       * children in string
       * @param lista
       * a list of children
       * @return vertex inserted
       */
       public Object insertarPadre(Simbolo padre, int x,int y, String[] hijos,List<Object>lista) {
        int pos;
        Object v1 = null;
        graph.getModel().beginUpdate();
               
		try
		{
                   
                     v1 = graph.insertVertex(parent, null, padre, x, y,anchoNodo,
                              altoNodo,"VERDE");
                     
                   for(int i=1; i<hijos.length;i++){                        
                       graph.insertEdge(parent, null, "", v1, lista.get(Integer.parseInt(hijos[i])));                      
                        
                   }
                   //añadimos la posición del nuevo nodo a la lista de posiciones para futuras inserciones
                   
                  
                  
		}
                
		finally
		{
		   
		}
		graph.getModel().endUpdate();
              return v1;
      }
      /**
       * remove a node
       * @param s 
       * symbol to remove
       */
      public void eliminar(Object s){
          
         
        graph.getModel().beginUpdate();
		try
		{                    
                   Object[] aux= new Object[1];
                    //graph.removeSelectionCell(s);   
                    //graph.removeCells(aux);
                    //graph.cellsRemoved(aux);
                    //graph.removeCells(graph.getAllEdges(aux));
                    //graph.refresh();
                    //Object elim=graph.insertVertex(parent, null, "prueba",
		     //  x+20, y+20, 50, 30);
                    aux[0]=s;
                    graph.removeCells(aux);
                  
		}
                
		finally
		{
		                      
		}
		graph.getModel().endUpdate();
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
              
      }
    /**
     * remove a array of symbols
     * @param s 
     * array of symbols
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
      
      /*
      Insertar las reglas de la gramatica      
      */
      /*
      public void insertarRegla(int y,String[] regla) {
         int pos=0; 
         int contador=0;
        graph.getModel().beginUpdate();
		try
		{                    
                    
                String color = "VERDE";
                   for(String s: regla){
                       Object r = graph.insertVertex(parent, null, s,
		        pos, y, 50, 30,color);
                       if(contador==0)
                            pos=pos+30;
                       else
                           pos=pos+20;
                   }
                  
		}
                
		finally
		{
		   graph.getModel().endUpdate();                   
		}
		
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
      }
      
        public void insertarRegla(int y,Simbolo[] regla) {
            String color;
            int pos=0; 
            
           graph.getModel().beginUpdate();
		try
		{              
                   for(Simbolo s: regla){
                       if(s.isTerminal()){
                      color="GROJO";  
                    }else
                      color="GVERDE";
                       Object r = graph.insertVertex(parent, null, s,
		        pos, y, 50, 30,color);
                        if(pos==0)
                            pos=pos+80;
                       else
                           pos=pos+20;
                   }
                  
		}
                
		finally
		{
		   graph.getModel().endUpdate();                   
		}
		
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
      }
    */ 
      /**
       * inserted a rule in the graph of the grammar 
       * @param y
       * position in y axis
       * @param regla
       * list of symbols to insert
       * @return vertex inserted
       */
         public List<Object> insertarRegla(int y,List<Simbolo> regla) {
            String color;
            int pos=15; 
            int t= 30;
            List<Object> listaR=new ArrayList<>();
           graph.getModel().beginUpdate();
		try
		{              
                   for(Simbolo s: regla){
                       if(s.isTerminal()){
                      color="GROJO";
                      pos=pos-5;
                    }else{
                      color="GVERDE";
                      
                       }
                       Object r = graph.insertVertex(parent, null, s,
		        pos, y, this.altoSimbRegla, this.anchoSimbRegla,color);
                        if(pos==0){
                            pos=pos+Grafo.desReglaInicial;
                            t=20;
                        }
                       else
                           pos=pos+Grafo.desRegla;  
                        
                        listaR.add(r);
                        this.miGramatica.add(r);
                   }
                  
		}
                
		finally
		{
		                     
		}
		graph.getModel().endUpdate(); 
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
              return listaR;
      }
/**
 * insert a rule with the color of the terminals and the no terminals
 * @param y
 * position in y axis
 * @param regla
 * list of symbols to insert
 * @param term
 * @param noTerm
 * @return vertexs inserted3
 */         
       public List<Object> insertarRegla(int y,List<Simbolo> regla,String term, String noTerm) {
            String color;
            int pos=15; 
            int t= 30;
            List<Object> listaR=new ArrayList<>();
           graph.getModel().beginUpdate();
		try
		{              
                   for(Simbolo s: regla){
                       if(s.isTerminal()){
                      color=term;
                      pos=pos-5;
                    }else{
                      color=noTerm;
                      
                       }
                       Object r = graph.insertVertex(parent, null, s,
		        pos, y, this.altoSimbRegla, this.anchoSimbRegla,color);
                        if(pos==0){
                            pos=pos+Grafo.desReglaInicial;
                            t=20;
                        }
                       else
                           pos=pos+Grafo.desRegla;  
                        
                        listaR.add(r);
                        this.miGramatica.add(r);
                   }
                  
		}
                
		finally
		{
		                     
		}
		graph.getModel().endUpdate(); 
              this.graphComponent = new mxGraphComponent(graph);                
              graphComponent.setEnabled(false);
              return listaR;
      }

      /*
        Inserto un rectángulo que engloba toda la regla
      */
      /**
       * insert a rectangle that englove all rule 
       * @param c
       * rule
       * @param x
       * position in x axis 
       * @param y
       * position in y axis
       * @return rectangle inserted
       */  
      public Object insertarRectangulo(Regla c, int x, int y){          
           String color;
           int pos=0; 
           int t= 30;
           int margenSup=10;
          
           
          /*
           el tamaño de la regla c consiste en conocer cuál es el último terminal
           reconocido hasta ese momento.
            Multiplicamos por la separación que hay entre los terminales 
           Restando esto con la posición del primer elemento del rectángulo (x)
           obtenemos el tamaño           
           */
          int tamX;
          /*
          if(traductor.equals("ascendente"))
           tamX= (c.getTam())*MiApp.separacionTerminales-x;//ancho del nodo
          else
             tamX= (c.getTam())*MiApp.separacionTerminales;//ancho del nodo
          */
          tamX= (c.getTam())*85-x;//ancho del nodo
          
           Object o;
           graph.getModel().beginUpdate();
		try
		{              
                  o=graph.insertVertex(parent,null,c,x,y-margenSup,tamX,altoNodo+2*margenSup,"GAZUL"); 
                  //insertar mapa q empareje celda y regla?
		}
                
		finally
		{
                                     
		}
               this.rectangRegla.put(o,c);
               FicheroXML ejemplo = new FicheroXML();
                ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\TFGv0\\traductores\\descend2.xml");
               if(ejemplo.getTipoTraductor().equals("Descendente")){
                   List<Object> pila=insertarPila(c,x,y);
                   emparejarPilaRectangulo(o, pila);
                   List<Integer> posicion=new ArrayList<>();
                   posicion.add(x+pila.size()*this.espacioPila);
                   posicion.add(y);
                  this.rectPos.put(o,posicion);
                   
               }else{
                   graphComponent.setEnabled(false);
		   graph.getModel().endUpdate(); 
               }
                 
                return o;
              
      }
      /**
  * 
  * @param rect
  * rectangle of node of the tree
  * @param pila 
  * 
  */  
    public void emparejarPilaRectangulo(Object rect, List<Object> pila){
        this.rectangPila.put(rect, pila);
    }
      /**
       * insert a rule to the stack
       * @param c
       * rule
       * @param x
       * position in x axis
       * @param y
       * position in y axis
       * @return stack
       */
      public List<Object> insertarPila(Regla c, int x, int y){
          String[] l=c.getValor().split(" ");
          Object o;
          int contador=0;
          String estilo;
          
          List<Object> pila= new ArrayList<>();
          for(String i:l){
              if (contador>1){
                try
                  { 
                      if ( (i.equals(";")) || (i.equals("num")) || (i.equals("*")) || (i.equals("+")) || (i.equals("(")) || (i.equals(")")) )
                          estilo="ROJOPILA";
                      else
                          estilo="VERDEPILA";
                      x=x+espacioPila;
                    o=graph.insertVertex(parent,null,i,x,y,anchoPila,altoPila,estilo); 
                    pila.add(o);
                    this.pilaPosicion.put(o, x);
                    //insertar mapa q empareje celda y regla?
                  }

                  finally
                  {
                                      
                  }  
                }
                contador++;
          }
          
          graphComponent.setEnabled(false);
                     graph.getModel().endUpdate();  
          return pila;
          
      }
      /*
      Insertar un nuevo elemento a la pila
      */
      /**
       * insert an element to the stack
       * @param s
       * element to insert 
       * @param x
       * position in x axis
       * @param y
       * position in y axis
       * @return stack
       */
      public Object insertarElemPila(String s, int x, int y){
          
          Object o;
          
          final int tamX=40;
         String estilo;
          if ( (s.equals(";")) || (s.equals("num")) || (s.equals("*")) || (s.equals("+")) || (s.equals("(")) || (s.equals(")"))  )
                          estilo="ROJOPILA";
                      else
                          estilo="VERDEPILA";
          
              
                try
                  { 
                     
                    o=graph.insertVertex(parent,null,s,x,y,anchoPila,altoPila,estilo); 
                    
                    this.pilaPosicion.put(o, x);
                    //insertar mapa q empareje celda y regla?
                  }

                  finally
                  {
                                      
                  }  
         
          
          return o;
          
      }
      /**
       * allow see the rule
       * @param c 
       * rule
       */
      public void verRegla(String c){
          
       
          String array[] =c.split("//");
          //System.out.println(array[1]);
          Grafo gramatica=this.app.getGramatica();
          
           if(array.length>1)  {       
           String id=array[1];
           String[] idAux=id.split("R");
           int fila= Integer.parseInt(idAux[1])-1;
           int posY=yInicial+MiApp.sepRegla*fila;
            
            
            gramatica.setMarcado(gramatica.senalizar(posY,fila));
           }
      }
      /**
       * allow see the rule
       * @param c 
       * rule
       */
      public void verRegla(String c,Grafo gramatica,List<List<Object>> listaReglas,int sepRegla,int sizeLetra){
        String array[] =c.split("//");
        if(array.length>1)  {       
            String id=array[1];
            String[] idAux=id.split("R");
            int fila= Integer.parseInt(idAux[1])-1;
            int posY=yInicial+MiApp.sepRegla*fila;
            gramatica.setMarcado(gramatica.senalizar(posY,fila,listaReglas,sizeLetra));
        }
      }
     /*
      función para colocar la acción semántica en la regla de la gramatica correspondiente
      */
      /**
       * place the semmantic action in the rule of the grammar 
       * @param c
       * semantic action
       * @param cell 
       * rule where it will be placed
       */
      public void accSeman(String c,Object cell){
          
          
          String array[] =c.split("//");
          System.out.println(array[1]);
          Grafo gramatica=this.app.getGramatica();
          Map<String,Regla> mapaGramatica=this.app.getMapaGramatica();
          List<String> atributosAccion= new ArrayList();
          for(int i=2;i<array.length;i++)
            atributosAccion.add(array[i]);
           String id=array[1];
           //obtenemos la fila eliminando la r de la id de la regla R1 ->1
           String[] divId=id.split("R");
           int fila=Integer.parseInt(divId[1])-1;//restamos 1 para comenzar en la fila 0
           List<Object> reglaAct=app.getListaReglas().get(fila);
           int posY=yInicial+MiApp.sepRegla*fila;
           
            Regla regla= mapaGramatica.get(id);
            
            List<Object> listaAcciones = gramatica.rectangAccionSem.get(id);//lista acciones insertadas con esa id
            List<AccionSemantica> acciones = regla.getAcciones();
            if(!regla.isAccionesInsertadas()){//comporbar si dicha acción ha sido ya insertada                
                List<Object> accionesInsertadas = gramatica.insertarAccSem(acciones,reglaAct,posY,fila,cell);
                
                for(int j=0;j<accionesInsertadas.size();j++){
                    if((atributosAccion.size()>j) && (!atributosAccion.isEmpty()))
                        gramatica.accionAtributos.put(accionesInsertadas.get(j),atributosAccion.get(j));
                    else
                        gramatica.accionAtributos.put(accionesInsertadas.get(j),null);
                }
                gramatica.rectangAccionSem.put(id,accionesInsertadas);
                regla.setAccionesInsertadas(true);
            }
            else if(listaAcciones!=null){
              String aux = gramatica.accionAtributos.get(listaAcciones.get(0));//falta prepararlo para varias acciones
              if(atributosAccion.isEmpty()){
                  gramatica.eliminarAccion(id,reglaAct,acciones);                
                  regla.setAccionesInsertadas(false);
              }
              else if(aux.equals(atributosAccion.get(0))){
                    gramatica.eliminarAccion(id,reglaAct,acciones);                
                    regla.setAccionesInsertadas(false);
                }
                else{
                    int k=0;
                    for(Object l:listaAcciones){                        
                       gramatica.accionAtributos.remove(l);                      
                       gramatica.accionAtributos.put(listaAcciones.get(0), atributosAccion.get(k));
                       k++;
                    }
                }
            }
            
          
          
          
       } 
      
       /**
       * place the semmantic action in the rule of the grammar 
       * @param c
       * semantic action
       * @param cell 
       * rule where it will be placed
       * @param gramatica
       * grammar
       * @param mapaGramatica
       * match the id of the xml with the rule
       * @param listaReglas
       * list of inserted rules in translator
       */
      public void accSeman(String c,Object cell,Grafo gramatica,Map<String,Regla> mapaGramatica,List<List<Object>> listaReglas,int sepRegla){
          
          
          String array[] =c.split("//");
          System.out.println(array[1]);
          List<String> atributosAccion= new ArrayList();
          for(int i=2;i<array.length;i++)
            atributosAccion.add(array[i]);
           String id=array[1];
           //obtenemos la fila eliminando la r de la id de la regla R1 ->1
           String[] divId=id.split("R");
           int fila=Integer.parseInt(divId[1])-1;//restamos 1 para comenzar en la fila 0
           List<Object> reglaAct=listaReglas.get(fila);
           int posY=yInicial+sepRegla*fila;
           
            Regla regla= mapaGramatica.get(id);
            
            List<Object> listaAcciones = gramatica.rectangAccionSem.get(id);//lista acciones insertadas con esa id
            List<AccionSemantica> acciones = regla.getAcciones();
            if(!regla.isAccionesInsertadas()){//comporbar si dicha acción ha sido ya insertada                
                List<Object> accionesInsertadas = gramatica.insertarAccSem(acciones,reglaAct,posY,fila,cell);
                
                for(int j=0;j<accionesInsertadas.size();j++){
                    if((atributosAccion.size()>j) && (!atributosAccion.isEmpty()))
                        gramatica.accionAtributos.put(accionesInsertadas.get(j),atributosAccion.get(j));
                    else
                        gramatica.accionAtributos.put(accionesInsertadas.get(j),null);
                }
                gramatica.rectangAccionSem.put(id,accionesInsertadas);
                regla.setAccionesInsertadas(true);
            }
            else if(listaAcciones!=null){
              String aux = gramatica.accionAtributos.get(listaAcciones.get(0));//falta prepararlo para varias acciones
              if(atributosAccion.isEmpty()){
                  gramatica.eliminarAccion(id,reglaAct,acciones);                
                  regla.setAccionesInsertadas(false);
              }
              else if(aux.equals(atributosAccion.get(0))){
                    gramatica.eliminarAccion(id,reglaAct,acciones);                
                    regla.setAccionesInsertadas(false);
                }
                else{
                    int k=0;
                    for(Object l:listaAcciones){                        
                       gramatica.accionAtributos.remove(l);                      
                       gramatica.accionAtributos.put(listaAcciones.get(0), atributosAccion.get(k));
                       k++;
                    }
                }
            }
            
          
          
          
       } 
      /**
       * insert the semantic action to the grammar
       * @param acciones
       * actions to insert
       * @param reglaAct
       * current rule
       * @param posY
       * position in y axis 
       * @param fila
       * row of the rule
       * @param cell
       * node to insert
       * @return empty list
       */
      public List<Object> insertarAccSem(List<AccionSemantica> acciones,List<Object> reglaAct, int posY,int fila, Object cell){
           boolean interm=false;//comprobar si hay intermedio para desplazar las acciones
           
           List<Object> lista= new ArrayList<>();
           Object o;
           final int despAccion=80; //desplazamiento de la accion semantica con respecto al simbolo de la gramtica
            int posX;
            String tipo;//tipo para saber si es una acción semántica situada al final o entre medias de la regla de la gramática
            int ancho,alto;
             ancho=60;
             alto=40;
             tipo="ACCIONES";
             int despTotal=(acciones.get(0).getValor().length())*multiplicador;
             int desplaz=0;
           for(AccionSemantica accion: acciones)
           {
               if(accion.isFin()){                   
                   
                  if(interm)
                       posX= despAccion+desReglaInicial+(accion.getPos()-2)*Grafo.desRegla+despTotal;
                  else
                   posX= despAccion+desReglaInicial+(accion.getPos()-2)*Grafo.desRegla;
               }
               else{                
                  desplaz = (accion.getValor().length())*multiplicador;
                   interm=true;
                   posX= despAccion+desReglaInicial+(accion.getPos()-2)*Grafo.desRegla;
                   desplazarRegla(reglaAct,accion.getPos(),posX,posY,desplaz);
                   //ponemos la lista de desplazados a true
                   
                   //posY=posY+5;
                    
               }
               
               this.listaDesplazados.remove(fila);
               if(acciones.size()>1)
                    this.listaDesplazados.add(fila, 2);
               else
                   this.listaDesplazados.add(fila,1);
               
               Object accionAInsertar=insertarNodoAccion(lista,accion,posX,posY,tipo,ancho,alto,desplaz,cell);
               
               listaAccionesInsertadas.add(accionAInsertar);
              
               
           }     
                return lista;
      }
 /**
  * 
  * @param lista
  * list of vertex
  * @param accion
  * semantic action
  * @param posX
  * position in x axis
  * @param posY
  * position in y axis
  * @param tipo
  * type 
  * @param ancho
  * width
  * @param alto
  * heigth
  * @param desplaz
  * displacement
  * @param cell
  * vertex to insert
  * @return vertex inserted
  */     
      public Object insertarNodoAccion(List lista, AccionSemantica accion, int posX, int posY,String tipo, int ancho,int alto,int desplaz, Object cell){
          Object o;
          int tamX;
          if(desplaz>0)
              tamX=desplaz-50;
          else
              tamX=2*ancho;
          graph.getModel().beginUpdate(); 
		try
		{              
                  o=graph.insertVertex(parent,null,accion.getValor(),posX,posY,tamX,alto,tipo);
                  
                  lista.add(o);
		}
                
		finally
		{
                                     
		}
                graphComponent.setEnabled(false);
		   graph.getModel().endUpdate(); 
                   return o;
      }
      /**
       * displace the rules
       * @param r
       * rules to displace
       * @param posAcc
       * Semantic action position
       * @param posX
       * 
       * @param posY
       * @param desplaz
       * displacement
       */
      public void desplazarRegla(List<Object> r,int posAcc,int posX,int posY,int desplaz){
          
          for(int i=posAcc; i<r.size();i++){              
                 
              graph.translateCell(r.get(i), desplaz, 0);
              
           }
         
      }
      /**
       * 
       * @param posY
       * position in y axis
       * @param fila
       * file to signpost
       * @return vertex signposted
       */
      public Object senalizar(int posY,int fila){
          int incremRect=3;
          Object o;
          graph.getModel().beginUpdate(); 
          int posX=0;
          int ancho;
          
          int sizeRegla=app.getListaReglas().get(fila).size();
          if(this.listaDesplazados.get(fila)==2)
              ancho=700;
          else if(this.listaDesplazados.get(fila)==1){
              
              ancho=55*sizeRegla+200+this.app.getSizeLetra()*10;
          }
          else{
              
              ancho=55*sizeRegla;
          }
		try
		{              
                  o=graph.insertVertex(parent,null,null,posX,posY,ancho,this.altoSimbRegla+incremRect,"SENALIZAR");
                  
		}
                
		finally
		{
                   graphComponent.setEnabled(false);
		   graph.getModel().endUpdate();                   
		}
                return o;
      }
      /**
       * signed the rule
       * @param posY
       * position in y axis
       * @param fila
       * file to signpost
       * @return vertex signposted
       */
      public Object senalizar(int posY,int fila,List<List<Object>> listaReglas,int sizeLetra){
          int incremRect=3;
          Object o;
          graph.getModel().beginUpdate(); 
          int posX=0;
          int ancho;
          
          int sizeRegla=listaReglas.get(fila).size();
          if(this.listaDesplazados.get(fila)==2)
              ancho=700;
          else if(this.listaDesplazados.get(fila)==1){
              
              ancho=55*sizeRegla+200+sizeLetra*10;
          }
          else{
              
              ancho=55*sizeRegla;
          }
		try
		{              
                  o=graph.insertVertex(parent,null,null,posX,posY,ancho,this.altoSimbRegla+incremRect,"SENALIZAR");
                  
		}
                
		finally
		{
                   graphComponent.setEnabled(false);
		   graph.getModel().endUpdate();                   
		}
                return o;
      }
      /**
       * remove action
       * @param id
       * id of the action to remove
       * @param regla
       * list of all rules
       * @param acciones 
       * list of all actions
       */
      public void eliminarAccion(String id,List<Object> regla,List<AccionSemantica> acciones){
          List<Object> l=this.rectangAccionSem.get(id);
         
            int lg = (acciones.get(0).getValor().length())*multiplicador;
          for(int i=acciones.get(0).getPos();i<regla.size();i++){
              
              graph.translateCell(regla.get(i), -lg , 0);
          }
         
                  String[] n=id.split("R");
                  int val=Integer.parseInt(n[1])-1;
                  this.listaDesplazados.remove(val);
                  this.listaDesplazados.add(val, 0);
             
          for(Object o:l){
            this.eliminar(o);
            
            listaAccionesInsertadas.remove(o);
            
          }
          this.rectangAccionSem.remove(id);
      }
        /**
       * Activate the tooltips
       */
      public void activarListener(Grafo gramatica,Map<String,Regla> mapaGramatica,List<List<Object>> listaReglas,int sepRegla,int sizeLetra){
           graphComponent.setToolTips(true);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter(){
		
			public void mouseReleased(MouseEvent e){
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				//app.getMenu().getBarraMenu().requestFocusInWindow();

				if (cell != null){
                                    //elimino el rectángulo de la gramática al pasar sobre el árbol o traductor
                                    //Grafo gramatica=app.getGramatica();
                                    if(gramatica!=null){
                                            if(gramatica.getMarcado()!=null){
                                                gramatica.eliminar(gramatica.getMarcado());
                                                gramatica.setMarcado(null);
                                            }                                            
                                    }else{
                                        if(marcado!=null){
                                            eliminar(marcado);
                                            setMarcado(null);
                                        }
                                    }
                                    if(objetoToolTip[0]!=null)
                                        graph.setCellStyle("GAZUL", objetoToolTip);
                                    Map<String, Object> m=graph.getCellStyle(cell);
                                    if(m.get("fontColor")=="#0000FF"){                                        
                                        //insertar acción
                                        accSeman(graph.getLabel(cell),cell,gramatica,mapaGramatica,listaReglas,sepRegla);
                                        //señalizar
                                        Object[] array = new Object[1];
                                        array[0]=cell;
                                        objetoToolTip[0]=cell;
                                        graph.setCellStyle("RECTTOOLTIP", array);
                                         //Para que el usuario sepa en que regla se insertan las acciones semánticas.
                                        verRegla(graph.getLabel(cell),gramatica,listaReglas,sepRegla,sizeLetra);
                                        
                                    }else{
                                        System.out.println("cell="+graph.getLabel(cell));
                                    }      
				}        
			}
		});
      }
      /**
       * Activate the tooltips
       */
      public void activarListener(){
           graphComponent.setToolTips(true);
		
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter()
		{
		
			public void mouseReleased(MouseEvent e)
			{
				Object cell = graphComponent.getCellAt(e.getX(), e.getY());
				app.getMenu().getBarraMenu().requestFocusInWindow();

				if (cell != null)
				{
                                    //elimino el rectángulo de la gramática al pasar sobre el árbol o traductor
                                    Grafo gramatica=app.getGramatica();
                                    if(gramatica!=null){
                                            if(gramatica.getMarcado()!=null){
                                                gramatica.eliminar(gramatica.getMarcado());
                                                gramatica.setMarcado(null);
                                            }                                            
                                    }else{
                                        if(marcado!=null){
                                            eliminar(marcado);
                                            setMarcado(null);
                                        }
                                    }
                                     if(objetoToolTip[0]!=null)
                                                graph.setCellStyle("GAZUL", objetoToolTip);
                                    Map<String, Object> m=graph.getCellStyle(cell);
                                    app.getElemValor().get(cell);
                                    if(m.get("fontColor")=="#0000FF"){                                        
                                                //insertar acción
                                        accSeman(graph.getLabel(cell),cell);
                                        //señalizar
                                        Object[] array = new Object[1];
                                                array[0]=cell;
                                                objetoToolTip[0]=cell;                              
                                                
                                                 graph.setCellStyle("RECTTOOLTIP", array);
                                                /*
                                                 Para que el usuario sepa en que regla se insertan las acciones semánticas.
                                                 
                                                 */
                                                verRegla(graph.getLabel(cell));
                                        
                                    }else{
                                        System.out.println("cell="+graph.getLabel(cell));
                                    }
                                        
                                        
				}
                                
                                    
			}
                   

                        
                        
                    
		});
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
       * relocated de elements of the stack
       * @param o
       * element
       * @param p
       * position
       */
     public void trasladarElemPila(Object o, int p){
         int posAnterior;
         if(pilaPosicion.containsKey(o)){
             posAnterior= this.pilaPosicion.get(o);        
            pilaPosicion.remove(o);
            this.graph.translateCell(o, p-posAnterior+this.anchoNodo, 0);
         }
            
         
     }
    
     /**
      * removed all grammar
      */
     public void eliminarGramatica(){ 
         app.setListaReglas(new ArrayList<>());
         Object[] arrayGram=miGramatica.toArray();
         eliminar(arrayGram);
         eliminar(listaAccionesInsertadas.toArray());
         rectangAccionSem= new HashMap<>(); 
         this.listaDesplazados= new ArrayList<>();
         for(int u=0; u<app.getFicheroXml().getListaGramatica().size();u++){
            listaDesplazados.add(0);
        }
         listaAccionesInsertadas= new ArrayList<>();
       
     }
    /**
     * 
     * @return the graph
     */ 
    public mxGraph getGraph() {
        return graph;
    }
/**
 * 
 * @param graph
 * the graph
 */
    public void setGraph(mxGraph graph) {
        this.graph = graph;
    }
/**
 * 
 * @return parent 
 */
    public Object getParent() {
        return parent;
    }
/**
 * 
 * @param parent
 * parent
 */
    public void setParent(Object parent) {
        this.parent = parent;
    }
/**
 * 
 * @return model 
 */
    public Object getModelo() {
        return modelo;
    }
/**
 * 
 * @param modelo
 * model
 */
    public void setModelo(Object modelo) {
        this.modelo = modelo;
    }
/**
 * 
 * @return graph component 
 */
    public mxGraphComponent getGraphComponent() {
        return graphComponent;
    }
/**
 * 
 * @param graphComponent 
 * graph component
 */
    public void setGraphComponent(mxGraphComponent graphComponent) {
        this.graphComponent = graphComponent;
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
 * 
 * @return maps with the rectangles and her position
 */
    public Map<Object, List<Integer>> getRectPos() {
        return rectPos;
    }
/**
 * 
 * @param rectPos 
 * maps with the rectangles and her position
 */
    public void setRectPos(Map<Object, List<Integer>> rectPos) {
        this.rectPos = rectPos;
    }
/**
 * 
 * @return width of node 
 */
    public int getAnchoNodo() {
        return anchoNodo;
    }
/**
 * 
 * @param anchoNodo
 * width of node 
 */
    public void setAnchoNodo(int anchoNodo) {
        this.anchoNodo = anchoNodo;
    }
/**
 * 
 * @return heigth of node
 */
    public int getAltoNodo() {
        return altoNodo;
    }
/**
 * 
 * @param altoNodo 
 * heigth of node
 */
    public void setAltoNodo(int altoNodo) {
        this.altoNodo = altoNodo;
    }
/**
 * 
 * @return width of the stack
 */
    public int getAnchoPila() {
        return anchoPila;
    }
/**
 * 
 * @param anchoPila
 * width of the stack
 */
    public void setAnchoPila(int anchoPila) {
        this.anchoPila = anchoPila;
    }
/**
 * 
 * @return heigth of stack 
 */
    public int getAltoPila() {
        return altoPila;
    }
/**
 * 
 * @param altoPila 
 * heigth of stack 
 */
    public void setAltoPila(int altoPila) {
        this.altoPila = altoPila;
    }
/**
 * 
 * @return stack space
 */
    public int getEspacioPila() {
        return espacioPila;
    }
/**
 * 
 * @param espacioPila
 * space stack
 */
    public void setEspacioPila(int espacioPila) {
        this.espacioPila = espacioPila;
    }
/**
 * 
 * @return 
 */
    public Object getMarcado() {
        return marcado;
    }
/**
 * 
 * @param marcado 
 */
    public void setMarcado(Object marcado) {
        this.marcado = marcado;
    }
/**
 * 
 * @return grammar 
 */
    public List<Object> getMiGramatica() {
        return miGramatica;
    }
/**
 * 
 * @param miGramatica
 * grammar
 */
    public void setMiGramatica(List<Object> miGramatica) {
        this.miGramatica = miGramatica;
    }

    public Map<Object, List<Object>> getRectangPila() {
        return rectangPila;
    }

    
    
     
    
    
}
