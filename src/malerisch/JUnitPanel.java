package malerisch;

import burp.IBurpExtenderCallbacks;
import burp.IHttpRequestResponse;
import burp.ISessionHandlingAction;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class JUnitPanel extends javax.swing.JPanel {

    final IBurpExtenderCallbacks callbacks;

    public JUnitPanel(final IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        addjunit = new javax.swing.JButton();
        executejunit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        removejunit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        junittable = new javax.swing.JTable();
        registerjunit = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("JUnit Integration");

        addjunit.setText("Add");
        addjunit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addjunitActionPerformed(evt);
            }
        });

        executejunit.setText("Execute");
        executejunit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executejunitActionPerformed(evt);
            }
        });

        jLabel6.setFont(jLabel6.getFont());
        jLabel6.setText("Example: C:\\test\\Test1.class - Class Path: file://C:/ - Full class name: test.Test1");

        removejunit.setText("Remove");
        removejunit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removejunitActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        junittable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {"file://C:/", "test.Test1", null, null}
                },
                new String[]{
                    "Class Path", "Full Class Name", "Description Name", "Registered"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean[]{
                true, true, true, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(junittable);

        registerjunit.setText("Register");
        registerjunit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerjunitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel4)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(jLabel6)))
                                                .addComponent(addjunit)
                                                .addComponent(removejunit)
                                                .addComponent(registerjunit)
                                                .addComponent(executejunit))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(77, 77, 77)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)))
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(11, 11, 11)
                        .addComponent(jLabel5)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6)
                        .addGap(6, 6, 6)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(addjunit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(removejunit)
                                        .addGap(4, 4, 4)
                                        .addComponent(registerjunit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(executejunit))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addContainerGap(240, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private void addjunitActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new javax.swing.JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(JUnitPanel.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            file.getAbsolutePath();
            DefaultTableModel model = (DefaultTableModel) junittable.getModel();
            model.addRow(new Object[]{file.getAbsolutePath(), "Specify class path - test.Test1", "Enter description", false});
        }
    }

    private void removejunitActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) junittable.getModel();
        int[] rows = junittable.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            model.removeRow(rows[i] - i);
        }
    }

    private void executejunitActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) junittable.getModel();
        int selectedRow = junittable.getSelectedRow();
        String rowValue = junittable.getValueAt(selectedRow, 1).toString();
        String aValue = junittable.getValueAt(selectedRow, 0).toString();
        try {
            Class classy = loadJUnit(aValue, rowValue);
            Runnable MyThread = new ThreadJUnit(classy);
            new Thread(MyThread).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private void registerjunitActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) junittable.getModel();
        int selectedRow = junittable.getSelectedRow();
        String rowValue = junittable.getValueAt(selectedRow, 1).toString();
        String aValue = junittable.getValueAt(selectedRow, 0).toString();
        String desc = junittable.getValueAt(selectedRow, 2).toString();
        try {
            Class classy = loadJUnit(aValue, rowValue);
            final InterfaceTest hello = new InterfaceTest(classy, desc);
            try {
                callbacks.registerSessionHandlingAction(hello);
                junittable.setValueAt(true, selectedRow, 3);
            } catch (Throwable e) {
                System.out.println("Stack trace for Registration: " + e);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify
    public static javax.swing.JButton addjunit;
    public static javax.swing.JButton executejunit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    public static javax.swing.JTable junittable;
    public static javax.swing.JButton registerjunit;
    public static javax.swing.JButton removejunit;

    public class ThreadJUnit implements Runnable {

        private final Class classy;

        public ThreadJUnit(Class classy) {
            this.classy = classy;
        }

        @Override
        public void run() {
            System.out.println("JUnit thread started");
            System.out.println("Loaded class: " + classy);
            JUnitCore junit = new JUnitCore();
            Result result = null;
            try {
                result = junit.run(classy);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            if (result != null && !result.wasSuccessful()) {
                List<Failure> failures = result.getFailures();

                int i;
                for (i = 0; i < result.getFailureCount(); i++) {
                    Failure e = failures.get(i);
                    System.out.println(e.getException());
                    System.out.println(e.getDescription());
                    System.out.println(e.getException());
                    System.out.println(e.getMessage());
                    System.out.println(e.getTrace());
                }
            } else {
                System.out.println("JUnit executed with success");
            }
        }
    }

    public Class loadJUnit(String junitfile, String classToBeLoaded) throws MalformedURLException {

        Class classy = null;

        String url2 = "file://" + junitfile.replace("\\", "/");
        URL url = new URL(url2);
        URLClassLoader loader = new URLClassLoader(new URL[]{url}, this.getClass().getClassLoader());
        try {
            classy = loader.loadClass(classToBeLoaded);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classy;
    }

    final static class InterfaceTest implements ISessionHandlingAction {

        private static Class classy;
        private static String desc;

        public InterfaceTest(Class classy, String desc) {
            InterfaceTest.classy = classy;
            InterfaceTest.desc = desc;
        }

        @Override
        public void performAction(IHttpRequestResponse currentRequest, IHttpRequestResponse[] macroItems) {
            Result result = JUnitCore.runClasses(classy);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());
        }

        @Override
        public String getActionName() {
            return desc;
        }
    }
}
