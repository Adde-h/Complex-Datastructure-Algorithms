import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.RandomAccessFile; // Import the RandomAccessFile
/*
    Rakin Ali CINTE19, KTH
    Adeel Hussain CINTE19, KTH
    Påbörjad 2021-09-12
*/
public class Konstruktion 
{

    // https://stackabuse.com/java-save-write-string-into-a-file/

    //File i_file = new File("I_index");
    //File p_file = new File("P_index");


    // This is the I index, where we store every distinct word
    //RandomAccessFile i_index = new RandomAccessFile (i_file, "rw");

    // This is the file where all the pointers shall be stored in
    //RandomAccessFile p_index = new RandomAccessFile(p_file, "rw");

    // This object reads the sorted file Temp L then places them.
    Scanner textReader; // = new File(tempL filen)

    //This object place
    
    
    /** __________ psuedo koden_______________
     * 
     * Variabel W = null
     * FreqW = 0
     * 
     * [Loop]
     * Ladda in nästa ordet från temp L, 
     * om ordet != W, 
         ersätt variabel W, 
         FreqW = 1
         lägg till W i lista I
         läs in följande byteposition (pos till ordet W) och spara --> variabel IndexPos
         Spara IndexPos i Fil P
         Hämta byteposition för IndexPos i Fil P
         Spara IndexPos från Fil P för ordet W i Fil I 
     *
     * medans nästa ord == W
         läs in följande byteposition och spara i P.  
         FreqW++
     *  
     * Spara FreqW till Fil I
     * 
     * [Starta om loopen]
     * 
     */

    // Här testar vi vår kod 

    public static void tester() throws FileNotFoundException 
    {

        // Int Pos --> Kolla TestDoc.txt. Kombinationen är ord - siffra - ord - siffra 
        int pos = 0;

        // We make the testDoc as an object 
        File testFile = new File("TestDoc.txt");

        // Scanner textReader is uesed to read the TestDoc.txt
        Scanner textReader = new Scanner(testFile);

        // Theis are the files where we create the I and P files. 
        RandomAccessFile iWriter = new RandomAccessFile("Test_RAF_I_Index", "rw");
        RandomAccessFile pWriter = new RandomAccessFile("Test_RAF_P_Index", "rw");

        try
            {
                //While textDoc has a new word 
                while(textReader.hasNextLine())
                {
                    //_____________________#Just a bunch of shortcuts#_______________________
                    
                    //String encoding = "ISO-8859-1";
                    String encoding = "UTF-8";

                    //Space in byte
                    String space = " ";
                    byte[] spaceB = space.getBytes(encoding);
                    
                    //New line in bytes 
                    String newLine = "\n";
                    byte[] newLineB = newLine.getBytes(encoding);
                    
                    //______________________________Code starts below_________________________

                    //Gets the word from the text
                    String data = textReader.next();
               
                    // Encodes the word from the text into a byte 
                    byte[] bytes = data.getBytes(encoding);

                    // p_position is the byteIndex on file P, basically where we're currently writting to
                    long p_position = pWriter.getFilePointer();
                    pos++;
                    
                    // if P == 1, then it's a word, else it's a byteIndexPosition
                    if(pos % 2 == 1)
                    {                       
                        //Here we write the text in bytes
                        iWriter.write(bytes);
                        
                        // We add space 
                        iWriter.write(spaceB);
                         
                        // Now we add the position
                        iWriter.writeLong(p_position);

                        // New line 
                        iWriter.write(newLineB);

                    }
                    // If it is a byteIndex
                    else
                    {
                        // We write the ByteIndex in L to P 
                        pWriter.write(bytes);

                        // Here we create a new line 
                        pWriter.write(newLineB);

                    }
                }

                iWriter.close();
                pWriter.close();

            }
            catch (IOException e) 
            {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        
        textReader.close();
    }


    public static void main(String[] args) throws FileNotFoundException  
    {
       
       tester();
        
    }
}
