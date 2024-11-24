/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package librarymanagement;

/**
 *
 * @author ADMIN
 */
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;


public class BookManagementUI extends javax.swing.JFrame {
    BookModify bookModify = new BookModify();
    //Dashboard dashboard = new Dashboard(); 
    private Connection conn;
    private Container cont;
    private ButtonGroup gr;
    private DefaultComboBoxModel cbbModel;
    private DefaultTableModel tblModel;
    private JTextField tfSearchBook, tfBookName, tfPageNo, tfLanguage, tfPrice, tfAmount, tfAuthor, tfPublisher;
	
    private JButton btnSearchBook, btnAddBook, btnResetBook, btnUpdateBook, btnDeleteBook,btnBackToDashboard;
						
    private JLabel lblBookName, lblPageNo, lblPrice, lblAmount, lblPublishYear,lblType, lblAuthor, lblPublisher, lblLanguage, lblTitle;
    private JScrollPane scrollPane;
					
    private JComboBox cbbFindBy, cbbPublishYear, cbbSort, cbbType;
    private JTable table;
    private JPanel pnlBookManagement; 
 
    
    public BookManagementUI() {
        cont = this.getContentPane();
	cont.setLayout(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 1200, 720);
        cont.setBackground(new Color(247, 247, 247));
	
	tblModel = new DefaultTableModel();
        table = new JTable(tblModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 341, 1166, 362);
        cont.add(scrollPane);	
	pnlBookManagement = new JPanel();
        pnlBookManagement.setBounds(0, 0, 1166, 310);
	pnlBookManagement.setLayout(null);
        
        lblTitle = new JLabel("QUẢN LÝ SÁCH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitle.setBackground(new Color(243,77,110));
        lblTitle.setForeground(new Color(255, 255, 255)); 
        lblTitle.setOpaque(true);
        
        JLabel bookImage = new JLabel();
        try {
            // Tải và thay đổi kích thước hình ảnh
            ImageIcon imageIcon = resizeImage("src/images/book_img.jpg", 606, 200); // Đường dẫn và kích thước mong muốn
            bookImage.setIcon(imageIcon);
        } catch (Exception e) {
            System.out.println("Không thể tải hình ảnh: " + e.getMessage());
        }

        // Đặt vị trí và kích thước cho JLabel
        bookImage.setBounds(568, 70, 535, 150);
        pnlBookManagement.add(bookImage);

        
	lblBookName = new JLabel("Tên sách:");
	lblPageNo = new JLabel("Số trang:");
	lblLanguage = new JLabel("Ngôn ngữ:");
	lblPrice = new JLabel("Giá trị:");
	lblAmount = new JLabel("Số lượng:");
	lblPublishYear = new JLabel("Năm xuất bản:");
	lblType = new JLabel("Thể loại:");
	lblAuthor = new JLabel("Tác giả:");
	lblPublisher = new JLabel("Nhà xuất bản:");
	
	tfBookName = new JTextField(null);
	tfPageNo = new JTextField(null);
	tfLanguage = new JTextField(null);
	tfPrice = new JTextField();
	tfPrice.setText("0");
	tfAmount = new JTextField(null);
	tfSearchBook = new JTextField();
        tfAuthor = new JTextField(null);
	tfPublisher = new JTextField(null);

	cbbPublishYear = new JComboBox();
	cbbType = new JComboBox();
	cbbSort = new JComboBox();
        cbbFindBy = new JComboBox();
        
        btnAddBook = new JButton("Thêm sách");
	btnDeleteBook = new JButton("Xóa sách");
	btnUpdateBook = new JButton("Cập nhật");
	btnResetBook = new JButton("Nhập lại");
	btnSearchBook = new JButton("Tìm kiếm");
        btnBackToDashboard = new JButton("Quay lại");
        
	lblTitle.setBounds(720, 10, 200, 40);
	lblBookName.setBounds(10, 77, 73, 26);
	lblPageNo.setBounds(10, 113, 73, 26);
	lblLanguage.setBounds(10, 149, 73, 26);
	lblPrice.setBounds(10, 185, 81, 26);
	lblAmount.setBounds(10, 221, 73, 19);
	lblPublishYear.setBounds(245, 81, 81, 19);
	lblType.setBounds(245, 113, 81, 26);
	lblAuthor.setBounds(245, 149, 86, 26);
	lblPublisher.setBounds(245, 185, 86, 26);
	tfBookName.setBounds(70, 81, 123, 19);
	tfPageNo.setBounds(70, 117, 123, 19);
	tfLanguage.setBounds(70, 153, 123, 19);
	tfPrice.setBounds(70, 189, 123, 19);
	tfAmount.setBounds(70, 221, 123, 19);
	tfAuthor.setBounds(336, 153, 123, 19);
	tfPublisher.setBounds(336, 189, 123, 19);
	btnAddBook.setBounds(10, 275, 103, 26);
	cbbType.setBounds(336, 115, 123, 23);
	btnDeleteBook.setBounds(136, 275, 96, 26);
	btnUpdateBook.setBounds(252, 275, 99, 26);
	cbbSort.setBounds(750, 235, 191, 30);
	cbbFindBy.setBounds(570, 235, 113, 30);
	tfSearchBook.setBounds(570, 275, 470, 30);
	btnSearchBook.setBounds(1048, 274, 103, 30);
	btnResetBook.setBounds(373, 275, 86, 26);
	cbbPublishYear.setBounds(336, 80, 123, 20);
        btnBackToDashboard.setBounds(10,10,100,30);
        
		
	cbbFindBy.setMaximumRowCount(7);
	cbbPublishYear.setMaximumRowCount(13);
		
	cbbPublishYear.setModel(new DefaultComboBoxModel(new String[] {"2024","2023","2022","2021", "2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009"}));
	cbbType.setModel(new DefaultComboBoxModel(new String[] {"Giáo trình", "Tài liệu tham khảo", "Luận án", "Luận văn", "Sách bài tập"}));
	cbbSort.setModel(new DefaultComboBoxModel(new String[] {"Sắp xếp", "Tăng dần theo số trang", "Giảm dần theo số trang"}));
	cbbFindBy.setModel(new DefaultComboBoxModel(new String[] {"Tất cả", "Tên sách", "Tác giả", "Ngôn ngữ", "Năm xuất bản", "Thể loại", "NXB"}));
        
        btnAddBook.setBackground(new Color(243, 215, 202));
        btnAddBook.setForeground(new Color(0, 0, 0));
        btnDeleteBook.setBackground(new Color(243, 215, 202));
        btnDeleteBook.setForeground(new Color(0, 0, 0));
        btnUpdateBook.setBackground(new Color(243, 215, 202));
        btnUpdateBook.setForeground(new Color(0, 0, 0));
        btnResetBook.setBackground(new Color(243, 215, 202));
        btnResetBook.setForeground(new Color(0, 0, 0));
        btnSearchBook.setBackground(new Color(243, 215, 202));
        btnSearchBook.setForeground(new Color(0, 0, 0));
        btnBackToDashboard.setBackground(new Color(255, 234, 197));
        btnBackToDashboard.setForeground(new Color(0, 0, 0));
        
        lblBookName.setBackground(new Color(243, 215, 202));
        lblPageNo.setBackground(new Color(243, 215, 202));
        lblLanguage.setBackground(new Color(243, 215, 202));
        lblPrice.setBackground(new Color(243, 215, 202));
        lblAmount.setBackground(new Color(243, 215, 202));
        lblPublishYear.setBackground(new Color(243, 215, 202));
        lblType.setBackground(new Color(243, 215, 202));
        lblAuthor.setBackground(new Color(243, 215, 202));
        lblPublisher.setBackground(new Color(243, 215, 202));
        
        cbbPublishYear.setBackground(new Color(247, 247, 247));
        cbbType.setBackground(new Color(247, 247, 247));
        cbbSort.setBackground(new Color(247, 247, 247));
        cbbFindBy.setBackground(new Color(247, 247, 247));
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(173, 23, 28)); 
        tableHeader.setForeground(Color.WHITE); 
        
        pnlBookManagement.add(lblTitle);
	pnlBookManagement.add(lblBookName);
	pnlBookManagement.add(lblPageNo);
	pnlBookManagement.add(lblLanguage);
	pnlBookManagement.add(lblPrice);
	pnlBookManagement.add(lblAmount);
	pnlBookManagement.add(lblPublishYear);
	pnlBookManagement.add(lblType);
	pnlBookManagement.add(lblAuthor);
	pnlBookManagement.add(lblPublisher);
	pnlBookManagement.add(tfBookName);
	pnlBookManagement.add(tfPageNo);
	pnlBookManagement.add(tfLanguage);		
	pnlBookManagement.add(tfAmount);
	pnlBookManagement.add(tfPrice);
	pnlBookManagement.add(cbbPublishYear);
	pnlBookManagement.add(tfAuthor);
	pnlBookManagement.add(tfPublisher);
	pnlBookManagement.add(btnAddBook);
	pnlBookManagement.add(cbbType);
	pnlBookManagement.add(btnDeleteBook);
	pnlBookManagement.add(btnUpdateBook);
	pnlBookManagement.add(cbbSort);
	pnlBookManagement.add(cbbFindBy);
	pnlBookManagement.add(tfSearchBook);
	pnlBookManagement.add(btnSearchBook);
	pnlBookManagement.add(btnResetBook);
        pnlBookManagement.add(btnBackToDashboard);
	
        cbbSort.addActionListener(new ActionListener() {	
            @Override
            public void actionPerformed(ActionEvent e) {
                    sortAZPageNo();		
		}
	});
        btnResetBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {		
		btnResetBookActionPerformed(evt);
            }
	});
		
	btnSearchBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
		btnSearchBookActionPerformed(evt);
        }});
	btnDeleteBook.addActionListener(new ActionListener() 
	{
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
		btnDeleteBookActionPerformed(evt);	}
        });
		
	btnUpdateBook.addActionListener(new ActionListener()
	{
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
		btnUpdateBookActionPerformed(evt);
            }
	});
	btnAddBook.addActionListener(new ActionListener()
	{
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                btnAddBookActionPerformed(evt);
            }
	});
        
       
        btnBackToDashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryDashboard().setVisible(true);
                dispose();  // Ẩn cửa sổ LibraryManagement hiện tại
            }
        });
        
        findAllBook();
        cont.add(pnlBookManagement);
        pnlBookManagement.setBackground(new Color(247, 247, 247));
	setVisible(true);	
    }
    
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu không tải được
        }
    }
    
    private void btnSearchBookActionPerformed(ActionEvent evt)
    {
	String sql;
	String parameter;
	if(tfSearchBook.getText().equals(""))
	{
           findAllBook();
        } 
        else
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
    }
    //private void btnBackToDashboardActionPerformed(ActionEvent evt) {
    //    dashboard.setVisible(true);  // Show the Dashboard
        //this.dispose();  // Close the current BookManagementUI window
   // }
    private void btnResetBookActionPerformed(ActionEvent evt)
    {
        tfBookName.setText(null);
        tfPageNo.setText(null);
        tfLanguage.setText(null);
        tfPrice.setText("0");
        tfAmount.setText(null);
        cbbPublishYear.setSelectedIndex(0);
        cbbType.setSelectedIndex(0);
        tfAuthor.setText(null);
        tfPublisher.setText(null);
    }
	
    private void btnDeleteBookActionPerformed(ActionEvent evt)
    {
        try
        {
            bookModify.deleteBook(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
            findAllBook();
	}catch(IndexOutOfBoundsException e)
	{
            JOptionPane.showInternalMessageDialog(cont,"Vui lòng chọn hàng cần xóa");
	}
    }
	
    private void btnUpdateBookActionPerformed(ActionEvent evt)
    {
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
		
		
	}
	
	private void btnAddBookActionPerformed(ActionEvent evt)
	{
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
	}
        public void sortAZPageNo()
	{
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
		for(Book book: bookList)
		{
			tblModel.addRow(new Object[] {book.getBookId(),book.getBookName(),book.getPageNo(),book.getLanguage(),book.getPrice(),
					book.getAmount(),book.getPublishYear(),book.getType(),book.getAuthor(),book.getPublisher()});
		}
		
	}
	//find information
	
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
	
	
}