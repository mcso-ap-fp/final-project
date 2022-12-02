Keeping track of info required for project write up here:
https://www.cs.utexas.edu/users/witchel/371M/hw/writeup.html


Make sure your report specifies the following items, but bear in mind that the report is just your chance to convince me that you did a good job. 
Include what you think is necessary to convince me of that fact. 
I love hearing about debugging war stories. I'm serious, it is a problem for me.

## Team Members
- Althea Smith
  - ars_610@yahoo.com
  - ars7344
- Simon Weisser
  - simon.weisser@utexas.edu
  - saw3548

## Title 
Report the title of your app

## Description
Our app is designed to help individuals or groups decide on a location to eat based on their dining preferences. 
We pass the user or group's preferences as input to the Google Maps API to display details of the best nearby restaurants.  

## Screenshot
Include a screenshot of your app, hopefully one that illustrates key functionality.

## API(s)
List the APIs your app uses, for example Google maps. 
Also let me know if you use certain Android features extensively like animation or you have custom controllers (navigation drawer, action bar, etc.).

Google Places Text Search - this api was used for fetching restaurants based on the users dining preference, and has minimal details about the restaurant.
Google Places Details - this api was used for fetching specific details about one given restaurant (description, hours, reviews, etc)
Google Places Directions - this api was used for finding directions between the users starting location and the restaurant location.
FusedLocationProviderClient - used to find the current location of the Android device.

