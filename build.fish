#!/usr/bin/env fish -N

rm ./build/libs/*
./gradlew build

if test $status -ne 0
    exit $status
end

rm ./MCServer/plugins/ExamplePlugin-*.jar
cp ./build/libs/*.jar ./MCServer/plugins/

#rcon "kick @a Reloading"
rcon "reload confirm"