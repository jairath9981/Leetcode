package nextlevel.InterviewPrep2025;


class KAlternateReverseNode{
    int data;
    KAlternateReverseNode next;

    KAlternateReverseNode(int data){
        this.data = data;
        next = null;
    }
}


public class KAlternateReverse {

    public static void main(String[] args) {
        int []arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};

        KAlternateReverseNode head = null, tail = null;
        for(int i = 0; i<arr.length; i++){
            if(head == null) {
                head = new KAlternateReverseNode(arr[i]);
                tail = head;
            }else{
                tail.next =  new KAlternateReverseNode(arr[i]);
                tail = tail.next;
            }
        }

        int k = 3;
        KAlternateReverse kAlternateReverse = new KAlternateReverse();
        KAlternateReverseNode newOutputHead = kAlternateReverse.KAlternateReverseMechanism(head, k);

        System.out.println("******Final Output********");
        kAlternateReverse.print(newOutputHead);
    }

    private KAlternateReverseNode KAlternateReverseMechanism(KAlternateReverseNode head, int k) {

        KAlternateReverseNode curr = head;
        KAlternateReverseNode outputHead = null;
        KAlternateReverseNode reverseGroupHead = null, reverseGroupTail = null, reverseGroupTailNext = null;
        KAlternateReverseNode nonReverseGroupTail = null;

        boolean toggle = true;
        int count = 0;

        while(curr!=null){

            if(toggle){
                count++;
                if(count == 1){
                    reverseGroupHead = curr;
                }else{
                    reverseGroupTail = curr;
                    reverseGroupTailNext = curr.next;
                }

                if(count == k){
                    reverseGroupTail.next = null;
                    KAlternateReverseNode reverseHead = this.reverse(reverseGroupHead);
                    reverseGroupHead.next = reverseGroupTailNext;

                    if(outputHead == null){
                        outputHead = reverseHead;
                    }else{
                        nonReverseGroupTail.next = reverseHead;
                    }

                    curr = reverseGroupHead;

                    count = 0;
                    toggle = false;
                }
            }else{
                count++;
                if(count == k){
                    nonReverseGroupTail = curr;

                    count = 0;
                    toggle = true;
                }
            }
            curr = curr.next;
        }

        if(toggle && count>=2){
            KAlternateReverseNode reverseHead = this.reverse(reverseGroupHead);

            if(outputHead == null){
                outputHead = reverseHead;
            }
            else if(nonReverseGroupTail!=null){
                nonReverseGroupTail.next = reverseHead;
            }
        }

        return outputHead;
    }

    private KAlternateReverseNode reverse(KAlternateReverseNode head){
        if(head == null || head.next == null)
            return head;

        KAlternateReverseNode curr = head;
        KAlternateReverseNode prev = null, next = curr.next;

        while(curr!=null){
            next =  curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }
        return prev;
    }


    private void print(KAlternateReverseNode hHead) {
        KAlternateReverseNode temp = hHead;
        while(temp!=null){
            System.out.print(temp.data+", ");
            temp = temp.next;
        }
    }
}
