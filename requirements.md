# Requirements

Projektvision: Plattform zur Ablage von kurzen Notizen und Weblinks
Idee: Die Web-Anwendung erlaubt es dem Anwender, kurze Notizen, Links auf Webseiten oder Bilder in einer Datenbank zu speichern. Zu allen Einträgen können Schlagwörter vergeben werden und es kann nach Schlagwörtern gesucht oder sortiert werden. Außerdem können einzelne Einträge durch Generierung eindeutiger Links freigegeben werden.
Die folgenden Use-Cases sind erforderlich:
UC-1: Neuanlage eines Benutzerkontos
UC-2: Einloggen und Ausloggen
UC-3: Ablage einer neuen Notiz / Links oder Bildes. Zu jeder Notiz können Schlagworte und Notizen gespeichert werden.
UC-4: Übersichtsdarstellung aller Notizen in einem Dashboard
UC-5: Filtern der angezeigten Notizen nach Schlagwort, Art und Zeitraum

Vergleichbare Lösung: PasteBin
Mindestumfang: Falls Sie das Projekt allein realisieren, sind die Anwendungsfälle UC-1, UC-2, UC-3 und UC-4 erforderlich. Bei Bearbeitung als Zweier-Team sollte der komplette Funktionsumfang von UC-1 bis UC-7 (prototypisch) realisiert sein. Es ist ausreichend, wenn die Anwendungsfälle grundsätzlich lauffähig sind; es muss keine Fehlerbehandlung aller möglichen Situationen erfolgen (Sie dürfen also davon ausgehen, dass der Benutzer wohlwollend ist und sinnvolle und vollständige Eingaben vornimmt).

# Restrictions

Selbständigkeit: Ihr Projekt beinhaltet nur wenig Beispiel- oder generierten Code. Sofern Sie von den üblichen Plattformen (StackExchange etc.) Code übernehmen, wird das im Sourcecode gekennzeichnet. Anderenfalls stellt ihre Abgabe ein Plagiat dar.
Funktionsumfang: Der eigenständig implementierte Funktionsumfang entspricht den Vorgaben der Aufgabenstellung. Untererfüllung führt zum Punktabzug, deutliche Übererfüllung aber nur im beschränkten Umfang zu einer verbesserten Bewertung.
Technische Features: Die von Ihnen implementierte Anwendung berücksichtigt die folgenden technischen Aspekte:
Serverseitig: Das Backend der Anwendung läuft in einem Applikationsserver (bspw. Apache Tomcat oder Django Server) ab. Ihre Anwendung ist über HTTP erreichbar.
Frontend: Es gibt ein zugängliches Web-Frontend. Dies kann entweder serverseitig generiert sein (Stichwort SSR) oder mit einer Javascript-Library realisiert sein, die auf ihr Backend via REST zugreift.
Multiuser: Ihre Anwendung ist Multi-User-fähig. Dies wird in der Regel vom Framework sichergestellt.
Datenhaltung: Ihre Anwendung greift zur Datenhaltung auf eine Datenbank zu. Im Regelfall sollte es sich um eine relationale DB (H2 oder SQLite) handeln. Sofern ihre Applikation mit unstrukturierten oder Zeitreihendaten arbeitet, dürfen Sie gern besser geeignete Alternativen (bspw. Apache Cassandra, MongoDB oder Influx) verwenden. Es ist nicht ausreichend, im Sourcecode statische Dummy-Daten zu erzeugen.
Lauffähigkeit: Ihre Anwendung kann mit einer kurzen Anleitung von mir in Betrieb genommen werden. Sie sind auf der sicheren Seite, wenn Sie ein Build-Tool wie Maven oder Gradle verwenden. Anleitungen, die eine bestimmte IDE voraussetzen und aus der Abfolge anzuklickender Menüs und Buttons bestehen, kann ich nicht akzeptieren. Zur Not können Sie auch ein Shellskript oder eine bat-Datei mitliefern, die alles startet.
Testkonzept: Ihre Anwendung beinhaltet ein Mindestmaß an Unit-Tests, mit der die Hauptbausteine unabhängig von der Ausführung als Web-Anwendung überprüft werden können.
Dokumentation: Die Abgabe beinhaltet eine kurze Dokumentation (ca. 8-15 Seiten) in der Sie die folgenden Dinge darstellen:
Projektvision: Worum geht es in ihrer Anwendung und welche Anwendungsfälle sollen implementiert werden? Diesen Punkt dürfen Sie bei der Wahl eines vorgebenen Themas entfallen lassen.
Plattform: Welche technische Plattform wird zur Implementierung verwendet und welche Versionen (von Java / Python / Go und den verwendeten Frameworks) sind zur Laufzeit erforderlich?
Architektur: Wie ist die grundsätzliche Struktur der Anwendung (Monolith / 3-Schicht / service-orientiert mit REST etc.)
Kurzanleitung: Wie kann die Anwendung gestartet werden und was kann erprobt werden (also bspw. Angabe von Dummy-Login-Daten etc.)
Ergänzender Hinweis: Für Bewertung des Funktionsumfanges ist entscheidend, wie viele Anwendungsfälle ihre Applikation abdeckt. Angerechnet werden nur die Funktionen, die Sie tatsächlich selbst implementiert haben. Wenn Sie also bspw. in Django die gesamte Nutzerverwaltung generieren lassen, dann sind die Anwendungsfälle "UC-1: Anmelden am System" oder "UC-2: Durchführen einer Selbstregistrierung" nicht mehr Teil ihrer Eigenleistung.