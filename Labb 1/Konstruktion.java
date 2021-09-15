import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

/*
    Rakin Ali CINTE19, KTH
    Adeel Hussain CINTE19, KTH
    Påbörjad 2021-09-11
*/

public class Konstruktion 
{
    /**
     * __________ psuedo koden_______________
     * 
     * Variabel W = null FreqW = 0
     * 
     * [Loop] Ladda in nästa ordet från temp L, om ordet != W, ersätt variabel W,
     * FreqW = 1 lägg till W i lista I läs in följande byteposition (pos till ordet
     * W) och spara --> variabel IndexPos Spara IndexPos i Fil P Hämta byteposition
     * för IndexPos i Fil P Spara IndexPos från Fil P för ordet W i Fil I
     *
     * medans nästa ord == W läs in följande byteposition och spara i P. FreqW++
     * 
     * Spara FreqW till Fil I
     * 
     * [Starta om loopen]
     * 
     */

    // Här testar vi vår kod
    public static void konstruktor() throws IOException 
    {

        // textReader läser filen
        BufferedReader textReader = new BufferedReader(new FileReader("TestDoc.txt"));

        // Create files I and P index files
        File i_index = new File("TEST_I_index");
        FileOutputStream p_index= new FileOutputStream("TEST_BIN_P_Index");

        // Creating writers to I and P - index-files 
        FileWriter i_Writer = new FileWriter(i_index);
        BufferedWriter iWriter = new BufferedWriter(i_Writer);   
        DataOutputStream pWriter = new DataOutputStream(new BufferedOutputStream(p_index));

        // Temp variable that stores the word previous and then compares it to see if the word is unique or not.
        String word = "";

        // Counts the frequencies of the word
        int wordfreq = 0;

        // The byte position of P, where in P we are writing to 
        long p_position = 0L;
        int byteCounter = 0;

        // Space in byte
        String space = " ";

        // New line in bytes
        String newLine = "\n";
        
        // Where we store the word read from textReader
        String data;
        
        // The byteIndex in Index L to Integer
        int binIndex;

        String encoding = "ISO-8859-1";

        try 
        {
            // While textDoc adds a new word and that word isn't null
            while ((data = textReader.readLine()) != null) 
            {
                //Converts from ISO-8859-1 to byteArray
               // byte [] dataToBytes = data.getBytes(encoding);
                
                //Converts byteArray to ISO-8859-1?
               // String decodeUTF = new String(dataToBytes, encoding);
                String[] text_line = data.split(" ");
                
                // Index_Word stores the word in String. ByteIndex stores the byteIndex in Korpus file
                String index_Word = text_line[0];
                String byteIndex = text_line[1];

                // byteIndex converted from String to Int and stored in binIndex
                binIndex = Integer.parseInt(byteIndex);

                // If the word is Distinkt
                if (!(word.equals(index_Word))) 
                {
                    // and not the first word of the text, we have to add wordfreq to the previous word then continue with the algo
                    if (!(word.equals(""))) 
                    {
                        // We add space
                        iWriter.write(space);

                        // Add frequency of the previous word (temp changed for readability)
                        String s_Wordfreq = "" + wordfreq;
      
                        // Writing to I index
                        iWriter.write(s_Wordfreq);

                        // Reset the wordfreq
                        wordfreq = 0;

                        // New line
                        iWriter.write(newLine);
                    }

                    // Updates the word variable, it checks the word behind.
                    word = index_Word;

                    // Here we write the word to file I
                    iWriter.write(index_Word);

                    // We add space
                    iWriter.write(space);

                    // Sets p_position to bytecounter before writing it out
                    p_position = byteCounter;

                    // Now we add the position
                    String sPos = "" + p_position;
                    iWriter.write(sPos);

                    // We write the ByteIndex in L to P
                    pWriter.writeByte(binIndex);
                    byteCounter++;

                    // Increments the frequency of the word
                    wordfreq++;

                }
                // If the word is not unique
                else 
                {
                    // We write the ByteIndex in L to P
                    pWriter.writeByte(binIndex);
                    byteCounter++;

                    // Counts the frequency of the word
                    wordfreq++;
                }
            }
            // Special case. WordFreq on the last word will not be written. We can fix this by adding it manually
            if ((data = textReader.readLine()) == null) 
            {
                // We add space
                iWriter.write(space);

                // Add frequency of the previous word
                String s_Wordfreq = "" + wordfreq;
                iWriter.write(s_Wordfreq);
            }
            // Close the writers 
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

    public static void main(String[] args) throws IOException 
    {
        konstruktor();
    }
}
