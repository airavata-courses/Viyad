const express = require('express')
const app = express()
const port = 2000
const routes = require('./routes')

app.use(express.json())
app.use('/', routes)

app.listen(port, () => (
    console.log("API gateway started on port:" + port)
))
