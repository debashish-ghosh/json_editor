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
        String type = mSchemaNode.getString("type");
        if (type != null) {
            return mSchemaNode != null && type.equalsIgnoreCase("array");
        }
        return false;
    }

    public Object getValue() {
        final String nodeType = mSchemaNode.getString("type");
        return mJsonNode;
    }

    public boolean setValue(Object value) {
        String type = mSchemaNode.getString("type");
        if (type.equalsIgnoreCase("string") && value instanceof String
                || type.equalsIgnoreCase("integer") && value instanceof Integer) {
            mJsonNode = value;
            return true;
        }
        return false;
    }
}
