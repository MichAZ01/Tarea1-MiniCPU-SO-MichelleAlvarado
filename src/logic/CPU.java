/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.IOException;

/**
 * a Singleton that works like a central processing unit
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
    
    /**
     * Singleton method
     * @return
     * @throws IOException 
     */
    public static CPU getCPU() throws IOException{
        if(myCPU == null){
            myCPU = new CPU();
        }
        return myCPU;
    }    
    
    /**
     * Calls a config reader object that reads a text file that contains the name of the general registers and his respective binary code
     * @throws IOException 
     */
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
    
    /**
     * Calls a config reader object that reads a text file that contains the name of the cpu registers and his respective initial value
     * @throws IOException 
     */
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
    
    /**
     * Calls a config reader object that reads a text file that contains the operations allowed by the CPU and his respective binary code
     * @throws IOException 
     */
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
    
    /**
     * this method returns an array that contains the name of the all operations allowed by the cpu
     * @return 
     */
    public String[] getCPUOperationNames(){
        int size = this.CPUOperations.length;
        String[] operationNames = new String[size];
        for(int i = 0; i < size; i++){
            operationNames[i] = this.CPUOperations[i].getOperationName();
        }
        return operationNames;
    }
    
    /**
     * this method returns an array that contains the name of the all general register of the cpu
     * @return 
     */
    public String[] getGeneralRegisterNames(){
        int size = this.generalRegisters.length;
        String[] registerNames = new String[size];
        for(int i = 0; i < size; i++){
            registerNames[i] = this.generalRegisters[i].getRegisterName();
        }
        return registerNames;
    }
    
    /**
     * this method return the respective binary code of an operation
     * @param operation
     * @return 
     */
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
    
    /**
     * this method returns the respective binary code of and general register
     * @param register
     * @return 
     */
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
    
    /**
     * this method returns the operation asociated by the paramater name
     * @param opName
     * @return 
     */
    public Operation getOperationByName(String opName){
        Operation op = null;
        for(int i = 0; i < this.CPUOperations.length; i++){
            op = this.CPUOperations[i];
            if(opName.equals(op.getOperationName())) break;
        }
        return op;
    }
    
    /**
     * this methos returns the general register asociated by the parameter name
     * @param registerName
     * @return 
     */
    public GeneralRegister getGeneralRegisterByName(String registerName){
        GeneralRegister register = null;
        for(int i = 0; i < this.generalRegisters.length; i++){
            register = this.generalRegisters[i];
            if(registerName.equals(register.getRegisterName())) break;
        }
        return register;
    }
    
    /**
     * this method returns the cpu register asociated by the parameter name
     * @param registerName
     * @return 
     */
    public CPURegister getCPURegisterByName(String registerName){
        CPURegister register = null;
        for(int i = 0; i < this.CPURegisters.length; i++){
            register = this.CPURegisters[i];
            if(registerName.equals(register.getRegisterName())) break;
        }
        return register;
    }
    
    /**
     * this method returns the general register asociated to the binary code sends by parameter
     * @param binaryCode
     * @return 
     */
    public GeneralRegister getGeneralRegisterByBinaryCode(String binaryCode){
        GeneralRegister register = null;
        for(int i = 0; i < this.generalRegisters.length; i++){
            register = this.generalRegisters[i];
            if(binaryCode.equals(register.getBinaryCode())) break;
        }
        return register;
    }
    
    /**
     * this method returns the operation asociated to the binary code sends by parameter
     * @param binaryCode
     * @return 
     */
    public Operation getOperationByBinaryCode(String binaryCode){
        Operation op = null;
        for(int i = 0; i < this.CPUOperations.length; i++){
            op = this.CPUOperations[i];
            if(binaryCode.equals(op.getBinaryCode())) break;
        }
        return op;
    }
    
    /**
     * takes each program binary instruction and stores them into the memory from a random position (that can be from 50 to size of the memory - 1
     * if the program fits within the amount of spaces it has)
     * @param binaryInstructions: the set of program instructions in 16 bits binary format 
     * @return 
     */
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
        return initIndex;
    }
    
    /**
     * 
     * @param min: the minimum position of the memory where a program can be store
     * @param max: the amount of memory spaces that the memory has
     * @param programSize: the amount of instructions that the current program has
     * @return 
     */
    public int getProgramInitialPosition(int min, int max, int programSize){
        Boolean invalidIndex = true;
        int index = 0;
        
        while(invalidIndex){
            index = (int) Math.floor(Math.random()*(max - min + 1)+ min);
            if((programSize + index - 1) < this.memory.getMemoryLength()) invalidIndex = false;
        }
        
        return index;
    }
    
    /**
     * creates all the memory spaces that the memory can have
     */
    private void initializeMemorySpaces(){
        MemorySpace[] memoryArray;
        int memoryLength = this.memory.getMemoryLength();
        memoryArray = new MemorySpace[memoryLength];
        for(int i = 0; i < memoryLength; i++){
            memoryArray[i] = new MemorySpace(i);
        }
        this.memory.setMemoryArray(memoryArray);
    }
    
    /**
     * restarts the default value of the all memory spaces
     */
    public void cleanMemorySpaces(){
        int memoryLength = this.memory.getMemoryLength();
        for(int i = 0; i < memoryLength; i++){
            this.memory.getMemoryArray()[i].setCurrentValue("0");
        }
    }
    
    /**
     * restarts the default value of the all cpu registers
     */
    public void cleanCPURegisters(){
        int size = this.CPURegisters.length;
        for(int i = 0; i < size; i++){
            this.CPURegisters[i].setCurrentValue(this.CPURegisters[i].getInitValue());
        }
    }
    
    /**
     * in this method the PC register will point at the current instruction that is being executed.
     * Also the IR register contains the ASM instruction equivalent to the binary instruction.
     * Is taken the operation part of the instruction and it is evaluated what operation should be performed
     * @param binaryInstruction: the current instruction being executed
     * @param currentIndex
     * @throws IOException 
     */
    public void executeInstruction(String binaryInstruction, int currentIndex) throws IOException{
        CPURegister pcRegister = this.getCPURegisterByName("PC");
        pcRegister.setCurrentValue(Integer.toString(currentIndex));
        String asmInstruction = new ProgramConversor().binaryToASMInstruction(binaryInstruction);
        CPURegister irRegister = this.getCPURegisterByName("IR");
        irRegister.setCurrentValue(asmInstruction);
        String operation = asmInstruction.split(" ")[0];
        String[] binaryOperationParts = binaryInstruction.split(" ");
        switch(operation){
            case "MOV":
                //move the number part to the indicated register
                this.executeMOVInstruction(binaryOperationParts[1], binaryOperationParts[2]);
                break;
            case "LOAD":
                //load the value of the indicated register into AC register
                this.executeLOADInstruction(binaryOperationParts[1]);
                break;
            case "STORE":
                //store the value of the AC register into the indicated register
                this.executeSTOREInstruction(binaryOperationParts[1]);
                break;
            case "SUB":
                //substract AC - value of the indicated register and store them into the AC register
                this.executeSUBInstruction(binaryOperationParts[1]);
                break;
            case "ADD":
                //sum AC - value of the indicated register and store them into the AC register
                this.executeADDInstruction(binaryOperationParts[1]);
                break;
            default:
                break;
        }
    }
    
    /**
     * 
     * @param binaryCode: destination register
     * @param binaryNumber: the number that will be moved
     */
    public void executeMOVInstruction(String binaryCode, String binaryNumber){
        BinaryConversor binaryConversor = new BinaryConversor();
        int memoryPosition = binaryConversor.binaryToDecimal(binaryCode);
        this.memory.getMemoryArray()[memoryPosition].setCurrentValue(binaryNumber);
    }
    
    /**
     * 
     * @param binaryCode: the register that contains the value that will be load into the AC register 
     */
    public void executeLOADInstruction(String binaryCode){
        BinaryConversor binaryConversor = new BinaryConversor();
        int memoryPosition = binaryConversor.binaryToDecimal(binaryCode);
        String value = this.memory.getMemoryArray()[memoryPosition].getCurrentValue();
        String decimalValue = Integer.toString(binaryConversor.binaryToDecimal(value));
        CPURegister acRegister = this.getCPURegisterByName("AC");
        acRegister.setCurrentValue(decimalValue);
    }
    
    /**
     * 
     * @param binaryCode: the register that will be contains the value that currently are stored into the AC register
     */
    public void executeSTOREInstruction(String binaryCode){
        BinaryConversor binaryConversor = new BinaryConversor();
        CPURegister acRegister = this.getCPURegisterByName("AC");
        String acValue = acRegister.getCurrentValue();
        int memoryPosition = binaryConversor.binaryToDecimal(binaryCode);
        String acBinaryValue = binaryConversor.decimalToBinary(Integer.parseInt(acValue));
        this.memory.getMemoryArray()[memoryPosition].setCurrentValue(acBinaryValue);
    }
    
    /**
     * 
     * @param binaryCode: the register that contains the value that will be substracted to the value into the AC register
     */
    public void executeSUBInstruction(String binaryCode){
        BinaryConversor binaryConversor = new BinaryConversor();
        CPURegister acRegister = this.getCPURegisterByName("AC");
        int acValue = Integer.parseInt(acRegister.getCurrentValue());
        int memoryPosition = binaryConversor.binaryToDecimal(binaryCode);
        int registerValue = binaryConversor.binaryToDecimal(this.memory.getMemoryArray()[memoryPosition].getCurrentValue());
        int sub = acValue - registerValue;
        String result = Integer.toString(sub);
        acRegister.setCurrentValue(result);
    }
    
    /**
     * 
     * @param binaryCode: the register that contains the value that will be added to the value into the AC register
     */
    public void executeADDInstruction(String binaryCode){
        BinaryConversor binaryConversor = new BinaryConversor();
        CPURegister acRegister = this.getCPURegisterByName("AC");
        int acValue = Integer.parseInt(acRegister.getCurrentValue());
        int memoryPosition = binaryConversor.binaryToDecimal(binaryCode);
        int registerValue = binaryConversor.binaryToDecimal(this.memory.getMemoryArray()[memoryPosition].getCurrentValue());
        int sum = acValue + registerValue;
        String result = Integer.toString(sum);
        acRegister.setCurrentValue(result);
    }
}
