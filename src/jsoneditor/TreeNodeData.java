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
    private final Object mJsonNode;
    private String mNodeTag;

    public TreeNodeData(JSONObject schemaNode, Object jsonNode, String tag) {
        mSchemaNode = schemaNode;
        mJsonNode = jsonNode;
        mNodeTag = tag;
    }
    
    public void setTag(String tag) {
        mNodeTag = tag;
    }

    @Override
    public String toString() {
        return mNodeTag;
    }
    
    public boolean isInt() {
        return mSchemaNode != null && mSchemaNode.getString("type").equalsIgnoreCase("integer");
    }

    public String getValue() {
        final String nodeType = mSchemaNode.getString("type");
        if (nodeType.equalsIgnoreCase("string")) {
            return ((String) mJsonNode);
        } else if (nodeType.equalsIgnoreCase("integer")) {
            return ("" + (int) mJsonNode);
        }
        return null;
    }
}
