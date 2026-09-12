# Online Auction System

A real-time online auction platform built with **Spring Boot**, **WebSocket**, **MySQL**, and **Bootstrap**.

Users can register, create auctions, place live bids, and see real-time updates. Auctions are automatically closed when the time expires, and the highest bidder is declared the winner.

---

## Features

- User Registration & Login
- Create, Start, and Close Auctions
- Real-time Bidding using WebSocket
- Live Bid Updates without page refresh
- Automatic Auction Closing with Winner Declaration
- Responsive Frontend (Bootstrap 5)
- MySQL Database Integration
- Global Exception Handling

---

## Tech Stack

| Layer       | Technology                          |
|-------------|-------------------------------------|
| Backend     | Java, Spring Boot, Spring Data JPA  |
| Real-time   | Spring WebSocket + STOMP + SockJS   |
| Frontend    | HTML, CSS, Bootstrap 5, JavaScript  |
| Database    | MySQL                               |
| Build Tool  | Maven                               |

---

## Modules

- **User Module** – Register, Login
- **Auction Module** – Create, Start, Close, View Auctions
- **Bidding Module** – Place bids, View bid history
- **Real-time Module** – Live bid broadcasting using WebSocket
- **Scheduler** – Automatically closes expired auctions

---

## Project Structure

auction-system/
├── src/main/java/auction_system/
│   ├── config/          # WebSocket & Scheduler configuration
│   ├── controller/      # REST Controllers
│   ├── entity/          # JPA Entities (User, Auction, Bid)
│   ├── enums/           # Role, AuctionStatus
│   ├── exception/       # Global Exception Handling
│   ├── repository/      # Spring Data JPA Repositories
│   └── service/         # Business Logic
├── src/main/resources/
│   ├── static/          # Frontend (index.html)
│   └── application.properties
└── pom.xml


---

## How to Run

### Prerequisites
- Java 17 or higher
- MySQL
- Maven / IntelliJ IDEA

### Steps

1. Clone the repository
``bash
git clone https://github.com/mohammadmudassir245/online-auction-system.git 

2. Create a MySQL database (or let the app create it automatically)
3. Update src/main/resources/application.properties with your MySQL password:
   spring.datasource.password=your_password
4. Run the application
   mvn spring-boot:run
5. or run the main class AuctionSystemApplication from IntelliJ.Open the application in browser:
   http://localhost:8090

Method           Endpoint                 Description
POST         /api/users/register       Register a new user
POST         /api/users/login             User login
POST         /api/auctions              Create auction
GET          /api/auctions              Get all auctions
GET          /api/auctions/{id}         Get auction by ID
PUT          /api/auctions/{id}/start   Start an auction
PUT          /api/auctions/{id}/close   Close an auction
POST         /api/bids                    Place a bid
GET          /api/bids/auction/{id}     Get bids of an auction


Future ImprovementsPassword encryption (BCrypt)
JWT based authentication
Image upload for auction items
Email notifications
Search & filter auctions
Seller dashboard

AuthorMohammad Mudassir
GitHub: mohammadmudassir245


---

### How to add it to GitHub:

1. In your project folder, create `README.md` and paste the content above.
2. Run these commands:

```bash
git add README.md
git commit -m "Added project README"
git push



