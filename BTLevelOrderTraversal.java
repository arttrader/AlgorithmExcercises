/* *****************************************************************************
 *  Name:              J Hirota
 *  Coursera User ID:
 *  Last modified:     2022-3-28
 **************************************************************************** */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BTLevelOrderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    /*
    void traverse(TreeNode node, int level, Queue<TreeNode> q) {
        q.add(node);
        if (node.left != null) traverse(node.left, level+1, q);
        if (node.right != null) traverse(node.right, level+1, q);
    }
    */


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.add(root);
        while (!q.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < q.size(); i++) {
                TreeNode node = q.remove();
                list.add(node.val);
                if (node.left != null)
                    q.offer(node.left);
                if (node.right != null)
                    q.offer(node.right);
            }
            result.add(list);
        }
        return result;
    }

    public static void main(String[] args) {

    }
}
