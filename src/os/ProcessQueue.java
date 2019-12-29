package os;

import java.util.Vector;

public class ProcessQueue {
	
	// Component
	private Vector<Process> queues;
	
	// Constructor
	public ProcessQueue() {this.queues = new Vector<Process>();}
	
	// Getter & Setter
	public void enqueue(Process process) {this.queues.add(process);}
	public Process dequeue() {return this.queues.remove(0);}
	public int getSize() {return this.queues.size();}
}
