package tress;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class TestClass {

    static class TreeNode {
        String name;
        TreeNode parent;
        List<TreeNode> children = new ArrayList<>();
        boolean isLocked = false;
        int lockedBy = -1;
        int lockedDescendantCount = 0;

        TreeNode(String name) {
            this.name = name;
        }
    }

    static class LockingTree {
        Map<String, TreeNode> nodeMap = new HashMap<>();

        public void buildTree(List<String> names, int m) {
            for (String name : names) {
                nodeMap.put(name, new TreeNode(name));
            }

            int n = names.size();
            for (int i = 0; i < n; i++) {
                TreeNode parent = nodeMap.get(names.get(i));
                for (int j = 1; j <= m; j++) {
                    int childIndex = m * i + j;
                    if (childIndex < n) {
                        TreeNode child = nodeMap.get(names.get(childIndex));
                        child.parent = parent;
                        parent.children.add(child);
                    }
                }
            }
        }

        public boolean lock(String name, int uid) {
            TreeNode node = nodeMap.get(name);
            if (node.isLocked || node.lockedDescendantCount > 0) return false;

            TreeNode current = node.parent;
            while (current != null) {
                if (current.isLocked) return false;
                current = current.parent;
            }

            node.isLocked = true;
            node.lockedBy = uid;
            current = node.parent;
            while (current != null) {
                current.lockedDescendantCount++;
                current = current.parent;
            }
            return true;
        }

        public boolean unlock(String name, int uid) {
            TreeNode node = nodeMap.get(name);
            if (!node.isLocked || node.lockedBy != uid) return false;

            node.isLocked = false;
            node.lockedBy = -1;
            TreeNode current = node.parent;
            while (current != null) {
                current.lockedDescendantCount--;
                current = current.parent;
            }
            return true;
        }

        public boolean upgrade(String name, int uid) {
            TreeNode node = nodeMap.get(name);
            if (node.isLocked || node.lockedDescendantCount == 0) return false;
            if (!checkAllLockedBySameUser(node, uid)) return false;

            unlockAllDescendants(node);
            return lock(name, uid);
        }

        private boolean checkAllLockedBySameUser(TreeNode node, int uid) {
            if (node.isLocked && node.lockedBy != uid) return false;
            for (TreeNode child : node.children) {
                if (!checkAllLockedBySameUser(child, uid)) return false;
            }
            return true;
        }

        private void unlockAllDescendants(TreeNode node) {
            if (node.isLocked) {
                unlock(node.name, node.lockedBy);
            }
            for (TreeNode child : node.children) {
                unlockAllDescendants(child);
            }
        }
    }

    public static void main(String args[] ) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        int Q = Integer.parseInt(br.readLine());

        List<String> nodes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            nodes.add(br.readLine().trim());
        }

        LockingTree tree = new LockingTree();
        tree.buildTree(nodes, m);

        for (int i = 0; i < Q; i++) {
            String[] parts = br.readLine().split(" ");
            int op = Integer.parseInt(parts[0]);
            String name = parts[1];
            int uid = Integer.parseInt(parts[2]);

            boolean res = false;
            if (op == 1) res = tree.lock(name, uid);
            else if (op == 2) res = tree.unlock(name, uid);
            else if (op == 3) res = tree.upgrade(name, uid);

            System.out.println(res);
        }
    }
}
