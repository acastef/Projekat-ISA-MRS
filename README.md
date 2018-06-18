# Projekat-ISA-MRS-Tim11

Preduslovi pokretanja deploy-ovane aplikacije:
1. Heroku nalog
2. Java 1.8 lokalno instalirana
3. Meaven 3 lokalno instaliran

Pokretanje deploy-ovane aplikacije:
1. git clone https://github.com/acastef/Projekat-ISA-MRS-Tim11.git
2. logovanje na heroku nalog
3. heroku create bioskopi --buildpack heroku/java
4. git push heroku master
5. heroku ps:scale web=1
6. heroku open

Preduslovi za lokalno pokretanje aplikacije:
1. Java 1.8 lokalno instalirana
2. IntelliJ/Eclipse lokalno instaliran

Lokalno pokretanje aplikacije:
1. git clone https://github.com/acastef/Projekat-ISA-MRS-Tim11.git
2. otvoriti preuziti projekat preko IntelliJ/Eclipse okruzenja
3. pokrenuti aplikaciju preko razvojnog okruzenja