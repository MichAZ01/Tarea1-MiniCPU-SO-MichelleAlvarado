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
public class MemorySpace {
    private int memorySpaceIndex;
    private String actualValue;
            
    public MemorySpace(int index){
        this.memorySpaceIndex = index;
        this.actualValue = "0";
    }

    public int getMemorySpaceIndex() {
        return memorySpaceIndex;
    }

    public void setMemorySpaceIndex(int memorySpaceIndex) {
        this.memorySpaceIndex = memorySpaceIndex;
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue;
    }
    
    
}
