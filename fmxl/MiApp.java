package fmxl;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

public class MiApp  extends JFrame 
{
	
	/**
	 * 
	 */
         /*
         constantes arbol
    */
       
         static final int separacionNodosDefecto=75;
         static final int separacionTerminalesDefecto=85; 
         public static final int sepRegla=40;
         int posXRaizDefecto=300;
         
        
          int separacionNodos=75;
          int posInicialDefecto=80;
          int posInicial=posInicialDefecto;
          int separacionTerminales=85; //La separación que vamos a poner en el arbol entre los terminales
          int posXRaiz;
         
         /*
         fin constantes arbol
         */
         private String tipoTraductor;
       
       private FicheroXML ficheroXml;
       private MiMenu menu;
       private List<Regla> miGramatica;
       private List<String> cadena;//lista con los elementos de la cadena (texto)
       private List<Informacion> listaPasos;
       private List<Hijos> conjuntoDeHijos; //grupos de listas
       /*
       JEditorPane entrada;//label con la cadena de entrada
       */
       CadenaEntrada entrada;
	private static final long serialVersionUID = -2764911804288120883L;
        int posicionTerm=0;
       boolean primerElem= true; //booleano para saber si estamos insertando el primer nodo del árbol
    
       
        GridBagConstraints constraints;
        Grafo grafo;
        Grafo gramatica;
        List<List<Object>> listaReglas= new ArrayList<>();//lista de las reglas insertadas en el traductor
        List<Object> lista = new ArrayList<>();//elementos del árbol
        List<Integer> listaPosiciones = new ArrayList<>();
        List<Object> hijos = new ArrayList<>();
        List<Object> rectangulos= new ArrayList<>();
         Map<Integer, Simbolo> mapa ;//empareja id con el símbolo del árbol
         Map<String,Regla> mapaGramatica = new HashMap<>();//empareja id del xml con la regla
         Map<Object,String> elemValor=new HashMap<>();//empareja el nodo con su valor (para tooltip)
         int numNodos;
        int contador=0;
        Map<Object,List<Object>> rectangPila= new HashMap<>();//para los traductores descendentes emparejamos los elementos q están en la pila con su rectángulo correspondiente
       Map<Object,Object> padreRect= new HashMap<>();//
       Map<Integer,String> valorToolTipAntiguo= new HashMap<>();
       Map<Object,List<String>> rectangAtribAcc = new HashMap<>();
      
               
       
       /*
       Creamos los splits para insertar los distintos elementos
       */
       JSplitPane splitPane = new JSplitPane();
       JSplitPane splitPane2 = new JSplitPane();
       JSplitPane splitPane3= new JSplitPane();
       JPanel panelArbol = new JPanel();
       
       JPanel panelBotones= new JPanel();
       
       JPanel panelZoom = new JPanel();
       GridLayout layout = new GridLayout(1,4,1,1);
       GridLayout layout2 = new GridLayout(1,3,1,1);
       /*
       valores del porcentaje de los paneles
       */
       double porArbol;
       double porTraductor;
       double porCadena;
       int sizeLetra;
       int sizeLetraTraductor;
       int sizeCadena;
       int sizeAcciones;
       
       //Valor inicial del zoom
        int zoomInicial;
        JSlider miZoom;
       
