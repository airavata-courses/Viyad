from xmlrpc.client import DateTime
from rest_framework import viewsets
from rest_framework.response import Response
from siphon.radarserver import RadarServer
from datetime import datetime
import numpy as np
import metpy.plots as mpplots
import matplotlib.pyplot as plt
import cartopy.crs as ccrs
import cartopy.feature as cfeature
from django.http import FileResponse
import base64
import os


class RadarInfoViewSet(viewsets.ViewSet):

    def list(self, request): #/api/radar
        print("Get Radar Info")
        rs = RadarServer('http://tds-nexrad.scigw.unidata.ucar.edu/thredds/radarServer/nexrad/level2/S3/')
        data = rs.stations
        rs._session.close()
        return Response(data)
    
    def fileCheck(self, filePath):
        try:
            return True if os.path.exists(filePath) and os.path.isfile(filePath) else False
        except IOError:
            return False


    def getData(self, request): # /api/radar
        # a = json.load(request)
        print("Get Data Call")
        radar_station = request.data["radarId"]
        time = datetime.strptime(request.data['time'], '%Y-%m-%d %H:%M:%S')
        rs = RadarServer('http://tds-nexrad.scigw.unidata.ucar.edu/thredds/radarServer/nexrad/level2/S3/')
        query = rs.query()
        query.stations(radar_station).time(time)
        catalog = rs.get_catalog(query)
        catalog.datasets
        print(catalog)
        if(len(catalog.datasets)):
            data = catalog.datasets[0].remote_access()
            print(data)
            def raw_to_masked_float(var, data):
                # Values come back signed. If the _Unsigned attribute is set, we need to convert
                # from the range [-127, 128] to [0, 255].
                if var._Unsigned:
                    data = data & 255

                # Mask missing points
                data = np.ma.array(data, mask=data==0)

                # Convert to float using the scale and offset
                return data * var.scale_factor + var.add_offset

            def polar_to_cartesian(az, rng):
                az_rad = np.deg2rad(az)[:, None]
                x = rng * np.sin(az_rad)
                y = rng * np.cos(az_rad)
                return x, y
            sweep = 0
            ref_var = data.variables['Reflectivity_HI']
            ref_data = ref_var[sweep]
            rng = data.variables['distanceR_HI'][:]
            az = data.variables['azimuthR_HI'][sweep]
            ref = raw_to_masked_float(ref_var, ref_data)
            x, y = polar_to_cartesian(az, rng)
            ref_norm, ref_cmap = mpplots.ctables.registry.get_with_steps('NWSReflectivity', 5, 5)
            ref_norm, ref_cmap = mpplots.ctables.registry.get_with_steps('NWSReflectivity', 5, 5)

            def new_map(fig, lon, lat):
                # Create projection centered on the radar. This allows us to use x
                # and y relative to the radar.
                proj = ccrs.LambertConformal(central_longitude=lon, central_latitude=lat)
                # New axes with the specified projection
                ax = fig.add_axes([0.02, 0.02, 0.96, 0.96], projection=proj)

                # Add coastlines and states
                ax.add_feature(cfeature.COASTLINE.with_scale('50m'), linewidth=2)
                ax.add_feature(cfeature.STATES.with_scale('50m'))
                return ax
            fig = plt.figure(figsize=(10, 10))
            ax = new_map(fig, data.StationLongitude, data.StationLatitude)
            ax.pcolormesh(x, y, ref, cmap=ref_cmap, norm=ref_norm, zorder=0)
            #plt.savefig('cache/'+radar_station+str(time)+'.png')
            # img = open('cache/'+radar_station+str(time)+'.png', 'rb')
            #with open('cache/'+radar_station+str(time)+'.png', 'rb') as img_file:
                #imageString = base64.b64encode(img_file.read())
            # response = FileResponse(img)
            rs._session.close()
            return Response("Plt fetched and stored in cache")
        else:
            #with open('cache/SS.png', 'rb') as img_file:
                #imageString = base64.b64encode(img_file.read())
            rs._session.close()
            return Response("Data not found")
