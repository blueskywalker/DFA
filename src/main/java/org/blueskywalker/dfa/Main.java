/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author jerry
 */
public class Main {

    public static final void main(String [] args) {
        
        String fileName = "en-common.wl";
                
        DFA dfa = new DFA();
        try {
            
            dfa.buildFrom(fileName);

            
            MinimizingDFA mini = new MinimizingDFA(dfa);
            
            mini.minimize();
            
            mini = null;
            
            dfa.travelDFA();
           // dfa.writeToFile("en-common.dfa");
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
