package os;

public class UXManager {

	// Associate
	private ProcessManager processManager;
	private FileManager fileManager;

	public void associate(ProcessManager processManager, FileManager fileManager) {
		this.processManager = processManager;
		this.fileManager = fileManager;
	}
	
	public void run() {this.processManager.storeProcess(this.fileManager.getFile());}
}
