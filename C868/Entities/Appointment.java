package C868.Entities;

import C868.Helper.DBCustomer;
import C868.Helper.DBType;

/**
 * This is the class to create Appointment objects.
 * @author Patrick Denney
 *
 */

public class Appointment {

    private int appointmentID;
    private String title;

    private String location;
    private String type;
    private String typeName;
    private String start;
    private String end;
    private String createdDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdatedBy;
    private int customerID;//fk
    private int userID;//fk
    private String customerName;

    /**
     * Constructor method for the Appointment Object
     * @param appointmentID This is the auto-generated ID number for the individual appointment.
     * @param title This is the Title given to the appointment.
     * @param location This is the location of the appointment.
     * @param type This is the type of appointment to be held.
     * @param start The start date and time of the appointment.
     * @param end The scheduled end date and time of the appointment.
     * @param createdDate Date and time that the appointment was created.
     * @param createdBy The user who created the appointment.
     * @param lastUpdate Date and time when the appointment information was last updated in the database.
     * @param lastUpdatedBy The user who updated the appointment information last.
     * @param customerID ID number of the customer associated with the appointment.
     * @param userID ID number of the user associated with the appointment.
     */
    public Appointment(int appointmentID, String title, String location, String type, String start, String end, String createdDate, String createdBy, String lastUpdate, String lastUpdatedBy, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.location = location;
        this.type = type;
        this.typeName = DBType.getATypeByID(type).getTypeName();
        this.start = start;
        this.end = end;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.customerName = DBCustomer.getACustomerByID(customerID).getCustomer_Name();
        this.userID = userID;
    }

    public Appointment() {
    }


    /**
     *
     * @return Returns the ID number of the appointment.
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @return Returns the title of the appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the appointment
     * @param title title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return Returns the location of the appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the appointment
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return Returns the type of the appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the appointment
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     *
     * @return Returns the start date and time of the appointment.
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start date and time of the appointment
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return Returns the end date and time of the appointment.
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end date and time of the appointment
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     *
     * @return Returns the date and time the appointment was first created.
     */
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date and time that the appointment was created.
     * @param createdDate
     */
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * @return Returns the user who created the appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created the appointment.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     *
     * @return Returns the date and time that the appointment was last updated.
     */
    public String getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Sets the date and time that the appointment was last updated.
     * @param lastUpdate
     */
    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     *
     * @return Returns the user who last updated the appointment information.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Sets the user who last updated the appointment information
     * @param lastUpdatedBy
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     *
     * @return Returns the ID number of the customer associated with the appointment.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the ID number for the customer associated with the appointment.
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(int customerID) {
        this.customerName = DBCustomer.getACustomerByID(customerID).getCustomer_Name();
    }



    /**
     *
     * @return Returns the ID number of the user associated with the appointment.
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the ID number for the user associated with the appointment.
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }




}
