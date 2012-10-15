/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.Serializable;
import java.util.TreeMap;

/**
 *
 * @author jerry
 */
public class State implements Serializable {
    private static final long serialVersionUID = -3647879125043388003L;

    private int total;
    public static enum STATUS {BEGIN,END,RUN};
    
    private STATUS  status;
    private TreeMap<Character, Edge> arcs;
    
    public State() {
        arcs = new TreeMap<Character,Edge>();
        status = STATUS.RUN;
    }
    
    public State(STATUS status) {
        this();
        this.status = status;
    }

    public TreeMap<Character,Edge> getArcs() {
        return arcs;
    }

    public void setArcs(TreeMap<Character,Edge> arcs) {
        this.arcs = arcs;
    }
    
    public boolean hasChar(char ch) {
        return arcs.containsKey(new Character(ch));
    }
    
    public Edge next(char ch) {
        return arcs.get(new Character(ch));
    }
 
    void addTransition(Edge tr) {
        arcs.put(new Character(tr.getChar()), tr);
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
 
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("S[%s]T[%d]:",status.name(),arcs.size()));
        for(Edge tr : arcs.values()) {
            sb.append(tr.toString());
        }
        
        return sb.toString();
    }
    
    
}
