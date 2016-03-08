package cct.github.droid.android_mvc_sample.model;

public class EventModel {

    public enum EventType {
        EVENT_BUS_MESSAGE_TEST,
        EVENT_BUS_MESSAGE_FAILED_JSON_REQUEST,
        EVENT_BUS_GET_POSTS_COMPLETE,
        TEST;
    }
    private EventType eventType;
    private Boolean Error = false;
    private Exception ErrorException = null;

    //constructors
    public EventModel() { }
    public EventModel(EventType _eventType) { eventType = _eventType; }

    //getters
    public EventType getEventType() { return eventType; }
    public Boolean getError() { return Error; }
    public Exception getErrorException() { return ErrorException; }

    //setters
    public void setEventType(EventType _eventType) { eventType = _eventType; }
    public void setError(Boolean _Error) { Error = _Error; }
    public void setErrorException(Exception _ErrorException) { ErrorException = _ErrorException; }

}
