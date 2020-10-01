/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Michelle Alvarado
 */
public class ConfigReader {
    
    public ConfigReader(){
        
    }
    
    public static String[] readFile(String selectedFile) throws FileNotFoundException, IOException {
        String cadena;
        String[] lines;
        
        FileReader fr = new FileReader(selectedFile);
        BufferedReader buffer = new BufferedReader(fr);
        
        int size = getFileLength(selectedFile);
        lines = new String[size];
        int i = 0;
        while((cadena = buffer.readLine())!=null) {
            lines[i] = cadena;
            i++;
        }
        buffer.close();
        
        return lines;
    }
    
    public static int getFileLength(String selectedFile) throws FileNotFoundException, IOException {
        String cadena;
        int size = 0;
        
        FileReader file = new FileReader(selectedFile);
        BufferedReader buffer = new BufferedReader(file);
        
        while((cadena = buffer.readLine())!=null) {
            size += 1;
        }
        buffer.close();
        
        return size;
    }
    
    
}
