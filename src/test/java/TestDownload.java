import java.io.IOException;

import io.github.oliviercailloux.y2018.jconfs.Download;

public class TestDownload {
	public static void main(String[] args) throws IOException {

		Download.downLoadFile("http://download.mapsforge.org/maps/world/world.map");
	}
}
