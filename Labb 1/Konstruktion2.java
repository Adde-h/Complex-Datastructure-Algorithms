import java.io.*;
import java.io.BufferedWriter;

import java.nio.charset.StandardCharsets;

/*
  Rakin Ali CINTE19, KTH
  Adeel Hussain CINTE19, KTH
  Påbörjad 2021-09-11
*/

public class Konstruktion2
{

  /*****************  To run on Shell Computer **********************/

    private static File index_P = new File("/var/tmp/Index_P");
    private static File i_index_file = new File("/var/tmp/Index_O");
    private static File a_index = new File("/var/tmp/Index_A");
    private static String fileToRead = "/var/tmp/rawindex.txt";
  
  /******************************************************************/

    /*****************  To run on Local Computer **********************/
   /* private static File a_index = new File("Index_A");
    private static String index_P = "Index_P"; 
    private static File i_index_file = new File("Index_I");
    private static String fileToRead = "rawindex.txt";
  */
  /******************************************************************/



  public static int hasher(String tre_alfabetCombo)
  {
    // Hantera fallet där ord längd mindre än 3 och större än 3
    int lengthOfWord = tre_alfabetCombo.length();
    String a_word;
    
    if(lengthOfWord > 3)
    {
      a_word = tre_alfabetCombo.substring(0, 3);
    }
    else
    {
      a_word = tre_alfabetCombo;
    }

    char[] toHash;
    toHash = a_word.toCharArray();
    int hashval = 0;

    // Hash funktionen
    for (int i = 0; i < toHash.length; i++) 
    {
      if ((int)toHash[i] == 228) // å in Ascii (actually 229)
      {
        hashval += 27 * Math.pow(30, 2 - i);
      }
      else if ((int)toHash[i] == 229) // ä in Ascii (actually 228)
      {
        hashval += 28 * Math.pow(30, 2 - i);
      }
      else if ((int)toHash[i] == 246) // ö in Ascii
      {
        hashval += 29 * Math.pow(30, 2 - i);
      }
      else if( (int)toHash[i] > 96 && (int)toHash[i] < 123) //Other words in the alfabet a-z
      {
        hashval += ((int) toHash[i] - 96) * Math.pow(30, 2 - i);
      }
    }
    // Multiplied by 4 because of writeInt (each position takes 4 bytes) --> 
    return (hashval - 900) * 4;
  }


  public static void konstruktor() throws IOException 
  {

    int maxHashVal = 30*900 + 30*30 + 30;

    // Create Index I, A and P files and be able to write to them
    FileOutputStream p_index = new FileOutputStream(index_P);
    
    //DataOutputStream aWriter = new DataOutputStream(new BufferedOutputStream(a_index));
    RandomAccessFile aWriter = new RandomAccessFile(a_index, "rw");
    DataOutputStream pWriter = new DataOutputStream(new BufferedOutputStream(p_index));
    BufferedWriter iWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(i_index_file), StandardCharsets.ISO_8859_1));
    aWriter.setLength(maxHashVal * 4); // maxHashVal * size of Integer in bytes

    // Reads the raw text-file
    BufferedReader textReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToRead), "ISO-8859-1"));

    //Local variables used in the construction file
    String[] text_line = textReader.readLine().split(" ");
    String wordPrev = text_line[0], currentWord = "";
    int freq = 1, p_position = 0, p_IntCounter = 0, i_position = 0, i_rowCounter = 0, binIndex = Integer.parseInt(text_line[1]);
    
    while(textReader.ready())
    { 
      // Extract the word and the pointer Index from file
      pWriter.writeInt(binIndex);
      p_IntCounter += 4;

      text_line = textReader.readLine().split(" ");
      currentWord = text_line[0];
      binIndex = Integer.parseInt(text_line[1]);

      //If the word is distinct --> add the previous word with the P position + freq
      if(!currentWord.equals(wordPrev))
      {
        String toWrite = wordPrev + " " + p_position + " " + freq + "\n";
        iWriter.write(toWrite);
        i_rowCounter += toWrite.length();
        
        // Write to Index A if hashval is distict from previous
        if(hasher(wordPrev) != hasher(currentWord))
        {
          // HÄR STANNADE VI AVVVVVV
          aWriter.seek(hasher(wordPrev));
          aWriter.writeInt(i_position);
          
          //Fills out non used hashcells to point to next hashcell
          for (int i = hasher(wordPrev) + 4; i < hasher(currentWord); i += 4) 
          {
            aWriter.seek(i);
            aWriter.writeInt(i_rowCounter);
          }
          i_position = i_rowCounter;
        }

        freq = 0;
        wordPrev = currentWord;
        p_position = p_IntCounter;
      }
      
      // If the word is not distinct --> Just increase the frequncy counter
      else
      {
        freq++;
      }
    }
    // Edgee case -> Last word. Add in manually 
    if(!textReader.ready())
    {
      freq++;
      pWriter.writeInt(binIndex);        
      String toWrite = wordPrev + " " + p_position + " " + freq + "\n";
      iWriter.write(toWrite);
      
      aWriter.seek(hasher(wordPrev));
      aWriter.writeInt(i_position);
        
      //Fills out non used hashcells to point to next hashcell
      for (int i = hasher(wordPrev) + 4; i < hasher(currentWord); i += 4) 
      {
        aWriter.seek(i);
        aWriter.writeInt(i_rowCounter);
      }
    }    

    aWriter.close();
    pWriter.close();
    iWriter.close();
    textReader.close();

  }

  public static void main(String[] args) throws IOException
  {
    konstruktor();
  }
}
