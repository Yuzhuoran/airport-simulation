//YOUR NAME HERE
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Random;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Airport implements EventHandler {

    //TODO add landing and takeoff queues, random variables
	java.text.DecimalFormat df=new java.text.DecimalFormat("#.##"); 
    private int m_inTheAir;
    private int m_onTheGround;
    private int m_peoplein;
    private int m_peopleout;
    private int m_id;
    private double m_circleTime;
    private double m_totalCircleTime;
    private boolean m_freeToLandOrTakeoff;


    private double m_flightTime;
    private double m_runwayTimeToLand;
    private double m_requiredTimeOnGround;
    private double m_runwayTimeToTakeoff;
    
    private ArrayList<Double> m_Dist=new ArrayList<Double>();
    private Airplane m_airplane;
    private Queue<AirportEvent> LandQueue=new LinkedList<AirportEvent>();
    private Queue<AirportEvent> DepartQueue=new LinkedList<AirportEvent>();
    private String m_airportName;
   

    public Airport(String name, double runwayTimeToLand, double requiredTimeOnGround, double runwayTimeToTakeoff,int id) {
        m_airportName = name;
        m_inTheAir =  0;
        m_onTheGround = 0;
        m_freeToLandOrTakeoff = true;
        m_runwayTimeToLand = runwayTimeToLand;
        m_requiredTimeOnGround = requiredTimeOnGround;
        m_runwayTimeToTakeoff=runwayTimeToTakeoff;
        m_peoplein=0;
        m_peopleout=0;
        m_circleTime=0;
        m_totalCircleTime=0;
        m_id=id;
       
    }

    public String getName() {
        return m_airportName;
    }
    
    public double getTotalCircleTime(){
    	return m_totalCircleTime;
    }
    public void setTotalCircleTime(double TotalTime){
    	m_totalCircleTime=TotalTime;
    }
    public double getCircleTime(){
    	return m_circleTime;
    }
    public void setCircleTime(double Time){
    	m_circleTime=Time;
    }
    public int getpeopleIn(){
    	return m_peoplein;
    }
    public int getpeopleOut(){
    	return m_peopleout;
    }
    
    public void handle(Event event) {
        AirportEvent airEvent= (AirportEvent)event;
        Airport []Airports=AirportsMatrix.Airport_M;
        Double [][]Dist=AirportsMatrix.DistanceMatrix;
        //ArrayList<Airport> Airports=airEvent.getAirports();
        m_airplane=airEvent.getPlane();
        
        Output printer=new Output();
      
        switch(airEvent.getType()) {
            case AirportEvent.PLANE_ARRIVES:
                m_inTheAir++;
                LandQueue.add(airEvent);

                //String Arrive_Infor=df.format(Simulator.getCurrentTime()) +", "+ airEvent.getPlane().getName()+","+m_airportName;
                //printer.Airport_Out(m_airportName,"_Arrive.txt",Arrive_Infor);                
                
                System.out.println(df.format(Simulator.getCurrentTime()) +": "+ airEvent.getPlane().getName()+ " Plane arrived at "+m_airportName +" airport"); 
                airEvent.getPlane().setStartTime(Simulator.getCurrentTime());
                if(m_freeToLandOrTakeoff) {
                	AirportEvent landedEvent = new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,airEvent.getPlane(),Airports);                                
                    Simulator.schedule(landedEvent);
                    m_freeToLandOrTakeoff=false;
                }
                break;
                               
            case AirportEvent.PLANE_DEPARTS:
                m_onTheGround++;
                DepartQueue.add(airEvent);
                //String Depart_Infor=df.format(Simulator.getCurrentTime()) +", "+ airEvent.getPlane().getName()+","+m_airportName;
                //printer.Airport_Out(m_airportName,"_Depart.txt",Depart_Infor);
            	System.out.println(df.format(Simulator.getCurrentTime()) + ": "+ airEvent.getPlane().getName()+" Plane departs from "+m_airportName);                     
                if(m_freeToLandOrTakeoff){
                	
                	AirportEvent takeoffEvent = new AirportEvent(m_runwayTimeToTakeoff, this, AirportEvent.PLANE_TAKEOFF,airEvent.getPlane(),Airports);            
                    Simulator.schedule(takeoffEvent);  
                    m_freeToLandOrTakeoff=false;
                }
                
                break;

            case AirportEvent.PLANE_LANDED:
               
                m_inTheAir--;
                LandQueue.remove();

                m_peoplein+=airEvent.getPlane().getPassenger();                
                AirportEvent departEvent=new AirportEvent(m_requiredTimeOnGround,this,AirportEvent.PLANE_DEPARTS,airEvent.getPlane(),Airports);
                Simulator.schedule(departEvent);
                
                //String Land_Infor=df.format(Simulator.getCurrentTime()) +", "+ airEvent.getPlane().getName()+","+m_airportName;
                String Peoplein=df.format(Simulator.getCurrentTime())+", "+m_peoplein;
                                            
                //printer.Airport_Out(m_airportName,"_Land.txt",Land_Infor); 
               // printer.Airport_Out(m_airportName, "_peoplein.txt", Peoplein);
              
                System.out.println(df.format(Simulator.getCurrentTime()) + ": "+airEvent.getPlane().getName()+" Plane lands at "+m_airportName+" airport with "+airEvent.getPlane().getPassenger()+" people");                         	           
                
                this.setCircleTime(Simulator.getCurrentTime()-airEvent.getPlane().getStartTime()-m_runwayTimeToLand);
                this.setTotalCircleTime(this.getTotalCircleTime()+this.getCircleTime());
                
                String TotalTime=df.format(Simulator.getCurrentTime())+", "+df.format(this.getTotalCircleTime());
                //printer.Airport_Out(m_airportName,"_TotalCircleTime.txt",TotalTime);
                
                if((m_inTheAir!=0)&&(m_onTheGround!=0))
                {
                	if(LandQueue.element().getTime()<=DepartQueue.element().getTime())
                	{                		
                		AirportEvent landingEvent=new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,LandQueue.element().getPlane(),Airports);            
                		Simulator.schedule(landingEvent); 
                		m_freeToLandOrTakeoff=false;
                	}
                	
                	else
                	{
                		AirportEvent takeoffEvent=new AirportEvent(m_runwayTimeToTakeoff, this, AirportEvent.PLANE_TAKEOFF,DepartQueue.element().getPlane(),Airports);                      				
                		Simulator.schedule(takeoffEvent);
                		m_freeToLandOrTakeoff=false;
                	}
                }
                else if((m_inTheAir!=0)&&(m_onTheGround==0))
                {
                	AirportEvent landingEvent=new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,LandQueue.element().getPlane(),Airports);            
            		Simulator.schedule(landingEvent); 
            		m_freeToLandOrTakeoff=false;
                }
                else if((m_inTheAir==0)&&(m_onTheGround!=0))
                {
                	AirportEvent takeoffEvent=new AirportEvent(m_runwayTimeToTakeoff, this, AirportEvent.PLANE_TAKEOFF,DepartQueue.element().getPlane(),Airports);                      				
            		Simulator.schedule(takeoffEvent); 
            		m_freeToLandOrTakeoff=false;
                }
                else{
                    m_freeToLandOrTakeoff=true;
                }
                break;
                                         
            case AirportEvent.PLANE_TAKEOFF:
            	m_onTheGround--;
            	DepartQueue.remove();           	            	
            	Random rand=new Random();
                Random randp=new Random();
                int index = rand.nextInt(Airports.length-1);
                int lb=(int) (m_airplane.getCapacity()*0.4);// 40% lowerbound
                int passenger=randp.nextInt(m_airplane.getCapacity()-lb+1)+lb;
                
                while(Airports[index].getName()==m_airportName){
                  index = rand.nextInt(Airports.length);
                 }
                  
                m_flightTime=Dist[m_id][index]/m_airplane.getspeed();
                
                Airport Destination=Airports[index];
                airEvent.getPlane().setp(passenger); 
                
                m_peopleout+=airEvent.getPlane().getPassenger();
                
                String Takeoff_Infor=df.format(Simulator.getCurrentTime()) +", "+ airEvent.getPlane().getName()+","+m_airportName;
                String Peopleout=df.format(Simulator.getCurrentTime())+", "+m_peopleout;
               // printer.Airport_Out(m_airportName,"_Takeoff.txt",Takeoff_Infor);
                //printer.Airport_Out(m_airportName, "_peopleout.txt", Peopleout);
                System.out.println(df.format(Simulator.getCurrentTime()) + ": "+airEvent.getPlane().getName()+" Plane Take off at "+m_airportName+" airport with "+airEvent.getPlane().getPassenger()+" people");                         	
                AirportEvent arriveEvent=new AirportEvent(m_flightTime,Destination,AirportEvent.PLANE_ARRIVES,airEvent.getPlane(),Airports);
                Simulator.schedule(arriveEvent);
                
                if((m_inTheAir!=0)&&(m_onTheGround!=0))
                {
                	if(LandQueue.element().getTime()<=DepartQueue.element().getTime())
                	{                		
                		AirportEvent landingEvent=new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,LandQueue.element().getPlane(),Airports);            
                		Simulator.schedule(landingEvent); 
                		m_freeToLandOrTakeoff=false;
                	}
                	
                	else
                	{
                		AirportEvent takeoffEvent=new AirportEvent(m_runwayTimeToTakeoff, this, AirportEvent.PLANE_TAKEOFF,DepartQueue.element().getPlane(),Airports);                      				
                		Simulator.schedule(takeoffEvent);
                		m_freeToLandOrTakeoff=false;
                	}
                }
                else if((m_inTheAir!=0)&&(m_onTheGround==0))
                {
                	AirportEvent landingEvent=new AirportEvent(m_runwayTimeToLand, this, AirportEvent.PLANE_LANDED,LandQueue.element().getPlane(),Airports);            
            		Simulator.schedule(landingEvent); 
            		m_freeToLandOrTakeoff=false;
                }
                else if((m_inTheAir==0)&&(m_onTheGround!=0))
                {
                	AirportEvent takeoffEvent=new AirportEvent(m_runwayTimeToTakeoff, this, AirportEvent.PLANE_TAKEOFF,DepartQueue.element().getPlane(),Airports);                      				
            		Simulator.schedule(takeoffEvent); 
            		m_freeToLandOrTakeoff=false;
                }
                else{
                    m_freeToLandOrTakeoff=true;
                }
                break;
        }
    }
}
