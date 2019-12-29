package hardware;

import java.util.Vector;

import os.OperatingSystem;
import os.Process;

public class Memory {

    // Association
    private Register mar, mbr;
    private CentralProcessingUnit centralProcessingUnit;

	// Component
	private OperatingSystem operatingSystem;
	private Vector<os.Process> processes;
	public int currentID;
	public static final int CODE = 0, DATA = 1;

    // Constructor
    public Memory() {
        this.processes = new Vector<os.Process>();
        this.operatingSystem = new OperatingSystem(this.processes);
    }

    public void connect(CentralProcessingUnit centralProcessingUnit) {
        this.centralProcessingUnit = centralProcessingUnit;
        this.mar = this.centralProcessingUnit.getMAR();
        this.mbr = this.centralProcessingUnit.getMBR();
        this.operatingSystem.associate(this.centralProcessingUnit);
    }

    public void run() {
        this.operatingSystem.run();
    }

    public void fetch(int mode) {
    	switch (mode){
			case CODE : this.mbr.setData(this.getProcess(this.currentID).fetchCode(this.mar.getData())); break;
			case DATA : this.mbr.setData(this.getProcess(this.currentID).fetchData(this.mar.getData())); break;
		}
    }

    public void store() {this.getProcess(this.currentID).storeData(this.mar.getData(), this.mbr.getData());}
    public Process getProcess(int id) {return this.processes.get(id);}
    public int getSize() {
    	return this.processes.size();
    }
}
