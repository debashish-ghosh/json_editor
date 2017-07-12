/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsoneditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

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
        mDefaultFormatterFactory = jJsonValue.getFormatterFactory();

        NumberFormatter numberFormatter = new NumberFormatter();
        numberFormatter.setAllowsInvalid(false);
        mNumberFormatterFactory = new DefaultFormatterFactory(numberFormatter);
        mDefaultFormatterFactory = jJsonValue.getFormatterFactory();

        jJsonTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jJsonTree.addTreeSelectionListener((TreeSelectionEvent) -> {
            jButtonEditVal.setText("Edit");
            jJsonValue.setEditable(false);
            jJsonValue.setValue(null);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jJsonTree.getLastSelectedPathComponent();

            if (node != null && node.isLeaf()) {
                mCurrentData = (TreeNodeData) node.getUserObject();
                if (mCurrentData.isInt()) {
                    jJsonValue.setFormatterFactory(mNumberFormatterFactory);
                } else {
                    jJsonValue.setFormatterFactory(mDefaultFormatterFactory);
                }

                jJsonValue.setValue(mCurrentData.getValue());
                jButtonEditVal.setEnabled(true);
            } else {
                mCurrentData = null;
                jJsonValue.setFormatterFactory(mDefaultFormatterFactory);
                jButtonEditVal.setEnabled(false);
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
        jScrollPane = new javax.swing.JScrollPane();
        jJsonTree = new javax.swing.JTree();
        jButtonNewFile = new javax.swing.JButton();
        jButtonOpenJsonData = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonCloseDialog = new javax.swing.JButton();
        jLabelJsonData = new javax.swing.JLabel();
        jLabelJsonValue = new javax.swing.JLabel();
        jButtonEditVal = new javax.swing.JButton();
        jJsonValue = new javax.swing.JFormattedTextField();

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
                jButtonOpenSchemaActionPerformed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jJsonTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jJsonTree.setRootVisible(false);
        jScrollPane.setViewportView(jJsonTree);

        jButtonNewFile.setText("New File...");
        jButtonNewFile.setEnabled(false);

        jButtonOpenJsonData.setText("Open");
        jButtonOpenJsonData.setEnabled(false);
        jButtonOpenJsonData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOpenJsonDataActionPerformed(evt);
            }
        });

        jButtonSave.setText("Save");
        jButtonSave.setActionCommand("");
        jButtonSave.setEnabled(false);

        jButtonCloseDialog.setText("Close");
        jButtonCloseDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseDialogActionPerformed(evt);
            }
        });

        jLabelJsonData.setText("JSON Data");

        jLabelJsonValue.setText("Value");

        jButtonEditVal.setText("Edit");
        jButtonEditVal.setEnabled(false);
        jButtonEditVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditValActionPerformed(evt);
            }
        });

        jJsonValue.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSchemaFilePath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOpenSchema, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelLoadSchema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelJsonData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jJsonValue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEditVal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelJsonValue)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNewFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonOpenJsonData, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addComponent(jButtonCloseDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jButtonCloseDialog))
                .addGap(9, 9, 9)
                .addComponent(jLabelJsonData)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelJsonValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEditVal)
                    .addComponent(jJsonValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOpenSchemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenSchemaActionPerformed
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
                jButtonSave.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_jButtonOpenSchemaActionPerformed

    private void jButtonCloseDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseDialogActionPerformed
        dispose();
        System.exit(0);
    }//GEN-LAST:event_jButtonCloseDialogActionPerformed

    private void jButtonOpenJsonDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOpenJsonDataActionPerformed
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
                        DefaultTreeModel model = (DefaultTreeModel) jJsonTree.getModel();
                        mJsonEditor.inflateJson((DefaultMutableTreeNode) model.getRoot());
                        model.reload();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(JsonEditorDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
    }//GEN-LAST:event_jButtonOpenJsonDataActionPerformed

    private void jButtonEditValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditValActionPerformed
        final String[] mode = {"Edit", "Done"};
        int index = (jButtonEditVal.getText().equals(mode[1])) ? 1 : 0;
        jButtonEditVal.setText(mode[index ^ 1]);
        jJsonValue.setEditable(index == 0);
    }//GEN-LAST:event_jButtonEditValActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            JsonEditorDialog dialog = new JsonEditorDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonCloseDialog;
    private javax.swing.JButton jButtonEditVal;
    private javax.swing.JButton jButtonNewFile;
    private javax.swing.JButton jButtonOpenJsonData;
    private javax.swing.JButton jButtonOpenSchema;
    private javax.swing.JButton jButtonSave;
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
    private JFormattedTextField.AbstractFormatterFactory mDefaultFormatterFactory, mNumberFormatterFactory;

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
