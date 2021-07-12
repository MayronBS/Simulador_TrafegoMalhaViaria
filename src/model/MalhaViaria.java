/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mayron
 */
public class MalhaViaria {

    private Estrada[][] estradas;

    private static MalhaViaria instance;

    public MalhaViaria() {
        this.estradas = new Estrada[0][0];

    }

    public static synchronized MalhaViaria getInstance() {
        if (instance == null) {
            instance = new MalhaViaria();
        }
        return instance;
    }
    
    public Estrada[][] getEstradas() {
        return estradas;
    }

    public void setEstradas(Estrada[][] estradas) {
        this.estradas = estradas;
    }

}
