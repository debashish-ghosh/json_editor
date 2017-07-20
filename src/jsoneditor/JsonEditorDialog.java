/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoneditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import org.json.JSONObject;

/**
 *
 * @author debashish.ghosh
 */
public class JsonEditorDialog extends javax.swing.JDialog {

    /**
     * Creates new form JsonEditorDialog
     *
     * @param parent
     * @param modal
     */
    public JsonEditorDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        NumberFormatter numberFormatter = new NumberFormatter();
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setFormat(new DecimalFormat("#"));
        numberFormatter.setOverwriteMode(false);
        mNumberFormatterFactory = new DefaultFormatterFactory(numberFormatter);

        mDefaultFormatterFactory = jJsonValue.getFormatterFactory();
        ((DefaultFormatter) jJsonValue.getFormatter()).setOverwriteMode(false);

        jJsonTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jJsonTree.addTreeSelectionListener((TreeSelectionEvent) -> {
            jButtonEditValue.setText("Edit");
            jJsonValue.setEditable(false);
            jJsonValue.setValue(null);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jJsonTree.getLastSelectedPathComponent();

            if (node != null && node.isLeaf()) {
                mCurrentData = (TreeNodeData) node.getUserObject();
                if (mCurrentData.isPrimitiveData()) {
                    if (mCurrentData.isInt()) {
                        jJsonValue.setFormatterFactory(mNumberFormatterFactory);
                    } else {
                        jJsonValue.setFormatterFactory(mDefaultFormatterFactory);
                    }

                    jJsonValue.setValue(mCurrentData.getJsonData());
                    jButtonEditValue.setEnabled(true);
                    jButtonAddNode.setEnabled(false);
                } else {
                    mCurrentData = null;
                    jJsonValue.setFormatterFactory(mDefaultFormatterFactory);
                    jButtonEditValue.setEnabled(false);
                    jButtonAddNode.setEnabled(true);
                }
            } else {
                mCurrentData = null;
                jJsonValue.setFormatterFactory(mDefaultFormatterFactory);
                jButtonEditValue.setEnabled(false);
                jButtonAddNode.setEnabled(true);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelLoadSchema = new javax.swing.JLabel();
        jSchemaFilePath = new javax.swing.JTextField();
        jButtonOpenSchema = new javax.swing.JButton();
        jButtonNewFile = new javax.swing.JButton();
        jButtonOpenJsonData = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonSaveAs = new javax.swing.JButton();
        jButtonAddNode = new javax.swing.JButton();
        jButtonRemoveNode = new javax.swing.JButton();
        jLabelJsonData = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        jJsonTree = new javax.swing.JTree();
        jLabelJsonValue = new javax.swing.JLabel();
        jJsonValue = new javax.swing.JFormattedTextField();
        jButtonEditValue = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(TITLE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(10, 10));
        setResizable(false);

        jLabelLoadSchema.setText("Load JSON Schema");

        jSchemaFilePath.setEditable(false);
        jSchemaFilePath.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jButtonOpenSchema.setText("...");
        jButtonOpenSchema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenSchema_ActionPerformed(evt);
            }
        });

