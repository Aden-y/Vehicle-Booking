
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Booking {
    private String id;
    private double bookingFee=1.5;
    private Date pickUpDateTime;
    private String  firstName;
    private String lastName;
    private int numPssengers;
    private double kilometersTravelled;
    private double tripFee;
    private Car car;
   // private boolean completed=false;
   public Booking(String firstName, String lastName, Date required,
    int numPassengers, Car car){
       this.firstName=firstName;
       this.lastName=lastName;
       this.pickUpDateTime=required;
       this.numPssengers=numPassengers;
       this.car=car;
       this.id=generateBookingID(this.car,this.firstName,this.lastName, new SimpleDateFormat("dd/MM/yyyy").format(required));
      // this.car.book(firstName, lastName, required, numPassengers);
      // System.out.println( this.getDetails());
   }
       private String generateBookingID(Car car, String fname,String lname, String date){
        date=date.replaceAll("/", "");
        return String.format("%s_%s%s_%s", car.getID(),fname.substring(0, 3),lname.substring(0, 3),date);
    }
   public String getDetails(){
       if(this.bookingFee==0.0 || this.kilometersTravelled==0.0){
              return String.format("id:\t\t%s\nBooking Fee:\t$%f\nPick up Date:\t%s\nName:\t\t%s  %s\nPassengers:\t%d\nTravelled:\tN/A\nTrip Fee:\tN/A\nCar Id:\t\t%s", this.id,this.bookingFee,this.getDateString(),
              this.firstName,this.lastName,this.numPssengers,this.car.getID());
       }else{
              return String.format("id:\t\t%s\nBooking Fee:\t$%f\nPick up Date:\t%s\nName:\t\t%s  %s\nPassengers:\t%d\nTravelled:\t%f\nTrip Fee:\t$%f\nCar Id:\t\t%s", this.id,this.bookingFee,this.getDateString(),
              this.firstName,this.lastName,this.numPssengers,
              this.kilometersTravelled,this.tripFee,this.car.getID());
       }
   }
   public String toString(){
      if(this.kilometersTravelled==0.0 || this.bookingFee==0.0){
           return String.format("%s:$1.50:%s:%s:%s:%d:N/A:N/A:%s", this.id,this.getDateString().replaceAll("/", ""),this.firstName,this.lastName,this.numPssengers,this.car.getID());
      }else{
           return String.format("%s:$1.50:%s:%s:%s:%d:%f:%f:%s", this.id,this.getDateString().replaceAll("/", ""),this.firstName,this.lastName,this.numPssengers,this.kilometersTravelled,this.tripFee,this.car.getID());
      }
   }
   public Date getDate(){
       return this.pickUpDateTime;
   }
   
   public String getID(){
       return this.id;
   }
   
   public void completeBooking(double km){
       this.kilometersTravelled=km;
       this.tripFee=this.kilometersTravelled*0.03;
       this.car.completeBooking(this);
       
   }
  public String getCar(){
      return this.car.getID();
  }
  public String getDateString(){
     return new SimpleDateFormat("dd/MM/yyyy").format(pickUpDateTime);
  }
  public String getLname(){
      return this.lastName;
      
  }
  
  public String getFname(){
      return this.firstName;
  }
  public Double getTripFee(){
      return this.tripFee;
  }
      public Car getMyCar(){
        return this.car;
    }
}
