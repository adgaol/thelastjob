package fmxl;



import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */


public class VentanaConfig {
    final static int posYHijos=100;
    final static int xInicial=100;
    final static int xFinal=200;
    final static int xPadre=150;
    final static int yPadre=50;
    final static int posRegla=200;
    final static int incCadena=70;
    final static int posYCadena=300;
    
        JComboBox combo;        
        JComboBox combo2;  
        JComboBox combo3 ;        
        JComboBox combo4;
        
        
        List<Object> terminales;
        List<Object> nTerminales;
        
        Grafo arbolEjemplo;
        ModificacionesTemp mT;
        MiApp app;
        JPanel panel;
        JFrame ventana;
        JSplitPane splitPane;
        JSplitPane splitPane2;
        /**
         * initialize the editor of configuration
         * @param app
         * app
         */
    public void crearEditor(MiApp app) {
        
      ventana = new JFrame("Configuración");
        splitPane = new JSplitPane();
        splitPane2= new JSplitPane();
        splitPane.setResizeWeight(0.8);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        
        splitPane2.setResizeWeight(0.99);
        splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        ventana.getContentPane().add(splitPane, BorderLayout.CENTER);
        this.app=app;
        combo = new JComboBox();        
        combo2 = new JComboBox();  
        combo3 = new JComboBox();
        combo4 = new JComboBox();
        
        terminales= new ArrayList<>();
        nTerminales = new ArrayList<>();
        ventana.setVisible(true);
        ventana.setSize(1200, 700);
        ventana.setLocation(30,30);
        panel = new JPanel();
        arbolEjemplo = new Grafo(app);
        mT=inicializarModificaciones();
        construirEjemplo();
        splitPane.setLeftComponent(splitPane2); 
        splitPane.setRightComponent(panel);
        splitPane2.setTopComponent(arbolEjemplo.getGraphComponent());
        
        
       
        
       
        
        
       addModLetra();
       addModColores();
       addModCadena(); 
        
        
        //creamos la parte de configuración de las acciones
        addModSemanticas();
         
        addBotones();
       
        panel.setLayout(new GridLayout(5,1));
        
    }
    
