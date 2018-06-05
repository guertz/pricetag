package com.guerzonica.app.picodom.components.modal;

import javafx.event.Event;
import javafx.event.EventType;
/**
* Classe used to build a costum event about {@link  com.guerzonica.app.picodom.components.modal.Modal}
* @author Singh Amarjot
*/
public class ModalEvent extends Event{
  private static final long serialVersionUID = 42l;
  public static EventType<ModalEvent> CLOSE = new EventType<>(Event.ANY, "MODAL_CLOSED");
  public ModalEvent(EventType<? extends Event> eventType){
    super(eventType);
  }
}
