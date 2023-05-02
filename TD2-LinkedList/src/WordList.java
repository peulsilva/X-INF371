public class WordList {
    Node content;
    WordList(){
        content = null;
    }

    WordList(String str, WordList next){
        this.content = new Node(str, null);
        if (next != null)
            this.content.next = next.content;
    }

    static WordList foobar = new WordList(
        "foo", 
        new WordList(
            "bar",
            new WordList(
                "baz",
                null
            )
        )    
    );

    public static void main (String[] args){
        System.out.println(Node.makeString(WordList.foobar.content.next));
    }
}
