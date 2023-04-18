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
        boolean[] seen = new boolean[grid.length];
        initSeenArray(seen);

        if (detectPath(seen, n, false))
            return true;
        return false;
    }

    static boolean detectPath(
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
        boolean isValidUp = upDirection > 0;
        boolean isValidDown = downDirection < grid.length;

        boolean isUpperBound = upDirection < size && upDirection >= 0;
        boolean isLowerBound = downDirection >= grid.length - size && downDirection < grid.length;

        if (isUpperBound && grid[upDirection] == true && up)
            return true;
        
        if (isLowerBound && grid[downDirection] == true && down)
            return true;

        if (isValidUp && grid[upDirection] == true && !seen[upDirection])
            if (detectPath(seen, upDirection, up))
                return true;
        
        if (isValidDown && grid[downDirection] == true && !seen[downDirection])
            if (detectPath(seen, downDirection, up))
                return true;

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


    public static void main(String[] args){
        init();
        boolean percolates = false; 
        while (!percolates){
            
            print();
            
            randomShadow();
            for (int firstRowElement = 0; firstRowElement < size; firstRowElement ++)
                if (grid[firstRowElement] == false)
                    continue;
                else if (isNaivePercolation(firstRowElement)){
                    percolates = true;
                    break;
                }
        }
        print();
    }
}