        jButtonNewFile.setText("New File...");
        jButtonNewFile.setEnabled(false);
        jButtonNewFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewFile_ActionPerformed(evt);
            }
        });

        jButtonOpenJsonData.setText("Open");
        jButtonOpenJsonData.setEnabled(false);
        jButtonOpenJsonData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenJsonData_ActionPerformed(evt);
            }
        });

        jButtonSave.setText("Save");
        jButtonSave.setActionCommand("");
        jButtonSave.setEnabled(false);
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Save_ActionPerformed(evt);
            }
        });

        jButtonSaveAs.setText("Save As...");
        jButtonSaveAs.setEnabled(false);
        jButtonSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAs_ActionPerformed(evt);
            }
        });

        jButtonAddNode.setText("Add Node");
        jButtonAddNode.setEnabled(false);
        jButtonAddNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNode_ActionPerformed(evt);
            }
        });

        jButtonRemoveNode.setText("Remove Node");
        jButtonRemoveNode.setEnabled(false);

        jLabelJsonData.setText("JSON Data");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jJsonTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jJsonTree.setRootVisible(false);
        jScrollPane.setViewportView(jJsonTree);

        jLabelJsonValue.setText("Value");

        jJsonValue.setEditable(false);
        jJsonValue.setFormatterFactory(new DefaultFormatterFactory(new DefaultFormatter()));

        jButtonEditValue.setText("Edit");
        jButtonEditValue.setEnabled(false);
        jButtonEditValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditValue_ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLoadSchema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jJsonValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditValue, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSchemaFilePath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonOpenSchema, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelJsonValue)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonNewFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOpenJsonData, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSaveAs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jButtonAddNode, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveNode))
                    .addComponent(jLabelJsonData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelLoadSchema)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSchemaFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonOpenSchema))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewFile)
                    .addComponent(jButtonOpenJsonData)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonSaveAs)
                    .addComponent(jButtonRemoveNode)
                    .addComponent(jButtonAddNode))
                .addGap(18, 18, 18)
                .addComponent(jLabelJsonData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelJsonValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEditValue)
                    .addComponent(jJsonValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void OpenSchema_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenSchema_ActionPerformed
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        if (jJsonSchemaChooser == null) {
            jJsonSchemaChooser = new JFileChooser();
            jJsonSchemaChooser.setAcceptAllFileFilterUsed(false);
            jJsonSchemaChooser.setFileFilter(new JsonFileFilter());
            jJsonSchemaChooser.setCurrentDirectory(new File(prefs.get("last_dir2", jJsonSchemaChooser.getCurrentDirectory().getAbsolutePath())));
        }

        switch (jJsonSchemaChooser.showOpenDialog(this)) {
            case JFileChooser.APPROVE_OPTION:
                File file = jJsonSchemaChooser.getSelectedFile();
                prefs.put("last_dir2", file.getParent());
                try {
                    jSchemaFilePath.setText(file.getCanonicalPath());
                } catch (IOException ex) {
                    Logger.getLogger(JsonEditorDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (mJsonEditor == null) {
                        mJsonEditor = new JsonEditor(file);
                    } else {
                        mJsonEditor.setJsonSchema(file);
                    }
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JsonEditorDialog.class.getName()).log(Level.SEVERE, null, ex);
                }

                jButtonNewFile.setEnabled(true);
                jButtonOpenJsonData.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_OpenSchema_ActionPerformed

    private void OpenJsonData_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenJsonData_ActionPerformed
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        if (jJsonFileChooser == null) {
            jJsonFileChooser = new JFileChooser();
            jJsonFileChooser.setAcceptAllFileFilterUsed(false);
            jJsonFileChooser.setFileFilter(new JsonFileFilter());
            jJsonFileChooser.setCurrentDirectory(new File(prefs.get("last_dir", jJsonFileChooser.getCurrentDirectory().getAbsolutePath())));
        }

        switch (jJsonFileChooser.showOpenDialog(this)) {
            case JFileChooser.APPROVE_OPTION:
                File file = jJsonFileChooser.getSelectedFile();
                prefs.put("last_dir", file.getParent());
                try {
                    setTitle(TITLE + " - " + file.getCanonicalPath());
                    if (mJsonEditor != null) {
                        mJsonEditor.setJsonFile(file);
                        jButtonSave.setEnabled(true);
                        jButtonSaveAs.setEnabled(true);
                        DefaultTreeModel model = (DefaultTreeModel) jJsonTree.getModel();
                        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
                        if (rootNode != null) {
                            rootNode.removeAllChildren();
                        }
                        mJsonEditor.inflateJson(rootNode);
                        model.reload();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JsonEditorDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }//GEN-LAST:event_OpenJsonData_ActionPerformed

    private void EditValue_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditValue_ActionPerformed
        final String[] mode = {"Edit", "Done"};
        int index = (jButtonEditValue.getText().equals(mode[1])) ? 1 : 0;
        jButtonEditValue.setText(mode[index ^ 1]);
        if (index == 0) {       // starting edits
            jJsonValue.setEditable(true);
            int textLen = jJsonValue.getText().length();
            if (textLen != 0) {
                jJsonValue.setCaretPosition(textLen - 1);
            }
        } else {        // finishing edits
            jJsonValue.setEditable(false);
            TreePath selectionPath = jJsonTree.getSelectionPath();
            if (selectionPath != null) {
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectionPath.getParentPath().getLastPathComponent();
                TreeNodeData parentNodeData = (TreeNodeData) parentNode.getUserObject();

                // updating the parent node
                JSONObject obj = (JSONObject) parentNodeData.getJsonData();
                obj.put(mCurrentData.getTag(), jJsonValue.getValue());
                mCurrentData.setValue(jJsonValue.getValue());
            }
        }
    }//GEN-LAST:event_EditValue_ActionPerformed

    private void Save_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Save_ActionPerformed
        mJsonEditor.writeToFile();
    }//GEN-LAST:event_Save_ActionPerformed

    private void SaveAs_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAs_ActionPerformed
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        if (jJsonSaveAsFileChooser == null) {
            jJsonSaveAsFileChooser = new JFileChooser();
            jJsonSaveAsFileChooser.setDialogTitle("Save As...");
            jJsonSaveAsFileChooser.setAcceptAllFileFilterUsed(false);
            jJsonSaveAsFileChooser.setFileFilter(new JsonFileFilter());
            jJsonSaveAsFileChooser.setCurrentDirectory(new File(prefs.get("last_dir_save_as", jJsonSaveAsFileChooser.getCurrentDirectory().getAbsolutePath())));
        }

        switch (jJsonSaveAsFileChooser.showSaveDialog(this)) {
            case JFileChooser.APPROVE_OPTION:
                File file = jJsonSaveAsFileChooser.getSelectedFile();
                prefs.put("last_dir_save_as", file.getParent());
                mJsonEditor.writeToFile(file);
                jButtonSave.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_SaveAs_ActionPerformed

    private void AddNode_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNode_ActionPerformed
        DefaultTreeModel model = (DefaultTreeModel) jJsonTree.getModel();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jJsonTree.getLastSelectedPathComponent();
        mJsonEditor.createChildTree(model, node);
    }//GEN-LAST:event_AddNode_ActionPerformed

    private void NewFile_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewFile_ActionPerformed
        DefaultTreeModel model = (DefaultTreeModel) jJsonTree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
        if (rootNode != null) {
            rootNode.removeAllChildren();
        }
        mJsonEditor.makeNewTree(rootNode);
        model.reload();

        jButtonSaveAs.setEnabled(true);
    }//GEN-LAST:event_NewFile_ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            JsonEditorDialog dialog = new JsonEditorDialog(null, true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddNode;
    private javax.swing.JButton jButtonEditValue;
    private javax.swing.JButton jButtonNewFile;
    private javax.swing.JButton jButtonOpenJsonData;
    private javax.swing.JButton jButtonOpenSchema;
    private javax.swing.JButton jButtonRemoveNode;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSaveAs;
    private javax.swing.JTree jJsonTree;
    private javax.swing.JFormattedTextField jJsonValue;
    private javax.swing.JLabel jLabelJsonData;
    private javax.swing.JLabel jLabelJsonValue;
    private javax.swing.JLabel jLabelLoadSchema;
    private javax.swing.JTextField jSchemaFilePath;
    private javax.swing.JScrollPane jScrollPane;
    // End of variables declaration//GEN-END:variables

    private JsonEditor mJsonEditor = null;
    private static final String TITLE = "JSON Editor";
    private TreeNodeData mCurrentData = null;
    private JFileChooser jJsonSchemaChooser = null;
    private JFileChooser jJsonFileChooser = null;
    private JFileChooser jJsonSaveAsFileChooser = null;
    private final JFormattedTextField.AbstractFormatterFactory mDefaultFormatterFactory, mNumberFormatterFactory;

    private static class JsonFileFilter extends FileFilter {

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) {
                return true;
            }

            String filename = f.getName();
            int i = filename.lastIndexOf(".");
            String extension = filename.substring(i + 1);
            return ("json".equalsIgnoreCase(extension));
        }

        @Override
        public String getDescription() {
            return "JSON Document";
        }
    }

}
