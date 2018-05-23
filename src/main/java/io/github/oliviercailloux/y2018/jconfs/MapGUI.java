package io.github.oliviercailloux.y2018.jconfs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
import org.mapsforge.map.layer.download.tilesource.OnlineTileSource;
import org.mapsforge.map.layer.download.tilesource.OpenStreetMapMapnik;
import org.mapsforge.map.layer.download.tilesource.TileSource;
import org.mapsforge.map.layer.hills.HillsRenderConfig;
import org.mapsforge.map.layer.overlay.Circle;
import org.mapsforge.map.layer.overlay.FixedPixelCircle;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.model.MapViewPosition;
import org.mapsforge.map.model.Model;

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
	final private GraphicFactory GRAPHIC_FACTORY = AwtGraphicFactory.INSTANCE;

	/**
	 * the frame in which the map is displayed
	 */
	final private Frame mapFrame;


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

	public MapGUI(String mapName) throws NullPointerException {

		if (!Objects.nonNull(mapName))
			throw new NullPointerException("mapName is null");

		Display display = new Display();
		this.shell = new Shell(display);

		shell.setText("map :" + mapName);
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		composite.setSize(900, 900);
		HillsRenderConfig hillsCfg = null; // Mutable frontend for the hillshading cache/processing in HgtCache (can be
											// changed)

		// Samples.class is a default preferences provide by MapForges
		final MapView mapView = createMapView();
		this.boundingBox = addLayers(mapView, mapName, hillsCfg);

		// a SWT FRAME to contain AWT component from AWT-MapsForge libraries
		this.mapFrame = SWT_AWT.new_Frame(composite);
		mapView.setSize(900, 900);
		mapFrame.add(mapView);
		mapFrame.setLocationRelativeTo(null);

		// action when the windows is closed
		shell.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				MessageBox closeMsg = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.YES | SWT.NO);
				closeMsg.setMessage("\"Are you sure you want to exit the application?");
				closeMsg.setText("Confirm close");
				event.doit = closeMsg.open() == SWT.YES;

				if (event.doit) {
					mapView.destroyAll();
					AwtGraphicFactory.clearResourceMemoryCache();
					shell.getDisplay().dispose();

				}
			}
		});

		//DEFAULT Zoom and position
		byte zoomLevel = LatLongUtils.zoomForBounds(mapView.getDimension(), boundingBox,
				mapView.getModel().displayModel.getTileSize());
		mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(boundingBox.getCenterPoint(), zoomLevel));

		// initialize a painFill to pain points
		Paint paintFill = GRAPHIC_FACTORY.createPaint();
		paintFill.setColor(Color.RED);
		paintFill.setStyle(Style.FILL);

		// startPoint creation

		this.startPoint = new FixedPixelCircle(new LatLong(48.866667, 2.333333), 5, paintFill, null);// Paris by default
		startPoint.setVisible(true);
		mapView.getLayerManager().getLayers().add(startPoint);

		// endPoint cration
		this.endPoint = new FixedPixelCircle(new LatLong(48.866667, 2.333333), 5, paintFill, null);// Paris by default
		startPoint.setVisible(true);
		mapView.getLayerManager().getLayers().add(endPoint);
	}

	/**
	 * this method display the GUI in a windows
	 */
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
	 * 
	 * @param mapName
	 * @param endPoint
	 */
	public MapGUI(String mapName, LatLong endPoint) throws NullPointerException {
		this(mapName);
		if (Objects.nonNull(endPoint)) {
			if (this.boundingBox.contains(endPoint)) {
				this.endPoint.setLatLong(endPoint);
			} else {
				throw new IllegalArgumentException("this location doesn't exist on this map");
			}
		} else {
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

	/**
	 * this methode create a mapTile from mapFile data and add it to the mapView if
	 * the file doesn't exist,this method download tileData
	 * 
	 * @param mapView
	 *            <code> not null<code>
	 * @param mapName
	 *            <code> not null<code>
	 * @param hillsRenderConfig
	 *            <code> can be null<code>
	 * @return<b> BoundingBox</b>,from mapViewModel
	 */
	private BoundingBox addLayers(MapView mapView, String mapName, HillsRenderConfig hillsRenderConfig) {
		if (Objects.isNull(mapView))
			throw new NullPointerException("mapView is null");
		if (Objects.isNull(mapName))
			throw new NullPointerException("mapName is null");

		URL mapURL = this.getClass().getClassLoader().getResource(mapName);
		// if the file doesn't exist, we will download it

		Layers layers = mapView.getLayerManager().getLayers();
		int tileSize = mapURL == null ? 256	 : 512;
		
		
		// Tile Cache
		TileCache tileCache = AwtUtil.createTileCache(tileSize, mapView.getModel().frameBufferModel.getOverdrawFactor(),
				1024, new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString()));

		if (!(mapURL == null)) {

			// Vector (the mapFile exist in resources)
			mapView.getModel().displayModel.setFixedTileSize(tileSize);
			MapFile mapFile = new MapFile(new File(mapURL.getFile()));
			MapDataStore mapDataStore = mapFile;
			TileRendererLayer tileRendererLayer = createTileRendererLayer(tileCache, mapDataStore,
					mapView.getModel().mapViewPosition, hillsRenderConfig);

			layers.add(tileRendererLayer);
			boundingBox = mapDataStore.boundingBox();

		} else {
			// Raster(the mapFile doesn't exist in resources)
			TileSource tileSource = OpenStreetMapMapnik.INSTANCE	;
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

	/**
	 * create a TileRenderLayer from mapFile datas
	 * 
	 * @param tileCache
	 *            <code>not null </code>
	 * @param mapDataStore
	 *            <code>not null </code>
	 * @param mapViewPosition
	 *            <code>not null </code>
	 * @param hillsRenderConfig
	 *            <code>can be null</code>
	 * @return TileRenderLayer, which represent mapsData
	 */
	private TileRendererLayer createTileRendererLayer(TileCache tileCache, MapDataStore mapDataStore,
			MapViewPosition mapViewPosition, HillsRenderConfig hillsRenderConfig) {
		TileRendererLayer tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore, mapViewPosition, false,
				true, false, GRAPHIC_FACTORY, hillsRenderConfig);
		tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.DEFAULT);
		return tileRendererLayer;
	}

	/**
	 * CreateTileDownloadLayer from tileSource
	 * 
	 * @param tileCache
	 *            <code>not null </code>
	 * @param mapViewPosition
	 *            <code>not null </code>
	 * @param tileSource
	 *            <code>not null </code>
	 * @return
	 */
	private TileDownloadLayer createTileDownloadLayer(TileCache tileCache, MapViewPosition mapViewPosition,
			TileSource tileSource) {

		return new TileDownloadLayer(Objects.requireNonNull(tileCache), Objects.requireNonNull(mapViewPosition),
				Objects.requireNonNull(tileSource), Objects.requireNonNull(GRAPHIC_FACTORY));

	}
}
