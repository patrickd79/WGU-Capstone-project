package C868;

import C868.Entities.Appointment;
import C868.Entities.Customer;
import C868.Entities.Type;
import C868.Entities.User;
import C868.Helper.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.util.Date;

public class AddAppointmentController {
    @FXML
    public TextField addAppointmentTitleField;
    @FXML
    public TextField addAppointmentLocationField;
    @FXML
    public ComboBox<String> typeCombo;
    @FXML
    public DatePicker addAppointmentStartDate;
    @FXML
    public ComboBox<String> addAppointmentCustIDField;
    @FXML
    public Label addApptErrorField;
    @FXML
    public TextField addApptStartTimeField;
    @FXML
    public TextField addApptEndTimeField;
    @FXML
    public Button addApptBtn;
    ObservableList<Customer> customers = FXCollections.observableArrayList();
    ObservableList<String> customerNames = FXCollections.observableArrayList();
    ObservableList<Type> types = FXCollections.observableArrayList();
    ObservableList<String> typeNames = FXCollections.observableArrayList();
    /**
     * Lambda to convert an integer to a String. It is useful for
     * reducing clutter in some of the methods where String.valueOf() is used frequently
     */
    StringValue sv = i -> String.valueOf(i);

    /**
     * @param start start date and time of the appointment
     * @param end   end date and time of the appointment
     * @return Returns true is the appointment's start and end time fall within the office hours.
     */
    public static boolean isDuringOfficeHours(String start, String end) {
        String startEST = TimeZones.convertToESTTimeZone(start);
        String endEST = TimeZones.convertToESTTimeZone(end);
        String startTimeEST = UpdateAppointmentController.getTime(startEST);
        String endTimeEST = UpdateAppointmentController.getTime(endEST);
        //remove the : from the time and convert to an int
        int startTimeInt = removeColonFromTime(startTimeEST);
        int endTimeInt = removeColonFromTime(endTimeEST);
        //System.out.println("Start Int: "+startTimeInt+ "    End Time Int:"+endTimeInt);
        return startTimeInt > 759 && startTimeInt < endTimeInt && endTimeInt < 2201;
    }

    /**
     * @param customerID
     * @param startDate
     * @param endDate
     * @return Returns true if the customer has another appointment that has overlapping time with the one
     * being passed in.
     */
    public static boolean customerHasOverlappingAppointments(String customerID, String startDate,
                                                             String endDate, String apptID) {
        ObservableList<Appointment> appts = DBAppointment.getAppointmentsForASingleCustomerByID(customerID);
        Date newApptStart = TimeZones.convertStringToDate(startDate);
        //System.out.println("customerHasOverlappingAppointments newApptStart == "+ newApptStart);
        Date newApptEnd = TimeZones.convertStringToDate(endDate);
        //System.out.println("customerHasOverlappingAppointments newApptEnd == "+ newApptEnd);
        for (Appointment a : appts) {
            //check if it is the appointment being updated or modified
            if (!String.valueOf(a.getAppointmentID()).equals(apptID)) {
                Date oldApptStart = TimeZones.convertStringToDate(a.getStart());
                //System.out.println("customerHasOverlappingAppointments oldApptStart == "+
                // oldApptStart);
                Date oldApptEnd = TimeZones.convertStringToDate(a.getEnd());
                //System.out.println("customerHasOverlappingAppointments oldApptEnd == "+
                // oldApptEnd);
                if ((newApptStart.before(oldApptEnd) &&
                        newApptEnd.after(oldApptStart)) || newApptEnd.after(oldApptStart)
                        && newApptStart.before(oldApptStart)) {
                    //System.out.println("customerHasOverlappingAppointments a.start == "+
                    // a.getStart());
                    //System.out.println("customerHasOverlappingAppointments a.end == "+
                    // a.getEnd());
                    System.out.println("customerHasOverlappingAppointments == true");
                    return true;
                }
            }
        }
        System.out.println("customerHasOverlappingAppointments == false");
        return false;
    }

    /**
     * @param time string representing the time portion of the date time.
     * @return Returns an int representing the time portion of the date string with the colon
     * removed, so that mathematical operations can be performed upon it.
     */
    public static int removeColonFromTime(String time) {
        char[] ca = time.toCharArray();
        StringBuilder sb = new StringBuilder();
        String number;
        for (int i = 0; i < 2; i++) {
            sb.append(ca[i]);
        }
        for (int i = 3; i < 5; i++) {
            sb.append(ca[i]);
        }
        number = sb.toString();
        //returns the time with the ':' removed
        return Integer.parseInt(number);
    }

    /**
     * @param time
     * @return Returns the minutes portion of the time string passed in.
     */
    public static String getMinutes(String time) {
        String[] strings = time.split(":", 2);
        return strings[1];
    }