       /*
       colores
       */
       Color cTerminales;               
        Color cNoTerminales;
        Color letraTerminales;
        Color letraNoTerminales;
        Color colorCadLeido;
        Color colorCadPendiente;
        private Color colorAccSem;
        private String tipoLetra;
	/**
         * initialite the app
         * @param l
         * translate in xml
         * @param ct
         * color of terminals
         * @param cnt
         * color of no terminals
         * @param clt
         * color of letter of the terminals
         * @param clnt
         * color of letter of the no terminals
         * @param cl
         * color of the read terminals
         * @param cp
         * color of the pending terminals
         * @param sizeLetra
         * size of letter of the tree
         * @param sizeLetraTraductor
         * size of the letter of the grammar
         * @param sizeCadena
         * size of the letter of the string
         * @param cAs
         * color of semantics actions
         * @param tl
         * 
         * @param sizeAcciones
         * size of semantics actions
         * @param zoomInicial 
         * initial zoom
         */		
	public MiApp(FicheroXML l,Color ct,Color cnt,Color clt, Color clnt,Color cl, Color cp,int sizeLetra, int sizeLetraTraductor,int sizeCadena,
                Color cAs,String tl, int sizeAcciones,int zoomInicial)
	{
		
                super("Depurador TDS "+l.getRuta());
                this.setFocusable(true);
               this.setResizable(true);
                this.tipoTraductor=l.getTipoTraductor();
                this.setExtendedState(MAXIMIZED_BOTH);
                Toolkit t = Toolkit.getDefaultToolkit();
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                //this.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2)); 
                
                
                panelArbol.setLayout(new BorderLayout());
                /*
                División de la pantalla en 2                */
                
                splitPane.setResizeWeight(0.5);
                splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
                this.getContentPane().add(splitPane, BorderLayout.CENTER);
                splitPane.setBackground(Color.WHITE);
                splitPane.setLeftComponent(panelArbol);
               
                
                
                splitPane2.setResizeWeight(0.7);
                splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
                splitPane2.setBackground(Color.WHITE);
                splitPane.setRightComponent(splitPane2);
                splitPane3.setResizeWeight(0.9);                
                splitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
                
                panelBotones.setLayout(layout);
                splitPane3.setBottomComponent(panelBotones);
                splitPane2.setBottomComponent(splitPane3);
                
               
                
                
                lista= new ArrayList<>();
                hijos=new ArrayList<>();
                this.conjuntoDeHijos=new ArrayList<>();
                
                this.ficheroXml=l;
                //colores
                this.cTerminales=ct;
                this.cNoTerminales=cnt;
                this.letraTerminales=clt;
                this.letraNoTerminales=clnt;
                this.colorCadLeido=cl;
                this.colorCadPendiente=cp;
                this.sizeLetra=sizeLetra;
                this.sizeLetraTraductor=sizeLetraTraductor;
                this.sizeCadena=sizeCadena;
                this.colorAccSem=cAs;
                this.tipoLetra=tl;
                this.sizeAcciones=sizeAcciones;
                this.zoomInicial=zoomInicial;
                
                /*
                LLamamos a a clase MiMenu para crear la estructura del menú de la aplicación
                posteriormente insertamos la barra creada en nuestra ventana
                */
               
                this.menu= new MiMenu(this);
                this.menu.insertarMenu();
                
		this.setJMenuBar(menu.getBarraMenu());
                
                /*
                Almacenamos la gramática 
                */
                this.miGramatica=l.getListaGramatica();
                this.cadena=l.getCadena();
		
		/*almacenamos la información de cada paso*/
                this.listaPasos=l.getListaPasos();
                //Mapa con todos los símbolos del árbol
                mapa=l.getMapa();
                //Numero de nodos del árbol
                numNodos=l.getNumNodos(); 
                
                
               
                this.inicializarArbol();
                
                this.colocarArbol();                
                
		//          
                        
                 //PARTE DE GRAMÁTICA
                
                this.construirGramatica();
                this.colocarGramatica();
                
                //this.grafo.setApp(this);
                
               
                    //PARTE DE CADENA DE ENTRADA
                    entrada= new CadenaEntrada(this);
                    
                    List<String> auxLista;
                    String[] cadenaPend=cadena.get(0).split("pend");//eliminamos pend de la cadena
                    auxLista=Arrays.asList(cadenaPend[1].split(" "));
                    
                    entrada.construirCadena(auxLista);
                    entrada.activarListener();
                    
                
                this.colocarEntrada();
                
               //colocamos botones y zoom
               
               
                final int FPS_MIN = 1;
                 final int FPS_MAX = 24;
                 final int FPS_INIT = 6;    //initial frames per second
                miZoom = new JSlider(JSlider.HORIZONTAL,
                                                      FPS_MIN, FPS_MAX, FPS_INIT);
                
                //Turn on labels at major tick marks.
                miZoom.setMajorTickSpacing(1);
                //miZoom.setMinorTickSpacing(1);
                miZoom.setPaintTicks(true);
                miZoom.setPaintLabels(true);
                
                miZoom.setUI(new MetalSliderUI() {
                    protected void scrollDueToClickInTrack(int direction) {
                        // this is the default behaviour, let's comment that out
                        //scrollByBlock(direction);

                        int value = miZoom.getValue(); 

                        if (miZoom.getOrientation() == JSlider.HORIZONTAL) {
                            value = this.valueForXPosition(miZoom.getMousePosition().x);
                        } else if (miZoom.getOrientation() == JSlider.VERTICAL) {
                            value = this.valueForYPosition(miZoom.getMousePosition().y);
                        }
                        miZoom.setValue(value);
                    }
                });
                
                                //Create the label table
                Hashtable labelTable = new Hashtable();
                //labelTable.put( new Integer( 1 ), new JLabel("10%") );
                //labelTable.put( new Integer( 3 ), new JLabel("80%") );
                labelTable.put( new Integer( 8 ), new JLabel("100%") );
                labelTable.put( new Integer(16), new JLabel("200%") );
                labelTable.put( new Integer( FPS_MIN), new JLabel("10%") );
                labelTable.put( new Integer( FPS_MAX ), new JLabel("300%") );
                miZoom.setLabelTable( labelTable );
                
                miZoom.setPaintTicks(true);
                miZoom.setValue(zoomInicial);
                miZoom.addChangeListener(new ChangeListener(){
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        
                            
                             
                             if(!miZoom.getValueIsAdjusting()){
                                 System.out.println("Zoom cambiado"+miZoom.getValue());                             
                                    setZoomInicial(miZoom.getValue());
                                    zoom();
                                 guardarZoom();
                            }
                        
                    }
                    
                });
                
              
              this.panelZoom.setLayout(this.layout2);
               JPanel etiq= new JPanel();
               JLabel label= new JLabel("Zoom");
               etiq.add(label);
               this.panelZoom.add(etiq);
               
                
               this.panelZoom.add(miZoom);
                
