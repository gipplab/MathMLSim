package com.formulasearchengine.mathmlsim.similarity.node;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * General tests of the object itself.
 *
 * @author Vincent Stange
 */
public class MathNodeTest {

    @Test
    public void isLeaf() throws Exception {
        MathNode mathNode = new MathNode();
        assertThat(mathNode.isLeaf(), is(true));
        // straight forward - a node with a child is not a leaf.
        mathNode.addChild(new MathNode());
        assertThat(mathNode.isLeaf(), is(false));
    }

    @Test
    public void getChildren() throws Exception {
        MathNode mathNode = new MathNode();
        MathNode firstChild = new MathNode("C", null);
        MathNode secondChild = new MathNode("A", null);
        MathNode thirdChild = new MathNode("B", null);
        mathNode.addChild(firstChild);
        mathNode.addChild(secondChild);
        mathNode.addChild(thirdChild);

        // pre-test
        assertThat(mathNode.getChildren(), hasItems(firstChild, secondChild, thirdChild));
        // a remove() should not be applied to inner list
        mathNode.getChildren().remove(0);
        // post-test
        assertThat(mathNode.getChildren(), hasItems(firstChild, secondChild, thirdChild));
    }

}