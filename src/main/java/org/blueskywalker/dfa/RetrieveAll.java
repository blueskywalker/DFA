/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blueskywalker
 */
public class RetrieveAll {

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("org.blueskywalker.dfa.RetrieveAll dfaFile");
            System.exit(0);
        }
        
        DFA dfa = new DFA();
        try {
            dfa.readFromFile(args[0]);
            dfa.travelDFA();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
