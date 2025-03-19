/**
 * Scapegoat Tree class
 *
 * This class contains an implementation of a Scapegoat tree.
 */

public class SGTree {
    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        int weight = 1;
        public TreeNode left = null;
        public TreeNode right = null;
        public TreeNode parent = null;

        TreeNode(int k) {
            key = k;
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return countNodes(node.left) + countNodes(node.right) + 1;
    }

    /**
     * Builds an array of nodes in the subtree rooted at node
     *
     * @param node the root of the subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node) {
        if (node == null) {
            return new TreeNode[0];
        }

        int count = countNodes(node);
        TreeNode[] res = new TreeNode[count];

        class InOrderTraversal {
            int ind;

            InOrderTraversal() {
                this.ind = 0;
            }

            void traverse(TreeNode curr) {
                if (curr == null) {
                    return;
                }
                traverse(curr.left);
                res[ind++] = curr;
                traverse(curr.right);
            }
        }

        new InOrderTraversal().traverse(node);

        return res;
    }

    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        int length = nodeList.length;
        if (length == 0) {
            return null;
        }
        
        class TreeBuilder {
            TreeNode makeSubTree(int start, int end) {
                if (start == end) {
                    nodeList[start].right = null;
                    nodeList[start].left = null;
                    nodeList[start].weight = 1;
                    return nodeList[start];
                } else if (start >= end) {
                    return null;
                }

                int mid = start + (end-start) / 2;
                
                TreeNode left = makeSubTree(start, mid-1);
                TreeNode right = makeSubTree(mid+1, end);

                nodeList[mid].weight = 1;
                nodeList[mid].left = left;
                if (left != null) {
                    left.parent = nodeList[mid];
                    nodeList[mid].weight += left.weight;
                }
                nodeList[mid].right = right;
                if (right != null) {
                    right.parent = nodeList[mid];
                    nodeList[mid].weight += right.weight;
                }

                return nodeList[mid];
            }
        }

        TreeNode newRoot = new TreeBuilder().makeSubTree(0, length-1);

        return newRoot;
    }

    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        if (node == null) {
            return true;
        }
        int leftCount = 0;
        if (node.left != null) {
            leftCount = node.left.weight;
        }
        int rightCount = 0;
        if (node.right != null) {
            rightCount = node.right.weight;
        }
        int threshold = 2 * node.weight / 3;
        
        if (leftCount > threshold || rightCount > threshold) {
            return false;
        }

        return true;
    }

    /**
    * Rebuilds the subtree rooted at node
    * 
    * @param node the root of the subtree to rebuild
    */
    public void rebuild(TreeNode node) {
        // Error checking: cannot rebuild null tree
        if (node == null) {
            return;
        }

        TreeNode p = node.parent;
        TreeNode[] nodeList = enumerateNodes(node);
        TreeNode newRoot = buildTree(nodeList);

        if (p == null) {
            root = newRoot;
        } else if (node == p.left) {
            p.left = newRoot;
        } else {
            p.right = newRoot;
        } 

        newRoot.parent = p;
    }

    /**
    * Inserts a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        insert(key, root);
    } 
 
    // Helper method to insert a key into the tree 
    private void insert(int key, TreeNode node) { 
        node.weight++;
        if (key <= node.key) { 
            if (node.left == null) { 
                node.left = new TreeNode(key); 
                node.left.parent = node; 
            } else { 
                insert(key, node.left); 
            } 
        } else { 
            if (node.right == null) { 
                node.right = new TreeNode(key); 
                node.right.parent = node; 
            } else { 
                insert(key, node.right); 
            } 
        }
        if (!checkBalance(node)) {
            rebuild(node);
        }
    } 

    // Helper method to see wth I am doin
    private static void printTree(TreeNode[] list) {
        System.out.println("");
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i].key);
            System.out.print(" ");
        }
        System.out.println("");
        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i].weight);
            System.out.print(" ");
        }
    }
 
    // Simple main function for debugging purposes 
    public static void main(String[] args) { 
        SGTree tree = new SGTree(); 
        for (int i = 0; i < 5; i++) { 
            tree.insert(i); 
        } 
        TreeNode[] list = tree.enumerateNodes(tree.root);
        printTree(list);
        TreeNode newRoot = tree.buildTree(list);
        TreeNode[] list2 = tree.enumerateNodes(newRoot);
        printTree(list2);
    } 
} 
 