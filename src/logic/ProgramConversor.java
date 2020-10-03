/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;

/**
 * this class contains methods that allow converting binary instructions to assembly instructions and the opposite
 * @author Michelle Alvarado
 */
public class ProgramConversor {
    public ProgramConversor(){
        
    }
    
    /**
     * 
     * @param line: the binary instruction that will be converted to an assembly instruction
     * @return
     * @throws IOException 
     */
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
    
    /**
     * take the three parts of a binary instruction and unites them
     * @param operation
     * @param register
     * @param number
     * @return
     * @throws IOException 
     */
    public String ConstructBinaryInstruction(String operation, String register, int number) throws IOException{
        String binaryOperation = CPU.getCPU().getAnOperationBinary(operation);
        String binaryRegister = CPU.getCPU().getAnGeneralRegisterBinary(register);
        String binaryNumber = new BinaryConversor().decimalToBinary(number);
        String binaryInstruction = binaryOperation + " " + binaryRegister + " " + binaryNumber;
        
        return binaryInstruction;
    }
    
    /**
     * takes the binary parts and convert each one in an assembly equivalent part
     * @param binaryCodeParts
     * @return
     * @throws IOException 
     */
    public String decodeThreePartsInstruction(String[] binaryCodeParts) throws IOException{
        String assemblyInstruction = "";
        String operation = CPU.getCPU().getOperationByBinaryCode(binaryCodeParts[0]).getOperationName();
        String register = CPU.getCPU().getGeneralRegisterByBinaryCode(binaryCodeParts[1]).getRegisterName();
        String number = Integer.toString(new BinaryConversor().binaryToDecimal(binaryCodeParts[2]));
        assemblyInstruction = operation + " " + register + ", " + number;
        return assemblyInstruction;
    }
    
    /**
     * takes the binary parts and convert each one in an assembly equivalent part
     * @param binaryCodeParts
     * @return
     * @throws IOException 
     */
    public String decodeTwoPartsInstruction(String[] binaryCodeParts) throws IOException{
        String assemblyInstruction = "";
        String operation = CPU.getCPU().getOperationByBinaryCode(binaryCodeParts[0]).getOperationName();
        String register = CPU.getCPU().getGeneralRegisterByBinaryCode(binaryCodeParts[1]).getRegisterName();
        assemblyInstruction = operation + " " + register;
        return assemblyInstruction;
    }
    
    /**
     * depends of the operation that binary instruction has it will be a three part or a two part assembly instruction. Example:
     * MOV, AX 2 has three parts (operation, destination register and a integer number that will be moved to the indicated register
     * LOAD BX has two parts (the operation ans the register that contains the value that will be manipulated)
     * @param binaryCode
     * @return
     * @throws IOException 
     */
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
