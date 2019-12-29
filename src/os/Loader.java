package os;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

import hardware.ControlUnit.EInstruction;

public class Loader {
	
	public Process load(String fileName) { // include compiling
		try {
			int stackSegmentSize = 0;
			int[] codes = null;
			File file = new File(fileName);
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				if(line.equals(".stack")) {
					stackSegmentSize = this.parseStack(sc);
				}else if(line.equals(".code")) {
					codes = this.parseCode(sc);
				}
			}
			System.out.println("==========프로세스생성==========");
			Process process = new Process(stackSegmentSize, codes, fileName);
			return process;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	private int parseStack(Scanner sc) {
		String stackLine = sc.nextLine();
		String[] stackArray = stackLine.split("\\s");
		int size = Integer.parseInt(stackArray[2]);
		return size;
	}
	
	private int[] parseCode(Scanner sc) {
		Vector<String> tmp = new Vector<>();
		while(sc.hasNextLine()) {
			String codeLine = sc.nextLine();
			if(codeLine.contains("//")) {
				System.out.println("주석문: " + codeLine);
			}else if(codeLine.isEmpty()) {
				System.out.println("비어있음");
			}else {
				tmp.add(codeLine);
				System.out.println("코드문: " + codeLine);
			}
		}
		
		int[] temp = new int[tmp.size()];
		for(int i=0; i<tmp.size(); i++) {
			String[] tmpSt = tmp.elementAt(i).split("\\s");
			String opcode = tmpSt[0];
			int codeArray = 0;
			int	num = Integer.parseInt(tmpSt[1]);
			for (EInstruction instruction: EInstruction.values()){
				if (instruction.name().equals(opcode)) {
					codeArray = (instruction.ordinal()<<16) + num;
					break;
				}
			}
			temp[i] = codeArray;
		}
		return temp;
	}
}
