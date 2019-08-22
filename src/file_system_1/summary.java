package file_system_1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class summary extends Thread{
	FileOutputStream out;
	int rename;
	int modify;
	int path;
	int size;
	
	summary(){
		try {
			this.out = new FileOutputStream("summary.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.exit(0);
			e.printStackTrace();
		}
		this.rename = 0;
		this.modify = 0;
		this.path = 0;
		this.size = 0;
	}
	
	void set(int kind){
		if(kind==1){
			rename++;
		}else if(kind==2){
			modify++;
		}else if(kind==3){
			path++;
		}else{
			size++;
		}
	}
	
	synchronized public void run(){
		while(true){
			try {
				out.write(("rename:"+rename+"\r\n").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
			try {
				out.write(("modify:"+modify+"\r\n").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
			try {
				out.write(("path-change:"+path+"\r\n").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
			try {
				out.write(("size-change:"+size+"\r\n").getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
			try {
				wait(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
}













