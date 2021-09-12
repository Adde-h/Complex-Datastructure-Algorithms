import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
    Rakin Ali Cinte19, KTH
    Adeel Hussain Cinte 19, KTH
    Påbörjad 2021-09-12
*/
public class Konstruktion 
{

    // https://stackabuse.com/java-save-write-string-into-a-file/

    // This is the I index, where we store every distinct word
    File I_index = new File("I_index");

    // This is the file where all the pointers shall be stored in
    File P_index = new File("P_index");

    // This object reads the sorted file Temp L 
    Scanner textReader; // = new File(tempL filen)
    
    
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
     * @throws FileNotFoundException
     */

    // Här testar vi vår kod 
    public static void main(String[] args) throws FileNotFoundException 
    {
        // 
        File testFile = new File("TestDoc");
        Scanner textReader = new Scanner(testFile);
        while(textReader.hasNextLine())
        {
            String data = textReader.nextLine();
            System.out.println(data);
        }
    }
    
}
