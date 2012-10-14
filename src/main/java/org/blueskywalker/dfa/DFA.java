/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jerry
 */
public class DFA implements Serializable {

    static final Logger logger = LogManager.getLogger(DFA.class);
    
    private static final long serialVersionUID = -3610261803234263676L;
    private ArrayList<State> states;

    
    public DFA() {
        states = new ArrayList<State>();
        states.add(new State(State.STATUS.BEGIN));
    }

    public void buildFrom(String fileName) throws FileNotFoundException, IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            add(line);
        }

        br.close();
    }

    public void writeToFile(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(new DeflaterOutputStream(fos));
        oos.writeObject(states);
        oos.close();
    }

    public void readFromFile(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        
        InflaterInputStream iis = new InflaterInputStream(
                new FileInputStream(fileName));    
        ObjectInputStream ois = new ObjectInputStream(iis);
        states = (ArrayList<State> ) ois.readObject();
        ois.close();
    }
    
    public void add(String line) {
        State current = states.get(0);

        for (int i = 0; i < line.length(); i++) {
            current = add(current, line.charAt(i));
        }

        current.setStatus(State.STATUS.END);
    }

    private State add(State now, char ch) {
        now.setTotal(now.getTotal()+1);
        
        if (!now.hasChar(ch)) {
            states.add(new State());
            now.addTransition(
                    new Transition(ch).setNext(states.size() - 1));
        }

        return nextOf(now.getArcs().get(ch));
    }

    public void travelDFA() {
         travelDFA(System.out);
    }
    
    public void travelDFA(PrintStream out) {
        StringBuilder sb = new StringBuilder();

        travelDFA(states.get(0), sb, 0,out);
    }

    private void travelDFA(State state, StringBuilder sb, int depth,PrintStream out) {
        

        if (state.getStatus() == State.STATUS.END) {
            sb.setLength(depth);
            out.println(sb.toString());
        }

        while (sb.length() <= depth) {
            sb.append(" ");
        }

        for (Transition tr : state.getArcs().values()) {
            sb.setCharAt(depth, tr.getChar());
            travelDFA(nextOf(tr), sb, depth + 1,out);
        }
    }
    
    public String find(String match) {
        return find(states.get(0),match,0);
    }
    
    private String find(State now,String match,int depth) {
    
  //      logger.debug(now.toString());
        
        if(match.length()== depth) {
            return match;
        }
               
        char ch = match.charAt(depth);
        if(now.hasChar(ch)) {
            return find(nextOf(now.next(ch)),match,depth+1);
        }
        return match.substring(0, depth);
    }
    
    public State nextOf(Transition tr) {
        return states.get(tr.getNext());
    }

    public State stateAt(int index) {
        return states.get(index);
    }
    
    public int sizeAt(int index) {
        return stateAt(index).getArcs().size();
    }
    
    public ArrayList<State> getStates() {
        return states;
    }

    public void setStates(ArrayList<State> states) {
        this.states = states;
    }


    
}
