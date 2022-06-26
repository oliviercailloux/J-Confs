package io.github.oliviercailloux.jconfs.conference;

import com.google.common.base.Preconditions;
import com.locationiq.client.ApiException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import net.fortuna.ical4j.data.ParserException;

public class ConferencesFromICal implements ConferencesRetriever {

  @Override
  public Set<Conference> retrieve()
      throws IOException, ParserException, InvalidConferenceFormatException {
    String filePath = ConferencesFromICal.class.getClassLoader().getResource("icaldata").getFile();
    File ressourcesDirectory = new File(filePath);
    File[] fileList = ressourcesDirectory.listFiles();
    Set<Conference> setOfConf = new LinkedHashSet<>();
    for (File file : fileList) {
      if (file.getName().endsWith(".ics")) {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file))) {
          try {
            setOfConf.addAll(ConferenceReader.readConferences(reader));
          } catch (IOException e) {
            throw new IllegalStateException(e);
          } catch (ParserException e) {
            throw new IllegalStateException(e);
          } catch (ApiException e) {
            throw new IllegalStateException(e);
          } catch (InterruptedException e) {
            throw new IllegalStateException(e);
          }
        }
      }
    }
    return setOfConf;
  }

  @Override
  public Set<Conference> retrieve(Instant minDate, Instant maxDate)
      throws IOException, ParserException, InvalidConferenceFormatException {
    Set<Conference> setOfAllConf = retrieve();
    Set<Conference> setOfConfFiltred = new LinkedHashSet<>();
    for (Conference conf : setOfAllConf) {
      if (conf.getStartDate().isAfter(minDate) && conf.getEndDate().isBefore(maxDate)) {
        setOfConfFiltred.add(conf);
      }
    }
    return setOfConfFiltred;
  }

  @Override
  public Set<Conference> retrieve(String fileName)
      throws InvalidConferenceFormatException, IOException, ParserException {
    Preconditions.checkNotNull(fileName);
    URL urlcalendar = ConferenceReader.class.getResource(fileName + ".ics");
    Path p;
    try {
      p = Path.of(urlcalendar.toURI());
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }
    String s = p.toString();
    try (FileReader reader = new FileReader(new File(s))) {
      try {
        return ConferenceReader.readConferences(reader);
      } catch (IOException e) {
        throw new IllegalStateException(e);
      } catch (ParserException e) {
        throw new IllegalStateException(e);
      } catch (ApiException e) {
        throw new IllegalStateException(e);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }

  }
}
