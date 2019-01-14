package fmxl;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class VentanaIni extends JFrame {
     JPanel panelBotones= new JPanel();
 JSplitPane splitPane = new JSplitPane();
       JSplitPane splitPane2 = new JSplitPane();
       JSplitPane splitPane3= new JSplitPane();
        GridLayout layout = new GridLayout(1,4,1,1);
        /**
         * constructor
         * initialize the principal window
         */
    public VentanaIni() {
         super("Depurador TDS ");
         this.setExtendedState(MAXIMIZED_BOTH);
        splitPane.setResizeWeight(0.7);
                splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
                this.getContentPane().add(splitPane, BorderLayout.CENTER);
                splitPane.setBackground(Color.WHITE);
                splitPane.setLeftComponent(new JPanel());
                splitPane.setRightComponent(new JPanel());
               
                
                
                splitPane2.setResizeWeight(0.7);
                splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
                splitPane2.setBackground(Color.WHITE);
                splitPane2.setTopComponent(new JPanel());
                splitPane.setRightComponent(splitPane2);
                
                splitPane3.setResizeWeight(0.95);                
                splitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
                splitPane3.setTopComponent(new JPanel());
                panelBotones.setLayout(layout);
                splitPane3.setBottomComponent(panelBotones);
                splitPane2.setBottomComponent(splitPane3);
                
                  
                  
                Boton b3=new Boton("Inicio");//creating instance of JButton 
               
                this.colocarBoton(b3);
                
                
                  Boton b2=new Boton("Anterior");//creating instance of JButton 
               
                this.colocarBoton(b2);
                
                 Boton b=new Boton("Siguiente");//creating instance of JButton  
               
                this.colocarBoton(b);
              
                 Boton b4=new Boton("Fin");//creating instance of JButton  
                
                this.colocarBoton(b4);
                
                
    }
    /**
     * add a boton to the panel
     * @param s 
     * boton to add
     */
    public void colocarBoton(Boton s){
            this.panelBotones.add(s.getBoton());            
                      
        }
}
