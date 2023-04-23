public class Percolation {
    static int size = 10;
    static int length = size * size;
    static boolean[] grid = new boolean[length];
    // UnionFind uf;
    

    public static void init(){
        // init all entries with false
        // UnionFind.init(length);
        
        for (int i = 0; i< grid.length; i++){
            grid[i] = false;
        }

        // String str = """
        //     *--*-*--*-
        //     *--*-*-*--
        //     *-*--**-**
        //     *----**--*
        //     **-**---**
        //     ****--**-*
        //     ---**---**
        //     --****-*--
        //     -*-*-**--*
        //     -********-
        // """;
        // int lastIdx = 0;
        // // boolean[] grid = new boolean[100];
        
        // for (int i=0; i < str.length(); i++){
        //     if (str.charAt(i) == '*'){
        //         grid[lastIdx] = true;
        //         lastIdx += 1;
        //     }
        //     else if (str.charAt(i) == '-'){
        //         grid[lastIdx] = false;
        //         lastIdx+=1;
        //     }
            
        // }
    }

    public static void print(boolean[] grid){
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
                // propagateUnion(j);
                return j;
            }
        }
        return -1;
    }
    static int nCalls = 0;

    static int allFalse(boolean[] arr){
        int nTrues = 0;
        for (int i = 0; i< arr.length; i++)
            if (arr[i] != false)
                nTrues += 1;

        return nTrues;
    }

    public static boolean isNaivePercolation(int n){
        boolean[] seen = new boolean[grid.length];
        initSeenArray(seen);
        nCalls = 0;

        if (grid[n] == false)
            return false;

        if (detectPath(seen, n, true)){
            initSeenArray(seen);
            if (detectPath(seen, n, false ))
                return true;
        }
        return false;
    }

    public static boolean detectPath(
        boolean[] seen,
        int n, 
        boolean up
    ){
        seen[n] = true;
        nCalls +=1;

        boolean down = !up;

        int upDirection = n - size;
        int downDirection = n + size;
        int leftDirection = n - 1;
        int rigthDirection = n + 1;

        boolean isValidLeft = leftDirection >= 0 && n % size != 0;
        boolean isValidRigth = rigthDirection < grid.length && rigthDirection % size != 0;
        boolean isValidUp = upDirection > 0;
        boolean isValidDown = downDirection < grid.length;

        boolean isUpperBound = upDirection < 0;
        boolean isLowerBound = downDirection >= length;

        if (isUpperBound && grid[n] == true && up)
            return true;
        
        if (isLowerBound && grid[n] == true && down)
            return true;

        if (isValidUp && grid[upDirection] == true && !seen[upDirection]){
            if (detectPath(seen, upDirection, up))
                return true;
        }
        
        if (isValidDown && grid[downDirection] == true && !seen[downDirection]){
            if (detectPath(seen, downDirection, up))
                return true;
        }

        if (isValidLeft && grid[leftDirection] == true && !seen[leftDirection]){
            if (detectPath(seen, leftDirection, up))
                return true;
        }

        if (isValidRigth && grid[rigthDirection] == true && !seen[rigthDirection]){
            if (detectPath(seen, rigthDirection, up))
                return true;
        }
        return false;
    }

    static void initSeenArray(boolean[] seen){
        for (int i = 0; i < seen.length; i++){
            seen[i] = false;
        }
    }

    public static boolean isPercolation(int n){
        return isNaivePercolation(n);
    }

    public static double percolation(){
        init();
        boolean percolates = false;
        double numBlackEntries = 0; 
        while (!percolates){

            randomShadow();
            numBlackEntries += 1;

            for (int firstRowElement = 0; firstRowElement < size; firstRowElement ++)
                if (grid[firstRowElement] == false)
                    continue;
                else if (isPercolation(firstRowElement)){
                    percolates = true;
                    break;
                }
        }
        return numBlackEntries/length;
    }

    public static double monteCarlo(int n){
        double[] results = new double[n];
        
        for (int i= 0; i < n; i++){
            results[i] = percolation();
        }

        return mean(results);
    }

    // public static void propagateUnion(int n){
    //     boolean colorN = grid[n];
        
    //     int upDirection = n - size;
    //     int downDirection = n + size;
    //     int leftDirection = n - 1;
    //     int rigthDirection = n + 1;

    //     boolean isValidLeft = leftDirection >= 0 && n % size != 0;
    //     boolean isValidRigth = rigthDirection < grid.length && rigthDirection % size != 0;
    //     boolean isValidUp = upDirection > 0;
    //     boolean isValidDown = downDirection < grid.length;

    //     if (isValidLeft && grid[leftDirection] == colorN)
    //         UnionFind.union(n, leftDirection);

    //     if (isValidRigth && grid[rigthDirection] == colorN)
    //         UnionFind.union(n, rigthDirection);

    //     if (isValidDown && grid[downDirection] == colorN)
    //         UnionFind.union(n, downDirection);

    //     if (isValidUp && grid[upDirection] == colorN)
    //         UnionFind.union(n, upDirection);

    // }

    // public static boolean isFastPercolation(int n){

    //     // grid[n1] = grid[n-1][0]
    //     // grid[nn] = grid[n-1][n-1]
    //     int n1 = size * (size - 1);
    //     int nn = (size * size) -1; 
    //     for (int i = n1; i <= nn; i++){
    //         if (grid[i] == true && (UnionFind.find(i) == UnionFind.find(n)))
    //             return true;
    //     }
    //     return false;
    // }

    

    public static void main(String[] args){

    }

    // utils
    static double sum(double[] array){
        double sum = 0;
        for (int i=0; i < array.length; i++)
            sum+= array[i];

        return sum;
    }

    static double mean(double[] array){
        return sum(array)/array.length;
    }
}
