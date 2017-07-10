/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoneditor;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import javax.swing.tree.DefaultMutableTreeNode;
import jsoneditor.TreeNodeData.NodeType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @version 1.0
 * @author debashish.ghosh
 */
public class JsonEditor {

    /**
     * Initialize a new JsonEditor object with a JSON Schema
     *
     * @param jsonSchema the JSON schema
     * @throws FileNotFoundException
     */
    public JsonEditor(File jsonSchema) throws FileNotFoundException {
        mJsonSchema = jsonSchema;
        loadSchema();
    }

    /**
     * Sets JSON data for editing
     *
     * @param jsonData
     * @throws FileNotFoundException
     */
    public void setJsonFile(File jsonData) throws FileNotFoundException {
        mJsonFile = jsonData;
        JSONTokener x = new JSONTokener(new FileInputStream(mJsonFile));
        mJsonObj = new JSONObject(x);
    }

    /**
     * Sets JSON schema file to define the structure of JSON data
     *
     * @param jsonSchema schema file
     * @throws FileNotFoundException
     */
    public void setJsonSchema(File jsonSchema) throws FileNotFoundException {
        mJsonSchema = jsonSchema;
        loadSchema();
    }

    /**
     * Inflates the JSON layout defined by the schema in the tree view
     *
     * @param node root node of the tree view
     */
    public void inflateJson(DefaultMutableTreeNode node) {
        inflate(mJsonSchemaObj, mJsonObj, node);
    }

    private void loadSchema() throws FileNotFoundException, JSONException {
        JSONTokener x = new JSONTokener(new FileInputStream(mJsonSchema));
        mJsonSchemaObj = new JSONObject(x);
    }

    private void inflate(JSONObject schema, Object json, DefaultMutableTreeNode node) {
        String nodeType = (String) schema.get("type");
        if (nodeType.equalsIgnoreCase("object")) {
            JSONObject prop = (JSONObject) schema.get("properties");
            Set<String> keySet = prop.keySet();
            for (String key : keySet) {
                if (!((JSONObject) json).isNull(key)) {
                    Object jsonVal = ((JSONObject) json).get(key);
                    JSONObject schemaVal = (JSONObject) prop.get(key);

                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new TreeNodeData(jsonVal, toEnum((String) schemaVal.get("type")), key));
                    node.add(childNode);
                    inflate(schemaVal, jsonVal, childNode);
                }
            }
        } else if (nodeType.equalsIgnoreCase("array")) {
            JSONObject items = (JSONObject) schema.get("items");
            for (int i = 0; i < ((JSONArray) json).length(); i++) {
                Object jsonVal = ((JSONArray) json).get(i);
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new TreeNodeData(jsonVal, toEnum((String) items.get("type")), "" + (i + 1)));
                node.add(childNode);
                inflate(items, jsonVal, childNode);
            }
        } else if (nodeType.equalsIgnoreCase("string")) {
        } else if (nodeType.equalsIgnoreCase("integer")) {
        }
    }

    private File mJsonFile = null;
    private File mJsonSchema = null;
    private JSONObject mJsonSchemaObj = null;
    private JSONObject mJsonObj = null;

    private static NodeType toEnum(String type) {
        if (type.equalsIgnoreCase("onject")) {
            return NodeType.OBJECT;
        }
        if (type.equalsIgnoreCase("array")) {
            return NodeType.ARRAY;
        }
        if (type.equalsIgnoreCase("integer")) {
            return NodeType.INTEGER;
        }
        if (type.equalsIgnoreCase("string")) {
            return NodeType.STRING;
        }

        return NodeType.UNKNOWN;
    }
}
