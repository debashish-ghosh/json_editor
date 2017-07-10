/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoneditor;

/**
 *
 * @author debashish.ghosh
 */
public class TreeNodeData {

    private Object mJsonNode;
    private NodeType mNodeType;
    private String mNodeName;

    public TreeNodeData(Object json, NodeType nodeType, String name) {
        mJsonNode = json;
        mNodeType = nodeType;
        mNodeName = name;
    }

    @Override
    public String toString() {
        return mNodeName;
    }

    public String getValue() {
        switch (mNodeType) {
            case INTEGER:
                break;
            case STRING:
                break;
        }
        return null;
    }

    public static enum NodeType {
        UNKNOWN,
        OBJECT,
        ARRAY,
        INTEGER,
        STRING
    }
}
