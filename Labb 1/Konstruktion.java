import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.RandomAccessFile; // Import the RandomAccessFile

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

    public static void seeker(RandomAccessFile indexFile, long pos) 
    {

    }

    // Här testar vi vår kod
    public static void tester() throws IOException 
    {

        // textReader läser filen
        BufferedReader textReader = new BufferedReader(new FileReader("TestRawIndex.txt"));

        // These are the files where we create the I and P files.
       // BufferedWriter 


        // final byte [] isoByte = String.getByte("ISO-8859-1")

        // isoByte.length

        File i_index = new File("TEST_I_index");
        File p_index = new File("TEST_P_index");

        /* RADERA INTE -> VIKTIGA KOMMENTARER
        RandomAccessFile iWriter = new RandomAccessFile("Test_RAF_I_Index", "rw");
        RandomAccessFile pWriter = new RandomAccessFile("Test_RAF_P_Index", "rw");
        */

        BufferedWriter iWriter = null;
        FileWriter i_Writer = new FileWriter(i_index);
        iWriter = new BufferedWriter(i_Writer);

        BufferedOutputStream pWriter =  new BufferedOutputStream(new FileOutputStream(p_index));

        // Temp variable that stores the word previous and then compares it to see if
        // the word is unique or not.
        String word = "";

        // Counts the frequencies of the word
        int wordfreq = 0;

        // The byte position of P, where in P we are writing to 
        long p_position = 0L;

        // The byte position of I, where in I_index we are writing to 
        long i_position = 0L;

        try 
        {
            String encoding = "ISO-8859-1";
            // String encoding = "UTF-8";

            // Space in byte
            String space = " ";
            byte[] spaceB = space.getBytes(encoding);
            int space_toBytesLength = spaceB.length;

            // New line in bytes
            String newLine = "\n";
            int newLineLength = newLine.getBytes().length;
            byte[] newLineB = newLine.getBytes(encoding);
            String data;

            // While textDoc has a new word and that word isn't null
            while ((data = textReader.readLine()) != null) 
            {

                String[] text_line = data.split(" ");
                String index_Word;
                String byteIndex;

                // The word from the text
                index_Word = text_line[0];

                // The byte index from the text
                byteIndex = text_line[1];

                // If the word is Distinkt
                if (!(word.equals(index_Word))) 
                {
                    // and not the first word of the text
                    if (!(word.equals(""))) 
                    {
                        // We add space
                        iWriter.write(space);
                        i_position += space_toBytesLength;

                        // Add frequency of the previous word (temp changed for readability)
                        String s_Wordfreq = "" + wordfreq;
      
                        //Writing to I index
                        int s_WordfreqLength = s_Wordfreq.getBytes().length;
                        i_position += s_WordfreqLength;
                        iWriter.write(s_Wordfreq);

                        // Reset the wordfreq
                        wordfreq = 0;

                        // New line
                        iWriter.write(newLine);
                        i_position += newLineLength;
                    }

                    // Encodes the ByteIndex from the text into a byte
                    byte[] bytesByteIndex = byteIndex.getBytes(encoding);

                    // p_position is the byteIndex on file P, basically where we're currently
                    // writing to in file P.
                    int byteIndexLength = bytesByteIndex.length;
                    //long p_position = pWriter.getFilePointer();
                    p_position += byteIndexLength;

                    // Updates the word variable, it checks the word behind.
                    word = index_Word;

                    // Encodes the word from the text into a byte
                   // byte[] bytesWord = index_Word.getBytes(encoding);

                    // Here we write the word to file I
                    i_position += index_Word.getBytes().length;
                    iWriter.write(index_Word);

                    // We add space
                    iWriter.write(space);
                    i_position += space_toBytesLength;

                    // Now we add the position (temp changed for readability)
                    String sPos = "" + p_position;
                    i_position += sPos.getBytes().length;
                    iWriter.write(sPos);

                    // Encodes the ByteIndex from the text into a byte
                   // byte[] bytesByteIndex = byteIndex.getBytes(encoding);
                    
                    // We write the ByteIndex in L to P
                    p_position += bytesByteIndex.length;
                    pWriter.write(bytesByteIndex);

                    // Here we create a new line
                    pWriter.write(newLineB);

                    // Counts the frequency of the word
                    wordfreq++;

                    // pos++
                }
                // If the word is not unique
                else 
                {
                    // Encodes the ByteIndex from the text into a byte
                   // byte[] bytesByteIndex = byteIndex.getBytes(encoding);

                    // We write the ByteIndex in L to P
                    pWriter.write(byteIndex.getBytes(encoding));

                    // Here we create a new line
                    pWriter.write(newLineB);

                    // Counts the frequency of the word
                    wordfreq++;
                }
            }
            // Special case. WordFreq on the last word will not be written. We can fix this by adding it manually
            if ((data = textReader.readLine()) == null) 
            {
                // We add space
                iWriter.write(space);
                i_position += space_toBytesLength;

                // Add frequency of the previous word (temp changed for readability)
                String s_Wordfreq = "" + wordfreq;
                i_position += s_Wordfreq.getBytes().length;
                iWriter.write(s_Wordfreq);
            }


            iWriter.close();
            pWriter.close();

        } catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        textReader.close();
    }

    public static void main(String[] args) throws IOException {

        tester();

    }
}
