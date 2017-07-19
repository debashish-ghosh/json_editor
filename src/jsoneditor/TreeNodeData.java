/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoneditor;

import org.json.JSONObject;

/**
 *
 * @author debashish.ghosh
 */
public class TreeNodeData {

    private final JSONObject mSchemaNode;
    private Object mJsonNode;
    private String mNodeTag;

    public TreeNodeData(JSONObject schemaNode, Object jsonNode, String tag) {
        mSchemaNode = schemaNode;
        mJsonNode = jsonNode;
        mNodeTag = tag;
    }

    public String getTag() {
        return mNodeTag;
    }

    public void setTag(String tag) {
        mNodeTag = tag;
    }
    
    public JSONObject getSchema() {
        return mSchemaNode;
    }

    @Override
    public String toString() {
        return mNodeTag;
    }

    /**
     * Query for leaf nodes whether they are integer or string
     *
     * @return
     */
    public boolean isInt() {
        String type = mSchemaNode.getString("type");
        if (type != null) {
            return mSchemaNode != null && type.equalsIgnoreCase("integer");
        }
        return false;
    }

    /**
     * Query for branch nodes whether they are JSONArray or JSONObject
     *
     * @return
     */
    public boolean isArray() {
        if (mSchemaNode.has("type")) {
            String type = mSchemaNode.getString("type");
            if (type != null) {
                return mSchemaNode != null && type.equalsIgnoreCase("array");
            }
        }
        return false;
    }
    
    public boolean isPrimitiveData() {
        if (mSchemaNode.has("type")) {
            String type = mSchemaNode.getString("type");
            if (type != null) {
                return mSchemaNode != null && (type.equalsIgnoreCase("string") || type.equalsIgnoreCase("integer"));
            }
        }
        return false;
    }

    public Object getJsonData() {
        final String nodeType = mSchemaNode.getString("type");
        return mJsonNode;
    }

    /**
     * Checks whether a child node can be added to this node
     *
     * @return true if a child node can be added
     */
    public boolean canAddChild() {
        if (isArray()) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether this node can be removed from the tree
     *
     * @param parentNode the parent node of this node
     * @return true if this node can removed from parentNode
     */
    public boolean canBeRemoved(TreeNodeData parentNode) {
        if (parentNode != null && parentNode.isArray()) {
            return true;
        }
        return false;
    }

    public boolean setValue(Object value) {
        String type = mSchemaNode.getString("type");
        if (type.equalsIgnoreCase("string") && value instanceof String
                || type.equalsIgnoreCase("integer") && (value instanceof Long || value instanceof Integer)) {
            mJsonNode = value;
            return true;
        }
        return false;
    }
}
