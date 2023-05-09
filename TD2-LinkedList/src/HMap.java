import java.util.Arrays;

public class HMap {
    EntryList[] t;
    int nbEntries = 0;

    HMap(int n){
        this.t = new EntryList[n];
    }

    HMap(){
        this.t = new EntryList[20];
    }

    WordList find(Prefix key){
        int hashIdx = key.hashCode(this.t.length);
        if (this.t[hashIdx] == null)
            return null;

        WordList found = new WordList();

        for (EntryList curr = this.t[hashIdx]; curr != null; curr = curr.next){
            found = new WordList(curr.head.value.toString(), found.content);
        } 
        return found; 
    }

    void addSimple(Prefix key, String w){
        int hashIdx = key.hashCode(this.t.length);
        if (this.t[hashIdx] != null){
            this.t[hashIdx] = new EntryList(
                new Entry(key, new WordList(w, null)), 
                this.t[hashIdx]
            );
        }
        else { // this.t[hashIdx] == null
            this.t[hashIdx] = new EntryList(
                new Entry(key, new WordList(w, null)), 
                null
            );

            this.nbEntries++;
        }
    }

    void print(){
        System.out.println("{");
        for (int i = 0; i < this.t.length; i++){
            if (this.t[i] == null)
                continue;

            for (EntryList curr = this.t[i]; curr != null; curr = curr.next){
                String str = "\t";
                str += Arrays.toString(curr.head.key.t)+ ": "+ curr.head.value.toString();
                System.out.println(str);
            }
            
        }
        System.out.println("}");
    }

    void rehash(int n){
        HMap rehashedMap = new HMap(n);
        for (int i = 0; i < this.t.length; i++){
            if (this.t[i] == null)
                continue;

            for (EntryList curr = this.t[i]; curr != null; curr = curr.next){
                rehashedMap.addSimple(
                    curr.head.key, 
                    curr.head.value.toString()
                );
            }
        }

        this.t = rehashedMap.t;
        this.nbEntries = rehashedMap.nbEntries;
    }

    void add(Prefix key, String w ){
        if (this.nbEntries > 0.75 * this.t.length)
            rehash(2*this.t.length);
        
        addSimple(key, w);
    }

    public static void main(String[] args){
        HMap dict = new HMap();
        
        // dict.print();
        dict.addSimple(new Prefix(new String[] {"hello"}), "world");
        dict.addSimple(new Prefix(new String[] {"hello"}), "france");
        dict.addSimple(new Prefix(new String[]{"I", "am", "a"}), "student");
        // dict.print();
        System.out.println(
            dict
            .find(new Prefix(new String[] {"hello"}))
            .toString()
        );
    }
}
