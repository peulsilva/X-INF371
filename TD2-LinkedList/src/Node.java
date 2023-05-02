public class Node {
    String head;
    Node next;
    
    Node(String head, Node next){
        this.head = head;
        this.next = next;
    }

    static int lengthRec(Node list){
        if (list == null)
            return 0;;
        
        return lengthRec(list.next) + 1;
    }

    static int length(Node list){
        int len = 0;
        for (Node current = list; current != null; current = current.next){
            len += 1;
        }
        return len;
    }

    static String makeString(Node list){
        String listStr = "[";
        for (Node current = list; current != null; current = current.next){
            if (current == list)
                listStr += current.head;
            else               
                listStr += ", " + current.head;

        }
        listStr += "]";
        return listStr;
    }

    static void addLast(String s, Node list){
        Node lastElement = list;
        while (lastElement.next != null)
            lastElement = lastElement.next;
        
        lastElement.next = new Node(s, null);
    }

    static Node copy(Node the){
        Node copyList = new Node(the.head, null);
        for (Node current = the.next; current != null; current = current.next){
            addLast(current.head, copyList);
        }
        return copyList;
    }

    // inserts in the sorted position
    static Node insert(String s, Node list){
        boolean isInserted = false;

        for (Node current = list; current != null; current = current.next){
            if (s.compareTo(current.head) <= 0){
                // current.head -> s
                // current.next -> current
                Node currentCopy = copy(current);
                current.head = s;
                current.next = currentCopy;
                isInserted = true;

                break;
            }
        }

        if (!isInserted)
            addLast(s, list);
        return list;
    }

    static Node insertionSort(Node list){
        Node sortedList = new Node(list.head, null);
        for (Node currentNode = list.next; currentNode != null; currentNode = currentNode.next ){
            insert(currentNode.head, sortedList);
        }
        return sortedList;
    }

    // main
    public static void main(String[] args){
        Node foobar = new Node("foo", null);
        
        addLast("qux", foobar);
        addLast("foo", foobar);
        addLast("bar", foobar);
        addLast("baz", foobar);
        addLast("null", foobar);

        Node sortedFoobar = insertionSort(foobar);

        System.out.println(makeString(foobar));
        System.out.println(makeString(sortedFoobar));
    }
}