    /**
     * Configuration of the semantic action 
     */
    public void addModSemanticas(){
        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new GridLayout(4,2));
        panelAcciones.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel accSem=new JLabel("Acciones semánticas");
         panelAcciones.add(accSem);
         panel.add(panelAcciones);
         Font fontAccSem = new Font(accSem.getFont().getName(),Font.BOLD,accSem.getFont().getSize()+5);
        accSem.setFont(fontAccSem);
        panelAcciones.add(new JLabel(""));
        JLabel label = new JLabel("       Tipo de fuente");   
        JComboBox combo= new JComboBox();
        combo.addItem("Times new Roman");
        combo.addItem("Arial");        
        combo.addItem("Calibri");
        combo.addItem("Courier");
        combo.addItem("Broadway"); 
        combo.addItem("Informal Roman"); 
        combo.addItem("Verdana"); 
        combo.setSelectedItem(app.getTipoLetra());
        combo.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String val = (String) combo.getSelectedItem();
                mT.setTipoLetra(val);                
                modificarArbol();
            }
                  
        });
        
        panelAcciones.add(label);
        JPanel panelCombo= new JPanel();        
        panelCombo.add(combo);
        panelAcciones.add(panelCombo);
        
        JButton colorAccSem = new JButton("Elegir color");
        colorAccSem.addActionListener(elegirColorTraductor(true));
        panelAcciones.add(new JLabel("        Color de fuente"));
        JPanel panelLetraAcc= new JPanel();
        panelLetraAcc.add(colorAccSem);
        panelAcciones.add(panelLetraAcc);
        
        JLabel label4 = new JLabel("        Tamaño de fuente");        
        combo4.addItem("5");
        combo4.addItem("8");
        combo4.addItem("10");  
        combo4.addItem("13");  
        combo4.addItem("15");
        combo4.addItem("18");
        combo4.addItem("20"); 
        int v=app.getSizeAcciones();
        combo4.setSelectedItem(Integer.toString(v));
        combo4.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String val = (String) combo4.getSelectedItem();
                mT.setSizeAcciones(Integer.parseInt(val));                
                modificarArbol();
            }          
        

        });
        
        panelAcciones.add(label4);
        JPanel panelCombo2 = new JPanel();
        panelCombo2.add(combo4);
        panelAcciones.add(panelCombo2);
    }
    /**
     * change the color of terminals or no terminals in the grammar
     * @param terminal
     * if is terminal or no terminal
     * @return action listener
     */
     public ActionListener elegirColorTraductor(boolean terminal){
            
          ActionListener listenerColores = new ActionListener(){
              
            @Override
            public void actionPerformed(ActionEvent e) {
                //miColor = JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
                obtenerColorTraductor(terminal);
                modificarArbol();
                
            }

            
            
        };
        return listenerColores;
    }
    
      /*
    obtener color y aplicarlo para la letra o el fondo de los terminales o no terminales
    */
     /**
      * change the color of the terminals or no terminals of the tree
      * @param terminal
      * if is terminal or no terminal
      * @param fondoLetra
      * if is f change the terminals else change background 
      * @return action listener
      */
        public ActionListener elegirColor(boolean terminal,char fondoLetra){
            
          ActionListener listenerColores = new ActionListener(){
              
            @Override
            public void actionPerformed(ActionEvent e) {
                //miColor = JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
                obtenerColor(terminal,fondoLetra);
                modificarArbol();
                
            }

            
            
        };
        return listenerColores;
    }
   /**
    * allow choose new color for the grammar
    * @param terminal
    * if is terminal or no terminals
    */     
    void obtenerColorTraductor (boolean terminal){
        Color c=JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
       mT.setColorAccSem(c);
    }
    /**
     * allow choose new color for the letter or the rectangles  of the tree
     * @param terminal
     * if is terminal or no terminals
     * @param fondoLetra 
     * if is f change the terminals else change background 
     */
    void obtenerColor (boolean terminal,Character fondoLetra){
        Color c=JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
        if(terminal){
            //a.setcTerminales(c);
            if(fondoLetra.equals('f'))
               mT.setColorTerminales(c);
            else
                mT.setColorLetraTerminales(c);
        }else{
            if(fondoLetra.equals('f'))
                mT.setColornTerminales(c);
            else
                mT.setColorLetranTerminales(c);
        }
    }
    /**
     * listener that react when accept the configuration  
     * @return action listener
     */
    ActionListener aceptarListener(){
           
          ActionListener listenerAceptar = new ActionListener(){
              
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.dispose();
                app.setSizeLetra(mT.getSizeLetraArbol());
                app.setSizeLetraTraductor(mT.getSizeLetraTraductor());
                app.setSizeCadena(mT.getSizeCadena());
                
                app.setcTerminales(mT.getColorTerminales());
                app.setcNoTerminales(mT.getColornTerminales());
                
                app.setLetraTerminales(mT.getColorLetraTerminales());
                app.setLetraNoTerminales(mT.getColorLetranTerminales());
                
                app.setColorCadLeido(mT.getColorCadenaLeido());
                app.setColorCadPendiente(mT.getColorCadenaPendiente());
                
                app.setTipoLetra(mT.getTipoLetra());
                app.setColorAccSem(mT.getColorAccSem());
                app.setSizeAcciones(mT.getSizeAcciones());
                
                
                generarVisualizador();
               
            }
        
        
    };
               return   listenerAceptar;
    } 
    /**
     * react when cancel the configuration
     * @return action listener
     */
    public  ActionListener cancelarListener(){
        return new ActionListener(){
        

            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.setVisible(false);
                ventana.dispose();
            }
                };
    }
    /*
    Generamos el visualizador con los nuevos valores
    */
    /**
     * generate the new style of the interface
     */
    public  void generarVisualizador(){
        
        int cont=app.getContador();
        
        //Obtenemos el valor hexadecimal de los colores actuales
        int nTerm=app.getcNoTerminales().getRGB();
        int term=app.getcTerminales().getRGB();
        String hnTerm=Integer.toString(nTerm,16);
        String hTerm=Integer.toString(term,16);
        
        //de la letra
        int lnterm=app.getLetraNoTerminales().getRGB();
        int lterm=app.getLetraTerminales().getRGB();
        String hlnTerm=Integer.toString(lnterm,16);
        String hlTerm=Integer.toString(lterm,16);
        
        int nCadenaLeido=app.getColorCadLeido().getRGB();
        int nCadenaPend=app.getColorCadPendiente().getRGB();
        String hLeido=Integer.toString(nCadenaLeido,16);
        String hPend=Integer.toString(nCadenaPend,16);
        
        int colorAccSem=app.getColorAccSem().getRGB();
         String hColorAccSem=Integer.toString(colorAccSem,16);
        String tipoLetra=app.getTipoLetra();
        Configuracion conf = new Configuracion();
        conf.guardarConfiguracion(".//config//configActual.xml",app.getSizeLetra(),app.getSizeLetraTraductor(),app.getSizeCadena(),hTerm,hnTerm,hlTerm,hlnTerm,hLeido,hPend,hColorAccSem,tipoLetra,app.getSizeAcciones(),app.getZoomInicial());
         /*
        actualizarEstilos(app.getGrafo().getGraph());
        actualizarEstilos(app.getGramatica().getGraph());
        actualizarEstilos(app.getEntrada().getGraph());
        */
        
        EventosMenu.irInicio(app);      
        app.getGramatica().eliminarGramatica();
        app.getEntrada().eliminarCadena();
         
        actualizarEstilos(app.getGrafo().getGraph());
        actualizarEstilos(app.getGramatica().getGraph());
        actualizarEstilos(app.getEntrada().getGraph());
         
        app.reconstruirGramatica();
        app.getEntrada().reconstruirCadena();
        //app.zoom();
        EventosMenu.irPaso(cont,app);
        
       
    }

    
    
    
