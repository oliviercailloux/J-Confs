package io.github.oliviercailloux.jconfs.gui;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * A GUI to use the others GUI*
 * 
 * @author stanislas
 *
 */
public class GeneralGUI {
  public GeneralGUI() throws NullPointerException, IllegalArgumentException, IOException {
    final Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("J-Confs");

    shell.setBackground(new Color(display, 0, 150, 255));
    FillLayout layout = new FillLayout(SWT.VERTICAL);

    layout.marginHeight = 100;
    layout.marginWidth = 100;
    shell.setLayout(layout);

    layout.spacing = 5;

    // button to use localMap GUI
    Button localMapButton = new Button(shell, SWT.PUSH);
    localMapButton.setText("LocalMap");
    localMapButton.setSize(200, 50);

    localMapButton.addListener(SWT.Selection, new Listener() {
      MapGUI mapGUI = new MapGUI("world.map", display); // endPoint (pékin latLong)

      @Override
      public void handleEvent(Event e) {
        mapGUI.display();
      }
    });

    // button to open GUIConference
    Button conferenceButton = new Button(shell, SWT.PUSH);
    conferenceButton.setText("conference");
    conferenceButton.setSize(200, 50);
    conferenceButton.addListener(SWT.Selection, new Listener() {

      @Override
      public void handleEvent(Event event) {
        GuiConference gui = new GuiConference();
        gui.Gui(display);
      }
    });

    shell.setSize(500, 500);

    shell.setVisible(true);
    while (!shell.isDisposed()) {

      if (!display.readAndDispatch())

        display.sleep();

    }

    display.dispose();

  }
}
