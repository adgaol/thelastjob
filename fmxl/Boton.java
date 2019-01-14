package fmxl;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class Boton implements MouseListener {
    JButton boton;
    MiApp g;
/**
 * 
 * @param s 
 * value of the button
 */
    public Boton(String s) {
        this.boton= new JButton(s);
        boton.setBounds(130,100,100, 40);//x axis, y axis, width, height  
        boton.addMouseListener(this); 
    }
/**
 * 
 * @return the button
 */
    public JButton getBoton() {
        return boton;
    }
/**
 * 
 * @param boton 
 * the button
 */
    public void setBoton(JButton boton) {
        this.boton = boton;
    }
/**
 * 
 * @return app 
 */
    public MiApp getG() {
        return g;
    }
/**
 * 
 * @param g
 * app
 */
    public void setG(MiApp g) {
        this.g = g;
    }
    
    
/**
 * react  when click a button
 * @param e 
 * evento
 */
    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.boton.getLabel()=="Siguiente"){
            System.out.println("boton siguiente");
            //g.construirArbol2();
            g.construirArbol();
            
        }
        else if(this.boton.getLabel()=="Anterior"){
            System.out.println("botón anterior");
            g.eliminar();
        }else if(this.boton.getLabel()=="Inicio"){            
            EventosMenu.irInicio(g);
        }else if(this.boton.getLabel()=="Fin"){
            EventosMenu.irFin(g);
        }
        g.getMenu().getBarraMenu().requestFocusInWindow();

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
            }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    
    
}
