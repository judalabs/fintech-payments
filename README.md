## Payment system for fictional Fintech company
![LINE](https://img.shields.io/badge/line--coverage-84.55%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-75.00%25-yellow.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1.38-brightgreen.svg)

## How to run
```
docker run -d -p 8080:8080 --name finorg rodriginhu/finorg
```

## What is it 
Fictional backend system that handles payments, considering merchants and common users across external APIs.
use these created users if necessary:
- 9e123648-efe5-4715-afa6-0234cbd67613 (COMMON) 
- 5375fc00-7743-4e38-8e60-59417f0674e3 (MERCHANT)


#### Tools 
- Spring Boot (web, data-jpa and validation, devTools).
- Documentation with swagger + README.md .
- H2 memory for testing purposes. Test users already created with uuid's.
- Lombok for reducing boilerplate's.

#### Requirements:
- For both types of users, we need the Full Name, CPF (Brazilian Tax ID), email, and Password. CPF/CNPJ (Company Tax ID) and emails must be unique in the system. Therefore, your system should allow only one registration with the same CPF or email address.
- Users can send money (perform transfers) to merchants and between users.
- Merchants only receive transfers; they do not send money to anyone.
- Validate whether the user has a sufficient balance before the transfer.
- Before finalizing the transfer, an external authorization service should be consulted. Use this mock to simulate (https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6).
- The transfer operation should be a transaction (meaning it can be reversed in case of any inconsistencies), and the money should be returned to the wallet of the sending user.
- Upon receiving a payment, the user or merchant needs to receive a notification (via email, SMS) sent by a third-party service. Additionally, this service might occasionally be unavailable or unstable. Use this mock to simulate the sending (http://o4d9z.mocklab.io/notify).
- This service should be RESTFul.
 
#### Technical considerations:
- RESTFul (HttpMethod's, ResponseEntity, status code).
- Clean Code (custom exceptions
  - documentation in code and test.
  - logs indicating state and not showing sensitive information.
  - clear and meaningful names.
- SOLID (single responsibility, interfaces to external API).
- Encapsulation (user.balance X sender.sendTo(receiver)).
- Atomicity with @Transactional.
- Anemic domain models avoidance.
- Security
  - UUID on sensitive info users.
  - protecting db constraints.
  - hide data from DataIntegrityViolationException to client.
- Maintainability.
- Handling exceptions (creating transactions don't rollback when notification fails).
- Unit and Integration tests.

#### Additional considerations
- Duplicated transaction avoidance (configurable debounce  w/ default of 2 seconds for the same pair sender+receiver).
- Failing notifications should be handled to send another time.
- Handling targeting self transactions.
- performant transactions with db indexes.
- Limiting time when calling external API.
- If the company has many products, then balance field could be in another table to avoid user lock.
