/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hdsb.gwss.cook.u4;

/**
 *
 * @author 1cookspe
 */
public class LinkedListClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        assert(list.size() == 0);
        list.addAtEnd("Hello");
        list.addAtEnd("Hi");
        list.addAtEnd("5");
        System.out.println(list.size());
    }
    
}