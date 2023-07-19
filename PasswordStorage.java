//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P09 Password Cracking
// Course:   CS 300 Spring 2023
//
// Author:   Shourjo Aditya Chaudhuri
// Email:    sachaudhuri@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    No Pair Programming in this project
// Partner Email:   No Pair Programming in this project
// Partner Lecturer's Name: No Pair Programming in this project
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         None
// Online Sources:  None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class is the Binary Search Tree to store the passwords
 *
 * @author Shourjo Aditya Chaudhuri
 */
public class PasswordStorage {

    protected PasswordNode root; //the root of this BST that contains passwords
    private int size; //how many passwords are in the BST
    private final Attribute COMPARISON_CRITERIA; //what password information to use to determine order in the tree

    /**
     * Constructor that creates an empty BST and sets the comparison criteria.
     *
     * @param comparisonCriteria, the Attribute that will be used to determine order in the tree
     */
    public PasswordStorage(Attribute comparisonCriteria) {
        COMPARISON_CRITERIA = comparisonCriteria;
    }

    /**
     * Getter for this BST's criteria for determining order in the three
     *
     * @return the Attribute that is being used to make comparisons in the tree
     */
    public Attribute getComparisonCriteria() {
        return COMPARISON_CRITERIA;
    }

    /**
     * Getter for this BST's size.
     *
     * @return the size of this tree
     */
    public int size() {
        return this.size;
    }

    /**
     * Determines whether or not this tree is empty.
     *
     * @return true if it is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Provides in-order String representation of this BST, with each Password on its own line. The
     * String representation ends with a newline character ('\n')
     *
     * @return this BST as a string
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return ""; //returns an empty string if the tree is empty
        }
        return toStringHelper(this.root); //returns String representation of this BST with each Password on its own line
    }

    /**
     * Recursive method the uses an in-order traversal to create the string representation of this
     * tree.
     *
     * @param currentNode, the root of the current tree
     * @return the in-order String representation of the tree rooted at current node
     */
    private String toStringHelper(PasswordNode currentNode) {
        // base case:
        if (currentNode == null) {
            return ""; //returns empty string if the currentNode is null
        }
        // recursive case:
        return toStringHelper(currentNode.getLeft()) + currentNode.getPassword().toString() + "\n" +
                toStringHelper(currentNode.getRight());
    }

    /**
     * Determines whether this tree is actually a valid BST.
     *
     * @return true if it is a BST, false otherwise
     */
    public boolean isValidBST() {
        return isValidBSTHelper(this.root, Password.getMinPassword(), Password.getMaxPassword());
    }

    /**
     * Recurisvely determines if the the tree rooted at the current node is a valid BST. That is,
     * every value to the left of currentNode is "less than" the value in currentNode and every
     * value to the right of it is "greater than" it.
     *
     * @param currentNode, the root node of the current tree
     * @param lowerBound,  the smallest possible password
     * @param upperBound,  the largest possible password
     * @return true if the tree rooted at currentNode is a BST, false otherwise
     */
    private boolean isValidBSTHelper(PasswordNode currentNode, Password lowerBound,
                                     Password upperBound) {
        //MUST BE IMPLEMENTED RECURSIVELY
        // BASE CASE 1: the tree rooted at currentNode is empty, which does not violate any BST
        // rules
        if (currentNode == null) {
            return true;
        }
        // BASE CASE 2: the current Password is outside of the upper OR lower bound for this
        // subtree, which is against the rules for a valid BST
        if (currentNode.getPassword().compareTo(upperBound, COMPARISON_CRITERIA) > 0 ||
                currentNode.getPassword().compareTo(lowerBound, COMPARISON_CRITERIA) < 0) {
            return false;
        }
        // If we do not have a base case situation, we must use recursion to verify currentNode's
        // child subtrees
        // RECURSIVE CASE 1: Check that the left subtree contains only Passwords greater than
        // lowerBound and less than currentNode's Password; return false if this property is NOT
        // satisfied
        if (!isValidBSTHelper(currentNode.getLeft(), lowerBound, currentNode.getPassword())) {
            return false;
        }
        // RECURSIVE CASE 2: Check that the right subtree contains only Passwords greater than
        // currentNode's Password and less than upperBound; return false if this property is NOT
        // satisfied
        if (!isValidBSTHelper(currentNode.getRight(), currentNode.getPassword(), upperBound)) {
            return false;
        }
        // COMBINE RECURSIVE CASE ANSWERS: this is a valid BST if and only if both case 1 and 2 are
        // valid
        return true;
    }

