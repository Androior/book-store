# Book Store App

# I. Introduction to project (non technical)

# Categories (Start point of the app):
- Book Store App allows user to have quick access to different print types and categories of books published on Google's book API (https://www.googleapis.com/books/v1/volumes?q=printType=books).
User can also see all books per different category for both print types (Books and Magazines). App reads data from the following urls per category:
- Healty Lifestyle - https://www.googleapis.com/books/v1/volumes?q=printType=books&intitle:healthy+lifestyle
- Welness - https://www.googleapis.com/books/v1/volumes?q=printType=books&?%20intitle:wellness
- Healty Meals - https://www.googleapis.com/books/v1/volumes?q=printType=books&?%20intitle:healty+meals
# Search bar:
- Users can search for various books published on the API which app is using. Search result is expected to be retrieve when the user type at least two characters in the search bar and the books based on search input will be listed on grid view.
# Sections:
- On main screen user is able to see all of the book types per category. There are three categories in total in this app: Healty Lifestyle, Welness and Healty Meals. By clicking "See All" user will be redirected on another screen where he can see all available books that belong to the certain category. When user clicks on book's image then he will be redirected to the book details' screen.
# Book Details:
- When user will click on book's image (does not matter if he clicks from the main screen or screen where he searches for books) he can get more details about the book. Here user can get require more information about writter, content, average rating and so on. There is also button which allows to user to save his favorite books and see under the favorites section (more on this a bit latter). On this screen there is a button called "More Details" which allows user to see content of the book in web browser directly from the app.
# Favorite Details:
- On main screen there is navigation to the favorite screen, where user can see his favorite (saved) books, can get more information such as who is writer, short description for the book content, average rating and some more details. Here user can see also his favorite/saved books. Option to read book content directly from the app using web browser is present in this screen.

# II. Introduction to technical part (which objects and libraries were used for making of the app)

# Main Activity
- On the entry point data from Google's book API, base url ("https://www.googleapis.com/books/v1/volumes") is parsed using HttpUrl.Builder class in order to add query parameters using addQueryParameter which belongs to the same class to add parameters to base url for every category of books.
- Request and response calls for base url and its query parameters are send and retrieve by the OkHttpClient and the result of valid response is applied to the arraylist needed to fill data in the SectionAdapter and then to the BookAdapter. For showing data that belongs to SectionAdapter linear layout manager is used with vertical orientation and for showing data that belongs to nested BookAdapter and its recyclerview with linear layout manager and horizontal orientation are used.
- Three fragments withing frame object are attached to the main screen, so user can choose between book, magazine and favorite options which will navigate him to different fragments and activities. When instantiating fragment object using newInstance function in the bundle object that is latter applied to fragment's one type of the book is stored in order to easier segregation to books per categories.

# Search Activity
- For the search screen again as base url is used ("https://www.googleapis.com/books/v1/volumes") and is parsed with the help of HttpUrl.Builder class. The difference in logic here is that as query parameter characters typed inside of edit text. For request and response calls again OkHttpClient object is used and to trigger response call for the query parameter at least two characters need to be entered. 
- For showing data arraylist from the response is filled and is latter used in SeeAllAdapter that is set to recyclerview object using GridLayout as layout manager showing books in grid order with 2 columns.

# Book Details Activity
- Here user can see additional details about every book when he clicks on the book's image no matter if he clicks from the Book or Magazine Fragments, belonging to Main Activity context or Search Activity. New intent with sending Parcelable model object (object of type Book in this case which implements Parcelable interface and its methods) with it's current data will be sent to new activity from where user can by clicking button "More Details" read the content of the book in the web browser using the WebView class via         loadUrl(url) method.

# Favorite Details Activity
In Book Details Activity, user can press on save icon and this will trigger the procedure to save this book and it's details in SQLite db. For this purpose, object that extends SQLiteOpenHelper needs to be declared and method for inserting data into db needs to be written. Insertion of data happens when on ContentValues object instance we invoke put method and passing pair of key and values for all of the data. Key in put method is usually name of the db column. When mapping of the data which we want to insert into db is over then SQLiteDatabase object needs to be instantiated, with invoking getWritableDatabase() method on Activity context and then we invoke insert method where we put table name and instance with data from ContentValues object.
Once the data will be saved in the database, arraylist of book object will be filled with data read from sqlite db using instance of Cursor object and then it will be applied to the adapter which is latter set to recyclerview with linear layout manager to show the data inside activity.

 



