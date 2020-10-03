/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import GUI.MiniPC;
import GUI.MyCustomFilter;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import logic.BinaryConversor;
import logic.CPU;
import logic.Program;
import logic.ProgramConversor;
import logic.ProgramLoader;
import logic.ProgramValidator;

/**
 * This class works like an intermediary between the view and the all logic of program
 * @author Michelle Alvarado
 */
public class Controller implements ActionListener {
    private MiniPC view;
    private Program currentProgram;
    private int currentProgramInitIndex;
    private int linesExecuted;
    private int currentProgramCurrentIndex;
    public Controller(){
    }
    
    /**
     * Manages the different cases for an event like open a file, start the execution of a program or execute the next instruction of a program
     * @param ae: an event produced by an object in the view (button in this case)
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch(ae.getActionCommand()){
            case "openFile":
                {
                    try {
                        this.CleanCodeTableModel(4, 16);
                        this.view.startButton.setEnabled(false);
                        this.view.nextButton.setEnabled(false);
                        OpenFolderButtonActionPerformed(this.view);
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "start":
                {
                    try {
                        this.startProgramExecution();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "next":
        {
            try {
                this.nextInstruction();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            default:
                break;
        }
    }
    
    public void showView(){
         /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MiniPC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiniPC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiniPC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiniPC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        this.view = new MiniPC();
        MiniPC viewP = this.view;
        this.view.OpenFolderButton.addActionListener(this);
        this.view.startButton.addActionListener(this);
        this.view.nextButton.addActionListener(this);
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                viewP.setVisible(true);
            }
        });
    }
    
    /**
     * This method is executed by the open file button, is responsible of create a fileChooser that only accepts .asm files and if its one is choosed is
     * stored in the 'selectedFile' attribute for later use.
     * @param view
     * @throws IOException 
     */
    public void OpenFolderButtonActionPerformed(javax.swing.JFrame view) throws IOException {                                                 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        MyCustomFilter assemblerFilter = (MyCustomFilter) new MyCustomFilter(".asm", "Archivo de código Ensamblador");
        fileChooser.addChoosableFileFilter(assemblerFilter);
        int result = fileChooser.showOpenDialog(view);
        File selectedFile = null;
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                selectedFile = fileChooser.getSelectedFile();
                CPU.getCPU().cleanMemorySpaces();
                CPU.getCPU().cleanCPURegisters();
                this.setCPURegistersGUI();
                this.setGeneralRegistersGUI();
                this.verifyFileFormat(selectedFile);
                break;
            case JFileChooser.CANCEL_OPTION:
                break;
            default:
                break;
        }
    }   
    
    /**
     * return an string that contains the all lines of the file concatenated
     * @param file: an assembly file
     * @return
     * @throws IOException 
     */
    public String extractFileInfo(File file) throws IOException {
        FileInputStream tokensFile = new FileInputStream(file);
        byte[] dataBytes = new byte[(int) file.length()];
        tokensFile.read(dataBytes);
        String data = new String(dataBytes, "UTF-8");
        return data;
    }
    
    /**
     * Calls a program validator that verify if the structure of the assembly instructions is correct. If its is: calls the methods that initialize
     * the variables in the view. If its not: displays an error message.
     * @param selectedFile: the current .asm file that was opened
     * @throws IOException 
     */
    public void verifyFileFormat(File selectedFile) throws IOException{
        ProgramValidator programValidator;
        programValidator = new ProgramValidator();
        Boolean correctFormat;
        correctFormat = programValidator.validateSelectedFile(this.extractFileInfo(selectedFile));
        if(correctFormat){
            this.createProgramStructure(selectedFile);
            this.setTableModel(this.InitializeCodeTableModel(4, 16));
            this.view.startButton.setActionCommand("start");
            this.view.startButton.setEnabled(true);
        }
        else{
            ImageIcon icon = new ImageIcon("GUI/Images/error.png");
            JOptionPane.showMessageDialog(this.view,"El programa no tiene el formato de instrucciones correctas","Error al leer archivo",JOptionPane.PLAIN_MESSAGE,icon);
        }
    }
    
    /**
     * Calls a object 'ProgramLoader' that loads the current program in an array that contains one instruction for each line in the original file
     * @param selectedFile: : the current .asm file that was opened
     * @throws IOException 
     */
    public void createProgramStructure(File selectedFile) throws IOException{
        ProgramLoader programLoader = new ProgramLoader(this.extractFileInfo(selectedFile));
        this.currentProgram = programLoader.getProgram();
    }
    
    /**
     * sets the values of the GUI table to displays the program and his equivalent binary code
     * @param columnLength
     * @param rowsTotalSize
     * @return 
     */
    public Object[][] InitializeCodeTableModel(int columnLength, int rowsTotalSize){
        int programSize = this.currentProgram.getProgramSize();
        int rows = this.currentProgram.getProgramSize();
        int columns = columnLength;
        if(rows < rowsTotalSize) rows = rows + (rowsTotalSize - rows);
        Object[][] data = new Object[rows][columns];
        
        for(int i = 0; i < rows; i++){
            data[i][0] = null;
            if(i < programSize){
                data[i][1] = this.currentProgram.getProgramInstructions()[i];
                data[i][2] = this.currentProgram.getBinaryInstructions()[i];
            }
            else{
                data[i][1] = null;
                data[i][2] = null;
            }
        }
        return data;
    }
    
    /**
     * displays the data in the view's table
     * @param data 
     */
    public void setTableModel(Object[][] data){
        this.view.codeTable.setModel(new javax.swing.table.DefaultTableModel(
            data,
            new String [] {
                "Posición memoria", "Código ASM", "Código Binario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    /**
     * restarts the view's table
     * @param columns
     * @param rows 
     */
    public void CleanCodeTableModel(int columns, int rows){
        Object[][] data = new Object[rows][columns];
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                data[i][j] = null;
            }
        }
        setTableModel(data);
    }
    
    /**
     * sets the values of the GUI table to displays the program, his equivalent binary code and and the memory location where each instruction is located
     * @param columnLength
     * @param rowsTotalSize
     * @param index
     * @return 
     */
    public Object[][] InitializeCodeTableModelWithMemoryPositions(int columnLength, int rowsTotalSize, int index){
        int programSize = this.currentProgram.getProgramSize();
        int rows = this.currentProgram.getProgramSize();
        int columns = columnLength;
        if(rows < rowsTotalSize) rows = rows + (rowsTotalSize - rows);
        Object[][] data = new Object[rows][columns];
        
        for(int i = 0; i < rows; i++){
            if(i < programSize){
                data[i][0] = index;
                data[i][1] = this.currentProgram.getProgramInstructions()[i];
                data[i][2] = this.currentProgram.getBinaryInstructions()[i];
                index += 1;
            }
            else{
                data[i][0] = null;
                data[i][1] = null;
                data[i][2] = null;
            }
        }
        return data;
    }
    
    /**
     * Cleans the memory and the CPU registers to make sure the program runs properly, initialize the 'TableModel' to displays the program instructions
     * and executes the first instruction of the program. Disabled the start button and enabled the next button to executes the next program instruction
     * @throws IOException 
     */
    public void startProgramExecution() throws IOException{
        CPU.getCPU().cleanMemorySpaces();
        CPU.getCPU().cleanCPURegisters();
        this.currentProgramInitIndex = CPU.getCPU().loadProgramIntoMemory(this.currentProgram.getBinaryInstructions());
        this.setTableModel(this.InitializeCodeTableModelWithMemoryPositions(4, 16, currentProgramInitIndex));
        this.linesExecuted = 0;
        this.currentProgramCurrentIndex = this.currentProgramInitIndex;
        view.startButton.setEnabled(false);
        this.executeProgramInstruction();
        view.nextButton.setEnabled(true);
    }
    
    /**
     * Calls the respective method in the CPU to execute the current instruction of the program
     * @throws IOException 
     */
    public void executeProgramInstruction() throws IOException{
        String binaryInstruction = CPU.getCPU().getMemory().getMemoryArray()[this.currentProgramCurrentIndex].getCurrentValue();
        CPU.getCPU().executeInstruction(binaryInstruction, this.currentProgramCurrentIndex);
        this.setCPURegistersGUI();
        this.setGeneralRegistersGUI();
        this.currentProgramCurrentIndex = this.currentProgramCurrentIndex + 1;
        this.linesExecuted = this.linesExecuted + 1;
    }
    
    /**
     * This method is executed by 'the next button'. Validates if exists a next instruction to execute. If its not: disables the next button and
     * enabled the start button to allowed restart the program execution
     * @throws IOException 
     */
    public void nextInstruction() throws IOException{
        if(this.linesExecuted < this.currentProgram.getProgramSize()){
            this.executeProgramInstruction();
        }
        if(this.linesExecuted == this.currentProgram.getProgramSize()){
            CPU.getCPU().cleanMemorySpaces();
            CPU.getCPU().cleanCPURegisters();
            view.startButton.setEnabled(true);
            view.nextButton.setEnabled(false);
        }
    }
    
    /**
     * loads the current value of the CPU registers into the GUI respective labels.
     * @throws IOException 
     */
    public void setCPURegistersGUI() throws IOException{
        view.pcValueLabel.setText(CPU.getCPU().getCPURegisterByName("PC").getCurrentValue());
        view.acValueLabel.setText(CPU.getCPU().getCPURegisterByName("AC").getCurrentValue());
        view.irValueLabel.setText(CPU.getCPU().getCPURegisterByName("IR").getCurrentValue());
    }
    
    /**
     * loads the current value of the general registers (that are ubicated in the memory) into the GUI respective labels
     * @throws IOException 
     */
    public void setGeneralRegistersGUI() throws IOException{
        BinaryConversor binaryConversor = new BinaryConversor();
        String[] labelRegisterNames = new String[]{"AX", "BX", "CX", "DX"};
        Label[] labelRegisterObjects = new Label[]{view.axValueLabel, view.bxValueLabel, view.cxValueLabel, view.dxValueLabel};
        
        for(int i = 0; i < labelRegisterNames.length; i++){
           String binaryCode = CPU.getCPU().getGeneralRegisterByName(labelRegisterNames[i]).getBinaryCode();
            int index = binaryConversor.binaryToDecimal(binaryCode);
            int value = new BinaryConversor().binaryToDecimal(CPU.getCPU().getMemory().getMemoryArray()[index].getCurrentValue());
            labelRegisterObjects[i].setText(Integer.toString(value)); 
        }
    }
}
