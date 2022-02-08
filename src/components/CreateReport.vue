<template>
  <div class="container" v-bind:class="{ disabled: loading }">
    <div style="height: 50px; background-color: rgba(137, 196, 244);, 1;">
      <ul class="nav justify-content-end">
        <li class="nav-item">
          <router-link class="nav-link" to="/myreports">My Reports</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/create">Create Report</router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Sign Out</a>
        </li>
      </ul>
    </div>  
    <div class = "input">
       <label for="dashboardname">Dashboard Name: &nbsp;</label>
       <input type="text" id="dashboardname" name="dashboardname" @change = "validateName">
       <div class = "datetime">
         <label for = "dtp">Please select date below:</label>
         <datetime format="YYYY-MM-DD H:i:s" v-model='dob' id= "dtp"></datetime>
       </div>
       <label v-if= "showerror" class= "error">{{error_message}}</label>
    </div>
    <div class="map container" id="map"></div>
    <div class="footer">
      <input type="submit" class="button" value="Save" @click="saveDashboard" />
    </div>
    <Loader :loading="loading" />
  </div>
</template>

<script>
import L, { Icon, latLng, CRS } from "leaflet";
import _L from "proj4leaflet";
import "leaflet/dist/leaflet.css";
import axios from "axios";
import markerIconPNG from "leaflet/dist/images/marker-icon.png";
import "proj4leaflet";
import proj4 from "proj4";
import datetime from "vuejs-datetimepicker";
import Loader from "./Loader";

