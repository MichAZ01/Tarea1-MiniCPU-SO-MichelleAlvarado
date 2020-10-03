/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * this class contains the attributes and accesor methods that allow simulating the operations that are allowed by the cpu
 * @author Michelle Alvarado
 */
public class Operation {
    private String operationName;
    private int bitsLength;
    private String binaryCode;
    
    public Operation(String operation, int bits, String code){
        this.operationName = operation;
        this.bitsLength = bits;
        this.binaryCode = code;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
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
