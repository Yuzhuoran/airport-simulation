import java.util.ArrayList;

//YOUR NAME HERE : Zhuoran Yu

public class AirportEvent extends Event {
    public static final int PLANE_ARRIVES = 0;
    public static final int PLANE_LANDED = 1;
    public static final int PLANE_DEPARTS = 2;
	public static final int PLANE_TAKEOFF = 3;
    
    private Airplane m_airplane;
    //private ArrayList<Airport> m_Airports=new ArrayList<Airport>();
    private Airport []m_Airports=AirportsMatrix.Airport_M;
    AirportEvent(double delay, EventHandler handler, int eventType, Airplane airplane, Airport []airports) {
        super(delay, handler, eventType);
        m_airplane=airplane;
        m_Airports=airports;
    }
    
    Airplane getPlane(){
    	return m_airplane;
    }
    
    Airport []getAirports(){
    	return m_Airports;
    	
    }
}
