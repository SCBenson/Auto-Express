# Auto-Express
Auto-Express is a tool designed to allow car dealerships to easily manage their inventory and connect them with potential buyers. It provides a user-friendly interface for dealerships to list their cars, and for buyers to browse and inquire about available vehicles.


## Tech Stack
- **Frontend**: React.js, Tailwind CSS
- **Backend**: Java Springboot, Gradle and Java 21

## Setup Instructions
1. Clone the repository: `git clone https://github.com/SCBenson/Auto-Express.git'
2. Navigate to the project directory: `cd Auto-Express`
3. Install dependencies: `./gradlew build`
4. Run the application: `./gradlew bootRun`
5. Access the application at `http://localhost:8080`
6. Use the provided credentials to log in and explore the features of Auto-Express.

## How to Run The Application
1. Ensure you have Java 21 installed on your machine.
2. Open a terminal and navigate to the project directory.
3. Run the application using the command: `./gradlew bootRun`
4. Once the application is running, open a web browser and go to `http://localhost:8080` to access the Auto-Express interface.
5. Use the provided credentials to log in and explore the features of Auto-Express, such as listing cars, browsing inventory, and connecting with potential buyers.
6. To stop the application, return to the terminal and press `Ctrl + C`.

## API Endpoints Overview
- `POST /api/cars`: Add a new car to the inventory.
- `GET /api/cars`: Retrieve a list of all cars in the inventory.
- `GET /api/cars/{id}`: Retrieve details of a specific car by its ID.
- `PUT /api/cars/{id}`: Update the details of a specific car by its ID.
- `DELETE /api/cars/{id}`: Remove a specific car from the inventory by its ID.
- `POST /api/inquiries`: Submit an inquiry about a specific car.
- `GET /api/inquiries`: Retrieve a list of all inquiries.
- `GET /api/inquiries/{id}`: Retrieve details of a specific inquiry by its ID.
- `DELETE /api/inquiries/{id}`: Remove a specific inquiry by its ID.
- `POST /api/auth/login`: Authenticate a user and obtain a JWT token.
- `POST /api/auth/register`: Register a new user account.
- `GET /api/users`: Retrieve a list of all users (admin only).
- `GET /api/users/{id}`: Retrieve details of a specific user by their ID (admin only).
- `DELETE /api/users/{id}`: Remove a specific user from the system by their ID (admin only).
- `GET /api/dealers`: Retrieve a list of all dealers.
- `GET /api/dealers/{id}`: Retrieve details of a specific dealer by their ID.
- `POST /api/dealers`: Add a new dealer to the system (admin only).
- `PUT /api/dealers/{id}`: Update the details of a specific dealer by their ID (admin only).
- `DELETE /api/dealers/{id}`: Remove a specific dealer from the system by their ID (admin only).
- `GET /api/analytics/sales`: Retrieve sales analytics data (admin only).
- `GET /api/analytics/inventory`: Retrieve inventory analytics data (admin only).
- `GET /api/analytics/inquiries`: Retrieve inquiry analytics data (admin only).

## Environment Variables Needed
- `DATABASE_URL`: The URL of the database to connect to.
- `DATABASE_USERNAME`: The username for the database connection.
- `DATABASE_PASSWORD`: The password for the database connection.
- `JWT_SECRET`: The secret key used for signing JWT tokens.
- `PORT`: The port number on which the application will run (default is 8080).
- `SPRING_PROFILES_ACTIVE`: The active Spring profile (e.g., `dev`, `prod`).
- `LOG_LEVEL`: The logging level for the application (e.g., `INFO`, `DEBUG`, `ERROR`).
- `CORS_ALLOWED_ORIGINS`: A comma-separated list of allowed origins for CORS requests (e.g., `http://localhost:3000`).
- `EMAIL_HOST`: The SMTP host for sending emails (e.g., `smtp.gmail.com`).
- `EMAIL_PORT`: The SMTP port for sending emails (e.g., `587`).
- `EMAIL_USERNAME`: The username for the email account used to send emails.
- `EMAIL_PASSWORD`: The password for the email account used to send emails.

## Contact Information
For any questions or support, please contact us at:
- Email: sean_c_benson@outlook.com
  - GitHub: [www.GitHub.com/SCBenson]
  - LinkedIn: [Sean Benson](https://www.linkedin.com/in/sean-benson-123456789/)
