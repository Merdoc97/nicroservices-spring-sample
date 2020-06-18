#after building images 
1. create deployment and run pods
kubectl apply -f ${pathToDeployment}
#expose ports for discovery
kubectl expose deployment ${deploymentName} --type=NodePort --port=${portNumber}