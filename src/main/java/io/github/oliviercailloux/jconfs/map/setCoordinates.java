package io.github.oliviercailloux.jconfs.map;

import io.github.oliviercailloux.jconfs.researcher.Researcher;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author huong
 *
 */
public class setCoordinates {
	private static final Logger logger = LoggerFactory.getLogger(setCoordinates.class);

	/**
	 * fill the paper with header
	 *
	 * @param user
	 * @return path where the file is saved
	 * @throws Exception
	 */

	public static String fillPapierEnTete(Researcher user, Path path) throws Exception {
		logger.info("The File will be saved in: " + path.toAbsolutePath());

		// define source and target
		ClassLoader classLoader = setCoordinates.class.getClassLoader();
		String source = classLoader.getResource("io/github/oliviercailloux/y2018/jconfs/papier_a_en_tete.fodt")
				.getPath();
		System.out.print(source);
		String target = path.toAbsolutePath() + "/papier_a_en_tete_CloneTest.docx";

		FileOutputStream fos = new FileOutputStream(target);
		try (PrintStream printStream = new PrintStream(fos)) {
			try (BufferedReader brSource = new BufferedReader(new FileReader(source))) {

				for (int i = 0; i < 36762; i++) {
					String lineSource = brSource.readLine();
					printStream.println(lineSource);
				}

				String lineSource = brSource.readLine();
				String regex = "Nom";
				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(lineSource);
				lineSource = m.replaceFirst(user.getLastname());
				regex = "Prenom";
				p = Pattern.compile(regex);
				m = p.matcher(lineSource);
				lineSource = m.replaceFirst(user.getFirstname());
				regex = "Tel";
				p = Pattern.compile(regex);
				m = p.matcher(lineSource);
				lineSource = m.replaceFirst(user.getPhone());
				printStream.println(lineSource);

				lineSource = brSource.readLine();
				regex = "Fonction";
				p = Pattern.compile(regex);
				m = p.matcher(lineSource);
				lineSource = m.replaceFirst(user.getGroup());
				regex = "E-mail";
				p = Pattern.compile(regex);
				m = p.matcher(lineSource);
				lineSource = m.replaceFirst(user.getMail());
				printStream.println(lineSource);

				while ((lineSource = brSource.readLine()) != null) {
					printStream.println(lineSource);
				}
			}
		}
		logger.info("PapierEnTete generated");
		return path.toAbsolutePath().toString();
	}

	public static String fillPapierEnTete(Researcher user) throws Exception {
		Path path = FileSystems.getDefault().getPath("");
		return fillPapierEnTete(user, path);
	}

}