/**
 * Obtain the color selected to the string
 * @param leido
 * if is read string or pending string
 * @return action listener
 */
    private  ActionListener elegirColorCadena(boolean leido) {
            
          ActionListener listenerColores = new ActionListener(){
              
            @Override
            public void actionPerformed(ActionEvent e) {
                //miColor = JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
                obtenerColorCadena(leido);
                modificarArbol();
            }

            

            
            
        };
        return listenerColores;
    }
/**
 * Obtain the color selected to the string
 * @param leido 
 * if is read string or pending string
 * 
 */    
      private  void obtenerColorCadena(boolean leido) {
                  Color c=JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
        if(leido){
            
            mT.setColorCadenaLeido(c);
            
        }else{
           
            mT.setColorCadenaPendiente(c);
        }
        }
/**
 * 
 */
    private void construirEjemplo() {
        
        
        List<Object> hijos= new ArrayList();
        Regla regla= new Regla();
        regla.setTam(4);        
        regla.setValor("");
        List<String> cadenaS = new ArrayList<>();
        regla.setValorAtributos(cadenaS);
        arbolEjemplo.insertarRectangulo(regla, 50, posYHijos);
        Object e1=arbolEjemplo.insertarElemento(new Simbolo("num",true), xInicial, posYHijos);
        hijos.add(e1);
        terminales.add(e1);
        Object e2=arbolEjemplo.insertarElemento(new Simbolo("A",false), xFinal, posYHijos);
        hijos.add(e2);
        nTerminales.add(e2);
        arbolEjemplo.insertarPadre(new Simbolo("Exp",false), xPadre,yPadre, hijos);
        nTerminales.add(e2);
        
        List<Simbolo> trad = new ArrayList<>();
        trad.add(new Simbolo("Exp::=",false));
        trad.add(new Simbolo("num",true));
        trad.add(new Simbolo("A",false));        
        List<Object> l=arbolEjemplo.insertarRegla(posRegla, trad);
        Object[] regInsertadas = new Object[l.size()];
        regInsertadas=  l.toArray();
        mT.setTraductor(regInsertadas);
        
        Object leido=arbolEjemplo.insertarElemento(new Simbolo("4",true), 10, posYCadena, 40,40,"LEIDO");
        mT.setLeido(leido);
        Object pend=arbolEjemplo.insertarElemento(new Simbolo("+",true), incCadena, posYCadena,40,40, "PENDIENTE");
        mT.setPendiente(pend);
        
        Object accSem=arbolEjemplo.insertarElemento(new Simbolo("print(valor)",true), incCadena+100, posRegla,40,40, "ACCIONES");
        mT.setAccSem(accSem);
    }
    
   /**
    * modify the example tree of the configuration
    */ 
     public void modificarArbol() {
       
        estilos();
        
        arbolEjemplo.insertarElemento(new Simbolo("num",true), xInicial, posYHijos, "MODTER");
        arbolEjemplo.insertarElemento(new Simbolo("A",false), xFinal, posYHijos, "MODNOTER");
        arbolEjemplo.insertarElemento(new Simbolo("Exp",false), xPadre, yPadre, "MODNOTER");
        
        List<Simbolo> trad = new ArrayList<>();
        trad.add(new Simbolo("Exp::=",false));
        trad.add(new Simbolo("num",true));
        trad.add(new Simbolo("A",false));
        arbolEjemplo.eliminar(mT.getTraductor());
        List<Object> l=arbolEjemplo.insertarRegla(posRegla, trad,"MODTRADTER","MODTRADNOTER");
        Object[] regInsertadas = new Object[l.size()];
        regInsertadas=  l.toArray();
        mT.setTraductor(regInsertadas);
        arbolEjemplo.eliminar(mT.getLeido());
        arbolEjemplo.eliminar(mT.getPendiente());
        
        Object leido=arbolEjemplo.insertarElemento(new Simbolo("4",true), 10, posYCadena, 40,40,"MODLEIDO");
        mT.setLeido(leido);
        Object pend=arbolEjemplo.insertarElemento(new Simbolo("+",true), incCadena, posYCadena,40,40, "MODPENDIENTE");
        mT.setPendiente(pend);
        
        arbolEjemplo.eliminar(mT.getAccSem());
        Object accSem=arbolEjemplo.insertarElemento(new Simbolo("print(valor)",true), incCadena+100, posRegla,40,40, "MODACCIONES");
        mT.setAccSem(accSem);
       
    }
     
     /**
      * initialize the value of the modifications
      * @return temporal modifications
      */
    private  ModificacionesTemp inicializarModificaciones() {
        ModificacionesTemp m= new ModificacionesTemp();
        m.setSizeLetraArbol(app.getSizeLetra());
        m.setSizeLetraTraductor(app.getSizeLetraTraductor());
        m.setSizeCadena(app.getSizeCadena());
        m.setColorTerminales(app.getcTerminales());
        m.setColornTerminales(app.getcNoTerminales());
        m.setColorLetraTerminales(app.getLetraTerminales());
        m.setColorLetranTerminales(app.getLetraNoTerminales());
        m.setColorCadenaLeido(app.getColorCadLeido());
        m.setColorCadenaPendiente(app.getColorCadPendiente());
        m.setColorAccSem(Color.black);
        m.setSizeAcciones(13);
        m.setTipoLetra("Times New Roman");
        return m;
    }
