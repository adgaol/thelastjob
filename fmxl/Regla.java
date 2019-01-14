package fmxl;


import java.util.ArrayList;
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
public class Regla {
    private String valor;
    private List<Simbolo> regla;
    private String id;
    private int tam;
    private List<AccionSemantica> acciones;
    boolean accionesInsertadas = false; //boolean para almacenar si podemos visualizar la acción semántica en la gramática
    List<String> valorAtributos;//el valor de los atributos de la acción semántica
    /**
     * inicialite the array of symbols
     */
    public Regla() {
        this.regla = new ArrayList<>();
    }
/**
 * 
 * @return the symbols of the rule 
 */
    public List<Simbolo> getRegla() {
        return regla;
    }
/**
 * 
 * @param regla 
 * the symbols of the rule 
 */
    public void setRegla(List<Simbolo> regla) {
        this.regla = regla;
    }
    /**
     * add a symbol to the rule
     * @param s 
     * symbol to add
     */
    public void añadir(Simbolo s){
        this.regla.add(s);
    }
/**
 * 
 * @return id of the rule 
 */
    public String getId() {
        return id;
    }
/**
 * 
 * @param id 
 * id of the rule 
 */
    public void setId(String id) {
        this.id = id;
    }
/**
 * 
 * @return the value of the rule
 */
    public String getValor() {
        return valor;
    }
/**
 * 
 * @param valor 
 * the value of the rule
 */
    public void setValor(String valor) {
        this.valor = valor;
    }
/**
 * 
 * @return the size of the rule
 */
    public int getTam() {
        return tam;
    }
/**
 * 
 * @param tam 
 * the size of the rule
 */
    public void setTam(int tam) {
        this.tam = tam;
    }
/**
 * 
 * @return the string "valor //id//value of the attributes of the semantic action " 
 */
    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder();
        int i=0;
        for(String v:valorAtributos){
            if(valorAtributos.size()-1==i)
                sb.append(v);
            else
                sb.append(v+"//");
            i++;
        }
      
        return  valor + " //"+id+"//"+sb.toString();
    }
/**
 * 
 * @return semantics actions 
 */
    public List<AccionSemantica> getAcciones() {
        return acciones;
    }
/**
 * 
 * @param acciones 
 * semantics actions 
 */
    public void setAcciones(List<AccionSemantica> acciones) {
        this.acciones = acciones;
    }
/**
 * 
 * @return if the semantic action is inserted
 */
    public boolean isAccionesInsertadas() {
        return accionesInsertadas;
    }
/**
 * 
 * @param accionesInsertadas 
 * if the semantic action is inserted
 */
    public void setAccionesInsertadas(boolean accionesInsertadas) {
        this.accionesInsertadas = accionesInsertadas;
    }
/**
 * 
 * @return value of the attributes of the semantics actions
 */
    public List<String> getValorAtributos() {
        return valorAtributos;
    }
/**
 * 
 * @param valorAtributos 
 * value of the attributes of the semantics actions
 */
    public void setValorAtributos(List<String> valorAtributos) {
        this.valorAtributos = valorAtributos;
    }

  
   
    
    
    
    
    
}
