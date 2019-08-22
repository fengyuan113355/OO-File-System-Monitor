package file_system_1;

import java.io.File;
import java.util.ArrayList;

public class monitor_modify extends Thread{
	filebag init;
	int operate;
	String path;
	summary sum;
	detail tail;
	monitor_modify(filebag init){
		this.init = init;
		this.init.tree(this.init);
	}
	void set_operate(int a,String path,summary sum,detail tail){
		this.operate=a;
		this.path=path;
		this.sum=sum;
		this.tail=tail;
	}
	
	boolean same(filebag initial,filebag nowfile){
		if(initial.wenjian==1&&nowfile.wenjian==1&&
		initial.parent.equals(nowfile.parent)&&
		initial.time==nowfile.time&&
		initial.bits==nowfile.bits&&
		initial.name.equals(nowfile.name)){
			return true;
		}else if(initial.wenjian==0&&nowfile.wenjian==0&&
		initial.parent.equals(nowfile.parent)&&
		initial.time==nowfile.time&&
		initial.bits==nowfile.bits&&
		initial.name.equals(nowfile.name)){
			return true;
		}else{
			return false;
		}
	}
	
	void modify(filebag temp_bag,filebag temp_file){
		ArrayList<filebag> ben_test = temp_bag.children;
		ArrayList<filebag> to_test = temp_file.children;
		int i,j;
		for(i=0;i<ben_test.size();i++){
			for(j=0;j<to_test.size();j++){
				if(same(ben_test.get(i),to_test.get(j))){
					modify(ben_test.get(i),to_test.get(j));
				}else{
					if(ben_test.get(i).wenjian==1&&to_test.get(j).wenjian==1&&
					ben_test.get(i).parent.equals(to_test.get(j).parent)&&
					ben_test.get(i).name.equals(to_test.get(j).name)&&
					ben_test.get(i).time!=to_test.get(j).time){
						if(operate==1){
							sum.modify++;
						}else{
							tail.save.get(1).add(ben_test.get(i).parent+File.separator+ben_test.get(i).name+" 1:"+ben_test.get(i).time+" 2:"+to_test.get(j).time);
						}
					}else if(ben_test.get(i).wenjian==0&&to_test.get(j).wenjian==0&&
					ben_test.get(i).parent.equals(to_test.get(j).parent)&&
					ben_test.get(i).name.equals(to_test.get(j).name)&&
					ben_test.get(i).time!=to_test.get(j).time){
						if(operate==1){
							sum.modify++;
						}else{
							tail.save.get(1).add(ben_test.get(i).parent+File.separator+ben_test.get(i).name+" 1:"+ben_test.get(i).time+" 2:"+to_test.get(j).time);
						}
						modify(ben_test.get(i),to_test.get(j));
					}
				}
			}
		}
		
	}
	
	filebag modifyfile(filebag old,filebag present){
		for(int i=0;i<present.children.size();i++){
			if(present.children.get(i).wenjian==1){
				if(old.parent.equals(present.children.get(i).parent)&&
				old.name.equals(present.children.get(i).name)&&
				old.time!=present.children.get(i).time){
					if(operate==1){
						sum.modify++;
					}else{
						tail.save.get(1).add(old.parent+"\\"+old.name+" 1:"+old.time+" 2:"+present.children.get(i).time);
					}
					return new filebag(old.parent+File.separator+old.name);
				}
			}else{
				continue;
			}
		}
		return old;
	}
	
	synchronized public void run(){
		File temp = new File(path);
		if(temp.isDirectory()){
			for(;;){
				File x = new File(path);
				if(!x.exists()){
					continue;
				}
				filebag now = new filebag(path);
				now.tree(now);/*snapshoot*/
				modify(init,now);
				init = now;
				try {
					wait(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.exit(0);
					e.printStackTrace();
				}
			}
		}else{
			for(;;){
				File y = new File(path);
				if(!y.exists()){
					continue;
				}
				filebag now = new filebag(y.getParent());
				now.tree(now);/*snapshoot*/
				init = modifyfile(init,now);
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
}	