    /**
     * Returns the password that matches the criteria of the provided key. Ex. if the COMPARISON
     * CRITERIA is OCCURRENCE and the key has an occurrence of 10 it will return the password stored
     * in the tree with occurrence of 10
     *
     * @param key, the password that contains the information for the password we are searching for
     * @return the Password that matches the search criteria, if it does not exist in the tree it
     * this will be null
     */
    public Password lookup(Password key) {
        return lookupHelper(key, root);
    }

    /**
     * Recursive helper method to find the matching password in this BST
     *
     * @param key,         password containing the information we are searching for
     * @param currentNode, the node that is the current root of the tree
     * @return the Password that matches the search criteria, if it does not exist in the tree it
     * this will be null
     */
    private Password lookupHelper(Password key, PasswordNode currentNode) {
        // base case1: Reached a Lef Node and did not find anything
        if (currentNode == null) {
            return null; //if current node is null, returns null
        }
        // base case 2: Found the key!
        if (currentNode.getPassword() == key) {
            return currentNode.getPassword(); //returns the password if there is a match at the currentNode
        }
        // recursive case
        Password toHunt = null;
        if (key.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) < 0) {
            toHunt = lookupHelper(key, currentNode.getLeft());
        } else if (key.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) > 0) {
            toHunt = lookupHelper(key, currentNode.getRight());
        }
        return toHunt;
    }

    /**
     * Returns the best (max) password in this BST
     *
     * @return the best password in this BST
     * @throws NoSuchElementException if the BST is empty
     */
    public Password getBestPassword() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty!"); //if the BST is empty
        }
        if (root.hasRightChild()) {
            PasswordNode followingNode = root.getRight(); // right child of root
            while (followingNode.hasRightChild()) { // iteratively goes to the right most node
                followingNode = followingNode.getRight();
            }
            return followingNode.getPassword();
        } else {
            return root.getPassword();
        }
    }

    /**
     * Returns the worst password in this BST
     *
     * @return the worst password in this BST
     * @throws NoSuchElementException if the BST is empty
     */
    public Password getWorstPassword() {
        if (isEmpty()) {
            throw new NoSuchElementException("Empty!");
        }
        if (root.hasLeftChild()) {
            PasswordNode followingNode = root.getLeft(); // left child of the root
            while (followingNode.hasLeftChild()) { // iteratively goes to the left most node
                followingNode = followingNode.getLeft();
            }
            return followingNode.getPassword();
        } else {
            return root.getPassword();
        }
    }

    /**
     * Adds the Password to this BST.
     *
     * @param toAdd, the password to be added to the tree
     * @throws IllegalArgumentException if the (matching) password object is already in the tree
     */
    public void addPassword(Password toAdd) throws IllegalArgumentException {
        PasswordNode toAddNode = new PasswordNode(toAdd);
        if (root == null) {
            root = toAddNode; //set the node to add as the root of the tree as tree empty
        } else if (addPasswordHelper(toAdd, root) == false) { // when password already exists
            throw new IllegalArgumentException("Password " + "already exists!");
        }
        size++; // increments the size after addition of a password
    }


    /**
     * Recursive helper that traverses the tree and adds the password where it belongs
     *
     * @param toAdd,       the password to add to the tree
     * @param currentNode, the node that is the current root of the (sub)tree
     * @return true if it was successfully added, false otherwise
     */
    private boolean addPasswordHelper(Password toAdd, PasswordNode currentNode) {
        // RECURSIVE CASE
        if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) < 0) {
            if (currentNode.getLeft() != null) { // checks if there is a left child
                return addPasswordHelper(toAdd, currentNode.getLeft());
            } else if (currentNode.getLeft() == null) {
                PasswordNode toAddNode = new PasswordNode(toAdd);
                currentNode.setLeft(toAddNode); // add
            }
        } else if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) > 0) {
            if (currentNode.getRight() != null) { // checksif there is a right child
                return addPasswordHelper(toAdd, currentNode.getRight());
            } else if (currentNode.getRight() == null) {
                PasswordNode toAddNode = new PasswordNode(toAdd);
                currentNode.setRight(toAddNode);
            }
        } else if (toAdd.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) == 0) {
            return false; // if the password already exists
        }
        return true; // password added successfully
    }

    /**
     * Removes the matching password from the tree
     *
     * @param toRemove, the password to be removed from the tree
     * @throws NoSuchElementException if the password is not in the tree
     */
    public void removePassword(Password toRemove) throws NoSuchElementException {
        if (lookup(toRemove) == null) { //if the password is not in the tree
            throw new NoSuchElementException("Password not found");
        }
        removePasswordHelper(toRemove, root);
        size--; // decrements the size after successfully removing a password
    }

    /**
     * Recursive helper method to that removes the password from this BST.
     *
     * @param toRemove,    the password to be removed from the tree
     * @param currentNode, the root of the tree we are removing from
     * @return the PasswordNode representing the NEW root of this subtree now that toRemove has been
     * removed. This may still be currentNode, or it may have changed!
     */
    private PasswordNode removePasswordHelper(Password toRemove, PasswordNode currentNode) {
        //BASE CASE: current tree is empty
        if (currentNode == null) {
            return null;// if the currentNode is null
        }
        //RECURSIVE CASE: toRemove is in the left subtree, continue searching
        if (toRemove.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) < 0) {
            currentNode.setLeft(removePasswordHelper(toRemove, currentNode.getLeft()));
        }
        //RECURSIVE CASE: toRemove is in the right subtree, continue searching
        else if (toRemove.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) > 0) {
            currentNode.setRight(removePasswordHelper(toRemove, currentNode.getRight()));
        }
        //otherwise we found the node to remove!
        else if (toRemove.compareTo(currentNode.getPassword(), COMPARISON_CRITERIA) == 0) {
            //BASE CASE: current node has no children
            if (currentNode.isLeafNode()) {
                return null;
            }
            //BASE CASE(S): current node has one child (one for the left and right respectively)
            else if (currentNode.numberOfChildren() == 1) {
                if (currentNode.getLeft() == null) {
                    return currentNode.getRight();
                } else if (currentNode.getRight() == null) {
                    return currentNode.getLeft();
                }
            }
            //RECURSIVE CASE: currentNode has 2 children
            //1)Find the predecessor password [HINT: Write a private helper method!]
            else if (currentNode.numberOfChildren() == 2) {
                Password prePass = findPredecessorNode(currentNode).getPassword();
                //2)Make new node for the predecessor password. It should have same left and right
                // subtree as the current node.
                PasswordNode currentReplacement = new PasswordNode(prePass, currentNode.getLeft(),
                        currentNode.getRight());
                //3)Replace currentNode with the new predecessor node
                currentNode = currentReplacement;
                //4)Remove the (duplicate) predecessor from the current tree and update the left
                // subtree
                if (toRemove.equals(root.getPassword())) root = currentNode;
                currentNode.setLeft(removePasswordHelper(prePass, currentNode.getLeft()));
            }
        }

        return currentNode;
    }

    /**
     * Find the node with the password closest, but less than the Node that is passed on to this
     * method as a parameter
     *
     * @param Node Node for which we have to find the immediate lower password node
     * @return the node containing whose password is just below the current password node
     */
    private PasswordNode findPredecessorNode(PasswordNode Node) {
        PasswordNode followingNode = Node.getLeft();
        if (followingNode.getRight() != null) {
            PasswordNode predecessorNode = followingNode.getRight(); //initialises with the following node
            while (predecessorNode.getRight() != null) {
                predecessorNode = predecessorNode.getRight(); //updates the predecessor appropriately
            }
            return predecessorNode;
        }
        return followingNode; //returns the node containing a password below the current password node
    }
}
