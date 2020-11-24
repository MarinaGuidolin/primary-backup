#!/bin/bash


function help() {
    echo "
    Utilização:
    $THIS -r <numero de replicas> [1-99]
    $THIS -h (mostra essa ajuda)
    "
    exit -1 #exit script
}

while getopts "hr:" OPT; do
case $OPT in
"h") help;;
"r") Replicas=$OPTARG; [[ $Replicas =~ ^[0-9]{1,2}$ ]] || help;;
"?") exit -1;;
esac
done

Command1="mvn clean install -f primary-backup/"
Command2="mvn clean install -f replicated-backup/"
Command3="mvn spring-boot:run -f primary-backup/"
Command4=""

#instalar as dependencias
$Command1
$Command2
#rodar o programa
$Command3 &
#rodar replicas
for i in $(seq 1 $Replicas); do
    Porta=$(($i+5))
    #echo "$Porta"
    Command4="mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=800$Porta -f replicated-backup/"
    #echo "$Command4"
    $Command4 &
done
