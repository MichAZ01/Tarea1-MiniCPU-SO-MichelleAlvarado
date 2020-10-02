/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;

/**
 *
 * @author Michelle Alvarado
 */
public class ProgramConversor {
    public ProgramConversor(){
        
    }
    
    public String createBinaryInstruction(String line) throws IOException{
        String binaryInstruction = "";
        String operation = line.split(" ")[0];
        
            switch(operation){
                case "MOV":
                    String lineWithoutOperation = line.substring(4);
                    String[] lineParts = lineWithoutOperation.split(", ");
                    binaryInstruction = ConstructBinaryInstruction(operation, lineParts[0], Integer.parseInt(lineParts[1]));
                    break;
                default:
                    String register = line.substring((operation.length() + 1));
                    binaryInstruction = ConstructBinaryInstruction(operation, register, 0);
                    break;
            }
        
        return binaryInstruction;
    }
    
    public String ConstructBinaryInstruction(String operation, String register, int number) throws IOException{
        String binaryOperation = CPU.getCPU().getAnOperationBinary(operation);
        String binaryRegister = CPU.getCPU().getAnGeneralRegisterBinary(register);
        String binaryNumber = new BinaryConversor().decimalToBinary(number);
        String binaryInstruction = binaryOperation + " " + binaryRegister + " " + binaryNumber;
        
        return binaryInstruction;
    }
    
    public String decodeThreePartsInstruction(String[] binaryCodeParts) throws IOException{
        String assemblyInstruction = "";
        String operation = CPU.getCPU().getOperationByBinaryCode(binaryCodeParts[0]).getOperationName();
        String register = CPU.getCPU().getGeneralRegisterByBinaryCode(binaryCodeParts[1]).getRegisterName();
        String number = Integer.toString(new BinaryConversor().binaryToDecimal(binaryCodeParts[2]));
        assemblyInstruction = operation + " " + register + ", " + number;
        return assemblyInstruction;
    }
    
    public String decodeTwoPartsInstruction(String[] binaryCodeParts) throws IOException{
        String assemblyInstruction = "";
        String operation = CPU.getCPU().getOperationByBinaryCode(binaryCodeParts[0]).getOperationName();
        String register = CPU.getCPU().getGeneralRegisterByBinaryCode(binaryCodeParts[1]).getRegisterName();
        assemblyInstruction = operation + " " + register;
        return assemblyInstruction;
    }
    
    public String binaryToASMInstruction(String binaryCode) throws IOException{
        String asmInstruction = "";
        String[] binaryCodeParts = binaryCode.split(" ");
        String operation = CPU.getCPU().getOperationByBinaryCode(binaryCodeParts[0]).getOperationName();
        
        switch(operation){
            case "MOV":
                asmInstruction = this.decodeThreePartsInstruction(binaryCodeParts);
                break;
            default:
                asmInstruction = this.decodeTwoPartsInstruction(binaryCodeParts);
                break;
        }
        return asmInstruction;
    }
}
