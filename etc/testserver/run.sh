#!/bin/bash
echo "Running plugins from $MINECRAFT_SERVER/plugins"

java -jar \
-Xms2G -Xmx2G \
-Dcom.mojang.eula.agree=true \
paper-229.jar nogui \
--plugins $MINECRAFT_SERVER/plugins \
--port 25565