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
    
    public Boolean validateSingleLine(String line){
        Boolean correctFormat = true;
        
        String operation = line.split(" ")[0];
        System.out.println(operation);
        
        
        
        return correctFormat;
    }
    
    public Boolean validateLineFormat(String data){
        Boolean correctFormat = true;
        String lines[] = data.split("\\r?\\n");
        
        for (int i = 0; i < lines.length; i++){
            if(!validateSingleLine(lines[i])) break; 
        }  
        return correctFormat;
    }
     
    
    public Boolean validateSelectedFile(String data) throws IOException{
        Boolean correctFile = true;
        Boolean fileIsEmpty = data.isEmpty();
        Boolean correctFormat = this.validateLineFormat(data);
        
        return correctFile;
    }
}