                JPanel panelMasMenos = new JPanel();
                JButton mas = new JButton("+");
                mas.addActionListener(modifZoom("+"));
                JButton menos = new JButton("-");
                menos.addActionListener(modifZoom("-"));
               
                panelMasMenos.add(menos);
                panelMasMenos.add(mas);
                
                panelZoom.add(panelMasMenos);
                
               
              //splitPane4.setBottomComponent(panelZoom);
                panelArbol.add(panelZoom,BorderLayout.SOUTH);
               
                 
                  
                Boton b3=new Boton("Inicio");//creating instance of JButton 
                b3.setG(this);
                this.colocarBoton(b3);
                
                 
                Boton b2=new Boton("Anterior");//creating instance of JButton 
                b2.setG(this);
                this.colocarBoton(b2);
                
                 Boton b=new Boton("Siguiente");//creating instance of JButton  
                b.setG(this);
                this.colocarBoton(b);
               
                 Boton b4=new Boton("Fin");//creating instance of JButton  
                b4.setG(this);
                this.colocarBoton(b4);
                
                
                
              this.zoom();
              
                
	}
        
        /**
         * Calc of the position of the tree node to insert  
         * @param info
         * step to make
         * @return position of tree to put the node
         */
        public int calculoPosicion(Informacion info){
            
            int pos;
            String[] hijos = info.getElemento().split(" "); 
            if((info.getTipo().equals("desplazamiento")) || (info.getTipo().equals("despDes"))){
                posicionTerm=posicionTerm+separacionTerminales;
                return posicionTerm;
            }
            else if(info.getTipo().equals("derivacion")){
                return posicionTerm;
            }
            else{
                float media = (float) hijos.length/2;  
                    int mediaEntera=(int) media;
                    if(media != mediaEntera){
                        //hacemos la media de la posición de los 2 hijos intermedios para colocar allí al padre
                        int aux= (listaPosiciones.get(Integer.parseInt(hijos[mediaEntera]))+listaPosiciones.get(Integer.parseInt(hijos[mediaEntera+1])));
                        pos=aux/2;
                    }
                    else{
                        pos=listaPosiciones.get(Integer.parseInt(hijos[mediaEntera]));
                    }
                    return pos;
            }
        }
      
        /**
         * Add the element to this step of tree
         * @return the tree in form of graph
         */
        public Grafo construirArbol(){            
            
           if(contador<this.ficheroXml.getNumNodos() ){
            Informacion info=listaPasos.get(contador);
           //Obtenemos el elemento a insertar en el árbol, y el id de sus hijos
           //en caso de ser una reducción
            String[] arrayInfo = info.getElemento().split(" "); 
            //obtenemos la posición del elemento a insertar
            //String[] arrayPosicion = info.getPosicion().split(" ");
            //posición Y del nodo en función de su nivel            
            int nivel=mapa.get(info.getId()).getNivel();
            boolean terminal= mapa.get(info.getId()).isTerminal();
            
            int posicionY= separacionNodos*nivel+posInicial/2;
            int posXActual=0;
            
            Object r=null;
            Object padre;
            if((info.getTipo().equals("primero")) || (this.contador==0)){
                primerElem=true;
            }
            
            if(primerElem==true)
                primerElem=false;
            else
                posXActual=calculoPosicion(info);
            if((info.getRegla()!=null) ){
                
                //Conector regla= new Conector(info.getNuevaRegla(),info.getTamRegla());
                //caluclamos la posicion x del nodo actual
                //distinguir entre descendente y ascendente
                //String traductor=clasificar(info.getTipo());//saber si es un traductor ascendente o descendente
                //Object r=grafo.insertarRectangulo(info.getRegla(),posXActual,posicionY,traductor);
                r=grafo.insertarRectangulo(info.getRegla(),posXActual,posicionY);
                this.rectangAtribAcc.put(r, info.getRegla().getValorAtributos());
               
                
                rectangulos.add(r);
            }
           if(info.getTipo().equals("primero")){
                 Object nodo = grafo.insertarElemento(new Simbolo(arrayInfo[0],terminal), posXRaiz, posInicial);           
                lista.add(nodo); 
                hijos.add(lista.get(lista.size()-1)); 
                this.elemValor.put(nodo, info.getValor());
           }
            else if(info.getTipo().equals("reduccion")){                
                //lista.add(grafo.insertarPadre(new Simbolo(arrayInfo[0],false), Integer.parseInt(arrayPosicion[0]),Integer.parseInt(arrayPosicion[1]), arrayInfo,lista));
                Object nodo=grafo.insertarPadre(new Simbolo(arrayInfo[0],terminal),posXActual,posicionY, arrayInfo,lista);
                lista.add(nodo);
                this.elemValor.put(nodo, info.getValor());
                //listaPosiciones.add(listaPosiciones.get(Integer.parseInt(arrayInfo[1])));
                //lista.add(grafo.insertarPadre(new Simbolo(arrayInfo[0],false), Integer.parseInt(arrayPosicion[0]), Integer.parseInt(arrayPosicion[1]),hijos.subList(Integer.parseInt(arrayInfo[1]),Integer.parseInt(arrayInfo[2]))));
                //List<Object> subHijos=hijos.subList(Integer.parseInt(arrayInfo[1]),Integer.parseInt(arrayInfo[2]));
                //Hijos grupoDeHijos = new Hijos(subHijos);
                //this.conjuntoDeHijos.add(grupoDeHijos);
                //hijos.removeAll(subHijos);
                //hijos.add(lista.get(lista.size()-1)); 
                
                
            }
            else if(info.getTipo().equals("derivacion")){
                //Object nodo=grafo.insertarPadre(new Simbolo(arrayInfo[0],false),posXActual,posicionY, arrayInfo,lista);
                //posicionY=posicionY+75;
                Simbolo simboloAux=new Simbolo(arrayInfo[0],terminal);
                padre=lista.get(Integer.parseInt(arrayInfo[1]));
                Object nodo=grafo.insertarHijo(padre,posXActual,posicionY, simboloAux);
                lista.add(nodo);
                this.elemValor.put(nodo, info.getValor());
                this.padreRect.put(padre,r);
                 
             }
             else if(info.getTipo().equals("despDes")){
                 Simbolo simboloAux=new Simbolo(arrayInfo[0],terminal);
                 padre=lista.get(Integer.parseInt(arrayInfo[1]));
                Object nodo=grafo.insertarHijo(padre,posXActual,posicionY,simboloAux);
                lista.add(nodo);
                this.elemValor.put(nodo, info.getValor());
                sacarPila(padre,posXActual);
                 
             }
             else{
                 //posicionTerm=posicionTerm+80;
                //Object aux = grafo.insertarElemento(new Simbolo(arrayInfo[0],true), Integer.parseInt(arrayPosicion[0]), Integer.parseInt(arrayPosicion[1]));
                Object nodo = grafo.insertarElemento(new Simbolo(arrayInfo[0],true), posicionTerm, posicionY); 
               
                lista.add(nodo); 
                hijos.add(lista.get(lista.size()-1)); 
                this.elemValor.put(nodo, info.getValor());
            
            }
           actualizarAtribuos(info);
            listaPosiciones.add(posXActual);
            
            contador++;
                         

            entrada.actualizar(cadena.get(contador));
                         
            //grafo.terminarActualizacion();   
           }
            return grafo;
        }
       
        /**
         * Remove the nodes whem press the botton before
         * @return  graph without the last node
         */
         public  Grafo eliminar(){            
            //Object r1=grafo.insertarRectangulo(regla1,5,300);
            //rectangulos.add(r1);
            
            Object padre;
            if(contador>0){
                contador--;              
               Informacion info=listaPasos.get(contador);
                String[] arrayInfo = info.getElemento().split(" ");
               Object e= lista.get(contador);               
               grafo.eliminar(e);                        
               lista.remove(e); 
               hijos.remove(e);              
               listaPosiciones.remove(contador);
               
                if(info.getTipo().equals("desplazamiento") || info.getTipo().equals("despDes")){  
                    if(posicionTerm>0)
                        posicionTerm=posicionTerm-separacionTerminales;      
                }
               
              if(info.getTipo().equals("despDes")){
                  padre=lista.get(Integer.parseInt(arrayInfo[1]));
                  addPila(padre,arrayInfo[0]);
                  
              }
            
               
               
                
                 if(info.getRegla()!=null){
                    Object rect = rectangulos.get(rectangulos.size()-1);
                    this.eliminarPila(rect);
                    rectangulos.remove(rect);
                    grafo.rectPos.remove(rect);
                    grafo.rectangRegla.remove(rect);
                    
                     grafo.eliminar(rect);
                     
                     
                 }

                entrada.actualizar(cadena.get(contador));
                  if(info.getSimbolosActualizados()!=null){
                      borrarAtributos(info);
                  }   
            }    
            //grafo.terminarActualizacion();
            return grafo; 
         }
          
        /**
         * initialize the tree
         */
        public void inicializarArbol(){
        //Árbol
            this.lista= new ArrayList<>();    
            //List<Object> aux = new ArrayList<>();     
             this.grafo= new Grafo(this);   
             grafo.activarListener();


        }
        /**
         * place the tree in the interface
         */
        public void colocarArbol(){
            
            //this.grafo.insertarTitulo("Árbol:");
           
           this.grafo.getGraphComponent().setPreferredSize(new Dimension(200,800));
           //splitPane4.setTopComponent(this.grafo.getGraphComponent());
           panelArbol.add(this.grafo.getGraphComponent());
        }
        /**
         * build the grammar 
         */
        public void construirGramatica(){            
           int posY=65;

            List<Object> reglaActual;
            gramatica=new Grafo(this);
            //gramatica.insertarTitulo("Traductor");
            for(Regla i: miGramatica){

                reglaActual=gramatica.insertarRegla(posY, i.getRegla());
                posY=posY+sepRegla;
                 mapaGramatica.put(i.getId(), i);
                 this.listaReglas.add(reglaActual);
            }

                //gramatica.activarListener();
                
        }
        /*volver 
        a insertar la gramática tras modificaciones de estilos
        */
        /**
         *update the grammar after the style change 
         */
        public void reconstruirGramatica(){
            int posY=65;
               
                List<Object> reglaActual;
                mapaGramatica= new HashMap<>();
             
                //gramatica.insertarTitulo("Traductor");
                for(Regla i: miGramatica){
                    i.setAccionesInsertadas(false);
                    reglaActual=gramatica.insertarRegla(posY, i.getRegla());
                    posY=posY+sepRegla;                    
                     mapaGramatica.put(i.getId(), i);
                     this.listaReglas.add(reglaActual);
                }
        }
       /**
        * place the grammar in the interface
        */ 
        public void colocarGramatica(){
        gramatica.getGraphComponent().getViewport().setOpaque(true);
        gramatica.getGraphComponent().getViewport().setBackground(Color.WHITE); 
           splitPane2.setTopComponent(this.gramatica.getGraphComponent());
        }
        /**
         * place the string in the interface
         */
        public void colocarEntrada(){
             this.entrada.getGraphComponent().getViewport().setOpaque(true);
            this.entrada.getGraphComponent().getViewport().setBackground(Color.WHITE); 
            //this.entrada.insertarTitulo("Cadena de entrada");
            splitPane3.setTopComponent(this.entrada.getGraphComponent());
            
        }
        /**
         * 
         * @param s 
         * boton to place
         */
        public void colocarBoton(Boton s){
            this.panelBotones.add(s.getBoton());            
                      
        }
