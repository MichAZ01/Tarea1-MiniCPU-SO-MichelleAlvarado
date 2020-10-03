/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import controller.Controller;

/**
 * initialize the GUI through a controller
 * @author Michelle Alvarado
 */
public class AplMiniPC {
    
    public static void main(String[] argv){
        new Controller().showView();
    }
}
