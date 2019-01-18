/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmxl;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author adgao
 */
public class FXMLDocumentController implements Initializable {
    
//    @FXML
//    private Label label;
//    @FXML
//    private SwingNode graphNode;
   @FXML
   private AnchorPane graphPane;
   @FXML
   private SwingNode graphNode;
   @FXML
   private SwingNode grammarNode;
   @FXML
   private SwingNode stringNode;

    int contador=0;
    private List<Informacion> stepsList;
    private ArrayList<Object> treeElements;
    private Grafo tree;
    private Map<Integer, Simbolo> relationIdSimbol ;
    private FicheroXML ejemplo;
    private int separacionNodos=75;
    private int posInicialDefecto=80;
    private int posInicial=posInicialDefecto;
    private boolean primerElem= true;
    private int posicionTerm=0;
    private int separacionTerminales=85;
    private List<Integer> listaPosiciones = new ArrayList<>();
    private Map<Object,List<String>> rectangAtribAcc = new HashMap<>();
    private List<Object> rectangulos= new ArrayList<>();
    private List<Object> rectangleList = new ArrayList<>();
    private List<Object> hijos = new ArrayList<>();
    private Map<Object,String> elemValor=new HashMap<>();//empareja el nodo con su valor (para tooltip)
    private int posXRaiz=300;
    private Map<Object,Object> padreRect= new HashMap<>();//map with the father of the rectangels
    //private Map<Object,List<Object>> rectangPila= new HashMap<>();//para los traductores descendentes emparejamos los elementos q están en la pila con su rectángulo correspondiente
    private Map<Integer,String> valorToolTipAntiguo= new HashMap<>();//relation the symbol with her past tooltip
    private CadenaEntrada entrada;
    private List<String> cadena;
    private Grafo gramatica;
    private List<Regla> miGramatica;//conjunto de reglas de la gramatica
    public static final int sepRegla=40;//separacion entre las reglas
    private Map<String,Regla> mapaGramatica = new HashMap<>();//match the id of the xml with the rule
    private List<List<Object>> listaReglas= new ArrayList<>();//list of inserted rules in translator
    private int numNodos;
    private Configuracion lectConf;
     @FXML
        public void handleOpenConfiguration(ActionEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocumentConfig.fxml"));
        
        Scene scene = new Scene(root);
        Stage configuration=new Stage();
        configuration.setScene(scene);
        configuration.initModality(Modality.WINDOW_MODAL);
 
            // Specifies the owner Window (parent) for new window
           configuration.initOwner(Fmxl.getStage());
           //configuration.onCloseRequestProperty().addListener();
            // Set position of second window, related to primary window.
            //newWindow.setX(primaryStage.getX() + 200);
            //newWindow.setY(primaryStage.getY() + 100);
        configuration.show();
        configuration.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
            int cont=contador;
            lectConf.cargarConfiguracion("./config/configActual.xml");
            irInicio(); 
            listaReglas=new ArrayList<>();
            gramatica.eliminarGramatica(ejemplo);
            entrada.eliminarCadena(); 
            actualizarEstilos(tree.getGraph());
            actualizarEstilos(gramatica.getGraph());
            actualizarEstilos(entrada.getGraph());
       
         
            reconstruirGramatica();
            entrada.reconstruirCadena(cadena);
       
            irPaso(0,cont);
          }
      }); 
        

        }
    @FXML
    private void handleRestaurarConfig(ActionEvent event) {
        int cont=contador;
            lectConf.cargarConfiguracion("./config/configDefecto.xml");
            irInicio(); 
            listaReglas=new ArrayList<>();
            gramatica.eliminarGramatica(ejemplo);
            entrada.eliminarCadena(); 
            actualizarEstilos(tree.getGraph());
            actualizarEstilos(gramatica.getGraph());
            actualizarEstilos(entrada.getGraph());
       
         
            reconstruirGramatica();
            entrada.reconstruirCadena(cadena);
       
            irPaso(0,cont);
           Configuracion conf = new Configuracion();
        conf.guardarConfiguracion(".//config//configActual.xml",lectConf.getLetraArbol(),lectConf.getLetraTraductor(),lectConf.getLetraCadena(),lectConf.getColorTerminal()/*Integer.toString(mT.getColorTerminales().getRGB(),16)*/,lectConf.getColorNoTerminal(),lectConf.getLetraTerminal(),lectConf.getLetraNoTerminal(),lectConf.getColorLeido(),lectConf.getColorPend(),lectConf.getColorAccSem(),lectConf.getTipoLetra(),lectConf.getSizeAcciones(),50);
        // get a handle to the stage
            
//        label.setText("Hello World!");
    }
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
//        label.setText("Hello World!");
    }
    @FXML
    private void handleAnteriorAction(ActionEvent event) {
         eliminar();
//        label.setText("Hello World!");
    }
    @FXML
    private void handleSiguienteAction(ActionEvent event) {
        construirArbol();
//        label.setText("Hello World!");
    }
    @FXML
    private void handleIrFinAction(ActionEvent event) {
        irFin();
//        label.setText("Hello World!");
    }
    @FXML
    private void handleIrInicioAction(ActionEvent event) {
        irInicio();
//        label.setText("Hello World!");
    }
    @FXML
    private void handleMouseAction(MouseEvent event) {
        System.out.println("You clicked me!");
        graphPane.requestFocus();
//        label.setText("Hello World!");
    }
    @FXML
    public void handleKeyAction(KeyEvent event){
        if (event.getCode()==KeyCode.LEFT){
            System.out.println("LEFT");
            eliminar();
            
            // graphPane.requestFocus();
            
        }
        else if (event.getCode()==KeyCode.RIGHT){
            System.out.println("RIGHT");
            construirArbol();
             //graphPane.requestFocus();
            
        }
        else if (event.getCode()==KeyCode.HOME){
            System.out.println("Inicio");
            irInicio();
             //graphPane.requestFocus();
            
        }
        else if (event.getCode()==KeyCode.END){
            System.out.println("Fin");
            irFin();
             //graphPane.requestFocus();
            
        }
        else{
            System.out.println(event.getCode());
        }
//       switch (e.getCode()) {
//            case LEFT:
//                System.out.println("Adios");
////                eliminar();
//                //this.placeTree(graphNode);
//                break;
//            case RIGHT:
//                System.out.println("Hola");
////                construirArbol();
////                this.placeTree(graphNode);
//                break;
//            case UP:
//                break;
//            case DOWN:
//                break;
//            default:
//                break;
//        }  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // load of configuration and translate
        lectConf= new Configuracion();
        //lectConf.cargarConfiguracion("C:\\Users\\adgao\\Documents\\NetBeansProjects\\fmxl\\src\\fmxl/configActual.xml");
        lectConf.cargarConfiguracion("./config/configActual.xml");
        ejemplo = new FicheroXML();
        ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\TFGv0\\traductores\\descend2.xml");      
        //grammar part
        this.miGramatica=ejemplo.getListaGramatica();
        this.construirGramatica(lectConf);
        this.colocarGramatica();
        placeGrammar(grammarNode);
        //tree part
        this.inicializarArbol(lectConf);
        stepsList=ejemplo.getListaPasos();
        relationIdSimbol=ejemplo.getMapa();
        cadena=ejemplo.getCadena();
        placeTree(graphNode);
        //string part
        entrada= new CadenaEntrada(lectConf);

        List<String> auxLista;
        String[] cadenaPend=cadena.get(0).split("pend");//eliminamos pend de la cadena
        auxLista=Arrays.asList(cadenaPend[1].split(" "));

        entrada.construirCadena(auxLista);
        entrada.activarListener(stepsList,contador,this);


        this.colocarEntrada();
        placeString(stringNode);
        
        graphPane.widthProperty().addListener((obs, oldVal, newVal) -> {
     // Do whatever you want
            System.out.println("funchiona");
        });
       numNodos= ejemplo.getNumNodos();
//        graphPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
//
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.LEFT) {
//                    System.out.println("Enter Pressed");
//                }
//            }
//        });
    } 
    /**
     * place the tree in the swing node
     * @param swingNode
     * where the tree will be placed
     */
     public void placeTree(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                 swingNode.setContent(tree.getGraphComponent());
            }
        });
    }
     /**
      * place the grammar in the swingNode
      * @param swingNode 
      * where the grammar will be placed
      */
    public void placeGrammar(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                 swingNode.setContent(gramatica.getGraphComponent());
            }
        });
    }
     /**
      * place the string in the swingNode
      * @param swingNode 
      * where the string will be placed
      */
     public void placeString(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                 swingNode.setContent(entrada.getGraphComponent());
            }
        });
    }
    /**
         * initialize the tree
         */

    public void inicializarArbol(Configuracion lectConf){
        //Árbol
            this.treeElements= new ArrayList<>();    
            //List<Object> aux = new ArrayList<>();     
             this.tree= new Grafo(lectConf,ejemplo,gramatica,elemValor,listaReglas,sepRegla);   
            tree.activarListener(gramatica,mapaGramatica,listaReglas,sepRegla,lectConf.getLetraArbol());


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
  * unstack the elemnt in the stack
  * @param padre
  * rectangle parent
  * @param pos 
  * pos of the parent
  */  
    public void sacarPila(Object padre, int pos){
        Object rect=this.padreRect.get(padre);
        List<Object> pila=tree.getRectangPila().get(rect);
        int contador=0;
        if(pila.size()>0){
            Object elemElim=pila.get(0);//elemento a eliminar
            tree.eliminar(elemElim);
            pila.remove(elemElim);
            //trasladar el resto de elementos
            for(Object ob: pila){
                
                    tree.trasladarElemPila(ob,pos);
                contador++;
            }
        }
        
    }
       /**
 * update the symbols when execute a semantic action
 * @param info
 * step
 */
    private void actualizarAtribuos(Informacion info) {
        List<Simbolo> simbolosActualizados=info.getSimbolosActualizados();
        if(simbolosActualizados!=null){
            for(Simbolo s:simbolosActualizados){
                Object nodoAActualizar=rectangleList.get(s.getId());//obtenemos el nodo a actualizar
                String toolTipAntiguo=elemValor.get(nodoAActualizar);
                 valorToolTipAntiguo.put(s.getId(), toolTipAntiguo);
                StringBuilder sB = new StringBuilder();
                sB.append(s.getValor());
                elemValor.put(nodoAActualizar, sB.toString());
            }
        }
    }
      /**
         * Add the element to this step of tree
         * @return the tree in form of graph
         */
        public Grafo construirArbol(){            
            
           if(contador<ejemplo.getNumNodos() ){
            Informacion info=stepsList.get(contador);
           //Obtenemos el elemento a insertar en el árbol, y el id de sus hijos
           //en caso de ser una reducción
            String[] arrayInfo = info.getElemento().split(" "); 
            //obtenemos la posición del elemento a insertar
            //String[] arrayPosicion = info.getPosicion().split(" ");
            //posición Y del nodo en función de su nivel            
            int nivel=relationIdSimbol.get(info.getId()).getNivel();
            boolean terminal= relationIdSimbol.get(info.getId()).isTerminal();
            
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
                r=tree.insertarRectangulo(info.getRegla(),posXActual,posicionY);
                this.rectangAtribAcc.put(r, info.getRegla().getValorAtributos());
               
                
                rectangulos.add(r);
            }
           if(info.getTipo().equals("primero")){
                 Object nodo = tree.insertarElemento(new Simbolo(arrayInfo[0],terminal), posXRaiz, posInicial);           
                rectangleList.add(nodo); 
                hijos.add(rectangleList.get(rectangleList.size()-1)); 
                this.elemValor.put(nodo, info.getValor());
           }
            else if(info.getTipo().equals("reduccion")){                
                //lista.add(grafo.insertarPadre(new Simbolo(arrayInfo[0],false), Integer.parseInt(arrayPosicion[0]),Integer.parseInt(arrayPosicion[1]), arrayInfo,lista));
                Object nodo=tree.insertarPadre(new Simbolo(arrayInfo[0],terminal),posXActual,posicionY, arrayInfo,rectangleList);
                rectangleList.add(nodo);
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
                padre=rectangleList.get(Integer.parseInt(arrayInfo[1]));
                Object nodo=tree.insertarHijo(padre,posXActual,posicionY, simboloAux);
                rectangleList.add(nodo);
                this.elemValor.put(nodo, info.getValor());
                this.padreRect.put(padre,r);
                 
             }
             else if(info.getTipo().equals("despDes")){
                 Simbolo simboloAux=new Simbolo(arrayInfo[0],terminal);
                 padre=rectangleList.get(Integer.parseInt(arrayInfo[1]));
                Object nodo=tree.insertarHijo(padre,posXActual,posicionY,simboloAux);
                rectangleList.add(nodo);
                this.elemValor.put(nodo, info.getValor());
                sacarPila(padre,posXActual);
                 
             }
             else{
                 //posicionTerm=posicionTerm+80;
                //Object aux = grafo.insertarElemento(new Simbolo(arrayInfo[0],true), Integer.parseInt(arrayPosicion[0]), Integer.parseInt(arrayPosicion[1]));
                Object nodo = tree.insertarElemento(new Simbolo(arrayInfo[0],true), posicionTerm, posicionY); 
               
                rectangleList.add(nodo); 
                hijos.add(rectangleList.get(rectangleList.size()-1)); 
                this.elemValor.put(nodo, info.getValor());
            
            }
           actualizarAtribuos(info);
            listaPosiciones.add(posXActual);
            
            contador++;
                         

           entrada.actualizar(cadena.get(contador),ejemplo,contador);
                         
            //grafo.terminarActualizacion();   
           }
            return tree;
        } 
         public  Grafo eliminar(){            
            //Object r1=grafo.insertarRectangulo(regla1,5,300);
            //rectangulos.add(r1);
            
            Object padre;
            if(contador>0){
                contador--;              
               Informacion info=stepsList.get(contador);
                String[] arrayInfo = info.getElemento().split(" ");
               Object e= rectangleList.get(contador);               
               tree.eliminar(e);                        
               rectangleList.remove(e); 
               hijos.remove(e);              
               listaPosiciones.remove(contador);
               
                if(info.getTipo().equals("desplazamiento") || info.getTipo().equals("despDes")){  
                    if(posicionTerm>0)
                        posicionTerm=posicionTerm-separacionTerminales;      
                }
               
              if(info.getTipo().equals("despDes")){
                  padre=rectangleList.get(Integer.parseInt(arrayInfo[1]));
                  addPila(padre,arrayInfo[0]);
                  
              }
            
               
               
                
                 if(info.getRegla()!=null){
                    Object rect = rectangulos.get(rectangulos.size()-1);
                    this.eliminarPila(rect);
                    rectangulos.remove(rect);
                    tree.rectPos.remove(rect);
                    tree.rectangRegla.remove(rect);
                    
                     tree.eliminar(rect);
                     
                     
                 }

                entrada.actualizar(cadena.get(contador),ejemplo,contador);
                  if(info.getSimbolosActualizados()!=null){
                      borrarAtributos(info);
                  }   
            }    
            //grafo.terminarActualizacion();
            return tree; 
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
                Object nodoAActualizar=rectangleList.get(s.getId());//obtenemos el nodo a actualizar         
                elemValor.put(nodoAActualizar, valorToolTipAntiguo.get(s.getId()));
            }
        }
    }
       /**
 * 
 * @param rect 
 * rectangle of node of the tree
 */
    public void eliminarPila(Object rect){
        if(ejemplo.getTipoTraductor().equals("Descendente")){
                        List<Object> elemPila=tree.getRectangPila().get(rect);                        
                        for(Object el:elemPila){
                            tree.eliminar(el);

                        }
                        tree.getRectangPila().remove(rect);
                         
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
        List<Object> pila=tree.getRectangPila().get(rect);
        int[]pos = new int[2];
        List<Integer> p=this.tree.getRectPos().get(rect);//obtenemos la posicion del rectangulo donde vamos a añadir 
        //pos[0]=(p.get(0))-pila.size()*grafo.espacioPila;
        pos[0]=this.posicionTerm + tree.anchoNodo;
        pos[1]=p.get(1);
        Object o=tree.insertarElemPila(s,pos[0],pos[1]);       
        pila.add(0, o);
        
    }
    /**
         * build the grammar 
         */
        public void construirGramatica(Configuracion lectConf){            
           int posY=65;

            List<Object> reglaActual;
            gramatica=new Grafo(lectConf,ejemplo,gramatica,elemValor,listaReglas,sepRegla);
            //gramatica.insertarTitulo("Traductor");
            for(Regla i: miGramatica){

                reglaActual=gramatica.insertarRegla(posY, i.getRegla());
                posY=posY+sepRegla;
                 mapaGramatica.put(i.getId(), i);
                 this.listaReglas.add(reglaActual);
            }

                //gramatica.activarListener();
                
        }
         /**
        * place the grammar in the interface
        */ 
        public void colocarGramatica(){
        gramatica.getGraphComponent().getViewport().setOpaque(true);
        gramatica.getGraphComponent().getViewport().setBackground(Color.WHITE); 
          // splitPane2.setTopComponent(this.gramatica.getGraphComponent());
        }
         /**
         * place the string in the interface
         */
        public void colocarEntrada(){
             this.entrada.getGraphComponent().getViewport().setOpaque(true);
            this.entrada.getGraphComponent().getViewport().setBackground(Color.WHITE); 
            //this.entrada.insertarTitulo("Cadena de entrada");
            //splitPane3.setTopComponent(this.entrada.getGraphComponent());
            
        }
        /**
    * go to the end of the execution of the tree
    * @param app 
    */ 
     public  void irFin(){
        for(int i=contador;i<numNodos;i++){
            construirArbol();
        }
    }
    /**
     * Back to the init of the execution of the tree
     * @param app 
     * app
     */
    public  void irInicio(){
        
        for(int i=contador;i>0;i--){
            eliminar();
        }
        posicionTerm=0;
       
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
                construirArbol();
            }
        }else{
                   for(int i=contador;i>id;i--){
                        eliminar();
                    } 
                 }
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
        style.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        style.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraArbol());
        //style.put(mxConstants.STYLE_FILLCOLOR,"#82EB20");
        style.put(mxConstants.STYLE_FILLCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorNoTerminal(),16)));
        style.put(mxConstants.STYLE_ROUNDED,"1");
        style.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylesheet.putCellStyle("VERDE", style);
         
        Hashtable<String, Object> style2 = new Hashtable<String, Object>();
        style2.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style2.put(mxConstants.STYLE_OPACITY, 100);
        style2.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraTerminal(),16)));
        style2.put(mxConstants.STYLE_FONTSIZE,lectConf.getLetraArbol());
        style2.put(mxConstants.STYLE_ROUNDED,"1");
        style2.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style2.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        stylesheet.putCellStyle("ROJO", style2);
        
        
        
        Hashtable<String, Object> style3 = new Hashtable<String, Object>();
        style3.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style3.put(mxConstants.STYLE_OPACITY, 0);
        style3.put(mxConstants.STYLE_FONTCOLOR,  Integer.toHexString(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        style3.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraTraductor());
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
        style4.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraTerminal(),16)));
        style4.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraTraductor());
        style4.put(mxConstants.STYLE_ROUNDED,"1");
        style4.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style4.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        //style4.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER"); 
        
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("GROJO", style4);
        
        Hashtable<String, Object> stylePilaN = new Hashtable<String, Object>();
        stylePilaN.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        stylePilaN.put(mxConstants.STYLE_OPACITY, 20);
        stylePilaN.put(mxConstants.STYLE_TEXT_OPACITY, 20);
        stylePilaN.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        stylePilaN.put(mxConstants.STYLE_FONTSIZE,lectConf.getLetraArbol());
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
        stylePila.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getLetraTerminal(),16)));
        stylePila.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraArbol());
        stylePila.put(mxConstants.STYLE_ROUNDED,"1");
        stylePila.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        stylePila.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style4.put(mxConstants.STYLE_SPACING_RIGHT,0);
         
        stylesheet.putCellStyle("ROJOPILA", stylePila);
        
       
        
        
        Hashtable<String, Object> style6 = new Hashtable<String, Object>();
        style6.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style6.put(mxConstants.STYLE_OPACITY, 0); //fondo transparente
        style6.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorAccSem(),16)));  
        style6.put(mxConstants.STYLE_FONTSIZE, lectConf.getSizeAcciones());
        style6.put(mxConstants.STYLE_ROUNDED,"1");
        style6.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        style6.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        //style4.put(mxConstants.STYLE_SPACING_LEFT,-20);
        style6.put(mxConstants.STYLE_SPACING_RIGHT,0);
        style6.put(mxConstants.STYLE_ALIGN,"ALIGN_LEFT");       
        style6.put(mxConstants.STYLE_FONTFAMILY,lectConf.getTipoLetra()); 
        
         Hashtable<String, Object> style11 = new Hashtable<String, Object>();
        style11.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style11.put(mxConstants.STYLE_OPACITY, 80);     
        style11.put(mxConstants.STYLE_ROUNDED,"1");
        style11.put(mxConstants.STYLE_GRADIENTCOLOR,"#FFFFFF");
        //style2.put(mxConstants.STYLE_FILLCOLOR,"#FF5118");
        style11.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        style11.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorLeido(),16)));
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
        style12.put(mxConstants.STYLE_FILLCOLOR,Integer.toHexString(Integer.parseInt(lectConf.getColorTerminal(),16)));
        style12.put(mxConstants.STYLE_FONTCOLOR, Integer.toHexString(Integer.parseInt(lectConf.getColorPend(),16)));
        style12.put(mxConstants.STYLE_FONTSIZE, lectConf.getLetraCadena());
        // style2.put(mxConstants.STYLE_ALIGN,"ALIGN_CENTER");
        stylesheet.putCellStyle("PENDIENTE", style12);
         
        stylesheet.putCellStyle("ACCIONES", style6);
    }
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
//        /**
// * restore the default configuration
// * @param app 
// * app
// */
//    private static void restablecerConfiguracion() {
//        Configuracion conf = new Configuracion();
//        conf.cargarConfiguracion(".//config//configDefecto.xml");
//        String colTerminal=conf.getColorTerminal();       
//        app.setcTerminales(new Color( Integer.parseInt(colTerminal, 16)));
//        String colnTerminal=conf.getColorNoTerminal();  
//        app.setcNoTerminales(new Color( Integer.parseInt(colnTerminal, 16)));
//        
//         String letraTerminal=conf.getLetraTerminal();       
//        app.setLetraTerminales(new Color( Integer.parseInt(letraTerminal, 16)));
//        String letranTerminal=conf.getLetraNoTerminal();  
//        app.setLetraNoTerminales(new Color( Integer.parseInt(letranTerminal, 16)));
//        
//        String colLeido=conf.getColorLeido();       
//        app.setColorCadLeido(new Color( Integer.parseInt(colLeido, 16)));
//        String colPend=conf.getColorPend();  
//        app.setColorCadPendiente(new Color( Integer.parseInt(colPend, 16)));
//        app.setZoomInicial(conf.getZoom());
//        String colorAccSem=conf.getColorAccSem();
//        app.setColorAccSem(new Color(Integer.parseInt(colorAccSem,16)));        
//        app.setTipoLetra(conf.getTipoLetra());
//        
//        app.setSizeLetra(conf.getLetraArbol());
//        app.setSizeLetraTraductor(conf.getLetraTraductor());
//        app.setSizeCadena(conf.getLetraCadena());
//        app.setSizeCadena(conf.getSizeAcciones());
//        
//        app.zoom();
//        app.getMiZoom().setValue(app.getZoomInicial());
//        
//        VentanaConfig v= new VentanaConfig();
//        v.setApp(app);
//        v.generarVisualizador();
//    }
        }
