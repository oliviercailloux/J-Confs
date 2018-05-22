package io.github.oliviercailloux.y2018.jconfs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.mapsforge.core.graphics.Color;
import org.mapsforge.core.graphics.GraphicFactory;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.BoundingBox;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.model.Point;
import org.mapsforge.core.util.LatLongUtils;
import org.mapsforge.map.awt.graphics.AwtGraphicFactory;
import org.mapsforge.map.awt.util.AwtUtil;
import org.mapsforge.map.awt.util.JavaPreferences;
import org.mapsforge.map.awt.view.MapView;
import org.mapsforge.map.datastore.MapDataStore;
import org.mapsforge.map.datastore.MultiMapDataStore;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.download.TileDownloadLayer;
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik;
import org.mapsforge.map.layer.download.tilesource.TileSource;
import org.mapsforge.map.layer.hills.HillsRenderConfig;
import org.mapsforge.map.layer.overlay.Circle;
import org.mapsforge.map.layer.overlay.FixedPixelCircle;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.model.MapViewPosition;
import org.mapsforge.map.model.Model;
import org.mapsforge.map.model.common.PreferencesFacade;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

/**
 * this class provides method to made GUI for displaying map inspired from awt
 * sample from org.mapsforge.samples.awt
 * 
 * @author stanislas
 *
 */
public class MapGUI {

	/**
	 * défault graphicFactor used by using libraries provide by mapsForge
	 */
	final private GraphicFactory GRAPHIC_FACTORY=AwtGraphicFactory.INSTANCE;

	/**
	 * the frame in which the map is displayed
	 */
	final private Frame mapFrame;

	/**
	 * users parameters
	 */
	final PreferencesFacade preferencesFacade;

	/**
	 * mapGUI constructor,it creates every items needed to make the GUI ans display
	 * it
	 * 
	 * @param mapFile
	 *            <code>not null</code>, must be a .map file
	 */

	final private Shell shell;
	
	private FixedPixelCircle startPoint;
	
	private FixedPixelCircle endPoint;
	
	private BoundingBox boundingBox;
	

