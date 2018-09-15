//YOUR NAME HERE

//TODO add number of passengers, speed

public class Airplane {
    private String m_name;
    private int m_capacity;
    private int m_speed;
    private int m_currentp;
    private double m_startTime;


    public Airplane(String name,int capacity,int speed) {
        m_name = name;
        m_capacity=capacity;
        m_speed=speed;
        m_currentp=0;
        

    }

    public String getName() {
        return m_name;
    }
    public int getPassenger(){
    	return m_capacity;
    }
    public int getCapacity(){
    	return m_capacity;
    }
    public int getspeed(){
    	return m_speed;
    }
    public void setp(int p){
    	m_currentp=p;
    	
    }
    public double getStartTime(){
    	return m_startTime;
    }
    public void setStartTime(double time){
    	m_startTime=time;
    	
    }
}
