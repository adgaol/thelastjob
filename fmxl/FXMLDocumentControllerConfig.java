/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmxl;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;
import java.awt.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;

import javafx.stage.Stage;
import javax.swing.SwingUtilities;

/**
 *
 * @author adgao
 */
public class FXMLDocumentControllerConfig implements Initializable {
@FXML
private SwingNode  treeNode;   
@FXML
private ComboBox sizeFuenteArbol;
@FXML
private ComboBox sizeFuenteTraduc;
@FXML
private ComboBox sizeFuenteCadena;
@FXML
private ComboBox fountTypeSemanticAct;
@FXML
private ComboBox sizeFountSemanticAct;
@FXML
private ColorPicker backgroundColorTerminals;
@FXML
private ColorPicker fontColorTerminals;
@FXML
private ColorPicker backgroundColorNoTerminals;
@FXML
private ColorPicker fontColorNoTerminals;
@FXML
private ColorPicker readPart;
@FXML
private ColorPicker pendPart;
@FXML
private ColorPicker colorFountSemanticAct;
@FXML
private javafx.scene.control.Button aceptButton;
@FXML
private javafx.scene.control.Button cancelButton;
private Grafo arbolEjemplo;
private int posYCadena=300;
private int posYHijos=100;
private int xInicial=100;
private int xFinal=200;
private List<Object> terminales=new ArrayList<>();
private List<Object> nTerminales=new ArrayList<>();
private int xPadre=150;
private int yPadre=50;
private int posRegla=200;
private int incCadena=70;
private ModificacionesTemp mT;
private Configuracion lectConf;
@FXML
/**
 * change the color of the elements using the ColorPickers
 */
    public void handleAcept(ActionEvent event) throws IOException {
        
        Configuracion conf = new Configuracion();
        conf.guardarConfiguracion(".//config//configActual.xml",mT.getSizeLetraArbol(),mT.getSizeLetraTraductor(),mT.getSizeCadena(),Integer.toString(mT.getColorTerminales().getRGB(),16),Integer.toString(mT.getColornTerminales().getRGB(),16),Integer.toString(mT.getColorLetraTerminales().getRGB(),16),Integer.toString(mT.getColorLetranTerminales().getRGB(),16),Integer.toString(mT.getColorCadenaLeido().getRGB(),16),Integer.toString(mT.getColorCadenaPendiente().getRGB(),16),Integer.toString(mT.getColorAccSem().getRGB(),16),mT.getTipoLetra(),mT.getSizeAcciones(),50);
        // get a handle to the stage
        Stage stage = (Stage) aceptButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
  /**
 * change the color of the elements using the ColorPickers
 */
    public void handleCancel(ActionEvent event) throws IOException {
        
        // get a handle to the stage
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }  
@FXML
/**
 * change the color of the elements using the ColorPickers
 */
    public void handleChooseColor(ActionEvent event) throws IOException {
        ColorPicker e=(ColorPicker)event.getSource();
        if(e.equals(backgroundColorTerminals)){
            javafx.scene.paint.Color coo=backgroundColorTerminals.getValue();    
            System.out.println(coo);        
            mT.setColorTerminales(newColorSw(coo));
            backgroundColorTerminals.setValue(backgroundColorTerminals.getValue());
            modificarArbol();
        }
        else if(e.equals(fontColorTerminals)){
            javafx.scene.paint.Color coo=fontColorTerminals.getValue();   
            System.out.println(coo);
            mT.setColorLetraTerminales(newColorSw(coo));
            fontColorTerminals.setValue(fontColorTerminals.getValue());
            modificarArbol();   
        }
        else if(e.equals(fontColorNoTerminals)){
            javafx.scene.paint.Color coo=fontColorNoTerminals.getValue();   
            System.out.println(coo);
            mT.setColorLetranTerminales(newColorSw(coo));
            fontColorNoTerminals.setValue(fontColorNoTerminals.getValue());
            modificarArbol();   
        }
        else if(e.equals(fontColorNoTerminals)){
            javafx.scene.paint.Color coo=fontColorNoTerminals.getValue();   
            System.out.println(coo);
            mT.setColorLetranTerminales(newColorSw(coo));
            fontColorNoTerminals.setValue(fontColorNoTerminals.getValue());
            modificarArbol();   
        }
        else if(e.equals(backgroundColorNoTerminals)){
            javafx.scene.paint.Color coo=backgroundColorNoTerminals.getValue();   
            System.out.println(coo);
            mT.setColornTerminales(newColorSw(coo));
            backgroundColorNoTerminals.setValue(backgroundColorNoTerminals.getValue());
            modificarArbol();   
        }
        else if(e.equals(pendPart)){
            javafx.scene.paint.Color coo=pendPart.getValue();   
            System.out.println(coo);
            mT.setColorCadenaPendiente(newColorSw(coo));
            pendPart.setValue(pendPart.getValue());
            modificarArbol();   
        }
        else if(e.equals(readPart)){
            javafx.scene.paint.Color coo=readPart.getValue();   
            System.out.println(coo);
            mT.setColorCadenaLeido(newColorSw(coo));
            readPart.setValue(readPart.getValue());
            modificarArbol();   
        }
        else if(e.equals(colorFountSemanticAct)){
            javafx.scene.paint.Color coo=colorFountSemanticAct.getValue();   
            System.out.println(coo);
            mT.setColorAccSem(newColorSw(coo));
            colorFountSemanticAct.setValue(colorFountSemanticAct.getValue());
            modificarArbol();   
        }
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lectConf= new Configuracion();
        lectConf.cargarConfiguracion("./config/configActual.xml");
        FicheroXML ejemplo = new FicheroXML();
        //ejemplo.cargarXml("C:\\Users\\adgao\\Documents\\universidad\\TFG\\TFG-Anterior\\TFG-Anterior\\TFGv0\\traductores\\descend2.xml");      
        mT=inicializarModificaciones();
        arbolEjemplo=new Grafo(lectConf, new FicheroXML(), arbolEjemplo, new HashMap<>(), new ArrayList<>(), 40);
        
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
        //Component comp =arbolEjemplo.getGraphComponent();
        place(treeNode);
        
        //change the configuration using the ComboBox
        ObservableList<Integer> optionsSizeLetraArbol = FXCollections.observableArrayList( 8,10,13,15,18,20);       
        sizeFuenteArbol.setItems(optionsSizeLetraArbol);
        sizeFuenteArbol.setValue(lectConf.getLetraArbol());
        sizeFuenteArbol.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFuenteArbol.getValue();
                mT.setSizeLetraArbol(val);                
                modificarArbol();
            }    
        });
        ObservableList<Integer> optionsSizeLetraCadena = FXCollections.observableArrayList( 8,10,13,15,18,20);
        sizeFuenteCadena.setItems(optionsSizeLetraCadena);
        sizeFuenteCadena.setValue(lectConf.getLetraCadena());
        sizeFuenteCadena.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFuenteCadena.getValue();
                mT.setSizeCadena(val);                
                modificarArbol();
            }    
        });
        ObservableList<Integer> optionsSizeLetraTraductor = FXCollections.observableArrayList( 8,10,13,15,18,20);
        sizeFuenteTraduc.setItems(optionsSizeLetraTraductor);
        sizeFuenteTraduc.setValue(lectConf.getLetraTraductor());
        sizeFuenteTraduc.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFuenteTraduc.getValue();
                mT.setSizeLetraTraductor(val);                
                modificarArbol();
            }    
        });
        ObservableList<Integer> optionsSizeLetraSemanticAct = FXCollections.observableArrayList( 8,10,13,15,18,20);       
        sizeFountSemanticAct.setItems(optionsSizeLetraSemanticAct);
        sizeFountSemanticAct.setValue(lectConf.getSizeAcciones());
        sizeFountSemanticAct.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override 
            public void changed(ObservableValue ov, Integer t, Integer t1) {
                Integer val= (Integer)sizeFountSemanticAct.getValue();
                mT.setSizeAcciones(val);                
                modificarArbol();
            }    
        });
        ObservableList<String> optionsFountType = FXCollections.observableArrayList( "Times new Roman","Arial","Calibri","Courier","Broadway","Informal Roman","Verdana");       
        fountTypeSemanticAct.setItems(optionsFountType);
        fountTypeSemanticAct.setValue(lectConf.getTipoLetra());
        fountTypeSemanticAct.valueProperty().addListener(new ChangeListener<String>() {
            @Override 
            public void changed(ObservableValue ov, String t, String t1) {
                String val=(String)fountTypeSemanticAct.getValue();
                mT.setTipoLetra(val);                
                modificarArbol();
            }    
        });
        backgroundColorTerminals.setValue(newColorFX(lectConf.getColorTerminal()));
        fontColorTerminals.setValue(newColorFX(lectConf.getLetraTerminal()));
        backgroundColorNoTerminals.setValue(newColorFX(lectConf.getColorNoTerminal()));
        fontColorNoTerminals.setValue(newColorFX(lectConf.getLetraNoTerminal()));
        pendPart.setValue(newColorFX(lectConf.getColorPend()));
        readPart.setValue(newColorFX(lectConf.getColorLeido()));
        colorFountSemanticAct.setValue(newColorFX(lectConf.getColorAccSem()));
        fountTypeSemanticAct.setValue(lectConf.getTipoLetra());
    }
    /**
     * place the tree in the swing node
     * @param swingNode
     * where the tree will be placed
     */
     public void place(final SwingNode swingNode) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                 swingNode.setContent(arbolEjemplo.getGraphComponent());
            }
        });
    }
