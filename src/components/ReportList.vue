<template>
  <div>
    <div class="d-flex p-2" style="overflow-y: scroll; max-height: 690px">
      <table class="table table-stripped">
        <thead>
          <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Delete</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, i) in report_info" v-bind:key="i">
            <th scope="row">{{ item.pers_id }}</th>
            <th>
              <a style="color: #000000" href="#" @click="redirectToReports(item.pers_id)">
                {{ item.pers_name }}
              </a>
            </th>
            <th>
              <a
                style="color: #000000"
                href="#"
                v-on:click.stop.prevent="clicked(item.pers_id)"
              >
                <font-awesome-icon icon="trash" />
              </a>
            </th>
          </tr>
        </tbody>
      </table>
      <Modal v-show="isModalVisible" @close="closeModal"/>
    </div>
    
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import Modal from './Modal.vue';
import CreateReport from './CreateReport.vue'

export default {
  name: "ReportList",
  data(){
    return{
      isModalVisible: false,
      redirect_to_reports: false
    }
  },
  props: ["report_info"],
  components: {
    FontAwesomeIcon,
    Modal,
  },
  methods:{
    clicked(id){
      console.log(id)
      this.isModalVisible = true
    },
    showModal(){
      this.isModalVisible = true
    },
    closeModal(){
      this.isModalVisible = false
    },
    redirectToReports(pers_id){
      var selected_pers = this.report_info.filter(function(report){
        return report.pers_id == pers_id
      })
      var ddate = new Date(selected_pers[0].pers_date).toISOString()
      var date_Split = ddate.split("T")
      var time_split = date_Split[1].split(".")[0]
      var date_Sent = date_Split[0] + " " + time_split

      this.$router.push({ name: 'Create', params: {dname: selected_pers[0].pers_name,loc: selected_pers[0].pers_location,ddate : date_Sent }})
    }

  }
};
</script>
