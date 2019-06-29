package io.github.oliviercailloux.researcher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.jconfs.researcher.Researcher;
import io.github.oliviercailloux.jconfs.researcher.ResearcherBuilder;

/**
 * a Junit test for ResearcherBuilder class
 * 
 * @author stanislas
 *
 */
public class TestResearcherBuilder {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TestResearcherBuilder.class);

	/**
	 * A test for the method create
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassCastException
	 */
	public final void createTest()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		Researcher oCailloux = ResearcherBuilder.create("ocailloux");
		LOGGER.debug("create Researcher object");

		assertEquals("CAILLOUX", oCailloux.getLastname());
		assertEquals("Olivier", oCailloux.getFirstname());
		assertEquals("+33 1 44 05 46 53", oCailloux.getPhone());
		assertEquals("MIDO-LAMSADE", oCailloux.getGroup());
		assertEquals("olivier.cailloux@lamsade.dauphine.fr", oCailloux.getMail());
		assertEquals("P405 ter", oCailloux.getOffice());

	}

	/**
	 * a main to try the method create
	 * 
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassCastException
	 */
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, ClassCastException {
		Researcher test = ResearcherBuilder.create("ocailloux");
		System.out.println(test.toString());

	}
}
