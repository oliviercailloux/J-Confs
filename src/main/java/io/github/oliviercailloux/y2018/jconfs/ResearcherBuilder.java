package io.github.oliviercailloux.y2018.jconfs;

import java.util.Objects;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

/**
 * Researcher is a classe which creates chercher object
 * 
 * @author stanislas
 *
 */
public class ResearcherBuilder {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ResearcherBuilder.class);

	/**
	 * empty constructor to create a researcher
	 */
	public ResearcherBuilder() {
		LOGGER.debug("create an empty Reasarcher");
	}

	/**
	 * create is a static factory method to create Researcher from dauphine website
	 * 
	 * @param login
	 * @return Researcher
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassCastException
	 */
	public static Researcher create(String login)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client
				.target("https://www.ent.dauphine.fr/annuaire/index.php?param0=fiche&param1=" + login);
		LOGGER.debug("webTarget initialisation succed");
		String result = webTarget.request(MediaType.TEXT_PLAIN).get(String.class);
		LOGGER.debug("request succed", result);

		DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
		DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");

		LSParser builder = impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
		LSInput input = impl.createLSInput();

		input.setStringData(result);

		Document doc = builder.parse(input);
		LOGGER.debug("item created", doc);

		String searcher = Objects
				.requireNonNull(doc.getElementsByTagName("h4").item(0).getFirstChild().getTextContent());
		String nom = searcher.split(" ")[1];
		String prenom = searcher.split(" ")[0];
		Researcher researcher = new Researcher(nom, prenom);
		NodeList listElements = doc.getElementsByTagName("li");
		researcher.setFunction(Objects.requireNonNull(listElements.item(2).getFirstChild().getTextContent()));
		researcher.setPhone(Objects.requireNonNull(listElements.item(4).getFirstChild().getTextContent().trim()));
		researcher.setOffice(Objects.requireNonNull(listElements.item(8).getFirstChild().getTextContent().trim()));
		researcher.setMail(Objects.requireNonNull(listElements.item(10).getFirstChild().getTextContent().trim()));
		listElements = doc.getElementsByTagName("a");

		String group = "";
		// add every groups
		for (int i = 3; i < listElements.getLength(); i++) {
			if (i != 3)
				group += "-";
			group += listElements.item(i).getFirstChild().getTextContent();
			LOGGER.debug("group " + i + " added", group);

		}
		researcher.setGroup(group);
		return researcher;
	}
}
