import java.io.*;
import java.util.Scanner;
import java.io.BufferedWriter;

import java.nio.charset.StandardCharsets;

/*
  Rakin Ali CINTE19, KTH
  Adeel Hussain CINTE19, KTH
  Påbörjad 2021-09-11
*/



public class Konstruktion2
{
  public static int hasher(String tre_alfabetCombo)
  {

    int lengthOfWord = tre_alfabetCombo.length();
    String a_word;

    if(lengthOfWord < 3)
    {
      StringBuilder sb = new StringBuilder();
      sb.append(tre_alfabetCombo);
      for(int i = 3 - lengthOfWord; i <= 3; i++)
      {
        sb.append(" ");
      }

      a_word = sb.toString();
    }
    else
    {
      a_word = tre_alfabetCombo.substring(0, 3);
    }


    char[] toHash;
    toHash = a_word.toCharArray();
    int letter1 = toHash[0];
    int letter2 = toHash[1];
    int letter3 = toHash[2];

    for (int i = 0; i < toHash.length; i++) 
    {
      if((int)toHash[i] == 229)
      {
        //Write more effective decode of letters program
      }
    }
    
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
    if (letter1 > 96 && letter1 < 123)
    {
      letter1 = letter1 - 96;
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


  public static void konstruktor() throws IOException 
  {

    String encoding = "ISO-8859-1";
    int maxHashVal = 30*900 + 30*30 + 30;

    // Create Index I, A and P files and be able to write to them
    File a_index = new File("Index_A");
    FileOutputStream p_index = new FileOutputStream("Index_P");
    File i_index_file = new File("Index_I");
    
    //DataOutputStream aWriter = new DataOutputStream(new BufferedOutputStream(a_index));
    RandomAccessFile aWriter = new RandomAccessFile(a_index, "rw");
    DataOutputStream pWriter = new DataOutputStream(new BufferedOutputStream(p_index));
    BufferedWriter iWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(i_index_file), StandardCharsets.ISO_8859_1));
    aWriter.setLength(maxHashVal * 4); // maxHashVal * size of Integer in bytes

    // Reads the raw text-file
    String fileToRead = "RawStart.txt";
    BufferedReader textReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToRead), "ISO-8859-1"));

    //Local variables used in the construction file
    String[] text_line = textReader.readLine().split("");
    String wordPrev = text_line[0], byteIndex, currentWord = "";
    int freq = 1, p_position = 0, p_IntCounter = 0, i_position = 0, i_rowCounter=0, binIndex = Integer.parseInt(text_line[1]);
    byte[] wordInBytes;
    
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
        String toWrite = wordPrev + " " + p_position + "" + freq + "\n";
        iWriter.write(toWrite);
        i_rowCounter += toWrite.length();
        
        // Write to Index A if hashval is distict from previous
        if(hasher(wordPrev) != hasher(currentWord))
        {
          aWriter.seek(hasher(wordPrev));
          aWriter.writeInt(i_position);
          for (int i = hasher(wordPrev); i < hasher(currentWord); i+=4) 
          {
            aWriter.seek(i);
            aWriter.writeInt(i_rowCounter); //i_pos??

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

  }

  public static void main(String[] args) throws IOException
  {
    konstruktor();

  }
  

  
}
