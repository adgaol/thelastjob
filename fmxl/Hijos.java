package fmxl;


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
public class Hijos {
    
    private List<Object> lista;
/**
 * 
 * @param lista 
 */
    public Hijos(List<Object> lista) {
        this.lista = lista;
    }

    public Hijos() {
    }
    
/**
 * 
 * @return list of childrens
 */
    public List<Object> getLista() {
        return lista;
    }
/**
 * 
 * @param lista
 * list the childrens
 */
    public void setLista(List<Object> lista) {
        this.lista = lista;
    }
    
    
    
}
