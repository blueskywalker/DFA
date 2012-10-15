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
public class Edge implements Serializable, Comparable<Character> {
    private static final long serialVersionUID = 4114888190954584419L;
    private char Char;
    private int next;

    public Edge() {
        Char = 0;
        next = -1;
    }

    public Edge(char ch) {
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

    public Edge setNext(int next) {
        this.next = next;
        return this;
    }
    
    public String toString() {
        return String.format("[%c:%d]",Char,next);
    }


}
