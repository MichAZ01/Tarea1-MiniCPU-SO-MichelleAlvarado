/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * this class contains the attributes and the accesor methods that allow to simulate the general registers of the cpu like AX, BX, CX, DX
 * @author Michelle Alvarado
 */
public class GeneralRegister {
    private String registerName;
    private int bitsLength;
    private String binaryCode;
    
    public GeneralRegister(String register, int bits, String binaryC){
        this.registerName = register;
        this.bitsLength = bits;
        this.binaryCode = binaryC;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public int getBitsLength() {
        return bitsLength;
    }

    public void setBitsLength(int bitsLength) {
        this.bitsLength = bitsLength;
    }

    public String getBinaryCode() {
        return binaryCode;
    }

    public void setBinaryCode(String binaryCode) {
        this.binaryCode = binaryCode;
    }
}
