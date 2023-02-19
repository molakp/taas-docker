# API-SERVICE
# Compilazione Maven e Docker
Write-Host "-----------Api Service-----------"

cd api-service
& "./create-image.ps1"
cd ..



# DISCOVERY-SERVICE
# Compilazione Maven e Docker
Write-Host "-----------Discovery Service-----------"

cd discovery-server
& "./create-image.ps1"
cd ..



# POSITION-SERVICE
# Compilazione Maven e Docker
Write-Host "-----------Position Service-----------"
cd position-service
& "./create-image.ps1"
cd ..




#GATEWAY-SERVICE
# Compilazione Maven e Docker
Write-Host "-----------Gateway Service-----------"
cd gateway
& "./create-image.ps1"
cd ..


#DASHBOARD
# Compilazione Maven e Docker
Write-Host "-----------Dashboard-----------"
cd Dashboard
& "./create-image.ps1"
cd ..




