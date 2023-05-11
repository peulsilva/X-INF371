import java.util.Arrays;

public class Bovary {

    static HMap buildTable(String[] files, int n){
        HMap hashTable = new HMap();

        for (int i = 0; i< files.length; i++){
            String filename = files[i];
            
            if (filename == null)
                throw new NullPointerException("filename " + filename + " not found. (i = "+ i + ")");

            WordReader wr = new WordReader(filename);
            Prefix prefix = new Prefix(n);


            for (String word = wr.read(); word != null; word = wr.read()){
                
                hashTable.add(prefix, word);
                prefix = prefix.addShift(word);
            }
            // word == null (end of chapter)
            hashTable.add(prefix, Prefix.end);
        }

        return hashTable;
    }

    static void generate(HMap t, int n){
        String text = "";
        Prefix keyPrefix = new Prefix(n);
        boolean isTextEnd = false;

        while (!isTextEnd){
            WordList lastWord = t.find(keyPrefix);

            

            if (lastWord == null)
                throw new NullPointerException("Could not find prefix " + Arrays.toString(keyPrefix.t) + " in hmap");


            String nextWord = sortWord(lastWord);

            boolean isParagraph = nextWord.compareTo(Prefix.par) == 0 ;
            boolean isEnd = nextWord.compareTo(Prefix.end) == 0;

            if (isParagraph){
                text+= "\n";
                keyPrefix = keyPrefix.addShift(nextWord);
            }
            else if (isEnd)
                isTextEnd = true;
            else{
                text+= nextWord + " ";
                keyPrefix = keyPrefix.addShift(nextWord);
            }
        }
        System.out.println(text);
    }

    static String sortWord(WordList wl){
        String[] wordArray = wl.toArray();
        int sortedIdx = (int) (Math.random() * wordArray.length);

        return wordArray[sortedIdx];
    }

    public static void main (String[] args){
        String[] files = new String[35];
        for (int i = 0; i < 35 ; i++){
            if (i + 1 < 10)
                files[i] = String.format("bovary/0%d.txt", i+1);
            else
            files[i] = String.format("bovary/%d.txt", i+1);
        }
            
        
        HMap hMap = buildTable(files, 3);
        System.out.println("Generated hash map with " + hMap.nbEntries + " entries");
        // hMap.print();
        generate(hMap, 3);
    }

}
