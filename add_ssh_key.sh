#!/bin/sh

set -e

# Création du répertoire .ssh s'il n'existe pas
mkdir -p ~/.ssh

# Ajout de la clé privée SSH
echo "$SSH_PRIVATE_KEY" | tr -d '\r' > ~/.ssh/id_rsa
chmod 600 ~/.ssh/id_rsa

# Démarrage de l'agent SSH
eval $(ssh-agent -s)

# Ajout de la clé privée à l'agent SSH
ssh-add ~/.ssh/id_rsa
