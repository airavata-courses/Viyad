var express = require('express');
var router = express.Router()
var authService = require('./authService')
var persistenceService = require('./persistenceService')
var dataService = require('./dataService')

router.use((req, res, next) => {
    console.log("Called: ", req.path)
    next()
})

router.use(authService)
router.use(persistenceService)
router.use(dataService)

module.exports = router