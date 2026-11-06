FROM ubuntu:latest
LABEL authors="appletech"

ENTRYPOINT ["top", "-b"]