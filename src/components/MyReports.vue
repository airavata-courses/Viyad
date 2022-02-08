<template>
    <div v-bind:class="{ disabled: loading }">
      <div style="height: 50px; background-color: rgba(137, 196, 244);, 1;">
        <ul class="nav justify-content-end">
          <li class="nav-item">
            <router-link class="nav-link" to="/myreports">My Reports</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{name: 'Create',params: {userId:userId}}">Create Report</router-link>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Sign Out</a>
          </li>
        </ul>
      </div>
        <div class="d-flex p-2 text-uppercase">
            <h2 class="h-100 row align-items-center">My Report</h2>
        </div>
    <report-list v-bind:report_info="report_info"/>
    <Loader :loading = "loading"/>
    </div>
</template>

<script>
import axios from "axios";
import ReportList from  "./ReportList"
import Loader from "./Loader"
export default {
  name: "MainApp",
  props:{
    userId: Number
  },
  data: function () {
    let report_info = [];
    return {
      title: "List from me.",
      report_info,
      loading:false
    };
  },
  components: {
    ReportList,
    Loader
  },
  mounted() {
    if(!this.userId){
      this.userId = localStorage.getItem('userId');
    }
    if(!this.userId){
      this.userId = 1
    }  
    var url = "http://127.0.0.1:3006/persistences/" + 1
    this.loading = true
    axios.get(url).then((response) => {
      this.report_info = response.data;
      this.loading = false
    }).catch((error)=>{
      this.loading = false
    })
  },
  methods:{
  }
};
</script>

<style>
.disabled{
  pointer-events: none;
  user-select: none;
}
</style>


