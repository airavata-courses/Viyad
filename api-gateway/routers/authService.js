var express = require('express');
var router = express.Router()
const apiAdapter = require('./apiAdapter')

const BASE_URL = 'http://localhost:8085'
const api = apiAdapter(BASE_URL)

// router.get('/users', (req, res) => {
//   api.get(req.path).then(resp => {
//     res.send(resp.data)
//   })
// })

// router.get('/user/:id', (req, res) => {
//   api.get(req.path).then(resp => {
//     res.send(resp.data)
//   })
// })

router.post('/api/register', (req, res) => {
    api.post(req.path, req.body).then(resp => {
        res.send(resp.data)
    })
})

module.exports = router