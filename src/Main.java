public class Main {
    public static void main(String[] args) {
        BinaryTree<Integer> tree = new BinaryTree<>(50,
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

        tree.prettifyPrintTree();
    }
}