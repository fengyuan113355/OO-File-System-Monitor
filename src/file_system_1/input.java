package file_system_1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class input {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a= "renamed";               //1
		String b= "modify";                //2
		String c= "path-changed";          //3
		String d= "size-changed";          //4
		String A= "record-summary";        //5
		String B= "record-detail";         //6
		String C= "recover";               //7
		
		int i;
		String path,touch,operate;
		Scanner sc = new Scanner(System.in);
		Pattern dil = Pattern.compile("IF,.+,((renamed)|(modify)|(path-changed)|(size-changed)),THEN,((record-summary)|(record-detail)|(recover))");
		ArrayList<func> save = new ArrayList<func>();
		summary sum = new summary();
		detail tail = new detail();
		for(;;){
			String m = sc.nextLine();
			m = m.replaceAll(" ","");
			m = m.replaceAll("\t","");
			Matcher dil_temp = dil.matcher(m);
			boolean pp = dil_temp.matches();
			if(!pp){
				if(m.equals("end")){
					break;
				}
				else{
					System.out.println("INVALID"+m);
					continue;
				}
			}else{
				String[] fetch=m.split("[,]");
				path = fetch[1];
				touch = fetch[2];
				operate = fetch[4];
				if((touch.equals("modify")|touch.equals("size-changed"))&&operate.equals("recover")){
					System.out.println("INVALID"+m);
					continue;
				}
				File temp = new File(path);
				if(!temp.exists()){
					System.out.println("INVALID"+m);
					continue;
				}
				save.add(new func(touch,operate,path));
			}
		}
		for(i=0;i<save.size();i++){
			System.out.println(save.get(i).touch);
			if(save.get(i).touch.equals("renamed")){
				filebag test = new filebag(save.get(i).path);
				monitor_renamed str = new monitor_renamed(test);
				if(save.get(i).operate.equals("record-summary")){
					str.set_operate(1,save.get(i).path,sum,tail);
				}else if(save.get(i).operate.equals("record-detail")){
					str.set_operate(2,save.get(i).path,sum,tail);
				}else{
					str.set_operate(3,save.get(i).path,sum,tail);
				}
				System.out.println("1");
				str.start();
			}else if(save.get(i).touch.equals("modify")){
				filebag test = new filebag(save.get(i).path);
				monitor_modify str = new monitor_modify(test);
				if(save.get(i).operate.equals("record-summary")){
					str.set_operate(1,save.get(i).path,sum,tail);
				}else if(save.get(i).operate.equals("record-detail")){
					str.set_operate(2,save.get(i).path,sum,tail);
				}else{
					str.set_operate(3,save.get(i).path,sum,tail);
				}
				System.out.println("2");
				str.start();
			}else if(save.get(i).touch.equals("path-changed")){
				filebag test = new filebag(save.get(i).path);
				monitor_pathchange str = new monitor_pathchange(test);
				if(save.get(i).operate.equals("record-summary")){
					str.set_operate(1,save.get(i).path,sum,tail);
				}else if(save.get(i).operate.equals("record-detail")){
					str.set_operate(2,save.get(i).path,sum,tail);
				}else{
					str.set_operate(3,save.get(i).path,sum,tail);
				}
				System.out.println("3");
				str.start();
			}else{
				filebag test = new filebag(save.get(i).path);
				monitor_sizechange str = new monitor_sizechange(test);
				if(save.get(i).operate.equals("record-summary")){
					str.set_operate(1,save.get(i).path,sum,tail);
				}else if(save.get(i).operate.equals("record-detail")){
					str.set_operate(2,save.get(i).path,sum,tail);
				}else{
					str.set_operate(3,save.get(i).path,sum,tail);
				}
				System.out.println("4");
				str.start();
			}
		}
		sum.start();
		tail.start();
		/////////////////////////
		handlefile handle = new handlefile();
		handle.operate();
		////////////////////////
		/*请测试者在handlefile类的operate函数中填写相应函数的参数对文件系统进行相应修改*/
	}

}

class func{
	String touch; 
	String operate;
	String path;
	func(String a,String b,String c){
		touch = a;
		operate = b;
		path = c;
	}
}