//     /**
//      * place the grammar in the swingNode
//      * @param swingNode 
//      * where the grammar will be placed
//      */
//    public void placeGrammar(final SwingNode swingNode) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//
//                 swingNode.setContent(gramatica.getGraphComponent());
//            }
//        });
//    }
//     /**
//      * place the string in the swingNode
//      * @param swingNode 
//      * where the string will be placed
//      */
//     public void placeString(final SwingNode swingNode) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                
//                 swingNode.setContent(entrada.getGraphComponent());
//            }
//        });
//    }
//    /**
//         * initialize the tree
//         */
//
//    public void inicializarArbol(Configuracion lectConf){
//        //Árbol
//            this.treeElements= new ArrayList<>();    
//            //List<Object> aux = new ArrayList<>();     
//             this.tree= new Grafo(lectConf,ejemplo,gramatica,elemValor,listaReglas,sepRegla);   
//            tree.activarListener(gramatica,mapaGramatica,listaReglas,sepRegla,lectConf.getLetraArbol());
//
//
//        }
    
 /**
      * initialize the value of the modifications
      * @return temporal modifications
      */
    private  ModificacionesTemp inicializarModificaciones() {
        ModificacionesTemp m= new ModificacionesTemp();
        m.setSizeLetraArbol(lectConf.getLetraArbol());
        m.setSizeLetraTraductor(lectConf.getLetraTraductor());
        m.setSizeCadena(lectConf.getLetraCadena());
        m.setColorTerminales(new Color(Integer.parseInt(lectConf.getColorTerminal(),16)));
        m.setColornTerminales(new Color(Integer.parseInt(lectConf.getColorNoTerminal(),16)));
        m.setColorLetraTerminales(new Color(Integer.parseInt(lectConf.getLetraTerminal(),16)));
        m.setColorLetranTerminales(new Color(Integer.parseInt(lectConf.getLetraNoTerminal(),16)));
        m.setColorCadenaLeido(new Color(Integer.parseInt(lectConf.getColorLeido(),16)));
        m.setColorCadenaPendiente(new Color(Integer.parseInt(lectConf.getColorPend(),16)));
        m.setColorAccSem(Color.black);
        m.setSizeAcciones(13);
        m.setTipoLetra("Times New Roman");
        return m;
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
  * Recive a color in format Swing and transform it into a format javaFX
  * @param color
  * String with the color in format Swing
  * @return 
  * Color in format javafx.scene.paint.Color
  */
 public javafx.scene.paint.Color newColorFX(String color){
    int col=Integer.parseInt(color,16);
    Color color1=new Color(col);
    double red=color1.getRed()/255.0;
    double green=color1.getGreen()/255.0;
    double blue=color1.getBlue()/255.0;
    double opacity=color1.getAlpha()/255.0;
    return new javafx.scene.paint.Color(red, green, blue, opacity);
 }
 /**
  * recive the color of a ColorPicker and transform it in a color for swing
  * @param color
  * Color in format javafx.scene.paint.Color
  * @return 
  * Color in format Swing
  */
 public Color newColorSw(javafx.scene.paint.Color color){
 
    float red=(float)(color.getRed());
    float green=(float) (color.getGreen());
    float blue=(float) (color.getBlue());
   // float alpha=(float) (color.getOpacity()*255);
    return new Color(red, green, blue);
 }
 /**
  * recive the color of a ColorPicker and transform it in a color for swing
  * @param color
  * Color in format javafx.scene.paint.Color
  * @return 
  * Color in format Swing
  */
// public String colorToSave(javafx.scene.paint.Color color){
//    color.
//    float red=(float)(color.getRed())*255;
//    float green=(float) (color.getGreen())*255;
//    float blue=(float) (color.getBlue())*255;
//   // float alpha=(float) (color.getOpacity()*255);
//    return ;
// }
        }
