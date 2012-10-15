/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.blueskywalker.dfa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author jerry
 */
public class MinimizingDFA {

    static final Logger logger = LogManager.getLogger(MinimizingDFA.class);
    
    private DFA dfa;
    private int [] dfaNext;
    private int [] dfaGroup;
    private ArrayList<GroupItem> group;

    class GroupItem {
        public int head;
        public int size;
        
        public GroupItem(int head,int size) {
            this.head = head;
            this.size = size;
        }
        
        public GroupItem() {
            this(0,0);
        }
    }
    
    public MinimizingDFA(DFA dfa) {
        this.dfa = dfa;
        int size = dfa.getStates().size();
        dfaNext = new int [size];
        dfaGroup = new int [size];
        
        for(int i=0;i<size;i++) {
            dfaGroup[i] = dfa.getStates().get(i).getStatus().ordinal();
            dfaNext[i] = 0;
        }
        
        group = new ArrayList<GroupItem>();
    }
    
    private State nextOf(Edge tr) {
        return dfa.nextOf(tr);
    }

    private void addToGroup(int groupId, int state) {
        
        while(group.size() <= groupId) {
            group.add(new GroupItem());
        }
        
        
        dfaNext[state] = group.get(groupId).head;
        dfaGroup[state] = groupId;
        group.get(groupId).head = state;
        group.get(groupId).size++;
        
    }
    
    private int removeFromGroup(int groupId, int prev) {
        int stateToDelete = dfaNext[prev];
        
        dfaNext[prev] = dfaNext[stateToDelete];
        
        group.get(groupId).size--;
        
        return stateToDelete;
    }
    
    private void init(int index) {
        
        State now = dfa.getStates().get(index);
        
        for(Edge tr : now.getArcs().values()) {
            if(nextOf(tr) == now) {
                continue;
            }
            
            init(tr.getNext());
        }
        dfaNext[index]=0;
        addToGroup(dfaGroup[index],index);
    }
    
    private boolean compareBetween(State a, State b) {
        
        if(a==b) {
            return true;
        }
        
        if(a.getStatus()!= b.getStatus()) {
            return false;
        }
        
        
        Edge [] avalues = a.getArcs().values().toArray(new Edge[0]);
        Edge [] bvalues = b.getArcs().values().toArray(new Edge[0]);
        
        if (avalues.length != bvalues.length) {
            return false;
        }
        
        for(int i=0;i<avalues.length;i++) {
            if(avalues[i].getChar() != bvalues[i].getChar()) {
                return false;
            }
            if(dfaGroup[avalues[i].getNext()]!= dfaGroup[bvalues[i].getNext()]) {
                return false;
            }            
        }
        return true;
    }
    
    private void tidyup() {
        
        ArrayList<State> mDfa = new ArrayList<State>();
        
       
        for(int i=0;i<group.size();i++) {
            
            int head = group.get(i).head;
            
            State newState = new State(dfa.stateAt(head).getStatus());
  
            for(Edge tr : dfa.stateAt(head).getArcs().values()) {               
                int next = dfaGroup[tr.getNext()];
                tr.setNext(next);
                newState.addTransition(tr);
            }
            mDfa.add(newState);
        }
        
        dfa.setStates(mDfa);
    }
    
    private String verifyState(State now) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(String.format("[%s][%d]",now.getStatus().name(),now.getArcs().size()));
        
        for(Edge tr : now.getArcs().values()) {
            sb.append(String.format("[%c:%d:%d]",tr.getChar(),tr.getNext(),dfaGroup[tr.getNext()]));
        }
        return sb.toString();
    }
    
    public void verify() {
        
        for(int i=0;i<group.size();i++) {
            int now = group.get(i).head;
            for(int j=0;j<group.get(i).size;j++) {
                System.out.print(i + ":");
                //System.out.println(dfa.stateAt(now).toString());
                System.out.println(verifyState(dfa.stateAt(now)));
                now = dfaNext[now];
            }
        }    
    }
    
    public void minimize() {
    
        logger.info("Initializing .....");
        init(0);
        
        int lastSize;
        int nGroup = group.size();
        
        logger.info("Minimizing .....");
        int count=0;
        do {
            lastSize = nGroup;
            
            for(int i=0;i<nGroup;i++) {
                if(group.get(i).size == 1) {
                    continue;
                }
                
                int head = group.get(i).head;
                int last = head;
                
                do {
                    int now = dfaNext[last];
                    
                    if(compareBetween(dfa.stateAt(head), dfa.stateAt(now))) {
                        last = now;
                    } else {
                        addToGroup(nGroup, removeFromGroup(i, last));
                    }
                } while (dfaNext[last]!=0);
                
                if(nGroup < group.size()) {
                    nGroup++;
                }
                System.out.print('.');
            }
            
            System.out.println();
            logger.info(String.format("progressing  count %d",++count));
            
        } while(lastSize!=nGroup);
        
        logger.info("Tidy up");
        tidyup();
    }
}
