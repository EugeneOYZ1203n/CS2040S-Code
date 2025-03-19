import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private class TrieNode {
        // TODO: Create your TrieNode class here.
        public TrieNode[] presentChars = new TrieNode[62];
        public boolean isTerminal = false;
        public String fullString = "";
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        TrieNode curr = root;

        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            int index = convertCharToIndex(ch);
            if (curr.presentChars[index] == null) {
                curr.presentChars[index] = new TrieNode();
            }
            curr = curr.presentChars[index];
        }

        curr.isTerminal = true;
        curr.fullString = s;
    }

    int convertCharToIndex(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return ch - 'a' + 10;  
        } else if (ch >= 'A' && ch <= 'Z') {
            return ch - 'A' + 36; 
        } else if (ch >= '0' && ch <= '9') {
            return ch - '0'; 
        } else {
            return -1;
        }
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        TrieNode curr = root;

        for (int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            int index = convertCharToIndex(ch);
            if (curr.presentChars[index] == null) {
                return false;
            }
            curr = curr.presentChars[index];
        }

        return curr.isTerminal;
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        Stack<TrieNode> stack1 = new Stack<TrieNode>();
        Stack<Integer> stack2 = new Stack<Integer>();
        stack1.push(root);
        stack2.push(0);

        while (!stack1.empty() && results.size() < limit) {
            TrieNode curr = stack1.pop();
            int step = stack2.pop();

            if (s.length() > step) {
                char ch = s.charAt(step);
                if (ch == WILDCARD) {
                    for (int i = 61; i >= 0; i--) {
                        if (curr.presentChars[i] == null) {
                            continue;
                        }
                        stack1.push(curr.presentChars[i]);
                        stack2.push(step+1);
                    }
                } else {
                    int index = convertCharToIndex(ch);
                    if (curr.presentChars[index] == null) {
                        continue;
                    }
                    stack1.push(curr.presentChars[index]);
                    stack2.push(step+1);
                }
            } else {
                if (curr.isTerminal) {
                    results.add(curr.fullString);
                }
                for (int i = 61; i >= 0; i--) {
                    if (curr.presentChars[i] == null) {
                        continue;
                    }
                    stack1.push(curr.presentChars[i]);
                    stack2.push(step+1);
                }
            }
        }
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("abbde");
        t.insert("abcd");
        t.insert("abcdef");
        t.insert("abed");
        t.insert("abd");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("", 10);
        //String[] result2 = t.prefixSearch("pe.", 10);

        System.out.println(Arrays.toString(result1));
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
