Revolut-test:

- This application was written in Java 12.
- Frameworks/Library used:
    - Javalin: Lightweight Java/Kotlin web framework (used to manage the API endpoints)
    - JOOQ: A fluent API for typesafe SQL query construction and execution (used to do easier and more understandable the different SQL queries)
    - JUnit: Unit testing framework for the Java programming language. (Used for testing)
    - HSQLDB: Relational database management system written in Java. (Used as in-memory datastore)
    - Jackson: High-performance JSON processor for Java. (Used to map Json's to Java Objects)

- Instructions to execute:
  - With script (compileAndRun.sh):
     - Assign the correct permissions to the file compileAndRun.sh (Linux example: $ sudo chmod +x compileAndRun.sh)
     - Run the script ($ ./compileAndRun.sh) or ($ sh compileAndRun.sh)
  - Without script:
    - Run in the terminal the following commands
        - $ mvn clean compile
        - $ mvn clean install
        - $ mvn clean package
        - $ mvn exec:java -Dexec.mainClass="com.revolut.rest.RestApplication"

- Enpoints:
  - GET http://localhost:7000/        --> Welcome page
  - GET http://localhost:7000/account
  - GET http://localhost:7000/account/:accountId  
  - POST http://localhost:7000/account
  - POST http://localhost:7000/transaction 
  - GET http://localhost:7000/transaction 

Body example for account:
    {
		"balance": 30,
		"id": "GB3343324233"	
    }
    
Body example for transaction:
    {
		"debtor": "NL12422123",	
		"creditor": "FL12312312",
		"amount": 3.33
    }
- Additional comments:
    - Multithreading test: Have chosen 100 threads to work at the same time, enough to test any concurrent issue and enough small to not of crash the memory.

Thanks for your time, for any question or error do not hesitate in contacting me: albertpereztoro@gmail.com

Any feedback or recommendation will be appreciated.