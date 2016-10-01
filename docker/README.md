# PaysafeInternsTimeTracking - Docker

## Generate docker containers
First of all, install docker by running the command `yum install -y docker`.

Navigate to `docker/database` and run `docker build -t local/database .` to build the image for the database,

then go to `docker/backend` and run `docker build -t local/backend .` to build the image for the back-end,

and finally to `docker/frontend` and run `docker build -t local/frontend .` to build the image for the front-end.

## Deploying the application

After you sucessfully created the docker images for database, back-end and front-end, run `docker images` to list the
images we just created. Start the database, backend and frontend images by typing:

`docker run -d -p 5432:5433 <Id of the database image>`

`docker run -d -p 8080:8181 <Id of the backend image>`

`docker run -d -p 4200:4201 <Id of the frontend image>`