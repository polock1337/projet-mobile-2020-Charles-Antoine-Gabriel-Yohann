# Projet Mobile

Équipe : 
- Charles
- Antoine
- Gabriel
- Yohann
## Vidéo de présentation : 
https://youtu.be/8jgJGvJK4MA
## Résumé basique de l'application : 

Application de "géocatching", donc un jeu ou l'utilisateur doit trouver un certain objet, texte, etc à l'aide d'une photo prise dans un lieu précis. Par exemple, l'utilisateur doit trouver ou à été prise une photo dans le parc des iles. Il voit donc apparaître le tracer sur une carte "google map" afin de se rendre à l'emplacement (parc des iles). Ensuite il doit trouver ou la photo à été prise et reprendre la même photo du même emplacement. Ensuite si l'utilisateur est situé à moin d'un certain nombre de mètre (ex : 20) lorsqu'il prend la photo, celui-ci réussira donc cet objectif et gagnera des points. Le joueur accomplira donc des objectifs et gagnera des points. Il y aura un tableau des utilisateurs possédant le plus de point. Le but est donc de gagner le plus de point possible pour être au sommet du tableau. 

Donc, pour l'aspect technologique, l'application sera créée en Android Java, aura le "swipe" comme gesture, stockera ces données dans un cloud Firestore, utilisera "google map" comme libraire et utilisera l'apparreil photo et le gps comme capteurs.

## Résumé de la structure : 

Il y aurais une page d'acceuil ou il faut se connecter ou créer un compte, ensuite on arrive sur la page du jeu ou il y a une de point à trouver avec un bouton "leaderboard" qui mène vers une page avec un "leaderboard" des meilleurs joueurs. Par la suite, si on clique sur un des objectifs dans la liste, ça ouvre une page avec les infos sur la photo à trouver et une carte "google map" avec le tracer pour arriver au lieu ou il faut trouver la photo. Au dessous de cette page, il y a un lien pour prendre une photo, donc si on prend une photo et l'emplacement est mauvais l'application lance une alerte mauvais emplacement veuillez réassayer et si on prend une photo et l'emplacement est le bon l'application lance une alerte bravo vous avez trouver le bon emplacement voici des points et ça retourne ensuite à l'écran de jeu principale.

Donc, il y aurais une page acceuil, une page connexion, une page créer un compte, une page acceuil du jeu , une page objectif et une page leaderboard.

## À Noté :  

- Oublie de faire la LIVRAISON_VUES et la LIVRAISON_CAPTEURS 
- Puisque notre projet Firebase est un projet gratuit, on ne peut pas faire de vrai export du firestore, c'est pourquoi il est sous forme de capture d'écran. Aussi, les autres captures d'écrans sont simplements là pour démontrer la structure des autres modules firebase que l'on utilise.
