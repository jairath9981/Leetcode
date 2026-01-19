package nextlevel.InterviewPrep2025;


class PalindromeLinkListNode{
    int data;
    PalindromeLinkListNode next;

    PalindromeLinkListNode(int data){
        this.data = data;
        this.next = null;
    }
}


public class PalindromeLinkList {

    public static void main(String[] args) {
        int []arr = {1, 1, 1, 1};

        PalindromeLinkListNode head = null, tail = null;
        for(int i = 0; i<arr.length; i++){
            if(head == null) {
                head = new PalindromeLinkListNode(arr[i]);
                tail = head;
            }else{
                tail.next =  new PalindromeLinkListNode(arr[i]);
                tail = tail.next;
            }
        }

        PalindromeLinkList palindromeLinkList = new PalindromeLinkList();
        boolean isPalindrome = palindromeLinkList.isLinkListPalindrome(head);
        System.out.println("Is Link List Palindrome: "+isPalindrome);
    }


    private boolean isLinkListPalindrome(PalindromeLinkListNode head) {

        if(head == null || head.next == null)
            return true;

        PalindromeLinkListNode slow = head, fast = head, prev = null;
        while(slow!=null && fast!=null && fast.next!=null && fast.next.next!=null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        PalindromeLinkListNode secondHalfHead = slow;

        if(fast.next == null){
            prev.next = null;
            secondHalfHead = slow.next;
        }

        else if(fast.next.next == null){
            secondHalfHead = slow.next;
            slow.next = null;
        }

        PalindromeLinkListNode secondHalfReverseHead = this.reverseLinkList(secondHalfHead);
        PalindromeLinkListNode curr = head;
        while(curr!=null && secondHalfReverseHead!=null){
            if(curr.data!=secondHalfReverseHead.data)
                return false;
            curr = curr.next;
            secondHalfReverseHead = secondHalfReverseHead.next;
        }
        return true;
    }

    private PalindromeLinkListNode reverseLinkList(PalindromeLinkListNode head){

        PalindromeLinkListNode curr = head;
        PalindromeLinkListNode prev = null, next = null;
        while(curr!=null){
            next = curr.next;
            curr.next = prev;

            prev = curr;
            curr = next;
        }
        return prev;
    }


    private void print(PalindromeLinkListNode head) {
        PalindromeLinkListNode curr = head;
        while(curr!=null){
            System.out.print(curr.data+", ");
            curr = curr.next;
        }
    }
}
