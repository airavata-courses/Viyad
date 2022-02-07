<template>
  <div class="container">
    <div class = "input">
      <label for="dashboardname">Dashboard Name: &nbsp;</label>
      <input type="text" id="dashboardname" name="dashboardname" required>
      <div class = "datetime">
        <label for = "dtp">Please select date below:</label>
        <datetime format="YYYY-MM-DD H:i:s" v-model='dob' id= "dtp"></datetime>
      </div>
      <label v-if= "showerror" class= "error">{{error_message}}</label>
   </div>
   <div class="map container" id="map"></div>
   <div class = "footer" >
       <input type="submit" class= "button" value = "Save" @click = "saveDashboard">
   </div>
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
import datetime from 'vuejs-datetimepicker';

export default {
  name: "CreateReport",
  components: {datetime},
  data() {
    this.error_message = "Hi"
    this.showerror = false
    let greenIcon;
    let blueIcon;
    // let marker_data = []
    let marker_data_show = [];
    let map;
    let lon2popup;
    let lon2;
    let swissCrs;
    this.swissCrs = new L.Proj.CRS(
      "EPSG:2056",
      "+proj=somerc +lat_0=46.95240555555556 +lon_0=7.439583333333333 +k_0=1 +x_0=2600000 +y_0=1200000 +ellps=bessel +towgs84=674.374,15.056,405.346,0,0,0,0 +units=m +no_defs",
      {
        resolutions: [
          4000, 3750, 3500, 3250, 3000, 2750, 2500, 2250, 2000, 1750, 1500,
          1250, 1000, 750, 650, 500, 250, 100, 50, 20, 10, 5, 2.5, 2, 1.5, 1,
          0.5,
        ],
        origin: [2420000, 1350000],
      }
    );

    // for (let key in marker_data) {
    //   marker_data_show.push({radar_location: L.latLng(marker_data[key][0], marker_data[key][1])})
    // // Use `key` and `value`
    // }

    return {
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      attribution:
        '&copy; <a target="_blank" href="http://osm.org/copyright">OpenStreetMap</a> contributors',
      zoom: 4.3,
      crs: swissCrs,
      center: [39.163896, -86.525816],
      markerLatLng: [39.163896, -86.525816],
      marker_data_show: marker_data_show,
    };
  },
  methods: {
    saveDashboard(){
      var dashname = document.getElementById("dashboardname").value
      var dateTime = this.dob
      if (!dashname){
        return
      }
      if (!dateTime){
        var today = new Date();
        var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
        var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
        var dateTime = date +' '+time;
      }
      var payload = { "name": dashname,"userId": 1,"date": dateTime,"location": "KAEC"};
      var payload_stringify = JSON.stringify(payload)
      axios.post('http://127.0.0.1:5000/addpersistence', payload_stringify,{headers: {
          'content-type': 'application/json'
        }
      })
      .then(response => console.log(response))
      .catch(error => {
          console.log(error);
      });
    },
    createMarkerObject(marker_data, blue_icon) {
      let marker = L.marker(
        [marker_data.radar_location[0], marker_data.radar_location[1]],
        {
          icon: blue_icon,
        }
      );
      // let marker = L.marker(
      //   [marker_data.radar_location[0], marker_data.radar_location[1]],
      //   { icon: icon_green }
      // );
      let marker_popup = L.popup({ offset: L.point(0, -10) }).setContent(
        marker_data.name
      );
      return { marker_: marker, marker_popup: marker_popup };
    },
    user_clicked(evt) {
      console.log(evt.sourceTarget._leaflet_id);
    },
  },
  mounted() {
    let marker_list = {};
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
    this.map = L.map("map", { maxZoom: 100 },).setView(
      [39.163896, -86.525816],
      5
    );
    this.swissCrs = new L.Proj.CRS(
      "EPSG:2056",
      "+proj=somerc +lat_0=46.95240555555556 +lon_0=7.439583333333333 +k_0=1 +x_0=2600000 +y_0=1200000 +ellps=bessel +towgs84=674.374,15.056,405.346,0,0,0,0 +units=m +no_defs",
      {
        resolutions: [
          4000, 3750, 3500, 3250, 3000, 2750, 2500, 2250, 2000, 1750, 1500,
          1250, 1000, 750, 650, 500, 250, 100, 50, 20, 10, 5, 2.5, 2, 1.5, 1,
          0.5,
        ],
        origin: [2420000, 1350000],
      }
    );
    L.tileLayer("http://{s}.tile.osm.org/{z}/{x}/{y}.png", {
      attribution:
        '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(this.map);
    // X:  -17567847.747323327, 17567847.747323327
    // Y: -9818521.516863836, 14753651.49656776
    var imageUrl = 'http://www.lib.utexas.edu/maps/historical/newark_nj_1922.jpg';
    //imageBounds = [[40.712216, -74.22655], [40.773941, -74.12544]];

// 'lon_0':-86.28027777777778
// 'lat_0':39.7075
// 'x_0':0.0
// 'y_0':0.0
// 'lat_1':33
// 'lat_2':45
// imageBounds = [[]]
    // let corner1 = L.latLng(39.163896, -86.525816);
    // let corner2 = L.latLng(39.163896, -86.525816);
    // let imageBounds = L.latLngBounds(corner1, corner2);
    // var southWest = this.map.unproject([-86.28027777777778,39.7075], this.map.getMaxZoom());
    // var northEast = this.map.unproject([33,45], this.map.getMaxZoom());
    // console.log(southWest)
    // console.log(northEast)
    // imageBounds = [[northEast.lat, northEast.lng], [southWest.lat, southWest.lng]]
    //imageUrl = 'http://www.lib.utexas.edu/maps/historical/newark_nj_1922.jpg'
    //imageBounds = [[-86.28027777777778, 39.7075], [33, 14753651.49656776]]
    // L.imageOverlay(imageUrl, imageBounds).addTo(this.map);
    // this.map.fitBounds(imageBounds);
    // imageUrl = 'http://www.lib.utexas.edu/maps/historical/newark_nj_1922.jpg',
    // //(-17567847.747323327, 17567847.747323327) (-9818521.516863836, 14753651.49656776)
    // //-17567847.747323327,-9818521.516863836,35135695.49464665,24572173.013431594
    // bounds = new L.LatLngBounds ( new L.LatLng(-17567847.747323327,-9818521.516863836), new L.LatLng(35135695.49464665,24572173.013431594))
    // //imageBounds = [[40.712216, -74.22655], [40.773941, -74.12544]];
    // L.imageOverlay(imageUrl, bounds).addTo(this.map);
    // this.map.fitBounds(bounds);
    // var imageUrl = 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Sydney_Opera_House_-_Dec_2008.jpg/1024px-Sydney_Opera_House_-_Dec_2008.jpg'
    // var center = [-33.8650, 151.2094]
    // imageBounds = [center, [-35.8650, 154.2094]];
    // //var center = [-33.8650, 151.2094]
    // L.imageOverlay(imageUrl, imageBounds).addTo(map);
    let _this = this;
    axios.get("localhost:8000/api/radars").then((response) => {
      for (let key in response.data) {
        let marker = this.createMarkerObject(
          {
            radar_location: [response.data[key][0], response.data[key][1]],
            name: response.data[key][2],
          },
          _this.blueIcon
        );
        marker.marker_ = marker.marker_.addTo(this.map);
        marker.marker_._leaflet_id = key;
        marker.marker_.bindPopup(marker.marker_popup);
        marker_list[key] = marker.marker_;
        marker.marker_.on("mouseover", function (evt) {
          this.openPopup();
        });
        marker.marker_.on("mouseout", function (evt) {
          this.closePopup();
        });
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
          previously_clicked["marker"] = marker.marker_;
          marker.marker_.setIcon(_this.greenIcon);
          _this.user_clicked(evt);
        });
      }
    });
  },
};
</script>

<style>
.input{
  position: absolute;
}
.map {
  position: absolute;
  width: 80% !important;
  height: 50% !important;
  margin-top: 10% !important;
  overflow: hidden;
  z-index:1;
}
.footer{
  position: absolute;
  width: 80% !important;
  margin-top:40% !important;
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
  margin-left : 80%;
}
.error{
  color:red;
}
</style>
