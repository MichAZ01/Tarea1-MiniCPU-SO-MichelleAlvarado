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
public class Number {
    private String numberValue;
    private int bitsLength;
    private String binaryCode;
    private Boolean isNegative;
    
    public Number(String number, int bits, String binaryC, Boolean negative){
        this.numberValue = number;
        this.bitsLength = bits;
        this.binaryCode = binaryC;
        this.isNegative = negative;
    }
}
