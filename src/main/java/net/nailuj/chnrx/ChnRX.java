/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nailuj.chnrx;

import javax.swing.UIManager;

/**
 *
 * @author Julian
 */
public class ChnRX {

    private static Steuerung steu;
    public static final String version = "v0.8.0";

    public ChnRX() {
        // Setzt das LaF auf das System LaF..
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        steu = new Steuerung();
    }

    public static void restart() {
        System.out.println("Spiel wird neugestartet..");
        steu = null;
        System.gc();
        steu = new Steuerung();
    }

    public static void main(String[] args) {
        new ChnRX();
    }

}
