const express = require('express')
const router = express.Router()
const axios = require('axios')
const registry = require('./registry.json')
const fs = require('fs')

router.all('/:apiName/:path', (request, response) => {
    console.log("Api Name:" + request.params.apiName)
    if (registry.services[request.params.apiName]) {
        axios({
            method: request.method,
            url: registry.services[request.params.apiName].url + request.params.path,
            headers: request.headers,
            data: request.body
        }).then((res) => {
            response.send(res.data)
        })
    } else {
        response.send("API name does not exists in the record")
    }
})

router.post('/register', (request, response) => {
    const registrationInfo = request.body
    registry.services[registrationInfo.apiName] = { ...registrationInfo }

    fs.writeFile('./routes/registry.json', JSON.stringify(registry), (error) => {
        if (error) {
            response.send("Could not register '" + registrationInfo.apiName + "'\n" + error)
        } else {
            response.send("Successfully registered '" + registrationInfo.apiName + "'")
        }
    })
})

module.exports = router
