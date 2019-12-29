package os;

import java.util.Vector;

import hardware.CentralProcessingUnit;

public class OperatingSystem {

	private UXManager uxManager;
	private ProcessManager processManager;
	private MemoryManager memoryManager;
	private FileManager fileManager;
	
	private CentralProcessingUnit centralProcessingUnit;
	
	public OperatingSystem(Vector<Process> processes) {
		this.processManager = new ProcessManager(processes);
		this.memoryManager = new MemoryManager();
		this.fileManager = new FileManager();
		
		this.uxManager = new UXManager();
	}
	
	public void associate(CentralProcessingUnit centralProcessingUnit) {
		this.processManager.associate(this.memoryManager, this.fileManager, this.centralProcessingUnit = centralProcessingUnit);
		this.uxManager.associate(this.processManager, this.fileManager);
	}
	
	public void run() {
		this.uxManager.run();
	}

}
