import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException; // Import the IOException class to handle errors
import java.io.RandomAccessFile; // Import the RandomAccessFile
/*
    Rakin Ali CINTE19, KTH
    Adeel Hussain CINTE19, KTH
    Påbörjad 2021-09-11
*/

public class Konstruktion {
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
        BufferedReader textReader = new BufferedReader(new FileReader("Nontxt"));

        // These are the files where we create the I and P files.
        RandomAccessFile iWriter = new RandomAccessFile("Test_RAI_Index", "rw");
        RandomAccessFile pWriter = new RandomAccessFile("Test_RAF_P_Index", "rw");

        // Temp variable that stores the word previous and then compares it to see if
        // the word is unique or not.
        String word = "";

        // Counts the frequencies of the word
        int wordfreq = 0;

        try 
        {
            // String encoding = "ISO-8859-1";
            String encoding = "UTF-8";

            // Space in byte
            String space = " ";
            byte[] spaceB = space.getBytes(encoding);

            // New line in bytes
            String newLine = "\n";
            byte[] newLineB = newLine.getBytes(encoding);
            String data;
            // While textDoc has a new word
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
                        iWriter.write(spaceB);

                        // Add frequency of the previous word (temp changed for readability)
                        String s_Wordfreq = "" + wordfreq;
                        iWriter.writeBytes(s_Wordfreq);

                        // Reset the wordfreq
                        wordfreq = 0;

                        // New line
                        iWriter.write(newLineB);
                    }

                    // p_position is the byteIndex on file P, basically where we're currently
                    // writing to in file P.
                    long p_position = pWriter.getFilePointer();

                    // Updates the word variable, it checks the word behind.
                    word = index_Word;

                    // Encodes the word from the text into a byte
                    byte[] bytesWord = index_Word.getBytes(encoding);

                    // Here we write the word to file I
                    iWriter.write(bytesWord);

                    // We add space
                    iWriter.write(spaceB);

                    // Now we add the position (temp changed for readability)
                    String sPos = "" + p_position;
                    iWriter.writeBytes(sPos);

                    // Encodes the ByteIndex from the text into a byte
                    byte[] bytesByteIndex = byteIndex.getBytes(encoding);

                    // We write the ByteIndex in L to P
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
                    byte[] bytesByteIndex = byteIndex.getBytes(encoding);

                    // We write the ByteIndex in L to P
                    pWriter.write(bytesByteIndex);

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
                iWriter.write(spaceB);

                // Add frequency of the previous word (temp changed for readability)
                String s_Wordfreq = "" + wordfreq;
                iWriter.writeBytes(s_Wordfreq);
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
