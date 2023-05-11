public class Prefix {
    String[] t;
    final static String start="<START>", end="<END>", par = "<PAR>";

    Prefix(int n){
        String[] prefix = new String[n];
        for (int i = 0; i<n; i++) 
            prefix[i] = start;

        this.t = prefix;
    }

    Prefix(String[] strArray){
        this.t = strArray;
    }

    static boolean eq(Prefix p1, Prefix p2){
        int minLength = Math.min(p1.t.length, p2.t.length);
        if (p1.t.length != p2.t.length )
            return false;

        for (int i = 0; i < minLength; i++){
            if (!p1.t[i].equals(p2.t[i]))
                return false;
        }
        
        return true;
    }

    Prefix addShift(String w){
        String[] newString = new String[this.t.length];
        for (int i = 1; i < this.t.length; i++){
            newString[i-1] = this.t[i];
        }
        newString[this.t.length - 1] = w;
        return new Prefix(newString);
    }

    public int hashCode(){
        int h=0;
        for (int i=0; i< this.t.length; i++)
            h = 37*h + this.t[i].hashCode();
        return h;
    }

    int hashCode(int n){
        return Math.floorMod(this.hashCode(), n);
    }
}
