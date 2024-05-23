module.exports = app => {
    const locals = require("../controllers/local.controller.js");
  
    // Create a new Local
    app.post("/locals", locals.create);
  
    // Retrieve all Locals
    app.get("/locals", locals.findAll);
  
    // Retrieve a single Local with localId
    app.get("/locals/:localId", locals.findOne);
  
    // Update a Local with localId
    app.put("/locals/:localId", locals.update);
  
    // Delete a Local with localId
    app.delete("/locals/:localId", locals.delete);
  
    // Delete all Locals
    app.delete("/locals", locals.deleteAll);
  };
  