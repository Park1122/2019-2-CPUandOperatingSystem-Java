package os;

import java.util.Vector;

import hardware.Register;
import hardware.StatusRegister;

public class Process {

    public enum EState {nnew, running, wait, ready, terminated}
    public enum ERegister {ePC, eSP, eMAR, eMBR, eIR, eAC}
    public enum EInterrupt {eIO, eTerminate}

    private int[] codeSegment;
    private int[] dataSegment;
    private String fileName;

    private PCB pcb;

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    public Process(int stackSegmentSize, int[] codes, String fileName) {
        this.pcb = new PCB();
        this.dataSegment = new int[stackSegmentSize];
        this.codeSegment = codes;
        this.fileName = fileName;
    }
    
    public String getFileName() {
    	return this.fileName;
    }

    public int fetchCode(int index) {
        return this.codeSegment[index];
    }

    public int fetchData(int index) {
        return this.dataSegment[index/4];
    }

    public int storeData(int index, int value) {
        return this.dataSegment[index/4] = value;
    }

    private static int pID = 0;
    
    public class PCB {
    	
        private int id;
        private EState eState;
        private Vector<Register> registers;
        private StatusRegister status;

        public PCB() {
            this.id = pID++;
            this.eState = EState.ready;
            this.registers = new Vector<>();
            for (ERegister eRegister : ERegister.values()) {
                this.registers.add(new Register());
            }
            this.status = new StatusRegister();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public EState geteState() {
            return eState;
        }

        public void seteState(EState eState) {
            this.eState = eState;
        }

        public Vector<Register> getRegisters() {
            return registers;
        }

        public void setRegisters(Vector<Register> registers) {
            this.registers = registers;
        }

        public StatusRegister getStatus() {
            return status;
        }

        public void setStatus(StatusRegister status) {
            this.status = status;
        }

    }
}
