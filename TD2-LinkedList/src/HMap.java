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

    public WordList find(Prefix key){
        int hashIdx = key.hashCode(this.t.length);
        if (this.t[hashIdx] == null)
            return null;

        for (EntryList curr = this.t[hashIdx]; curr != null; curr = curr.next){
            if (Prefix.eq(key, curr.head.key))
                return curr.head.value;
        }
        return null;
        
    }

    void addSimple(Prefix key, String w){
        boolean isInserted = false;
        int hashIdx = key.hashCode(this.t.length);

        if (this.t[hashIdx] != null){
            for (EntryList curr = this.t[hashIdx]; curr != null; curr = curr.next){
                if (Prefix.eq(key, curr.head.key)){
                    curr.head
                        .value
                        .addLast(w);

                    isInserted = true;
                }
            }

            if (!isInserted){
                EntryList lastElement = this.t[hashIdx];
                while (lastElement.next != null)
                    lastElement = lastElement.next;

                lastElement.next = new EntryList(
                    new Entry(key, new WordList(w, null)), 
                    null
                );

                this.nbEntries++;
               
            }
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
            String str = "\t" + i+ ": ";
            for (EntryList curr = this.t[i]; curr != null; curr = curr.next){
                str += "{" + Arrays.toString(curr.head.key.t)+ ": "+ curr.head.value.toString() + "}";
            }
            System.out.println(str);
            
        }
        System.out.println("}");
    }

    void rehash(int n){
        HMap rehashedMap = new HMap(n);
        for (int i = 0; i < this.t.length; i++){

            for (EntryList curr = this.t[i]; curr != null; curr = curr.next){
                for (Node word = curr.head.value.content; word != null; word = word.next)
                    rehashedMap.addSimple(
                        curr.head.key, 
                        word.head
                    );
            }
        }

        this.t = rehashedMap.t;
        this.nbEntries = rehashedMap.nbEntries;
    }

    void add(Prefix key, String w ){
        if (this.nbEntries >= 0.75 * this.t.length){
            rehash(2*this.t.length);
        }
        
        addSimple(key, w);
    }

    public static void main(String[] args){
        HMap dict = new HMap(1);
        
        // dict.print();
        // System.out.println(new Prefix(new String[] {"hello"}).hashCode(20));
        
        dict.add(new Prefix(new String[] {"hello"}), "world");
        dict.add(new Prefix(new String[] {"hello"}), "france");
        dict.add(new Prefix(new String[] {"Aa"}), "word1");
        dict.add(new Prefix(new String[] {"BB"}), "word2");
        dict.add(new Prefix(new String[] {"BB"}), "word2.1");
        dict.add(new Prefix(new String[]{"I", "am", "a"}), "student");
        dict.add(new Prefix(new String[] {"x"}), "word2");
        dict.add(new Prefix(new String[] {"y"}), "word2");
        dict.add(new Prefix(new String[] {"BB"}), "d");
        dict.add(new Prefix(new String[] {"f"}), "tr");
        dict.add(new Prefix(new String[] {"BB"}), "ewr");
        dict.print();

        // System.out.println(
        //     dict
        //     .find(new Prefix(new String[] {"BB"}))
        //     .toString()
        // );
    }
}
