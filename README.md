# Feather Lyrics

This repository contains the source code for the Feather Lyrics Android APP.

## How does it work?

The APP's puts working on background a BroadcastReceiver that gets track's name and artist's name from the the actual playing song.
When the user interface is opened, the APP takes the data collected by the BroadcastReceiver. When user demands it, lyrics from the actual song are downloaded.

The app uses two API's:

- Vagalume API -> http://api.vagalume.com.br/docs/
- Spotify API -> https://developer.spotify.com/web-api/

The lyrics, and album arts are taken from Vagalume's API, artist pictures are taken from Spotfy's API.

## Features:

The application concept is fully modular, so the structure is based on a Navigation Drawer formed by fragments. The app has differents menus wich allow the user to search an specific song or artist, reading recent news from music world and the preference menu to let the user personalize the APP to its liking.

- Follows the Material Design philosophy
- User friendly interface
- On background BroadcastReceiver
- Automatic current song search
- Artist discography searching menu
- Beautifull Splash Screen with a simple animation
- Audio playing when starting as the application tone
- Toast with current song information
- Cute widget for tracking the current song lyrics
- Custom PreferenceFragment menu
- "About" section
- Battery life friendly :)

## Which audio players are supported?

Feather Lyrics tracks the next players:

* Android stock music player
* Google Music
* LastFM Player
* Samsung Galaxy S3 Music Player
* Winamp
* Amazon Music
* MIUI Player
* Real
* Sony Ericsson Player
* Rdio
* Apollo Music Player
* Spotify
* PowerAmp
* Rhapsody Music Player

## Screenshots

<img src="http://i.imgur.com/ObSxjv0.jpg" width="250">
<img src="http://i.imgur.com/1CDRmde.png" width="250">
<img src="http://i.imgur.com/LNVG2zo.png" width="250">
<img src="http://i.imgur.com/fkU3dhN.png" width="250">


