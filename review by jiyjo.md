# Code Review

* username and password in application.yml is not changed.
* There are unused imports in AccountBalanceRequest, UserRequest,BookingResponse, TaxiResponse, TaxiController,Booking(model),TaxiRepository,JwtService,TaxiService,UserService,BookingValidator,UserBalanceValidator,TaxiControllerTest,UserControllerTest.
* @RequiredArgsConstructor could be used in TaxiController and UserController instead of creating a constructor using @Autowired.
* There is an enum TaxiStatus in model class which can be written in a separate package named constants.
* There is a commented line of code in TaxiRepository,UserService,UserControllerTest.
* When booking a taxi  in response is shown as null.
* fare deduction from user's account balance is not implemented.
