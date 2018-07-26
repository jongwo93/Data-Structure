import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Nick Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        for (T d : data) {
            if (d == null) {
                throw new IllegalArgumentException("data is null");
            }
            add(d);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        root = addHelper(root, data);
    }
    
    /**
     * Recursive add helper method that would add the data in the AVL tree, 
     * in the appropriate location, balance, and rotate. If the data is
     * already in the tree, then nothing should be done
     * @param node the pointer of current node
     * @param data the data to be added
     * @return root of AVL tree
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            node = new AVLNode<T>(data);
        } else {
            if (node.getData().compareTo(data) == 0) {
                return node;
            } else if (node.getData().compareTo(data) > 0) {
                node.setLeft(addHelper(node.getLeft(), data));
            } else {
                node.setRight(addHelper(node.getRight(), data));
            }
            node.setHeight(Math.max(heightHelper(node.getLeft()), 
                    heightHelper(node.getRight())) + 1);
            node.setBalanceFactor(heightHelper(node.getLeft())
                    - heightHelper(node.getRight()));
            
            node = balHelper(node);
            node.setHeight(Math.max(heightHelper(node.getLeft()), 
                heightHelper(node.getRight())) + 1);
            node.setBalanceFactor(heightHelper(node.getLeft()) 
                    - heightHelper(node.getRight()));
            
        }
        return node;
    }
    
    /**
     * height helper method that would explicitly return -1 if node is null 
     * or height stored in node
     * @param node the node to retrieve height of
     * @return height of the node
     */
    private int heightHelper(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
        
    }
    
    /**
     * Balance helper method that would compute which rotation would be needed
     * at the current node
     * @param node the pointer of current node
     * @return balanced parent node.
     */
    private AVLNode<T> balHelper(AVLNode<T> node) {
        if (node.getBalanceFactor() == -2) { //left heavy
            if (node.getRight().getBalanceFactor() == 1) {
                node = rightleftR(node);
            } else {
                node = leftR(node);
            }
        }
        if (node.getBalanceFactor() == 2) { //left heavy
            if (node.getLeft().getBalanceFactor() == -1) {
                node = leftrightR(node);
            } else {
                node = rightR(node);
            }
        }
        
        return node;
    }

    /**
     * Left Rotation of unbalanced subtree
     * @param node the node that is unbalanced
     * @return balanced node 
     */
    private AVLNode<T> leftR(AVLNode<T> node) {
        AVLNode<T> newParent = node.getRight();
        node.setRight(newParent.getLeft());
        newParent.setLeft(node);
        node.setHeight(Math.max(heightHelper(node.getLeft()), 
                heightHelper(node.getRight())) + 1);
        node.setBalanceFactor(heightHelper(node.getLeft()) 
                - heightHelper(node.getRight()));
        newParent.setHeight(Math.max(heightHelper(newParent.getLeft()), 
                heightHelper(newParent.getRight())) + 1);
        newParent.setBalanceFactor(heightHelper(newParent.getLeft()) 
                - heightHelper(newParent.getRight()));
        
        return newParent;
    }
    
    /**
     * Right rotation of unbalanced subtree
     * @param node the node that is unbalanced
     * @return balanced node
     */
    private AVLNode<T> rightR(AVLNode<T> node) {
        AVLNode<T> newParent = node.getLeft();
        node.setLeft(newParent.getRight());
        newParent.setRight(node);
        node.setHeight(Math.max(heightHelper(node.getLeft()), 
                heightHelper(node.getRight())) + 1);
        node.setBalanceFactor(heightHelper(node.getLeft()) 
                - heightHelper(node.getRight()));
        newParent.setHeight(Math.max(heightHelper(newParent.getLeft()), 
                heightHelper(newParent.getRight())) + 1);
        newParent.setBalanceFactor(heightHelper(newParent.getLeft()) 
                - heightHelper(newParent.getRight()));
        
        return newParent;
    }
    
    /**
     * right - left rotation of unbalanced subtree
     * @param node the node that is unbalanced
     * @return balanced node
     */
    private AVLNode<T> rightleftR(AVLNode<T> node) {
        node.setRight(rightR(node.getRight()));
        AVLNode<T> newParent = leftR(node);
        
        return newParent;
    }
    
    /**
     * left - right rotation of unbalanced subtree
     * @param node the node that is unbalanced
     * @return balanced node
     */
    private AVLNode<T> leftrightR(AVLNode<T> node) {
        node.setLeft(leftR(node.getLeft()));
        AVLNode<T> newParent = rightR(node);
        
        return newParent;
    }
    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if (root == null) {
            throw new NoSuchElementException("Tree is empty");
        }
        AVLNode<T> node = root;
        AVLNode<T> prev = null;
        T retVal = get(data);
        root = removeHelper(node, data);
        
        return retVal;
    }
    
    /**
     * Recursive remove helper method that would remove the data from tree
     * Takes care of 3 cases (no child, one child, two child)
     * check for balance, rotate.
     * @param node the pointer of current node
     * @param data the data to be removed from the tree
     * @return root of AVL tree
     */
    private AVLNode<T> removeHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("data not found");
        }
        if (node.getData().compareTo(data) > 0) {
            node.setLeft(removeHelper(node.getLeft(), data));
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(removeHelper(node.getRight(), data));
        } else {
            

            if (node.getRight() == null && node.getLeft() == null) {
                node = null;
                size--;
            } else if (node.getLeft() != null && node.getRight() != null) {
                AVLNode<T> successor = getSuccessor(node.getRight());
                node.setData(successor.getData());
                node.setRight(removeHelper(node.getRight(), 
                        successor.getData()));
                size--;
            } else {
                if (node.getLeft() == null) {
                    node = node.getRight();
                } else {
                    node = node.getLeft();
                }
                size--;
            }
        }
        if (node != null) {
            
            node.setHeight(Math.max(heightHelper(node.getLeft()), 
                    heightHelper(node.getRight())) + 1);
            node.setBalanceFactor(heightHelper(node.getLeft()) 
                    - heightHelper(node.getRight()));

            node = balHelper(node);
            node.setHeight(Math.max(heightHelper(node.getLeft()), 
                    heightHelper(node.getRight())) + 1);
            node.setBalanceFactor(heightHelper(node.getLeft()) 
                    - heightHelper(node.getRight()));
        }
        return node;
    }
    
    /**
     * Recursive successor method helps removing when there are two children
     * @param node the right child of removing node
     * @return the successor (left most node) of removing node
     */
    private AVLNode<T> getSuccessor(AVLNode<T> node) {
        if (node.getLeft() == null) {
            size++;
            return node; 
        } else {
            return getSuccessor(node.getLeft());
        }
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return getHelper(root, data);
    }

    /**
     * Recursive get helper method that would return the data in the tree
     * matching the parameter passed in
     * @param node the pointer of current node
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     */
    private T getHelper(AVLNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("data is not found");
        }
        if (node.getData().compareTo(data) == 0) {
            return node.getData();
        } else if (node.getData().compareTo(data) > 0) {
            return getHelper(node.getLeft(), data);
        } else {
            return getHelper(node.getRight(), data);
        }
    }
    
    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return containsHelper(root, data);
    }
    
    /**
     * Recursive contains helper method that returns whether or not the data 
     * equal to parameter is in the tree
     * @param node the pointer of current node
     * @param data the data to search for in the tree
     * @return boolean value of whether or not the parameter data is in the tree
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
        if (node == null) {
            return false;
        }
        if (node.getData().compareTo(data) == 0) {
            return true;
        } else if (node.getData().compareTo(data) > 0) {
            return containsHelper(node.getLeft(), data);
        } else {
            return containsHelper(node.getRight(), data);
        }
    }

    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    @Override
    public T getSecondLargest() {
        if (size <= 1) {
            throw new NoSuchElementException("not enough data in the tree "
                    + "for 2nd largest element");
        }
        int num = 0;

        List<T> list = new ArrayList<T>();
        
        getSecondLargestHelper(root, list); 
        
        return list.get(list.size() - 1);
    }
    
    /**
     * Recursive helper method that gets the largest, and second largest
     * data from the tree
     * @param node the current pointer of node
     * @param list the list of largest and second largest element from tree
     */
    private void getSecondLargestHelper(AVLNode<T> node, List<T> list) {
        if (node.getRight() != null) {
            getSecondLargestHelper(node.getRight(), list);
        }
        if (list.size() < 2) {
            list.add(node.getData());
        } else {
            return;
        }
        
        if (node.getLeft() != null) {
            getSecondLargestHelper(node.getLeft(), list);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof AVL)) {
            AVL<T> myAVL = (AVL<T>) obj;
            return equalsHelper(root, myAVL.root);
        }
        return false;
    }
    
    /**
     * Recursive equals helper method that returns whether the Object is equal
     * to this AVL tree
     * @param node the root of this AVL tree
     * @param obj the object we are comparing to
     * @return booelan value of whether or not the parameter object is equal
     * to AVL tree
     */
    private boolean equalsHelper(AVLNode<T> node, AVLNode<T> obj) {
        if (node == obj) {
            return true;
        } else if (node == null || obj == null) {
            return false;
        } else if (!node.getData().equals(obj.getData())) {
            return false;
        } else {
            return equalsHelper(node.getLeft(), obj.getLeft()) 
                    && equalsHelper(node.getRight(), obj.getRight());
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {

        return heightRec(root);
    }
    
    /**
     * Recursive height helper method that calculate and return the height of
     * the root of the tree
     * @param node the pointer of current node
     * @return the height of the root of the tree, -1 if tree is empty
     */
    private int heightRec(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return Math.max(heightRec(node.getLeft()), 
                heightRec(node.getRight())) + 1;
    }
    

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
