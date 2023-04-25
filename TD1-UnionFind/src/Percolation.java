public class Percolation {
    static int size = 10;
    static int length = size * size;
    static boolean[] grid = new boolean[length];
    

    public static void init(){
        // init all entries with false
        UnionFind.init(length);
        
        for (int i = 0; i< grid.length; i++){
            grid[i] = false;
        }
        
        // String str = """
        // String str = 
        //     "*----**-*-" + 
        //     "**-**--***" +
        //     "-*----**--" +
        //     "****---***" +
        //     "--*--**---" +
        //     "*-*-*--*-*" +
        //     "--*--**-*-" + 
        //     "-***-**-**" + 
        //     "-*-*--**-*" +
        //     "*--***-*--"; 
        // // """;
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
                propagateUnion(j);
                return j;
            }
        }
        return -1;
    }
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

        if (grid[n] == false)
            return false;

        if (detectPath(seen, n, true)){
            initSeenArray(seen);
            if (detectPath(seen, n, false))
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

        boolean down = !up;

        int upDirection = n - size;
        int downDirection = n + size;
        int leftDirection = n - 1;
        int rigthDirection = n + 1;

        boolean isValidLeft = leftDirection >= 0 && n % size != 0;
        boolean isValidRigth = rigthDirection < grid.length && rigthDirection % size != 0;
        boolean isValidUp = upDirection >= 0;
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
        // return isNaivePercolation(n);
        return isFastPercolation(n);
    }

    public static double percolation(){
        init();
        boolean percolates = false;
        double numBlackEntries = 0; 
        while (!percolates){
            print();

            int n = randomShadow();
            numBlackEntries += 1;

            if (isLogPercolation())
                percolates = true;

        }
        print();
        return numBlackEntries/length;
    }

    public static double monteCarlo(int n){
        double[] results = new double[n];
        
        for (int i= 0; i < n; i++){
            results[i] = percolation();
        }

        return mean(results);
    }

    public static void propagateUnion(int n){
        boolean colorN = grid[n];
        
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

        if (isValidLeft && grid[leftDirection] == colorN)
            UnionFind.union(n, leftDirection);

        if (isValidRigth && grid[rigthDirection] == colorN)
            UnionFind.union(n, rigthDirection);

        if (isValidDown && grid[downDirection] == colorN)
            UnionFind.union(n, downDirection);

        if (isValidUp && grid[upDirection] == colorN)
            UnionFind.union(n, upDirection);

        if (isUpperBound)
            UnionFind.union(n, length );
        else if (isLowerBound)
            UnionFind.union(n, length + 1);

    }

    public static boolean isFastPercolation(int n){
        boolean isConnectedToFirstRow = false;
        boolean isConnectedToLastRow = false;

        for (int firstRowElement = 0; firstRowElement < size; firstRowElement ++){
            if (grid[n] == grid[firstRowElement] && UnionFind.find(firstRowElement) == UnionFind.find(n)){
                isConnectedToFirstRow = true;
                break;
            }
        }

        for (int lastRowElement = length - size; lastRowElement < length; lastRowElement ++){
            if (grid[n] == grid[lastRowElement] && UnionFind.find(lastRowElement) == UnionFind.find(n)){
                isConnectedToLastRow = true;
                break;
            }
        }

        return isConnectedToFirstRow && isConnectedToLastRow;
    }

    static boolean isLogPercolation(){
        return UnionFind.find(length) == UnionFind.find(length+1);
    }

    

    public static void main(String[] args){
        percolation();
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
