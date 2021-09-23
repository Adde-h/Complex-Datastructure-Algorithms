
/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;
import java.utils.Arrays;

public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;
  public static int loop = 1;

  public static int partDist(String w1, String w2, int w1len, int w2len) {
    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;

    int res = partDist(w1, w2, w1len - 1, w2len - 1) + (w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);

    // System.out.println("Loop " + loop + " res: " + res);
    // loop++;
    // System.out.println("w2 charat: " + w2.charAt(w1len - 1));

    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;

    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    return res;
  }

  public static int distance(String w1, String w2) {
    return partDist(w1, w2, w1.length(), w2.length());
  }

  ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
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

  public static void main(String[] args) {

    // Vill ha Word 1 <- Vi har Word 2
    String word1 = "bl";
    String word2 = "la";

    int[][] m = new int[word1.length() + 1][word2.length() + 1];

    for (int i = 0; i < m.length; i++) {
      for (int j = 0; j < m[i].length; j++) {
        m[i][j] = partDist(word1, word2, i, j);
      }
    }

    for (int index = 0; index < m.length; index++) {
      for (int index2 = 0; index2 < m[index].length; index2++) {
        System.out.print(m[index][index2] + " ");
      }
      System.out.println();
    }
  }

}
