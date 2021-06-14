package Adven;

public class Checkout {
	int idbarang;
	String namabarang;
	int stokbarang;
	long hargabarang;
	

	public Checkout(int id, String nama, int stok, long harga) {
		super();
		this.idbarang = id;
		this.namabarang = nama;
		this.stokbarang = stok;
		this.hargabarang = harga;
	}
	

	public int getIdbarang() {
		return idbarang;
	}


	public void setIdbarang(int idbarang) {
		this.idbarang = idbarang;
	}


	public String getNamabarang() {
		return namabarang;
	}


	public void setNamabarang(String namabarang) {
		this.namabarang = namabarang;
	}


	public int getStokbarang() {
		return stokbarang;
	}


	public void setStokbarang(int stokbarang) {
		this.stokbarang = stokbarang;
	}


	public long getHargabarang() {
		return hargabarang;
	}


	public void setHargabarang(long hargabarang) {
		this.hargabarang = hargabarang;
	}



}
