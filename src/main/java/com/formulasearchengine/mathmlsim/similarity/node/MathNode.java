package com.formulasearchengine.mathmlsim.similarity.node;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.*;

/**
 * This object represents the node of a mathematic expression tree (MET).
 * <br/>
 * It is to simplify the tree comparison and avoid unnecessary complexity.
 * This object should not be dependent on the former document and any
 * namespace-dependent attributes should be ignored.
 * <br/>
 * If you start with this object, it is usually the root of a MET.
 *
 * @author Vincent Stange
 */
public class MathNode {

    /**
     * tag-name of the node (element name, e.g. <apply> = "apply")
     */
    private String name = null;

    /**
     * text value of a node
     */
    private String value = "";

    /**
     * specific id attribute of the original node
     */
    private String id = null;

    /**
     * all attributes of node
     */
    private Map<String, String> attributes = new HashMap<>();

    /**
     * Are children nodes order sensitive?
     */
    private boolean orderSensitive = true;

    private int depth = 0;

    private int maxDepth = 0;

    private boolean marked = false;

    private boolean abstractNode = false;

    public MathNode() {
        // empty constructur
    }

    public MathNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * all children in order via an ArrayList
     */
    private ArrayList<MathNode> children = new ArrayList<>();

    public void setAttributes(NamedNodeMap attributes) {
        if (attributes == null)
            return;
        // extract all attributes into a simple map
        int numAttrs = attributes.getLength();
        for (int i = 0; i < numAttrs; i++) {
            Node attr = attributes.item(i);
            String attrName = attr.getNodeName();
            if ("id".equals(attrName)) {
                setId(attr.getNodeValue());
            }
            this.attributes.put(attrName, attr.getNodeValue());
        }
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public boolean isOrderSensitive() {
        return orderSensitive;
    }

    public List<MathNode> getChildren() {
        return new ArrayList<>(children);
    }

    public void addChild(MathNode child) {
        if (child == null)
            return;
        if (child.getName() != null && "plus times".contains(child.getName())) {
            orderSensitive = false;
        }
        this.children.add(child);
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked() {
        this.marked = true;
    }

    public boolean isAbstractNode() {
        return abstractNode;
    }

    public MathNode setAbstractNode() {
        this.abstractNode = true;
        return this;
    }

    public List<MathNode> getLeafs() {
        if (isLeaf()) {
            return Collections.singletonList(this);
        }
        List<MathNode> unterlings = new ArrayList<>();
        // if this is an apply node, the first child is an
        // operation-node not a constant or number leaf
        int startIdx = "apply".equals(getName()) ? 1 : 0;
        for(int i = startIdx; i < children.size(); i ++) {
            unterlings.addAll(children.get(i).getLeafs());
        }
        return unterlings;
    }

    public int getMaxDepth() {
        if (isLeaf()) {
            // set and return at the same time
            return maxDepth = depth;
        }
        if (maxDepth == 0) {
            maxDepth = children.stream()
                    .max((c1, c2) -> Math.max(c1.getMaxDepth(), c2.getMaxDepth()))
                    .get().getDepth();
        }
        return maxDepth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MathNode mathNode = (MathNode) o;

        if (name != null ? !name.equals(mathNode.name) : mathNode.name != null) return false;
        if (isAbstractNode()) {
            // this is a abstractNode node, value check is not applied
            return true;
        } else {
            return value != null ? value.equals(mathNode.value) : mathNode.value == null;
        }

    }

    @Override
    public int hashCode() {
        // the missing check on isAbstractNode is not a bug, but a deliberate
        // decision for the coverage computation (we use hashes there)
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", name, value);
    }

}
