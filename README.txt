-----Validity Checker-----

* Requires Java 17 to run.

  
* Any data can be given to the input option for validation, the 
  checks can be run in any order, one at a time or all together.    
  To run the Validity Checker execute following examples in a 
  command line standing in the same directory as the jar file.
  
  
java -jar ValidityChecker-1.0-SNAPSHOT.jar -input "DDD 222" -order "CarRegistrationNumberCheck SocialSecurityCheck NotNullCheck"

java -jar ValidityChecker-1.0-SNAPSHOT.jar -input "19780202-2389" -order "SocialSecurityCheck NotNullCheck"

Please note:

Running without arguments brings up the help menu.

Also remember to have quotes around input with spaces.





