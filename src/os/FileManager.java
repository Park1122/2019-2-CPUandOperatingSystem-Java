package os;

import java.io.File;
import java.util.Scanner;

public class FileManager {

	public String getFile() {
		System.out.println("=====Show File in exe Folder=====");
		File folder = new File("exe");
		File[] listOfFiles = folder.listFiles();
		
		for(int i=0; i<listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()) {
				System.out.println(listOfFiles[i].getName());
			}
		}
		
		System.out.println("=========Select the File=========");
		System.out.println("Multi Processing�� �Ϸ��� ������ ���� �ؼ� ���ÿ�.");
		Scanner selectScanner = new Scanner(System.in);
		String files = selectScanner.nextLine();
		System.out.println("=================================");
		return files;
	}

}
