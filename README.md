# Async Microservices Account System in Java 

This program has developed in Java 17 using Spring Boot 3.3.4, Gradle and PostgreSQL 17 for database.

## How execute the program?

### Database Configuration

First create an empty database in Postgres, in this case I use the default user and port of Postgres. If you need to update the settings of database, you can update the files application.properties of any project. However if you want to use the same default 
settings, just set the same variables you can see in this image:

For client and account service, the properties file is the same only changing the 8081 port for client and 8082 port for account service:

![image](https://github.com/user-attachments/assets/a3f3128f-654a-4150-8a1f-d7500faa6759)  ![image](https://github.com/user-attachments/assets/e2e77886-20d0-4637-88b4-8c85e6b4cd23)

For accountstatement service (report service), it uses the 8083 port and this is the properties file:

![image](https://github.com/user-attachments/assets/3fa36bf6-ae3a-4f2d-b4d9-4e559d8af301)

If you want to restore a database, there is a backup file in "sql" folder:

![image](https://github.com/user-attachments/assets/214993e8-8a1a-4648-9e67-4d2d980969b2)

### Run Program

To run the program, open all project folders in your IDE. These java projects were made using Gradle, so check it first in your IDE. Execute The program in your IDE and you will something like this:

![image](https://github.com/user-attachments/assets/159e7e72-17d0-40e7-b947-3b540d05b875)

If is the first time you run the program, it will create the tables to database automatically.

### How to prove it?

In "postman" folder, you will see a collection file of Postman in JSON. If you import it. You will see this folders with all data you need, so just run it one by one:

![image](https://github.com/user-attachments/assets/59d4d03f-a7e4-41ab-a8fd-ac73acebc468)

### Funcionality Examples

After run in Postman the POST options, you can check by ID or get all registers:

![image](https://github.com/user-attachments/assets/cc3b7fb1-eaee-4769-8c6f-c51d35dfde90)

![image](https://github.com/user-attachments/assets/db355a3c-2ad8-4588-9998-e58ff605cde6)

![image](https://github.com/user-attachments/assets/bc1f013d-b9ca-41a7-88ca-4dea5329c501)

![image](https://github.com/user-attachments/assets/59db96df-c482-4883-9cff-348db1c48a32)

![image](https://github.com/user-attachments/assets/edc302f1-e0d5-498a-96dc-907ad84082a8)

![image](https://github.com/user-attachments/assets/78ca6661-e3cc-4d05-9346-ca433cdc9933)

### Kafka

To prove with Kafka, we can use Offset Explorer and create a new connection with this configuration:

![image](https://github.com/user-attachments/assets/9d46fad1-2bea-4a89-a51b-7ec6a7a119a3)

When you send creation or updates of new clients, accounts or transactions, you can see the messages sent on this:

![image](https://github.com/user-attachments/assets/3a9dcd3a-8b7d-4d01-8af6-cbda82dd5e9a)

![image](https://github.com/user-attachments/assets/f79a7c89-04b4-4fe9-8b5c-d9a5380c4c14)

![image](https://github.com/user-attachments/assets/294ee90b-c6c5-474e-8791-d849f0051ba8)

In AccountStatement service, on log, you can see the consumer of the messages working:

![image](https://github.com/user-attachments/assets/b02ac286-3baf-4ba9-bfc9-4c6d360e62df)
