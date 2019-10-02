package com.example.dndsorcerapp.Responses;

import com.example.dndsorcerapp.DatabaseTools.SpellEntity;

import java.util.ArrayList;

/**
 * SpellResponse Model Object.
 *
 * <P>This is necessary to fill in the start of the json object sinc it starts with the bottom
 * three parameters before displ;aying the spells in object array notation.
 *
 * @author Damon Estrada
 * @version 1.0
 * @since   2019-09-07
 */
public class SpellResponse {
    private int count;
    private String next;
    private String previous;
    private ArrayList<SpellEntity> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<SpellEntity> getResults() {
        return results;
    }

    public void setResults(ArrayList<SpellEntity> results) {
        this.results = results;
    }
}
