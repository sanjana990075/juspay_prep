package tress;
import java.util.*;

public class NormalTree {
    
    private static class Node {
        int value;
        Node left;
        Node right;
        
        public Node(int value) {
            this.value = value;
        }
    }

    private Node root;

    public void populate(Scanner scanner) {
        System.out.println("Enter the root node:");
        int value = scanner.nextInt();
        root = new Node(value);
        populate(scanner, root);
    }

    private void populate(Scanner scanner, Node node) {
        System.out.println("Do you want to enter left of " + node.value + "? (true/false)");
        boolean left = scanner.nextBoolean();
        if (left) {
            System.out.println("Enter the value for the left of node " + node.value + ":");
            int value = scanner.nextInt();
            node.left = new Node(value);
            populate(scanner, node.left);
        }

        System.out.println("Do you want to enter right of " + node.value + "? (true/false)");
        boolean right = scanner.nextBoolean();
        if (right) {
            System.out.println("Enter the value for the right of node " + node.value + ":");
            int value = scanner.nextInt();
            node.right = new Node(value);
            populate(scanner, node.right);
        }
    }

    // For testing: Preorder traversal
    public void display() {
        display(root);
    }

    private void display(Node node) {
        if (node == null) return;
        System.out.print(node.value + " ");
        display(node.left);
        display(node.right);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NormalTree tree = new NormalTree();
        tree.populate(scanner);
        System.out.println("traversal");
        tree.display();
    }
}
