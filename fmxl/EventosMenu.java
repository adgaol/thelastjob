package fmxl;


import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuListener;
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
public class EventosMenu  {
   
    /**
     * function to do the actions of process menu
     * @return the listener of menu
     */
    public static MenuListener listenerConfiguracion(){
        MenuListener o = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
               JOptionPane.showMessageDialog(null, "Configuración en construcción");
                
            }
            
            @Override
            public void menuDeselected(MenuEvent e) {
               
            }
            
            @Override
            public void menuCanceled(MenuEvent e) {
                
            }
        };
        return o;
    }
 /**
  * action to new traductor or new string
  * @return Action listener
  */
    public static ActionListener listenerNuevo(){
         ActionListener o = new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if(e.getActionCommand().equals("Traductor")){
                     System.out.println("nuevo traductor");
                     JOptionPane.showMessageDialog(null, "Se llamará a otra aplicación para diseñar un nuevo traductor");
                 }else{
                     System.out.println("nueva cadena de entrada");
                     
                     JOptionPane.showInputDialog("introduzca la cadena deseada (No implementado)");
                 }
               
             }
           
                
           
        };
        return o;
    }
    /**
     * Action to load existing traductor or string
     * @param a
     * app
     * @return action listener
     */
     public static ActionListener listenerCargar(MiApp a){
         ActionListener o = new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if(e.getActionCommand().equals("Traductor")){
                     
                     elegirArchivo(a,"xml");
                 }else{
                     
                     elegirArchivo(a,"txt");
                 }
               
             }
           
                
           
        };
        return o;
    }
