public class UnionFind {
    static int[] equiv;

    public static void init(int len) {
        equiv = new int[len];
        for (int i = 0; i< equiv.length; i++)
            equiv[i] = i;
    }

    public static int naiveFind(int x){
        return equiv[x];
    }

    public static int naiveUnion(int x, int y){
        int equivX = equiv[x];
        for (int i = 0; i < equiv.length; i++){
            if (equiv[i] == equivX)
                equiv[i] = equiv[y];
        }
        return equiv[y];
    }

    public static int find(int x){
        return naiveFind(x);
    }

    public static int union(int x, int y){
        return naiveUnion(x, y);
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
