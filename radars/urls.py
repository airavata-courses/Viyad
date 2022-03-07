from django.contrib import admin
from django.urls import path

from .views import RadarInfoViewSet

urlpatterns = [
    path('radars',RadarInfoViewSet.as_view({
        'get': 'list'
    })), 
    path('getData', RadarInfoViewSet.as_view({
        'post':'getData'
    }))
]
