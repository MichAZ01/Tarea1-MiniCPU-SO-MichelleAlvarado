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
public class CPU {
    private static CPU myCPU = null;
    private Memory memory;
    private GeneralRegister[] generalRegisters;
    private CPURegister[] CPURegisters;
    private Operation[] CPUOperations;
    
    private CPU() throws IOException{
        this.memory = new Memory();
        this.initializeCPUOperations();
        this.initializeCPURegisters();
        this.initializeGeneralRegisters();
    }
    
    public static CPU getCPU() throws IOException{
        if(myCPU == null){
            myCPU = new CPU();
        }
        return myCPU;
    }    
    
    public void initializeGeneralRegisters() throws IOException{
        String[] generalRegistersLines = new ConfigReader().readFile(System.getProperty("user.dir") + "\\src\\logic\\ConfigFiles\\GeneralRegisters.txt");
        int size = generalRegistersLines.length;
        String line = "";
        String[] registerConfig = new String[2];
        this.generalRegisters = new GeneralRegister[size];
        for(int i = 0; i < size; i++){
            line = generalRegistersLines[i];
            registerConfig = line.split(" ");
            GeneralRegister register = new GeneralRegister(registerConfig[0], registerConfig[1].length(), registerConfig[1]);
            this.generalRegisters[i] = register;
        }
    }
    
    public void initializeCPURegisters() throws IOException{
        String[] CPURegistersLines = new ConfigReader().readFile(System.getProperty("user.dir") + "\\src\\logic\\ConfigFiles\\CPURegisters.txt");
        int size = CPURegistersLines.length;
        String line = "";
        this.CPURegisters = new CPURegister[size];
        for(int i = 0; i < size; i++){
            line = CPURegistersLines[i];
            CPURegister register = new CPURegister(line);
            this.CPURegisters[i] = register;
        }
    }
    
    public void initializeCPUOperations() throws IOException{
        String[] CPUOperationsLines = new ConfigReader().readFile(System.getProperty("user.dir") + "\\src\\logic\\ConfigFiles\\Operations.txt");
        int size = CPUOperationsLines.length;
        String line = "";
        String[] operationConfig = new String[2];
        this.CPUOperations = new Operation[size];
        for(int i = 0; i < size; i++){
            line = CPUOperationsLines[i];
            operationConfig = line.split(" ");
            Operation operation = new Operation(operationConfig[0], operationConfig[1].length(), operationConfig[1]);
            this.CPUOperations[i] = operation;
        }
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public GeneralRegister[] getGeneralRegisters() {
        return generalRegisters;
    }

    public void setGeneralRegisters(GeneralRegister[] generalRegisters) {
        this.generalRegisters = generalRegisters;
    }

    public CPURegister[] getCPURegisters() {
        return CPURegisters;
    }

    public void setCPURegisters(CPURegister[] CPURegisters) {
        this.CPURegisters = CPURegisters;
    }

    public Operation[] getCPUOperations() {
        return CPUOperations;
    }

    public void setCPUOperations(Operation[] CPUOperations) {
        this.CPUOperations = CPUOperations;
    }
    
    public String[] getCPUOperationNames(){
        int size = this.CPUOperations.length;
        String[] operationNames = new String[size];
        for(int i = 0; i < size; i++){
            operationNames[i] = this.CPUOperations[i].getOperationName();
        }
        return operationNames;
    }
    
    public String[] getGeneralRegisterNames(){
        int size = this.generalRegisters.length;
        String[] registerNames = new String[size];
        for(int i = 0; i < size; i++){
            registerNames[i] = this.generalRegisters[i].getRegisterName();
        }
        return registerNames;
    }
    
    
}
