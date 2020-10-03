/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * this class contains the attributes and accessor methods that allow to simulate a cpu register like PC, AC and IR
 * @author Michelle Alvarado
 */
public class CPURegister {
    private String registerName;
    private String initValue;
    private String currentValue;
    
    public CPURegister(String register, String value){
        this.registerName = register;
        this.initValue = value;
        this.currentValue = value;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
    
}
