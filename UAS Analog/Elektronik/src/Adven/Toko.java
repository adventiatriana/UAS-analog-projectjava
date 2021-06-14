package Adven;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class Toko {

	private JFrame frmElektronikAdven;
	private JTextField txtNama;
	private JTextField txtStok;
	private JTextField txtHarga;
	private JTextField txtId;
	private JTable table;
	private JTextField txtSearch;
	private JTextField txtBeli;
	private JTextArea textAreaDesc;
	private JTextArea textAreaCheck;
	private JButton checkout;
	private JButton search;
	private JButton clearall;
	private JButton bayar;
	private JTextField txtBayar;
	
	// ARRAYLIST DATA CHECKOUT
	ArrayList<Checkout> check = new ArrayList<Checkout>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Toko window = new Toko();
					window.frmElektronikAdven.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	 *  KODINGAN UNTUK FUNGSI MENAMPILKAN DATA DARI DATABASE KE JTABLE
	 */
	public void tampilData() {
		try {
			String query = "select * from barang";
			Connection con = (Connection)KoneksiJDBC.koneksi();
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery(query);
			
			DefaultTableModel model = (DefaultTableModel)table.getModel();
	        model.setRowCount(0);
	        
	        String [] data = new String [4];
	        
	        while(rs.next()) {
	        	 data[0] = rs.getString("id");
		         data[1] = rs.getString("nama");
		         data[2] = rs.getString("stok");
		         data[3] = rs.getString("harga");
		         
	             model.addRow(data); 
	        }
	        
	        if (con != null) {
	        	try {
	        		con.close();
				} catch (SQLException e4) {}
	        }
		 
		    if (pst != null) {
	        	try {
	        		pst.close();
				} catch (SQLException e5) {}
	        }
		    
		    if (rs != null) {
	        	try {
	        		rs.close();
				} catch (SQLException e5) {}
	        }
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Gagal Insert Data\n"+e);
		}
	}
	
	/*
	 *  KODINGAN UNTUK FUNGSI MENGOSONGKAN FORM
	 */
	public void kosongForm() {
		txtId.setText("");
		txtNama.setText("");
		txtStok.setText("");
		txtHarga.setText("");
	}
	
	/*
	 *  KODINGAN UNTUK FUNGSI SEARCH DATA FORM DATABASE
	 */
	public class searchFunction{
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		public ResultSet find(String s){
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/elektronik", "root", "");
				ps = con.prepareStatement("select * from barang where id= ?");
				ps.setString(1, s);
				rs = ps.executeQuery();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			return rs;
		}
	}


	/**
	 * Create the application.
	 */
	public Toko() {
		initialize();
		tampilData();
		kosongForm();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmElektronikAdven = new JFrame();
		frmElektronikAdven.setResizable(false);
		frmElektronikAdven.setTitle("ELEKTRONIC STORE");
		frmElektronikAdven.setFont(new Font("Stencil", Font.PLAIN, 14));
		frmElektronikAdven.setForeground(Color.MAGENTA);
		frmElektronikAdven.setIconImage(Toolkit.getDefaultToolkit().getImage(Toko.class.getResource("/gambar/icon.png")));
		frmElektronikAdven.setBounds(300, 100, 800, 560);
		frmElektronikAdven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmElektronikAdven.getContentPane().setLayout(null);
		
		search = new JButton("SEARCH");
		search.setFocusable(false);
		
		/*
		 *  KODINGAN UNTUK FUNGSI SEARCH DATA DAN MENAMPILKANNYA KE TEXT AREA
		 */
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtSearch.getText());
				
				try {
					if (rs.next()) {
						textAreaDesc.setText("ID Barang\t: "+String.valueOf(rs.getString("id"))+"\nNama Barang\t: "+rs.getString("nama")+"\nStok Barang\t: "+String.valueOf(rs.getString("stok"))+"\nHarga Per unit\t: "+String.valueOf(rs.getString("harga")));
					} else {
						JOptionPane.showMessageDialog(null, "Tidak ada data dengan ID tersebut");
						txtSearch.setText("");
						textAreaDesc.setText("");
					
	        		    if (rs != null) {
	        	        	try {
	        	        		rs.close();
	        				} catch (SQLException e5) {}
	        	        }
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					txtSearch.setText("");
					textAreaDesc.setText("");
				  }
			}
		});
		search.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				search.setForeground(new Color(255,255,255));
				search.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				search.setForeground(new Color(0, 0, 0));
				search.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				search.setForeground(new Color(255,255,255));
				search.setBackground(new Color(75,0,130));
			}
		});
		
		/*
		 *  KODINGAN UNTUK FUNGSI TOMBOL CLEARALL, MENGHAPUS TEXTFIELD BAGIAN PENJUALAN
		 */
		clearall = new JButton("CLEAR ALL");
		clearall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearch.setText("");
				textAreaDesc.setText("");
				txtSearch.setText("");
				textAreaCheck.setText("");
				txtBeli.setText("");
				txtBayar.setText("");
			}
		});
		clearall.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				clearall.setForeground(new Color(255,255,255));
				clearall.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				clearall.setForeground(new Color(0, 0, 0));
				clearall.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				clearall.setForeground(new Color(255,255,255));
				clearall.setBackground(new Color(75,0,130));
			}
		});
		
		/*
		 *  KODINGAN UNTUK FUNGSI TOMBOL BAYAR DIMANA KETIKA NOMINAL BAYAR LEBIH DARI TOTAL BIAYA,
		 *  MAKA AKAN SUKSES TERJUAL...DAN DATA STOK BARANG DIPERBAHARUI
		 *  
		 *  KETIKA NOMINAL BAYAR TIDAK MENCUKUPI, MAKA AKAN MUNCUL PESAN NOMINAL TIDAK CUKUP
		 *  KETIKA BARANG DIBELI HABIS MAKA DATA OTOMATIS TERHAPUS DARI DATABASE
		 */
		bayar = new JButton("BAYAR");
		bayar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtSearch.getText());
				
				try {
					
					if (rs.next()) {
						check.add(new Checkout(rs.getInt("id"), rs.getString("nama"),rs.getInt("stok"), rs.getLong("harga")));
						
						int sisaStok = rs.getInt("stok") - Integer.valueOf(txtBeli.getText());
						long totalBayar = Long.valueOf(txtBeli.getText()) * rs.getLong("harga");
						
						if (Long.valueOf(txtBayar.getText()) >= totalBayar && sisaStok > 0) {
							int con = JOptionPane.showConfirmDialog(textAreaCheck, "Lanjutkan membayar?", "Confirm", JOptionPane.YES_NO_OPTION);
							if (con == 0) {
								JOptionPane.showMessageDialog(textAreaCheck, "Pembayaran Berhasil");
								try {
									String query = "update barang set stok='"+(rs.getInt("stok") - Integer.valueOf(txtBeli.getText()))+"' where id='"+txtSearch.getText()+"'";
									Connection conn = (Connection)KoneksiJDBC.koneksi();
									PreparedStatement st = conn.prepareStatement(query);
				                    st.executeUpdate();
				                    tampilData();
				                    textAreaDesc.setText("Penjualan Berhasil\n"+"Sisa Stok Barang: "+sisaStok+" unit");
				                    textAreaCheck.setText("");
				                    txtSearch.setText("");
				                    txtBeli.setText("");
				                    txtBayar.setText("");
				                    
				                    if (conn != null) {
				        	        	try {
				        	        		conn.close();
				        				} catch (SQLException e4) {}
				        	        }
				        		 
				        		    if (st != null) {
				        	        	try {
				        	        		st.close();
				        				} catch (SQLException e5) {}
				        	        }
				        		    
				        		    if (rs != null) {
				        	        	try {
				        	        		rs.close();
				        				} catch (SQLException e5) {}
				        	        }
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(textAreaCheck, "Pembayaran Gagal!!! Periksa koneksi Database");
									
								}
							}
						} else if (Long.valueOf(txtBayar.getText()) >= totalBayar && sisaStok == 0) {
							int con = JOptionPane.showConfirmDialog(textAreaCheck, "Lanjutkan membayar?", "Confirm", JOptionPane.YES_NO_OPTION);
							if (con == 0) {
								JOptionPane.showMessageDialog(textAreaCheck, "Pembayaran Berhasil");
								try {
									String query = "delete from barang where id='"+txtSearch.getText()+"'";
									Connection conn = (Connection)KoneksiJDBC.koneksi();
									PreparedStatement st = conn.prepareStatement(query);
									st.executeUpdate();
									tampilData();
				                    textAreaDesc.setText("Penjualan Berhasil\n"+"Sisa Stok Barang Habis");
				                    textAreaCheck.setText("");
				                    txtSearch.setText("");
				                    txtBeli.setText("");
				                    txtBayar.setText("");
				                    
				                    if (conn != null) {
				        	        	try {
				        	        		conn.close();
				        				} catch (SQLException e4) {}
				        	        }
				        		 
				        		    if (st != null) {
				        	        	try {
				        	        		st.close();
				        				} catch (SQLException e5) {}
				        	        }
				        		    
				        		    if (rs != null) {
				        	        	try {
				        	        		rs.close();
				        				} catch (SQLException e5) {}
				        	        }
				                    
								} catch (Exception e3) {
									JOptionPane.showMessageDialog(textAreaCheck, "Pembayaran Gagal!!! Periksa koneksi Database");
									
								}
							}
						} else if (Long.valueOf(txtBayar.getText()) < totalBayar) {
							JOptionPane.showMessageDialog(textAreaCheck, "Nominal Bayar tidak cukup!!!");
						}
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					txtSearch.setText("");
					textAreaDesc.setText("");
				  }	finally {
					    if (rs != null) {
				        	try {
				        		rs.close();
							} catch (SQLException e5) {
								
							}
				        }
				  }
			}
		});
		bayar.setFocusable(false);
		bayar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bayar.setForeground(new Color(255,255,255));
				bayar.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				bayar.setForeground(new Color(0, 0, 0));
				bayar.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				bayar.setForeground(new Color(255,255,255));
				bayar.setBackground(new Color(75,0,130));
			}
		});
		
		JLabel lblNewLabel_3_1 = new JLabel("Masukkan");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(674, 397, 100, 20);
		frmElektronikAdven.getContentPane().add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3 = new JLabel("Nominal Bayar");
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_3.setBounds(674, 415, 100, 20);
		frmElektronikAdven.getContentPane().add(lblNewLabel_3);
		
		txtBayar = new JTextField();
		txtBayar.setText("");
		txtBayar.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txtBayar.setColumns(10);
		txtBayar.setBounds(674, 434, 100, 27);
		frmElektronikAdven.getContentPane().add(txtBayar);
		bayar.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		bayar.setFocusable(false);
		bayar.setBackground(Color.WHITE);
		bayar.setBounds(674, 472, 100, 26);
		frmElektronikAdven.getContentPane().add(bayar);
		clearall.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		clearall.setFocusable(false);
		clearall.setBackground(Color.WHITE);
		clearall.setBounds(589, 275, 114, 20);
		frmElektronikAdven.getContentPane().add(clearall);
		
		JScrollPane scrollPaneCheck = new JScrollPane();
		scrollPaneCheck.setBounds(503, 360, 161, 138);
		frmElektronikAdven.getContentPane().add(scrollPaneCheck);
		
		textAreaCheck = new JTextArea();
		scrollPaneCheck.setViewportView(textAreaCheck);
		
		checkout = new JButton("CHECKOUT");
		checkout.setFocusable(false);
		
		/*
		 * KODINGAN UNTUK FUNGSI CHECKOUT PEMBELIAN BARANG
		 */
		checkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchFunction sf = new searchFunction();
				ResultSet rs = null;
				
				rs = sf.find(txtSearch.getText());
				
				try {
					
					if (rs.next()) {
						check.add(new Checkout(rs.getInt("id"), rs.getString("nama"),rs.getInt("stok"), rs.getLong("harga")));
						
						int sisaStok = rs.getInt("stok") - Integer.valueOf(txtBeli.getText());
						long totalBayar = Long.valueOf(txtBeli.getText()) * rs.getLong("harga");
						
						Checkout checkoutValue = (Checkout)check.get(check.size()-1);
						
						if (Integer.valueOf(txtBeli.getText()) > rs.getInt("stok")) {
							JOptionPane.showMessageDialog(checkout, "Stok barang tidak cukup");
						} else {
							textAreaCheck.setText("ID Barang\t:"+checkoutValue.getIdbarang()+"\nNama Barang\t:"+checkoutValue.getNamabarang()+"\nStok Tersedia\t:"+String.valueOf(rs.getInt("stok"))+"\nJumlah Jual\t:"+txtBeli.getText()+"\n=================="+"\nTotal Biaya\t:"+totalBayar+"\nSisa Stok\t:"+sisaStok);
						}
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					txtSearch.setText("");
					textAreaDesc.setText("");
				  }	finally {
	        		    if (rs != null) {
	        	        	try {
	        	        		rs.close();
	        				} catch (SQLException e5) {}
	        	        }
				  }
				
			}
		});
		checkout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				checkout.setForeground(new Color(255,255,255));
				checkout.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				checkout.setForeground(new Color(0, 0, 0));
				checkout.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				checkout.setForeground(new Color(255,255,255));
				checkout.setBackground(new Color(75,0,130));
			}
		});
		checkout.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		checkout.setBackground(Color.WHITE);
		checkout.setBounds(674, 328, 100, 26);
		frmElektronikAdven.getContentPane().add(checkout);
		
		txtBeli = new JTextField();
		txtBeli.setText("");
		txtBeli.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txtBeli.setColumns(10);
		txtBeli.setBounds(503, 327, 161, 27);
		frmElektronikAdven.getContentPane().add(txtBeli);
		
		JLabel lblNewLabel_2_1 = new JLabel("Masukkan Jumlah Barang");
		lblNewLabel_2_1.setForeground(Color.WHITE);
		lblNewLabel_2_1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2_1.setBounds(503, 295, 173, 37);
		frmElektronikAdven.getContentPane().add(lblNewLabel_2_1);
		
		JScrollPane scrollPaneArea = new JScrollPane();
		scrollPaneArea.setBounds(503, 183, 271, 81);
		frmElektronikAdven.getContentPane().add(scrollPaneArea);
		
		textAreaDesc = new JTextArea();
		textAreaDesc.setFont(new Font("Cambria", Font.PLAIN, 17));
		textAreaDesc.setEditable(false);
		scrollPaneArea.setViewportView(textAreaDesc);
		search.setBackground(new Color(255, 255, 255));
		search.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		search.setBounds(653, 145, 121, 26);
		frmElektronikAdven.getContentPane().add(search);
		
		JLabel lblNewLabel_1_1 = new JLabel("ELEKTRONIK");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(new Color(255, 0, 255));
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel_1_1.setBounds(503, 44, 271, 48);
		frmElektronikAdven.getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1 = new JLabel("PENJUALAN BARANG\r\n");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(new Color(255, 0, 255));
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 21));
		lblNewLabel_1.setBounds(503, 11, 271, 53);
		frmElektronikAdven.getContentPane().add(lblNewLabel_1);
		
		JPanel panelJudul = new JPanel();
		panelJudul.setBackground(new Color(255, 255, 204));
		panelJudul.setBorder(null);
		panelJudul.setBounds(503, 11, 271, 81);
		frmElektronikAdven.getContentPane().add(panelJudul);
		
		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		txtSearch.setText("");
		txtSearch.setColumns(10);
		txtSearch.setBounds(503, 145, 140, 27);
		frmElektronikAdven.getContentPane().add(txtSearch);
		
		JLabel lblNewLabel_2 = new JLabel("Masukkan ID Barang");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel_2.setBounds(503, 122, 140, 23);
		frmElektronikAdven.getContentPane().add(lblNewLabel_2);
		
		JPanel panelData = new JPanel();
		panelData.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY, null, null));
		panelData.setBounds(10, 295, 483, 215);
		panelData.setOpaque(false);
		frmElektronikAdven.getContentPane().add(panelData);
		panelData.setLayout(null);
		
		JLabel lblDataBarang = new JLabel("DATA BARANG");
		lblDataBarang.setHorizontalAlignment(SwingConstants.CENTER);
		lblDataBarang.setForeground(Color.WHITE);
		lblDataBarang.setFont(new Font("Stencil", Font.ITALIC, 17));
		lblDataBarang.setBounds(10, 11, 126, 25);
		panelData.add(lblDataBarang);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 35, 463, 169);
		panelData.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID Barang", "Nama Barang", "Stok Barang", "Harga Barang"
			}
		) {
			private static final long serialVersionUID = 1L;
		
			Class[] columnTypes = new Class[] {
				String.class, String.class, Integer.class, Long.class
			};
			
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				boolean set = false;
				txtId.setEditable(set);
				
				int row = table.rowAtPoint(e.getPoint());
				
				String id = table.getValueAt(row, 0).toString();
				String nama = table.getValueAt(row, 1).toString();
				String stok = table.getValueAt(row, 2).toString();
				String harga = table.getValueAt(row, 3).toString();
				
				
				txtId.setText(String.valueOf(id));
				txtNama.setText(String.valueOf(nama));
				txtStok.setText(String.valueOf(stok));
				txtHarga.setText(String.valueOf(harga));
			}
		});
		
		JPanel panelBaru = new JPanel();
		panelBaru.setBackground(Color.LIGHT_GRAY);
		panelBaru.setBorder(new BevelBorder(BevelBorder.RAISED, Color.LIGHT_GRAY, Color.LIGHT_GRAY, null, null));
		panelBaru.setBounds(10, 11, 483, 273);
		panelBaru.setOpaque(false);
		frmElektronikAdven.getContentPane().add(panelBaru);
		panelBaru.setLayout(null);
		
		JLabel lblInput = new JLabel("INPUT DATA BARANG BARU");
		lblInput.setForeground(new Color(255, 255, 255));
		lblInput.setFont(new Font("Stencil", Font.ITALIC, 17));
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInput.setBounds(10, 11, 238, 25);
		panelBaru.add(lblInput);
		
		JLabel lblNewLabel = new JLabel("ID Barang");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNewLabel.setBounds(67, 59, 95, 35);
		panelBaru.add(lblNewLabel);
		
		JLabel lblNamaBarang = new JLabel("Nama Barang");
		lblNamaBarang.setForeground(Color.WHITE);
		lblNamaBarang.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblNamaBarang.setBounds(67, 105, 95, 25);
		panelBaru.add(lblNamaBarang);
		
		JLabel lblJumlahBarang = new JLabel("Jumlah Barang");
		lblJumlahBarang.setForeground(Color.WHITE);
		lblJumlahBarang.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblJumlahBarang.setBounds(67, 145, 95, 25);
		panelBaru.add(lblJumlahBarang);
		
		JLabel lblHargaBarang = new JLabel("Harga Barang");
		lblHargaBarang.setForeground(Color.WHITE);
		lblHargaBarang.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblHargaBarang.setBounds(67, 181, 95, 31);
		panelBaru.add(lblHargaBarang);
		
		txtNama = new JTextField();
		txtNama.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txtNama.setColumns(10);
		txtNama.setBounds(217, 105, 184, 27);
		panelBaru.add(txtNama);
		
		txtStok = new JTextField();
		txtStok.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txtStok.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))) {
				    e.consume();
				}
			}	
		});
		txtStok.setColumns(10);
		txtStok.setBounds(217, 145, 184, 27);
		panelBaru.add(txtStok);
		
		txtHarga = new JTextField();
		txtHarga.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtHarga.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))) {
				    e.consume();
				}
			}	
		});
		txtHarga.setColumns(10);
		txtHarga.setBounds(217, 184, 184, 27);
		panelBaru.add(txtHarga);
		
		txtId = new JTextField();
		txtId.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char karakter = e.getKeyChar();
				if (!(((karakter >= '0') && (karakter <= '9') || (karakter == KeyEvent.VK_BACK_SPACE) || (karakter == KeyEvent.VK_DELETE)))) {
				    e.consume();
				}
			}	
		});
		txtId.setColumns(10);
		txtId.setBounds(217, 64, 184, 27);
		panelBaru.add(txtId);
		
		JButton save = new JButton("SAVE");
		save.setFocusable(false);
		
		/*
		 *  KODINGAN UNTUK FUNGSI TOMBOL SAVE MENYIMPAN DATA KE DATABASE
		 */
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						String sql = "insert into barang values('"+Integer.parseInt(txtId.getText())+"','"+txtNama.getText()+"','"+Integer.parseInt(txtStok.getText())+"','"+Long.parseLong(txtHarga.getText())+"')";
						Connection con = (Connection)KoneksiJDBC.koneksi();
						PreparedStatement pst = con.prepareStatement(sql);
						if (txtId.getText().equals("")) {
							JOptionPane.showMessageDialog(save, "ID Barang tidak boleh kosong!");
						} else if (txtNama.getText().equals("")) {
							JOptionPane.showMessageDialog(save, "Nama Barang tidak boleh kosong!");
						} else if (txtStok.getText().equals("")) {
							JOptionPane.showMessageDialog(save, "Jumlah Barang tidak boleh kosong!");
						} else if (txtHarga.getText().equals("")) {
							JOptionPane.showMessageDialog(save, "Harga Barang tidak boleh kosong!");
						}  else {
							pst.execute();
							JOptionPane.showMessageDialog(save, "Data Barang Elektronik Tersimpan");
							tampilData();
							kosongForm();
							txtId.setEditable(true);
							
							if (con != null) {
		        	        	try {
		        	        		con.close();
		        				} catch (SQLException e4) {}
		        	        }
		        		 
		        		    if (pst != null) {
		        	        	try {
		        	        		pst.close();
		        				} catch (SQLException e5) {}
		        	        }
						} 
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(save, "Gagal simpan data", "KESALAHAN INPUT!!", 0);
					}
			}
		});
		save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				save.setForeground(new Color(255,255,255));
				save.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				save.setForeground(new Color(0, 0, 0));
				save.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				save.setForeground(new Color(255,255,255));
				save.setBackground(new Color(75,0,130));
			}
		});
		save.setBackground(new Color(255, 255, 255));
		save.setForeground(new Color(0, 0, 0));
		save.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		save.setBounds(67, 232, 89, 30);
		panelBaru.add(save);
		
		JButton delete = new JButton("DELETE");
		
		/*
		 *  KODINGAN UNTUK TOMBOL HAPUS, MENGHAPUS DATA DARI DATABASE
		 */
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(delete, "Apakah anda yakin ingin menghapus data?", "Warning!!!.", JOptionPane.YES_NO_OPTION);
				
				try {
					String query = "delete from barang where id='"+txtId.getText()+"'";
					Connection conn = (Connection)KoneksiJDBC.koneksi();
					PreparedStatement st = conn.prepareStatement(query);
					
					if (confirm==0) {
						st.executeUpdate();
						JOptionPane.showMessageDialog(delete, "Berhasil menghapus data...");
						tampilData();
						kosongForm();
						txtId.setEditable(true);
						
						if (conn != null) {
	        	        	try {
	        	        		conn.close();
	        				} catch (SQLException e4) {}
	        	        }
	        		 
	        		    if (st != null) {
	        	        	try {
	        	        		st.close();
	        				} catch (SQLException e5) {}
	        	        }
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(delete, "Gagal Hapus");
				}
			}
		});
		delete.setFocusable(false);
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				delete.setForeground(new Color(255,255,255));
				delete.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				delete.setForeground(new Color(0, 0, 0));
				delete.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				delete.setForeground(new Color(255,255,255));
				delete.setBackground(new Color(75,0,130));
			}
		});
		delete.setBackground(new Color(255, 255, 255));
		delete.setForeground(Color.BLACK);
		delete.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		delete.setBounds(312, 232, 89, 30);
		panelBaru.add(delete);
		
		JButton update = new JButton("UPDATE");
		
		/*
		 *  KODINGAN UNTUK FUNGSI TOMBOL EDIT, MENGEDIT DATA BARANG DARI DATABASE
		 */
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(update, "Yakin ingin mengubah data barang?", "Confirm", JOptionPane.YES_NO_OPTION);
				try {
					String query = "update barang set nama='"+txtNama.getText()+"',stok='"+Integer.parseInt(txtStok.getText())+"',harga='"+Long.parseLong(txtHarga.getText())+"' where id='"+Integer.parseInt(txtId.getText())+"'";
					Connection conn = (Connection)KoneksiJDBC.koneksi();
					PreparedStatement st = conn.prepareStatement(query);
					if (txtId.getText().equals("")) {
						JOptionPane.showMessageDialog(update, "ID Barang tidak boleh kosong!");
					} else if (txtNama.getText().equals("")) {
						JOptionPane.showMessageDialog(update, "Nama Barang tidak boleh kosong!");
					} else if (txtStok.getText().equals("")) {
						JOptionPane.showMessageDialog(update, "Jumlah Barang tidak boleh kosong!");
					} else if (txtHarga.getText().equals("")) {
						JOptionPane.showMessageDialog(update, "Harga Barang tidak boleh kosong!");
					} else if (confirm==0) {
                        st.executeUpdate();
                        JOptionPane.showMessageDialog(update, "Update data barang berhasil");
                        tampilData();
                        kosongForm();
                        txtId.setEditable(true);
                        
                        if (conn != null) {
	        	        	try {
	        	        		conn.close();
	        				} catch (SQLException e4) {}
	        	        }
	        		 
	        		    if (st != null) {
	        	        	try {
	        	        		st.close();
	        				} catch (SQLException e5) {}
	        	        }
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(update, "Gagal update data");
				}
			}
		});
		update.setFocusable(false);
		update.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				update.setForeground(new Color(255,255,255));
				update.setBackground(new Color(75,0,130));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				update.setForeground(new Color(0, 0, 0));
				update.setBackground(new Color(255,255,255));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				update.setForeground(new Color(255,255,255));
				update.setBackground(new Color(75,0,130));
			}
		});
		update.setBackground(new Color(255, 255, 255));
		update.setForeground(Color.BLACK);
		update.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		update.setBounds(191, 232, 89, 30);
		panelBaru.add(update);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(Toko.class.getResource("/gambar/background.png")));
		background.setHorizontalAlignment(SwingConstants.CENTER);
		background.setBounds(0, 0, 784, 521);
		frmElektronikAdven.getContentPane().add(background);
	}
}
