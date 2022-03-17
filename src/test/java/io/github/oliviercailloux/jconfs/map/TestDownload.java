package io.github.oliviercailloux.jconfs.map;

import java.io.IOException;

public class TestDownload {
  public static void main(String[] args) throws IOException {

    Download.downLoadFile("http://download.mapsforge.org/maps/world/world.map");
  }
}
