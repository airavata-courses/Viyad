from rest_framework import serializers

from .models import RadarInfo

class RadarInfoSerializer(serializers.ModelSerializer):
    class Meta:
        model = RadarInfo
        fields = '__all__'