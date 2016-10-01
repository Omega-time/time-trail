# PaysafeInternsTimeTracking - Docker

## Generate docker images
First of all, install docker by running the command `yum install -y docker`.

Navigate to `docker/database` and run `docker build -t local/database .` to build the image for the database,

then go to `docker/backend` and run `docker build -t local/backend .` to build the image for the back-end.

## Running the docker containers

After you sucessfully created the docker images for database, back-end and front-end, run `docker images` to list the
images we just created. Start the database, backend and frontend images by typing:

`docker run -d -p 5432:5433 <Id of the database container>`

`docker run -d -p 8080:8181 <Id of the backend container>`
