package aesi.io;

import java.io.*;

/**
 * A class that contains a lot of public static functions to manipulate data on the computer. These functions
 * will probably always come in handy as now a simple actions such a writing to a file can be performed in 
 * one line of code with a simple boolean indicating success without the pain of having to add try-catch 
 * statements or considering thousands of scenarios for what could be the case, i.e.: does the file exist? 
 * do I have write access?
 * <p>
 * Also, this class
 * only contains static methods but Java does not support the static declaration for classes. The result
 * is a disappointing constructor entry in the JavaDoc. We should sincerely consider compiling this JavaDoc
 * from source for the full effect. For now, please ignore the constructor entry in the JavaDoc, it's not 
 * supposed to be there!
 * 
 * @author Maarten Inja
 *
 */
public class IO 
{
    //////////////////// i/o

    /**
     * Writes the String content to the file represented by fileName. Actually uses the other  
     * {@link #writeFile(String fileName, String content, boolean overwrite) writeFile} method where 
     * it matches the arguments and fills in overwrite as false. 
     * <p>
     * <li> If the file already exists the existing file is {@link #updateFile(File file, String content) updated}. 
     * <li> If the file does not yet exist the file is created.
     * <li> If anything fails an error is printed to System.err in the format <i>ERROR: error message</i>.
     * 
     * @param fileName	The string that represents a file. 
     * @param content	The string that represents the content that will be written in the file.
     * 
     * @return	A boolean indicating success. If anything fails this boolean is false, otherwise true.
     * 
     * @see #updateFile(File file, String content)
     * @see #writeFile(String fileName, String content, boolean overwrite)
     */
    public static boolean writeFile(String fileName, String content)
    {
        return writeFile(fileName, content, false);
    }

    /**
     * Writes the String content to the file represented by fileName, overwrites when overwrite
     * is true and updates the said file when false.  
     * <p>
     * <li> If the file already exists and overwrite is true the existing file is deleted and 
     * a new found created with content as the content. 
     * <li> If the file already exists and overwrite is false the existing file is 
     * {@link #updateFile(File file, String content) updated}. 
     * <li> If the file does not yet exist the file is created.
     * <li> If anything fails an error is printed to System.err in the format <i>ERROR: error message</i>.
     * 
     * @param fileName	The string that represents a file.
     * @param content	The string that represents the content that will be written in the file.
     * @param overwrite	The boolean that indicates if the file should be updated or deleted 
     * when the file already is in existence. 
     * 
     * @return	A boolean indicating success. If anything fails this boolean is false, otherwise true.
     * 
     * @see #updateFile(File file, String content)
     * @see #writeFile(String fileName, String content)
     */
    public static boolean writeFile(String fileName, String content, boolean overwrite)
    {
        File f = new File(fileName);
        if (f.exists()) 
        {
            if (!overwrite)
                return updateFile(f, content);
            f.delete();
        }
        else 
        {
            try {f.createNewFile(); } 
            catch(Exception e) 
            { 
        	System.err.print("ERROR: " + e.getMessage() + "\n"); 
                return false;
            }
        }
        try
        {
            FileWriter fstream = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(content);
            out.close();
        }   
        catch (Exception e)
        {
            System.err.print("ERROR: " + e.getMessage() +"\n");
            return false;
        }
        return true;
    }

    /**
     * Updates a file object with content which will be written at the end
     * of the file. This method uses a BufferedWriter and I have a feeling this 
     * is quite optimal! 
     * <p>
     * This file is probably mostly called by {@link #writeFile(String fileName, String content, boolean overwrite)}.
     * <p>
     * If anything goes south an error is printed to System.err in the format <i>ERROR: error message</i>.
     * 
     * @param file	The File object representing the file to be updated.
     * @param content	The content, a string to be added (written) on the bottom of the file.
     * @return	A boolean indicating success. If anything fails this boolean is false, otherwise true.
     * @see 	#writeFile(String fileName, String content, boolean overwrite)
     * @todo	Currently only support for the File object is present. It would be pretty to also have 
     * an overloaded method accepting a string as file. 
     */
    public static boolean updateFile(File file, String content) 
    {
        try 
        {
            BufferedWriter bw = new BufferedWriter (new FileWriter (file, true));
            bw.write(content);
            bw.flush();
            bw.close();
        }
        catch (Exception e)
        {
            System.err.print("ERROR: " + e.getMessage() + "\n");
            return false;
        }
        return true;
    }

    /**
     * Deletes a file. Not everything is completely implemented. The result of this is clear when
     * <i>runtime errors are thrown</i>. Preferably I would like to see indications of success or failure only 
     * in the returned boolean. 
     * 
     * @param fileName	The string that represents the file that needs to be deleted
     * @return	A boolean indicating success. If anything fails this boolean is false, otherwise true. 
     * This does not mean that when false is returned the file is not there, it could also mean the file
     * did not exist in the first place. 
     * @todo Complete the implementation of this method. It works but isn't completely finished.
     */
    public static boolean deleteFile(String fileName)
    {
        File f = new File(fileName);

        // Make sure the file or directory exists and isn't write protected
        if (!f.exists())
            return false;

        if (!f.canWrite())
            throw new IllegalArgumentException("Delete: write protected: " + fileName);

        boolean success = f.delete();

        if (!success)
            throw new IllegalArgumentException("Delete: deletion failed");
        return true;
    }

    /**
     * Reads a file as a string array. If anything fails an error is printed to System.err
     *  in the format <i>ERROR: error message</i>. This method is current also used by 
     *  {@link #readFile(String)}, which, inefficiently, reads a file as a string.
     * 
     * @param file	A string that represents the file to be read.
     * @return	A string array that is the content of the file or null when something went wrong.
     * @see #readFile(String)
     */
    public static String[] readFileAsLines(String file)
    {
        try
        {
            FileInputStream fileStream = new FileInputStream(file);
            DataInputStream dataStream = new DataInputStream(fileStream);
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(dataStream));
            String contentString = "";
            String line = bufferReader.readLine();
            while(line != null)
            {
                contentString = contentString + line + "\n"; 
                line = bufferReader.readLine();
            }
            dataStream.close(); 
            return contentString.split("\n");
        }
        catch (Exception e)
        {
            System.err.print("ERROR: " + e.getMessage() +"\n");
        }
        return null;
    }
    
    /**
     * Reads a file as a string. If anything fails an error is printed to System.err
     *  in the format <i>ERROR: error message</i>. This method is currently <i>very</i>
     *  Lazily implemented and actually uses {@link #readFileAsLines(String file)} to first 
     *  create a string array which it then converts to a string using an enhanced for loop.
     *  
     * @param file	A string that represents the file to be read.
     * @return	A string that is the content of the file or null when something went wrong.
     * @see #readFileAsLines(String file)
     * @todo This function can be a lot more efficient without that much trouble (don't forget
     * to also update the documentation at {@link #readFileAsLines(String file)}). 
     */
    public static String readFile(String file)
    {
	String[] lines = readFileAsLines(file);
	String result = "";
	for (String l : lines)
	    result += l + "\n";
	return result;
    }


}