/**
 * 
 * @return the grammar 
 */
    public Grafo getGramatica() {
        return gramatica;
    }
/**
 * 
 * @param gramatica
 * the grammar
 */
    public void setGramatica(Grafo gramatica) {
        this.gramatica = gramatica;
    }
/**
 * 
 * @return the map with the association id rule
 */
    public Map<String, Regla> getMapaGramatica() {
        return mapaGramatica;
    }
/**
 * 
 * @param mapaGramatica 
 * the map with the association id rule
 */
    public void setMapaGramatica(Map<String, Regla> mapaGramatica) {
        this.mapaGramatica = mapaGramatica;
    }
/**
 * 
 * @return number of nodes 
 */
    public int getNumNodos() {
        return numNodos;
    }
/**
 * 
 * @param numNodos 
 * number of nodes
 */
    public void setNumNodos(int numNodos) {
        this.numNodos = numNodos;
    }
/**
 * 
 * @return map with the association step value
 */
    public Map<Object, String> getElemValor() {
        return elemValor;
    }
/**
 * 
 * @param elemValor 
 * map with the association step value
 */
    public void setElemValor(Map<Object, String> elemValor) {
        this.elemValor = elemValor;
    }
/**
 * 
 * @return the xml with the translate 
 */
    public FicheroXML getFicheroXml() {
        return ficheroXml;
    }
