package file_system_1;

import java.io.File;
import java.util.ArrayList;

public class monitor_renamed extends Thread{
	filebag init;
	int operate;
	String path;
	summary sum;
	detail tail;
	monitor_renamed(filebag init){
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
	
	void rename(filebag temp_bag,filebag temp_file){
		ArrayList<filebag> ben_test = temp_bag.children;
		ArrayList<filebag> to_test = temp_file.children;
		int i,j;
		for(i=0;i<ben_test.size();i++){
			for(j=0;j<to_test.size();j++){
				if(same(ben_test.get(i),to_test.get(j))){
					if(to_test.get(j).wenjian==1){
						break;
					}else{
						rename(ben_test.get(i),to_test.get(j));
					}
				}else{
					if(ben_test.get(i).wenjian==1&&to_test.get(j).wenjian==1&&
					ben_test.get(i).parent.equals(to_test.get(j).parent)&&
					ben_test.get(i).time==to_test.get(j).time&&
					ben_test.get(i).bits==to_test.get(j).bits){
						if(operate==1){
							sum.rename++;
						}else if(operate==2){
							tail.save.get(0).add(ben_test.get(i).name+"----"+to_test.get(j).name);
						}else{
							File x = new File(ben_test.get(i).parent+File.separator+ben_test.get(i).name);
							File y = new File(to_test.get(j).parent+File.separator+to_test.get(j).name);
							y.renameTo(x);
						}//recover
					}
				}
				
			}
		}
	}
	
	synchronized public void run(){
		File temp = new File(path);
		if(temp.isDirectory()){
			for(;;){
				filebag now = new filebag(path);
				now.tree(now);/*snapshoot*/
				rename(init,now);
				if(operate==3){
					continue;
				}
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
				File x = new File(path);
				filebag y = new filebag(x.getParent());
				y.tree(y);
				for(int i=0;i<y.children.size();i++){
					if(y.children.get(i).wenjian==0){
						continue;
					}else{
						if(!y.children.get(i).name.equals(init.name)&&
						y.children.get(i).time==init.time&&
						y.children.get(i).bits==init.bits){
							if(operate==1){
								sum.rename++;
								init=y.children.get(i);
							}else if(operate==2){
								tail.save.get(0).add(init.name+"----"+y.children.get(i).name);
								init=y.children.get(i);
							}else{
								File xx = new File(init.parent+File.separator+init.name);
								File yy = new File(y.children.get(i).parent+File.separator+y.children.get(i).name);
								yy.renameTo(xx);
							}//recover
						}else{
							continue;
						}
					}
				}
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
