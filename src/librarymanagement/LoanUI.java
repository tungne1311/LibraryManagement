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
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class LoanUI extends JFrame {
    
    LoanModify loanModify = new LoanModify();
    PunishmentModify punishmentModify = new PunishmentModify();
    
    private Connection conn;
    private Container cont;
    private JTable table;
    private JTextField tfReaderId,tfBookId,tfSearch, tfLoanNo;
    private JButton jbReaderCheck,jbBookIdcheck,jbLoan,jbReturn,jbReset,jbPunish,jbSerach,btnBack;
    private JLabel lblReaderId, lblBookId, lblLoanNo, lblReturnAppointmentDate, lblOutputReader, lblOutputBook;
    private DefaultTableModel tblModel;
    private JPanel pnlLoan;
    private JDateChooser dc;
    private DateFormat df;
    
    public LoanUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 720);
        cont = this.getContentPane();
        cont.setLayout(null);                
        
//        // Tạo JScrollPane và JTable
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 321, 1166, 362);
        table = new JTable();
        scrollPane.setViewportView(table);
        cont.add(scrollPane);
       
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(173, 23, 28)); 
        tableHeader.setForeground(Color.WHITE); 
        //table
         String[] columnNames = {"Mã mượn", "Mã độc giả", 
				"Mã sách", "Số lượng mượn", "Ngày hẹn trả", "Ngày trả", "Trạng thái"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
        
        // Thiết lập panel 
        pnlLoan = new JPanel();
        pnlLoan.setBounds(9, 0, 1166, 300);
        pnlLoan.setLayout(null);
        pnlLoan.setBackground(new Color(243, 238, 234));
        // Tạo JPanel làm ô vuông
        JPanel pnlTitleBox = new JPanel();
        pnlTitleBox.setBounds(700, 0, 250, 40); // Xác định kích thước vuông
        pnlTitleBox.setLayout(new BorderLayout());  // Sử dụng BorderLayout để dễ dàng căn giữa nội dung
        pnlTitleBox.setBackground(new Color(243,77,110));
        // Tạo JLabel cho tiêu đề "Quản lý độc giả"
        JLabel lblTitle = new JLabel("MƯỢN TRẢ SÁCH", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitle.setForeground(new Color(255, 255, 255));
        
        // Thêm JLabel vào JPanel
        pnlTitleBox.add(lblTitle, BorderLayout.CENTER);
                               
        // Thêm JPanel vào panel quản lý độc giả
        pnlLoan.add(pnlTitleBox);
        //ảnh
        
        //Chức năng
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
        
        
        JLabel imageLabel = new JLabel();
        try {
            ImageIcon imageIcon = new ImageIcon("src\\images\\quanlydocgia2.jpg");
            imageLabel.setIcon(imageIcon);
        } catch (Exception e) {
            System.out.println("Không thể tải hình ảnh: " + e.getMessage());
        }
        imageLabel.setBounds(568, 70, 535, 150);
        pnlLoan.add(imageLabel);
        
        //Lable
        lblReaderId = new JLabel ("Mã độc giả");
        lblBookId = new JLabel ("Mã Sách");
        lblLoanNo = new JLabel ("Số lượng mượn");
        lblReturnAppointmentDate = new JLabel ("Ngày hẹn trả");
        
        lblOutputReader = new JLabel();
	lblOutputBook = new JLabel();
        
        //TextField
        tfReaderId = new JTextField();
        tfBookId = new JTextField();
        tfLoanNo = new JTextField();
        tfSearch = new JTextField();
         

        dc = new JDateChooser();
	pnlLoan.add(dc);
	dc.setBounds(175, 210, 138, 20);
        
	dc.setDateFormatString("yyyy-MM-dd");
        	
	df = new SimpleDateFormat("yyyy-MM-dd");

        
        
        //Button
        jbReaderCheck = new JButton("Kiểm tra");
        
        jbBookIdcheck =new JButton ("Kiểm tra");
        
        
        jbSerach = new JButton("Tìm kiếm");
        
        jbLoan = new JButton("Mượn sách");
        
        jbReturn = new JButton("Trả sách");
        
        jbReset = new JButton("Nhập lại");
        
        jbPunish = new JButton("DS phạt");
                
        
        //setup vị trí
        lblReaderId.setBounds(65, 60, 100, 20);
        lblBookId.setBounds(65, 110, 100, 20);
        lblLoanNo.setBounds(65, 160, 100, 20);
        lblReturnAppointmentDate.setBounds(65, 210, 148, 20);
        
        lblOutputReader.setBounds(175, 36, 221, 20);
	lblOutputBook.setBounds(175, 90, 221, 20);
        
        tfReaderId.setBounds(175,60, 138, 20);
        tfBookId.setBounds(175, 110, 138, 20);
        tfLoanNo.setBounds(175, 160, 138, 20);
        tfSearch.setBounds(570, 237, 470, 28);
        
        jbReaderCheck.setBounds(323, 60, 90, 21);
        jbBookIdcheck.setBounds(323, 110, 90, 20);
        jbLoan.setBounds(10, 237, 100, 28);
        jbReturn.setBounds(150, 237, 100, 28);
        jbReset.setBounds(290, 237, 100, 28);
        jbPunish.setBounds(430, 237, 100, 28);
        jbSerach.setBounds(1051, 236, 100, 28);
        
        
        
        
        //color
        Color buttonColor = new Color(243, 215, 202);
        jbBookIdcheck.setBackground(buttonColor);
        jbLoan.setBackground(buttonColor);
        jbPunish.setBackground(buttonColor);
        jbReaderCheck.setBackground(buttonColor);
        jbReset.setBackground(buttonColor);
        jbReturn.setBackground(buttonColor);
        jbSerach.setBackground(buttonColor);
        
        
        
        //add
        pnlLoan.add(lblReaderId);
        pnlLoan.add(lblBookId);
        pnlLoan.add(lblLoanNo);
        pnlLoan.add(lblReturnAppointmentDate);
        pnlLoan.add(tfReaderId);
        pnlLoan.add(tfBookId);
        pnlLoan.add(tfSearch);
        pnlLoan.add(tfLoanNo);
        pnlLoan.add(jbBookIdcheck);
        pnlLoan.add(jbLoan);
        pnlLoan.add(jbPunish);
        pnlLoan.add(jbReaderCheck);
        pnlLoan.add(jbReset);
        pnlLoan.add(jbReturn);
        pnlLoan.add(jbSerach);
        pnlLoan.add(btnBack);
        pnlLoan.add(lblOutputReader);
	pnlLoan.add(lblOutputBook);
        cont.add(pnlLoan);
        
     
	jbReaderCheck.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			btnTestReaderIdActionPerformed(evt);
				
		}
	});
		
	jbBookIdcheck.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			btnTestBookIdActionPerformed(evt);
		}
	});
		
	jbLoan.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) 
		{
				btnAddLoanActionPerformed(evt);
		}
	});
		
	jbReturn.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			btnReturnBookActionPerformed(evt);
		}
	});
	jbSerach.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			btnSearchLoanActionPerformed(evt);
		}
	});
	jbReset.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			btnResetLoanActionPerformed(evt);
		}
	});
		
	jbPunish.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent evt)
		{
			btnPunishActionPerformed(evt);
		}
	});
        findAllLoan();     
        setVisible(true);
    }
    
    private void btnSearchLoanActionPerformed(ActionEvent evt)
	{
		findAllLoan();
	}
	
	private void btnAddLoanActionPerformed(ActionEvent evt)
	{
		Loan loan = new Loan();
		try
		{
			loan.setReaderId(Integer.parseInt(tfReaderId.getText()));
			loan.setBookId(Integer.parseInt(tfBookId.getText()));
                        loan.setLoanNo(Integer.parseInt(tfLoanNo.getText()));

			try
			{

				loan.setBookReturnAppointmentDate(df.format(dc.getDate()));
				if(lblOutputBook.getText().equals("Không tìm thấy sách") || lblOutputReader.getText().equals("Không tìm thấy độc giả"))
				{
					JOptionPane.showInternalMessageDialog(cont, "Mượn sách thất bại\nVui lòng kiểm tra mã độc giả và mã sách");
				} else
				{

				String booksInLastWeek = loanModify.checkBooksInLastWeek(tfReaderId.getText());
                                int currentBooks = Integer.parseInt(booksInLastWeek);
                                int newBooks = loan.getLoanNo();
                                
				if(currentBooks + newBooks <= 3)
				{
					int addLoan = loanModify.addLoan(loan);
					if(addLoan ==0 )
					{
						JOptionPane.showMessageDialog(this, "Sách này đã hết trong kho");
					}
                                        else {
                                                JOptionPane.showMessageDialog(this, "Mượn sách thành công");
                                        }
				} 
                                else
				{
					JOptionPane.showInternalMessageDialog(cont, "Mỗi độc giả chỉ được mượn tối đa 3 quyển sách trong 1 tuần");
				}
				}
				
				findAllLoan();
			} catch(Exception e)
			{
				JOptionPane.showInternalMessageDialog(cont, "Ngày hẹn trả không hợp lệ");
			}



		} catch(Exception e)
		{
			JOptionPane.showInternalMessageDialog(cont, "Vui lòng nhập đầy đủ thông tin");
		}
	}
	
	private void btnReturnBookActionPerformed(ActionEvent evt)
	{
		try
		{
		loanModify.returnBook(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
		findAllLoan();
		} catch(Exception e)
		{
			JOptionPane.showInternalMessageDialog(cont, "Vui lòng chọn mã mượn của sách cần trả");
		}
	}
	
	private void btnTestReaderIdActionPerformed(ActionEvent evt)
	{
		try
		{
			Reader reader = loanModify.testReaderId(Integer.parseInt(tfReaderId.getText()));
			if(reader.getName().equals(""))
			{
				lblOutputReader.setText("Không tìm thấy độc giả");
				lblOutputReader.setForeground(Color.RED);
			}
			else
			{
				lblOutputReader.setText(reader.getName());
				lblOutputReader.setForeground(Color.GREEN);
			}
		} catch(Exception e)
		{
			lblOutputReader.setText("Không tìm thấy độc giả");
			lblOutputReader.setForeground(Color.RED);
		}
	}
	
	private void btnTestBookIdActionPerformed(ActionEvent evt)
	{
		try
		{
			Book book = loanModify.testBookId(Integer.parseInt(tfBookId.getText()));
			if(book.getBookName().equals(""))
			{
				lblOutputBook.setText("Không tìm thấy sách");
				lblOutputBook.setForeground(Color.GREEN);
			}
			lblOutputBook.setText(book.getBookName());
			lblOutputBook.setForeground(Color.GREEN);
		} catch(Exception e)
		{
			lblOutputBook.setText("Không tìm thấy sách");
			lblOutputBook.setForeground(Color.RED);
		}
	}
	
	
	private void btnResetLoanActionPerformed(ActionEvent evt)
	{
		tfReaderId.setText(null);
		tfBookId.setText(null);
                tfLoanNo.setText(null);
		lblOutputReader.setText(null);
		lblOutputBook.setText(null);
		dc.setCalendar(null);
	}
	
	
	public void findAllLoan()
	{
		Vector<Loan> loanList = null;
		if(tfSearch.getText().equals(""))
		{
			loanList = loanModify.getLoanlist();
		} else
		{
			loanList = loanModify.findLoanByAll(tfSearch.getText());
		}
		String[] columnNames = {"Mã mượn","Mã độc giả","Mã sách", "Số lượng mượn", "Ngày mượn", "Ngày hẹn trả", "Ngày trả", "Trạng thái"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		for(Loan loan: loanList)
		{
			tblModel.addRow(new Object[] {loan.getLoanId(), loan.getReaderId(), loan.getBookId(), loan.getLoanNo(), loan.getLoanDate(), loan.getBookReturnAppointmentDate(), loan.getBookReturnDate(), loan.getStatus()});
		}
	}
        
        private void btnPunishActionPerformed(ActionEvent evt)
	{
		punish();
	}
	
	public void punish()
	{
		Vector<Punishment> punishmentList1 = punishmentModify.getPunishmentList();
		Vector<Punishment> punishmentList2 = punishmentModify.getPunishmentListReturnYet();
		String[] columnNames = {"Mã mượn","Mã độc giả","Tên độc giả","Mã sách","Tên sách","Số lượng mượn","Quá hạn (ngày)","Thành tiền (đồng)", "Trạng thái sách"};
		tblModel = new DefaultTableModel();
		tblModel.setColumnIdentifiers(columnNames);
		table.setModel(tblModel);
		for(Punishment punishment : punishmentList1)
		{
			tblModel.addRow(new Object[] {punishment.getLoanId(),punishment.getReaderId(),punishment.getFullname(),punishment.getBookId(),punishment.getBook(),punishment.getLoanNo(),punishment.getDaysLate(),punishment.getTotal(),punishment.getStatus()});
		}
		
		for(Punishment punishment: punishmentList2)
		{
			tblModel.addRow(new Object[] {punishment.getLoanId(),punishment.getReaderId(),punishment.getFullname(),punishment.getBookId(),punishment.getBook(),punishment.getLoanNo(),punishment.getDaysLate(),punishment.getTotal(),punishment.getStatus()});
		}
	}
        public static void main(String[] args) {
            new LoanUI();
    }
}