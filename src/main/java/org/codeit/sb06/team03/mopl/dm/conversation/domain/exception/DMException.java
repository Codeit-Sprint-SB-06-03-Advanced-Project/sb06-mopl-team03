package org.codeit.sb06.team03.mopl.dm.conversation.domain.exception;

public abstract class DMException extends RuntimeException {
  protected DMException(String message) {
    super(message);
  }
}
