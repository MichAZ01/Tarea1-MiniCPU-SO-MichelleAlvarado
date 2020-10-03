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
 * that class contains the methods that allows to read and get the file length of the configuration files
 * @author Michelle Alvarado
 */
public class ConfigReader {
    
    public ConfigReader(){
        
    }
    
    /**
     * takes a path and returns an array that contains all the lines of the respective file
     * @param selectedFile
     * @return lines: all the lines of the configuration file
     * @throws FileNotFoundException
     * @throws IOException 
     */
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
    
    /**
     * takes a file and returns the amount of the lines that its file contains
     * @param selectedFile
     * @return size
     * @throws FileNotFoundException
     * @throws IOException 
     */
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
