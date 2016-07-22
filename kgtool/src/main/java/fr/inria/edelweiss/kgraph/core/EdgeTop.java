
package fr.inria.edelweiss.kgraph.core;

import fr.inria.acacia.corese.api.IDatatype;
import fr.inria.edelweiss.kgram.api.core.Entity;
import fr.inria.edelweiss.kgram.api.core.Node;
import fr.inria.edelweiss.kgram.api.core.Pointerable;
import java.util.ArrayList;

/**
 *
 * @author Olivier Corby, Wimmics Inria I3S, 2014
 *
 */
public abstract class EdgeTop extends GraphObject implements Entity {
    
        public Entity copy() {
            return this;
        }
        
         public void setTag(Node node) {           
        }
         
         public void setGraph(Node node){
             
         }
         
         public Object getProvenance(){
             return null;
         }
         
         public void setProvenance(Object o){
             
         }               
        
     @Override
    public Iterable<IDatatype> getLoop() {
        return getNodeList();
    }
     
     public ArrayList<IDatatype> getNodeList() {
        ArrayList<IDatatype> list = new ArrayList();
        for (int i = 0; i < 4; i++) {
            list.add(getValue(null, i));
        }
        return list;
    }
        
        @Override
      public IDatatype getValue(String var, int n){     
        switch (n){
            case 0: return nodeValue(getNode(0));
            case 1: return nodeValue(getEdge().getEdgeNode());                 
            case 2: return nodeValue(getNode(1));
            case 3: return nodeValue(getGraph());
        }
        return null;
    }
      
      IDatatype nodeValue(Node n){
          return (IDatatype) n.getValue();
      }
      
        
        @Override
        public int pointerType(){
            return Pointerable.ENTITY_POINTER;
        }
        
        @Override
        public Entity getEntity(){
            return this;
        }
        
            @Override
        public Node getGraphStore() {
            return null;
        }



}