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
public class Operation {
    private String operationName;
    private int bitsLength;
    private String binaryCode;
    
    public Operation(String operation, int bits, String binaryC){
        this.operationName = operation;
        this.bitsLength = bits;
        this.binaryCode = binaryC;
    }
}
