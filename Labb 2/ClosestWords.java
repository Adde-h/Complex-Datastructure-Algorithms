
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

public class ClosestWords 
{
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

  int[][] matrix = new int[40][40];
  String wordPrev = "";

  void fillFirst(int[][] matrix, int w1len, int w2len)
  {
    
    for (int i = 0; i <= w1len; i++) 
    {
      matrix[i][0] = i;
    }

    for (int j = 0; j <= w2len; j++) 
    {
      matrix[0][j] = j;
    }
  }

  int partDistOptimized(String w1, String w2, int w1len, int w2len) 
  {
    // Compare the previous word with the new word from the wordlist
    // If the same letters, same matrice
    // If not the same letter at a certain spot, End the for -loop and matrice
    // starts there  

    int letterCounter = nrOfSameLetter(wordPrev, w2);
  
    // Base case --> just return the length of the other word
    if (w1len == 0)
      return w2len;

    if (w2len == 0)
      return w1len;

    // Fill the first row and column with w1 length. The length represent the chars
    fillFirst(matrix, w1len, w2len);

    // Special case -> The rows that werent completly filled from prev matrice
    for (int i = 1; i <= w1len; i++) 
    {
      for (int j = letterCounter + 1; j <= w2len; j++)
      {
        int replace = matrix[i-1][j-1] + (w1.charAt(i - 1) == w2.charAt(j - 1) ? 0 : 1);
        int delete = matrix[i][j-1] + 1;
        int insert = matrix[i-1][j] + 1;

        matrix[i][j] = min(replace, delete, insert);
      }
    }

    /*
     * PRINT MATRIX 
     * for (int index = 0; index < matrix.length; index++) { for (int
     * index2 = 0; index2 < matrix[index].length; index2++) {
     * System.out.print(matrix[index][index2] + " "); } System.out.println(); }
    
    */
    wordPrev = w2;
    return (matrix[w1len][w2len]);

  }

  int nrOfSameLetter(String w1, String w2)
  {
    int length = Math.min(w1.length(), w2.length());
    for (int i = 0; i < length; i++) 
    {
      if (wordPrev.charAt(i) != w2.charAt(i)) 
      {
        return i;
      } 
    }
    return 0;
  }

  // Returns the smallest integer of a parameter out of the three
  int min(int diag, int left, int top) 
  {
    return Math.min(Math.min(diag, left), top);
  }

  int partDist(String w1, String w2, int w1len, int w2len) {
    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;

    // When word1 is equally long as word2
    int res = partDist(w1, w2, w1len - 1, w2len - 1) + (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);

    // When word1 is longer than word2
    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;

    // When word2 is longer than word1
    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    return res;
  }

  int distance(String w1, String w2) 
  {
    return partDistOptimized(w1, w2, w1.length(), w2.length());
    // return partDist(w1, w2, w1.length(), w2.length());
  }

  // String "w" is the word to be spell-checked and compared to the list
  // "wordList"
  public ClosestWords(String w, List<String> wordList) {

    // Iterate through all the words in the wordlist och
    for (String s : wordList) 
    {
      // If the word we read from WordList is longer than the current closest distance, skip the word from WordList
      // (We already have a better "more likely" word that we wanted to have)
      if((Math.abs(s.length() - w.length()) > closestDistance) && closestDistance != -1)
      {
        continue;
      }
      int dist = distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      } else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
  /*
   * public static void main(String[] args) { //Main.main("large");
   * 
   * String word1 = "blada"; String word2 = "labd"; int w1len = word1.length();
   * int w2len = word2.length();
   * 
   * System.out.println(ClosestWords.partDistOptimized(word1, word2, w1len,
   * w2len));
   * 
   * }
   */
}