/**
 * 
 * @param ficheroXml 
 * the xml with the translate 
 */
    public void setFicheroXml(FicheroXML ficheroXml) {
        this.ficheroXml = ficheroXml;
    }
/**
 * classify the traductor in ascendant/descendant
 * @param tipo
 * type of step
 * @return ascendant, descendant or unknown
 */
    private String clasificar(String tipo) {
        if(tipo.equals("derivacion") || tipo.equals("despDes")){
            return "descendente";
        }else if(tipo.equals("reduccion") || tipo.equals("desplazamiento")){
            return "ascendente";
            
        }
        else
            return "desconocido";
    }
/**
 * 
 * @return list of steps
 */
    public List<Informacion> getListaPasos() {
        return listaPasos;
    }
/**
 * 
 * @param listaPasos 
 * list of steps
 */
    public void setListaPasos(List<Informacion> listaPasos) {
        this.listaPasos = listaPasos;
    }
/**
 * 
 * @return steps executed
 */
    public int getContador() {
        return contador;
    }
/**
 * 
 * @param contador
 * steps executed
 */
    public void setContador(int contador) {
        this.contador = contador;
    }
/**
 * 
 * @return the type of translate
 */
    public String getTipoTraductor() {
        return tipoTraductor;
    }
/**
 * 
 * @param tipoTraductor 
 * the type of translate
 */
    public void setTipoTraductor(String tipoTraductor) {
        this.tipoTraductor = tipoTraductor;
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
 * 
 * @param rect 
 * rectangle of node of the tree
 */
    public void eliminarPila(Object rect){
        if(this.tipoTraductor.equals("Descendente")){
                        List<Object> elemPila=this.rectangPila.get(rect);                        
                        for(Object el:elemPila){
                            grafo.eliminar(el);

                        }
                        this.rectangPila.remove(rect);
                         
                    }
    }
 /**
  * unstack the elemnt in the stack
  * @param padre
  * rectangle parent
  * @param pos 
  * pos of the parent
  */  
    public void sacarPila(Object padre, int pos){
        Object rect=this.padreRect.get(padre);
        List<Object> pila=this.rectangPila.get(rect);
        int contador=0;
        if(pila.size()>0){
            Object elemElim=pila.get(0);//elemento a eliminar
            grafo.eliminar(elemElim);
            pila.remove(elemElim);
            //trasladar el resto de elementos
            for(Object ob: pila){
                
                    grafo.trasladarElemPila(ob,pos);
                contador++;
            }
        }
        
    }
 /**
  * Stack an element in the stack
  * @param padre
  * rectangle parent
  * @param s 
  * element to add 
  */   
    public void addPila(Object padre,String s){
        Object rect=this.padreRect.get(padre);
        List<Object> pila=this.rectangPila.get(rect);
        int[]pos = new int[2];
        List<Integer> p=this.grafo.getRectPos().get(rect);//obtenemos la posicion del rectangulo donde vamos a añadir 
        //pos[0]=(p.get(0))-pila.size()*grafo.espacioPila;
        pos[0]=this.posicionTerm + grafo.anchoNodo;
        pos[1]=p.get(1);
        Object o=grafo.insertarElemPila(s,pos[0],pos[1]);       
        pila.add(0, o);
        
    }
/**
 * 
 * @return the list of rules 
 */
    public List<List<Object>> getListaReglas() {
        return listaReglas;
    }
/**
 * 
 * @param listaReglas 
 * the list of rules 
 */
    public void setListaReglas(List<List<Object>> listaReglas) {
        this.listaReglas = listaReglas;
    }
/**
 * 
 * @return position of the terminal
 */
    public int getPosicionTerm() {
        return posicionTerm;
    }
/**
 * 
 * @param posicionTerm 
 * position of the terminal
 */
    public void setPosicionTerm(int posicionTerm) {
        this.posicionTerm = posicionTerm;
    }
    /**
     * apply the zoom
     */
    public void zoom(){
        int contAct=this.contador;
        EventosMenu.irInicio(this);
        int vInicial=1;
        int valorIni=zoomInicial-vInicial;//valor de la diferecia entre el defecto y la pos con signo
        double valor=(Math.abs(valorIni)+1);       
            if(valor==1 || valor==2)
             posInicial=0;
            else
                posInicial=posInicialDefecto;
       
            valor=valor/8;
            
        
        this.grafo.setAltoNodo((int) (Grafo.altoNodoDefecto*valor));
        this.grafo.setAnchoNodo((int) (Grafo.anchoNodoDefecto*valor));
        this.grafo.setAltoPila((int) (Grafo.altoPilaDefecto*valor));
        this.grafo.setAnchoPila((int) (Grafo.anchoPilaDefecto*valor));
        this.grafo.setEspacioPila((int) (Grafo.espacioPilaDefecto*valor));//espacio entre elementos pila        i
        this.separacionNodos=(int) (this.separacionNodosDefecto*valor);
        if(separacionNodos<5)
            separacionNodos=5;
        
        this.separacionTerminales=(int) (this.separacionTerminalesDefecto*valor);
        if(separacionTerminales<5)
            separacionTerminales=5;
        if(valor>=1)
            this.sizeLetra= (int) ((int) 10*valor);
        else
            this.sizeLetra= (int) ((int) 15*valor);
        actualizarEstilos();
        this.posXRaiz=(int) (this.posXRaizDefecto*valor); 
        EventosMenu.irPaso(contAct, this);
        menu.getBarraMenu().requestFocusInWindow();
       
                
    }
/**
 * 
 * @return color of the terminal
 */
    public Color getcTerminales() {
        return cTerminales;
    }
/**
 * 
 * @param cTerminales 
 * color of the terminal
 */
    public void setcTerminales(Color cTerminales) {
        this.cTerminales = cTerminales;
    }
/**
 * 
 * @return  color of the no terminal
 */
    public Color getcNoTerminales() {
        return cNoTerminales;
    }
/**
 * 
 * @param cNoTerminales 
 * color of the no terminal
 */
    public void setcNoTerminales(Color cNoTerminales) {
        this.cNoTerminales = cNoTerminales;
    }
/**
 * 
 * @return size of the letter of the tree
 */
    public int getSizeLetra() {
        return sizeLetra;
    }
/**
 * 
 * @param sizeLetra 
 * size of the letter of the tree
 */
    public void setSizeLetra(int sizeLetra) {
        this.sizeLetra = sizeLetra;
    }
/**
 * 
 * @return size of the letter of the grammar 
 */
    public int getSizeLetraTraductor() {
        return sizeLetraTraductor;
    }
/**
 * 
 * @param sizeLetraTraductor 
 * size of the letter of the grammar 
 */
    public void setSizeLetraTraductor(int sizeLetraTraductor) {
        this.sizeLetraTraductor = sizeLetraTraductor;
    }
/**
 * 
 * @return size of the letter of the string
 */
    public int getSizeCadena() {
        return sizeCadena;
    }
/**
 * 
 * @param sizeCadena 
 * size of the letter of the string
 */
    public void setSizeCadena(int sizeCadena) {
        this.sizeCadena = sizeCadena;
    }
/**
 * 
 * @return initial zoom
 */
    public int getZoomInicial() {
        return zoomInicial;
    }
/**
 * 
 * @param zoomInicial
 * initial zoom
 */
    public void setZoomInicial(int zoomInicial) {
        this.zoomInicial = zoomInicial;
    }
/**
 * 
 * @return color of the read string
 */
    public Color getColorCadLeido() {
        return colorCadLeido;
    }
/**
 * 
 * @param colorCadLeido 
 * color of the read string
 */
    public void setColorCadLeido(Color colorCadLeido) {
        this.colorCadLeido = colorCadLeido;
    }
/**
 * 
 * @return color of the pending string
 */
    public Color getColorCadPendiente() {
        return colorCadPendiente;
    }
/**
 * 
 * @param colorCadPendiente 
 * color of the pending string
 */
    public void setColorCadPendiente(Color colorCadPendiente) {
        this.colorCadPendiente = colorCadPendiente;
    }
/**
 * 
 * @return the panel of the tree
 */
    public JPanel getPanelArbol() {
        return panelArbol;
    }
/**
 * 
 * @param panelArbol 
 * the panel of the tree
 */
    public void setPanelArbol(JPanel panelArbol) {
        this.panelArbol = panelArbol;
    }
/**
 * Update the zoom
 * @param s
 * zoom
 * @return action listener
 */
    private ActionListener modifZoom(String s) {
        ActionListener al = new ActionListener(){
       

            @Override
            public void actionPerformed(ActionEvent e) {
               if(s.equals("+")){
                   if(zoomInicial<24)
                        ++zoomInicial;
                    
                }else{
                   if (zoomInicial>1)
                    --zoomInicial;
                }
               miZoom.setValue(zoomInicial);
               zoom();
            }
        };
        return al;
    }
/**
 * 
 * @return color of thre letter of the terminals
 */
    public Color getLetraTerminales() {
        return letraTerminales;
    }
/**
 * 
 * @param letraTerminales 
 *  color of the letter of the terminals
 */
    public void setLetraTerminales(Color letraTerminales) {
        this.letraTerminales = letraTerminales;
    }
/**
 * 
 * @return color of the letter of the no terminals
 */
    public Color getLetraNoTerminales() {
        return letraNoTerminales;
    }
/**
 * 
 * @param letraNoTerminales 
 * color of the letter of the no terminals
 */
    public void setLetraNoTerminales(Color letraNoTerminales) {
        this.letraNoTerminales = letraNoTerminales;
    }
/**
 * 
 * @return the menu
 */
    public MiMenu getMenu() {
        return menu;
    }
/**
 * 
 * @param menu 
 * the menu
 */
    public void setMenu(MiMenu menu) {
        this.menu = menu;
    }
/**
 * 
 * @return type of the letter
 */
    public String getTipoLetra() {
        return tipoLetra;
    }
/**
 * 
 * @param tipoLetra 
 * type of letter
 */
    public void setTipoLetra(String tipoLetra) {
        this.tipoLetra = tipoLetra;
    }
/**
 * 
 * @return color of the semantics actions
 */
    public Color getColorAccSem() {
        return colorAccSem;
    }
/**
 * 
 * @param colorAccSem
 * color of the semantics actions
 */
    public void setColorAccSem(Color colorAccSem) {
        this.colorAccSem = colorAccSem;
    }
    
    /**
     * Update the styles
     */
    public void actualizarEstilos() {
        mxStylesheet stylesheet = grafo.getGraph().getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(this.getLetraNoTerminales().getRGB()));
        style.put(mxConstants.STYLE_FONTSIZE, this.getSizeLetra());
        //style.put(mxConstants.STYLE_FILLCOLOR,"#82EB20");
        style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(this.getcNoTerminales().getRGB()));
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylesheet.putCellStyle("VERDE", style);
         
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 100);
        style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(this.getLetraTerminales().getRGB()));
        style2.put(mxConstants.STYLE_FONTSIZE, this.getSizeLetra());
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(this.getcTerminales().getRGB()));
        stylesheet.putCellStyle("ROJO", style2);
        
    }
