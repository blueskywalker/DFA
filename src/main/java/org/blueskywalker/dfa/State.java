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


    
    public static enum STATUS {BEGIN,END,RUN};
    
    private STATUS  status;
    private TreeMap<Character, Transition> arcs;
    
    public State() {
        arcs = new TreeMap<Character,Transition>();
        status = STATUS.RUN;
    }
    
    public State(STATUS status) {
        this();
        this.status = status;
    }

    public TreeMap<Character,Transition> getArcs() {
        return arcs;
    }

    public void setArcs(TreeMap<Character,Transition> arcs) {
        this.arcs = arcs;
    }
    
    public boolean hasChar(char ch) {
        return arcs.containsKey(new Character(ch));
    }
    
    void addTransition(Transition tr) {
        arcs.put(new Character(tr.getChar()), tr);
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
    
}
