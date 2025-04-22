import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}


public class Main {
    public static boolean repeatedSubstringPattern(String s) {
        for(int i = 1; i < s.length(); i++) {
            if (isrepeted(s, i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isrepeted(String s, int k) {
        if (s.length() % k != 0) {
            return false;
        }
        int len = s.length() / k;
        String word = s.substring(0, k);
        for (int i = 0; i < len; i++) {
            String cur = s.substring(i * k, i * k + k);
            if (!cur.equals(word)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        int[] nums = {1, 1, 1, 1, 1, 1, 1, 1};
        ListNode head = new ListNode(1);
        ListNode one = new ListNode(2);
        ListNode two = new ListNode(3);
        ListNode three = new ListNode(4);
        head.next = one;
        one.next = two;
        two.next = three;
        three.next = null;

        boolean res = repeatedSubstringPattern("abcabcabcabc");

//        boolean res = canConstruct("aa", "aab");

        System.out.println("wtf");

    }
}