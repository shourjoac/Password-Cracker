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
 * This class is the tester class to test the implementations of PasswordStorage, PasswordNode,
 * Password class.
 *
 * @author Shourjo Aditya Chaudhuri
 */
public class PasswordCrackingTester {

    /**
     * Validates the constructor and accessor methods of PasswordStorage, specifically the
     * getComparisonCriteria(), size(), and isEmpty() methods, as well as accessing the
     * protected data field root.
     * <p>
     * Be sure to try making multiple PasswordStorage objects with different Attributes.
     *
     * @return true if the basic accessor methods work as expected, false otherwise
     */
    public static boolean testBasicPasswordStorageMethods() {
        PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
        PasswordStorage test2 = new PasswordStorage(Attribute.HASHED_PASSWORD);
        Password pass1 = new Password("Password@01", 101);
        Password pass2 = new Password("Password@02", 102);
        Password pass3 = new Password("Password@03", 103);
        Password pass4 = new Password("Password@04", 104);
        Password pass5 = new Password("Password@05", 105);
        { // Tree is empty right now, we have not connected any nodes
            try {
                // size() method
                int sizeActual = test1.size();
                int sizeExpected = 0;
                if (sizeExpected != sizeActual) {
                    return false;
                }
                // isEmpty() method : should return true
                boolean expected = true;
                boolean actual = test1.isEmpty();
                if (actual != expected) {
                    return false;
                }
                //getComparisionCriteria() method
                Attribute expectedAttribute = Attribute.OCCURRENCE;
                Attribute actualAttribute = test1.getComparisonCriteria();
                if (actualAttribute != expectedAttribute) {
                    return false;
                }
                PasswordNode expectedRoot = null;
                PasswordNode actualRoot = test1.root;
                if (expectedRoot != actualRoot) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        test1.addPassword(pass4);
        test1.addPassword(pass5);
        test1.addPassword(pass2);
        test1.addPassword(pass3);
        test1.addPassword(pass1);
        //                           pass 4

        //          pass5                             pass2

        //      pass3      pass1
        { // Tree is not empty
            try {
                // size() method
                int sizeActual = test1.size();
                int sizeExpected = 5;
                if (sizeExpected != sizeActual) {
                    return false;
                }
                //  isEmpty() method : should return true
                boolean expected = false;
                boolean actual = test1.isEmpty();
                if (actual != expected) {
                    return false;
                }
                //getComparisionCriteria() method
                Attribute expectedAttribute = Attribute.OCCURRENCE;
                Attribute actualAttribute = test1.getComparisonCriteria();

                if (actualAttribute != expectedAttribute) {
                    return false;
                }
                PasswordNode passNode4 = new PasswordNode(pass4);
                PasswordNode expectedRoot = passNode4;
                PasswordNode actualRoot = test1.root;
                if (!expectedRoot.getPassword().equals(actualRoot.getPassword())) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        test2.addPassword(pass4);
        test2.addPassword(pass5);
        test2.addPassword(pass2);
        test2.addPassword(pass3);
        test2.addPassword(pass1);
        { // Tree is not empty - TRYING WITH A DIFFERENT ATTRIBUTE
            try {
                // size() method
                int sizeActual = test2.size();
                int sizeExpected = 5;
                if (sizeExpected != sizeActual) {
                    return false;
                }
                //  isEmpty() method : should return true
                boolean expected = false;
                boolean actual = test1.isEmpty();
                if (actual != expected) {
                    return false;
                }
                //getComparisionCriteria() method
                Attribute expectedAttributed = Attribute.HASHED_PASSWORD;
                Attribute actualAttribute = test2.getComparisonCriteria();
                if (actualAttribute != expectedAttributed) {
                    return false;
                }
                PasswordNode passNode4 = new PasswordNode(pass4);
                PasswordNode expectedRoot = passNode4;
                PasswordNode actualRoot = test2.root;
                if (!expectedRoot.getPassword().equals(actualRoot.getPassword())) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates the Password class compareTo() method. Create at least two DIFFERENT
     * Password objects and compare them on each of the Attribute values. See the writeup
     * for details on how the various comparisons are expected to work.
     *
     * @return true if Password's compareTo() works as expected, false otherwise
     */
    public static boolean testPasswordCompareTo() {
        Password p1 = new Password("BadPass999", 300);
        Password p2 = new Password("66BadPass999@", 460);
        Password p3 = new Password("BadPass999", 300);
        Password p4 = new Password("66BAdPass999@!", 43);
        Password p5 = new Password("BadPass999", 400);
        { // BASED ON NUMBER OF OCCURANCES
            int result = p1.compareTo(p2, Attribute.OCCURRENCE);
            if (result >= 0) {
                return false;
            }
            int result2 = p1.compareTo(p3, Attribute.OCCURRENCE);
            if (result2 != 0) {
                return false;
            }
            int result3 = p2.compareTo(p1, Attribute.OCCURRENCE);
            if (result3 <= 0) {
                return false;
            }
        }
        { // BASED ON STRENGTH RATING
            // P2 SHOULD BE A STRONGER PASSWORD BECAUSE IT AN EXTRA NUMERICAL AND SPECIAL CHARACTER
            int result = p2.compareTo(p4, Attribute.STRENGTH_RATING);
            if (result >= 0) {
                return false;
            }
            int result2 = p5.compareTo(p1, Attribute.STRENGTH_RATING);
            if (result2 != 0) {
                return false;
            }
        }
        { // BASED ON HASH PASSWORD STRING
            int result = p1.compareTo(p2, Attribute.HASHED_PASSWORD);
            if (result >= 0) return false;
        }
        return true;
    }

    /**
     * Validates the incomplete methods in PasswordNode, specifically isLeafNode(),
     * numberOfChildren(), hasLeftChild() and hasRightChild(). Be sure to test all
     * possible configurations of a node in a binary tree!
     *
     * @return true if the status methods of PasswordNode work as expected, false otherwise
     */
    public static boolean testNodeStatusMethods() {
        Password pass1 = new Password("Password@01", 101);
        Password pass2 = new Password("Password@02", 102);
        Password pass3 = new Password("Password@03", 103);
        Password pass4 = new Password("Password@04", 104);
        Password pass5 = new Password("Password@05", 105);
        PasswordNode passNode3 = new PasswordNode(pass3, null, null);
        PasswordNode passNode1 = new PasswordNode(pass1, null, null);
        PasswordNode passNode5 = new PasswordNode(pass5, passNode1, passNode1);
        PasswordNode passNode4 = new PasswordNode(pass4, passNode5, null);
        //                           pass 4
        //          pass 5                             (null)
        //      pass3      pass1
        { // verifying isLeafNode() method on a leaf and internal node
            // leaf Node
            boolean actual1 = passNode1.isLeafNode();
            boolean expected1 = true;
            // Internal Node - Not a leaf Node
            boolean actual2 = passNode5.isLeafNode();
            boolean expected2 = false;
            if (actual2 != expected2 || actual1 != expected1) return false;
        }
        { //verifying getNumberOfChildren()
            //lead Node - No Children !
            int actual1 = passNode1.numberOfChildren();
            int expected1 = 0;
            int actual2 = passNode5.numberOfChildren();
            int expected2 = 2;
            int actual3 = passNode4.numberOfChildren();
            int expected3 = 1;
            if (actual1 != expected1 || actual2 != expected2 || actual3 != expected3) {
                return false;
            }
        }
        // hasLeftChild() and hasRightChild()
        {
            boolean expectedHasLeft = true;
            boolean excpectedHasRight = true;
            boolean actualHasLeft = passNode5.hasLeftChild();
            boolean actualHasRight = passNode5.hasRightChild();
            if (excpectedHasRight != actualHasRight || expectedHasLeft != actualHasLeft) {
                return false;
            }
        }
        {
            boolean expectedHasLeft = true;
            boolean excpectedHasRight = false;
            boolean actualHasLeft = passNode4.hasLeftChild();
            boolean actualHasRight = passNode4.hasRightChild();
            if (excpectedHasRight != actualHasRight || expectedHasLeft != actualHasLeft) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests the implementation of the overridden toString() method of the PasswordStorage class
     *
     * @return true if and only if all the test cases pass, false otherwise
     */
    public static boolean testToString() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);

            // empty is empty string
            String expected = "";
            String actual = bst.toString();
            if (!actual.equals(expected)) {
                System.out.println("toString() does not return the proper value on an empty tree!");
                return false;
            }

            // size one only returns 1 thing
            Password p = new Password("1234567890", 15000);
            PasswordNode rootNode = new PasswordNode(p);

            bst.root = rootNode; // here I am manually building the tree by editing the root node
            // directly to be the node of my choosing

            expected = p.toString() + "\n";
            actual = bst.toString();
            if (!actual.equals(expected)) {
                return false;
            }


            // big tree returns in-order traversal
            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);

            PasswordNode p4Node = new PasswordNode(p4);
            PasswordNode p3Node = new PasswordNode(p3);
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p6Node = new PasswordNode(p6, p7Node, null);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            expected = p4.toString() + "\n" + p2.toString() + "\n" + p3.toString() + "\n" +
                    p.toString()
                    + "\n" + p5.toString() + "\n" + p7.toString() + "\n" + p6.toString() + "\n";
            actual = bst.toString();

            if (!actual.equals(expected)) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Tests the implementation of the overridden isValidBST() method of the PasswordStorage class
     *
     * @return true if and only if all the test cases pass, false otherwise
     */
    public static boolean testIsValidBST() {
        try {
            PasswordStorage bst = new PasswordStorage(Attribute.OCCURRENCE);
            // empty tree is a valid bst
            /*
             * String expected = ""; String actual = bst.toString();
             */
            if (!bst.isValidBST()) {
                System.out.println("isValidBST() says that an empty tree is not a valid BST!");
                return false;
            }

            // size one is a bst
            Password p = new Password("1234567890", 1000);
            PasswordNode rootNode = new PasswordNode(p);

            bst.root = rootNode; // here I am manually building the tree by editing the root node
            // directly to be the node of my choosing

            if (!bst.isValidBST()) {
                System.out.println("isValidBST() says that a tree of size 1 is not a valid BST!");
                return false;
            }

            Password p2 = new Password("test", 500);
            Password p3 = new Password("iloveyou", 765);
            Password p4 = new Password("qwerty", 250);
            Password p5 = new Password("admin", 1002);
            Password p6 = new Password("password", 2232);
            Password p7 = new Password("abc123", 2090);

            // works on indentifying small obviously invalid tree
            PasswordNode p7Node = new PasswordNode(p7);
            PasswordNode p3Node = new PasswordNode(p3);
            rootNode = new PasswordNode(p, p7Node, p3Node);
            bst.root = rootNode;
            if (bst.isValidBST())
                return false;

            // tree with only one subtree being valid, other subtree has a violation a couple layers
            // deep


            PasswordNode p4Node = new PasswordNode(p4);
            p7Node = new PasswordNode(p7);
            p3Node = new PasswordNode(p3);
            PasswordNode p6Node = new PasswordNode(p6, null, p7Node);
            PasswordNode p5Node = new PasswordNode(p5, null, p6Node);
            PasswordNode p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            if (bst.isValidBST()) {
                System.out.println("isValidBST() says that a tree with only one valid subtree " +
                        "is a valid bst");
                return false;
            }


            // works on valid large tree
            p4Node = new PasswordNode(p4);
            p3Node = new PasswordNode(p3);
            p7Node = new PasswordNode(p7);
            p6Node = new PasswordNode(p6, p7Node, null);
            p5Node = new PasswordNode(p5, null, p6Node);
            p2Node = new PasswordNode(p2, p4Node, p3Node);
            rootNode = new PasswordNode(p, p2Node, p5Node);
            bst.root = rootNode;

            if (!bst.isValidBST())
                return false;

            PasswordNode one = new PasswordNode(p4);
            PasswordNode three = new PasswordNode(p3, one, null);
            PasswordNode two = new PasswordNode(p2, null, three);
            bst.root = two;

            if (bst.isValidBST()) {
                System.out.println("bad bst is valid");
                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * tests the implementation of lookup() method. test cases include scenarios when key is
     * found in the BST and when it is not found it BST
     *
     * @return true if and only if all test scenarios pass, false otherwise
     */
    public static boolean testLookup() {
        {
            PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
            Password pass1 = new Password("PasswDC@01", 101);
            Password pass2 = new Password("PVFJNDVFd@02", 102);
            Password pass3 = new Password("PaVFDNVFrd@03", 103);
            Password pass4 = new Password("PasDFVBord@04", 104);
            Password pass5 = new Password("PVFVd@05", 105);

            test1.addPassword(pass4);
            test1.addPassword(pass5);
            test1.addPassword(pass2);
            test1.addPassword(pass1);

            try { // when the key is found in the BST
                Password actual = test1.lookup(pass4);
                Password expected = pass4;
                if (actual != expected) return false;
            } catch (Exception e) {
                return false;
            }
            try { // when the key is not present in the BST
                Password actual = test1.lookup(pass3);
                Password expected = null;
            } catch (Exception e) {
                return false;
            }
            try { // when the key is found in the BST
                Password actual = test1.lookup(pass5);
                Password expected = pass5;
                if (actual != expected) return false;
            } catch (Exception e) {
                return false;
            }
            test1.addPassword(pass3);
            try { // when the key is found in the BST
                Password actual = test1.lookup(pass3);
                Password expected = pass3;
                if (actual != expected) return false;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /**
     * test the addPassword() method of PasswordStorage class on different BST wih different
     * Attribute criteria. We even try to add the password which is already in the BST which
     * returns an IllegalArgumentException. We also verify if the passwords are added in the
     * correct order based on the comparison criteria.
     *
     * @return true if and only if all the test cases pass, false otherwise
     */
    public static boolean testAddPassword() {

        try {
            PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
            Password pass1 = new Password("Password@01", 101);
            Password pass2 = new Password("Password@02", 102);
            Password pass3 = new Password("Password@03", 103);
            Password pass4 = new Password("Password@04", 104);
            Password pass5 = new Password("Password@05", 105);

            test1.addPassword(pass4);
            test1.addPassword(pass2);
            test1.addPassword(pass5);
            test1.addPassword(pass1);
            test1.addPassword(pass3);

            // let us verify the size of the BST after having added 5 passwords

            int actualSize = test1.size();
            int expectedSize = 5;

            if (actualSize != expectedSize) return false;

        } catch (Exception e) {
            return false;
        }

        // trying to add the same password
        {
            try {
                PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
                Password pass1 = new Password("Password@01", 101);
                Password pass2 = new Password("Password@02", 104);
                Password pass3 = new Password("Password@03", 103);
                Password pass4 = new Password("Password@04", 104);
                Password pass5 = new Password("Password@05", 105);

                test1.addPassword(pass4);
                test1.addPassword(pass2);

                return false;
            } catch (IllegalArgumentException e) {

            } catch (Exception e) {
                return false;
            }
        }

        { // Should throw IllegalArgumentException
            try {
                PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
                Password pass1 = new Password("Password@01", 101);
                Password pass2 = new Password("Password@02", 102);
                Password pass3 = new Password("Password@03", 103);
                Password pass4 = new Password("Password@04", 104);
                Password pass5 = new Password("Password@05", 105);

                test1.addPassword(pass4);
                test1.addPassword(pass3);
                test1.addPassword(pass5);
                test1.addPassword(pass2);
                test1.addPassword(pass1);

                // let us verify the size of the BST after having added 5 passwords

                int actualSize = test1.size();
                int expectedSize = 5;

                if (actualSize != expectedSize) return false;

                if (test1.lookup(pass1) != pass1 ||
                        test1.lookup(pass2) != pass2 ||
                        test1.lookup(pass3) != pass3 || test1.lookup(pass4) != pass4 ||
                        test1.lookup(pass5) != pass5) {
                    return false;
                }

            } catch (Exception e) {
                return false;
            }

        }

        { // adding based on strength rating
            try {
                PasswordStorage test4 = new PasswordStorage(Attribute.STRENGTH_RATING);
                Password pass1 = new Password("!*srd@1", 101);
                Password pass2 = new Password("!P@2B^gc67b3!", 102);
                Password pass3 = new Password("PSword@3", 103);
                Password pass4 = new Password("PA!$^rd@4", 104);
                Password pass5 = new Password("Pasd@5", 105);


                test4.addPassword(pass4);
                test4.addPassword(pass3);
                test4.addPassword(pass5);
                test4.addPassword(pass2);
                test4.addPassword(pass1);

                int actualSize = test4.size();
                int expectedSize = 5;

                if (actualSize != expectedSize) {
                    System.out.println("here1");
                    return false;
                }

                if (test4.lookup(pass1) != pass1 ||
                        test4.lookup(pass2) != pass2 ||
                        test4.lookup(pass3) != pass3 ||
                        test4.lookup(pass4) != pass4 ||
                        test4.lookup(pass5) != pass5) {
                    return false;
                }

                String actual = test4.toString();
                String expected = "!*srd@1(2e12ace9d9871e5d505021b546c64620a7d19b80): 101 [12.25]\n" +
                        "Pasd@5(cdc82fc8ab42825f9b8c8ff1f2b3d4a4dd4b726f): 105 [13.0]\n" +
                        "PSword@3(74edc9a0f77b3c8f5c7444635953517bac93ee25): 103 [15.0]\n" +
                        "PA!$^rd@4(046b5dbacf2fed9e7140b2c446284c09fec4740b): 104 [16.0]\n" +
                        "!P@2B^gc67b3!(59999f1b5a0c489cfcb1915aa919ba32ecc73037): 102 [20.0]\n";

                if (!actual.equals(expected)) {
                    return false;
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        { // catching IllegalArgumentException based o strength rating
            try {
                PasswordStorage test4 = new PasswordStorage(Attribute.STRENGTH_RATING);
                Password pass1 = new Password("!P@2F^gb67b3!", 101);
                Password pass2 = new Password("!P@2B^gc67b3!", 102);
                Password pass3 = new Password("PSword@3", 103);
                Password pass4 = new Password("PA!$^rd@4", 104);
                Password pass5 = new Password("Pasd@5", 105);

                test4.addPassword(pass4);
                test4.addPassword(pass3);
                test4.addPassword(pass5);
                test4.addPassword(pass2);
                test4.addPassword(pass1);


            } catch (IllegalArgumentException ex) {

            } catch (Exception ex) {
                return false;
            }
        }

        { // adding based on Attribute HASHED_PASSWORD
            try {
                PasswordStorage test4 = new PasswordStorage(Attribute.HASHED_PASSWORD);
                Password pass1 = new Password("!*srd@1", 101);
                Password pass2 = new Password("!P@2B^gc67b3!", 102);
                Password pass3 = new Password("PSword@3", 103);
                Password pass4 = new Password("PA!$^rd@4", 104);
                Password pass5 = new Password("Pasd@5", 105);

                test4.addPassword(pass4);
                test4.addPassword(pass3);
                test4.addPassword(pass5);
                test4.addPassword(pass2);
                test4.addPassword(pass1);
            } catch (Exception ex) {
                return false;
            }
        }

        { // Catching IllegalArgumentException while adding based on HASHED_PASSWORD Attribute
            try {
                PasswordStorage test4 = new PasswordStorage(Attribute.HASHED_PASSWORD);
                Password pass1 = new Password("!P@2B^gc67b3!", 101);
                Password pass2 = new Password("!P@2B^gc67b3!", 102);
                Password pass3 = new Password("PSword@3", 103);
                Password pass4 = new Password("PA!$^rd@4", 104);
                Password pass5 = new Password("Pasd@5", 105);

                test4.addPassword(pass4);
                test4.addPassword(pass3);
                test4.addPassword(pass5);
                test4.addPassword(pass2);
                test4.addPassword(pass1);

                return false;

            } catch (IllegalArgumentException ex) {

            } catch (Exception ex) {
                return false;
            }
        }

        return true;
    }

    /**
     * tests the implementation of the removePassword(). Tests scenarios include removing a leaf
     * node, a child with two children, and a node with password that, is not present in the BST.
     *
     * @return true and only true if all the test scenarios pass, false otherwise
     */
    public static boolean testRemovePassword() {
        {
            // removing a leaf Node from left tree
            try {
                PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
                Password pass1 = new Password("Password@01", 101);
                Password pass2 = new Password("Password@02", 102);
                Password pass3 = new Password("Password@03", 103);
                Password pass4 = new Password("Password@04", 104);
                Password pass5 = new Password("Password@05", 105);
                Password pass6 = new Password("Password@06", 106);
                test1.addPassword(pass4);
                test1.addPassword(pass2);
                test1.addPassword(pass5);
                test1.addPassword(pass1);
                test1.addPassword(pass3);
                test1.addPassword(pass6);
                test1.removePassword(pass2);

                String expected = "Password@01(e7a54bbceececd780ff55479aff08ea081a8b026): 101 " +
                        "[18.0]\n" +
                        "Password@03(21dac72cabc43b4b5a22f24743ea53cb73a2ffd8): 103 [18.0]\n" +
                        "Password@04(5c7eb1b1fcdedd76f8d9bfba63d2b45e97aca33d): 104 [18.0]\n" +
                        "Password@05(3c2c6c99417b34e02015bef72624e2cb8219cc63): 105 [18.0]\n" +
                        "Password@06(48ced97cbf397d94332c362e4271441e76ecddad): 106 [18.0]\n";

                String actual = test1.toString();

                if (!expected.equals(actual)) {
                    return false;
                }

                if (test1.size() != 5) {
                    return false;
                }


            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        { // removing a leaf node from the right tree
            try {
                PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
                Password pass1 = new Password("Password@01", 101);
                Password pass2 = new Password("Password@02", 102);
                Password pass3 = new Password("Password@03", 103);
                Password pass4 = new Password("Password@04", 104);
                Password pass5 = new Password("Password@05", 105);
                Password pass6 = new Password("Password@06", 106);

                test1.addPassword(pass4);
                test1.addPassword(pass2);
                test1.addPassword(pass5);
                test1.addPassword(pass1);
                test1.addPassword(pass3);


                test1.removePassword(pass6);

                return false; // should have thrown an exception by this time

            } catch (NoSuchElementException ex) {

            } catch (Exception ex) {
                return false;
            }

        }

        { // removing a node with single child
            try {
                PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
                Password pass1 = new Password("Password@01", 101);
                Password pass2 = new Password("Password@02", 102);
                Password pass3 = new Password("Password@03", 103);
                Password pass4 = new Password("Password@04", 104);
                Password pass5 = new Password("Password@05", 105);
                Password pass6 = new Password("Password@06", 106);

                test1.addPassword(pass4);
                test1.addPassword(pass2);
                test1.addPassword(pass5);
                test1.addPassword(pass1);
                test1.addPassword(pass3);
                test1.addPassword(pass6);

                test1.removePassword(pass5);

                String actual = test1.toString();
                String expected = "Password@01(e7a54bbceececd780ff55479aff08ea081a8b026): 101 " +
                        "[18.0]\n" +
                        "Password@02(e245386fcf88e5a432eca40127d80e67b8e5541f): 102 [18.0]\n" +
                        "Password@03(21dac72cabc43b4b5a22f24743ea53cb73a2ffd8): 103 [18.0]\n" +
                        "Password@04(5c7eb1b1fcdedd76f8d9bfba63d2b45e97aca33d): 104 [18.0]\n" +
                        "Password@06(48ced97cbf397d94332c362e4271441e76ecddad): 106 [18.0]\n";

                if (!actual.equals(expected)) {
                    return false;
                }

            } catch (Exception ex) {
                return false;
            }
        }

        { // removing a node based on a different Attribute
            try {

                PasswordStorage test4 = new PasswordStorage(Attribute.STRENGTH_RATING);
                Password pass1 = new Password("!*ssrd@1", 01);
                Password pass2 = new Password("!2Yb3!", 02);
                Password pass3 = new Password("PAord@3", 2903);
                Password pass4 = new Password("OIhbo!$^rd@4", 204);
                Password pass5 = new Password("!*ssXrd@8", 205);

                test4.addPassword(pass4);
                test4.addPassword(pass3);
                test4.addPassword(pass5);
                test4.addPassword(pass2);
                test4.addPassword(pass1);

                test4.removePassword(pass4);

            } catch (Exception ex) {
                return false;
            }
        }

        {
            // removing a root Node
            try {
                PasswordStorage test1 = new PasswordStorage(Attribute.OCCURRENCE);
                Password pass1 = new Password("Password@01", 101);
                Password pass2 = new Password("Password@02", 102);
                Password pass3 = new Password("Password@03", 103);
                Password pass4 = new Password("Password@04", 104);
                Password pass5 = new Password("Password@05", 105);


                test1.addPassword(pass4);
                test1.addPassword(pass2);
                test1.addPassword(pass5);
                test1.addPassword(pass1);
                test1.addPassword(pass3);

                PasswordNode pass1duplicate = new PasswordNode(pass1, null, null);

                test1.removePassword(pass4);

                String actual = test1.toString();
                System.out.println(actual);
                String expected = "Password@01(e7a54bbceececd780ff55479aff08ea081a8b026): 101 " +
                        "[18.0]\n" +
                        "Password@02(e245386fcf88e5a432eca40127d80e67b8e5541f): 102 [18.0]\n" +
                        "Password@03(21dac72cabc43b4b5a22f24743ea53cb73a2ffd8): 103 [18.0]\n" +
                        "Password@05(3c2c6c99417b34e02015bef72624e2cb8219cc63): 105 [18.0]\n";

                if (!actual.equals(expected)) {
                    return false;
                }

                if (test1.size() != 4) {
                    return false;
                }

            } catch (Exception ex) {
                return false;
            }
        }

        return true;
    }

    /**
     * This is the main method of the tester class
     *
     * @param args unused
     */

    public static void main(String[] args) {
        runAllTests();
    }

    /**
     * This method runs all the tester methods of this class
     *
     * @return true if all the tester methods returns true
     */
    public static boolean runAllTests() {
        boolean compareToPassed = testPasswordCompareTo();
        boolean nodeStatusPassed = testNodeStatusMethods();
        boolean basicMethodsPassed = testBasicPasswordStorageMethods();
        boolean toStringPassed = testToString();
        boolean isValidBSTPassed = testIsValidBST();
        boolean lookupPassed = testLookup();
        boolean addPasswordPassed = testAddPassword();
        boolean removePasswordPassed = testRemovePassword();

        System.out.println("Password compareTo: " + (compareToPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordNode Status Methods: " + (nodeStatusPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage Basic Methods: " +
                (basicMethodsPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage toString: " + (toStringPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage isValidBST: " + (isValidBSTPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage lookup: " + (lookupPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage addPassword: " + (addPasswordPassed ? "PASS" : "FAIL"));
        System.out.println("PasswordStorage removePassword: " +
                (removePasswordPassed ? "PASS" : "FAIL"));

        // AND ANY OTHER ADDITIONAL TEST METHODS YOU MAY WANT TO WRITE!

        return compareToPassed && nodeStatusPassed && basicMethodsPassed && toStringPassed
                && isValidBSTPassed && lookupPassed && addPasswordPassed && removePasswordPassed;
    }

}
