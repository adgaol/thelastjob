package fmxl;


import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class MiMenu implements ActionListener,
                                     ItemListener, MouseListener {
    
     MiApp app;
            /* Declaro mi JMenuBar, éste me dibujara una barra horizontal */
      private JMenuBar barraMenu;

      /* Declaro los JMenu */
      private JMenu menuArchivo,menuConfiguracion,menuAyuda,menuProceso,menuNuevo,menuCargar,menuGuardar;

      /* Declaro todos los JMenuItem */
      private JMenuItem traductorN,cadenaN,traductorC,cadenaC,traductorG,cadenaG;
       private JMenuItem guardarTodo, configuracion, construir,inicio, fin,sig,ant;
       private JMenuItem verAyuda, acercaDe;
    private final JMenuItem editarConfiguracion,restablecerConfiguracion;
   
/**
 * initialize and add the funtionality to menu
 * @param app 
 * app
 */
    public MiMenu(MiApp app) {
        this.app=app;
         this.barraMenu          = new JMenuBar();
         
        this.menuArchivo            = new JMenu("Archivo");   
        menuArchivo.setMnemonic('A');
        this.menuConfiguracion        = new JMenu("Configuración");
        
        this.menuAyuda          = new JMenu("Ayuda");
        this.menuProceso     = new JMenu("Ejecución");
        
        this.menuNuevo     = new JMenu("Nuevo");
       
        this.menuCargar     = new JMenu("Cargar");
        this.menuGuardar     = new JMenu("Guardar");
         
        this.guardarTodo        = new JMenuItem("Guardar todo");
        
        this.editarConfiguracion= new JMenuItem("Editar configuración");
        editarConfiguracion.addActionListener(this);
        this.restablecerConfiguracion= new JMenuItem("Restablecer configuración por defecto");
        restablecerConfiguracion.addActionListener(this);
        
        this.verAyuda      = new JMenuItem("Ver ayuda");
        verAyuda.addActionListener(this);
        this.acercaDe     = new JMenuItem("Acerca de");
        acercaDe.addActionListener(this);
         
        this.traductorN          = new JMenuItem("Traductor");
        traductorN.addActionListener(EventosMenu.listenerNuevo());
        this.cadenaN          = new JMenuItem("Cadena");
        cadenaN.addActionListener(EventosMenu.listenerNuevo());
        
        this.traductorC          = new JMenuItem("Traductor");             
        this.traductorC.addActionListener(EventosMenu.listenerCargar(this.app));
        
        this.cadenaC          = new JMenuItem("Cadena");
        cadenaC.addActionListener(EventosMenu.listenerCargar(this.app));
        this.traductorG          = new JMenuItem("Traductor");        
        traductorG.addActionListener(EventosMenu.listenerGuardar(this.app));
        this.cadenaG          = new JMenuItem("Cadena");
         cadenaG.addActionListener(EventosMenu.listenerGuardar(this.app));
         
        
        this.construir = new JMenuItem("Construir");
         this.inicio=new JMenuItem("Inicio");
        this.inicio.addActionListener(this);
        inicio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME,0));
        this.fin = new JMenuItem("Fin");
        this.fin.addActionListener(this);
        fin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_END,0));
        
        this.sig           = new JMenuItem("Siguiente");        
        this.sig.addActionListener(this);
        //sig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        sig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0));
        this.ant          = new JMenuItem("Anterior");       
        this.ant.addActionListener(this);
        ant.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0));
       
        
        
    }
    /**
     * build the menu
     */
    public void insertarMenu(){
        this.barraMenu.add(this.menuArchivo);
        /*
        JMenu (Archivo)
    |           |_______ JMenu (Nuevo)
			|_______ JMenuItem (Proyecto)
    |                   |_______ JMenuItem (Gramática)
    |                   |_______ JMenuItem (Cadena de entrada)

    |           |_______ JMenu (Cargar)
			|_______ JMenuItem (Proyecto)
    |                   |_______ JMenuItem (Gramática)
    |                   |_______ JMenuItem (Cadena de entrada)
                |_______ JMenu (Guardar)
			|_______ JMenuItem (Proyecto)
    |                   |_______ JMenuItem (Gramática)
    |                   |_______ JMenuItem (Cadena de entrada)
                |_______ JMenuItem (Guardar todo

        */
            this.menuArchivo.add(this.menuNuevo);
                this.menuNuevo.add(this.traductorN);
                
                this.menuNuevo.add(this.cadenaN);
            this.menuArchivo.add(this.menuCargar);
                this.menuCargar.add(this.traductorC);
                
                this.menuCargar.add(this.cadenaC);
            this.menuArchivo.add(this.menuGuardar);
                this.menuGuardar.add(this.traductorG);
                
                this.menuGuardar.add(this.cadenaG);
        
      
            
        this.barraMenu.add(this.menuProceso);
        /*
        |_______ JMenu (Proceso)
                 |_______ JMenuItem (Anterior)
                |_______ JMenuItem (Siguiente)    |          
    |           |_______ JMenuItem (Inicio)
    |           |_______ JMenuItem (Final)
		  |           
		

        */
        
            this.menuProceso.add(this.inicio);           
            this.menuProceso.add(this.ant);
            this.menuProceso.add(this.sig);
            this.menuProceso.add(this.fin);
        
           
            
            
           
         this.barraMenu.add(this.menuConfiguracion);
         this.menuConfiguracion.add(this.editarConfiguracion);
         this.menuConfiguracion.add(this.restablecerConfiguracion);
         
        
            
            
        this.barraMenu.add(this.menuAyuda);
        /*
            |_______ JMenu (Ayuda)
                |_______ JMenuItem (Ver la Ayuda)
		|_______ JMenuItem (Revisión)


        */
            this.menuAyuda.add(this.verAyuda);
            this.menuAyuda.add(this.acercaDe);
    }
       
     @Override
    public void mouseClicked(MouseEvent e) {
       System.out.println("click en "+e.getButton());
    }   
