import java.io.*;
import java.util.Scanner;

public class KonkordansAR 
{

  /*****************  To run on Shell Computer **********************/
  
    private static File index_P = new File("/var/tmp/Index_P");

  
  /******************************************************************/


  /*****************  To run on Local Computer **********************/
    //private static File index_P = new File("Index_P");

  /******************************************************************/


  public static int hasher(String tre_alfabetCombo)
  {
    // Hantera fallet där ord längd mindre än 3 och större än 3
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
    int hashval = 0;

    // Hash funktionen nu
    for (int i = 0; i < toHash.length; i++) 
    {
      if ((int)toHash[i] == 229) // å in Ascii
      {
        hashval += 27 * Math.pow(30, 2 - i);
      }
      else if ((int)toHash[i] == 228) // ä in Ascii
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

  /* From Pseudocode in Lecture 3*/
  public static String[] check_i (String wordLook) throws IOException
  {
    // Access the files 
    RandomAccessFile aReader = new RandomAccessFile("Index_A", "r");
    RandomAccessFile iReader = new RandomAccessFile("Index_I", "r");
    
    // Find the word in A index to know where to look in I Index
    aReader.seek(hasher(wordLook));
    int i = aReader.readInt();
    aReader.seek(hasher(wordLook) + 4);
    int j = aReader.readInt();

    int middle = 0;

    //Word, PositionInP, Frequency --> Binary search index I to find the exact word
    String[] fromIndexI = new String[3];
    while(i - j > 1000 && j > i)
    {
      middle = (i + j) / 2;
      iReader.seek(middle);
      fromIndexI = iReader.readLine().split(" ");
      int checkWord = fromIndexI[0].compareTo(wordLook);

      // Same word were looking for!
      if (checkWord == 0) 
      {
        break;         
      }
      // Word were looking for is less than we've found
      else if (checkWord < 0)
      {
        i = middle;
      }
      // Word were looking for is greater than we've found
      else
      {
        j = middle;
      }
    }

    iReader.seek(i);      
    
    while (true) 
    {
      fromIndexI = iReader.readLine().split(" ");
      // Ordet finns 
      if(fromIndexI[0].compareTo(wordLook) == 0)
      {
        iReader.close();
        aReader.close();
        return fromIndexI;
      }
      else if(fromIndexI[0].compareTo(wordLook) > 0)
      {
        System.out.println("Word is not in the file, Null returned");
        System.exit(1);
        iReader.close();
        aReader.close();
        return null;
      }
    }
  }

  private static String printer (String wordlook) throws IOException
  {
       
    /* Korpus on Shell computer */
    /*
     /afs/kth.se/misc/info/kurser/DD2350/adk21/labb1/korpus
    */
    
    // Finds the files
    RandomAccessFile korpus = new RandomAccessFile("/afs/kth.se/misc/info/kurser/DD2350/adk21/labb1/korpus", "r");
    RandomAccessFile pReader = new RandomAccessFile(index_P, "r");
    
    // Find the word in index I 
    String[] i_row = check_i(wordlook);
    int p_Pos = Integer.parseInt(i_row[1]);
    int lineFromI;
    int frequncy = Integer.parseInt(i_row[2]);
    int rowsToPrint;
    
    StringBuilder allText = new StringBuilder();

    // Choose how much we want to print
    if(frequncy > 25)
    {
      System.out.println("The word " + wordlook + " occurs " + frequncy + " times, how many rows do you want to see?");
      System.out.println("Type in amount of rows to print between [1-" + frequncy + "]");
      Scanner userInput = new Scanner(System.in);
      rowsToPrint = userInput.nextInt();
      userInput.close();
    }
    else
    {
      System.out.println("The word " + wordlook + " occurs " + frequncy + " times");
      rowsToPrint = frequncy;
    }
    
    // Now we extract information from P 
    pReader.seek(p_Pos);
    for (int i = 0; i < rowsToPrint; i++) 
    {
      lineFromI = pReader.readInt();
      if (lineFromI < 30) 
      {
        byte[] sentence = new byte[lineFromI + wordlook.length() + 30];
        korpus.seek(0);
        korpus.read(sentence);
        allText.append(new String(sentence, "ISO-8859-1").replaceAll("[\\n\\t]", " "));
        allText.append("\n");
      } 
      else 
      {
        byte[] sentence = new byte[30 + wordlook.length() + 30];
        korpus.seek(lineFromI - 30);
        korpus.read(sentence);
        allText.append(new String(sentence, "ISO-8859-1").replaceAll("[\\n\\t]", " "));
        allText.append("\n");
      }
    }
    korpus.close();
    pReader.close();
    return allText.toString();

  }

 

  public static void main(String[] args) throws IOException
  {

    /*
    String wordToFind = args[0].toLowerCase();
    
    if(wordToFind == null || (!(wordToFind.matches("[a-zå-ö]+$"))) || args.length > 1)
    {
      System.out.println("Typed either too many words, no words or using words not in the Swedish alphabet ");
      System.out.println("Thank you come again!");
      System.exit(0);
    }
    */

    // Looks for the word in the Index I and extracts information
    String wordLook = args[0].toLowerCase(); 
    System.out.println(printer(wordLook));

  }
}