import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
 
/**
 * Your implementation of a binary search tree.
 *
 * @author Jongwoo Jang
 * @userid jjang74 (i.e. gburdell3)
 * @GTID 903170765 (i.e. 900000000)
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;
 
    /**
     * A no-argument constructor that should initialize an empty BST.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }
 
    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if(data == null) {
            throw new IllegalArgumentException("data is null");
        }
        for(T d : data) {
            if(d == null) {
                throw new IllegalArgumentException("data is null");
            }
            add(d);
        }
    }
 
    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null, add");
        }
        if(size == 0) {
            root = new BSTNode<T>(data);
            size++;
            return;
        } else {
            BSTNode<T> node = root;
            addHelper(data, node);
        }
    }
    /**
     * Recursive add helper method that would add the data as a leaf in the BST in the 
     * appropriate location. If data is already in the tree, then nothing should be done
     * 
     * @param data the data to be added
     * @param node the pointer of current node.
     */
    private void addHelper(T data, BSTNode<T> node) {
           if(node.getData().compareTo(data) == 0) {
               return;
           } else if(node.getData().compareTo(data) > 0 ) {
               if(node.getLeft() == null) {
                   node.setLeft(new BSTNode<T>(data));
                   size++;
                   return;
               } else {
                   addHelper(data, node.getLeft());
               }
           } else if(node.getData().compareTo(data) < 0 ) {
               if(node.getRight() == null) {
                   node.setRight(new BSTNode<T> (data));
                   size++;
                   return;
               } else {
                   addHelper(data, node.getRight());
               }
           }
    }  
   
    @Override
    public T remove(T data) {
    	if(data == null) {
    		throw new IllegalArgumentException("data is null");
    	}
    	if(root == null) {
    		throw new NoSuchElementException("root is empty");
    	}
    	BSTNode<T> node = root;
    	BSTNode<T> prev = null;
    	return removeHelper(node, data, prev);
    }
    
    /**
     * Recursive remove helper method that would remove the data from tree
     * Takes care of 3 cases (no child, one child, two children)
     * 
     * @param node the pointer of current node
     * @param data the data to remove from the tree
     * @param prev the pointer of previous node
     * @return the data removed from tree that was stored in the tree.
     */
    private T removeHelper(BSTNode<T> node, T data, BSTNode<T> prev) {
    	if(node.getData().compareTo(data) == 0) {
    		T retVal = node.getData();
    		size--;
    		if(node.getLeft() == null && node.getRight() == null) {
    			if(prev == null) {
    				root = null;
    			} else {
    				if(prev.getLeft()!= null && prev.getLeft() == node) {
    					prev.setLeft(null);
    				} else {
    					prev.setRight(null);
    				}
    			}
    			return retVal;
    		} else if(node.getLeft() != null && node.getRight() != null) {
    			BSTNode<T> pred = getPredecessor(node.getLeft(), null, node);
    			pred.setRight(node.getRight());
    			pred.setLeft(node.getLeft());
    			if(prev == null) {
    				root = pred;
    			} else {
    				if(prev.getLeft()!= null && prev.getLeft() == node) {
    					prev.setLeft(pred);
    				} else {
    					prev.setRight(pred);
    				}
    			}
    		} else {
    			if(prev == null) {
    				if(node.getLeft() != null) {
    					root = node.getLeft();
    				} else {
    					root = node.getRight();
    				}
    			} else {
    				if(prev.getLeft() != null && prev.getLeft() == node) {
    					if(node.getLeft() != null) {
    						prev.setLeft(node.getLeft());
    					} else {
    						prev.setLeft(node.getRight());
    					}
    				} else {
    					if(node.getRight() != null) {
    						prev.setRight(node.getRight());
    					} else {
    						prev.setRight(node.getLeft());
    					}
    				}
    			}
    		}
    		return retVal;
    	} else if(node.getData().compareTo(data) > 0) {
    		if(node.getLeft() == null) {
    			throw new NoSuchElementException("data not found");
    		} else {
    			return removeHelper(node.getLeft(), data, node);
    		}
    	} else {
    		if(node.getRight() == null) {
    			throw new NoSuchElementException("data not found");
    		} else {
    			return removeHelper(node.getRight(), data, node);
    		}
    	}
    	
    }
    
    /**
     * Recursive Predecessor method that would return the biggest value in the tree that is smaller
     * than the removing node.
     * 
     * @param cur the pointer of current node
     * @param prev the pointer of previous node
     * @param node the pointer of removing node
     * @return
     */
    private BSTNode<T> getPredecessor(BSTNode<T> cur, BSTNode<T> prev, BSTNode<T> node) {
    	if(cur.getRight() == null) {
    		if (prev == null) {
    			node.setLeft(null);
    		} else {
    			if(cur.getLeft() != null) {
    				prev.setRight(cur.getLeft());
    			} else {
        			prev.setRight(null);
    			}
    		}
    		return cur;
    	} else {
    		return getPredecessor(cur.getRight(), cur, node);
    	}
    	
    }
    
    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
 
        if (root == null) {
            throw new NoSuchElementException("");
        }
        
        BSTNode<T> node = root;
        T retVal = getHelper(node, data);
        
        return retVal;
    }
 
    /**
     * Recursive get helper method that would return the data in the tree matching the
     * parameter passed in
     * 
     * @param node the pointer of current node
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter.
     */
    private T getHelper(BSTNode<T> node, T data) {
        if (node == null) {
            throw new NoSuchElementException("nosuch");
        }
        if(node.getData().compareTo(data) == 0) {
            return node.getData();
        } else if(node.getData().compareTo(data) > 0) {
            if(node.getLeft() != null) {
                return getHelper(node.getLeft(), data);
            } else {
                return getHelper(null, data);
            }
        } else {
            if(node.getRight() != null) {
                return getHelper(node.getRight(), data);
            } else {
                return getHelper(null, data);
            }
        }
    }
 
    @Override
    public boolean contains(T data) {
        if(data == null) {
            throw new IllegalArgumentException("data is null");
        }
        if(root == null) {
            return false;
        }
        BSTNode<T> node = root;
        return containsHelper(node, data);
    }
 
    /**
     * Recursive contains helper method that returns whether or not the data equal to 
     * parameter is in the tree.
     * 
     * @param node the pointer of current node
     * @param data the data to search for in the tree
     * @return boolean value of whether or not the parameter data is in the tree data
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if(node.getData().compareTo(data) == 0) {
            return true;
        } else if (node.getData().compareTo(data) > 0) {
            if(node.getLeft() != null) {
                return containsHelper(node.getLeft(), data);
            } else {
                return false;
            }
        } else {
            if(node.getRight() != null) {
                return containsHelper(node.getRight(), data);
            } else {
                return false;
            }
        }
    }
 
    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        if (root == null) {
        	return list;
        }
        BSTNode<T> node = root;
        preorderHelper(node, list);
        return list;
        
    }
    
    /**
     * recursive preorder helper method that would return a preorder traversal of the tree
     * 
     * @param node the pointer of current node
     * @param list the list of preorder traversal of the tree
     */
    private void preorderHelper(BSTNode<T> node, List<T> list) {
    	
    	list.add(node.getData());
    	if(node.getLeft() != null) {
    		preorderHelper(node.getLeft(), list);
    	}
    	if(node.getRight() != null) {
    		preorderHelper(node.getRight(), list);
    	}
    }
 
    @Override
    public List<T> postorder() {
    	List<T> list = new ArrayList<T>();
    	if(root == null) {
    		return list;
    	}
    	BSTNode<T> node = root;
    	postorderHelper(node, list);
    	return list;
    }
    /**
     * recursive postorder helper method that would return a postorder traversal of the tree
     * 
     * @param node the pointer of current node
     * @param list the list of postorder traversal of the tree
     */
    private void postorderHelper(BSTNode<T> node, List<T> list) {
    	if(node.getLeft() != null) {
    		postorderHelper(node.getLeft(), list);
    	}
    	if(node.getRight() != null) {
    		postorderHelper(node.getRight(), list);
    	}
    	list.add(node.getData());
    }
    
 
    @Override
    public List<T> inorder() {
    	List<T> list = new ArrayList<T>();
    	if (root == null) {
    		return list;
    	}
    	BSTNode<T> node = root;
    	inorderHelper(node, list);
    	return list;
    }
    /**
     * recursive inorder helper method that would return a inorder traversal of the tree
     * 
     * @param node the pointer of current node
     * @param list the list of inorder traversal of the tree
     */
    private void inorderHelper(BSTNode<T> node, List<T> list) {
    	if(node.getLeft() != null) {
    		inorderHelper(node.getLeft(), list);
    	}
    	list.add(node.getData());
    	if(node.getRight() != null) {
    		inorderHelper(node.getRight(), list);
    	}
    }
 
    @Override
    public List<T> levelorder() {
    	List<T> list = new ArrayList<T>();
    	if (root == null) {
    		return list;
    	}
    	Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    	queue.add(root);
    	while (!queue.isEmpty()) {
    		BSTNode<T> node = queue.remove();
    		list.add(node.getData());
    		if (node.getLeft() != null) {
    			queue.add(node.getLeft());
    		}
    		if (node.getRight() != null) {
    			queue.add(node.getRight());
    		}
    	}
    	return list;
    	
    }
 
    @Override
    public int distanceBetween(T data1, T data2) {
    	if (data1 == null || data2 == null) {
    		throw new IllegalArgumentException("data1 or data2 is null");
    	}
    	if(data1 == data2) {
    		return 0;
    	}
    	if(root == null) {
    		throw new NoSuchElementException("data is not in the tree");
    	}
    	
    	if(data1.compareTo(data2) > 0) {
    		T temp = data1;
    		data1 = data2;
    		data2 = temp;
    	}
    	BSTNode<T> node = root;
    	int retVal = distanceBetweenHelper(node, data1, data2);
    	
    	return retVal;
    }
 
    /**
     * Recursive distancebetween helper method that returns the distance between nodes
     * with data1 and data2. 
     * @param node the pointer of current node, common ancestor
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the shortest distance between the two data in the tree
     * @throws java.util.NoSuchElementException if data1 or data2 is not in the tree
     */
    private int distanceBetweenHelper(BSTNode<T> node, T data1, T data2) {
    	T data = node.getData();
    	if(data.compareTo(data1) >= 0 && data.compareTo(data2) <= 0) {
    		if(data.compareTo(data1) > 0 && data.compareTo(data2) == 0) {
    			if(node.getLeft() == null) {
    				throw new NoSuchElementException("data is not in the tree");
    			}
    			return getLevel(node.getLeft(), data1) + 1;
    			
    		} else if(data.compareTo(data1) == 0 && data.compareTo(data2) < 0) {
    			if(node.getRight() == null) {
    				throw new NoSuchElementException("data is not in the tree");
    			}
    			return getLevel(node.getRight(), data2) + 1;
    		} else {
    			if(node.getLeft() == null || node.getRight() == null) {
    				throw new NoSuchElementException("data is not in the tree");
    			}
    			return getLevel(node.getLeft(), data1) + getLevel(node.getRight(), data2) + 2;
    		}
    	} else if(data.compareTo(data1) < 0) {
    		if(node.getRight() == null) {
    			throw new NoSuchElementException("data is not in the tree");
    		}
    		return distanceBetweenHelper(node.getRight(), data1, data2);
    	} else if(data.compareTo(data2) > 0) {
    		if(node.getLeft() == null) {
    			throw new NoSuchElementException("data is not in the tree");
    		}
    		return distanceBetweenHelper(node.getLeft(), data1, data2);
    	} else {
    		return 0;
    	}
    }
    
    /**
     * recursive method that compute the distance between 
     * the common ancestor(node) to the node with data
     * 
     * @param node the pointer of current node. 
     * @param data the data to end path on
     * @return the distance between the starting node, and the ending node with data
     * @throws java.util.NoSuchElementException if data is not in the tree
     */
    private int getLevel(BSTNode<T> node, T data) {
    	if(node.getData().compareTo(data) == 0) {
    		return 0;
    	} else if(node.getData().compareTo(data) > 0) {
    		if(node.getLeft() == null) {
    			throw new NoSuchElementException("data is not in the tree");
    		}
    		return getLevel(node.getLeft(), data) + 1;
    	} else {
    		if(node.getRight() == null) {
    			throw new NoSuchElementException("data is not in the tree");
    		}
    		return getLevel(node.getRight(), data) + 1;
    	}
    	
    }
    
    @Override
    public void clear() {
    	root = null;
    	size = 0;
    }
 
    @Override
    public int height() {
    	if (root == null) {
    		return -1;
    	}
    	BSTNode<T> node = root;
    	return heightHelper(node);
    }
 
    /**
     * Recursive height helper method that calculate and return the height of the
     * root of the tree
     * @param node the pointer of current node
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int heightHelper(BSTNode<T> node) {
        if (node == null) {
            return -1;
        }
        return Math.max(heightHelper(node.getLeft()),
                heightHelper(node.getRight())) + 1;
    }
    
    @Override
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }
 
    @Override
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}