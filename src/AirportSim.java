//YOUR NAME HERE: Zhuoran Yu

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;


public class AirportSim {
    public static void main(String[] args) {
    	java.text.DecimalFormat df=new java.text.DecimalFormat("#.##"); 
    	Airport []Airports=AirportsMatrix.Airport_M;
		Random randCapacity=new Random();
		Random randSpeed=new Random();
		Random randAirport=new Random();
		ArrayList<Airplane> AirplaneList=new ArrayList<Airplane>();
		for(int i=0;i<60;i++)
		{
			int capacity=randCapacity.nextInt(374)+126;
	    	int speed=randSpeed.nextInt(51)+520;
	    	Airplane airplane=new Airplane("id="+i,capacity,speed);
	    	AirplaneList.add(airplane);
		}
		for(int i=0;i<AirplaneList.size();i++)
		{
			int AirportId=randAirport.nextInt(5);
			int ArriveTime=randAirport.nextInt(12);
			AirportEvent event=new AirportEvent(ArriveTime,Airports[AirportId],AirportEvent.PLANE_ARRIVES,AirplaneList.get(i),Airports);
			Simulator.schedule(event);
		}
	
	    Simulator.stopAt(300);
	    Simulator.run();
    }
    	
    
}
