package hardware;

import java.util.Vector;

import os.Process.ERegister;
import os.Process.PCB;
import view.MainFrame;

public class ControlUnit {

	private Register pc, sp, mar, mbr, ir, ac;
	private StatusRegister status;
	private ArithmeticLogicUnit arithmeticLogicUnit;
	private Memory memory;
	
	public enum EInstruction {LDI, LDA, STA, ADDI, ADDA, SUBI, SUBA, CMP, JEZ, JGZ, JLZ, JGE, HLT, PRT}; // JEZ(==), JGZ(>), JLZ(<), JGE(>=), JLE(<=)
	enum EState {halt, running};
	private EState eState;
	
	MainFrame mainFrame;
	public ControlUnit() {
		this.mainFrame = new MainFrame();
		this.mainFrame.initialize();
		this.eState = EState.halt;
	}
	
	public void connect(Register pc, Register sp, Register mar, Register mbr, Register ir, Register ac,
			StatusRegister status) {
		this.pc = pc;
		this.sp = sp;
		this.mar = mar;
		this.mbr = mbr;
		this.ir = ir;
		this.ac = ac;
		this.status = status;
	}

	public void connect(ArithmeticLogicUnit arithmeticLogicUnit) {
		this.arithmeticLogicUnit = arithmeticLogicUnit;	
	}
	public void connect(Memory memory) {
		this.memory = memory;
	}

	int haltPoint =0;
	int timeSlice = 2;
	int nowTimeSlice = 0;
	
	public void run() {
		this.eState = EState.running;
		for (int i = 0; i < this.memory.getSize(); i++) {
			this.mainFrame.addProcessID(this.memory.getProcess(i).getFileName(), i);
		}
		while(haltPoint < 3) {
			this.fetch();
			this.decode();
			nowTimeSlice++;
			if(timeSlice == nowTimeSlice) {
				this.pcbStore();
				this.memory.currentID+=1; // if limitÀÌ¸é ->0
				if(this.memory.currentID==this.memory.getSize()) {this.memory.currentID = 0;}
				this.pcbLoad();
				nowTimeSlice=0;
				this.mainFrame.addContent("Time Expired");
			}
			try {Thread.sleep(10);} catch (InterruptedException e) {}
		}
	}
	
	private void pcbLoad() {
		os.Process process = this.memory.getProcess(this.memory.currentID);
		PCB pcb = process.getPcb();
		Vector<Register> registers = pcb.getRegisters();
		this.pc.setData(registers.get(ERegister.ePC.ordinal()).getData());
		this.sp.setData(registers.get(ERegister.eSP.ordinal()).getData());
		this.mar.setData(registers.get(ERegister.eMAR.ordinal()).getData());
		this.mbr.setData(registers.get(ERegister.eMBR.ordinal()).getData());
		this.ir.setData(registers.get(ERegister.eIR.ordinal()).getData());
		this.ac.setData(registers.get(ERegister.eAC.ordinal()).getData());		
	}

	private void pcbStore() {
		os.Process process = this.memory.getProcess(this.memory.currentID);
		PCB pcb = process.getPcb();
		Vector<Register> registers = pcb.getRegisters();
		registers.get(ERegister.ePC.ordinal()).setData(this.pc.getData());
		registers.get(ERegister.eSP.ordinal()).setData(this.sp.getData());
		registers.get(ERegister.eMAR.ordinal()).setData(this.mar.getData());
		registers.get(ERegister.eMBR.ordinal()).setData(this.mbr.getData());
		registers.get(ERegister.eIR.ordinal()).setData(this.ir.getData());
		registers.get(ERegister.eAC.ordinal()).setData(this.ac.getData());
	}

	public void fetch() {
		this.mar.setData(this.pc.getData());
		this.memory.fetch(Memory.CODE);
		this.ir.setData(this.mbr.getData());
	}

	public void decode() {
		int instruction = this.ir.getData() >>> 16;
		switch (EInstruction.values()[instruction]) {
		case LDI:	LDI();	break;	case LDA:	LDA();	break;	case STA:	STA();	break;
		case ADDI:	ADDI();	break;	case ADDA:	ADDA(); break;	case SUBI:	SUBI();	break;
		case SUBA:	SUBA(); break;	case CMP:	CMP();	break;	case JEZ:	JEZ();	break;
		case JGZ:	JGZ();	break;	case JLZ:	JLZ();	break;	case JGE:	JGE();	break;
		case HLT:	HLT();	break;	case PRT: 	PRT();	break;
		default:
			break;
		}
	}
	