    /**
     * @param time
     * @return Returns the hours portion of the time string passed in.
     */
    public static int getHour(String time) {
        String[] strings = time.split(":", 2);
        int result = Integer.parseInt(strings[0]);
        //System.out.println(result);
        return result;
    }
    public boolean validateFields(String title, String location, String date, String start,
                                         String end){
        System.out.println("Date: " + date);
        if(DataValidation.isValidTitle(title) && DataValidation.isValidLocation(location) &&
        DataValidation.isValidDate(date) && DataValidation.isValidTime(start) &&
                DataValidation.isValidTime(end) && !typeCombo.getSelectionModel().isEmpty()
        && !addAppointmentCustIDField.getSelectionModel().isEmpty()){
            return true;
        }else if (!DataValidation.isValidTitle(title)){
            DataValidation.entryErrorAlert("Title");
        }else if (!DataValidation.isValidLocation(location)){
            DataValidation.entryErrorAlert("Location");
        }else if (!DataValidation.isValidDate(date)){
            DataValidation.entryErrorAlert("Date");
        }else if (!DataValidation.isValidTime(start)){
            DataValidation.entryErrorAlert("Start Time");
        }else if (!DataValidation.isValidTime(end)){
            DataValidation.entryErrorAlert("End Time");
        }else if (addAppointmentCustIDField.getSelectionModel().isEmpty()){
            DataValidation.entryErrorAlert("Customer");
        }else if (typeCombo.getSelectionModel().isEmpty()){
            DataValidation.entryErrorAlert("Appointment Type");
        }
        return false;
    }

    /**
     * Adds an appointment record to the database.
     *
     * @param event
     */
    public void addAppointment(ActionEvent event) {

        try {
            User user = DBUser.getAUserByName("owner");
            Customer customer = DBCustomer.getACustomerByName(addAppointmentCustIDField.getValue());
            String start = addApptStartTimeField.getText();
            String end = addApptEndTimeField.getText();
            String date = addAppointmentStartDate.getValue().toString();
            String startTime = date + " " + start;
            String endTime = date + " " + end;
            String typeID = String.valueOf(DBType.getATypeByName(typeCombo.getValue()).getTypeID());
            String location = addAppointmentLocationField.getText();
            String title = addAppointmentTitleField.getText();
            if(validateFields(title, location, date, start, end)) {
                if (isDuringOfficeHours(startTime, endTime) &&
                        !customerHasOverlappingAppointments(sv.str(customer.getCustomer_ID()),
                                startTime, endTime, "0")) {
                    DBAppointment.addAppointment(title, location,
                            typeID, startTime, endTime,
                            LoginController.thisUser,
                            sv.str(customer.getCustomer_ID()), sv.str(user.getUserID()));
                    addApptErrorField.setTextFill(Color.BLACK);
                    addApptErrorField.setText("Appointment Created");
                    addApptBtn.setDisable(true);

                } else if (!isDuringOfficeHours(startTime, endTime)) {
                    addApptErrorField.setTextFill(Color.RED);
                    addApptErrorField.setText("Please make sure that appointment time is between 0800 " +
                            "EST and 2200 EST.");
                } else if (customerHasOverlappingAppointments(sv.str(customer.getCustomer_ID()),
                        startTime, endTime, "0")) {
                    addApptErrorField.setTextFill(Color.RED);
                    addApptErrorField.setText("Please change the date or time of this appointment. " +
                            "This appointment overlaps another one of the customer's appointments.");
                }
            }
        } catch (Exception e) {
            addApptErrorField.setTextFill(Color.RED);
            addApptErrorField.setText("Please complete all fields");
            e.printStackTrace();
        }
    }

    /**
     * Adds all the customer names to an Observable List of Strings and then sets the combo box with
     * the values.
     */
    public void populateComboBoxCustomerNames() {
        for (Customer c : customers) {
            customerNames.add(c.getCustomer_Name());
        }
        addAppointmentCustIDField.setItems(customerNames);
        System.out.println("Run4");

    }


    public void populateComboBoxTypeNames() {
        for (Type t : types) {
            typeNames.add(t.getTypeName());
        }
        typeCombo.setItems(typeNames);
    }

    public void goToMainMenuWindow(ActionEvent event) throws IOException {
        Main.mainScreen.goToMain(event);
    }

    /**
     * Prepares the screen and organizes data upon opening
     */
    public void initialize() {
        JDBC.openConnection();
        customers = DBCustomer.getAllCustomers();
        System.out.println("Customers: " + customers.size());
        types = DBType.getAllTypes();
        System.out.println("Types: " + types.size());

        populateComboBoxCustomerNames();
        populateComboBoxTypeNames();
    }
}
