const { Router } = require("express");
const userController = require("../controllers/userController");

const userRoutes = Router();

// Auth
userRoutes.post("/register", userController.register);
userRoutes.post("/login", userController.login);

module.exports = userRoutes;
