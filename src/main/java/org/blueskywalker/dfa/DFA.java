/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author jerry
 */
public class DFA implements Serializable {

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

        ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(fileName));

        oos.writeObject(states);

        oos.close();
    }

    public void add(String line) {
        State current = states.get(0);

        for (int i = 0; i < line.length(); i++) {
            current = add(current, line.charAt(i));
        }

        current.setStatus(State.STATUS.END);
    }

    private State add(State now, char ch) {

        if (!now.hasChar(ch)) {
            states.add(new State());
            now.addTransition(
                    new Transition(ch).setNext(
                    states.get(states.size() - 1)));
        }

        return now.NextOf(ch);
    }

    public void travelDFA() {
        StringBuilder sb = new StringBuilder();

        travelDFA(states.get(0), sb, 0);
    }

    private void travelDFA(State state, StringBuilder sb, int depth) {
        TreeMap<Character, Transition> transition = state.getArcs();

        if (state.getStatus() == State.STATUS.END) {
            sb.setLength(depth);
            System.out.println(sb.toString());
        }

        while (sb.length() <= depth) {
            sb.append(" ");
        }

        for (Character ch : transition.keySet()) {
            sb.setCharAt(depth, ch.charValue());
            travelDFA(transition.get(ch).getNext(), sb, depth + 1);
        }
    }
}