/**
 * save the configuration
 * @return action listener
 */
    private ActionListener guardarZoom() {
        MiApp aux = this;
        //Obtenemos el valor hexadecimal de los colores actuales
        int nTerm=aux.getcNoTerminales().getRGB();
        int term=aux.getcTerminales().getRGB();
        String hnTerm=Integer.toString(nTerm,16);
        String hTerm=Integer.toString(term,16);
        
        //de la letra
        int lnterm=aux.getLetraNoTerminales().getRGB();
        int lterm=aux.getLetraTerminales().getRGB();
        String hlnTerm=Integer.toString(lnterm,16);
        String hlTerm=Integer.toString(lterm,16);
        
        int nCadenaLeido=aux.getColorCadLeido().getRGB();
        int nCadenaPend=aux.getColorCadPendiente().getRGB();
        String hLeido=Integer.toString(nCadenaLeido,16);
        String hPend=Integer.toString(nCadenaPend,16);
        
        int colorAccSem=aux.getColorAccSem().getRGB();
         String hColorAccSem=Integer.toString(colorAccSem,16);
        ActionListener a = new ActionListener(){        
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Configuracion conf = new Configuracion();
                 conf.guardarConfiguracion(".//config//configActual.xml",
                         aux.getSizeLetra(),aux.getSizeLetraTraductor(),aux.getSizeCadena(),
                         hTerm,hnTerm,hlTerm,hlnTerm,hLeido,hPend,hColorAccSem,tipoLetra,aux.getSizeAcciones(),aux.getZoomInicial());
            }
            
        };
        return a;
    }