	private void PRT() {
		int address = this.ir.getData() & 0x0000ffff;
		int addressA = address + this.sp.getData();
		this.mar.setData(addressA);
		this.memory.fetch(Memory.DATA);
		this.mainFrame.print("processID: "+this.memory.currentID+" -> "+"print"+ this.mbr.getData());
		this.pc.setData(this.pc.getData()+1);
	}

	private void LDI() {
		int value = this.ir.getData() & 0x0000ffff;
		this.ac.setData(value);
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"LDI");
		this.pc.setData(this.pc.getData()+1);
	}
	private void LDA() {
		int address = this.ir.getData() & 0x0000ffff;
		int addressA = address + this.sp.getData();
		this.mar.setData(addressA);
		this.memory.fetch(Memory.DATA);
		this.ac.setData(this.mbr.getData());
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"LDA");
		this.pc.setData(this.pc.getData()+1);
	}
	private void STA() {
		int address = this.ir.getData() & 0x0000ffff;
		address = address + this.sp.getData();
		this.mar.setData(address);
		this.mbr.setData(this.ac.getData());
		this.memory.store();
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"STA");
		this.pc.setData(this.pc.getData()+1);
	}
	private void ADDI() {
		int address = this.ir.getData() & 0x0000ffff;
		this.arithmeticLogicUnit.add(this.ac.getData(), address);
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"ADDI");
		this.pc.setData(this.pc.getData()+1);
	}
	private void ADDA(){
		int address = this.ir.getData() & 0x0000ffff;
		address = address + this.sp.getData();
		this.mar.setData(address);
		this.memory.fetch(Memory.DATA);
		this.arithmeticLogicUnit.add(this.ac.getData(), this.mbr.getData());
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"ADDA");
		this.pc.setData(this.pc.getData()+1);
	}
	private void SUBI() {
		int address = this.ir.getData() & 0x0000ffff;
		this.arithmeticLogicUnit.sub(this.ac.getData(), address);
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"SUBI");
		this.pc.setData(this.pc.getData()+1);
	}
	private void SUBA(){
		int address = this.ir.getData() & 0x0000ffff;
		address = address + this.sp.getData();
		this.mar.setData(address);
		this.memory.fetch(Memory.DATA);
		this.arithmeticLogicUnit.sub(this.ac.getData(), this.mbr.getData());
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"SUBA");
		this.pc.setData(this.pc.getData()+1);
	}
	private void CMP() {
		if(this.ac.getData()>0){
			this.status.setNegativeFlag(false);
			this.status.setZeroFlag(false);
		}else if(this.ac.getData()<0){
			this.status.setNegativeFlag(true);
			this.status.setZeroFlag(false);
		}else if(this.ac.getData()==0){
			this.status.setNegativeFlag(false);
			this.status.setZeroFlag(true);
		}
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"CMP");
		this.pc.setData(this.pc.getData()+1);
	}
	private void JEZ() {
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"JEZ");
		if(this.status.isZeroFlag()) {
			int address = this.ir.getData() & 0x0000ffff;
			this.pc.setData(address);
		}else{
			this.pc.setData(this.pc.getData()+1);
		}
	}
	private void JGZ() {
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"JGZ");
		if(!this.status.isNegativeFlag()) {
			int address = this.ir.getData() & 0x0000ffff;
			this.pc.setData(address);
		}else{
			this.pc.setData(this.pc.getData()+1);
		}
	}
	private void JGE(){
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"JGE");
		if((!this.status.isNegativeFlag())||this.status.isZeroFlag()){
			int address = this.ir.getData() & 0x0000ffff;
			this.pc.setData(address);
		}else{
			this.pc.setData(this.pc.getData()+1);
		}
	}
	private void JLZ() {
		this.mainFrame.addContent("processID: "+this.memory.currentID+" -> "+"JLZ");
		if(this.status.isNegativeFlag()) {
			int address = this.ir.getData() & 0x0000ffff;
			this.pc.setData(address);
		}else{
			this.pc.setData(this.pc.getData()+1);
		}
	}
	private void HLT() {
		haltPoint++;
//		this.eState = EState.halt;
//		System.out.println("HLT");
	}
}
