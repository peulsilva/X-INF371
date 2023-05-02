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
        if (the == null)
            return null;
        Node copyList = new Node(the.head, null);
        Node last = copyList;
        for (Node current = the.next; current != null; current = current.next){
            last.next = new Node(current.head, null);
            last = last.next;
            
        }

        return copyList;
    }

    // inserts in the sorted position
    static Node insert(String s, Node list){
        boolean isInserted = false;
        
        for (Node current = list; current != null; current = current.next){
            if (current != null && current.head != null && s.compareTo(current.head) <= 0){
                // current.head -> s
                // current.next -> current

                Node currentCopy = copy(current);
                current.head = s;
                current.next = currentCopy;
                isInserted = true;

                break;
            }
        }

        if (list != null && !isInserted)
            addLast(s, list);
        
        if (list == null)
            return new Node(s, null);
        return list;
    }

    static Node insertionSort(Node list){
        
        Node sortedList = new Node(list.head, null);
        for (Node currentNode = list.next; currentNode != null; currentNode = currentNode.next ){
            if (currentNode.head != null)
                insert(currentNode.head, sortedList);
        }
        return sortedList;
    }

    static Node mergeSort(Node l1, Node l2){
        Node mergedList = null;
        while (l1 != null && l2 != null){
            if (l1.head.compareTo(l2.head) <= 0){
                mergedList = new Node(l1.head, mergedList);
                l1 = l1.next;
            }
            else{ // l1.head.compareTo(l2.head) > 0
                mergedList = new Node(l2.head, mergedList);
                l2 = l2.next;
            }
        }

        if (l1 == null){
            while (l2 != null){
                mergedList = new Node(l2.head, mergedList);
                l2 = l2.next;
            }
        }

        else if (l2 == null){
            while (l1 != null){
                mergedList = new Node(l1.head, mergedList);
                l1 = l1.next; 
            }
        }

        return mergedList;
    }

    // main
    public static void main(String[] args){
        Node foobar = new Node("foo", null);
        // Node foobar = null;
        // copy(foobar);
        addLast("qux", foobar);
        addLast("foo", foobar);
        addLast("bar", foobar);
        addLast("baz", foobar);

        Node sortedFoobar = insertionSort(foobar);

        // System.out.println(makeString(foobar));
        // System.out.println(makeString(copy(sortedFoobar)));

        System.out.println(makeString(mergeSort(sortedFoobar, sortedFoobar)));
    }
}
