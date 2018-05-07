package com.guerzonica.app.components;
import javafx.event.Event;
import javafx.event.EventType;
public class ModalEvent extends Event{
  private static final long serialVersionUID = 42l;
  public static EventType<ModalEvent> CLOSE = new EventType<>(Event.ANY, "MODAL_CLOSED");
  public ModalEvent(EventType<? extends Event> eventType){
    super(eventType);
  }
}
