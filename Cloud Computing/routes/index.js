const { Router } = require("express");
const userRoutes = require("./userRoutes");

const router = Router();

// User
router.use("/user", userRoutes);

module.exports = router;
