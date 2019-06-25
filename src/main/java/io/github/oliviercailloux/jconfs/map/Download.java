package io.github.oliviercailloux.jconfs.map;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * this class enables to download file from given link to test/resources
 * directory
 * 
 *
 */
public class Download {
	public static void downLoadFile(String host) throws IOException {
		if (Objects.isNull(host))
			throw new NullPointerException("host can't be null");
		URL url = new URL(host);
		URLConnection connection = url.openConnection();

		int fileLength = connection.getContentLength();
		if (fileLength == -1)
			throw new IllegalArgumentException("Invalide URL or file.");

		try (InputStream input = connection.getInputStream()) {
			String fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);

			try (FileOutputStream writeFile = new FileOutputStream("target/classes/" + fileName);) {

				byte[] buffer = new byte[1024];
				int read;

				while ((read = input.read(buffer)) > 0)
					writeFile.write(buffer, 0, read);
				writeFile.flush();
			}

		}

	}

}
