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
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.table.*;

public class ReaderManagementUI extends JFrame {
    private Connection conn;
    private Container cont;
    private ButtonGroup gr;
    private DefaultTableModel tblModel;
    private JTextField tfReaderName, tfIdentityCard, tfPhoneNumber, tfSurname, tfSearchReader;
    private JButton btnAddReader, btnUpdateReader, btnDeleteReader, btnResetReader, btnSearchReader, btnBack;
    private JTable table;
    private JPanel pnlReaderManagement; 
    private JRadioButton rdoLecturer, rdoStudent;

    private ReaderModify readerModify = new ReaderModify();

    public ReaderManagementUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 720);
        cont = this.getContentPane();
        cont.setLayout(null);
        
        // Nút Quay lại
        btnBack = new JButton("Quay lại");
        btnBack.setBounds(10, 10, 100, 30);
        
        btnBack.setBackground(new Color(255, 234, 197));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LibraryDashboard().setVisible(true);
                dispose();  // Ẩn cửa sổ LibraryManagement hiện tại
            }
        });
        cont.add(btnBack);

        // Tạo JScrollPane và JTable
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 321, 1166, 362);
        table = new JTable();
        scrollPane.setViewportView(table);
        cont.add(scrollPane);
        
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(173, 23, 28)); 
        tableHeader.setForeground(Color.WHITE); 


        // Thiết lập panel quản lý độc giả
        pnlReaderManagement = new JPanel();
        pnlReaderManagement.setBounds(9, 0, 1166, 300);
        pnlReaderManagement.setLayout(null);
        pnlReaderManagement.setBackground(new Color(243, 238, 234));
        
        // Tạo JPanel làm ô vuông
        JPanel pnlTitleBox = new JPanel();
        pnlTitleBox.setBounds(700, 0, 250, 40); // Xác định kích thước vuông
        pnlTitleBox.setLayout(new BorderLayout());  // Sử dụng BorderLayout để dễ dàng căn giữa nội dung
        pnlTitleBox.setBackground(new Color(243,77,110));
        

        // Tạo JLabel cho tiêu đề "Quản lý độc giả"
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐỘC GIẢ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(255, 255, 255)); 

        // Thêm JLabel vào JPanel
        pnlTitleBox.add(lblTitle, BorderLayout.CENTER);

        // Thêm JPanel vào panel quản lý độc giả
        pnlReaderManagement.add(pnlTitleBox);

        // Hiển thị hình ảnh bên phải
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon imageIcon = new ImageIcon("src\\images\\quanlydocgia2.jpg");
            imageLabel.setIcon(imageIcon);
        } catch (Exception e) {
            System.out.println("Không thể tải hình ảnh: " + e.getMessage());
        }
        imageLabel.setBounds(520, 50, 600, 200);
        pnlReaderManagement.add(imageLabel);

        // Tạo các nhãn và trường nhập liệu cho thông tin độc giả
        JLabel lblReaderName = new JLabel("Tên:");
        JLabel lblIdentityCard = new JLabel("CMND:");
        JLabel lblPhoneNumber = new JLabel("Số điện thoại:");
        JLabel lblPosition = new JLabel("Chức vụ:");
        JLabel lblSurname = new JLabel("Họ đệm:");

        lblReaderName.setBounds(64, 95, 80, 26);
        lblIdentityCard.setBounds(64, 130, 80, 26);
        lblPhoneNumber.setBounds(64, 165, 80, 29);
        lblPosition.setBounds(64, 203, 80, 13);
        lblSurname.setBounds(64, 60, 80, 26);
        
        tfReaderName = new JTextField();
        tfIdentityCard = new JTextField();
        tfPhoneNumber = new JTextField();
        tfSurname = new JTextField();
        tfSearchReader = new JTextField();
        
        rdoLecturer = new JRadioButton("Giảng viên");
        rdoStudent = new JRadioButton("Sinh viên");
        rdoStudent.setSelected(true);

        // Nút chức năng
        btnAddReader = new JButton("Thêm độc giả");
        btnAddReader.addActionListener(this::btnAddReaderActionPerformed);
        
        btnUpdateReader = new JButton("Cập nhật");
        btnUpdateReader.addActionListener(this::btnUpdateReaderActionPerformed);
        
        btnDeleteReader = new JButton("Xóa độc giả");
        btnDeleteReader.addActionListener(this::btnDeleteReaderActionPerformed);
        
        btnResetReader = new JButton("Nhập lại");
        btnResetReader.addActionListener(this::btnResetReaderActionPerformed);
        
        btnSearchReader = new JButton("Tìm kiếm");
        btnSearchReader.addActionListener(this::btnSearchReaderActionPerformed);

        // Thêm các thành phần vào panel
        pnlReaderManagement.add(lblReaderName);
        pnlReaderManagement.add(lblIdentityCard);
        pnlReaderManagement.add(lblPhoneNumber);
        pnlReaderManagement.add(lblPosition);
        pnlReaderManagement.add(lblSurname);
        pnlReaderManagement.add(tfReaderName);
        pnlReaderManagement.add(tfIdentityCard);
        pnlReaderManagement.add(tfPhoneNumber);
        pnlReaderManagement.add(tfSurname);
        pnlReaderManagement.add(tfSearchReader);
        pnlReaderManagement.add(btnAddReader);
        pnlReaderManagement.add(btnUpdateReader);
        pnlReaderManagement.add(btnDeleteReader);
        pnlReaderManagement.add(btnResetReader);
        pnlReaderManagement.add(btnSearchReader);
        pnlReaderManagement.add(rdoLecturer);
        pnlReaderManagement.add(rdoStudent);
        
        cont.add(pnlReaderManagement);

        // Bố trí vị trí của các thành phần
        tfReaderName.setBounds(170, 95, 246, 26);  
        tfIdentityCard.setBounds(170, 130, 246, 26);  
        tfPhoneNumber.setBounds(170, 165, 246, 26);  
        tfSearchReader.setBounds(520, 265, 480, 31);  
        tfSurname.setBounds(170, 60, 246, 26);
        
        btnAddReader.setBounds(64, 235, 153, 26);  
        btnUpdateReader.setBounds(272, 235, 141, 26);  
        btnDeleteReader.setBounds(64, 265, 153, 26);  
        btnResetReader.setBounds(272, 265, 141, 26);
        btnSearchReader.setBounds(1018, 265, 110, 31);
        
        rdoLecturer.setBounds(170, 200, 90, 21);  
        rdoStudent.setBounds(290, 200, 90, 21);  

        //Màu các button
        Color buttonColor = new Color(243, 215, 202);
    
        btnAddReader.setBackground(buttonColor);
        btnDeleteReader.setBackground(buttonColor);
        btnResetReader.setBackground(buttonColor);
        btnUpdateReader.setBackground(buttonColor);
        btnSearchReader.setBackground(buttonColor);
        
        
        
        // Button Group
        gr = new ButtonGroup();
        gr.add(rdoStudent);
        gr.add(rdoLecturer);
        
        // Gọi phương thức hiển thị dữ liệu ban đầu
        findAllReader();
        setVisible(true);
    }
    
    private void btnAddReaderActionPerformed(ActionEvent evt)
    {
	Reader reader = new Reader();
	if(tfReaderName.getText().length()==0  || tfIdentityCard.getText().length()==0 || tfPhoneNumber.getText().length()==0)
	{
		JOptionPane.showInternalMessageDialog(cont,"Vui lòng điền đầy đủ thông tin");
	} 
	else
	{
		if(tfSurname.getText().length()==0)
		{
			reader.setSurname(null);
		}
		else
		{
			reader.setSurname(tfSurname.getText());
		}
		reader.setName(tfReaderName.getText());
		reader.setIdentityCard(tfIdentityCard.getText());
		reader.setPhoneNo(tfPhoneNumber.getText());
		if(rdoLecturer.isSelected())
		{
			reader.setJob(rdoLecturer.getText());
		} else if(rdoStudent.isSelected())
		{
			reader.setJob(rdoStudent.getText());
		}
		readerModify.addReader(reader);
		findAllReader();
	}

    }
	
    private void btnUpdateReaderActionPerformed(ActionEvent evt)
    {
	Reader reader = new Reader();
        if(tfReaderName.getText().length()==0  || tfIdentityCard.getText().length()==0 || tfPhoneNumber.getText().length()==0)
	{
		JOptionPane.showInternalMessageDialog(cont,"Vui lòng điền đầy đủ thông tin");
	} 
	else
	{
		if(tfSurname.getText().length()==0)
		{
			reader.setSurname(null);
		}
		else
		{
			reader.setSurname(tfSurname.getText());
		}
		reader.setName(tfReaderName.getText());
		reader.setIdentityCard(tfIdentityCard.getText());
		reader.setPhoneNo(tfPhoneNumber.getText());
		if(rdoLecturer.isSelected())
		{
			reader.setJob(rdoLecturer.getText());
		} else if(rdoStudent.isSelected())
		{
			reader.setJob(rdoStudent.getText());
		}
		reader.setReaderId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
       		readerModify.updateReader(reader);
		findAllReader();
	}
		
    }
	
    private void btnDeleteReaderActionPerformed(ActionEvent evt)
    {
	try
	{
		readerModify.deleteReader(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
		findAllReader();
	} catch(IndexOutOfBoundsException e)
	{
		JOptionPane.showInternalMessageDialog(cont,"Vui lòng chọn hàng cần xóa");
	}
    }
	
    private void btnResetReaderActionPerformed(ActionEvent evt)
    {
	tfSurname.setText(null);
	tfReaderName.setText(null);
	tfIdentityCard.setText(null);
	tfPhoneNumber.setText(null);
	rdoStudent.setSelected(true);
		
    }
        
    private void btnSearchReaderActionPerformed(ActionEvent e) {
        String sql;
        String parameter;
        if(tfSearchReader.getText().equals(""))
        {
            findAllReader();
        } 
        else
        {
            sql = "call sp_findByAllBook(?)";
            parameter = tfSearchReader.getText();
            findAllReader();
        }
    }
        
    public void findAllReader()
    {
	Vector<Reader> readerList = null;
        if(btnSearchReader.getText().equals(""))
	{
		readerList = readerModify.getReaderList();
	} else
	{
		readerList = readerModify.findReaderByAll(tfSearchReader.getText());
	}
	String[] columnNames = {"Mã độc giả", "Họ đệm", 
			"Tên", "CMND", "SĐT", "Chức vụ"};
	tblModel = new DefaultTableModel();
	tblModel.setColumnIdentifiers(columnNames);
	table.setModel(tblModel);
	for(Reader reader: readerList)
	{
		tblModel.addRow(new Object[] {reader.getReaderId(), reader.getSurname(), reader.getName(), reader.getIdentityCard(), reader.getPhoneNo(), reader.getJob()});
	}
		
		
    }
    public static void main(String[] args) {
        new ReaderManagementUI();
    }

}
