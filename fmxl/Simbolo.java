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
public class Simbolo {
    private boolean terminal;
    private String valor;
    private int nivel;
    private int id;
/**
 * 
 * @param valor
 * value of the symbol
 * @param terminal 
 * if is terminal or no terminal
 */
    public Simbolo(String valor, boolean terminal) {
        this.terminal = terminal;
        this.valor = valor;
    }

    public Simbolo() {
    }
    
/**
 * 
 * @return if is terminal true else false
 */
    public boolean isTerminal() {
        return terminal;
    }
/**
 * 
 * @param terminal 
 * if is terminal or no terminal
 */
    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }
/**
 * 
 * @return value of the symbol
 */
    public String getValor() {
        return valor;
    }
/**
 * 
 * @param valor 
 * value of the symbol
 */
    public void setValor(String valor) {
        this.valor = valor;
    }
/**
 * 
 * @return value of the symbol in type string 
 */
    @Override
    public String toString() {
        return this.valor;
    }
/**
 * 
 * @return level of the tree
 */
    public int getNivel() {
        return nivel;
    }
/**
 * 
 * @param nivel 
 * level of the tree
 */
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
/**
 * 
 * @return id of the symbol
 */
    public int getId() {
        return id;
    }
/**
 * 
 * @param id 
 * id of the symbol
 */
    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    
}
