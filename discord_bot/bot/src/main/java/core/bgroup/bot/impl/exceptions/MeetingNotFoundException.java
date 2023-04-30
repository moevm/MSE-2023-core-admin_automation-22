package core.bgroup.bot.impl.exceptions;

public class MeetingNotFoundException extends Exception {
    public MeetingNotFoundException(String message) {
        super("Meeting not found:" + message);
    }
}
