package file_system_1;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class filebag {
	String name;
	long time;
	long bits;
	String parent;
	int wenjian;
	ArrayList<filebag> children;
	
	filebag(String path){
		File self;
		self = new File(path);
		if(self.isFile()){
			wenjian=1;
		}else{
			wenjian=0;
		}
		name = self.getName();
		time = self.lastModified();
		bits = self.length();
		parent = self.getParent();
		children = new ArrayList<filebag>();
	}
	
	void tree(filebag file_bag){
		File self = new File(file_bag.parent+File.separator+file_bag.name);
		File[] temp = self.listFiles();
		if(temp == null){
			return;
		}
		for(int i=0;i<temp.length;i++){
			if(temp[i].isFile()){
				file_bag.children.add(new filebag(temp[i].getAbsolutePath()));
			}else{
				file_bag.children.add(new filebag(temp[i].getAbsolutePath()));
				tree(file_bag.children.get(i));
			}
		}
	}
	
}


