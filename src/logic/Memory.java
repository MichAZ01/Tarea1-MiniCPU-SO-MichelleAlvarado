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
public class Memory {
    private static int memoryLength = 100;
    private MemorySpace[] memoryArray;
    
    public Memory(){
    }

    public static int getMemoryLength() {
        return memoryLength;
    }

    public static void setMemoryLength(int memoryLength) {
        Memory.memoryLength = memoryLength;
    }

    public MemorySpace[] getMemoryArray() {
        return memoryArray;
    }

    public void setMemoryArray(MemorySpace[] memoryArray) {
        this.memoryArray = memoryArray;
    }
}
