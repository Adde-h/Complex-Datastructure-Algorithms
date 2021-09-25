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

  public static void konstruktor() throws IOException 
  {

    String encoding = "ISO-8859-1";

    // Create Index I, A and P files and be able to write to them
    FileOutputStream a_index = new FileOutputStream("Index_A");
    FileOutputStream p_index = new FileOutputStream("Index_P");
    File i_index_file = new File("Index_I");
    
    DataOutputStream aWriter = new DataOutputStream(new BufferedOutputStream(a_index));
    DataOutputStream pWriter = new DataOutputStream(new BufferedOutputStream(p_index));
    BufferedWriter iWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(i_index_file), StandardCharsets.ISO_8859_1));

    // Reads the raw text-file
    String fileToRead = "RawStart.txt";
    BufferedReader textReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToRead), "ISO-8859-1"));

    //Local variables used in the construction file
    String [] text_line = textReader.readLine().split("");
    String wordPrev = text_line[0], byteIndex, currentWord = "";
    int freq = 1, p_position = 0, p_IntCounter = 0, i_position = 0, binIndex = Integer.parseInt(text_line[1]);
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
        i_position += toWrite.length();
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
