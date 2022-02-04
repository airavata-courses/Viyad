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
            <th scope="row">{{ item.id }}</th>
            <th>
              <a style="color: #000000" href="#">
                {{ item.report_name }}
              </a>
            </th>
            <th>
              <a
                style="color: #000000"
                href="#"
                v-on:click.stop.prevent="clicked(item.id)"
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

export default {
  name: "ReportList",
  data(){
    return{
      isModalVisible: false,
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
    }
  }
};
</script>
