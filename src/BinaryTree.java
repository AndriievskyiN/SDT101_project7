import java.util.Iterator;
import java.util.Stack;

public class BinaryTree<T> implements Iterable<T> {
    private Node<T> root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(T rootData) {
        this.root = new Node<>(rootData);
    }

    public BinaryTree(T rootData, BinaryTree<T> left, BinaryTree<T> right) {
        this.root = new Node<>(rootData);
        if (left != null) {
            this.root.left = left.root;
        }
        if (right != null) {
            this.root.right = right.root;
        }
    }

    private static class Node<E> {
        private E data;
        private Node<E> left;
        private Node<E> right;

        public Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // Declarative tree creation
    public static void main(String[] args) {
        BinaryTree<Integer> sampleTree = new BinaryTree<>(
                50,
                new BinaryTree<>(17,
                        new BinaryTree<>(12,
                                new BinaryTree<>(9, null, null),
                                new BinaryTree<>(14, null, null)
                        ),
                        new BinaryTree<>(23,
                                new BinaryTree<>(19, null, null),
                                null
                        )
                ),
                new BinaryTree<>(72,
                        new BinaryTree<>(54,
                                null,
                                new BinaryTree<>(67, null, null)
                        ),
                        new BinaryTree<>(76, null, null)
                )
        );
        sampleTree.printTree();
        System.out.println();
        sampleTree.prettifyPrintTree();
    }

    // Recursive traversals with Visitor pattern
    public interface Visitor<T> {
        void visit(T data);
    }

    public void inorderTraverse(Visitor<T> visitor) {
        inorderTraverse(root, visitor);
    }

    private void inorderTraverse(Node<T> node, Visitor<T> visitor) {
        if (node != null) {
            inorderTraverse(node.left, visitor);
            visitor.visit(node.data);
            inorderTraverse(node.right, visitor);
        }
    }

    public void postorderTraverse(Visitor<T> visitor) {
        postorderTraverse(root, visitor);
    }

    private void postorderTraverse(Node<T> node, Visitor<T> visitor) {
        if (node != null) {
            postorderTraverse(node.left, visitor);
            postorderTraverse(node.right, visitor);
            visitor.visit(node.data);
        }
    }

    public void preorderTraverse(Visitor<T> visitor) {
        preorderTraverse(root, visitor);
    }

    private void preorderTraverse(Node<T> node, Visitor<T> visitor) {
        if (node != null) {
            visitor.visit(node.data);
            preorderTraverse(node.left, visitor);
            preorderTraverse(node.right, visitor);
        }
    }

    // Inorder Iterator
    private class InorderIterator implements Iterator<T> {
        private Node<T> currentNode;
        private Stack<Node<T>> stack;

        public InorderIterator() {
            this.currentNode = root;
            this.stack = new Stack<>();
            initializeStack(root);
        }

        private void initializeStack(Node<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new UnsupportedOperationException();
            }

            Node<T> nextNode = stack.pop();
            currentNode = nextNode.right;
            initializeStack(currentNode);

            return nextNode.data;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new InorderIterator();
    }

    // getSize() method
    public int getSize() {
        return getSizeOfNode(root);
    }

    private int getSizeOfNode(Node<T> node) {
        if (node == null) {
            return 0;
        } else {
            return 1 + getSizeOfNode(node.left) + getSizeOfNode(node.right);
        }
    }

    // Print Tree
    public void printTree() {
        System.out.println("Inorder Traversal:");
        inorderTraverse(new Visitor<T>() {
            @Override
            public void visit(T data) {
                System.out.print(data + " ");
            }
        });
        System.out.println();

        System.out.println("Preorder Traversal:");
        preorderTraverse(new Visitor<T>() {
            @Override
            public void visit(T data) {
                System.out.print(data + " ");
            }
        });
        System.out.println();

        System.out.println("Postorder Traversal:");
        postorderTraverse(new Visitor<T>() {
            @Override
            public void visit(T data) {
                System.out.print(data + " ");
            }
        });
        System.out.println();

        System.out.println("Tree Size: " + getSize());
    }

    public void prettifyPrintTree() {
        prettifyPrintTree(root, 0);
    }

    private void prettifyPrintTree(Node<T> node, int level) {
        if (node == null) {
            return;
        }

        prettifyPrintTree(node.right, level + 1);

        for (int i = 0; i < level; i++) {
            System.out.print("    ");
        }
        System.out.println(node.data);

        prettifyPrintTree(node.left, level + 1);
    }
}