/**
 * 
 * @return menu bar
 */
    public JMenuBar getBarraMenu() {
        return barraMenu;
    }
/**
 * 
 * @param barraMenu 
 * menu bar
 */
    public void setBarraMenu(JMenuBar barraMenu) {
        this.barraMenu = barraMenu;
    }
/**
 * 
 * @return file menu
 */
    public JMenu getMenuArchivo() {
        return menuArchivo;
    }
/**
 * 
 * @param menuArchivo 
 * file menu
 */
    public void setMenuArchivo(JMenu menuArchivo) {
        this.menuArchivo = menuArchivo;
    }

   
/**
 * 
 * @return help menu
 */
    public JMenu getMenuAyuda() {
        return menuAyuda;
    }
/**
 * 
 * @param menuAyuda 
 * help menu
 */
    public void setMenuAyuda(JMenu menuAyuda) {
        this.menuAyuda = menuAyuda;
    }
/**
 * 
 * @return process menu
 */
    public JMenu getMenuProceso() {
        return menuProceso;
    }
/**
 * 
 * @param menuProceso 
 * process menu
 */
    public void setMenuProceso(JMenu menuProceso) {
        this.menuProceso = menuProceso;
    }
/**
 * 
 * @return  new menu
 */
    public JMenu getMenuNuevo() {
        return menuNuevo;
    }
/**
 * 
 * @param menuNuevo 
 * new menu
 */
    public void setMenuNuevo(JMenu menuNuevo) {
        this.menuNuevo = menuNuevo;
    }
/**
 * 
 * @return load menu
 */
    public JMenu getMenuCargar() {
        return menuCargar;
    }
/**
 * 
 * @param menuCargar 
 * load menu
 */
    public void setMenuCargar(JMenu menuCargar) {
        this.menuCargar = menuCargar;
    }
/**
 * 
 * @return save menu
 */
    public JMenu getMenuGuardar() {
        return menuGuardar;
    }
/**
 * 
 * @param menuGuardar 
 * save menu
 */
    public void setMenuGuardar(JMenu menuGuardar) {
        this.menuGuardar = menuGuardar;
    }
/**
 * 
 * @return new translate option of the menu
 */
    public JMenuItem getTraductorN() {
        return traductorN;
    }
/**
 * 
 * @param traductorN 
 * new translate option of the menu
 */
    public void setTraductorN(JMenuItem traductorN) {
        this.traductorN = traductorN;
    }

    

   

   
/**
 * 
 * @return new string option of the menu
 */
    public JMenuItem getCadenaN() {
        return cadenaN;
    }
