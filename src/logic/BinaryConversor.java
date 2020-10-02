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
    
    public int binaryToDecimal(String binaryCode){
        int decimalNumber = 0;
        String sign = binaryCode.substring(0,1);
        binaryCode = binaryCode.substring(1);
        
        decimalNumber = Integer.parseInt(binaryCode, 2);
        
        if(sign.equals("1")) decimalNumber = decimalNumber * -1;
        
        return decimalNumber;
    }
    
    public String fillWithZeros(String binaryCode, String binaryNumber, int BinaryLength){
        int requiredZeros = BinaryLength - binaryNumber.length();
        
        for(int i = 0; i < requiredZeros; i++){
            binaryCode += "0";
        }
        binaryCode += binaryNumber;
        
        return binaryCode;
    }
}
