public class Test {

  public static void main(String[] args) {

    int letterCounter = 0;
    /*
     * int[][] matrix = { { 6,9 }, { 6,9 } }; int[][] matrixBig = { { 1, 2, 3 }, {
     * 2, 3, 4 }, { 5, 6, 9 } };
     */
    String wordPrev = "Helaaj";
    String w2 = "Helaaaaaaa";

    for (int i = 0; i < w2.length(); i++) {
      if (wordPrev.charAt(i) == w2.charAt(i)) {
        letterCounter++;
      } else {
        break;
      }
    }

    System.out.println(letterCounter);

    /*
     * for (int i = 0; i < letterCounter; i++) //Rader v { for (int j = 0; j <
     * letterCounter; j++) //Kolumner -> { matrix[i][j] = matrixBig[i][j]; } }
     * 
     * for (int index = 0; index < matrix.length; index++) { for (int index2 = 0;
     * index2 < matrix[index].length; index2++) {
     * System.out.print(matrix[index][index2] + " "); } System.out.println(); }
     */
  }

}