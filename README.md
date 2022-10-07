## Technologies & Libraries
- Java 11
- Maven
- Spring Boot
- Docker
- Swagger
- Spring JPA 
- Spring Security
- Json Web Tokens
- Mapstruct
- Junit
- Mocito

## Build & Run

### Prerequesties
Docker engine

### Build an application image
docker build -t bstm-app:1.0 .

### Run and publish application image
docker run -it --name bstm-app --rm -p 8080:8080 bstm-app:1.0

## Testing

### Test Files
can be found under src/test folder

### During building the application image
docker build -t bstm-app:1.0 .

## Entity Structure
- User
- Role
- Book
- Order(user_id)
- OrderLineItem(order_id, book_id, quantity)
- Stats(user_id)

## APIs


Note that; all required request body, request param, path variables can be found using swagger ui.

### Port
:8080

### Context Path
/api/v1

### Token Example
Bearer jwt_token

### Swagger ui (In Local)

http://localhost:8080/api/v1/swagger-ui.html


### Auth Controller

#### POST /auth/login 

- Generetes and returns jwt tokens with credentials
- Permitted all

#### POST /auth/register

- Creates Customer user
- Permitted all

#### POST /auth/register/admin

- Creates Admin user
- Permitted all

#### Book Controller

#### GET /book

- Returns all books

- Permitted only registered customers and admins.


#### GET /book/{id}

- Returns a book with path variable id

- Permitted only registered customers and admins.

#### POST /book

- Creates a book

- Permitted only registered admins.


#### PATCH /book/{id}

- Updates book stock with path variable id

- Permitted only registered admins.

### Order Controller

#### GET /order

- Returns all orders ordering by id

- Permitted only registered admins.

#### GET /order/{id}

- Returns an order with path variable id

- Permitted only registered admins.


#### GET /order/filter?start=START&end=END

- Returns orders with given start date and end date

- Permitted only registered admins.

#### POST /order

- Creates an order

- Permitted only registered admins and customers.

### Stats Controller

#### GET /stats?userId=USERID&year=YEAR

- Returns users' order statistics with given year

- Permitted only registered customers and admins.

### User Controller

#### GET /user/{id}/order?pageNumber=PAGENUM&pageSize=PAGESIZE

- Returns users' order with given pagination informations 

- Permitted only customer and admin users. 
