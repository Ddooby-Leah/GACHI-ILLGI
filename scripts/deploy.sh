# 기존 컨테이너 삭제
sudo docker rm -f gachi-backend

# 기존 이미지 삭제
sudo docker rmi qkdrmsgh73/gachi-diary-backend

sudo docker pull qkdrmsgh73/gachi-diary-backend

# 도커 실행
sudo docker run -d -p 8080:8080 --restart=unless-stopped --log-opt max-size=10m --log-opt max-file=3 --name gachi-backend qkdrmsgh73/gachi-diary-backend