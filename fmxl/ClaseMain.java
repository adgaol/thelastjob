package fmxl;


import java.awt.Color;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class ClaseMain {
    /**
     * initialized the app and load the configuration
     * @param args 
     * 
     */    
    public static void main(String[] args)
	{
            Configuracion lectConf= new Configuracion();
            lectConf.cargarConfiguracion(".//config//configActual.xml");
            //Color cTerminalesDefecto= new Color(Integer.parseInt("FF5118",16));  
            Color cTerminalesDefecto= new Color(Integer.parseInt(lectConf.getColorTerminal(),16));
            
            //Color cNoTerminalesDefecto=new Color(Integer.parseInt("82EB20",16));
            Color cNoTerminalesDefecto= new Color(Integer.parseInt(lectConf.getColorNoTerminal(),16));
            
            Color cLetraTermDef= new Color(Integer.parseInt(lectConf.getLetraTerminal(),16));
            
            //Color cNoTerminalesDefecto=new Color(Integer.parseInt("82EB20",16));
            Color cLetraNTermDef= new Color(Integer.parseInt(lectConf.getLetraNoTerminal(),16));
            
             Color cLeidoDefecto= new Color(Integer.parseInt(lectConf.getColorLeido(),16));
            
            //Color cNoTerminalesDefecto=new Color(Integer.parseInt("82EB20",16));
            Color cPendDefecto= new Color(Integer.parseInt(lectConf.getColorPend(),16));
            
             Color cAccSem= new Color(Integer.parseInt(lectConf.getColorAccSem(),16));
            
            
                MiApp a=inicio(".//traductores//descend.xml",cTerminalesDefecto,cNoTerminalesDefecto,cLetraTermDef,
                        cLetraNTermDef,cLeidoDefecto,cPendDefecto,lectConf.getLetraArbol(),lectConf.getLetraTraductor(),lectConf.getLetraCadena(),
                        cAccSem,lectConf.getTipoLetra(),lectConf.getSizeAcciones(),lectConf.getZoom());    
               a.setVisible(false);
               VentanaIni b=primPantalla();    
                EventosMenu.elegirArchivoInicio(a,b, "xml");
                    
                
	}
    /**
     * Load the example and initialize the app with this example.
     * @param ruta
     * route of the example
     * @param ct
     * color of the terminal
     * @param cnt
     * color of the no terminal
     * @param clt
     * color of the leter of the terminal
     * @param clnt
     * color of the leter of the no terminal
     * @param cl
     * color of the read string 
     * @param cp
     * color of the pending string 
     * @param sizeLetra
     * size of the leter of the tree
     * @param sizeLetra2
     * size of the letter of the grammar
     * @param sizeCadena
     * size of the letter of the string
     * @param accSem
     * color of the letter of the semantic action
     * @param tip
     * type of letter
     * @param sizeAcciones
     * size of the actions
     * @param zoomInicial
     * initial zoom
     * @return initialScreen
     * 
     */
    public static MiApp inicio(String ruta,Color ct, Color cnt,Color clt, Color clnt,Color cl,Color cp,int sizeLetra,int sizeLetra2,int sizeCadena,Color accSem, String tip,int sizeAcciones,int zoomInicial){
                FicheroXML ejemplo = new FicheroXML();
                ejemplo.cargarXml(ruta);
		MiApp frame = new MiApp(ejemplo,ct,cnt,clt,clnt,cl,cp,sizeLetra,sizeLetra2,sizeCadena,accSem,tip,sizeAcciones,zoomInicial);                
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 620);
                frame.splitPane3.setResizeWeight(0.9);
		frame.setVisible(true);     
                
              
                return frame;       
                
    
                
                
    
    }
    /**
     * init the principal screen
     * @return Initial screen  
     * 
     */
    public static VentanaIni primPantalla(){
                
		VentanaIni frame = new VentanaIni();                
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 620);
               
		frame.setVisible(true);     
                
              
                return frame;       
                
    
                
                
    
    }
    /**
     * Initialize the app(not used)
     * @param app
     * @return initialized app
     * 
     */
    static MiApp generar(MiApp app) {
       
        MiApp nuevaApp=ClaseMain.inicio(app.getFicheroXml().getRuta(),app.getcTerminales(),
                app.getcNoTerminales(),app.getLetraTerminales(),app.getLetraNoTerminales(),app.getColorCadLeido(), 
                app.getColorCadPendiente(),app.getSizeLetra(),app.getSizeLetraTraductor(),app.getSizeCadena(),
                app.getColorAccSem(),app.getTipoLetra(), app.getSizeAcciones(),app.getZoomInicial());
        return nuevaApp;
    }
    
    
    
            
                
                
    }
