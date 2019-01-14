package fmxl;


import java.awt.Color;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class ModificacionesTemp {
    private int sizeLetraArbol;
    private int sizeLetraTraductor;
    private int sizeCadena;
    private int sizeAcciones;
    private Color colorTerminales;
    private Color colornTerminales;
    private Color colorLetraTerminales;
    private Color colorLetranTerminales;
    private Color colorCadenaLeido;
    private Color colorCadenaPendiente;
    private Color colorAccSem;
    private String tipoLetra;
    private Object[] traductor;
    private Object leido;
    private Object pendiente;
    private Object accSem;
    public ModificacionesTemp() {
    }
/**
 * 
 * @return size of the letter of the tree
 */
    public int getSizeLetraArbol() {
        return sizeLetraArbol;
    }
/**
 * 
 * @param sizeLetraArbol 
 * size of the letter of the tree
 */
    public void setSizeLetraArbol(int sizeLetraArbol) {
        this.sizeLetraArbol = sizeLetraArbol;
    }
/**
 * 
 * @return color of terminals
 */
    public Color getColorTerminales() {
        return colorTerminales;
    }
/**
 * 
 * @param colorTerminales 
 * color of the terminals
 */
    public void setColorTerminales(Color colorTerminales) {
        this.colorTerminales = colorTerminales;
    }
/**
 * 
 * @return color of no terminals
 */
    public Color getColornTerminales() {
        return colornTerminales;
    }
/**
 * 
 * @param colornTerminales 
 * color of no terminals
 */
    public void setColornTerminales(Color colornTerminales) {
        this.colornTerminales = colornTerminales;
    }
/**
 * 
 * @return color of the letters of terminals
 */
    public Color getColorLetraTerminales() {
        return colorLetraTerminales;
    }
/**
 * 
 * @param colorLetraTerminales
 * color of the letters of terminals
 */
    public void setColorLetraTerminales(Color colorLetraTerminales) {
        this.colorLetraTerminales = colorLetraTerminales;
    }
/**
 * 
 * @return  color of the letters of no terminals
 */
    public Color getColorLetranTerminales() {
        return colorLetranTerminales;
    }
/**
 * 
 * @param colorLetranTerminales 
 * color of the letters of no terminals
 */
    public void setColorLetranTerminales(Color colorLetranTerminales) {
        this.colorLetranTerminales = colorLetranTerminales;
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
 * @return size of the string
 */
    public int getSizeCadena() {
        return sizeCadena;
    }
/**
 * 
 * @param sizeCadena 
 * size of the string
 */
    public void setSizeCadena(int sizeCadena) {
        this.sizeCadena = sizeCadena;
    }
/**
 * 
 * @return array of rules
 */
    public Object[] getTraductor() {
        return traductor;
    }
/**
 * 
 * @param traductor
 * array of rules
 */
    public void setTraductor(Object[] traductor) {
        this.traductor = traductor;
    }
/**
 * 
 * @return pending string
 */
    public Object getPendiente() {
        return pendiente;
    }
/**
 * 
 * @param pendiente 
 * pending string
 */
    public void setPendiente(Object pendiente) {
        this.pendiente = pendiente;
    }
/**
 * 
 * @return color of the read string
 */
    public Color getColorCadenaLeido() {
        return colorCadenaLeido;
    }
/**
 * 
 * @param colorCadenaLeido 
 * color of the read string
 */
    public void setColorCadenaLeido(Color colorCadenaLeido) {
        this.colorCadenaLeido = colorCadenaLeido;
    }
/**
 * 
 * @return color of pending string
 */
    public Color getColorCadenaPendiente() {
        return colorCadenaPendiente;
    }
/**
 * 
 * @param colorCadenaPendiente 
 * color of pending string
 */
    public void setColorCadenaPendiente(Color colorCadenaPendiente) {
        this.colorCadenaPendiente = colorCadenaPendiente;
    }
/**
 * 
 * @return read string
 */
    public Object getLeido() {
        return leido;
    }
/**
 * 
 * @param leido 
 * read string
 */
    public void setLeido(Object leido) {
        this.leido = leido;
    }
/**
 * 
 * @return color of semantics actions
 */
    public Color getColorAccSem() {
        return colorAccSem;
    }
/**
 * 
 * @param colorAccSem 
 * color of semantics actions
 */
    public void setColorAccSem(Color colorAccSem) {
        this.colorAccSem = colorAccSem;
    }
/**
 * 
 * @return type of the letter of semantics actions
 */
    public String getTipoLetra() {
        return tipoLetra;
    }
/**
 * 
 * @param tipoLetra 
 * type of the letter of semantics actions
 */
    public void setTipoLetra(String tipoLetra) {
        this.tipoLetra = tipoLetra;
    }
/**
 * 
 * @return semantic action
 */
    public Object getAccSem() {
        return accSem;
    }
/**
 * 
 * @param accSem 
 * semantic action
 */
    public void setAccSem(Object accSem) {
        this.accSem = accSem;
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
