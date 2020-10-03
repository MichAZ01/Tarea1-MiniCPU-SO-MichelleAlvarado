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
 *
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
    
    public String extractFileInfo(File file) throws IOException {
        FileInputStream tokensFile = new FileInputStream(file);
        byte[] dataBytes = new byte[(int) file.length()];
        tokensFile.read(dataBytes);
        String data = new String(dataBytes, "UTF-8");
        return data;
    }
    
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
    
    public void createProgramStructure(File selectedFile) throws IOException{
        ProgramLoader programLoader = new ProgramLoader(this.extractFileInfo(selectedFile));
        this.currentProgram = programLoader.getProgram();
    }
    
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
    
    public void CleanCodeTableModel(int columns, int rows){
        Object[][] data = new Object[rows][columns];
        
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                data[i][j] = null;
            }
        }
        setTableModel(data);
    }
    
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
    
    public void executeProgramInstruction() throws IOException{
        String binaryInstruction = CPU.getCPU().getMemory().getMemoryArray()[this.currentProgramCurrentIndex].getCurrentValue();
        CPU.getCPU().executeInstruction(binaryInstruction, this.currentProgramCurrentIndex);
        this.setCPURegistersGUI();
        this.setGeneralRegistersGUI();
        this.currentProgramCurrentIndex = this.currentProgramCurrentIndex + 1;
        this.linesExecuted = this.linesExecuted + 1;
    }
    
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
    
    public void setCPURegistersGUI() throws IOException{
        view.pcValueLabel.setText(CPU.getCPU().getCPURegisterByName("PC").getCurrentValue());
        view.acValueLabel.setText(CPU.getCPU().getCPURegisterByName("AC").getCurrentValue());
        view.irValueLabel.setText(CPU.getCPU().getCPURegisterByName("IR").getCurrentValue());
    }
    
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
