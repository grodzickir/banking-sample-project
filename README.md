# Sample Spring Boot + Mongo REST API

Including Spring Security Basic configuration

## Setup
1. run `docker.sh` or `docker.bat` to start mongo
2. start the app through main() in Application.kt

## REST interface
1. users are defined in application.yaml
2. use HTTP Basic Authorization. Token can be generated in TokenGenerator test (after removing @Ignored) 

## Tests
EndToEndTest.kt has tests mentioned in requirements

## Potential improvements
- Caching customers queries


# Task definition - Bank audit application
## 1. Create REST API application for retrieving bank's clients data.
API will be used by bank employee for auditing operations on accounts. For simplicity the user will be authenticated using Basic HTTP Authorization. The users list may be predefined in any way ie. the properties file.
The API interface accepts two parameters: client's identification number and the identifier of account type. In response the user gets a list of transactions, sorted ascending by transaction amount, with transaction date, amount, id, name of the account type, and first and last name of the ordering client.

## 2. Input data:
  - transactions.csv - transactions with their amount, customers' ids and account type id
  - accounttypes.csv - account types with their identifiers
  - customers.csv - customers data
  
## 3. Search parameters:
  a) account_type - it may be a single account type id (ie. "1"), list of identifiers ("1, 3") or the special value "ALL" which returns all account types. Empty parameter equals the "ALL" wildcard.
  b) Ordering client id - analogous a single id, a list, "ALL" value or empty.
  
## 4. Technical requirements:
  Spring Boot application written in Kotlin. Provided data should be loaded at the start of application to MongoDB. Every time the application is started the data should be removed and loaded again. Collections can be splitted however one wants. Read speed should be priority.
  
  Preferred build tools: Maven or Gradle 5+
  
  The endpoint should have at least 3 unit tests - with both arguments stated, one argument empty and both arguments empty or "ALL".
