/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;

/**
 * MyCustomFilter Helps restrict only files with desired extension can be opened
 * @author Michelle Alvarado
 */
public class MyCustomFilter extends javax.swing.filechooser.FileFilter{
        private String extension;
        private String description;
        
        /**
         * Description
         * @param extension: the desired extension to be opened
         * @param description: message that clarify which files can be opened
         */
        public MyCustomFilter(String extension, String description){
            this.extension = extension;
            this.description = description;
        }
        
        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
            return true;
            }
            return file.getName().endsWith(".asm");
        }
        
        @Override
        public String getDescription() {
            return description + String.format(" (*%s)", extension);
        }
}


