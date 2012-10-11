/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author jerry
 */
public class State implements Serializable {
    private static final long serialVersionUID = -3647879125043388003L;
    
    public static enum STATUS {BEGIN,RUN,END};
    
    private STATUS  status;
    private TreeSet<Transition> arcs;
    
    public State() {
        arcs = new TreeSet<Transition>();
        status = STATUS.RUN;
    }
    
    public State(STATUS status) {
        this();
        this.status = status;
    }
    
}
