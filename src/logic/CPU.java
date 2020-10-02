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
        this.initializeMemorySpaces();
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
        String[] registersConfig = new String[2];
        this.CPURegisters = new CPURegister[size];
        for(int i = 0; i < size; i++){
            line = CPURegistersLines[i];
            registersConfig = line.split(" ");
            CPURegister register = new CPURegister(registersConfig[0], registersConfig[1]);
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
    
    public String getAnOperationBinary(String operation){
        String binaryCode = "";
        for(int i = 0; i < this.CPUOperations.length; i++){
            if(operation.equals(this.CPUOperations[i].getOperationName())){
                binaryCode = this.CPUOperations[i].getBinaryCode();
                break;
            }
        }
        return binaryCode;
    }
    
    public String getAnGeneralRegisterBinary(String register){
        String binaryCode = "";
        for(int i = 0; i < this.generalRegisters.length; i++){
            if(register.equals(this.generalRegisters[i].getRegisterName())){
                binaryCode = this.generalRegisters[i].getBinaryCode();
                break;
            }
        }
        return binaryCode;
    }
    
    public Operation getOperationByName(String opName){
        Operation op = null;
        for(int i = 0; i < this.CPUOperations.length; i++){
            op = this.CPUOperations[i];
            if(opName.equals(op.getOperationName())) break;
        }
        return op;
    }
    
    public GeneralRegister getGeneralRegisterByName(String registerName){
        GeneralRegister register = null;
        for(int i = 0; i < this.generalRegisters.length; i++){
            register = this.generalRegisters[i];
            if(registerName.equals(register.getRegisterName())) break;
        }
        return register;
    }
    
    public CPURegister getCPURegisterByName(String registerName){
        CPURegister register = null;
        for(int i = 0; i < this.CPURegisters.length; i++){
            register = this.CPURegisters[i];
            if(registerName.equals(register.getRegisterName())) break;
        }
        return register;
    }
    
    public GeneralRegister getGeneralRegisterByBinaryCode(String binaryCode){
        GeneralRegister register = null;
        for(int i = 0; i < this.generalRegisters.length; i++){
            register = this.generalRegisters[i];
            if(binaryCode.equals(register.getBinaryCode())) break;
        }
        return register;
    }
    
    public Operation getOperationByBinaryCode(String binaryCode){
        Operation op = null;
        for(int i = 0; i < this.CPUOperations.length; i++){
            op = this.CPUOperations[i];
            if(binaryCode.equals(op.getBinaryCode())) break;
        }
        return op;
    }
    
    public int loadProgramIntoMemory(String[] binaryInstructions){
        int programSize = binaryInstructions.length;
        int initIndex = this.getProgramInitialPosition(50, this.memory.getMemoryLength(), programSize);
        int finalIndex = programSize + initIndex;
        MemorySpace[] memoryArray = this.memory.getMemoryArray();
        int x = 0;
        for(int i = initIndex; i < finalIndex; i++){
            memoryArray[i].setCurrentValue(binaryInstructions[x]);
            x += 1;
        }
        for(int j = 0; j < 100; j++){
            System.out.println(this.memory.getMemoryArray()[j].getMemorySpaceIndex() + ": " + this.memory.getMemoryArray()[j].getCurrentValue());
        }
        return initIndex;
    }
    
    public int getProgramInitialPosition(int min, int max, int programSize){
        Boolean invalidIndex = true;
        int index = 0;
        
        while(invalidIndex){
            index = (int) Math.floor(Math.random()*(max - min + 1)+ min);
            if((programSize + index - 1) < this.memory.getMemoryLength()) invalidIndex = false;
        }
        
        return index;
    }
    
    private void initializeMemorySpaces(){
        MemorySpace[] memoryArray;
        int memoryLength = this.memory.getMemoryLength();
        memoryArray = new MemorySpace[memoryLength];
        for(int i = 0; i < memoryLength; i++){
            memoryArray[i] = new MemorySpace(i);
        }
        this.memory.setMemoryArray(memoryArray);
    }
    
    public void cleanMemorySpaces(){
        int memoryLength = this.memory.getMemoryLength();
        for(int i = 0; i < memoryLength; i++){
            this.memory.getMemoryArray()[i].setCurrentValue("0");
        }
    }
    
    public void cleanCPURegisters(){
        int size = this.CPURegisters.length;
        for(int i = 0; i < size; i++){
            this.CPURegisters[i].setCurrentValue(this.CPURegisters[i].getInitValue());
        }
    }
    
    public void executeInstruction(String BinaryInstruction){
        
    }
    
}
