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
        File file = new File(fileName);
        
        if(file.exists()) {
            System.out.println("There is dictionary.");
        } else {
            System.exit(-1);
        }
        
        DFA dfa = new DFA();
        try {
            
            dfa.buildFrom(fileName);
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
}
