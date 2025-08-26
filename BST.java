package tress;

class BST {

    public static class Node {
        private int value;
        private Node left;
        private Node right;
        private int height;

        public Node(int value) {
            this.value = value;
            this.height = 0; // height of a new node is 0
        }

        public int getValue() {
            return value;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    // Get height of a node
    public int height(Node node) {
        if (node == null) return -1;
        return node.height;
    }

    // Check if BST is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Public insert
    public void insert(int value) {
        root = insert(value, root);
    }

    // Private recursive insert
    private Node insert(int value, Node node) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.value) {
            node.left = insert(value, node.left);
        } else if (value > node.value) {
            node.right = insert(value, node.right);
        }

        // Update height
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        return node;
    }

    // Populate from unsorted array
    public void populate(int[] nums) {
        for (int num : nums) {
            insert(num);
        }
    }

    // Populate from sorted array to create balanced BST
    public void populatedSorted(int[] nums) {
        populatedSorted(nums, 0, nums.length - 1);
    }

    private void populatedSorted(int[] nums, int start, int end) {
        if (start > end) return;

        int mid = (start + end) / 2;
        insert(nums[mid]);
        populatedSorted(nums, start, mid - 1);
        populatedSorted(nums, mid + 1, end);
    }

    // Check if BST is balanced
    public boolean balanced() {
        return balanced(root);
    }

    private boolean balanced(Node node) {
        if (node == null) return true;

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return Math.abs(leftHeight - rightHeight) <= 1
                && balanced(node.left)
                && balanced(node.right);
    }

    // Display BST in readable format
    public void display() {
        display(root, "Root Node: ");
    }

    private void display(Node node, String details) {
        if (node == null) return;

        System.out.println(details + node.value);
        display(node.left, "Left child of " + node.value + " : ");
        display(node.right, "Right child of " + node.value + " : ");
    }

    // Main method
    public static void main(String[] args) {
        BST tree = new BST();

        int[] nums = { 5, 2, 7, 1, 4, 6, 9, 8, 3, 10 };
        tree.populate(nums); // You can use populate or populatedSorted for a balanced tree
        // tree.populatedSorted(nums); // Uncomment to insert as a balanced BST

        tree.display();

        System.out.println("Is balanced? " + tree.balanced());
    }
}
