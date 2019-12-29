package hardware;

public class ArithmeticLogicUnit {

	private Register ac, mbr;
	private StatusRegister status;
	
	public void connect(Register ac, Register mbr, StatusRegister status) {
		this.ac = ac;
		this.mbr = mbr;
		this.status = status;
	}

	public void add(int x1, int y1) {
		this.ac.setData(x1 + y1);
	}

	public void sub(int x2, int y2) {
		this.ac.setData(x2 - y2);
	}

}
