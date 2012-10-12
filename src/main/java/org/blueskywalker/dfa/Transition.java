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
public class Transition implements Serializable, Comparable<Character> {
    private static final long serialVersionUID = 4114888190954584419L;
    private char Char;
    private int next;
    
    public Transition() {
        Char = 0;
        next = -1;
    }

    public Transition(char ch) {
        Char = ch;
        next = -1;
    }
    
    public int compareTo(Character t) {
        return t.charValue() - Char;
    }

    public char getChar() {
        return Char;
    }

    public void setChar(char Char) {
        this.Char = Char;
    }

    public int getNext() {
        return next;
    }

    public Transition setNext(int next) {
        this.next = next;
        return this;
    }
    
}
