import java.io.File;
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
	public static void main(String[]args) {
		MapGUI GUI=new MapGUI("ile-de-france.map",new LatLong(48.9333,2.45)); //endPoint is Drancy
		GUI.display();
	}
}



