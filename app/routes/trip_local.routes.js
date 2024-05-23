module.exports = app => {
    const tripLocals = require("../controllers/trip_local.controller.js");
  
    // Create a new TripLocal
    app.post("/trip_locals", tripLocals.create);
  
    // Retrieve all TripLocals
    app.get("/trip_locals", tripLocals.findAll);
  
    // Retrieve a single TripLocal with tripLocalId
    app.get("/trip_locals/:tripLocalId", tripLocals.findOne);
  
    // Update a TripLocal with tripLocalId
    app.put("/trip_locals/:tripLocalId", tripLocals.update);
  
    // Delete a TripLocal with tripLocalId
    app.delete("/trip_locals/:tripLocalId", tripLocals.delete);
  
    // Delete all TripLocals
    app.delete("/trip_locals", tripLocals.deleteAll);
  };
  