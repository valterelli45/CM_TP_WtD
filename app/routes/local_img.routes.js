module.exports = app => {
    const localImgs = require("../controllers/local_img.controller.js");
  
    // Create a new LocalImg
    app.post("/local_imgs", localImgs.create);
  
    // Retrieve all LocalImgs
    app.get("/local_imgs", localImgs.findAll);
  
    // Retrieve a single LocalImg with localImgId
    app.get("/local_imgs/:localImgId", localImgs.findOne);
  
    // Update a LocalImg with localImgId
    app.put("/local_imgs/:localImgId", localImgs.update);
  
    // Delete a LocalImg with localImgId
    app.delete("/local_imgs/:localImgId", localImgs.delete);
  
    // Delete all LocalImgs
    app.delete("/local_imgs", localImgs.deleteAll);
  };
  