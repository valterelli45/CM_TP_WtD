const sql = require("./db.js");

// Constructor
const TripLocal = function(tripLocal) {
  this.trip_id = tripLocal.trip_id;
  this.local_id = tripLocal.local_id;
};

TripLocal.create = (newTripLocal, result) => {
  sql.query("INSERT INTO trips_locals SET ?", newTripLocal, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    console.log("created trip local: ", { id: res.insertId, ...newTripLocal });
    result(null, { id: res.insertId, ...newTripLocal });
  });
};

TripLocal.findById = (id, result) => {
  sql.query(`SELECT * FROM trips_locals WHERE id = ${id}`, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(err, null);
      return;
    }

    if (res.length) {
      console.log("found trip local: ", res[0]);
      result(null, res[0]);
      return;
    }

    result({ kind: "not_found" }, null);
  });
};

TripLocal.getAll = (result) => {
  sql.query("SELECT * FROM trips_locals", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log("trip locals: ", res);
    result(null, res);
  });
};

TripLocal.updateById = (id, tripLocal, result) => {
  sql.query(
    "UPDATE trips_locals SET trip_id = ?, local_id = ? WHERE id = ?",
    [tripLocal.trip_id, tripLocal.local_id, id],
    (err, res) => {
      if (err) {
        console.log("error: ", err);
        result(null, err);
        return;
      }

      if (res.affectedRows == 0) {
        result({ kind: "not_found" }, null);
        return;
      }

      console.log("updated trip local: ", { id: id, ...tripLocal });
      result(null, { id: id, ...tripLocal });
    }
  );
};

TripLocal.remove = (id, result) => {
  sql.query("DELETE FROM trips_locals WHERE id = ?", id, (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    if (res.affectedRows == 0) {
      result({ kind: "not_found" }, null);
      return;
    }

    console.log("deleted trip local with id: ", id);
    result(null, res);
  });
};

TripLocal.removeAll = (result) => {
  sql.query("DELETE FROM trips_locals", (err, res) => {
    if (err) {
      console.log("error: ", err);
      result(null, err);
      return;
    }

    console.log(`deleted ${res.affectedRows} trip locals`);
    result(null, res);
  });
};

module.exports = TripLocal;
