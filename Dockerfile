FROM clojure
COPY . /usr/src/common-swagger-api
COPY ./docker/profiles.clj /root/.lein/profiles.clj
WORKDIR /usr/src/common-swagger-api
RUN lein deps
CMD ["lein", "test"]
