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
public class Register {
    private String registerName;
    private int bitsLength;
    private String binaryCode;
    
    public Register(String register, int bits, String binaryC){
        this.registerName = register;
        this.bitsLength = bits;
        this.binaryCode = binaryC;
    }
}
