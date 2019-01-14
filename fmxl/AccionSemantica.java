package fmxl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
class AccionSemantica {
    int pos;
    boolean fin;
    String valor;
    public AccionSemantica() {
    }
/**
 * 
 * @return position of semantic action
 */
    public int getPos() {
        return pos;
    }
/**
 * 
 * @param pos 
 * position of semantic action
 */
    public void setPos(int pos) {
        this.pos = pos;
    }
/**
 * 
 * @return value of semantic action
 */
    public String getValor() {
        return valor;
    }
/**
 * 
 * @param valor
 * value of semantic action
 */
    public void setValor(String valor) {
        this.valor = valor;
    }
/**
 * 
 * @return if is the last semantic action of rule
 */
    public boolean isFin() {
        return fin;
    }
/**
 * 
 * @param fin 
 * if is the last semantic action of rule
 */
    public void setFin(boolean fin) {
        this.fin = fin;
    }
    
    
    
    
}
