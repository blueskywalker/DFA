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
public class Build {

    public static void main(String [] args) {
        
        if(args.length < 1) {
            System.out.println("org.blueskywalker.dfa.Main file");
            System.exit(0);
        }
        
        String fileName = args[0];
                
        DFA dfa = new DFA();
        try {
            
            dfa.buildFrom(fileName);
            
            MinimizingDFA mini = new MinimizingDFA(dfa);
            
            mini.minimize();            
            mini = null;           
            //dfa.travelDFA();
            dfa.writeToFile(String.format("%s.dfa", args[0]));
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
