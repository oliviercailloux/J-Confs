import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.awt.graphics.AwtGraphicFactory;
import org.mapsforge.map.awt.util.AwtUtil;
import org.mapsforge.map.awt.view.MapView;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.download.TileDownloadLayer;
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik;

import io.github.oliviercailloux.y2018.jconfs.MapGUI;

public class TestMapGUI {
	public static void main(String[]args) throws NullPointerException, IllegalArgumentException, IOException  {
		MapGUI GUI=new MapGUI("world.map",new LatLong( 39.913818, 116.363625)); //endPoint (pékin latLong)
		
		GUI.display();
		
		
	}
}
