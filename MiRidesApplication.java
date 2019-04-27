
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class MiRidesApplication {
    private Car[] cars=new Car[100];
    private Booking[] bookings=new Booking[100];
    
   /*See if booking exists*/
    private boolean bookingExist(String regDate){
        boolean found=false;
        for(int i=0;i<bookings.length;i++){
            if(bookings[i]==null){
                break;
            }
            if(bookings[i].getCar().equals(regDate) || bookings[i].getDateString().equals(regDate)){
             found=true;
             break;
            }
        }
        return found;
    }
    /*Display specicic car*/
    private void displaySpecific(){
        System.out.print("Enter Registration No:");
        String carId=new Scanner(System.in).nextLine().toUpperCase();
        boolean found=false;
        for(int i=0;i<cars.length; i++){
            if(cars[i]!=null){
                if(cars[i].getID().equals(carId)){
                    found=true;
                    System.out.println(cars[i].getDetails());
                    break;
                }
            }
        }
        if(!found){
            System.out.println("Error - The car could not be located.");  
            return;
        }
    }
    /*Displays all cars*/
    
   private void displayAll(){
       boolean found=false;
       for(int i=0;i<cars.length;i++){
           if(cars[i]!=null){
               found=true;
               System.out.println(cars[i].getDetails());  
           }
       }
       if(!found){
           System.out.println("Error- No Cars to display.");
           return;
       }
   }
    /*Completes the booking*/
    private void completeBooking(){
        System.out.print("Enter Registration or Booking Date:");
        Scanner sn=new Scanner(System.in);
        String dateReg=sn.nextLine().toUpperCase();
        if(!bookingExist(dateReg)){
            System.out.println("Error - The booking could not be located.");
        return;
        
        }
        System.out.print("Enter first name:");
        String fname=sn.nextLine();
        System.out.print("Enter Last name:");
        String lname=sn.nextLine(); 
        boolean found=false;
        for(int i=0;i<bookings.length;i++){
        if(bookings[i]==null){
        System.out.println("Error - The booking could not be located.");
        return; 
         }
        if(bookings[i].getFname().equals(fname) && bookings[i].getLname().equals(lname)){
            found=true;
            System.out.print("Enter kilometers:");
            bookings[i].completeBooking(Double.parseDouble(sn.nextLine()));
            System.out.println("Thank you for riding with MiRide. We hope you enjoyed your trip.\n" +
            "$"+bookings[i].getTripFee()+" has been deducted from your account.");
            bookings[i].getMyCar().completeBooking(bookings[i]);
            return;
        }
         
        }
    if(!found){
        System.out.println("Error - The booking could not be located.");
    }
    }
    
    
    private boolean validateName(String name){
        if(name.length()>=3){
            return true;
        }else{
            return false;
        }
    }

    /*For booking */
    private void bookCar() throws ParseException{
        int count=0;
        Car[] available=new Car[cars.length];
        boolean av=false;
        System.out.print("Enter Date Required: ");
        String day=new Scanner(System.in).nextLine();
        Date date=new SimpleDateFormat("dd/MM/yyyy").parse(day);
        Date today=new Date();
        long afterAweek=today.getTime()+(7*24*60*1000);
        if(new Date().getTime()>date.getTime()){
         
            System.out.println("Error -Date can not be in the past.");
            return;
        }/*else if(afterAweek < date.getTime()){
               System.out.println("Leo=="+afterAweek);
            System.out.println("Then=="+date.getTime());
          System.out.println("Error -Date can not be more than one week from today.");
          return;
        }*/
        for(int n=0;n<cars.length;n++){
            if(isEmpty(cars)){
                System.out.println("Error - No cars are available on this date");
               return;
            }
            if(cars[n]!=null){
                 if(cars[n].isAvailable()){
                if(!cars[n].takenThisDay(day)){
                    if(count==0){
                        System.out.println("The following cars are available."); 
                    }
                    av=true;
            System.out.println((count+1)+". "+cars[n].getID());
            available[count]=cars[n];
            count++;
                }
            }
            }
            
        }
        
        if(!av){
          System.out.println("Error - No cars are available on this date");   
          return;
        }
 
        
        int input=0;
        Scanner sc=new Scanner(System.in);
        while(input==0 || input>count || input<1 ){
           System.out.print("Please select the number next to the car you wish to book:"); 
           input=Integer.parseInt(sc.nextLine());
        }
        System.out.print("Enter First Name:");
        String fname=sc.nextLine();
        if(!validateName(fname)){
            System.out.println("Name must be more than 3 characters");
            return;
        }
        
        System.out.print("Enter Last Name:");
        String lname=sc.nextLine();
         if(!validateName(lname)){
            System.out.println("Name must be more than 3 characters");
            return;
        }
        System.out.print("Enter Number of Passengers:");
        int pass=Integer.parseInt(sc.nextLine());
        Booking bk=new Booking(fname,lname,new SimpleDateFormat("dd/MM/yyyy").parse(day),pass,available[input-1]);
        addToBookings(bk);
        available[input-1].book(fname, lname, new SimpleDateFormat("dd/MM/yyyy").parse(day), pass);
        System.out.println("Thank you for your booking. "+available[input-1].getDriver()+" will pick you up on "+day+".\n" +
        "Your booking reference is:"+bk.getID());
     }
    /*add to bookings*/
    
    /*Seed Data*/
    private void seedData(){
        if(isEmpty(cars)){
            Car car1=new Car("XDC789","New Make","Nissan","Eunice Nyambura",8);
            Car car2=new Car("XDC889","Modern","Toyota","Joakim Adeny",8);
            Car car4=new Car("XDC769","New Make","Nissan","Briver Brivo",8);
            Car car5=new Car("XDC111","Modern","ISUZU","Carlos Andrew",8);
            Car car3=new Car("XDC899","New Make","Nissan","Someone Random",6);
            Car car6=new Car("XDC888","Old Make","Nissan","Kimani Wanga",6);
            
            Booking booking1=new Booking("David","Malamu",new Date(),7,car1);
            Booking booking2=new Booking("Kevin","Omondi",new Date(),6,car2);
            Booking booking3=new Booking("Brian","Brayo",new Date(),3,car3);
            Booking booking4=new Booking("Sumson","Mutangili",new Date(),6,car4);
            
            
            addCarSeed(car1);
            addCarSeed(car2);
            addCarSeed(car3);
            addCarSeed(car4);
            addCarSeed(car5);
            addCarSeed(car6);
            
            bookViaSeed(booking1);
            bookViaSeed(booking2);
            bookViaSeed(booking3);
            bookViaSeed(booking4);
            
            
            completeBookingViaSeed(booking1,56.0,car1);
            completeBookingViaSeed(booking2,89.30,car2);
            
            System.out.println("Six cars added");
            
            
        }else{
            System.out.println("Error - Cars already in store.");
            
        }
    }
    
    /*Complete Booking Via SeedData*/
    private void completeBookingViaSeed(Booking booking,double km,Car car){
        //   bookings[i].getMyCar().completeBooking(bookings[i]);
        booking.completeBooking(km);
        car.completeBooking(booking);
    }
    
    /*Book car via seedData Method*/
    private void bookViaSeed(Booking booking){
       for(int i=0;i<bookings.length;i++){
           if(bookings[i]==null){
               bookings[i]=booking;
               return;
           }
       }
    }
    /*Adds a car through the seed method*/
    private void addCarSeed(Car car){
        for(int i=0;i<cars.length;i++){
            if(cars[i]==null){
                cars[i]=car;
                return;
            }
        }
    }
    
    /*Checks if an array is empty*/
    private boolean isEmpty(Object[] array){
       boolean empty=true;
       for(int i=0;i<array.length;i++){
           if(array[i]!=null){
              // System.err.println("Car found");
               empty=false;
               break;
           }
       }
       return empty;
    }
    private void addToBookings(Booking bk){
        for(int i=0;i<bookings.length;i++){
            if(bookings[i]==null){
                bookings[i]=bk;
                return;
            }
        }
    }
    /*Creates a new car*/
    private  void createCar(){
        Scanner sc=new Scanner(System.in);
        
        System.out.print("Enter Registration No:");
        String regNo=sc.nextLine();
        if(!validateReg(regNo)){
            System.out.println("Error - Registration number is invalid.");
            return;
        }
        if(carExist(regNo)){
          
            System.out.println("Error - already exists in the system.");
        return;
        }
         System.out.print("Enter Make:");
         String make=sc.nextLine();
         System.out.print("Enter Model:");
         String model=sc.nextLine();
         System.out.print("Enter Driver's Name:");
        String  driverName=sc.nextLine();
         System.out.print("Enter Passenger Capacity:");
        int  passengerCapacity=Integer.parseInt(sc.nextLine());
         if(passengerCapacity <1 || passengerCapacity>10){
           System.out.println("Error - Invalid passanger capacity.");   
         return;
         }
        addCar(new Car(regNo.toUpperCase(),  make,
        model,  driverName,  passengerCapacity));
    }
    /*adds new car to array*/
    private void addCar(Car car){
        for(int i=0; i<cars.length;i++){
            if(cars[i]==null){
                cars[i]=car;
                break;
            }
        }
        System.out.println("New Car added successfully for registion number: "+car.getID());
    }
    /*Validates input for car reg number*/
    private boolean validateReg(String reg){
        String regex="\\b[a-zA-Z]{3}\\d{3}\\b";
        if(Pattern.matches(regex, reg)){
            return true;
        }else{
            return false;
        }
    }
    /*See if car already added*/
    private boolean carExist(String  reg){
        boolean found=false;
        for(int i=0;i<cars.length;i++){
            
            if(cars[i]!=null){
                if(reg.equals(cars[i].getID())){
                found= true;   
            }
            if(found){
                break;
            }
            }
        }
        return found;
    }
    private static String  menu(){
        Scanner in=new Scanner(System.in);
        String input="";
        while (input==""){
            System.out.println("*** MiRides System Menu ***");
            System.out.print("Create Car\t\t\tCC\n");
            System.out.print("Book  Car\t\t\tBC\n");
            System.out.print("Complete Booking\t\tCB\n");
            System.out.print("Display All Cars\t\tDA\n");
            System.out.print("Search Specific Car\t\tSS\n");
            System.out.print("Search available cars\t\tSA\n");
            System.out.print("Seed Data\t\t\tSD\n");
            System.out.print("Exit Program cars\t\tEX\n");
        String userInput=in.next().toUpperCase();
            if(userInput.equals("CC") || userInput.equals("BC") ||userInput.equals("CB") || userInput.equals("DA") || userInput.equals("SS") || userInput.equals("SA") || userInput.equals("SD") || userInput.equals("EX")){
                input=userInput;
            }else{
                System.out.println("Invalid option. Try again");
            }
        }
        return input;
    }
    public static void main(String [] args) throws ParseException{

        MiRidesApplication app=new MiRidesApplication();
       String menu= menu();
       
       while(!menu.equals("EX")){
           if(menu.endsWith("CC")){
               app.createCar();
           }else if(menu.equals(("BC"))){
               app.bookCar();
           }else if(menu.equals("CB")){
               app.completeBooking();
           }else if(menu.equals("DA")){
               app.displayAll();
           }else if(menu.equals("SS")){
               app.displaySpecific();
           }else if(menu.equals("SA")){
               app.displaySpecific();
           }else if(menu.equals("SD")){
               app.seedData();
           }
           menu=menu();
       }
    }
}
