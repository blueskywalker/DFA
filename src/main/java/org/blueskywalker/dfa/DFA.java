/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.TreeSet;

/**
 *
 * @author jerry
 */
public class DFA  implements Serializable {
    
    private static final long serialVersionUID = -3610261803234263676L;
    private TreeSet<State> states;
    
    public DFA() {
        states = new TreeSet<State> ();
        states.add(new State(State.STATUS.BEGIN));
    }

    void buildFrom(String fileName) throws FileNotFoundException, IOException {
        
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        
        String line;
        while( (line=br.readLine()) != null) {
            line = line.trim();
            add(line);
        }
        
        br.close();
    }

    public void add(String line) {
        add(line,states.,0);
    }

    private void add(String line,State now, int i) {
        if(line.length()<= i)
            return;
        
        if()
        add(line,i+1);
        
    }
}