	public MapGUI(String mapName) {
		
		if(!Objects.nonNull(mapName))
				throw new NullPointerException("mapName is null");
		
		Display display = new Display();
		this.shell = new Shell(display);
		Paint paintFill = GRAPHIC_FACTORY.createPaint();

		
		shell.setText("map :" + mapName);
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setBounds(20, 20, 1000, 800);

		HillsRenderConfig hillsCfg = null; // Mutable frontend for the hillshading cache/processing in HgtCache (can be
											// changed)

		// Samples.class is a default preferences provide by MapForges
		this.preferencesFacade = new JavaPreferences(Preferences.userNodeForPackage(Samples.class));
		final MapView mapView = createMapView();
		this.boundingBox = addLayers(mapView, mapName, hillsCfg);

		this.mapFrame = SWT_AWT.new_Frame(composite);
		mapFrame.add(mapView);
		
		mapFrame.setLocationRelativeTo(null);
		
		
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				MessageBox closeMsg = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
				closeMsg.setMessage("\"Are you sure you want to exit the application?");
				closeMsg.setText("Confirm close");
				int answer = closeMsg.open();

				if (answer == SWT.YES) {
					mapView.getModel().save(preferencesFacade);
					mapView.destroyAll();
					AwtGraphicFactory.clearResourceMemoryCache();

				}

			}
		});
		
		
		
		shell.addListener(SWT.Show, new Listener() {
			@Override
			public void handleEvent(Event event) {
				System.out.println("open");
				final Model model = mapView.getModel();
				model.init(preferencesFacade);
				if (model.mapViewPosition.getZoomLevel() == 0
						|| !boundingBox.contains(model.mapViewPosition.getCenter())) {
					System.out.println(boundingBox);
					byte zoomLevel = LatLongUtils.zoomForBounds(mapView.getDimension(), boundingBox,
							model.displayModel.getTileSize());
					System.out.println("ici");
					model.mapViewPosition.setMapPosition(new MapPosition(boundingBox.getCenterPoint(), zoomLevel));
					mapView.setZoomLevel(zoomLevel);
				}

			}
		});
		
		
		// startPoint creation
		paintFill.setColor(Color.BLACK);
		paintFill.setStyle(Style.FILL);
		this.startPoint=new FixedPixelCircle( new LatLong( 48.866667, 2.333333), 5,paintFill,null);//Paris by default
		startPoint.setVisible(true);
		mapView.getLayerManager().getLayers().add(startPoint);
		
		//endPoint cration
		paintFill.setColor(Color.RED);
		paintFill.setStyle(Style.FILL);
		this.endPoint=new FixedPixelCircle( new LatLong( 48.866667, 2.333333), 5,paintFill,null);//Paris by default
		startPoint.setVisible(true);
		mapView.getLayerManager().getLayers().add(endPoint);
		
	}

	public void display() {
		this.shell.pack();
		System.out.println(shell);
		this.shell.open();

		while (!this.shell.isDisposed())

		{
			if (!shell.getDisplay().readAndDispatch()) {
				shell.getDisplay().sleep();

			}

		}
	}
	
	
	
	
	/**
	 * constructor in which we can choose the endPoint;
	 * @param mapName
	 * @param endPoint
	 */
	public MapGUI(String mapName,LatLong endPoint) throws NullPointerException{
		this(mapName);
		if(Objects.nonNull(endPoint)) {
			if(this.boundingBox.contains(endPoint)) {
				this.endPoint.setLatLong(endPoint);
			}else {
				throw new IllegalArgumentException("this location doesn't exist on this map");
			}
		}else {
			throw new NullPointerException("endPoint is null");
		}
		
	}




	/**
	 * this method create an ampty mapView with a MapScaleBar
	 * 
	 * @return MapView <code> not null<code>
	 */
	private MapView createMapView() {
		MapView mapViewTmp = new MapView();
		mapViewTmp.getMapScaleBar().setVisible(true);

		return mapViewTmp;
	}

	private BoundingBox addLayers(MapView mapView, String mapName, HillsRenderConfig hillsRenderConfig) {
		URL mapURL = this.getClass().getClassLoader().getResource(mapName);
		// if the file doesn't exist, we will download it

		Layers layers = mapView.getLayerManager().getLayers();
		int tileSize = mapURL == null ? 257 : 514;

		// Tile Cache
		TileCache tileCache = AwtUtil.createTileCache(tileSize, mapView.getModel().frameBufferModel.getOverdrawFactor(),
				1024, new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString()));
		final BoundingBox boundingBox;

		if (!(mapURL == null)) {

			// Vector
			mapView.getModel().displayModel.setFixedTileSize(tileSize);
			MapFile mapFile = new MapFile(new File(mapURL.getFile()));
			MapDataStore mapDataStore = mapFile;
			TileRendererLayer tileRendererLayer = createTileRendererLayer(tileCache, mapDataStore,
					mapView.getModel().mapViewPosition, hillsRenderConfig);

			layers.add(tileRendererLayer);
			boundingBox = mapDataStore.boundingBox();

		} else {
			// Raster
			TileSource tileSource = OpenStreetMapMapnik.INSTANCE;
			mapView.getModel().displayModel.setFixedTileSize(tileSize);
			TileDownloadLayer tileDownloadLayer = createTileDownloadLayer(tileCache, mapView.getModel().mapViewPosition,
					tileSource);

			layers.add(tileDownloadLayer);
			tileDownloadLayer.start();
			mapView.setZoomLevelMin(tileSource.getZoomLevelMin());
			mapView.setZoomLevelMax(tileSource.getZoomLevelMax());

			boundingBox = new BoundingBox(LatLongUtils.LATITUDE_MIN, LatLongUtils.LONGITUDE_MIN,
					LatLongUtils.LATITUDE_MAX, LatLongUtils.LONGITUDE_MAX);

		}
		return boundingBox;
	}

	private TileRendererLayer createTileRendererLayer(TileCache tileCache, MapDataStore mapDataStore,
			MapViewPosition mapViewPosition, HillsRenderConfig hillsRenderConfig) {
		TileRendererLayer tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore, mapViewPosition, false,
				true, false, GRAPHIC_FACTORY, hillsRenderConfig);
		tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.DEFAULT);
		return tileRendererLayer;
	}

	private TileDownloadLayer createTileDownloadLayer(TileCache tileCache, MapViewPosition mapViewPosition,
			TileSource tileSource) {
		return new TileDownloadLayer(tileCache, mapViewPosition, tileSource, GRAPHIC_FACTORY);

	}
}