/**
 *set all styles
 */
    private  void estilos() {
        mxStylesheet stylesheet = arbolEjemplo.getGraph().getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR,Integer.toHexString(mT.getColorLetraTerminales().getRGB()));
        style.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraArbol());        
        style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(mT.getColorTerminales().getRGB()));
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylesheet.putCellStyle("MODTER", style);   
        
         
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 100);
        style2.put(mxConstants.STYLE_FONTCOLOR,Integer.toHexString(mT.getColorLetranTerminales().getRGB()));
        style2.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraArbol());        
        style2.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(mT.getColornTerminales().getRGB()));
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylesheet.putCellStyle("MODNOTER", style2);   
        
        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
        style3.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style3.put(mxConstants.STYLE_OPACITY, 0);
        style3.put(mxConstants.STYLE_FONTCOLOR,  Integer.toHexString(mT.getColorLetraTerminales().getRGB()));
        style3.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraTraductor());
        //style3.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcNoTerminales().getRGB()));
        style3.put(mxConstants.STYLE_ROUNDED,"1");
        style3.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        
        //style3.put(mxConstants.STYLE_ENDSIZE,10);
        //style3.put(mxConstants.STYLE_SPACING_RIGHT,10);
        stylesheet.putCellStyle("MODTRADTER", style3);
        
        
        
        Hashtable<String, Object> style4 = new Hashtable<String, Object>();
        style4.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style4.put(mxConstants.STYLE_OPACITY, 0);
        style4.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorLetranTerminales().getRGB()));
        style4.put(mxConstants.STYLE_FONTSIZE, mT.getSizeLetraTraductor());
        style4.put(mxConstants.STYLE_ROUNDED,"1");
        style4.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style4.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(app.getcTerminales().getRGB()));
        
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("MODTRADNOTER", style4);
        
        Hashtable<String, Object> style11 = new Hashtable<String, Object>();
        style11.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style11.put(mxConstants.STYLE_OPACITY, 80);     
        style11.put(mxConstants.STYLE_ROUNDED,"1");
        style11.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style11.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(mT.getColorTerminales().getRGB()));
        style11.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorCadenaLeido().getRGB()));
        style11.put(mxConstants.STYLE_FONTSIZE, mT.getSizeCadena());
        //style.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
       
        stylesheet.putCellStyle("MODLEIDO", style11);
        
        Hashtable<String, Object> style12 = new Hashtable<String, Object>();
        style12.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style12.put(mxConstants.STYLE_OPACITY, 10);
        style12.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        style12.put(mxConstants.STYLE_ROUNDED,"1");
        style12.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style12.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(mT.getColorTerminales().getRGB()));
        style12.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorCadenaPendiente().getRGB()));
        style12.put(mxConstants.STYLE_FONTSIZE, mT.getSizeCadena());
        // style2.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
        stylesheet.putCellStyle("MODPENDIENTE", style12);
        
        Hashtable<String, Object> style6 = new Hashtable<String, Object>();
        style6.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style6.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
        style6.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(mT.getColorAccSem().getRGB())); 
        style6.put(mxConstants.STYLE_FONTSIZE, mT.getSizeAcciones());
        style6.put(mxConstants.STYLE_ROUNDED,"1");
        style6.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style6.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style6.put(mxConstants.STYLE_SPACING_RIGHT,0);
        style6.put(mxConstants.STYLE_ALIGN,"ALIGN_LEFT");       
        style6.put(mxConstants.STYLE_FONTFAMILY,mT.getTipoLetra()); 
         
        stylesheet.putCellStyle("MODACCIONES", style6);

    }
    
    /**
     * add the panel with the components to modify the letter
     */
    private void addModLetra() {        
        JLabel label = new JLabel("    Tamaño fuente en el árbol:");        
        combo.addItem("5");
        combo.addItem("8");        
        combo.addItem("10");
        combo.addItem("13");
        combo.addItem("15");
        combo.addItem("18");
        combo.addItem("20");
        //combo.setSelectedItem(Integer.toString(app.getSizeLetra()));
        combo.setSelectedItem("10");
        combo.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String val = (String) combo.getSelectedItem();
                mT.setSizeLetraArbol(Integer.parseInt(val));                
                modificarArbol();
            }

        });
        JLabel label2 = new JLabel("    Tamaño fuente en el traductor:");        
        
        combo2.addItem("5");
        combo2.addItem("8");
        combo2.addItem("10");
        combo2.addItem("13");
        combo2.addItem("15");
        combo2.addItem("18");
        combo2.addItem("20");
        combo2.setSelectedItem(Integer.toString(app.getSizeLetraTraductor()));
        combo2.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String val = (String) combo2.getSelectedItem();
                mT.setSizeLetraTraductor(Integer.parseInt(val));                
                modificarArbol();
            }

        });
        
        
        JLabel label3 = new JLabel("    Tamaño fuente en la cadena de entrada:");        
        combo3.addItem("5");
        combo3.addItem("8");
        combo3.addItem("10");  
        combo3.addItem("13");  
        combo3.addItem("15");
        combo3.addItem("20");
        combo3.addItem("25");
        combo3.addItem("35");       
        combo3.setSelectedItem(Integer.toString(app.getSizeCadena()));
        combo3.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                String val = (String) combo3.getSelectedItem();
                mT.setSizeCadena(Integer.parseInt(val));                
                modificarArbol();
            }          
        

        });
        
       
        
         //panel para modificar el tamaño de letra
        JPanel panelLetra= new JPanel();        
        panel.add(panelLetra);
        panelLetra.setBorder(BorderFactory.createLineBorder(Color.black));
        panelLetra.setLayout(new GridLayout(4,2));
        JLabel letra=new JLabel("Símbolos");
        panelLetra.add(letra);
        Font fontLetra = new Font(letra.getFont().getName(),Font.BOLD,letra.getFont().getSize()+5);
        letra.setFont(fontLetra);
        panelLetra.add(new JLabel(""));
        panelLetra.add(label);
        JPanel panelTexto= new JPanel();
        panelTexto.add(combo);
        panelLetra.add(panelTexto);
        panelLetra.add(label2);
        JPanel panelTraductor = new JPanel();
        panelTraductor.add(combo2);
        panelLetra.add(panelTraductor);
        
         panelLetra.add(label3);
        JPanel panelCombo = new JPanel();
        panelCombo.add(combo3);
        panelLetra.add(panelCombo);
        
    }
    /**
     * add the panel to choose colors
     */
    public void addModColores(){
        
         JButton colorTerminales = new JButton("Elegir color");
        colorTerminales.addActionListener(elegirColor(true,'f'));
        
        JButton colorNoTerminales = new JButton("Elegir color");       
        colorNoTerminales.addActionListener(elegirColor(false,'f'));
        
        JButton colorLetraTerminales = new JButton("Elegir color");
        colorLetraTerminales.addActionListener(elegirColor(true,'l'));
        
        JButton colorLetraNoTerminales = new JButton("Elegir color");       
        colorLetraNoTerminales.addActionListener(elegirColor(false,'l'));
        
         //panel para modificar los colores
        //JPanel panelColores= new JPanel();        
        //panel.add(panelColores);
        //panelColores.setBorder(BorderFactory.createLineBorder(Color.black));
       // panelColores.setLayout(new GridLayout(3,1));
        
         JPanel panelTit= new JPanel();        
        //panelColores.add(panelTit);
        panelTit.setBorder(BorderFactory.createLineBorder(Color.black));
        panelTit.setLayout(new GridLayout(1,2));
        /*
        JLabel colores = new JLabel("Colores");
        panelTit.add(colores);
        Font fontColores = new Font(colores.getFont().getName(),Font.BOLD,2*colores.getFont().getSize());
        colores.setFont(fontColores);
        panelTit.add(new JLabel(""));
        /
//panel para modificar los colores
*/
        JPanel panelTerminales= new JPanel();        
        panel.add(panelTerminales);
        panelTerminales.setBorder(BorderFactory.createLineBorder(Color.black));
        panelTerminales.setLayout(new GridLayout(3,2));
        
        
        JLabel labelTerminales=new JLabel("Símbolos terminales");
        panelTerminales.add(labelTerminales);
        Font fontTerminales = new Font(labelTerminales.getFont().getName(),Font.BOLD,labelTerminales.getFont().getSize()+5);
        labelTerminales.setFont(fontTerminales);
        panelTerminales.add(new JLabel(""));
        panelTerminales.add(new JLabel("        Color de fondo"));
        JPanel panelcolTerminales= new JPanel();
        panelcolTerminales.add(colorTerminales);
        panelTerminales.add(panelcolTerminales);   
        panelTerminales.add(new JLabel("        Color de fuente"));
        JPanel panelLetraTerminales= new JPanel();
        panelLetraTerminales.add(colorLetraTerminales);
        panelTerminales.add(panelLetraTerminales);  
        
        
        JPanel panelNoTerminales= new JPanel();
        panelNoTerminales.setBorder(BorderFactory.createLineBorder(Color.black));
        panelNoTerminales.setLayout(new GridLayout(3,2));        
        panel.add(panelNoTerminales);
        
       
         JLabel labelNoTerminales=new JLabel("Símbolos no Terminales");
        panelNoTerminales.add(labelNoTerminales);
        Font fontNoTerminales = new Font(labelNoTerminales.getFont().getName(),Font.BOLD,labelNoTerminales.getFont().getSize()+5);
        labelNoTerminales.setFont(fontNoTerminales);
        panelNoTerminales.add(new JLabel(""));
        
        panelNoTerminales.add(new JLabel("        Color de fondo"));
        JPanel panelcolNoTerminales= new JPanel();
        panelcolNoTerminales.add(colorNoTerminales);
        panelNoTerminales.add(panelcolNoTerminales); 
        panelNoTerminales.add(new JLabel("        Color de fuente"));
        JPanel panelLetraNoTerminales= new JPanel();
        panelLetraNoTerminales.add(colorLetraNoTerminales);
        panelNoTerminales.add(panelLetraNoTerminales);
    }
    
    /**
     * add panel with the configuration of the chain
     */
    public void addModCadena(){
         JButton colorCadenaLeido = new JButton("Elegir color");
        colorCadenaLeido.addActionListener(elegirColorCadena(true));
        
        JButton colorCadenaPend = new JButton("Elegir color");       
        colorCadenaPend.addActionListener(elegirColorCadena(false));
        
         //panel para modificar los colores
        JPanel panelCadena= new JPanel();        
        panel.add(panelCadena);
        panelCadena.setBorder(BorderFactory.createLineBorder(Color.black));
        panelCadena.setLayout(new GridLayout(3,2));
        
        JLabel cadena=new JLabel("Cadena de entrada");
         panelCadena.add(cadena);
         Font fontCadena = new Font(cadena.getFont().getName(),Font.BOLD,cadena.getFont().getSize()+5);
        cadena.setFont(fontCadena);
        panelCadena.add(new JLabel(""));
        
        
       
        panelCadena.add(new JLabel("        Parte procesada"));
        JPanel panelCadenaLeido= new JPanel();
        panelCadenaLeido.add(colorCadenaLeido);
        panelCadena.add(panelCadenaLeido);
        
         panelCadena.add(new JLabel("        Parte pendiente"));
        JPanel panelCadenaPend= new JPanel();
        panelCadenaPend.add(colorCadenaPend);
        panelCadena.add(panelCadenaPend);
        
       
    }
    /**
     * add the buttons of accept and cancel
     */
    public void addBotones(){
         //panel para modificar los colores
        JPanel panelBotones= new JPanel();        
        splitPane2.setBottomComponent(panelBotones);
        //panelBotones.setBorder(BorderFactory.createLineBorder(Color.black));
        panelBotones.setLayout(new GridLayout(1,2));
        
        JPanel panelAceptar= new JPanel();
        JButton boton = new JButton("Aceptar");        
        boton.addActionListener(aceptarListener());
        JPanel panelCancelar = new JPanel();
        JButton boton2 = new JButton("Cancelar");
        boton2.addActionListener(cancelarListener());
        
        panelAceptar.setLayout(new BorderLayout());
        panelAceptar.add(boton,BorderLayout.CENTER);
        panelBotones.add(panelAceptar);
        
        panelCancelar.setLayout(new BorderLayout());
        panelCancelar.add(boton2,BorderLayout.CENTER);       
        panelBotones.add(panelCancelar);
    }
