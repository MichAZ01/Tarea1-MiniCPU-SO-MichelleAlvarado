/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * this class contains the attributes and the accesor methods that allow to simulate cpu memory that contains one hundred memory spaces to store
 * programs and general registers values.
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
