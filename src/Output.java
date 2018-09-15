import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Output {
	public void Airport_Out(String m_airportName,String type,String content)
	{
		 File file=new File("output/"+m_airportName+type);
         try{
         
         	if(!file.exists()){
         		file.createNewFile();
         	}
         	 PrintWriter Land_out = new PrintWriter(new FileWriter(file,true));
         	 Land_out.append(content);
         	 Land_out.append("\r\n");
         	 Land_out.close();
         }
         catch(IOException e){
         	e.printStackTrace();
         	
         }

	}

}
