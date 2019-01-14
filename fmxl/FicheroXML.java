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
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.jdom2.Attribute;
 
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
 
public class FicheroXML {
    private ArrayList<Regla> listaGramatica= new ArrayList<>();   
    private List<String> cadena= new ArrayList<>();//lista con cómo va evolucionando la cadena de entrada 
    private List<Informacion> listaPasos = new ArrayList<>();//como va evolucionando en cada paso el árbol y la cadena
    private int numNodos;
    private Map<Integer,Simbolo> mapa = new HashMap<>();
    private String ruta;
    private String tipoTraductor;

    public FicheroXML() {
    }
  
  /**
   * load the xml example
   * @param ruta 
   * route of the xml
   */
    
    public void cargarXml(String ruta)
{
    this.ruta=ruta;
    //Se crea un SAXBuilder para poder parsear el archivo
    SAXBuilder builder = new SAXBuilder();
    //File xmlFile = new File( "src\\pruebaxml\\archivo.xml" );
    File xmlFile = new File( ruta );
    try
    {
        //Se crea el documento a traves del archivo
        Document document = (Document) builder.build( xmlFile );
 
        //Se obtiene la raiz 'tables'
        Element rootNode = document.getRootElement();
 
        //Se obtiene la lista de hijos de la raiz 'tables'
        List list = rootNode.getChildren();
 
        //Se recorre la lista de hijos de 'tables'
        for ( int i = 0; i < list.size(); i++ )
        {
            //Se obtiene el elemento 'tabla'
            Element tabla = (Element) list.get(i);
            //if(i==0){ //Elemento inicio del xml       
                //String gramatica = tabla.getChildTextTrim("gramatica");
                lecturaGramatica(tabla);
                cadena.add("pend"+tabla.getChildTextTrim("cadena"));//todo por leer
                System.out.println(cadena);
                lecturaArbol(tabla);
                Element contenido=tabla.getChild("contenido");//nodo contenido para obtener un listado de todos los pasos                
                List recorrido=contenido.getChildren();//listado de los pasos
                for(int p=0;p<recorrido.size();p++){
                    Element paso = (Element)recorrido.get( p );
                    String numero=paso.getAttributeValue("id");
                    System.out.println("Paso "+numero);
                    String tipo = paso.getChildTextTrim("tipo");
                    System.out.println("Contrucción árbol: "+ tipo);
                    StringBuilder aux= new StringBuilder();
                    Element pasCad=(Element)paso.getChild("cadena");
                    aux.append(pasCad.getChildTextTrim("leido"));
                    aux.append("pend"+pasCad.getChildTextTrim("pendiente"));
                    cadena.add(aux.toString());
                    String elemento = paso.getChildTextTrim("elemento");
                    String relNodos = paso.getChildTextTrim("relNodos");
                    StringBuilder elemHijos= new StringBuilder();
                    elemHijos.append(elemento);
                    if(relNodos!=null){
                        elemHijos.append(" ");
                        elemHijos.append(relNodos);
                    }
                    String valor = paso.getChildTextTrim("valor");
                    Informacion inf=new Informacion(Integer.parseInt(numero),tipo,pasCad.getChildTextTrim("leido"),pasCad.getChildTextTrim("pendiente"),elemHijos.toString(),valor);
                    listaPasos.add(inf);
                    String nuevaRegla = paso.getChildTextTrim("nuevaRegla");
                    Element elNuevaRegla = paso.getChild("nuevaRegla");
                    if (nuevaRegla!=null){               
                        String tamRegla=paso.getChildText("widthRegla");
                        Regla reglaAux= new Regla();
                        reglaAux.setValor(nuevaRegla);
                        String idRegla=elNuevaRegla.getAttributeValue("refRegla");
                        reglaAux.setId(idRegla);                       
                        reglaAux.setTam(Integer.parseInt(tamRegla));
                        List<Element> eValores=elNuevaRegla.getChildren("valores");
                        List<String> valorAtributos=new ArrayList<>();//lista de los valores de los atributos de la acción semántica                        
                        for(Element e:eValores)
                            valorAtributos.add(e.getText());
                        if(elNuevaRegla!=null){
                            reglaAux.setValorAtributos(valorAtributos);
                        }
                         inf.setRegla(reglaAux);
                    }
                      
                    Element accSemEj=paso.getChild("accionSemanticaEjecutada");
                   
                    inf.setSimbolosActualizados(ejecutarAccion(accSemEj));
                    
                }
            //}
            
        } 
    }catch ( IOException io ) {
        System.out.println( io.getMessage() );
    }catch ( JDOMException jdomex ) {
        System.out.println( jdomex.getMessage() );
    }
   }
  /**
   * read the part of the xml correspond to the grammar
   * @param tabla
   * Tha data of the xml
   */ 
   public void lecturaGramatica(Element tabla){
       Element gramatica = (Element) tabla.getChild("traductor");
       tipoTraductor=gramatica.getChildText("tipo");
       List reglas = gramatica.getChildren("regla");
       for(int j=0; j<reglas.size(); j++){
           Element regla = (Element) reglas.get(j);
           String id =regla.getAttributeValue("id");
           List<Element> acciones = regla.getChildren("accionSemantica");
          //creamos el objecto accion semántica para cada regla y la almacenamos en una lista
          List<AccionSemantica> listaAcciones = new ArrayList<>();          
           for(int k=0; k< acciones.size();k++){
               AccionSemantica objAcc= new AccionSemantica();               
               Element accSem=(Element)acciones.get(k);
               String posAcc=accSem.getAttributeValue("pos");
               System.out.println("pos de la accion "+ posAcc);
               objAcc.setPos(Integer.parseInt(posAcc));
               String valorAccion= accSem.getText().replaceAll("\n", "").replaceAll(" ","");//elimino saltos de linea y espacios
               System.out.println("Valor de la accion " + valorAccion);
               objAcc.setValor(valorAccion);
              //Comprobacion de si el nodo es intermedio
               Object interm=acciones.get(k).getChild("intermedio");
               if(interm==null)
                    objAcc.setFin(true);
                else
                    objAcc.setFin(false);
                     
                
               listaAcciones.add(objAcc);
           }
           
           
           List simbolos  = regla.getChildren("simbolo");
           Regla listaRegla = new Regla();//añadimos los simbolos al objeto Regla 
           listaRegla.setAcciones(listaAcciones);//añadimos las acciones semánticas a la regla
           listaRegla.setId(id);
           listaGramatica.add(listaRegla);
            for(int i=0; i<simbolos.size(); i++){                
                Element simbolo= (Element) simbolos.get(i);
                 String valor = simbolo.getChildTextTrim("valor");
                 boolean terminal=Boolean.parseBoolean(simbolo.getChildText("terminal"));
                 Simbolo s = new Simbolo(valor,terminal); 
                 listaRegla.añadir(s);
                 System.out.print(s +" ");
            }
            System.out.println("siguiente regla");  
            
       } 
   }
   /**
    * read the part of the xml correspond to the grammar
    * @param tabla 
    * Tha data of the xml
    */
   public void lecturaArbol(Element tabla){
       Element arbol = (Element) tabla.getChild("arbol");
       String nN=arbol.getChildTextTrim("num_nodos");
       nN=nN.replace(" ", "");       
       this.numNodos=Integer.parseInt(nN);
       String altura=arbol.getChildTextTrim("altura");
       List recorrido=arbol.getChildren("nodo");//listado de los pasos
       for (int i=0; i<recorrido.size();i++){
           Simbolo s = new Simbolo();
           Element nodo= (Element) recorrido.get(i);
           String numero=nodo.getAttributeValue("id");
           s.setId(Integer.parseInt(numero));
           String elemento = nodo.getChildText("elemento");
           s.setValor(elemento);
           String nivel = nodo.getChildText("nivel");
           s.setNivel(Integer.parseInt(nivel));
           String terminal = nodo.getChildText("terminal");
           s.setTerminal(Boolean.parseBoolean(terminal));
           mapa.put(Integer.parseInt(numero), s);
          
       }
       
       
   }
/**
 * 
 * @return list of rules of the grammar
 */
    public ArrayList<Regla> getListaGramatica() {
        return listaGramatica;
    }
/**
 * 
 * @param listaGramatica 
 * list of rules of the grammar
 */
    public void setListaGramatica(ArrayList<Regla> listaGramatica) {
        this.listaGramatica = listaGramatica;
    }
/**
 * 
 * @return string 
 */
    public List<String> getCadena() {
        return cadena;
    }
/**
 * 
 * @param cadena 
 * string
 */
    public void setCadena(List<String> cadena) {
        this.cadena = cadena;
    }
/**
 * 
 * @return list of steps of the generate of the tree 
 */
    public List<Informacion> getListaPasos() {
        return listaPasos;
    }
/**
 * 
 * @param listaPasos 
 * list of steps of the generate of the tree 
 */
    public void setListaPasos(List<Informacion> listaPasos) {
        this.listaPasos = listaPasos;
    }
/**
 * 
 * @return number of nodes 
 */
    public int getNumNodos() {
        return numNodos;
    }
/**
 * 
 * @param numNodos 
 * number of nodes
 */
    public void setNumNodos(int numNodos) {
        this.numNodos = numNodos;
    }
/**
 * 
 * @return map that relates the symbols with the id of the step in which it is processed
 */
    public Map<Integer, Simbolo> getMapa() {
        return mapa;
    }
/**
 * 
 * @param mapa
 * map that relates the symbols with the id of the step in which it is processed
 */
    public void setMapa(Map<Integer, Simbolo> mapa) {
        this.mapa = mapa;
    }
/**
 * 
 * @return route of the xml of example 
 */
    public String getRuta() {
        return ruta;
    }
/**
 * 
 * @param ruta 
 * route of the xml of example 
 */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
/**
 * 
 * @return the type of the translate, ascendant or descendant 
 */
    public String getTipoTraductor() {
        return tipoTraductor;
    }
/**
 * 
 * @param tipoTraductor 
 * the type of the translate, ascendant or descendant
 */
    public void setTipoTraductor(String tipoTraductor) {
        this.tipoTraductor = tipoTraductor;
    }
/**
 * execute an action and update the values of the symbols
 * @param accSemEj
 * semantic action exucuted
 * @return list of symbols updated
 */
    private List<Simbolo> ejecutarAccion(Element accSemEj) {
        List<Simbolo> simbolosActualizados=new ArrayList<>();
        if(accSemEj!=null){
           List<Element> lista=accSemEj.getChildren("nodo");
            for(Element e:lista){
                Simbolo s = new Simbolo();
                s.setId(Integer.parseInt(e.getChildText("refNodo")));
                s.setValor(e.getChildText("atributos"));
                simbolosActualizados.add(s);
            }
            
        }
       
        
        
        return simbolosActualizados;
    }
    
    
 
}