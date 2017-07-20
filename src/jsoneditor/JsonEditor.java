/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoneditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @version 1.0
 * @author debashish.ghosh
 */
public class JsonEditor {

    private File mJsonFile = null;
    private File mJsonSchema = null;
    private JSONObject mJsonSchemaObj = null;
    private JSONObject mJsonSchemaDefs = null;
    private JSONObject mJsonObj = null;

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
        if (mJsonFile != jsonData) {
            mJsonFile = jsonData;
            JSONTokener x = new JSONTokener(new FileInputStream(mJsonFile));
            mJsonObj = new JSONObject(x);
        }
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

    public void writeToFile() {
        writeToFile(mJsonFile);
    }

    public void writeToFile(File file) {
        if (file != null) {
            try (Writer writer = new FileWriter(file)) {
                // 4 spaces for indentation, and 0 indentation for root node
                mJsonObj.write(writer, 4, 0);
                if (mJsonFile != file) {
                    mJsonFile = file;
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Inflates the JSON layout defined by the schema in the tree view
     *
     * @param node root node of the tree view
     */
    public void inflateJson(DefaultMutableTreeNode node) {
        inflate(mJsonSchemaObj, mJsonObj, "JSON Root", node);
    }

    public void makeNewTree(DefaultMutableTreeNode node) {
        if (mJsonFile != null) {
            mJsonFile = null;
        }
        mJsonObj = new JSONObject();
        inflateJson(node);
    }

    private void loadSchema() throws FileNotFoundException, JSONException {
        JSONTokener x = new JSONTokener(new FileInputStream(mJsonSchema));
        mJsonSchemaObj = new JSONObject(x);

        if (mJsonSchemaObj.has("definitions")) {
            mJsonSchemaDefs = mJsonSchemaObj.getJSONObject("definitions");
        } else {
            mJsonSchemaDefs = null;
        }
    }

    private void inflate(JSONObject schema, Object json, String tag, DefaultMutableTreeNode node) {

        schema = resolveRefs(schema);

        if (schema != null) {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new TreeNodeData(schema, json, tag));
            node.add(childNode);

            String nodeType = schema.getString("type");
            if (nodeType.equalsIgnoreCase("object")) {
                JSONObject prop = schema.getJSONObject("properties");
                Set<String> keySet = prop.keySet();
                for (String key : keySet) {
                    if (!((JSONObject) json).isNull(key)) {
                        JSONObject schemaVal = prop.getJSONObject(key);
                        Object jsonVal = ((JSONObject) json).get(key);
                        inflate(schemaVal, jsonVal, key, childNode);
                    }
                }
            } else if (nodeType.equalsIgnoreCase("array")) {
                JSONObject items = schema.getJSONObject("items");
                for (int i = 0; i < ((JSONArray) json).length(); i++) {
                    Object jsonVal = ((JSONArray) json).get(i);
                    inflate(items, jsonVal, "" + (i + 1), childNode);
                }
            }
        }
    }

    public void createChildTree(DefaultTreeModel model, DefaultMutableTreeNode parent) {
        TreeNodeData treeNodeData = (TreeNodeData) parent.getUserObject();
        JSONObject parentSchema = treeNodeData.getSchema();

        String parentNodeType = parentSchema.getString("type");
        if (parentNodeType.equalsIgnoreCase("object")) {
            JSONObject jsonData = (JSONObject) treeNodeData.getJsonData();

            JSONObject prop = parentSchema.getJSONObject("properties");
            Set<String> keySet = prop.keySet();
            for (String key : keySet) {
                if (!jsonData.has(key)) {
                    JSONObject value = prop.getJSONObject(key);
                    Object nodeData = null;

                    value = resolveRefs(value);

                    if (value != null) {
                        String valueType = value.getString("type");
                        if (valueType.equalsIgnoreCase("integer")) {
                            nodeData = 0;
                        } else if (valueType.equalsIgnoreCase("string")) {
                            nodeData = "";
                        } else if (valueType.equalsIgnoreCase("object")) {
                            nodeData = new JSONObject();
                        } else if (valueType.equalsIgnoreCase("array")) {
                            nodeData = new JSONArray();
                        }

                        jsonData.put(key, nodeData);
                        DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(new TreeNodeData(value, nodeData, key));
                        model.insertNodeInto(childTreeNode, parent, parent.getChildCount());
                    }
                }
            }
        } else if (parentNodeType.equalsIgnoreCase("array")) {
            JSONArray jsonData = (JSONArray) treeNodeData.getJsonData();

            JSONObject items = parentSchema.getJSONObject("items");
            String itemType = items.getString("type");

            Object childJsonNode = null;
            if (itemType.equalsIgnoreCase("array")) {
                childJsonNode = new JSONArray();
            } else if (itemType.equalsIgnoreCase("object")) {
                childJsonNode = new JSONObject();
            }
            jsonData.put(childJsonNode);

            int tag = parent.getChildCount();
            DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(new TreeNodeData(items, childJsonNode, "" + (tag + 1)));
            model.insertNodeInto(childTreeNode, parent, tag);

            createChildTree(model, childTreeNode);
        }
    }

    public void removeNode(DefaultTreeModel model, DefaultMutableTreeNode node) {
        if (node != null && model != null) {
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

            if (parent != null) {
                TreeNodeData parentTreeNodeData = (TreeNodeData) parent.getUserObject();
                JSONObject parentSchema = resolveRefs(parentTreeNodeData.getSchema());
                String parentNodeType = parentSchema.getString("type");
                if (parentNodeType.equalsIgnoreCase("object")) {
                    JSONObject parentNodeData = (JSONObject) parentTreeNodeData.getJsonData();
                    String key = ((TreeNodeData) node.getUserObject()).getTag();
                    parentNodeData.remove(key);
                    model.removeNodeFromParent(node);
                }/* else if (parentNodeType.equalsIgnoreCase("array")) {
                    JSONArray parentNodeData = (JSONArray) parentTreeNodeData.getJsonData();
                }*/
            }
        }
    }

    private JSONObject resolveRefs(JSONObject schema) {
        if (schema != null && schema.has("$ref")) {
            if (mJsonSchemaDefs == null) {
                return null;
            }
            String[] tokens = schema.getString("$ref").split("/");
            if (tokens.length > 2 && tokens[0].equals("#") && tokens[1].equalsIgnoreCase("definitions")) {
                schema = mJsonSchemaDefs;
                for (int i = 2; i < tokens.length; i++) {
                    if (schema.has(tokens[i])) {
                        schema = schema.getJSONObject(tokens[i]);
                    } else {
                        return null;
                    }
                }
            }
        }

        return schema;
    }
}
