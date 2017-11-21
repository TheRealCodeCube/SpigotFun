jar cvfm SpigotFun.jar manifest.txt plugin.yml -C bin io
rm ../Spigot/plugins/Waterfall.jar
mv ./SpigotFun.jar ../Spigot/plugins/Waterfall.jar
