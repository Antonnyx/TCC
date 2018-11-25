/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Antonio
 */
public class TestaCopiarArquivo {
    public static void main(String[] args) throws IOException {
        copyFile(new File("C:\\Users\\Antonio\\Videos\\video1.mp4"), new File("C:\\Users\\Antonio\\Videos\\Copia\\video1.mp4"));
        String dict = FilenameUtils.getPath("C:\\Users\\Antonio\\Videos\\video1.mp4");
        String file = FilenameUtils.getBaseName("C:\\Users\\Antonio\\Videos\\video1.mp4") + "." + FilenameUtils.getExtension("C:\\Users\\Antonio\\Videos\\video1.mp4");
        System.out.println("directory: " + dict);
        System.out.println("file: "+ file);
        
    }
    public static void copyFile(File source, File destination) throws IOException {
        System.out.println("oi");
        if (destination.exists()){
            destination.delete();
        }
        FileChannel sourceChannel = null;
        FileChannel destinationChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destinationChannel = new FileOutputStream(destination).getChannel();
            sourceChannel.transferTo(0, sourceChannel.size(),
                    destinationChannel);
        } finally {
            if (sourceChannel != null && sourceChannel.isOpen())
                sourceChannel.close();
            if (destinationChannel != null && destinationChannel.isOpen())
                destinationChannel.close();
       }
   }
    
}
