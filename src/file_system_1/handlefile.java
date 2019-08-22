package file_system_1;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class handlefile {
	
	void rename(String path,String old_name,String new_name){
		File oldfile = new File(path+File.separator+old_name);
		File newfile = new File(path+File.separator+new_name);
		if(!oldfile.exists()){
			System.out.println("file doesn't exist");
		}else if(newfile.exists()){
			System.out.println("file has existed");
		}else{
			oldfile.renameTo(newfile);
		}
	}
	
	void pathchange(String oldpath,String newpath){
		File oldfile = new File(oldpath);
		File newfile = new File(newpath);
		if(!oldfile.exists()){
			System.out.println("file doesn't exist");
		}else if(newfile.exists()){
			System.out.println("file has existed");
		}else{
			oldfile.renameTo(newfile);
		}
	}
	
	void createfile(String path,String name){
		File parentfile = new File(path);
		File childfile = new File(path+File.separator+name);
		if(!parentfile.exists()){
			System.out.println("parentfile doesn't exist");
		}else if(childfile.exists()){
			System.out.println("childfile has existed");
		}else{
			try {
				childfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.exit(0);
				e.printStackTrace();
			}
		}
	}
	
	void createdir(String path,String name){
		File parentfile = new File(path);
		File childfile = new File(path+File.separator+name);
		if(!parentfile.exists()){
			System.out.println("parentfile doesn't exist");
		}else if(childfile.exists()){
			System.out.println("childfile has existed");
		}else{
			childfile.mkdir();
		}
	}
	
	void deletefile(String path){
		File file = new File(path);
		if(!file.exists()){
			System.out.println("file doesn't exist");
		}else{
			if(file.isFile()){
				file.delete();
			}
			else{
				delete(file);
			}
		}
	}
	
	void delete(File file){
		File[] list = file.listFiles();
		int i=0;
		for(;i<list.length;i++){
			if(list[i].isFile()){
				list[i].delete();
			}else{
				delete(list[i]);
			}
		}
	}
	
	void modify(String path){
		File file = new File(path);
		if(!file.exists()){
			System.out.println("file doesn't exist");
		}else{
			file.setLastModified(System.currentTimeMillis());
		}
	}
	
	public void operate(){
		//void rename(String path,String old_name,String new_name)
		//void pathchange(String oldpath,String newpath)
		//void createfile(String path,String name)
		//void createdir(String path,String name)
		//void deletefile(String path)
		//void delete(File file)
		//void modify(String path)
	}
}