/**
 * update the styles
 * @param graph 
 * graph to update
 */
    private void actualizarEstilos(mxGraph graph) {
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
         
        stylesheet.putCellStyle("ACCIONES", style6);
    }
/**
 * 
 * @return combo box of the letter of the tree
 */
    public JComboBox getCombo() {
        return combo;
    }
/**
 * 
 * @param combo 
 * combo box of the letter of the tree
 */
    public void setCombo(JComboBox combo) {
        this.combo = combo;
    }

/**
 * 
 * @return combo box of the letter of the grammar
 */
    public JComboBox getCombo2() {
        return combo2;
    }
/**
 * 
 * @param combo2 
 * combo box of the letter of the grammar
 */
    public void setCombo2(JComboBox combo2) {
        this.combo2 = combo2;
    }
/**
 * 
 * @return combo box of the letter of the string
 */
    public JComboBox getCombo3() {
        return combo3;
    }
/**
 * 
 * @param combo3 
 * combo box of the letter of the string
 */
    public void setCombo3(JComboBox combo3) {
        this.combo3 = combo3;
    }
/**
 * 
 * @return combo box of the type of letter of the semantic actions
 */
    public JComboBox getCombo4() {
        return combo4;
    }
/**
 * 
 * @param combo4 
 * combo box of the type of letter of the semantic actions
 */
    public void setCombo4(JComboBox combo4) {
        this.combo4 = combo4;
    }
