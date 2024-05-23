module.exports = app => {
    const userTrips = require("../controllers/user_trip.controller.js");
  
    // Create a new UserTrip
    app.post("/user_trips", userTrips.create);
  
    // Retrieve all UserTrips
    app.get("/user_trips", userTrips.findAll);
  
    // Retrieve a single UserTrip with userTripId
    app.get("/user_trips/:userTripId", userTrips.findOne);
  
    // Update a UserTrip with userTripId
    app.put("/user_trips/:userTripId", userTrips.update);
  
    // Delete a UserTrip with userTripId
    app.delete("/user_trips/:userTripId", userTrips.delete);
  
    // Delete all UserTrips
    app.delete("/user_trips", userTrips.deleteAll);
  };
  