package Vaccify_Project.Vaccify_Project.exceptions;

public class AppointmentAlreadyDoneException extends  RuntimeException{
    public AppointmentAlreadyDoneException(String msg)
    {
        super(msg);
    }
}
