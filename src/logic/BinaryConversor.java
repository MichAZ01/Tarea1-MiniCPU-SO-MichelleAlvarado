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
public class BinaryConversor {
    
    public BinaryConversor(){
 
    }
    
    /**
     * Takes a decimal number and convert them in a binary 8 bits number (using a Java method). 
     * If its negative: the first bit will be '1', if its not: the first bit will be '0';
     * @param decimal
     * @return 
     */
    public String decimalToBinary(int decimal){
        String binaryCode = "";
        
        if(decimal < 0) {
            binaryCode += "1";
            decimal = decimal * -1;
        }
        else binaryCode += "0";
        
        String binary = Integer.toBinaryString(decimal);
        if (binary.length() < 7) binaryCode = this.fillWithZeros(binaryCode, binary, 7);
        else binaryCode += binary;
        
        return binaryCode;
    }
    /**
     * Takes a binary 8 bits number and convert them in a decimal number (using a Java method).
     * If the first bit is '1' the number will be negative, positive otherwise.
     * @param binaryCode
     * @return 
     */
    public int binaryToDecimal(String binaryCode){
        int decimalNumber = 0;
        String sign = binaryCode.substring(0,1);
        binaryCode = binaryCode.substring(1);
        
        decimalNumber = Integer.parseInt(binaryCode, 2);
        
        if(sign.equals("1")) decimalNumber = decimalNumber * -1;
        
        return decimalNumber;
    }
    
    /**
     * 
     * @param binaryCode: works like a buffer
     * @param binaryNumber: the binary number that we want to add leading zeros
     * @param BinaryLength: how much bits we want
     * @return 
     */
    public String fillWithZeros(String binaryCode, String binaryNumber, int BinaryLength){
        int requiredZeros = BinaryLength - binaryNumber.length();
        
        for(int i = 0; i < requiredZeros; i++){
            binaryCode += "0";
        }
        binaryCode += binaryNumber;
        
        return binaryCode;
    }
}
