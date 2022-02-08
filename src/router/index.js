import Vue from 'vue'
import VueRouter from 'vue-router'
import CreateReport from '../components/CreateReport'
import MyReports from '../components/MyReports'
import Login from '../components/Login'
import Registration from '../components/Registration'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Signup',
    component: Registration
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/create',
    name: 'Create',
    component: CreateReport,
    props: true
  },
  {
    path: '/myreports',
    name: 'MyReports',
    component: MyReports,
    props: true
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
