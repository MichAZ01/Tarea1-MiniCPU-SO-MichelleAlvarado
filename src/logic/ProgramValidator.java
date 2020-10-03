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
public class ProgramValidator {
    
    public ProgramValidator(){
        
    }
    
    /**
     * validate (depend of the operation) if a instruction is correct or not
     * @param line
     * @return
     * @throws IOException 
     */
    public Boolean validateSingleLine(String line) throws IOException{
        Boolean correctFormat = true;
        String[] allowedOperations = CPU.getCPU().getCPUOperationNames();
        String[] allowedRegisters = CPU.getCPU().getGeneralRegisterNames();
        String operation = line.split(" ")[0];
        if(this.StringExistsInArray(operation, allowedOperations)){
            switch(operation){
                case "MOV":
                    if(line.length() >= 9){
                        String lineWithoutOperation = line.substring(4);
                        correctFormat = this.validateMOVOperation(lineWithoutOperation, allowedRegisters);
                    } else correctFormat = false;
                    break;
                default:
                    if(line.length() >= (operation.length() + 1)){
                        String lineWithoutOperation = line.substring((operation.length() + 1));
                        if(this.StringExistsInArray(lineWithoutOperation, allowedRegisters)) correctFormat = true;
                        else correctFormat = false;
                    }
                    else correctFormat = false;
                    
                    break;
            }
        }
        else correctFormat = false;
        
        return correctFormat;
    }
    
    /**
     * the loop for validate each program instruction
     * @param data
     * @return
     * @throws IOException 
     */
    public Boolean validateLineFormat(String data) throws IOException{
        Boolean correctFormat = true;
        String lines[] = data.split("\\r?\\n");
        
        for (int i = 0; i < lines.length; i++){
            if(!validateSingleLine(lines[i])) {
                correctFormat = false;
                break;
            } 
        }  
        return correctFormat;
    }
     
    /**
     * validates that the .asm selected file is not empty and has the correct format
     * @param data
     * @return
     * @throws IOException 
     */
    public Boolean validateSelectedFile(String data) throws IOException{
        Boolean correctFile = true;
        Boolean fileIsEmpty = data.isEmpty();
        Boolean correctFormat = this.validateLineFormat(data);
        
        if(fileIsEmpty || !correctFormat) correctFile = false;
        
        return correctFile;
    }
    
    /**
     * validate if a string exists in an array. Used to verify for example if the operation part exists in the array of the operations
     * allows by the cpu
     * @param string
     * @param array
     * @return 
     */
    public boolean StringExistsInArray(String string, String[] array){
        Boolean exists = false;
        int size = array.length;
        for(int i = 0; i < size; i++){
            if(string.equals(array[i])){
                exists = true;
                break;
            }
        }
        return exists;
    }
    
    /**
     * validate if a MOV operation has three correct parts: operation (MOV), destination register (AX for example) and a integer number
     * that will be moved into a indicated register
     * @param line
     * @param allowedRegisters
     * @return 
     */
    public boolean validateMOVOperation(String line, String[] allowedRegisters){
        Boolean correctFormat = true;
        String[] lineParts = line.split(", ");
        int size = lineParts.length;
        if(size == 2){
            if(!this.StringExistsInArray(lineParts[0], allowedRegisters) || !isNumeric(lineParts[1])){ 
                correctFormat = false;
            }
            else{
                if(isNumeric(lineParts[1]) && Integer.parseInt(lineParts[1]) > 127){
                    correctFormat = false;
                }
            }
        }
        return correctFormat;
    }
    
    /**
     * validate if an string might be cast to an integer number
     * @param str
     * @return 
     */
    private static boolean isNumeric(String str){
	try {
		Integer.parseInt(str);
		return true;
	} catch (NumberFormatException nfe){
		return false;
	}
}
}
