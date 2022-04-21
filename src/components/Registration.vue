<template>
    <div class="vue-tempalte">
        <div style="height: 50px; background-color: rgba(137, 196, 244);, 1;">
            <ul class="nav justify-content-end">
                <li class="nav-item">
                <router-link class="nav-link" to="/">Sign Up</router-link>
                </li>
                <li class="nav-item">
                <router-link class="nav-link" to="/login">Sign In</router-link>
                </li>
            </ul>
        </div>    
        <form>
            <h3>Sign Up</h3>
            <div class="form-group">
                <label>User Name</label>
                <input type="text" class="form-control form-control-lg" id="uname"/>
            </div>
 
            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control form-control-lg" id = "password"/>
            </div>
 
            <button type="submit" class="btn btn-dark btn-lg btn-block" @click="signUp($event)">Sign Up</button>
 
            <p class="forgot-password text-right">
                Already registered 
                <router-link :to="{name: 'Login'}">sign in?</router-link>
            </p>
        </form>
        <label v-if= "showError" class= "error">{{error_message}}</label>
    </div>
</template>
 
<script>
import axios from "axios";
    export default {
        name: "Registration",
        data() {
            return {
                showError: false,
                error_message: ""
            }
        },
        methods:{
            signUp(event){
                event.preventDefault()
                var usrname = document.getElementById("uname").value
                var password = document.getElementById("password").value
                if (!usrname && !password){
                    this.showError = true
                    this.error_message = "Please enter username and password"
                    return  

                }else if (!password){
                    this.showError = true
                    this.error_message = "Please enter password"
                    return  
                }else if(!usrname){
                    this.showError = true
                    this.error_message = "Please enter username"
                    return  
                }else{
                    this.showError = false
                    this.error_message = ""
                }
                var payload = {
                    username: usrname,
                    password: password,
                    role: "USER"
                };
                var payload_stringify = JSON.stringify(payload);
                axios
                    .post("http://127.0.0.1:3006/api/register", payload_stringify, {
                    headers: {
                        "content-type": "application/json",
                    },
                    })
                    .then((response) => {
                        this.$router.push({ name: 'Login'})                        
                    })
                    .catch((error) => {
                        console.log(error);
                        this.showError = true
                        this.error_message = "User was not registered successfully"
                    });                                   
            }
        }
    }
</script>