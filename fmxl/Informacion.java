package fmxl;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase que contiene toda la información de ese paso
 * @author chema
 */
public class Informacion {
    private int id;
    private String tipo;
    private String leido;
    private String pendiente;
    private String elemento;
    private String valor;
    private Regla regla;
    private List<Simbolo> simbolosActualizados;
  //  private List<Simbolo> atribAcciones;
   
    
/**
 * 
 * @param id
 * id of the of the step
 * @param tipo
 * type of the step(reducction,displacement or derivation)
 * @param leido
 * read terminals 
 * @param pendiente
 * pending terminals
 * @param elemento
 * elements that are being processed
 * @param valor 
 * semantic actions
 */
    public Informacion(int id, String tipo, String leido, String pendiente, String elemento, String valor) {
        this.id = id;
        this.tipo = tipo;
        this.leido = leido;
        this.pendiente = pendiente;
        this.elemento = elemento;
        this.valor = valor;
        
    }

/**
 * 
* @param tipo
 * type of the step(reducction,displacement or derivation)
 * @param leido
 * read terminals 
 * @param pendiente
 * pending terminals
 * @param elemento
 * elements that are being processed
 * @param valor 
 * semantic actions
 */    
    public Informacion(String tipo, String leido, String pendiente, String elemento, String valor) {
        this.tipo = tipo;
        this.leido = leido;
        this.pendiente = pendiente;
        this.elemento = elemento;
        this.valor = valor;
        
    }
   public Informacion() {
    }

    /**
     * 
     * @return type of step 
     */
    public String getTipo() {
        return tipo;
    }
/**
 * 
 * @param tipo 
 * type of step
 */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
/**
 * 
 * @return read terminals of the string
 */
    public String getLeido() {
        return leido;
    }
/**
 * 
 * @param leido 
 * read terminals of the string
 */
    public void setLeido(String leido) {
        this.leido = leido;
    }
/**
 * 
 * @return pending terminals of the string
 */
    public String getPendiente() {
        return pendiente;
    }
/**
 * 
 * @param pendiente 
 * pending terminals of the string
 */
    public void setPendiente(String pendiente) {
        this.pendiente = pendiente;
    }
/**
 * 
 * @return elements that are being processed
 */
    public String getElemento() {
        return elemento;
    }
/**
 * 
 * @param elemento 
 * elements that are being processed
 */
    public void setElemento(String elemento) {
        this.elemento = elemento;
    }
/**
 * 
 * @return semantic actions
 */
    public String getValor() {
        return valor;
    }
/**
 * 
 * @param valor 
 * semantic actions
 */
    public void setValor(String valor) {
        this.valor = valor;
    }   
/**
 * 
 * @return id of the step
 */
    public int getId() {
        return id;
    }
/**
 * 
 * @param id 
 * id of the step
 */
    public void setId(int id) {
        this.id = id;
    }

   
/**
 * 
 * @return rule of the grammar 
 */
    public Regla getRegla() {
        return regla;
    }
/**
 * 
 * @param regla 
 * rule of the grammar 
 */
    public void setRegla(Regla regla) {
        this.regla = regla;
    }
/**
 * 
 * @return all symbols of the grammar
 */
    public List<Simbolo> getSimbolosActualizados() {
        return simbolosActualizados;
    }
/**
 * 
 * @param simbolosActualizados 
 * all symbols of the grammar
 */
    public void setSimbolosActualizados(List<Simbolo> simbolosActualizados) {
        this.simbolosActualizados = simbolosActualizados;
    }

   
     
    
}
