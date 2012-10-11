/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.Serializable;

/**
 *
 * @author jerry
 */
public class Transition implements Serializable {
    private static final long serialVersionUID = 4114888190954584419L;
    private char Char;
    private State next;
    
    public Transition() {
        Char = 0;
        next = null;
    }
}
