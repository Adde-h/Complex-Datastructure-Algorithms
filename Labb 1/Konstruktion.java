import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

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

     /*
        Psuedokod för A 
        ______________________
        Special 1 -> Allra första ordet
        Special 2 -> Samma ord
        Special 3 -> om ordet är 'aa' men vi kollar 'a'
        Special 4 ->  

        Skapa intArray hashTable med 27000 ngt platser
        Skapa a_word = ""
        Därefter sätt a_word (3 första orden) till första ordet i Index I (Första fallet blir a__)
        Hasha a_word och få ut hashvärdet -> hashval
        Spara plats för pekare från Index I till I_pos
        Läs nästa ord i Index I
        Ifall nästa ord i Index I, dess 3 bokstäver != a_word
            Skriv ut I_Pos till hashTable[hashval]
        annars
            forstätt med algorimten

     */


    public static void find(String findWord, int[] hashTable)
    {
        try
        {
            // Access the files
            RandomAccessFile raf_I = new RandomAccessFile("Index_I", "r");
            RandomAccessFile raf_P = new RandomAccessFile("Index_P", "r");

            // To find the appropiate byte Size
            int wordLength = findWord.length();

            //Pull the 3 letters out then get the hash value
            String seekLetters = findWord.substring(0,3);
            int hashVal = hasher(seekLetters);
           
           // Go to I and look for the word 
            byte[] byteArr = new byte[wordLength];
            long look_I = hashTable[hashVal];
            raf_I.seek(look_I);
            System.out.println("Position in I : " + look_I);
            
            /* Binary search delen 

            */

            // Word found! -> Reads the word and puts in the byte array. Print out later
            raf_I.readFully(byteArr, 0, wordLength);
            String wordFound = new String(byteArr,"ISO-8859-1");
            System.out.println("The word found: " + wordFound);

            // Extract information 
            raf_I.skipBytes(1);
            int posOfP = raf_I.read();
            System.out.println("PosFound: " + posOfP);
            
            raf_I.skipBytes(1);
            int freqOfWord = raf_I.read();
            System.out.println("FreqOfTheWord: " + freqOfWord);

            raf_P.seek(posOfP);
            int whereInL = raf_P.read();
            System.out.println("WhereInIndexL: " + whereInL);

            raf_I.close();
            raf_P.close();
            
            // print array function

        }
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    // Här körs konstruktionsprogrammet
    public static int[] konstruktor() throws IOException 
    {
        String encoding = "ISO-8859-1";

        // textReader läser filen med encodning ISO-8859-1
        BufferedReader textReader = new BufferedReader(new InputStreamReader(new FileInputStream("ISO-8859-1.txt"), encoding));
        
        // Create Index I, A and P files
        File a_index = new File("Index_A");
        FileOutputStream p_index= new FileOutputStream("Index_P");
        FileOutputStream i_index= new FileOutputStream("Index_I");

        // Creating writers to I, P and A - index-files 
        BufferedWriter aWriter = new BufferedWriter(new FileWriter(a_index));
        DataOutputStream pWriter = new DataOutputStream(new BufferedOutputStream(p_index));
        OutputStreamWriter iWriter = new OutputStreamWriter(new BufferedOutputStream(i_index), encoding);


        // Temp variable that stores the word previous and then compares it to see if the word is unique or not.
        String word = "";
        
        // Temp variable that stores 3 alfabet combo and then compares with the new distint word
        String a_word = "";
        String a_check_word = " ";
        
        // Counts the frequencies of the word
        int wordfreq = 0;

        // The byte position of P, where in P we are writing to 
        int p_position = 0;
        int p_byteCounter = 0;

        // The byte position of I, where in I we are writing to 
        int i_position = 0;
        int i_byteCounter = 0;

        // Space in byte
        String space = " ";

        // New line in bytes
        String newLine = "\n";
        
        // Where we store the word read from textReader
        String data;
        
        // The byteIndex in Index L to Integer
        int binIndex;

        int hashval;

        int maxHashVal = 30*900 + 30*30 + 30;
        int[] hashTable = new int[maxHashVal + 1];


        try 
        {
            // While textDoc adds a new word and that word isn't null
            while ((data = textReader.readLine()) != null) 
            {
                // Splits line into 2 words
                String[] text_line = data.split(" ");
                
                // Index_Word stores the word in String. ByteIndex stores the byteIndex in Korpus file
                String index_Word = text_line[0];
                String byteIndex = text_line[1];

                // byteIndex converted from String to Int and stored in binIndex
                binIndex = Integer.parseInt(byteIndex);

                // Takes first 3 letters of the word writing to I
                if(index_Word.length() < 3)
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append(index_Word);
                    int length = index_Word.length();
                    for(int i = 3 - length; i <= 3; i++)
                    {
                        sb.append(" ");
                    }

                    a_word = sb.toString();
                }
                else
                {
                    a_word = index_Word.substring(0, 3);
                }

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

                        // Update I_byte Counter
                        i_byteCounter += s_Wordfreq.length() + 1;
                    }
                    
                    // if a_word doesn't match check_word. add it to A_list array and file
                    if(!(a_check_word.equals(a_word)))
                        {

                            // Handles special case first time running the constructor
                            if(!(a_check_word.equals("")))
                            {
                                i_position = i_byteCounter;
                            }
                            
                            //Get HashValue and insert it to Hashtable
                            hashval = hasher(a_word);
                            hashTable[hashval] = i_position;
                        
                            StringBuilder build_a = new StringBuilder();



                            build_a.append(hashval);
                            build_a.append(space);
                            build_a.append(i_position);
                            build_a.append(newLine);
                            // Now writing to A
                            aWriter.write(build_a.toString());

                            //Update a_check_word
                            a_check_word = a_word;
                        }

                    // Updates the word variable, it checks the word behind.
                    word = index_Word;
                    
                    // Takes first 3 letters of the word writing to I
                    if(index_Word.length() < 3)
                    {
                        StringBuilder sb = new StringBuilder();
                        sb.append(index_Word);
                        int length = index_Word.length();
                        for(int i = 3 - length; i <= 3; i++)
                        {
                            sb.append(" ");
                        }
    
                        a_word = sb.toString();
                    }
                    else
                    {
                        a_word = index_Word.substring(0, 3);
                    }
                    
                    // Here we write the word to file I
                    iWriter.write(index_Word);

                    // We add space
                    iWriter.write(space);

                    // Sets p_position to p_bytecounter before writing it out
                    p_position = p_byteCounter;

                    // Now we add the position
                    //String sPos = "" + p_position;
                    //iWriter.write(sPos);
                    iWriter.write(p_position);

                    // We write the ByteIndex in L to P
                    pWriter.writeByte(binIndex);
                    p_byteCounter++;

                    // Increments the frequency of the word
                    wordfreq++;

                    //i_byteCounter += index_Word.length() + 1 + sPos.length() + 1;
                    i_byteCounter += index_Word.length() + 1 + p_position + 1;

                }
                // If the word is not unique
                else 
                {
                    // We write the ByteIndex in L to P
                    pWriter.writeByte(binIndex);
                    p_byteCounter++;

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
                //String s_Wordfreq = "" + wordfreq;
                //iWriter.write(s_Wordfreq);
                iWriter.write(wordfreq);

            }
            // Close the writers 
            iWriter.close();
            pWriter.close();
            aWriter.close();
        } 
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }    
        textReader.close();

        return hashTable;
        
    }

    public static int hasher(String tre_alfabetCombo)
    {
        char[] toHash;
        toHash = tre_alfabetCombo.toCharArray();
        int letter1 = toHash[0];
        int letter2 = toHash[1];
        int letter3 = toHash[2];

        //ASCII mellan a-z
        if (letter1 > 96 && letter1 < 123)
        {
            letter1 = letter1- 96;
        }
        else if (letter1 == 229) // Letter å
        {
            letter1 = 28;
        }
        else if (letter1 == 228) // Letter ä
        {
            letter1 = 29;
        }
        else if (letter1 == 246) // Letter ö
        {
            letter1 = 30;
        }
        else if (letter1 == 32) // Space
        {
            letter1 = 0;
        }

        //ASCII mellan a-z
        if (letter2 > 96 && letter2 < 123)
        {
            letter2 = letter2- 96;
        }
        else if (letter2 == 229) // Letter å
        {
            letter2 = 28;
        }
        else if (letter2 == 228) // Letter ä
        {
            letter2 = 29;
        }
        else if (letter2 == 246) // Letter ö
        {
            letter2 = 30;
        }
        else if (letter2 == 32) // Space
        {
            letter2 = 0;
        }

        //ASCII mellan a-z
        if (letter3 > 96 && letter3 < 123)
        {
            letter3 = letter3- 96;
        }
        else if (letter3 == 229) // Letter å
        {
            letter3 = 28;
        }
        else if (letter3 == 228) // Letter ä
        {
            letter3 = 29;
        }
        else if (letter3 == 246) // Letter ö
        {
            letter3 = 30;
        }
        else if (letter3 == 32) // Space
        {
            letter3 = 0;
        }

        int hashval = (letter1 * 900) + (letter2 * 30) + letter3;

        return hashval;
    }

    public static void main(String[] args) throws IOException 
    {
        int[] hashTable =  konstruktor();    
        String findWord = "özgur";
        find(findWord, hashTable);
    }
}
