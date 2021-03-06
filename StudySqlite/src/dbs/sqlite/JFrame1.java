/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbs.sqlite;

import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.table.*;

import dbi.sqlite.*;

/**
 *
 * @author Emiliarge
 */
public class JFrame1 extends javax.swing.JFrame {
	
	Connection conn;
	
	/**
	 * Creates new form JFrame1
	 */
	public JFrame1() {
		initComponents();
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jLabel1 = new javax.swing.JLabel();
		jComboBox1 = new javax.swing.JComboBox();
		jButton1 = new javax.swing.JButton();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowOpened(java.awt.event.WindowEvent evt) {
				formWindowOpened(evt);
			}
		});
		
		jTable1.setModel(new javax.swing.table.DefaultTableModel(new Object[][]{{}, {}, {}, {}}, new String[]{
		
		}));
		jScrollPane1.setViewportView(jTable1);
		
		jLabel1.setText("Select table:");
		
		jButton1.setText("OK");
		jButton1.setName("jButtonOK"); // NOI18N
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton1MouseClicked(evt);
			}
		});
		
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
												Short.MAX_VALUE)
										.addGroup(
												layout.createSequentialGroup()
														.addComponent(jLabel1)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
																122, javax.swing.GroupLayout.PREFERRED_SIZE)
														.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(jButton1).addGap(0, 141, Short.MAX_VALUE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel1)
												.addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jButton1))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
								.addContainerGap()));
		
		pack();
	}// </editor-fold>//GEN-END:initComponents
	
	private void formWindowOpened(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowOpened
		try {
			// connect to db (file test.db must lay in the project dir)
			// NOTE: it will be created if not exists
			// Class.forName("org.sqlite.JDBC");
			// conn = DriverManager.getConnection("jdbc:sqlite:test.db");
			//
			// // create table "Table 1" if not exists
			// Statement stmt = conn.createStatement();
			// String sql =
			// "CREATE TABLE IF NOT EXISTS [Table 1] (id INTEGER PRIMARY KEY AUTOINCREMENT, 'text column' TEXT, 'int column' INTEGER);";
			// stmt.executeUpdate(sql);
			// stmt.close();
			SqliteDB sqlitedb = new SqliteDB("tst4Java.db");
			
			conn = sqlitedb.getConnection();
			// get all tables in db and them names to combobox
			ResultSet rs = null;
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getTables(null, null, null, new String[]{"TABLE"});
			
			while (rs.next()) {
				String tableName = rs.getString("TABLE_NAME");
				jComboBox1.addItem(tableName);
			}
			jComboBox1.updateUI();
		} catch (Exception e) {
			
		}
	}// GEN-LAST:event_formWindowOpened
	
	private void Select() {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM [" + jComboBox1.getSelectedItem() + "];");
			
			// get columns info
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			// for changing column and row model
			DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
			
			// clear existing columns
			tm.setColumnCount(0);
			
			// add specified columns to table
			for (int i = 1; i <= columnCount; i++) {
				tm.addColumn(rsmd.getColumnName(i));
			}
			
			// clear existing rows
			tm.setRowCount(0);
			
			// add rows to table
			while (rs.next()) {
				String[] a = new String[columnCount];
				for (int i = 0; i < columnCount; i++) {
					a[i] = rs.getString(i + 1);
				}
				tm.addRow(a);
			}
			tm.fireTableDataChanged();
			
			rs.close();
			stmt.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex, ex.getMessage(), WIDTH, null);
		}
	}
	
	private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jButton1MouseClicked
		// connect selected table
		Select();
	}// GEN-LAST:event_jButton1MouseClicked
	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(JFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JFrame1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		
		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame1 frame1 = new JFrame1();
				frame1.setLocationRelativeTo(null);
				frame1.setVisible(true);
			}
		});
	}
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JComboBox jComboBox1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable jTable1;
	// End of variables declaration//GEN-END:variables
}
