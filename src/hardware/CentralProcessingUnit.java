package hardware;

public class CentralProcessingUnit {
	
	// Association
	private Memory memory;
	
	// Component
	public ControlUnit controlUnit;
	private ArithmeticLogicUnit arithmeticLogicUnit;
	private Register pc, sp, mar, mbr, ir, ac;
	private StatusRegister status;

	// Constructor
	public CentralProcessingUnit() {
		this.controlUnit = new ControlUnit();
		this.arithmeticLogicUnit = new ArithmeticLogicUnit();
		this.pc = new Register(0); this.sp = new Register(); this.mar = new Register();
		this.mbr = new Register(); this.ir = new Register(); this.ac = new Register();
		this.status = new StatusRegister();
	}
	
	public void connect(Memory memory) {
		this.controlUnit.connect(this.pc, this.sp, this.mar, this.mbr, this.ir, this.ac, this.status);
		this.controlUnit.connect(this.arithmeticLogicUnit);
		this.arithmeticLogicUnit.connect(this.ac, this.mbr, this.status);
		this.memory = memory;
		this.controlUnit.connect(this.memory);
	}

	public void run() {
	}

	// Getter & Setter
	public Register getMAR() {return this.mar;}
	public Register getMBR() {return this.mbr;}
	public void setID(int id){
		this.memory.currentID = id;
	}
	public Memory getMemory() {return this.memory;}
}
