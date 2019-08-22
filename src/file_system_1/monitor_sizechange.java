package file_system_1;

import java.io.File;
import java.util.ArrayList;

public class monitor_sizechange extends Thread{
	filebag init;
	int operate;
	String path;
	summary sum;
	detail tail;
	monitor_sizechange(filebag init){
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
		initial.bits==nowfile.bits&&
		initial.name.equals(nowfile.name)){
			return true;
		}else if(initial.wenjian==0&&nowfile.wenjian==0&&
		initial.parent.equals(nowfile.parent)&&
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
	
		
	void sizechange1(ArrayList<filebag> temp_delete,filebag temp_init,filebag temp_now){
		int i;
		for(i=0;i<temp_init.children.size();i++){
			if(survive_in_after(temp_init.children.get(i),temp_now)){
				if(temp_init.children.get(i).wenjian==0){
					sizechange1(temp_delete,temp_init.children.get(i),temp_now);
				}
			}else{
				if(temp_init.children.get(i).wenjian==1){
					temp_delete.add(temp_init.children.get(i));
				}else{
					//temp_delete.add(temp_init.children.get(i));
					sizechange1(temp_delete,temp_init.children.get(i),temp_now);///
				}
			}
		}
	}
	void sizechange2(ArrayList<filebag> temp_add,filebag temp_init,filebag temp_now){
		int i;
		for(i=0;i<temp_now.children.size();i++){
			if(survive_in_after(temp_now.children.get(i),temp_init)){
				if(temp_now.children.get(i).wenjian==0){
					sizechange2(temp_add,temp_init,temp_now.children.get(i));
				}
			}else{
				if(temp_now.children.get(i).wenjian==1){
					temp_add.add(temp_now.children.get(i));
				}else{
					//temp_add.add(temp_now.children.get(i));
					sizechange2(temp_add,temp_init,temp_now.children.get(i));
				}
			}
		}
	}
	
	
	synchronized public void run(){
		File temp = new File(path);
		if(temp.isDirectory()){
			for(;;){
				if(!temp.exists()){
					continue;
				}
				ArrayList<filebag> temp_delete = new ArrayList<filebag>();
				ArrayList<filebag> temp_add = new ArrayList<filebag>();
				filebag now = new filebag(path);
				now.tree(now);/*snapshoot*/
				sizechange1(temp_delete,init,now);
				sizechange2(temp_add,init,now);
				int i,j,label;
				for(i=0;i<temp_delete.size();i++){
					for(j=0;j<temp_add.size();j++){
						if(temp_delete.get(i).parent.equals(temp_add.get(j).parent)&&
						temp_delete.get(i).name.equals(temp_add.get(j).name)&&
						temp_delete.get(i).bits!=temp_add.get(j).bits){
							//System.out.println("size-change:"+temp_delete.get(i).bits+"\\/"+temp_add.get(j).bits);
							if(operate==1){
								sum.size++;
							}else{
								tail.save.get(3).add(temp_delete.get(i).bits+"\\/"+temp_add.get(j).bits);
							}
							temp_delete.remove(i);
							temp_add.remove(j);
							i--;
							continue;
						}
					}
				}
				for(i=0;i<temp_delete.size();i++){
					//System.out.println("size-change:"+temp_delete.get(i).bits+"\\/"+0);
					if(operate==1){
						sum.size++;
					}else{
						tail.save.get(3).add(temp_delete.get(i).bits+"\\/"+0);
					}
				}
				for(i=0;i<temp_add.size();i++){
					//System.out.println("size-change:"+0+"\\/"+temp_add.get(i).bits);
					if(operate==1){
						sum.size++;
					}else{
						tail.save.get(3).add(0+"\\/"+temp_add.get(i).bits);
					}
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
			for(int k=1;;){
				File y = new File(path);
				if(k==0){
					if(y.exists()){
						if(operate==1){
							sum.size++;
						}else{
							tail.save.get(3).add(0+"\\/"+y.length());
						}
						k=1;
						init = new filebag(path);
					}
				}else{
					if(!y.exists()){
						if(operate==1){
							sum.size++;
						}else{
							tail.save.get(3).add(init.bits+"\\/"+0);
						}
						k=0;
					}else{
						if(init.bits!=y.length()){
							if(operate==1){
								sum.size++;
							}else{
								tail.save.get(3).add(init.bits+"\\/"+y.length());
							}
							init = new filebag(path);
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
