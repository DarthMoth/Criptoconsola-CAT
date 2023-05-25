# CriptoconsoleCypher-CAT
[![General badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/es/)        
[![General badge](https://img.shields.io/badge/License-MIT-blue.svg)]()

## Encriptador Cèsar en Java

Aquest repositori conté una implementació de l'encriptat Cèsar en Java. L'encriptat Cèsar és una tècnica de criptografia que es basa en desplaçar 
cada lletra de l'alfabet un nombre fix de posicions per obtenir el text encriptat.

## Com utilitzar
- Executem el programa i s'obre en consola.
- El programa detecta el Sistema Operatiu per el format de la ruta automaticament.
- Escriu "DIR" per cambiar de directori
- Escriu "HASH" per passar una clau a bytes
- Escriu	ENCRIPTA per encriptar una clau 
- Escriu "DESENCRIPTARAES" per desencriptar una clau ja encriptada
- Per visualitzar mes comandes escriu "AJUDA" per obtenir la llista de comandes

## Tecnologies utilitzades
En aquest projecte s'han utilitzat diverses biblioteques de Java per implementar l'encriptat Cèsar. A continuació, es detallen algunes d'aquestes biblioteques i com s'utilitzen en el projecte:

`java.nio.file.*`: Aquesta biblioteca proporciona una API per accedir i manipular arxius i directoris en Java. En aquest projecte s'utilitza per llegir i escriure el text encriptat en un arxiu.

`java.security.*`: Aquesta biblioteca proporciona classes i interfícies per a funcions criptogràfiques, com ara generar claus i signar i verificar dades. En aquest projecte, s'utilitza per generar una clau secreta per al xifratge.

`javax.crypto.*`: Aquesta biblioteca proporciona classes i interfícies per a la criptografia i el xifratge. En aquest projecte s'utilitza per a la funció de xifratge Cèsar.

`javax.crypto.spec.SecretKeySpec`: Aquesta classe proporciona una manera de crear una clau secreta a partir d'un array de bytes. En aquest projecte, s'utilitza per crear una clau secreta a partir d'una paraula clau.

`javax.xml.bind.DatatypeConverter`: Aquesta classe proporciona mètodes per convertir dades entre diferents formats, com ara String i byte array. En aquest projecte, s'utilitza per convertir el text encriptat en una cadena de caràcters per escriure'l en un arxiu.
