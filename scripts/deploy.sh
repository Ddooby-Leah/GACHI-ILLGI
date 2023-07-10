# 기존 컨테이너 삭제
sudo docker rm -f gachi-diary-backend

# 기존 이미지 삭제
sudo docker rmi gachi-diary-backend

# 도커 실행
sudo docker run -d -p 8080:8080 --restart=unless-stopped --log-opt max-size=10m --log-opt max-file=3 --name gachi-backend qkdrmsgh73/gachi-diary-backend

# 사용하지 않는 불필요한 이미지 삭제 -> 현재 컨테이너가 물고 있는 이미지는 삭제되지 않습니다.
sudo docker rmi -f $(docker images -f "dangling=true" -q) || true