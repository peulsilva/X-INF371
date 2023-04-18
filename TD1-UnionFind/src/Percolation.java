public class Percolation {
    static int size = 10;
    static int length = size * size;
    static boolean[] grid = new boolean[length];

    public static void init(){
        // init all entries with false
        for (int i = 0; i< grid.length; i++){
            grid[i] = false;
        }
    }

    public static void print(){
        String gridStr = "";
        for (int i = 0; i < grid.length; i++){
            boolean isBlack = grid[i];
            char entry = isBlack ? '*' : '-';
            
            gridStr += entry;
            if (shouldBreakLine(i, size))
                gridStr += '\n';
        }
        System.out.println(gridStr);
    }

    static boolean shouldBreakLine(
        int position,
        int gridSize
    ){
        if ((position + 1) % gridSize == 0 )
            return true;

        return false;
    }

    public static int randomShadow(){
        for (int i = grid.length - 1 ; i >=0; i--){
            int j = (int) (Math.random() * i); 

            if (grid[j] == false){
                grid[j] = true;
                return j;
            }
        }
        return -1;
    }

    public static boolean isNaivePercolation(int n){
        int up = n - size;
        int down = n + size;
        int left = n - 1;
        int rigth = n + 1;

        boolean[] seen = new boolean[grid.length];

        if (up >= 0){

        }

        return false;
    }

    static boolean detectPath(
        boolean[] seen,
        int n, 
        boolean up
    ){
        return false;
    }

    static void initSeenArray(boolean[] seen){
        for (int i = 0; i < seen.length; i++){
            seen[i] = false;
        }
    }


    public static void main(String[] args){
        init();
        randomShadow();
        print();
    }
}
