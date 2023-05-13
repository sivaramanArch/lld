### Parking system 


1. Actors : 
   1. Customer
   2. Parking Agent
   3. Parking System

2. Entities 
   1. Vehicle
   2. Parking Slot 
   3. Parking Ticket
   4. Parking Display
   5. Parking System ( Composite , Singleton )
   6. Entry
   7. Exit

3. Enumerations
   1. Vehicle Type 
   2. Parking Slot Type 



#### User journey : 

- Customer : 
  - Visits the Entry 
  - Checks the display - shows how much empty space is available 
  - Requests for ticket 
  - Proceeds to Park the vehicle 

- Parking Agent : 
  - At Entry : 
    - Receives a vehicle 
    - If Space available 
      - Assigns slot to vehicle 
      - Generate ticket 
    - Return ticket 
  - At Exit: 
    - Receives ticket 
    - Validates ticket 
    - Requests payment 
    - If Payment success - free up slot

- Parking System: 
  - Add new slot 
  - Remove existing slot 
  - Update slot as operational / not operational 
  - Mark entry / exit as operational / not operational

#### Entities Deep dive :

1. Vehicle
   1. String regNo
   2. VehicleTypeEnum vehicleType
2. Parking Slot
   1. String id
   2. ParkingSlotTypeEnum slotType
   3. Vehicle vehicle
   4. Boolean isOperational 
   
3. Parking Ticket
   1. String id
   2. ParkingSlot slot 
   3. LocalDateTime startTime 
   4. LocalDateTime endTime 
   5. Boolean isPaid
   6. Double amount 
4. Parking Display
   1. Map<String, Integer> openSlotCountBySlotType; 
5. Entry
   1. String gateId
   2. Boolean isOperational
   
6. Exit 
   1. String gateId
   2. Boolean isOperational

7. Parking System ( Composite , Singleton )
   1. Entry
   2. Exit
   3. DisplayBoard
   4. Slots 



