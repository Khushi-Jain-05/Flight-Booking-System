# Flight Booking System

## Description
The **Flight Booking System** is a Java-based application that provides a user-friendly interface for booking flight tickets. It allows users to select the departure and destination locations, choose between economy and business class, and opt for meal services. The system calculates ticket prices based on the distance between the selected locations and displays the total price to the user.

## Features
- **Graphical User Interface (GUI):** Built using Java Swing for an interactive experience.
- **Flight Selection:** Users can enter the origin and destination locations.
- **Class Selection:** Choose between Economy and Business class.
- **Meal Service:** Option to add meal service with additional charges.
- **Price Calculation:** Ticket price is determined based on flight distance and selected services.
- **Distance Calculation:** Uses OpenWeatherMap API to fetch coordinates and compute distance using the Haversine formula.
- **Error Handling:** Handles invalid location inputs by displaying appropriate error messages and preventing incorrect calculations.

## Technologies Used
- **Java (Swing for GUI)**
- **OpenWeatherMap API** (to fetch location coordinates)
- **JSON Parsing (org.json library)**

## Installation & Setup
1. **Prerequisites:**
   - Java Development Kit (JDK 8 or later)
   - Internet connection for API access
   - OpenWeatherMap API key (Replace with your own in `DistanceAPI.java`)

2. **Clone the Repository:**
   ```sh
   git clone https://github.com/yourusername/FlightBookingSystem.git
   cd FlightBookingSystem
   ```

3. **Compile and Run the Program:**
   ```sh
   javac -cp lib/json-20240303.jar src/*.java -d out
   java -cp lib/json-20240303.jar:out BookingSystem
   ```

## Usage
1. Run the application.
2. Click on **Start Flight Booking** to open the booking panel.
3. Enter departure and destination locations.
4. Choose the flight class (Economy/Business).
5. Select meal service if required.
6. Click **Get Bill** to see the total ticket price.
7. If an invalid location is entered, an error message will be displayed and the booking process will halt until a valid input is provided.

## File Structure
```
FlightBookingSystem/
│── lib/
│   ├── json-20240303.jar         # JSON parsing library
│── out/
│   ├── APIHandler.class          # Compiled class files
│── src/        
│   ├── BookingSystem.java        # Main GUI and event handling
│── image.jpg                     # Background image for UI
```

## API Configuration
- The system fetches geographical coordinates using the OpenWeatherMap API.
- Replace `API_KEY` in `DistanceAPI.java` with your valid API key.

