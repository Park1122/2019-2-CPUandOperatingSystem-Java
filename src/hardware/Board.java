package hardware;

public class Board {
	private CentralProcessingUnit centralProcessingUnit;
	private Memory memory;
	
	public Board() {
		this.centralProcessingUnit = new CentralProcessingUnit();
		this.memory = new Memory();
		
		this.centralProcessingUnit.connect(this.memory);
		this.memory.connect(this.centralProcessingUnit);
	}

	public void run() {
		this.memory.run();
		this.centralProcessingUnit.run();
	}
}
