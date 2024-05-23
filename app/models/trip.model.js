const sql = require("./db.js");

// constructor
const Trip = function(trip) {
  this.name             = trip.name;
  this.description      = trip.description;
  this.date_created     = trip.date_created;
  this.date_of_the_trip = trip.date_of_the_trip;
  this.end_of_the_trip  = trip.end_of_the_trip;
  this.classification   = trip.classification;
};

Trip.create = (newTrip, result) => {
  sql.query("INSERT INTO trips SET ?", newTrip, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    console.log("created trip: ", { id: res.insertId, ...newTrip });
    result(null, { id: res.insertId, ...newTrip });
  });
};

Trip.findById = (id, result) => {
  sql.query(`SELECT * FROM trips WHERE id = ${id}`, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    if (res.length) {
      console.log("found trip: ", res[0]);
      result(null, res[0]);
      return;
    }

    // not found Trip with the id
    result({ kind: "not_found" }, null);
  });
};

Trip.getAll = (result) => {
  sql.query("SELECT * FROM trips", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log("trips: ", res);
    result(null, res);
  });
};

Trip.updateById = (id, trip, result) => {
  sql.query(
    "UPDATE trips SET name = ?, description = ?, date_created = ? WHERE id = ?",
    [trip.name, trip.description, trip.date, id],
    (err, res) => {
      if (err) {
        console.log("error: ", err);
        result(null, err);
        return;
      }

      if (res.affectedRows == 0) {
        // not found Trip with the id
        result({ kind: "not_found" }, null);
        return;
      }

      console.log("updated trip: ", { id: id, ...trip });
      result(null, { id: id, ...trip });
    }
  );
};

Trip.remove = (id, result) => {
  sql.query("DELETE FROM trips WHERE id = ?", id, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    if (res.affectedRows == 0) {
      // not found Trip with the id
      result({ kind: "not_found" }, null);
      return;
    }

    console.log("deleted trip with id: ", id);
    result(null, res);
  });
};

Trip.removeAll = result => {
  sql.query("DELETE FROM trips", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log(`deleted ${res.affectedRows} trips`);
    result(null, res);
  });
};

module.exports = Trip;
