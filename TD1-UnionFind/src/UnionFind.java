public class UnionFind {
    static int[] equiv;
    static int[] height;

    public static void init(int len) {
        equiv = new int[len + 2];
        height = new int [len + 2];
        for (int i = 0; i< equiv.length; i++){
            equiv[i] = i;
            height[i] = 1;
        }
    }

    public static int naiveFind(int x){
        return equiv[x];
    }

    public static int fastFind(int x){
        while (equiv[x] != x){
            x = equiv[x];
        }
        return x;
    }

    public static int logFind(int x){
        while (equiv[x] != x){
            equiv[x] = equiv[equiv[x]];
            x = equiv[x];
        }
        return x;
    }

    public static int naiveUnion(int x, int y){
        int equivX = equiv[x];
        for (int i = 0; i < equiv.length; i++){
            if (equiv[i] == equivX)
                equiv[i] = equiv[y];
        }
        return equiv[y];
    }

    public static int fastUnion(int x, int y){
        int rootY = fastFind(y);
        int rootX = fastFind(x);

        x = rootX;
        y = rootY;

        equiv[x] = rootY;
        return rootY;
    }

    public static int logUnion(int x, int y){
        if (height[x] > height[y]){
            return fastUnion(y, x);
        }
        else if (height[y] > height[x]){
            return fastUnion(x, y);
        }
        else { 
            // height[x] == height[y]
            height[y] += 1;
            return fastUnion(x, y);
        } 

    }

    public static int find(int x){
        // return naiveFind(x);
        // return fastFind(x); 
        return logFind(x);
    }

    public static int union(int x, int y){
        // return naiveUnion(x, y);
        // return fastUnion(x, y);
        return logUnion(x, y);
    }

    public static void main(String[] args){
        init(10);
        union(0, 3);
        union(8, 4);
        union(0, 5);
        union(5, 8);
        union(9, 3);

        System.out.println("find(0): " + find(0));
        System.out.println("find(3): " + find(3));
    }
}
