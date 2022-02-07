from django.db import models

class RadarInfo(models.Model):
    id = models.CharField(max_length=200)
    elevation = models.DecimalField(max_digits=20)
    latitude = models.DecimalField(max_digits= 20)
    longitude = models.DecimalField(max_digits= 20)
    name = models.CharField(max_length=200)