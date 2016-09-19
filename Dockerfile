FROM clojure
COPY ./docker/profiles.clj /root/.lein/profiles.clj
WORKDIR /usr/src/common-swagger-api

COPY project.clj /usr/src/common-swagger-api/
RUN lein deps

COPY . /usr/src/common-swagger-api
CMD ["lein", "test"]
