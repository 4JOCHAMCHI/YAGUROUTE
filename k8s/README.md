kubectl create namespace kafka
kubectl create namespace redis

helm apply -f ebs-storage.yaml

helm repo add bitnami https://charts.bitnami.com/bitnami

helm repo update

helm install kafka-cluster bitnami/kafka -f kafka-values.yaml -n kafka
helm install redis bitnami/redis -f redis-values.yaml -n redis
