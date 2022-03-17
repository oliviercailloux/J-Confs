package io.github.oliviercailloux.jconfs.conference;

/**
 * @author nikola Custom exception use for a conference creation
 */
public class InvalidConferenceFormatException extends Exception {
  /**
   * Default Serial ID
   */
  private static final long serialVersionUID = 1L;

  public InvalidConferenceFormatException(String errorMessage) {
    super(errorMessage);
  }

  public InvalidConferenceFormatException(String errorMessage, Exception e) {
    super(errorMessage, e);
  }
}
