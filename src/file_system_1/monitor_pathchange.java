package file_system_1;

import java.io.File;
import java.util.ArrayList;

public class monitor_pathchange extends Thread{
	filebag init;
	int operate;
	String path;
	summary sum;
	detail tail;
	monitor_pathchange(filebag init){
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
	
	boolean survive_in_after(filebag initial,filebag nowfile){
		int i = 0;
		ArrayList<filebag> temp = nowfile.children;
		for(;i<temp.size();i++){
			if(temp.get(i).wenjian==1){
				if(same(initial,temp.get(i))){
					return true;
				}
			}else{
				if(survive_in_after(initial,temp.get(i))){
					return true;
				}
			}
		}
		return false;
	}/*only consider file for path_change*/
	
		
	void pathchange1(ArrayList<filebag> temp_delete,filebag temp_init,filebag temp_now){
		int i;
		for(i=0;i<temp_init.children.size();i++){
			if(survive_in_after(temp_init.children.get(i),temp_now)){
				if(temp_init.children.get(i).wenjian==0){
					pathchange1(temp_delete,temp_init.children.get(i),temp_now);
				}
			}else{
				if(temp_init.children.get(i).wenjian==1){
					temp_delete.add(temp_init.children.get(i));
				}else{
					pathchange1(temp_delete,temp_init.children.get(i),temp_now);///
				}
			}
		}
	}
	void pathchange2(ArrayList<filebag> temp_add,filebag temp_init,filebag temp_now){
		int i;
		for(i=0;i<temp_now.children.size();i++){
			if(survive_in_after(temp_now.children.get(i),temp_init)){
				if(temp_now.children.get(i).wenjian==0){
					pathchange2(temp_add,temp_init,temp_now.children.get(i));
				}
			}else{
				if(temp_now.children.get(i).wenjian==1){
					temp_add.add(temp_now.children.get(i));
				}else{
					pathchange2(temp_add,temp_init,temp_now.children.get(i));
				}
			}
		}
	}
	
	void pd(ArrayList<filebag> temp_add,ArrayList<filebag> temp_delete){
		int i=0,j=0;
		for(;i<temp_add.size();i++){
			for(;j<temp_delete.size();j++){
				if(temp_add.get(i).name.equals(temp_delete.get(j).name)&&
				temp_add.get(i).bits==temp_delete.get(j).bits&&
				temp_add.get(i).time==temp_delete.get(j).time&&
				!temp_add.get(i).parent.equals(temp_delete.get(j).parent)){
					if(operate==1){
						sum.path++;
					}else if(operate==2){
						tail.save.get(2).add(temp_add.get(i).parent+"---"+temp_delete.get(j).parent);
					}else{
						File x = new File(temp_add.get(i).parent+File.separator+temp_add.get(i).name);
						File y = new File(temp_delete.get(j).parent+File.separator+temp_delete.get(j).name);
						x.renameTo(y);
					}
				}
			}
		}
	}
	
	
	synchronized public void run(){
		File temp = new File(path);
		if(temp.isDirectory()){
			for(;;){
				ArrayList<filebag> temp_delete = new ArrayList<filebag>();
				ArrayList<filebag> temp_add = new ArrayList<filebag>();
				filebag now = new filebag(path);
				now.tree(now);/*snapshoot*/
				pathchange1(temp_delete,init,now);
				pathchange2(temp_add,init,now);
				pd(temp_add,temp_delete);
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
			String identify = (new File(path)).getParent();
			filebag moov = init;
			for(;;){
				init = new filebag(identify);
				init.tree(init);//init moov
				String[] a = new String[1];
				if(pathchange(init,moov,a)&&operate!=3){
					moov = new filebag(a[0]);
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
	
	boolean pathchange(filebag temp_init,filebag temp_moov,String[] a){
		int i;
		for(i=0;i<temp_init.children.size();i++){
			if(temp_init.children.get(i).wenjian==1){
				if(temp_init.children.get(i).name.equals(temp_moov.name)&&
				!temp_init.children.get(i).parent.equals(temp_moov.parent)&&
				temp_init.children.get(i).bits==temp_moov.bits&&
				temp_init.children.get(i).time==temp_moov.time){
					a[0] = temp_init.children.get(i).parent+File.separator+temp_init.children.get(i).name;
					if(operate==1){
						sum.path++;
					}else if(operate==2){
						tail.save.get(2).add(temp_init.children.get(i).parent+"---"+temp_moov.parent);
					}else{
						File x = new File(temp_init.children.get(i).parent+File.separator+temp_init.children.get(i).name);
						File y = new File(temp_moov.parent+File.separator+temp_moov.name);
						x.renameTo(y);
					}
					return true;
				}
			}else{
				if(pathchange(temp_init.children.get(i),temp_moov,a)){
					return true;
				}
			}
		}
		return false;
	}
}







