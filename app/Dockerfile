FROM python:3.8.5-alpine
COPY . /app
WORKDIR /app
RUN pip install --upgrade pip
RUN apk add gcc musl-dev python3-dev libffi-dev openssl-dev
RUN pip install -r requirements.txt
ENTRYPOINT ["python"]
CMD ["rest.py"]
