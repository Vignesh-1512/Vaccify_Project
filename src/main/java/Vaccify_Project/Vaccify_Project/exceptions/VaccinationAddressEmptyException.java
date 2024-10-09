package Vaccify_Project.Vaccify_Project.exceptions;

public class VaccinationAddressEmptyException extends RuntimeException{
    public VaccinationAddressEmptyException (String msg)
    {
        super(msg);
    }
}