## Libraries
Material Design - We used this library to access additional components for our app like the Sliders found in the user preferences 
fragment. This library has fairly detailed documentation (https://m2.material.io/develop/android) which made it easy to set up and customize the components.

List all third party libraries you use, and what they do for your app (each in their own paragraph). 
Describe briefly what was good and/or challenging about using them.

## Services
List all third party services you use, and what they do for your app (each in their own paragraph).
Describe briefly what was good and/or challenging about using them.

Firebase Authentication - Authentication was used to allow users to sign in/ sign out to keep track
of their dining preferences. All dining preferences are linked to user UID's as seen in the DB schema below 
making it simple to fetch the current user's dining preferences when they are looking for a place to eat. 

Firestore Database - We used Firestore DB in order to keep track of our users' preferences from session to session. 
For this project we had to do research on batch transactions so we could update all of the preferences at once. 
I was pleased to find that Firestore has a set method which will create a document if 
one does not exist or update it if it does exist. This made storing/managing user preferences easy for both new and existing users. 

## UI/UX
Discuss anything noteworthy about your UI/UX/display code.

Nothing too complex here. We just have different views for the different functionality that users can click through. We use two recycle views, and also use google maps. We also have a button for calling the restaurant which is not possible to fully test using an emulator.

## Back End
Discuss anything noteworthy about your back end or processing logic.

We essentially make different API requests for the different functionality we have. When a user wants to select a restaurant to eat at, we load restaurants based on proximity, cuisine, and price. We list restaurants with their title, average rating, address and price. The user can also sort these results. If the user is interested in the restaurant, the can click on it and trigger another api call using the selected restaurants google places api id and we then display more detailed information such as a description, the hours, dining options and reviews. The user can also select the call restaurant button to make a phone call. From the initial list of restaurants, the user can also hit directions where we then use FusedLocationProviderClient to get the users current address, and then using the restaurants address we make a call to google maps directions api to get the polylines and place it onto the map in the activity to show directions to the restaurant.

## Learnings / Challenges 
Discuss the most important or interesting thing you learned doing your project.
Discuss the most difficult challenge you overcame and/or your most interesting debugging story.

The most difficult challenge was getting the directions feature up and running. After initially realizing that we were not able to map out directions to the restaurant, we were able to figure out that the reason was the location of the emulator was accross the country in California. We figured out how to change the location of the phone in the emulator. Afterwards, we were having issues using FusedLocationProviderClient to access my current location and using that to load directions. The order of events was tough here, as we need to figure out the users location, use that location to get directions to the restaurant, load the map, and place the route on the map. We were able to figure this by first loading the map which handles observing directions live data. After the map is loaded, we then use FusedLocationProviderClient to make the api call (in the onCreate) to get the directions and the users current address. The only set back is it takes a few seconds to initalize the camera to the users current address. So we initially load the camera to coordinates used from a previous homework to a location in Austin. We also had to teach ourselves how to do phone calls and set up the permissions for that, but this was much simpler than mapping directions on a google map.

## Local Setup
1. Create and connect to a Firebase project: https://firebase.google.com/docs/android/setup
2. Enable Authentication and Firestore DB via the Firebase console
3. Set up API Key (for the sake of simplicity we left ours unrestricted)
4. If using an Emulator, make sure to change the location to represent your actual physical location.


If necessary, briefly tell us how to build and run your project. Include details about how to set back end services (if you use them). 
In the common case, we will rely on your demo, but just in case we have an issue, we'd like some tips.

## Database Schema

![](./app/src/main/assets/DBSchema.png)

## Lines of Code

Report the count of lines of code in your project. Use cloc (http://cloc.sourceforge.net/) to count the lines. Run this command on your code.
cloc -by-file-by-lang app/src/main/
Report the total Java and total XML lines that you wrote. Do not include boilerplate lines, external libraries, or other sources in the total of lines on which you claim authorship.
Indicate how you calculate how many lines you authored if you report line totals. Maybe use a table.
You can break down the lines into modules if you think that helps, but I'm most interested in the totals of code you wrote.


--------------------------------------------
File                                                                                     blank        comment           code
----------------------------------------------------------------------------------------------------------------------------
app/src/main/res/drawable/ic_launcher_background.xml                                         0              0            170
app/src/main/java/com/example/finalproject/ui/PreferencesFragment.kt                        20              1            149
app/src/main/res/layout/activity_single_restaurant_details.xml                              32              0            143
app/src/main/java/com/example/finalproject/ui/Directions.kt                                 28              7            142
app/src/main/java/com/example/finalproject/ui/SingleRestaurantDetails.kt                    29              3            118
app/src/main/res/layout/fragment_preferences.xml                                            11              0            101
app/src/main/res/layout/restaurant_details.xml                                              12              2             86
app/src/main/java/com/example/finalproject/MainActivity.kt                                  22              2             86
app/src/main/java/com/example/finalproject/api/PlacesApi.kt                                 11              5             72
app/src/main/java/com/example/finalproject/ui/RestaurantDetails.kt                          14              0             70
app/src/main/java/com/example/finalproject/ui/MainViewModel.kt                              21              3             67
app/src/main/java/com/example/finalproject/ui/HomeFragment.kt                               10              1             61
app/src/main/java/com/example/finalproject/ui/RestaurantDetailsAdapter.kt                   15              4             55
app/src/main/res/layout/fragment_home.xml                                                    5              0             52
app/src/main/res/layout/restaurant_reviews.xml                                               8              0             52
app/src/main/AndroidManifest.xml                                                             3              0             46
app/src/main/java/com/example/finalproject/ui/ReviewsAdapter.kt                             15              1             43
app/src/main/java/com/example/finalproject/api/RestaurantDetailsData.kt                      5              0             42
app/src/main/java/com/example/finalproject/ViewModelDBHelper.kt                              4              0             41
app/src/main/java/com/example/finalproject/api/DirectionsData.kt                             6              1             32
app/src/main/res/layout/cuisine_row.xml                                                      4              0             30
app/src/main/res/drawable-v24/ic_launcher_foreground.xml                                     0              0             30
app/src/main/res/layout/activity_restaurant_details.xml                                      4              0             28
app/src/main/res/layout/direcions_map.xml                                                    2              0             24
app/src/main/java/com/example/finalproject/AuthInit.kt                                       4              2             20
app/src/main/res/layout/content_main.xml                                                     2              0             20
app/src/main/java/com/example/finalproject/api/PlacesRepository.kt                           9              0             19
app/src/main/java/com/example/finalproject/PreferencesViewModel.kt                           7              0             17
app/src/main/java/com/example/finalproject/api/RestaurantData.kt                             2              1             16
app/src/main/java/com/example/finalproject/model/UserPreferences.kt                          3              0             16
app/src/main/res/values/strings.xml                                                          0              0             15
app/src/main/res/layout/activity_main.xml                                                    2              0             11
app/src/main/res/values-night/themes.xml                                                     0              5             11
app/src/main/res/values/themes.xml                                                           0              5             11
app/src/main/res/drawable/ic_favorite_black_24dp.xml                                         0              0             10
app/src/main/res/values/colors.xml                                                           0              0             10
app/src/main/java/com/example/finalproject/model/CuisineRepository.kt                        2              1              7
app/src/main/res/menu/menu_main.xml                                                          0              0              7
app/src/main/res/layout/fragment_create_group.xml                                            1              0              7
app/src/main/res/values/google_maps_api.xml                                                  0              2              6
app/src/main/res/values/google_places_api.xml                                                0              2              6
app/src/main/res/mipmap-anydpi-v26/ic_launcher_round.xml                                     0              0              5
app/src/main/res/xml/data_extraction_rules.xml                                               0             15              4
app/src/main/res/xml/backup_rules.xml                                                        0             11              2
----------------------------------------------------------------------------------------------------------------------------
SUM:                                                                                       313             74           1960
----------------------------------------------------------------------------------------------------------------------------

-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Kotlin                          19            227             32           1073
XML                             25             86             42            887
-------------------------------------------------------------------------------
SUM:                            44            313             74           1960
-------------------------------------------------------------------------------

Code in files written by us = 1513
Lines of code taken from class lectures/demos = 78

Total code written by us = 1435


## Code Frequency Graph (if available)
We want you to include the code frequency graph that github gives you for your repository. 
Open your project's github page in a web browser. Click the "Insights" tab. 
Then click the "Code frequency" tab on the menu bar on the left. You will see the "Code frequency" graph. 
Put a screen shot of your code frequency figure into your writeup. Here is an example figure for reference. 
If you don't have a pro account, or can't provide this data, that is ok.

![Screen Shot 2022-12-01 at 7 28 46 PM](https://user-images.githubusercontent.com/60682487/205194167-1e60f82f-3fad-4c9c-b755-fb54af848b07.png)

