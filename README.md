# DnDUtility
As a new player in the overwhelming world of DnD I wanted to help me understand the concepts of the game by tracking things I struggle to remember on my mobile device and try to provide quick look ups to things I do not know. This application uses the https://open5e.com/API to help supply the data on monsters, spells, etc.

## Spells
Updated: Spells tab can now add spells with filters from the api. Still need to add filtering by spell name.
The app utilizes a FragmentStatePagerAdapter because I am not aware of how many spells a user might have at an instance. Therefore, instead of holding possible 20 spells in memory, it will destroy these spells when not in view.

![](https://media.giphy.com/media/KZepLPS6owxvGEgzEx/giphy.gif) ![](https://media.giphy.com/media/VHfRLVrgY3pTErRZHT/giphy.gif)![](https://media.giphy.com/media/kz11zb9yDbAQ2yH3eH/giphy.gif)

## References
This is a goal driven learning experience. There is so much I have to learn and would not have made this much progress without these resources to help me structure and build my application. 

- Navigation Drawer
  - https://www.youtube.com/playlist?list=PLrnPJCHvNZuDQ-jWPw13-wY2J57Z6ep6G
- PageindicatorView
  - https://github.com/romandanylyk/PageIndicatorView
- Room database setup
  - https://www.youtube.com/watch?v=ARpn-1FPNE4&list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118
- Sending the spells from activities 
  - https://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents/2141166#2141166
  
## TODO
- Fix the character sheet (start page that user sees first).
- Support the search function to look up items, monsters, etc.
- Support Spell name filtering and suggestions drop down menu.
- Support different device sizes as well as horizontal view.
- Support settings tab which allows different themse, fonts and possibly character sheets.

