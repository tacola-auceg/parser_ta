package analyser;
//vf
import java.net.URL;
import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
* Load images from correct place (directory or JAR file) into GUI.
*
*/
public class FileLocator
{
/**
* Load file from correct place (directory or JAR file).
*
* @param imageName name of image to be loaded (with no path)
* @return loaded image
*/
public static URL getFile(String fileName)
{
ClassLoader cl = FileLocator.class.getClassLoader();
//System.out.println("file name from get resource"+cl.getResource("noun.txt"));
//System.out.println("as url"+ cl.getResource(fileName));
//System.out.println("as file"+cl.getResource(fileName).getFile());


return cl.getResource(fileName);

}

}