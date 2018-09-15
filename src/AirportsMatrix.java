import java.util.ArrayList;
import java.util.Arrays;

public class AirportsMatrix {
	public static final Double [][]DistanceMatrix={{0.0,1937.0,760.0,607.0,689.0},{1937.0,0.0,2472.0,1743.0,1378.0},{760.0,2472.0,0.0,739.0,1417.0},{607.0,1743.0,739.0,0.0,927.0},{689.0,1378.0,1417.0,927.0,0.0}};
	public static double runwaytoland=0.25;
	public static double runwaytotakeoff=0.25;
	public static double OntheGround=2;
	public static Airport atl= new Airport("ATL", runwaytoland, OntheGround,runwaytotakeoff,0); 
	public static Airport lax = new Airport("LAX", runwaytoland, OntheGround,runwaytotakeoff,1);
	public static Airport jfk=new Airport("JFK",runwaytoland, OntheGround,runwaytotakeoff,2);
	public static Airport ord=new Airport("ORD",runwaytoland, OntheGround,runwaytotakeoff,3);
	public static Airport hou=new Airport("HOU",runwaytoland, OntheGround,runwaytotakeoff,4);
	public static Airport []Airport_M={atl,lax,jfk,ord,hou};
}