/**
 * 
 * @return size of the semantics actions
 */
    public int getSizeAcciones() {
        return sizeAcciones;
    }
/**
 * 
 * @param sizeAcciones 
 * size of the semantics actions
 */
    public void setSizeAcciones(int sizeAcciones) {
        this.sizeAcciones = sizeAcciones;
    }
/**
 * 
 * @return string 
 */
    public CadenaEntrada getEntrada() {
        return entrada;
    }
/**
 * 
 * @param entrada 
 * string
 */
    public void setEntrada(CadenaEntrada entrada) {
        this.entrada = entrada;
    }
/**
 * 
 * @return list of terminals of the string 
 */
    public List<String> getCadena() {
        return cadena;
    }
/**
 * 
 * @param cadena 
 * list of terminals of the string
 */
    public void setCadena(List<String> cadena) {
        this.cadena = cadena;
    }
/**
 * 
 * @return the tree in form of graph
 */
    public Grafo getGrafo() {
        return grafo;
    }
/**
 * 
 * @param grafo 
 * the tree in form of graph
 */
    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }
//actualizar los símbolos al ejecutar una acción semántica
/**
 * update the symbols when execute a semantic action
 * @param info
 * step
 */
    private void actualizarAtribuos(Informacion info) {
        List<Simbolo> simbolosActualizados=info.getSimbolosActualizados();
        if(simbolosActualizados!=null){
            for(Simbolo s:simbolosActualizados){
                Object nodoAActualizar=lista.get(s.getId());//obtenemos el nodo a actualizar
                String toolTipAntiguo=elemValor.get(nodoAActualizar);
                 valorToolTipAntiguo.put(s.getId(), toolTipAntiguo);
                StringBuilder sB = new StringBuilder();
                sB.append(s.getValor());
                elemValor.put(nodoAActualizar, sB.toString());
            }
        }
    }
/**
 * 
 * @param info 
 * step
 */
    private void borrarAtributos(Informacion info) {
        List<Simbolo> simbolosActualizados=info.getSimbolosActualizados();
        if(simbolosActualizados!=null){
            for(Simbolo s:simbolosActualizados){
                Object nodoAActualizar=lista.get(s.getId());//obtenemos el nodo a actualizar         
                elemValor.put(nodoAActualizar, valorToolTipAntiguo.get(s.getId()));
            }
        }
    }
/**
 * 
 * @return 
 */
    public Map<Object, List<String>> getRectangAtribAcc() {
        return rectangAtribAcc;
    }

    public void setRectangAtribAcc(Map<Object, List<String>> rectangAtribAcc) {
        this.rectangAtribAcc = rectangAtribAcc;
    }
/**
 * 
 * @return the slider of the zoom
 */
    public JSlider getMiZoom() {
        return miZoom;
    }
/**
 * 
 * @param miZoom 
 * the slider of zoom
 */
    public void setMiZoom(JSlider miZoom) {
        this.miZoom = miZoom;
    }

   


    

   
    
    
}
