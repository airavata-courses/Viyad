FROM ubuntu:latest
# Update package manager (apt-get) 
# and install (with the yes flag `-y`)
# Python and Pip
RUN apt-get update && apt-get install -y \
    python3.8 \
    python3-pip

ENV PYTHONUNBUFFERED 1
WORKDIR /app
RUN  apt install python3-dev libproj-dev proj-data proj-bin libgeos-dev -y
COPY requirements.txt /app/requirements.txt
RUN pip install -r requirements.txt

COPY . /app

CMD python3.8 manage.py runserver 0.0.0.0:8000