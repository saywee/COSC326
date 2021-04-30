
public class Cubes {

    public static void main(String[] args) {
        // To simulate a 2x2x2 cube using 1 and 0 to simulate two different
        // colors
        int[][][] cube = new int[2][2][2];
        // Triple nested for loop to initialize them all to zero.
        for (int i = 0; i < cube.length; i++) {
            for (int j = 0; j < cube[i].length; j++) {
                for (int k = 0; k < cube[i][j].length; k++) {
                    cube[i][j][k] = 0;
                    //System.out.println(i + " " + j +" " + k);
                }
            }
        }
        //Example code: running to find permutations on cube, given 0 of the other color.
        //permutations(cube, 0);

        int csize = 8;
        int totalperms = 0;
        for(int i = 0; i <= csize; i++) {
            System.out.print(csize + "!" + "/" + (csize - i) + "!*" + i + "!" + " = ");
            System.out.println(factorial(csize)/(factorial(csize-i) * factorial(i)));
        }

    }
    
    public static int factorial(int cap) {
        int start = 1;
        for(int i = 1; i <= cap; i++) {
            start = start * i;
        }
        
        return start;
    }

    public static void permutations(int[][][] cube, int change) {
        System.out.println();
        //This should add in however many of the secondary cube there are.
        for(int i = 0; i < change;i++) {
            cube[i/4][i/2][i%2] = 1;
        }
        
        //Generate all permutations. Check if any place with 1 has to traverse more than once to generate permutation.
        //This should print out the cube at its current form.
        for (int i = 0; i < cube.length; i++) {
            for (int j = 0; j < cube[i].length; j++) {
                for (int k = 0; k < cube[i][j].length; k++) {
                    System.out.print(cube[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        
        
    }
    
    public static void rotateClockwise(int[][][] cube) {
        int[][][] transpose = new int[cube.length][cube.length][cube.length];
        for(int i = 0; i < cube.length; i++) {
            for(int j = 0; j < cube[i].length; j++) {
                
            }
        }
        
    }

}
