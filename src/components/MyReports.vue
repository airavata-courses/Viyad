<template>
    <div>
        <div class="d-flex p-2 text-uppercase">
            <h2 class="h-100 row align-items-center">My Report</h2>
        </div>
    <report-list v-bind:report_info="report_info"/>
    </div>
</template>

<script>
import axios from "axios";
import ReportList from  "./ReportList"
export default {
  name: "MainApp",
  data: function () {
    let report_info = [];
    return {
      title: "List from me.",
      report_info,
    };
  },
  components: {
    ReportList,
  },
  mounted() {
    axios.get("./data/report_info.json").then((response) => {
      this.report_info = response.data;
    });
  },
  methods:{
  }
};
</script>