export default {
  name: "CreateReport",
  components: { datetime, Loader },
  props: {
    dname: String,
    loc: String,
    ddate: String,
    userId: Number
  },
  data() {
    let greenIcon;
    let blueIcon;
    let marker_data_show = [];
    let radarCity = {};
    let location = ''
    var today = new Date();
    var date =
      today.getFullYear() +
      "-" +
      (today.getMonth() + 1) +
      "-" +
      today.getDate();
    var time =
      today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    var dateTime = date + " " + time;
    //this.ddate = datetime;
    return {
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 4.3,
      //crs: swissCrs,
      center: [39.163896, -86.525816],
      markerLatLng: [39.163896, -86.525816],
      marker_data_show: marker_data_show,
      error_message: "",
      showerror: false,
      dob: dateTime,
      loading: false,
      location:this.loc
    };
  },
  methods: {
    saveDashboard() {
      var dashname = document.getElementById("dashboardname").value;
      var dateTime = this.dob;
      if (!dashname) {
        this.showerror = true;
        this.error_message = "Please enter dashboard name";
        return;
      }
      if (!dateTime) {
        var today = new Date();
        var date =
          today.getFullYear() +
          "-" +
          (today.getMonth() + 1) +
          "-" +
          today.getDate();
        var time =
          today.getHours() +
          ":" +
          today.getMinutes() +
          ":" +
          today.getSeconds();
        var dateTime = date + " " + time;
      }
      if(!this.userId){
        this.userId = localStorage.getItem('userId');
      }
      if(!this.userId){
        this.userId = 1
      }      
      var payload = {
        name: dashname,
        userId: 1,
        date: dateTime,
        location: this.location,
      };
      var payload_stringify = JSON.stringify(payload);
      this.loading = true;
      axios
        .post("http://127.0.0.1:3006/addpersistence", payload_stringify, {
          headers: {
            "content-type": "application/json",
          },
        })
        .then((response) => {
          this.loading = false;
        })
        .catch((error) => {
          this.loading = false;
          console.log(error);
        });
    },
    validateName() {
      var dashname = document.getElementById("dashboardname").value;
      if (dashname) {
        this.showerror = false;
      }
    },
    createMarkerObject(marker_data, blue_icon) {
      let marker = L.marker(
        [marker_data.radar_location[0], marker_data.radar_location[1]],
        {
          icon: blue_icon,
        }
      );
      let marker_popup = L.popup({ offset: L.point(0, -10) }).setContent(
        marker_data.name
      );
      return { marker_: marker, marker_popup: marker_popup };
    },
    user_clicked(evt) {
      console.log(evt.sourceTarget._leaflet_id);
    },
  },
  created() {
    if (this.ddate) {
      this.dob = this.ddate;
    }
    if (this.loc) {
      console.log(this.loc);
    }
  },
  mounted() {
    if (this.dname) {
      document.getElementById("dashboardname").value = this.dname;
    }

    let marker_list = {};
    this.radarCity = {};
    this.location = this.loc
    let previously_clicked = {};
    this.blueIcon = new Icon({
      iconUrl: markerIconPNG,
      iconSize: [15, 25],
      iconAnchor: [12, 41],
    });
    this.greenIcon = new Icon({
      iconUrl:
        "https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png",
      shadowUrl:
        "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png",
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41],
    });
    this.map = L.map("map", { maxZoom: 100 }).setView(
      [39.163896, -86.525816],
      5
    );
    L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(this.map);
    let _this = this;
    this.loading = true;
    axios
      .get("http://localhost:3006/api/radars")
      .then((response) => {
        this.loading = false;
        for (let key in response.data) {
          let marker = this.createMarkerObject(
            {
              radar_location: [response.data[key][2], response.data[key][3]],
              name: response.data[key][4],
            },
            _this.blueIcon
          );
          this.radarCity[response.data[key][0]] = response.data[key][4];
          marker.marker_ = marker.marker_.addTo(this.map);
          marker.marker_._leaflet_id = key;
          if (key == _this.loc) {
            marker.marker_.setIcon(_this.greenIcon);
            this.loading = true;
            axios
              .post("http://localhost:3006/api/radars", {
                radarId: key,
                time: _this.dob,
              })
              .then((response) => {
                this.loading = false;
                const src = "data:image/png;base64," + response.data;
                const popupContent = document.createElement("div");
                popupContent.innerHTML =
                  "<img style='width: 250px; height: 250px;' src='" +
                  src +
                  "' >";
                marker.marker_.bindPopup(popupContent, {
                  width: "250px",
                  height: "250px",
                });
                marker.marker_.update();
                marker.marker_.openPopup();
              })
              .catch((error) => {
                this.loading = false;
              });
          } else {
            marker.marker_.bindPopup(marker.marker_popup);
          }
          marker_list[key] = marker.marker_;
          // marker.marker_.on("mouseover", function (evt) {
          //   this.openPopup();
          // });
          // marker.marker_.on("mouseout", function (evt) {
          //   this.closePopup();
          // });
          marker.marker_.on("click", function (evt) {
            if (previously_clicked.hasOwnProperty("marker")) {
              previously_clicked["marker"].setIcon(_this.blueIcon);
              if (
                evt.sourceTarget._leaflet_id ==
                previously_clicked["marker"]._leaflet_id
              ) {
                delete previously_clicked["marker"];
                return;
              }
            }
            let _thisRef = _this;
            marker.marker_.unbindPopup();
            marker.marker_.bindPopup();
            this.loading = true;
            axios
              .post("http://localhost:3006/api/radars", {
                radarId: evt.sourceTarget._leaflet_id,
                time: _this.dob,
              })
              .then((response) => {
                _this.location = evt.sourceTarget._leaflet_id
                this.loading = false;
                const src = "data:image/png;base64," + response.data;
                const popupContent = document.createElement("div");
                popupContent.innerHTML =
                  "<img style='width: 250px; height: 250px;' src='" +
                  src +
                  "' >";
                marker.marker_.bindPopup(popupContent, {
                  width: "250px",
                  height: "250px",
                });
                marker.marker_.update();
                marker.marker_.openPopup();
              })
              .catch((error) => {
                this.loading = false;
              });
            previously_clicked["marker"] = marker.marker_;
            marker.marker_.setIcon(_this.greenIcon);
            _this.user_clicked(evt);
          });
        }
      })
      .catch((error) => {
        this.loading = false;
      });
  },
};
</script>

<style>
.input {
  position: absolute;
}
.map {
  position: absolute;
  width: 80% !important;
  height: 50% !important;
  margin-top: 10% !important;
  overflow: hidden;
  z-index: 1;
}
.footer {
  position: absolute;
  width: 80% !important;
  margin-top: 40% !important;
}
.button {
  display: block;
  width: 20%;
  border: none;
  background-color: rgb(137, 196, 244);
  color: white;
  padding: 14px 28px;
  font-size: 16px;
  cursor: pointer;
  text-align: center;
  margin-left: 80%;
}
.error {
  color: red;
}
.disabled {
  pointer-events: none;
  user-select: none;
}
</style>
