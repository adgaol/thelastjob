package fmxl;


import java.io.File;
import java.io.FileWriter;
import java.util.List;
import javax.xml.transform.*;



import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class Configuracion {
    private String ruta;
    private int letraArbol,letraTraductor,letraCadena,sizeAcciones;
    private String colorTerminal,colorNoTerminal,letraTerminal,letraNoTerminal,colorLeido,colorPend;
    private String colorAccSem,tipoLetra;
    private int zoom;
    public Configuracion() {
    }
    /**
     * load the data of the xml of configuration
     * @param ruta 
     * configuration route
     * 
     */
    public void cargarConfiguracion(String ruta){
        this.ruta=ruta;
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        //File xmlFile = new File( "src\\pruebaxml\\archivo.xml" );
        File xmlFile = new File( ruta );
        try
        {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build( xmlFile );

            //Se obtiene la raiz 'tables'
            Element rootNode = document.getRootElement();

            //Se obtiene la lista de hijos de la raiz 'tables'
            List list = rootNode.getChildren();

            //Se recorre la lista de hijos de 'tables'
            for ( int i = 0; i < list.size(); i++ )
            {
                //Se obtiene el elemento 'tabla'
                Element tabla = (Element) list.get(i);
                obtenerTamLetras(tabla.getChild("tamLetras"));
                obtenerColores(tabla.getChild("colores"));
                obtenerAccSem(tabla.getChild("accSem"));
                zoom=Integer.parseInt(tabla.getChildText("zoom"));
            }
        }catch(Exception e){
            
        }
    }
    /**
     * save the actual configuration
     * @param ruta
     * configuration route
     * @param tArbol
     * size of letter of tree
     * @param tTraductor
     * size of letter of grammar
     * @param tCadena
     * size of letter of string
     * @param cTerminales
     * color of terminals
     * @param cNoTerminales
     * color of no terminals
     * @param letraTerminales
     * size of letter of terminals
     * @param letraNoTerminales
     * size of letter of no terminals
     * @param leido
     * color of read terminals in string
     * @param pend
     * color of pending terminals in string
     * @param colorAccSem
     * color of semantic actions
     * @param tipoLetra
     * type of letter
     * @param sizeAcciones
     * size of the actions
     * @param z 
     * zoom
     * 
     */
    public void guardarConfiguracion(String ruta,int tArbol, int tTraductor, int tCadena, 
            String cTerminales, String cNoTerminales,String letraTerminales, String letraNoTerminales,
            String leido, String pend,String colorAccSem, String tipoLetra, int sizeAcciones,int z){
        this.ruta=ruta;
        //Se crea un SAXBuilder para poder parsear el archivo
        SAXBuilder builder = new SAXBuilder();
        //File xmlFile = new File( "src\\pruebaxml\\archivo.xml" );
        File xmlFile = new File( ruta );
        try
        {
            //Se crea el documento a traves del archivo
            Document document = (Document) builder.build( xmlFile );

            //Se obtiene la raiz 'tables'
            Element rootNode = document.getRootElement();

            //Se obtiene la lista de hijos de la raiz 'tables'
            List list = rootNode.getChildren();

            //Se recorre la lista de hijos de 'tables'
            for ( int i = 0; i < list.size(); i++ )
            {
                //Se obtiene el elemento 'tabla'
                Element tabla = (Element) list.get(i);
                cambiarTamLetras(tabla.getChild("tamLetras"),tArbol,tTraductor,tCadena);
                cambiarColores(tabla.getChild("colores"),cTerminales,cNoTerminales,letraTerminales,letraNoTerminales,leido,pend);
                cambiarAccSem(tabla.getChild("accSem"),colorAccSem,tipoLetra,sizeAcciones);
                Element zoom=tabla.getChild("zoom");
                zoom.setText(Integer.toString(z));
            }
        XMLOutputter xmlOut = new XMLOutputter(Format.getPrettyFormat());

            FileWriter fw = new FileWriter(new File(ruta));

            xmlOut.output(document, fw);

            fw.close();

      
        }catch(Exception e){
            
        }
    }
/**
 * obtain the size of the letters
 * @param letras 
 * list of the sizes of the letters
 * 
 * 
 */
    private void obtenerTamLetras(Element letras) {
        letraArbol=Integer.parseInt(letras.getChildText("arbol"));
        letraTraductor=Integer.parseInt(letras.getChildText("traductor"));
        letraCadena=Integer.parseInt(letras.getChildText("cadena"));
    }
/**
 * obtain the colors of teh elements of the app
 * @param colores 
 * list with the colors of the different elements
 */
    private void obtenerColores(Element colores) {
        colorTerminal=colores.getChildText("terminales");
        colorNoTerminal=colores.getChildText("noTerminales");
        this.letraTerminal=colores.getChildText("letraTerminales");
        this.letraNoTerminal=colores.getChildText("letraNoTerminales");
        this.colorLeido=colores.getChildText("leido");
        this.colorPend=colores.getChildText("pend");
        
    }
    /**
     * obtain the color,type of letter and the size of the string
     * @param cad 
     * list of semantic actions
     */
     private void obtenerAccSem(Element cad) {
        this.colorAccSem=cad.getChildText("colorAccSem");
        this.tipoLetra=cad.getChildText("tipoLetra");
        this.sizeAcciones=Integer.parseInt(cad.getChildText("sizeAcciones"));
    }
     /**
      * 
      * @return String ruta
      */
    public String getRuta() {
        return ruta;
    }
    /**
     * 
     * @param ruta 
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    /**
     * 
     * @return size of the letter tree
     */
    public int getLetraArbol() {
        return letraArbol;
    }
    /**
     * 
     * @param letraArbol 
     * size of the letter of the tree
     */
    public void setLetraArbol(int letraArbol) {
        this.letraArbol = letraArbol;
    }
/**
 * 
 * @return size of the letter of the grammar
 */
    public int getLetraTraductor() {
        return letraTraductor;
    }
/**
 * 
 * @param letraTraductor 
 * size of the letter of the grammar
 */
    public void setLetraTraductor(int letraTraductor) {
        this.letraTraductor = letraTraductor;
    }
/**
 * 
 * @return size of the letter of the string
 */
    public int getLetraCadena() {
        return letraCadena;
    }
/**
 * 
 * @param letraCadena 
 * size of the letter of the grammar
 */
    public void setLetraCadena(int letraCadena) {
        this.letraCadena = letraCadena;
    }
/**
 * 
 * @return color of the terminal
 */
    public String getColorTerminal() {
        return colorTerminal;
    }
/**
 * 
 * @param colorTerminal 
 * color of the terminal
 */
    public void setColorTerminal(String colorTerminal) {
        this.colorTerminal = colorTerminal;
    }
/**
 * 
 * @return color of the no terminal
 */
    public String getColorNoTerminal() {
        return colorNoTerminal;
    }
/**
 * 
 * @param colorNoTerminal 
 * color of the no terminal
 */
    public void setColorNoTerminal(String colorNoTerminal) {
        this.colorNoTerminal = colorNoTerminal;
    }
/**
 * 
 * @return zoom
 */
    public int getZoom() {
        return zoom;
    }
/**
 * 
 * @param zoom 
 */
    public void setZoom(int zoom) {
        this.zoom = zoom;
    }
/**
 * change the size of the letters
 * @param letras
 * list of the sizes o the different letters
 * @param tArbol
 * size of the letter of the tree
 * @param tTraductor
 * size of the letter of the grammar
 * @param tCadena 
 * size of the letter of the string
 */
    private void cambiarTamLetras(Element letras,int tArbol, int tTraductor, int tCadena) {
        Element elArbol = letras.getChild("arbol");
        elArbol.setText(Integer.toString(tArbol));
        Element elTraductor = letras.getChild("traductor");
        elTraductor.setText(Integer.toString(tTraductor));
        Element elCadena = letras.getChild("cadena");
        elCadena.setText(Integer.toString(tCadena));
    }
/**
 * changes the colors of the differents elements of the app
 * @param col
 * list with the colors 
 * @param cTerminales
 * color of the terminals
 * @param cNoTerminales
 * color of the no terminals
 * @param letraTerminales
 * color of the letter of the terminals
 * @param letraNoTerminales
 * color of the letter of the no terminals
 * @param leido
 * color of the read terminals 
 * @param pend 
 * color of the pending terminals 
 */
    private void cambiarColores(Element col,String cTerminales, String cNoTerminales, String letraTerminales, String letraNoTerminales,String leido, String pend) {
        Element term=col.getChild("terminales");    
        term.setText(cTerminales);
        Element nTerm=col.getChild("noTerminales");
        nTerm.setText(cNoTerminales);
        
        Element lterm=col.getChild("letraTerminales");    
        lterm.setText(letraTerminales);
        Element lnTerm=col.getChild("letraNoTerminales");
        lnTerm.setText(letraNoTerminales);
        
        Element eleido=col.getChild("leido");
        eleido.setText(leido);
        Element ePend=col.getChild("pend");
        ePend.setText(pend);
        
    }
    /**
     * 
     * @param accSem
     * list with the attributes of the semantics actions
     * @param colorAccSem
     * color of the semantics actions
     * @param tipoLetra
     * type of the letter of the semantics actions
     * @param sizeAcciones 
     * size of the semantic actions
     */
     private void cambiarAccSem(Element accSem, String colorAccSem, String tipoLetra,int sizeAcciones) {
        Element eColorAcc=accSem.getChild("colorAccSem");    
        eColorAcc.setText(colorAccSem);
        Element eTipoLetra=accSem.getChild("tipoLetra");
        eTipoLetra.setText(tipoLetra);
        Element eSizeAcciones=accSem.getChild("sizeAcciones");
        eSizeAcciones.setText(Integer.toString(sizeAcciones));
        
    }
/**
 * 
 * @return color of the read terminals
 */
    public String getColorLeido() {
        return colorLeido;
    }
/**
 * 
 * @param colorLeido 
 * color of the read terminals
 */
    public void setColorLeido(String colorLeido) {
        this.colorLeido = colorLeido;
    }
/**
 * 
 * @return color of the pending terminals
 */
    public String getColorPend() {
        return colorPend;
    }
/**
 * 
 * @param colorPend 
 * color of the pending terminals
 */
    public void setColorPend(String colorPend) {
        this.colorPend = colorPend;
    }
/**
 * 
 * @return color of the letter of the terminals
 */
    public String getLetraTerminal() {
        return letraTerminal;
    }
/**
 * 
 * @param letraTerminal 
 * color of the letter of the terminals
 */
    public void setLetraTerminal(String letraTerminal) {
        this.letraTerminal = letraTerminal;
    }
/**
 * 
 * @return color of the letter of the no terminals
 */
    public String getLetraNoTerminal() {
        return letraNoTerminal;
    }
/**
 * 
 * @param letraNoTerminal 
 * color of the letter of the no terminals
 */
    public void setLetraNoTerminal(String letraNoTerminal) {
        this.letraNoTerminal = letraNoTerminal;
    }
/**
 * 
 * @return color of the semantics actions
 */
    public String getColorAccSem() {
        return colorAccSem;
    }
/**
 * 
 * @param colorAccSem 
 *  color of the semantics actions
 */
    public void setColorAccSem(String colorAccSem) {
        this.colorAccSem = colorAccSem;
    }
/**
 * 
 * @return type of letter 
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

   
    
   
    
    
}
