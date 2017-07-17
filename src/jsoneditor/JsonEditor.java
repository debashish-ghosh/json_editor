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

    public void writeToFile() {
        writeToFile(mJsonFile);
    }

    public void writeToFile(File file) {
        try (Writer writer = new FileWriter(file)) {
            // 4 spaces for indentation, and 0 indentation for root node
            mJsonObj.write(writer, 4, 0);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JsonEditor.class.getName()).log(Level.SEVERE, null, ex);
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

        if (schema.has("$ref")) {
            String[] tokens = schema.getString("$ref").split("/");
            if (tokens.length > 2 && tokens[0].equals("#") && tokens[1].equalsIgnoreCase("definitions")) {
                schema = mJsonSchemaDefs;
                for (int i = 2; i < tokens.length; i++) {
                    if (schema.has(tokens[i])) {
                        tag = tokens[i];
                        schema = schema.getJSONObject(tokens[i]);
                    }
                }
            }
        }

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
        } else if (nodeType.equalsIgnoreCase("string")) {
        } else if (nodeType.equalsIgnoreCase("integer")) {
        }
    }

}
