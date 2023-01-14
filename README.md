# SpawnMod - Remove the light spawn condition changes in the nether for MC 1.19.3.

![](spawnmod.gif)

## Setup
Becuase I write tons of java i have 100000 JDKs installed *SO PLEASE* change this line or remove it from `gradle.properties`
`org.gradle.java.home=D:/Programs/java/jdk-19.0` 
- This was something i personally needed to add to not interfere with other development environments I have locally :)

Required fabric loader 0.14.11 And Fabric API to run on your server/Fabric Loader to run on your client.

## Notes From The Author
- While im still getting familiar with Fabric's Api and SpongePowered Mixins, I was only able to completely remove the spawning logic checks in *MobEntity.class*
  for all invocations of the static methods where the EntityType is either skeleton or wither_skeleton and the Dimension is the nether.
  Please feel free to open a PR to improve this much love

## Manual Building

you should be able to exectute `./gradlew clean build` in the root of the Project and add the outputted .JAR that is generated in `./build/libs` to your server
and client. If you can believe it i ACTUALLY tested it!

## License

Insert something about licensing. take from this and learn my child.
