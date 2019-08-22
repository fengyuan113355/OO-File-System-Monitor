package file_system_1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class detail extends Thread{
	FileOutputStream out;
	ArrayList<ArrayList<String>> save;
	
	detail(){
		try {
			this.out = new FileOutputStream("detail.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.exit(0);
			e.printStackTrace();
		}
		save = new ArrayList<ArrayList<String>>();
		for(int i=0;i<4;i++){
			save.add(new ArrayList<String>());
		}
	}
	
	void setinfo(int place,String info){
		save.get(place).add(info);
	}
	void delete(ArrayList<ArrayList<String>> temp_save){
		int i,j;
		for(i=0;i<4;i++){
			for(;temp_save.get(i).size()!=0;){
				temp_save.get(i).remove(0);
			}
		}
	}
	synchronized public void run(){
		int i,j;
		while(true){
			for(i=0;i<4;i++){
				for(j=0;j<save.get(i).size();j++){
					if(i==0){
						try {
							out.write(("rename:"+save.get(i).get(j)+"\r\n").getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.exit(0);
							e.printStackTrace();
						}
					}else if(i==1){
						try {
							out.write(("modify:"+save.get(i).get(j)+"\r\n").getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.exit(0);
							e.printStackTrace();
						}
					}else if(i==2){
						try {
							out.write(("path-change:"+save.get(i).get(j)+"\r\n").getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.exit(0);
							e.printStackTrace();
						}
					}else{
						try {
							out.write(("size-change:"+save.get(i).get(j)+"\r\n").getBytes());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							System.exit(0);
							e.printStackTrace();
						}
					}
				}
			}
			delete(save);
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
}
