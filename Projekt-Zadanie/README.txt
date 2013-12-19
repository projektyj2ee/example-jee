Autor: Marcin Czapiewski

Aplikacja utworzona została dla wersji Oracle JAVA 1.6 (ver.1.6.0_33) i sprawdzona została na serwerze aplikacyjnym Glassfish oraz kompiluje się pod Ubuntu 12.04 LTS oraz Windows 7 dla Maven'a w wersji 3.1.1.
Informacja: aplikacja może zostać uruchomiona na dowolnym serwerze aplikacyjnym przed odpowiednim skonfigurowaniem zasobów JDBC Resources i JDBC Connection Pools i skonfigurowana jest do pracy z bazą Apache-Derby 10.10.1.1
Kody aplikacji znajdują się także na ogólno-dostępnym repozytorium GitHub.
Kody można pobrać z tego repozytorum wykonując polecenie:
	git clone git://github.com/projektyj2ee/example-jee.git

Struktura projektu:

Projekt-Zadanie
	|
	|--binary - skompilowana wersja apliakcji + zdefiniowane resource dla Glassfisha w pliku .xml
	|--doc - dokumentacja 
	|--source - źródła aplikacji
		 |
		 |---ZadanieTestoweMarcinCzapiewski  -- root folder
						|
						|----project - zawiera kompletna strukture aplikacji (core i moduły zależne)
						|		|
						|		|-----CommentsDemo-api - biblioteka wspólna 
						|		|-----CommentsDemo-ear - katalog w którym znajdują się skompilowane binaria aplikacji i definicja resource dla GlassFish'a
						|		|-----CommentsDemo-ejb - moduł EJB
						|		|-----CommentsDemo-web - moduł WAR 
						|
						|----integration-test - testy integracyjne (nie wiem czy zdąże zrobić:/)
						|----dbversioner - projekt odpowiedziany za aktualizację struktury bazy danych


Instalacja samych binarek
	1. Binarka aplikacji dostępna jest w katalogu binary z konfiguracja dla Glassfisha i skryptami do bazy danych
						
Budowanie i instalacja aplikacji:
						
1. Przed wykonaniem build'a aplikacji należy:
	- sprawdzić czy na lokalnej maszynie utworzona jest baza danych Derby o nazwie "sun-appserv-samples" dla użytkownika "APP" z hasłem "APP".
	W przypadku kiedy takiej bazy nie ma proszę ją utworzyć dla tego użytkownika lub odpowiednio zmodyfikować plik 'glassfish-resources-mc.xml' 
	- zaimportować konfigurację zasobów na serwer Glassfish, w tym celu należy wykonać polecenie:
		<glassfish_path>/bin/asadmin add-resources "<project_path>/ZadanieTestoweMarcinCzapiewski/project/CommentsDemo-ear/glassfish-resources-mc.xml"
	
	Jeżeli nie chcemy kompilować źródeł tylko uruchomić aplikacje z załaczonych binariów, należy zaktualizowć schemat bazy danych.
	W tym celu należy przejść do folderu  
		"<project_path>/ZadanieTestoweMarcinCzapiewski/dbversioner" i wykonać polecenie 
		mvn clean install - uruchomiony zostanie plugin liquidbase, który zaktualizuję baze 'sun-appserv-samples' do odpowiedniej struktury.
		
2. Po odpowiednim skonfigurowaniu serwera aplikacyjnego zgodnie z wytycznymi z pkt. 1 należy przejść do katalogu:
	"<project_path>/ZadanieTestoweMarcinCzapiewski/" i zbudować aplikację poleceniem 
	
		mvn clean install -Drefresh=[wartość integer] -Dcount=[wartość integer]
		
		Gdzie:
			-Drefresh - określa czas po jakim tabela z wiadomościami zostanie automatycznie odświeżona, wartość określona w sekundach
			-Dcount - ilość wyświetlanych wiadomości w tabeli z wiadomościami na stronie - przy czym w oknie, w którym dodawana jest wiadomości tabela ta odświeżona jest natychmiast, 
					  a odświeżenie z podanym opóźnieniem nastąpi w innych oknach z uruchomioną aplikacją - zrobiłem to specjalnie, żeby widoczne było to że wiadomości utrwalane są w bazie.
		
		W przupadku braku powyższych parametrów przypisane zostaną wartości domyślne, czyli
			- refresh = 0 (mechanizm automatycznego odświeżania wyłączony)
			- count = 5 (wyświetlane jest tylko 5 ostatnio dodanych wiadomości)

3. Po zbudowaniu aplikacji zkompilowana binaria znajdują się w katalogu "<project_path>/ZadanieTestoweMarcinCzapiewski/CommentsDemo-ear/target"

4. Binaria aplikacji można "zdeployować" na serwer glassfisha. 
	Opcjonalnie. Można użyć do tego celu pluginu 'maven-glassfish-plugin',
	Plugin ten przed wykonaniem "deploy'a" należy odmarkować w pliku "<project_path>/ZadanieTestoweMarcinCzapiewski/CommentsDemo-ear/pom.xml" oraz uzupełnić 
	properties <glassfish.directory> podając scieżkę do serwera Glassfish i należy wykonać target 'mvn glassfish:create-domain' oraz 'deployować' aplikację poleceniem 'mvn glassfish:start-domain'.
	
5. Aplikacja używa Log4j i domyślnie logi ląduja w /<user_home>/.CommentsDemoLogs
	

	