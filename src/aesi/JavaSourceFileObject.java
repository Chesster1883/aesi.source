package aesi;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A datastructure java needs to compile source within source. The object needs as input a URI, which as far
 * as I know is <i>not used</i>. This makes me sad but at least we got it to work!
 * 
 * @author Maarten Inja
 *
 * @todo <li>Low priority, but still awesome. Check what the hips haps is with the URI object. Do we use it at all?
 * 	And why do we store the fileContent? Is it used at any point? One of the two can be left out!!!
 * @todo <li>More priority, but still low as it is already working. For optimization we could check if a custom
 * 	constructor could be used as as far as we all know the only data we use is the content. This would 
 * 	eliminate both ugly and slow code. 
 */
public class JavaSourceFileObject extends javax.tools.SimpleJavaFileObject
{
    /**
     * A string that represents the fileContent. It was not sure if the SimpleJavaFileObject read this 
     * from the URI but no chances were taken and {@link #getCharContent(boolean ignoreEncodingErrors)} was
     * Overridden!
     */
    private String fileContent;
    
    /**
     * The constructor to store the simplest info about a file in a really complicated manner. It's
     * especially horrible how a URISyntaxException had to thrown... :( Overloaded methods can be used here.
     * 
     * @param file	The file object that represents a file. 
     * @param iFileContent	The string that represents the content of the file. 
     * @throws URISyntaxException An exception that had to be thrown and I could do nothing about, sorry guys!
     * 	sorry 'bout that!
     * @see javax.tools.SimpleJavaFileObject
     * @see java.net.URISyntaxException
     */
    public JavaSourceFileObject (File file, String iFileContent) throws URISyntaxException 
    {
	super(new URI(file.getName()), javax.tools.JavaFileObject.Kind.SOURCE);
	fileContent = iFileContent;
    }
    
    /**
     * Overridden because we do not know what the JavaSourceFileObject does with this method
     * and because we already store the fileContent? But I have the feeling we do 
     * not actually use this (but it can still be useful somewhere in the source code of sun).
     * 
     * @param ignoreEncodingErrors self explanatory
     * @throws IOException self explanatory
     * @return	A CharSequence that is the content of the file/uri. Our string is probably implicitly casted
     * 	to a CharSequence.
     */
    public CharSequence getCharContent( boolean ignoreEncodingErrors ) throws IOException
    {
	return fileContent;
    }

}
