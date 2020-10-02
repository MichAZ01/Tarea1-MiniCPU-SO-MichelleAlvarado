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
public class ProgramLoader {
    private Program program;
    
    public ProgramLoader(){
        this.program = new Program();
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
    
    public void LoadProgramInstructions(String data) throws IOException{
        String lines[] = data.split("\\r?\\n");
        this.program.setProgramInstructions(lines);
    }
    
    public void LoadBinaryInstructions() throws IOException{
        String[] instructions = this.program.getProgramInstructions();
        int size = instructions.length;
        String[] binaryInstructions = new String[size];
        for(int i = 0; i < size; i++){
            String binaryInstruction = this.createBinaryInstruction(instructions[i]);
            binaryInstructions[i] = binaryInstruction;
        }
        this.program.setBinaryInstructions(binaryInstructions);
    }

    
    public String ConstructBinaryInstruction(String operation, String register, int number) throws IOException{
        String binaryOperation = CPU.getCPU().getAnOperationBinary(operation);
        String binaryRegister = CPU.getCPU().getAnGeneralRegisterBinary(register);
        String binaryNumber = new BinaryConversor().decimalToBinary(number);
        String binaryInstruction = binaryOperation + " " + binaryRegister + " " + binaryNumber;
        
        return binaryInstruction;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
    
}
