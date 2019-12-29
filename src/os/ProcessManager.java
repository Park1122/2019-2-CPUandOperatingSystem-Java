package os;

import java.util.Vector;

import hardware.CentralProcessingUnit;

public class ProcessManager {

	// Association
	private Vector<Process> processes;
	private MemoryManager memoryManager;
	private FileManager fileManager;
	private CentralProcessingUnit centralProcessingUnit;
	
	// Component
	private Loader loader;
	private ProcessQueue readyQueue;
	private ProcessQueue ioQueue;
	
	// Constructor
	public ProcessManager(Vector<Process> processes) {
		this.processes=processes;
		this.loader = new Loader();
		this.readyQueue = new ProcessQueue();
		this.ioQueue = new ProcessQueue();
	}
	
	public void associate(MemoryManager memoryManager, FileManager fileManager, CentralProcessingUnit centralProcessingUnit) {
		this.memoryManager = memoryManager;
		this.fileManager = fileManager;
		this.centralProcessingUnit = centralProcessingUnit;
	}

	public void storeProcess(String files) {
		for(String fileName : files.split("\\s")) {
			Process process = this.loader.load("exe/"+fileName);
			this.readyQueue.enqueue(process);
			this.processes.add(process);
		}
		execute();
	}

	public void execute(){
		Process process = this.readyQueue.dequeue();
		this.centralProcessingUnit.setID(process.getPcb().getId());
		this.centralProcessingUnit.controlUnit.run();
	}
}
