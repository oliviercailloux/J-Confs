package io.github.oliviercailloux.jconfs.map;
import java.io.IOException;

import io.github.oliviercailloux.jconfs.map.Download;

public class TestDownload {
	public static void main(String[] args) throws IOException {

		Download.downLoadFile("http://download.mapsforge.org/maps/world/world.map");
	}
}
