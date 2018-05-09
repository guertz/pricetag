package com.guerzonica.app.components;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.event.EventTarget;
public class ToolbarEvent extends Event{
  private static final long serialVersionUID = 42l;
  // public static EventType<MouseEvent> BACK_CLICK = new EventType<T>(MouseEvent.MOUSE_CLICKED, "BACK_BUTTON_CLICK");
  // public static EventType<ToolbarEvent> BACK_CLICK = new EventType<ToolbarEvent>(Event.ANY, "BACK_BUTTON_CLICK");
  // public static EventType<ToolbarEvent> BACK_CLICK = new EventType<ToolbarEvent>(Event.ANY, "BACK_BUTTON_CLICK");
  public static EventType<ToolbarEvent> BACK_CLICK = new EventType<ToolbarEvent>(Event.ANY, "BACK_BUTTON_CLICK");

  public ToolbarEvent(EventType<? extends Event> eventType){
    super(eventType);
  }
}