/**
 * 
 * @return list of terminals
 */
    public List<Object> getTerminales() {
        return terminales;
    }
/**
 * 
 * @param terminales 
 * list of terminals
 */
    public void setTerminales(List<Object> terminales) {
        this.terminales = terminales;
    }
/**
 * 
 * @return list of no terminals
 */
    public List<Object> getnTerminales() {
        return nTerminales;
    }
/**
 * 
 * @param nTerminales
 * list of no terminals
 */
    public void setnTerminales(List<Object> nTerminales) {
        this.nTerminales = nTerminales;
    }
/**
 * 
 * @return the example tree
 */
    public Grafo getArbolEjemplo() {
        return arbolEjemplo;
    }
/**
 * 
 * @param arbolEjemplo 
 * the example tree
 */
    public void setArbolEjemplo(Grafo arbolEjemplo) {
        this.arbolEjemplo = arbolEjemplo;
    }
/**
 * 
 * @return temporal modifications  
 */
    public ModificacionesTemp getmT() {
        return mT;
    }
/**
 * 
 * @param mT 
 *  temporal modifications 
 */
    public void setmT(ModificacionesTemp mT) {
        this.mT = mT;
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
 * @return 
 */
    public JPanel getPanel() {
        return panel;
    }
/**
 * 
 * @param panel 
 */
    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public JFrame getVentana() {
        return ventana;
    }

    public void setVentana(JFrame ventana) {
        this.ventana = ventana;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    public JSplitPane getSplitPane2() {
        return splitPane2;
    }

    public void setSplitPane2(JSplitPane splitPane2) {
        this.splitPane2 = splitPane2;
    }
    
    
}
