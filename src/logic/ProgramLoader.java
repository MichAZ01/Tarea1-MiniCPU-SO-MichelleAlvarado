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
    
    public ProgramLoader(String data) throws IOException{
        this.program = new Program();
        this.LoadProgramInstructions(data);
        this.LoadBinaryInstructions();
        this.program.setProgramSize(this.program.getProgramInstructions().length);
    }

    
    public void LoadProgramInstructions(String data) throws IOException{
        String lines[] = data.split("\\r?\\n");
        this.program.setProgramInstructions(lines);
    }
    
    public void LoadBinaryInstructions() throws IOException{
        String[] instructions = this.program.getProgramInstructions();
        int size = instructions.length;
        String[] binaryInstructions = new String[size];
        ProgramConversor programConversor = new ProgramConversor();
        for(int i = 0; i < size; i++){
            String binaryInstruction = programConversor.createBinaryInstruction(instructions[i]);
            binaryInstructions[i] = binaryInstruction;
        }
        this.program.setBinaryInstructions(binaryInstructions);
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
    
}
