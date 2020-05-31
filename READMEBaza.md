aplikacja klient-serwer

klient wysyła zapytanie do serwera, w którym określa czy chce
dodać samochód do bazy, usunąć samochód z bazy,
wyświetlić wszystkie samochody, czy wyszukać samochód
zgodny z zadanymi kryteriami.

serwer po otrzymaniu zapytania, tworzy nowy wątek i laczy sie
z baza danych. Wykonuje odpowiednie polecenie i wysyła klientowi w odpowiedzi JSONa.
