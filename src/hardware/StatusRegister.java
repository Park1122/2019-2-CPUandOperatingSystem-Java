package hardware;

public class StatusRegister {

	private boolean zeroFlag; // zero�� ��� ��.
	private boolean negativeFlag; // �������� ������ �� ��.
	private boolean timeOutFlag;
	private boolean terminatedFlag;
	private boolean ioInterruptFlag;
	
	public boolean isZeroFlag() {return zeroFlag;}
	public boolean isNegativeFlag() {return negativeFlag;}
	public boolean isTimeOutFlag() {return timeOutFlag;}
	public boolean isTerminatedFlag() {return terminatedFlag;}
	public boolean isIoInterruptFlag() {return ioInterruptFlag;}
	public void setZeroFlag(boolean zeroFlag) {this.zeroFlag = zeroFlag;}
	public void setNegativeFlag(boolean negativeFlag) {this.negativeFlag = negativeFlag;}
	public void setTimeOutFlag(boolean timeOutFlag) {this.timeOutFlag = timeOutFlag;}
	public void setTerminatedFlag(boolean terminatedFlag) {this.terminatedFlag = terminatedFlag;}
	public void setIoInterruptFlag(boolean ioInterruptFlag) {this.ioInterruptFlag = ioInterruptFlag;}
}
