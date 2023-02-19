# taas-microservices-eureka

|Servizio| Funzione| Porta| Java |
|----------|----------|----------|----------|
|Eureka| Discovery|  http://localhost:8761/ | 11 |
|API service| Il servizio principale che fa tutto tranne posizioni | http://localhost:8080/ | 11 |
|Position | Il servizo delle posizioni | http://localhost:8081/ | 11 |
| Api Gateway | Riunisce tutti i servizi sotto un unico url | http://localhost:9191/ | 18 |

Per avviarli da IntelliJ occhio alle rispettive versioni, JAVA 11 per i primi 3 e java 18 per api gateway.

AVVIO DEL PROGETTO

*Con Docker*

Per avviare tutto insieme è meglio usare Docker
Andare su docker-compose.yaml e dovrebbe uscire il pulsante per avviare tutto. Docker compose va a prendersi i docker file dei singoli servizi e li esegue  con le specifiche che gli sono date
 
*Senza Docker*

 Per avviare i singoli servizi senza docker  ( per fare sviluppo veloce e robe così ) fare come di solito ma occhio alla versione di Java che si usa dei setting di Intellij (questo problema  non c'è con Docker)



KUBERNETES
Installazione:
Installare Minikube https://minikube.sigs.k8s.io/docs/start/

Dopo aprire il terminale:
- minikube start (ci mette un po' perchè scarica tutto kubernetes)

kubectl apply -f api-service-deployment.yaml
minikube service gateway
kubectl get svc
kubectl get pods
minikube gateway



### Lista comandi minikube help

minikube provisions and manages local Kubernetes clusters optimized for development workflows.

Basic Commands:
- start            Starts a local Kubernetes cluster
- status           Gets the status of a local Kubernetes cluster
- stop             Stops a running local Kubernetes cluster
- delete           Deletes a local Kubernetes cluster
- dashboard        Access the Kubernetes dashboard running within the minikube cluster
- pause            pause Kubernetes
- unpause          unpause Kubernetes

Images Commands:
- docker-env       Provides instructions to point your terminal's docker-cli to the Docker Engine inside minikube.
(Useful for building docker images directly inside minikube)
- podman-env       Configure environment to use minikube's Podman service
- cache            Manage cache for images
- image            Manage images

Configuration and Management Commands:
- addons           Enable or disable a minikube addon
- config           Modify persistent configuration values
- profile          Get or list the current profiles (clusters)
- update-context   Update kubeconfig in case of an IP or port change

Networking and Connectivity Commands:
- service          Returns a URL to connect to a service
- tunnel           Connect to LoadBalancer services

Advanced Commands:
- mount            Mounts the specified directory into minikube
- ssh              Log into the minikube environment (for debugging)
- kubectl          Run a kubectl binary matching the cluster version
- node             Add, remove, or list additional nodes
- cp               Copy the specified file into minikube

Troubleshooting Commands:

- ssh-key          Retrieve the ssh identity key path of the specified node
- ssh-host         Retrieve the ssh host key of the specified node
- ip               Retrieves the IP address of the specified node
- logs             Returns logs to debug a local Kubernetes cluster
- update-check     Print current and latest version number
- version          Print the version of minikube
- options          Show a list of global command-line options (applies to all commands).
- Other Commands:
- completion       Generate command completion for a shell
- license          Outputs the licenses of dependencies to a directory

Use "minikube <command> --help" for more information about a given command.

Se le immagini docker sono già state create andare nella cartella kubernetes e fare da Linux:
 kubectl apply -f nome-file-deployment.yaml
