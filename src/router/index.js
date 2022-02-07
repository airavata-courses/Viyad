import Vue from 'vue'
import VueRouter from 'vue-router'
import CreateReport from '../components/CreateReport'
import MyReports from '../components/MyReports'

Vue.use(VueRouter)

const routes = [
  {
    path: '/create',
    name: 'Create',
    component: CreateReport,
    props: true
  },
  {
    path: '/',
    name: 'MyReports',
    component: MyReports
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
