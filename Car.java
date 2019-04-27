
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
public class Car {
    private String regNo;
    private String make;
    private String model;
    private String driverName;
    private int passengerCapacity;
    private boolean available=true;
    private Booking[] currentBookings=new Booking[5];
    private Booking[] pastBookings =new Booking[100];
    
   public Car(String regNo, String make,
       String model, String driverName, int passengerCapacity){
       this.passengerCapacity=passengerCapacity;
       this.regNo=regNo;
       this.make=make;
       this.model=model;
       this.driverName=driverName;
    
      // System.out.println(this.toString());
       
   }
     public boolean book(String firstName, String lastName, Date required,
    int numPassengers){
         
     addToBooking(this.currentBookings,new Booking ( firstName,  lastName,  required,
     numPassengers,  this));
     if(this.currentBookings[4]!=null){
         this.available=false;
     }
     return true;
     } 
     public void addToBooking(Booking [] bookings,Booking booking){
         for(int i=0;i<bookings.length;i++){
             if(bookings[i]!=null){
                 bookings[i]=booking;
                 break;
             }
         }
         
     }
     public String getDetails(){
         if(this.available){
             return String.format("RegNo:\t\t%s\nMake & Model:\t%s & %s\nDriver Name:\t%s\nCapacity:\t%d\nAvailability:\tYES", this.regNo,this.make,this.model,this.driverName,this.passengerCapacity);
         }else{
              return String.format("RegNo:\t\t%s\nMake & Model:\t%s & %s\nDriver Name:\t%s\nCapacity:\t%d\nAvailability:\tNO", this.regNo,this.make,this.model,this.driverName,this.passengerCapacity);
         }
     }
    public String toString(){
        if(this.available){
            return String.format("%s:%s:%s:%s:%d:YES", this.regNo,this.make,this.model,this.driverName,this.passengerCapacity);
        }else{
            return String.format("%s:%s:%s:%s:%d:NO", this.regNo,this.make,this.model,this.driverName,this.passengerCapacity);
        }
    }
    public String getID(){
        return this.regNo;
    }
    public boolean isAvailable(){
        return this.available;
    }
    
    
    public boolean takenThisDay(String day){
        boolean taken=false;
      //  SimpleDateFormat date=new  SimpleDateFormat(day);
        
        for(int i=0;i<this.currentBookings.length;i++){
            if(this.currentBookings[i]==null){
                break;
                
            }else{
                Date bookingDate=this.currentBookings[i].getDate();
               if(new SimpleDateFormat("dd/MM/yyyy").format(bookingDate).equals(day)){
                   taken=true;
                   break;
               } 
            }
        }
        return taken;
    }
    public String getDriver(){
        return this.driverName;
    }
    public void completeBooking(Booking booking){
        addToBooking(this.pastBookings,booking);
    }

    
}
