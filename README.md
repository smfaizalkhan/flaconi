# flaconi

 ###  Challenge
  
  - Which Carrier is selected. Each Carrier has different delivery days DHL for example needs 2 days, Hermes needs 3 days.
  - The cut-off hour in the warehouse, after this time, a parcel is not shipped on this day anymore.
  - The working times in the warehouse. Our warehouse only works from Monday to Friday.
  - Public Holidays, in Germany we have a lot of common public holidays but Berlin has some special holidays.
  
  ###  Assumptions
  
   - Currently the scope for Holiday List is only in Berlin for 2021(https://www.berlin.de/en/tourism/travel-information/1887651-2862820-public-holidays-school-holidays.en.html)
   - The Holiday list is stored in static way
   - CutOffTime is configuable in application.yaml(12:00)
   
   ###  Room for improvement
  
   - The Holiday List can be externalized as a rest API so as to dynamically check the holiday based on State and Date

   ###  Run the project
  
   - clone the project
   - ./mvnw spring-boot:run
