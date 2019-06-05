package io.github.oliviercailloux.y2018;
import java.io.IOException;
import io.github.oliviercailloux.y2018.jconfs.MapGUI;

public class TestMapGUI {
	public static void main(String[]args) throws Exception  {
		//MapGUI GUI=new MapGUI("world.map",new LatLong( 39.913818, 116.363625)); //endPoint (pékin latLong)
		MapGUI GUI=new MapGUI();//test online map
		GUI.display();
		
		
	}
}
