package Interview;


/*
    rotate link list by k steps
*/

class Node {
    int data;
    Node next;

    Node(int data, Node next){
        this.data = data;
        this.next = next;
    }
}

public class RotateLinkListByKSteps {


    public static void main(String[] args) {
        Node five = new Node(5, null);
        Node four = new Node(4, five);
        Node three = new Node(3, four);
        Node two = new Node(2, three);

        Node head = new Node(1, two);

        int k = 10;

        Node temp = head;
        while(temp!=null){
            System.out.println(temp.data+", ");
            temp = temp.next;
        }


        RotateLinkListByKSteps main = new RotateLinkListByKSteps();
        Node afterRotate = main.rotateByKSteps(head, k);


        System.out.println("************ OUTPUT**********");
        while(afterRotate!=null){
            System.out.println(afterRotate.data+", ");
            afterRotate = afterRotate.next;
        }
        System.out.println("************ OUTPUT**********");

    }

    public Node rotateByKSteps(Node head, int k){
        int n = countNodes(head);
        if(k%n == 0)
            return head;
        int n_fromEnd = k%n;
        int n_fromBegining = n-n_fromEnd;
        System.out.println("n: "+n);
        System.out.println("n_fromEnd: "+n_fromEnd);
        System.out.println("n_fromBegining: "+n_fromBegining);
        Node prevOfNewHead = getKthNode(head, n_fromBegining);

        Node newHead = prevOfNewHead.next;
        System.out.println("newHead: "+newHead.data);
        Node temp = newHead;
        prevOfNewHead.next = null;

        Node lastNode = null;
        while(temp!=null){
            lastNode = temp;
            temp = temp.next;
        }
        System.out.println("lastNode: "+lastNode.data);
        System.out.println("head: "+head.data);
        lastNode.next = head;
        return newHead;
    }

    private Node getKthNode(Node head, int k){
        Node temp = head;
        int count = 0;
        while(temp!=null){
            count++;
            if(count == k){
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    private int countNodes(Node head){
        Node temp = head;
        int count = 0;
        while(temp!=null){
            count++;
            temp = temp.next;
        }
        return count;
    }
}
