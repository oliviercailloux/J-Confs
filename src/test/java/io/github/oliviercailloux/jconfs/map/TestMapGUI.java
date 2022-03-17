package io.github.oliviercailloux.jconfs.map;

import io.github.oliviercailloux.jconfs.gui.MapGUI;
import java.io.IOException;
import org.mapsforge.core.model.LatLong;

public class TestMapGUI {
  public static void main(String[] args)
      throws NullPointerException, IllegalArgumentException, IOException {
    MapGUI GUI = new MapGUI("world.map", new LatLong(39.913818, 116.363625)); // endPoint (pékin
                                                                              // latLong)
    // MapGUI GUI=new MapGUI();//test online map
    GUI.display();

  }
}
