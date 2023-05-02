public class WordList {
    Node content;
    WordList(){
        content = null;
    }

    WordList(String str, Node next){
        this.content = new Node(str, next);
    }

    WordList(String[] t){
        if (t.length == 0)
            return;

        this.content = new Node(t[0], null);

        if (t.length == 1)
            return;

        for (int i = 1; i < t.length; i++ ){
            addLast(t[i]);
        }
    }

    int length(){
        int len = 0;
        for (Node current = this.content; current != null; current = current.next){
            len += 1;
        }
        return len;
    } 

    public String toString(){
        String listStr = "[";
        for (Node current = this.content; current != null; current = current.next){
            if (current == this.content)
                listStr += current.head;
            else               
                listStr += ", " + current.head;

        }
        listStr += "]";
        return listStr;
    }

    // add w at the first position
    void addFirst(String w){
        this.content = new Node(w, this.content);
    }

    // add w at the last position
    void addLast(String w){
        if (this.content == null ) {
            this.content = new Node(w, null);
            return;
        }
        Node lastElement = this.content;
        while (lastElement.next != null)
            lastElement = lastElement.next;
        
        lastElement.next = new Node(w, null);
    }

    // remove first element and returns it
    String removeFirst(){
        if (this.content == null)
            return null;
        String firstEl = this.content.head;
        this.content = this.content.next;

        return firstEl;
    }

    String removeLast(){
        if (this.content == null)
            return null;
        
        Node antepenultimateNode = this.content;
        if (antepenultimateNode.next == null){
            Node lastNode = antepenultimateNode;
            this.content = this.content.next;
            return lastNode.head;
        }

        while (antepenultimateNode.next.next != null)
            antepenultimateNode = antepenultimateNode.next;
        
        Node lastNode = antepenultimateNode.next;
        antepenultimateNode.next = null;

        return lastNode.head;
    }

    String[] toArray(){
        String[] arr = new String[this.length()]; 
        int i = 0;
        for (Node currentNode = this.content; currentNode != null; currentNode = currentNode.next){
            arr[i] = currentNode.head;
            i ++;
        }

        return arr;
    }

    void insert(String s){
        if (this.content == null){
            this.content = new Node(s, null);
            return;
        }

        boolean isInserted = false;
        
        if (s.compareTo(this.content.head) <=0 ){
            this.addFirst(s);
            return;
        }
        
        for (Node current = this.content; current.next != null; current = current.next){
            if (s.compareTo(current.next.head) <= 0){
                Node temp = current.next;
                current.next = new Node(s, temp);
                isInserted = true;

                break;
            }
        }

        if (!isInserted)
            this.addLast(s);
    }

    void insertionSort(){
        if (this.content == null )
            return;
        Node temp = this.content;
        this.content = new Node(temp.head, null);

        for (Node currentNode = temp.next; currentNode != null; currentNode = currentNode.next ){
            if (currentNode.head != null)
                this.insert(currentNode.head);
        }
    }

    static WordList foobar = new WordList(
        "foo",
        new Node("bar", new Node("baz", null))
    );

    public static void main (String[] args){
        
        foobar.addFirst("first");
        foobar.addLast("last");

        // System.out.println("string version of list: " + foobar.toString());
        // System.out.println("length of list: " + foobar.length());

        // System.out.println("removing first element of list: " + foobar.removeFirst());
        // System.out.println("removing last element of list: " + foobar.removeLast());

        // foobar.removeLast();
        // foobar.removeLast();
        // foobar.removeLast();
        // foobar.removeFirst();

        foobar.insertionSort();
        System.out.println(foobar.toString());

    }
}
