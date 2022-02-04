import Vue from 'vue'
import App from './App.vue'
import Faker from "vue-faker";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.css"
import {library} from "@fortawesome/fontawesome-svg-core";

import{
  faTrash, faEllipsisV, faPlus, faUser
} from "@fortawesome/free-solid-svg-icons"

import router from './router'

Vue.use(Faker);
library.add( faTrash, faEllipsisV, faPlus, faUser);

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')