/**
 * 
 * @param cadenaN 
 * new string option of the menu
 */
    public void setCadenaN(JMenuItem cadenaN) {
        this.cadenaN = cadenaN;
    }
/**
 * 
 * @return load translate option of the menu 
 */
    public JMenuItem getTraductorC() {
        return traductorC;
    }
/**
 * 
 * @param traductorC 
 * load translate option of the menu 
 */
    public void setTraductorC(JMenuItem traductorC) {
        this.traductorC = traductorC;
    }

    

   
/**
 * 
 * @return load string option of the menu
 */
    public JMenuItem getCadenaC() {
        return cadenaC;
    }
/**
 * 
 * @param cadenaC 
 * load string option of the menu
 */
    public void setCadenaC(JMenuItem cadenaC) {
        this.cadenaC = cadenaC;
    }
/**
 * 
 * @return save translate option of the menu
 */
    public JMenuItem getTraductorG() {
        return traductorG;
    }
/**
 * 
 * @param traductorG 
 * save translate option of the menu
 */
    public void setTraductorG(JMenuItem traductorG) {
        this.traductorG = traductorG;
    }


/**
 * 
 * @return save string option of the menu
 */
    public JMenuItem getCadenaG() {
        return cadenaG;
    }
/**
 * 
 * @param cadenaG 
 * save string option of the menu
 */
    public void setCadenaG(JMenuItem cadenaG) {
        this.cadenaG = cadenaG;
    }
/**
 * 
 * @return save all option of the menu
 */
    public JMenuItem getGuardarTodo() {
        return guardarTodo;
    }
/**
 * 
 * @param guardarTodo 
 * save all option of the menu
 */
    public void setGuardarTodo(JMenuItem guardarTodo) {
        this.guardarTodo = guardarTodo;
    }
/**
 * 
 * @return configuration option of the menu
 */
    public JMenuItem getConfiguracion() {
        return configuracion;
    }
/**
 * 
 * @param configuracion 
 * configuration option of the menu
 */
    public void setConfiguracion(JMenuItem configuracion) {
        this.configuracion = configuracion;
    }
/**
 * 
 * @return build menu
 */
    public JMenuItem getConstruir() {
        return construir;
    }
/**
 * 
 * @param construir 
 * build menu
 */
    public void setConstruir(JMenuItem construir) {
        this.construir = construir;
    }
/**
 * 
 * @return start option of the menu
 */
    public JMenuItem getInicio() {
        return inicio;
    }
/**
 * 
 * @param inicio 
 * start option of the menu
 */
    public void setInicio(JMenuItem inicio) {
        this.inicio = inicio;
    }
/**
 * 
 * @return end option of the menu
 */
    public JMenuItem getFin() {
        return fin;
    }
/**
 * 
 * @param fin 
 * end option of the menu
 */
    public void setFin(JMenuItem fin) {
        this.fin = fin;
    }   
    
/**
 * 
 * @return  next option of the menu
 */
    public JMenuItem getSig() {
        return sig;
    }
/**
 * 
 * @param sig
 * next option of the menu
 */
    public void setSig(JMenuItem sig) {
        this.sig = sig;
    }
/**
 * 
 * @return before option of the menu
 */
    public JMenuItem getAnt() {
        return ant;
    }
/**
 * 
 * @param ant 
 * before option of the menu
 */
    public void setAnt(JMenuItem ant) {
        this.ant = ant;
    }
/**
 * 
 * @return see help option of the menu
 */
    public JMenuItem getVerAyuda() {
        return verAyuda;
    }
/**
 * 
 * @param verAyuda 
 * see help option of the menu
 */
    public void setVerAyuda(JMenuItem verAyuda) {
        this.verAyuda = verAyuda;
    }
/**
 * 
 * @return the about option of the menu 
 */
    public JMenuItem getAcercaDe() {
        return acercaDe;
    }
/**
 * 
 * @param acercaDe 
 * the about option of the menu 
 */
    public void setAcercaDe(JMenuItem acercaDe) {
        this.acercaDe = acercaDe;
    }

   

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("evento " + e.getActionCommand());
        EventosMenu.botonMenu(e.getActionCommand(),this.app,this);
         
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
       
       
    
}
