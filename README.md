# Summary
#### 
This App is an Android App that can search movie info from OMDB. (http://www.omdbapi.com/)
<br> Users can search by Movie-Title and Year of release(Year is optional).
<br> The search result shows the movie Title and Year and the Plot can be read too. 

# Technology
- OS: Android
- Language: kotlin
- Design Pattern: MVVM
- Others: LiveData, ViewModel, Retrofit, Coroutine, JUnit, REST API, RecyclerView

# How to use
- Enter Title(required) and Year(optional). (without year, all years data can be gotten)
- Tap “SEARCH”.
- After showing the result, tap “NEXT” to see the next page/“BACK” to see the previous
page.
- Tap “PLOT” in the movie card to show the short plot of the movie.

# Fetching Data(API) flow
- When “SEARCH” is tapped, this App fetches movie info(except Poster Image) by using Title and Year and updates the movie list on UI.
- After that, this App fetches Poster Image of each movie by using the image url. Each time when fetching a movie’s poster image, update List on UI.
- When “PLOT” is tapped, this App fetches the movie’s plot by using the movie’s id and updates the Plot text(in the bottom of the screen).

# Other Tech Tips
- On the “Year” box, I added the restriction so that users can only enter numbers and up to 4 digits.
- If the movie doesn’t have a poster image, “Non-Image” picture is shown.
- Unit-Test is written and the Mock repository is created for it.(Only for a ViewModel class)
- Error handling (Even when there is no result or an error happened in the searching function, corresponding messages are shown.)
- After entering the Title or Year, the keyboard will be hidden.
- When the movie list(UI) is updated, the list scroll back to the top.

# Unimplemented Tasks
- Adding the “page” box on the UI so that User can see the result of the page directly.
- Developing ViewModel so that “Context” can be used even in ViewModel to use the “strings” file in ViewModel.
- Writing other Unit-Test code especially for Repositories.
