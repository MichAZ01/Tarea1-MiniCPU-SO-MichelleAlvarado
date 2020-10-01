/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author Michelle Alvarado
 */
public class CPU {
    public static CPU myCPU = null;
    private Memory memory;
    
    
    private CPU(){
        this.memory = new Memory();
    }
    
    public static CPU getCPU(){
        if(myCPU == null){
            myCPU = new CPU();
        }
        return myCPU;
    }    
    
}
