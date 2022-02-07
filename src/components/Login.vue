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
            <h3>Sign In</h3>
 
            <div class="form-group">
                <label>Email address</label>
                <input type="email" class="form-control form-control-lg" />
            </div>
 
            <div class="form-group">
                <label>Password</label>
                <input type="password" class="form-control form-control-lg" />
            </div>
 
            <button type="submit" class="btn btn-dark btn-lg btn-block">Sign In</button>

        </form>
    </div>
</template>
 
<script>
    export default {
        name: "Login",
        data() {
            return {}
        }
    }
</script>