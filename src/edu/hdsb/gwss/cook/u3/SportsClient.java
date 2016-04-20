/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hdsb.gwss.cook.u3;

import java.util.ArrayList;

/**
 *
 * @author 1cookspe
 */
public class SportsClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println( "-------------------" );
        System.out.println( "Test #1 - Empty Constructor" );
        Team penguins = new Team();
        System.out.println("PRE-CONDITION:");
        assert(penguins.getCity() == null);
        assert(penguins.getId() == 1);
        System.out.println("POST-CONDITION:");
        penguins.setCity("Pittsburgh");
        penguins.setName("Penguins");
        System.out.println("Printing... " + penguins.getCity());
        assert(penguins.getCity() == "Pittsburgh");
        assert(penguins.getName() == "Penguins");
        
        System.out.println( "-------------------" );
        System.out.println( "Test #2 - isValid()" );
        System.out.println("PRE-CONDITION:");
        assert(penguins.isValid() == false);
        penguins.setId(314725);
        System.out.println("POST-CONDITION:");
        assert(!penguins.isValid());
        penguins.setSport("hockey");
        assert(penguins.isValid());
        
        System.out.println( "-------------------" );
        System.out.println( "Test #3 - add(..) invalid Player" );
        Player sidneyCrosby = new Player("Sidney", "Crosby");
        System.out.println("PRE-CONDITION:");
        assert(penguins.getPlayers().size() == 0);
        System.out.println("POST-CONDITION:");
        penguins.add(sidneyCrosby);
        assert(penguins.getPlayers().size() == 1);
        penguins.remove(sidneyCrosby);
        assert(penguins.getPlayers().size() == 0);
        
        System.out.println( "-------------------" );
        System.out.println( "Test #3 - isValid(..) invalid Team" );
        Team blueJays = new Team("Toronto", "Blue Jays");
        System.out.println("PRE-CONDITION:");
        assert(!blueJays.isValid());
        blueJays.setId(235762);
        blueJays.setSport("baseball");
        System.out.println("POST-CONDITION:");
        assert(blueJays.isValid());
        
        System.out.println( "-------------------" );
        System.out.println( "Test #3 - remove(..) invalid Player" );
        System.out.println("PRE-CONDITION:");
        Player joshDonaldson = new Player(518626);
        blueJays.remove(joshDonaldson);
        assert(blueJays.getPlayers().size() == 0);
        joshDonaldson.setFirstName("Josh");
        joshDonaldson.setLastName("Donaldson");
        blueJays.add(joshDonaldson);
        blueJays.add(joshDonaldson);
        System.out.println("POST-CONDITION:");
        assert(blueJays.getPlayers().size() == 1);
        blueJays.remove(joshDonaldson);
        assert(blueJays.getPlayers().size() == 0);
        blueJays.add(sidneyCrosby);
        blueJays.add(joshDonaldson);
        assert(blueJays.getPlayers().size() == 2);
        assert(blueJays.get("Crosby") == sidneyCrosby);
        blueJays.remove(sidneyCrosby);
        assert(blueJays.get("Crosby") == null);
        
        System.out.println( "-------------------" );
        System.out.println( "Test #3 - remove(..) inequal Players" );
        assert(sidneyCrosby.equals(joshDonaldson) == false);
        Player joseBautistsa = new Player("Josh", "Donaldson");
        joseBautistsa.setId(518626);
        assert(joseBautistsa.equals(joshDonaldson));
        
    }
    
}