/**
 *Action listener to save the traductor or the string
 * @param a
 * app
 * @return action listener 
 */
     public static ActionListener listenerGuardar(MiApp a){
         ActionListener o = new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 if(e.getActionCommand().equals("Traductor")){
                     
                     JOptionPane.showMessageDialog(null, "Se guardará la especificación del traductor actual (por implementar).");
                 }else{
                     
                     JOptionPane.showMessageDialog(null, "Se guardará la cadena de entrada actual (por implementar). ");
                 }
               
             }
           
                
           
        };
        return o;
    }
     /**
      * 
      * @param s
      * Action of menu to execute
      * @param app
      * app
      * @param m
      * menu (don't used)
      */
    public static void botonMenu(String s,MiApp app,MiMenu m){
        switch(s){
            case "Inicio":
                irInicio(app);
             break;
            case "Fin":
                irFin(app);
             break;
            case "Elegir paso":
               ElegirPaso(app);
                
                break;
            case "Siguiente":
                app.construirArbol();
            break;
            case "Anterior":
                app.eliminar();
            break;
            case "Ver ayuda":                
                File path=new File("./manuales/manualUsuario.pdf");
        
            try {
                Desktop.getDesktop().open(path);
                } catch (IOException ex) {
                    Logger.getLogger(EventosMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            break;
            case "Acerca de":
                JOptionPane.showMessageDialog(null, "Trabajo de Fin de Grado:\nJosé Manuel Loeches. \nGrado en Ingeniería Informática, Universidad Rey Juan Carlos"
                        + "\nCorreo electrónico autor: chemapkmn@gmail.com\nDirigido por Jaime Urquiza. \nCorreo electrónico tutor: jaime.urquiza@urjc.es \nÚltima revisión:08/11/2018");
            break;
            case "Editar configuración":
                editarConfiguracion(app);
            break;
            case "Restablecer configuración por defecto":
                restablecerConfiguracion(app);
            break;
            
            
        }
    }
    /**
     * choose the example traductor
     * @param app
     * app
     * @param tipo 
     * type of file
     */
    public static  void elegirArchivo(MiApp app,String tipo){
        // muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
                JFileChooser selectorArchivos = new JFileChooser();
                selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                //Cambiar al directorio Windows
                //selectorArchivos.setCurrentDirectory(new File("D:\\datos\\urjc\\4\\TFG\\TFGv0\\src"));
                selectorArchivos.setCurrentDirectory(new File("./traductores"));
                 FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros "+ tipo, tipo);
                selectorArchivos.setFileFilter(filter);

                // indica cual fue la accion de usuario sobre el jfilechooser
                int respuesta = selectorArchivos.showOpenDialog(app);
                
                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    //Crear un objeto File con el archivo elegido
                    File archivoElegido = selectorArchivos.getSelectedFile();
                    //Mostrar el nombre del archvivo en un campo de texto                   
                    String ruta =  archivoElegido.getAbsolutePath();
                     System.out.println(ruta);
                     //Al elegir fichero cerrar ventana y volver a abrir?
                     //app.hide();
                    
                    ClaseMain.inicio(ruta,app.getcTerminales(),app.getcNoTerminales(),
                            app.getLetraTerminales(),app.getLetraNoTerminales(),app.getColorCadLeido(),
                            app.getColorCadPendiente(),app.getSizeLetra(),app.getSizeLetraTraductor(),
                            app.getSizeCadena(),app.getColorAccSem(),app.getTipoLetra(),app.getSizeAcciones(),app.getZoomInicial());
                     app.setVisible(false);
                     app.dispose();
                    
                }
    }
    /**
     * Back to the init of the execution of the tree
     * @param app 
     * app
     */
    public static void irInicio(MiApp app){
        
        for(int i=app.contador;i>0;i--){
            app.eliminar();
        }
        app.setPosicionTerm(0);
       
    }
   /**
    * go to the end of the execution of the tree
    * @param app 
    */ 
     public static void irFin(MiApp app){
        for(int i=app.contador;i<app.numNodos;i++){
            app.construirArbol();
        }
    }
     /*
     Abrimos cuadro de diálogo para preguntar al usuario a qué paso quiere ir 
     */
    
/**
 * permit to choose a step of the execution and go to that step
 * (don't used)
 * @param app 
 * app
 */
    private static void ElegirPaso(MiApp app) {
        String respuesta=JOptionPane.showInputDialog("Escriba el número de paso al que desea ir (0-"+app.getNumNodos()+").");
        if(respuesta!=null){
            try{
                int numero=Integer.parseInt(respuesta);
                if((numero>=0)&& (numero<=app.getNumNodos()))
                    irPaso(numero,app); 
                else{                
                    JOptionPane.showMessageDialog(null, "número fuera de rango");
                    ElegirPaso(app);
                }

            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Tiene que escribir un número");
                ElegirPaso(app);
            }
        }
        
    }
/**
 * go to the step choosen
 * @param pasoElegido
 * step choosen
 * @param app 
 * app
 */
    public static void irPaso(int pasoElegido, MiApp app) {
       if(pasoElegido>app.contador){
           for(int i=app.contador;i<pasoElegido;i++){
            app.construirArbol();
            }
       }
         else{
                for(int i=app.contador;i>pasoElegido;i--){
                    app.eliminar();
                }    
            }
       }
 /**
  * create the editor of configuration
  * @param app 
  * app
  */       
    public static void editarConfiguracion(MiApp app){
        VentanaConfig vC = new VentanaConfig();
        vC.crearEditor(app);
        /*
        JTextField textfield = new JTextField("nombre",20);
        JButton boton = new JButton("Enviar");
        app.setVisible(false);
        app.dispose();
       ClaseMain.inicio(app.getFicheroXml().getRuta());
*/
    }
/*
    creamos ventana de edición
    */
    
    /**
     * 
     * @param ventana
     * section of the object that is going to change color
     * @param a
     * app
     * @param terminal
     * if is the terminals or the no terminals
     * @return action listener
     */
        public static ActionListener elegirColor(JFrame ventana,MiApp a,boolean terminal){
            
          ActionListener listenerColores = new ActionListener(){
              
            @Override
            public void actionPerformed(ActionEvent e) {
                //miColor = JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
                obtenerColor(ventana,a,terminal);
            }

            
            
        };
        return listenerColores;
    }
 /**
  * obtain the color 
  * @param ventana
  * @param a
  * @param terminal 
  */
    static void obtenerColor (JFrame ventana,MiApp a,boolean terminal){
        Color c=JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
        if(terminal)
            a.setcTerminales(c);
        else
            a.setcNoTerminales(c);
    }
    
    

    /**
     * Generamos el visualizador con los nuevos valores
     * @param app
     * app
     * @param v1
     * size of the letter of the tree
     * @param v2
     * size of the letter of the grammar
     * @param v3
     * size of the letter of the string
     */
    public static void generarVisualizador(MiApp app,String v1, String v2, String v3){
        app.setVisible(false); 
        int cont=app.getContador();
        MiApp nuevaApp=ClaseMain.inicio(app.getFicheroXml().getRuta(),
                app.getcTerminales(),app.getcNoTerminales(),app.getLetraTerminales(),
                app.getLetraNoTerminales(),app.getColorCadLeido(),app.getColorCadPendiente(),
                Integer.parseInt(v1),Integer.parseInt(v2),Integer.parseInt(v3),app.getColorAccSem(),app.getTipoLetra(),app.getSizeAcciones(),app.getZoomInicial());
        Configuracion conf= new Configuracion();
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
        
        conf.guardarConfiguracion(".//config//configActual.xml",Integer.parseInt(v1),Integer.parseInt(v2),Integer.parseInt(v3),hTerm,hnTerm,hlTerm,hlnTerm,hLeido,hPend,hColorAccSem,tipoLetra,app.getSizeAcciones(),app.getZoomInicial());
        irPaso(cont,nuevaApp);
        app.dispose();
       
    }
/**
 * restore the default configuration
 * @param app 
 * app
 */
    private static void restablecerConfiguracion(MiApp app) {
        Configuracion conf = new Configuracion();
        conf.cargarConfiguracion(".//config//configDefecto.xml");
        String colTerminal=conf.getColorTerminal();       
        app.setcTerminales(new Color( Integer.parseInt(colTerminal, 16)));
        String colnTerminal=conf.getColorNoTerminal();  
        app.setcNoTerminales(new Color( Integer.parseInt(colnTerminal, 16)));
        
         String letraTerminal=conf.getLetraTerminal();       
        app.setLetraTerminales(new Color( Integer.parseInt(letraTerminal, 16)));
        String letranTerminal=conf.getLetraNoTerminal();  
        app.setLetraNoTerminales(new Color( Integer.parseInt(letranTerminal, 16)));
        
        String colLeido=conf.getColorLeido();       
        app.setColorCadLeido(new Color( Integer.parseInt(colLeido, 16)));
        String colPend=conf.getColorPend();  
        app.setColorCadPendiente(new Color( Integer.parseInt(colPend, 16)));
        app.setZoomInicial(conf.getZoom());
        String colorAccSem=conf.getColorAccSem();
        app.setColorAccSem(new Color(Integer.parseInt(colorAccSem,16)));        
        app.setTipoLetra(conf.getTipoLetra());
        
        app.setSizeLetra(conf.getLetraArbol());
        app.setSizeLetraTraductor(conf.getLetraTraductor());
        app.setSizeCadena(conf.getLetraCadena());
        app.setSizeCadena(conf.getSizeAcciones());
        
        app.zoom();
        app.getMiZoom().setValue(app.getZoomInicial());
        
        VentanaConfig v= new VentanaConfig();
        v.setApp(app);
        v.generarVisualizador();
    }
    /**
     * choose the translator at the beginning of the app
     * @param app
     * app
     * @param v
     * initial window
     * @param tipo 
     * type of file
     */
    public static void elegirArchivoInicio(MiApp app,VentanaIni v,String tipo){
        
        // muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
                JFileChooser selectorArchivos = new JFileChooser();
                selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                //Cambiar al directorio Windows
                //selectorArchivos.setCurrentDirectory(new File("D:\\datos\\urjc\\4\\TFG\\TFGv0\\src"));
                selectorArchivos.setCurrentDirectory(new File("./traductores"));
                 FileNameExtensionFilter filter = new FileNameExtensionFilter("Ficheros "+ tipo, tipo);
                selectorArchivos.setFileFilter(filter);

                // indica cual fue la accion de usuario sobre el jfilechooser
                int respuesta = selectorArchivos.showOpenDialog(app);
                
                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    //Crear un objeto File con el archivo elegido
                    File archivoElegido = selectorArchivos.getSelectedFile();
                    //Mostrar el nombre del archvivo en un campo de texto                   
                    String ruta =  archivoElegido.getAbsolutePath();
                     System.out.println(ruta);
                     //Al elegir fichero cerrar ventana y volver a abrir?
                     //app.hide();
                    
                    ClaseMain.inicio(ruta,app.getcTerminales(),app.getcNoTerminales(),
                            app.getLetraTerminales(),app.getLetraNoTerminales(),app.getColorCadLeido(),
                            app.getColorCadPendiente(),app.getSizeLetra(),app.getSizeLetraTraductor(),
                            app.getSizeCadena(),app.getColorAccSem(),app.getTipoLetra(),app.getSizeAcciones(),app.getZoomInicial());
                     app.setVisible(false);
                     app.dispose();
                     v.setVisible(false);
                     v.dispose();
                    
                }
                else{
                    app.setVisible(false);
                    app.dispose();
                    v.setVisible(false);
                    v.dispose();
                }
    }
/**
 * Choose and change the color of the string
 * (not used)
 * @param ventana
 * @param app
 * @param leido
 * @return action listener
 */
    private static ActionListener elegirColorCadena(JFrame ventana, MiApp app, boolean leido) {
            
          ActionListener listenerColores = new ActionListener(){
              
            @Override
            public void actionPerformed(ActionEvent e) {
                //miColor = JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
                obtenerColorCadena(ventana,app,leido);
            }

            

            
            
        };
        return listenerColores;
    }
  /**
   * 
   * @param ventana
   * window to select color
   * @param app
   * app
   * @param leido 
   * if the color is to the read string or the pending string
   */  
      private static void obtenerColorCadena(JFrame ventana, MiApp app, boolean leido) {
                  Color c=JColorChooser.showDialog(ventana, "elegir colores", Color.yellow);
        if(leido)
            app.setColorCadLeido(c);
        else
            app.setColorCadPendiente(c);
        }
}
