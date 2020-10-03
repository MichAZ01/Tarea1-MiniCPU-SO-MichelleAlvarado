/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * this class contains the attributes and accesor methods that allow to simulate the memory spaces of a cpu memory
 * @author Michelle Alvarado
 */
public class MemorySpace {
    private int memorySpaceIndex;
    private String currentValue;
            
    public MemorySpace(int index){
        this.memorySpaceIndex = index;
        this.currentValue = "00000000";
    }

    public int getMemorySpaceIndex() {
        return memorySpaceIndex;
    }

    public void setMemorySpaceIndex(int memorySpaceIndex) {
        this.memorySpaceIndex = memorySpaceIndex;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String actualValue) {
        if(actualValue == "0") actualValue = new BinaryConversor().fillWithZeros("", actualValue, 7);
        this.currentValue = actualValue;
    }
    
    
}
