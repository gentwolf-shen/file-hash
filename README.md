java -jar ./filehash-1.0.jar help

hash:
```shell
java -jar ./filehash-1.0.jar -target verify -signHost http://60.247.61.98:15009 -filename /home/jerry/Downloads/sm3/hash/20211017.txt
```
 
verify:
```shell
java -jar ./filehash-1.0.jar -target hash -signHost http://60.247.61.98:15009 -src /home/jerry/Downloads/sm3/log -dst /home/jerry/Downloads/sm3/hash/
```
 
