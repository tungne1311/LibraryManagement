/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package librarymanagement;

/**
 *
 * @author ADMIN
 */
import java.awt.Color;
import javax.swing.JComboBox;
import java.awt.*;
import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.*;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDayChooser;
import librarymanagement.Book;

public class BookManagement extends javax.swing.JFrame {

    /**
     * Creates new form BookManagement
     */
    BookModify bookModify = new BookModify();
    private Connection conn;
    private Container cont;
    private ButtonGroup gr;
    private DefaultComboBoxModel cbbModel;
    private DefaultTableModel tblModel;
    private JScrollPane scrollPane;
    private JComboBox cbbFindBy, cbbPublishYear, cbbSort, cbbType;
    private JTable table;
    private JTabbedPane tabbedPane;
    private JRadioButton rdoLecturer, rdoStudent;
  
    
    public BookManagement() {
        initComponents();
        cont = this.getContentPane();
        
        Color col= new Color(252,250,238);
        getContentPane().setBackground(col);
        
        String[] items1 = {"2024","2023","2022","2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009"}; // First JComboBox items
        cbbPublishYear = new JComboBox<>(items1);
        cbbPublishYear.setBounds(323, 140, 105, 30); // Position and size of the first JComboBox
        add(cbbPublishYear); // Add the first JComboBox to JFrame

        String[] items2 = {"Giáo trình", "Tài liệu tham khảo", "Luận án", "Luận văn", "Sách bài tập"}; // Second JComboBox items
        cbbType = new JComboBox<>(items2);
        cbbType.setBounds(525, 140, 105, 30); // Position and size of the second JComboBox
        add(cbbType); // Add the second JComboBox to JFrame
        
       String[] items3 = {"Sắp xếp", "Tăng dần theo số trang", "Giảm dần theo số trang"};
       cbbSort = new JComboBox<>(items3);
       cbbSort.setBounds(12,250,120,30);
       add(cbbSort);
       
       String[] items4 = {"Tất cả", "Tên sách", "Tác giả", "Ngôn ngữ", "Năm xuất bản", "Thể loại", "NXB"};
       cbbFindBy = new JComboBox<>(items4);
       cbbFindBy.setBounds(200,250,120,30);
       add(cbbFindBy);
       
       
       scrollPane = new JScrollPane();
       scrollPane.setBounds(10, 330, 800, 362);
       table = new JTable();
       table.setCellSelectionEnabled(true);
       scrollPane.add(table);
       scrollPane.setViewportView(table);
       cont.add(scrollPane);
       
       findAllBook();
       setupListeners();
       
    }
    private void setupListeners() {
        cbbSort.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sortAZPageNo();
				
			}
	});

    }
    
    public void sortAZPageNo()
	{
                System.out.println("sortAZPageNo được gọi");
		Vector<Book> bookList = new Vector();
		if(cbbSort.getSelectedItem().equals("Tăng dần theo số trang"))
		{
			bookList = bookModify.sortAZPageNo("call sp_sortAZPageNo");
		}
		else
		if(cbbSort.getSelectedItem().equals("Giảm dần theo số trang"))
		{
			bookList = bookModify.sortAZPageNo("call sp_sortZAPageNo");
		}
                String[] columnNames = {"Mã sách","Tên sách","Số trang","Ngôn ngữ","Giá trị","Số lượng còn","Năm xuất bản","Thể loại","Tác giả","Nhà xuất bản"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
                System.out.println("Số lượng sách trả về: " + bookList.size());
		for(Book book: bookList)
		{
			tblModel.addRow(new Object[] {book.getBookId(),book.getBookName(),book.getPageNo(),book.getLanguage(),book.getPrice(),
					book.getAmount(),book.getPublishYear(),book.getType(),book.getAuthor(),book.getPublisher()});
		}
		
	}
    
    public void findAllBook()
    {
        Vector<Book> bookList = bookModify.getBookList();
	String[] columnNames = {"Mã sách", "Tên sách", "Số trang", "Ngôn ngữ", "Giá trị", "Số lượng còn", "Năm xuất bản", "Thể loại", "Tác giả", "Nhà xuất bản"}; 
	tblModel = new DefaultTableModel();
	tblModel.setColumnIdentifiers(columnNames);
	table.setModel(tblModel);	
	for(Book book: bookList)
	{
            tblModel.addRow(new Object[] {book.getBookId(),book.getBookName(),book.getPageNo(),book.getLanguage(),book.getPrice(),
											book.getAmount(),book.getPublishYear(),book.getType(),book.getAuthor(),book.getPublisher()});
        }
    }  

    public void findBookBy(String sql, String parameter)
    {
	Vector<Book> bookList = bookModify.findBookBy(sql, parameter);
	String[] columnNames = {"Mã sách", "Tên sách", "Số trang", "Ngôn ngữ", "Giá trị", "Số lượng còn", "Năm xuất bản", "Thể loại", " Tác giả", " Nhà xuất bản"}; 
	tblModel = new DefaultTableModel();
	tblModel.setColumnIdentifiers(columnNames);
	table.setModel(tblModel);
		
	for(Book book: bookList)
	{
            tblModel.addRow(new Object[] {book.getBookId(),book.getBookName(),book.getPageNo(),book.getLanguage(),book.getPrice(),
											book.getAmount(),book.getPublishYear(),book.getType(),book.getAuthor(),book.getPublisher()});
	}
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblBookName = new javax.swing.JLabel();
        tfBookName = new javax.swing.JTextField();
        lblPageNo = new javax.swing.JLabel();
        tfPageNo = new javax.swing.JTextField();
        lblLanguage = new javax.swing.JLabel();
        tfLanguage = new javax.swing.JTextField();
        lblPrice = new javax.swing.JLabel();
        tfPrice = new javax.swing.JTextField();
        lblAmount = new javax.swing.JLabel();
        tfAmount = new javax.swing.JTextField();
        lblPublishYear = new javax.swing.JLabel();
        lblType = new javax.swing.JLabel();
        lblAuthor = new javax.swing.JLabel();
        tfAuthor = new javax.swing.JTextField();
        lblPublisher = new javax.swing.JLabel();
        tfPublisher = new javax.swing.JTextField();
        tfSearchBook = new javax.swing.JTextField();
        btnSearchBook = new javax.swing.JButton();
        btnAddBook = new javax.swing.JButton();
        btnUpdateBook = new javax.swing.JButton();
        btnDeleteBook = new javax.swing.JButton();
        btnResetBook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(251, 231, 195));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Quay lại");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(251, 231, 195));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Quản lý sách");
        jLabel1.setOpaque(true);

        lblBookName.setBackground(new java.awt.Color(232, 190, 191));
        lblBookName.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBookName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBookName.setText("Tên sách");
        lblBookName.setOpaque(true);

        lblPageNo.setBackground(new java.awt.Color(252, 250, 238));
        lblPageNo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPageNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPageNo.setText("Số trang");
        lblPageNo.setOpaque(true);

        tfPageNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPageNoActionPerformed(evt);
            }
        });

        lblLanguage.setBackground(new java.awt.Color(252, 250, 238));
        lblLanguage.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLanguage.setText("Ngôn ngữ");
        lblLanguage.setOpaque(true);

        tfLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfLanguageActionPerformed(evt);
            }
        });

        lblPrice.setBackground(new java.awt.Color(252, 250, 238));
        lblPrice.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPrice.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrice.setText("Giá trị");
        lblPrice.setOpaque(true);

        tfPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPriceActionPerformed(evt);
            }
        });

        lblAmount.setBackground(new java.awt.Color(252, 250, 238));
        lblAmount.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAmount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAmount.setText("Số lượng");
        lblAmount.setOpaque(true);

        lblPublishYear.setBackground(new java.awt.Color(252, 250, 238));
        lblPublishYear.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPublishYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPublishYear.setText("Năm XB");
        lblPublishYear.setOpaque(true);

        lblType.setBackground(new java.awt.Color(252, 250, 238));
        lblType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblType.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblType.setText("Thể loại");
        lblType.setOpaque(true);

        lblAuthor.setBackground(new java.awt.Color(252, 250, 238));
        lblAuthor.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAuthor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAuthor.setText("Tác giả");
        lblAuthor.setOpaque(true);

        tfAuthor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAuthorActionPerformed(evt);
            }
        });

        lblPublisher.setBackground(new java.awt.Color(252, 250, 238));
        lblPublisher.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPublisher.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPublisher.setText("NXB");
        lblPublisher.setOpaque(true);

        tfPublisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPublisherActionPerformed(evt);
            }
        });

        btnSearchBook.setBackground(new java.awt.Color(251, 231, 195));
        btnSearchBook.setText("Tìm kiếm");
        btnSearchBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchBookActionPerformed(evt);
            }
        });

        btnAddBook.setBackground(new java.awt.Color(173, 23, 28));
        btnAddBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAddBook.setForeground(new java.awt.Color(255, 255, 255));
        btnAddBook.setText("Thêm sách");
        btnAddBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddBookActionPerformed(evt);
            }
        });

        btnUpdateBook.setBackground(new java.awt.Color(251, 231, 195));
        btnUpdateBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnUpdateBook.setText("Cập nhật");
        btnUpdateBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateBookActionPerformed(evt);
            }
        });

        btnDeleteBook.setBackground(new java.awt.Color(251, 231, 195));
        btnDeleteBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteBook.setText("Xóa sách");
        btnDeleteBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteBookActionPerformed(evt);
            }
        });

        btnResetBook.setBackground(new java.awt.Color(251, 231, 195));
        btnResetBook.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnResetBook.setText("Nhập lại");
        btnResetBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBookName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPageNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAuthor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAmount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(230, 230, 230)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfBookName, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(tfAmount))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPublishYear, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(126, 126, 126)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnAddBook)
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfAuthor, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                    .addComponent(tfPageNo))
                                .addGap(4, 4, 4)
                                .addComponent(lblPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(tfSearchBook, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSearchBook, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnDeleteBook)
                                    .addGap(35, 35, 35)
                                    .addComponent(btnUpdateBook)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnResetBook)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(tfLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfBookName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPublishYear, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(lblType, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfPublisher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfSearchBook, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSearchBook)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(tfAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPageNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddBook, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateBook, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteBook, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnResetBook, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(441, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tfPageNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPageNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPageNoActionPerformed

    private void tfLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfLanguageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfLanguageActionPerformed

    private void tfPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPriceActionPerformed

    private void tfPublisherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPublisherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfPublisherActionPerformed

    private void btnSearchBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchBookActionPerformed
        // TODO add your handling code here:
                String sql;
		String parameter;
		if(tfSearchBook.getText().equals(""))
		{
			findAllBook();
		} else
		{
			if(cbbFindBy.getSelectedItem().equals("Tên sách"))
			{
				sql = "call sp_findBookByName(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql,parameter);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Tác giả"))
			{
				sql = "call sp_findBookByAuthor(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql,parameter);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Ngôn ngữ"))
			{
				sql = "call sp_findBookByLanguage(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql,parameter);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Năm xuất bản"))
			{
				sql = "call sp_findBookByPublishYear(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql,parameter);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Thể loại"))
			{
				sql = "call sp_findBookByType(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql,parameter);
			} 
			else if(cbbFindBy.getSelectedItem().equals("NXB"))
			{
				sql = "call sp_findBookByPublisher(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql,parameter);
			} 
			else if(cbbFindBy.getSelectedItem().equals("Tất cả"))
			{
				sql = "call sp_findByAllBook(?)";
				parameter = tfSearchBook.getText();
				findBookBy(sql, parameter);
			}
		}	
    }//GEN-LAST:event_btnSearchBookActionPerformed

    private void btnDeleteBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteBookActionPerformed
        // TODO add your handling code here:
        
        try
	{
            bookModify.deleteBook(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
            findAllBook();
	} catch(IndexOutOfBoundsException e)
	{
            JOptionPane.showInternalMessageDialog(cont,"Vui lòng chọn hàng cần xóa");
	}
    }//GEN-LAST:event_btnDeleteBookActionPerformed

    private void btnAddBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddBookActionPerformed
        Book book = new Book();
		if(tfBookName.getText().length()==0  || tfAmount.getText().length()==0)
		{
			JOptionPane.showInternalMessageDialog(cont,"Tên sách và số lượng sách không được để trống");
		} 
		else
		{
			book.setBookName(tfBookName.getText());
			if(tfPageNo.getText().length()==0)
			{
				book.setPageNo(null);
			}
			else
			{
				book.setPageNo(tfPageNo.getText());
			}
			
			if(tfLanguage.getText().length()==0)
			{
				book.setLanguage(null);
			}
			else
			{
				book.setLanguage(tfLanguage.getText());
			}
			book.setPrice(Integer.parseInt(tfPrice.getText()));
			book.setAmount(Integer.parseInt(tfAmount.getText()));
			book.setPublishYear(String.valueOf(cbbPublishYear.getSelectedItem()));
			book.setType(String.valueOf(cbbType.getSelectedItem()));
			if(tfAuthor.getText().length()==0)
			{
				book.setAuthor(null);
			}
			else
			{
				book.setAuthor(tfAuthor.getText());
			}
			
			if(tfPublisher.getText().length()==0)
			{
				book.setPublisher(null);
			}
			else
			{
				book.setPublisher(tfPublisher.getText());
			}
			bookModify.addBook(book);
			findAllBook();
		}
    }//GEN-LAST:event_btnAddBookActionPerformed

    private void btnUpdateBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateBookActionPerformed
        // TODO add your handling code here:
                Book book = new Book();
		if(tfBookName.getText().length()==0  || tfAmount.getText().length()==0)
		{
			JOptionPane.showInternalMessageDialog(cont,"Tên sách và số lượng sách không được để trống");
		} 
		else
		{
			book.setBookName(tfBookName.getText());
			if(tfPageNo.getText().length()==0)
			{
				book.setPageNo(null);
			}
			else
			{
				book.setPageNo(tfPageNo.getText());
			}
			
			if(tfLanguage.getText().length()==0)
			{
				book.setLanguage(null);
			}
			else
			{
				book.setLanguage(tfLanguage.getText());
			}
			book.setPrice(Integer.parseInt(tfPrice.getText()));
			book.setAmount(Integer.parseInt(tfAmount.getText()));
			book.setPublishYear(String.valueOf(cbbPublishYear.getSelectedItem()));
			book.setType(String.valueOf(cbbType.getSelectedItem()));
			if(tfAuthor.getText().length()==0)
			{
				book.setAuthor(null);
			}
			else
			{
				book.setAuthor(tfAuthor.getText());
			}
			
			if(tfPublisher.getText().length()==0)
			{
				book.setPublisher(null);
			}
			else
			{
				book.setPublisher(tfPublisher.getText());
			}
			book.setBookId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
			bookModify.updateBook(book);
			findAllBook();
		}
    }//GEN-LAST:event_btnUpdateBookActionPerformed

    private void btnResetBookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetBookActionPerformed
        tfBookName.setText(null);
	tfPageNo.setText(null);
	tfLanguage.setText(null);
	tfPrice.setText("0");
	tfAmount.setText(null);
	cbbPublishYear.setSelectedIndex(0);
	cbbType.setSelectedIndex(0);
	tfAuthor.setText(null);
	tfPublisher.setText(null);
    }//GEN-LAST:event_btnResetBookActionPerformed

    private void tfAuthorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAuthorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAuthorActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BookManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddBook;
    private javax.swing.JButton btnDeleteBook;
    private javax.swing.JButton btnResetBook;
    private javax.swing.JButton btnSearchBook;
    private javax.swing.JButton btnUpdateBook;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblAuthor;
    private javax.swing.JLabel lblBookName;
    private javax.swing.JLabel lblLanguage;
    private javax.swing.JLabel lblPageNo;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblPublishYear;
    private javax.swing.JLabel lblPublisher;
    private javax.swing.JLabel lblType;
    private javax.swing.JTextField tfAmount;
    private javax.swing.JTextField tfAuthor;
    private javax.swing.JTextField tfBookName;
    private javax.swing.JTextField tfLanguage;
    private javax.swing.JTextField tfPageNo;
    private javax.swing.JTextField tfPrice;
    private javax.swing.JTextField tfPublisher;
    private javax.swing.JTextField tfSearchBook;
    // End of variables declaration//GEN-END:variables
}
