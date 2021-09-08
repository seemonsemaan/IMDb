# IMDb

## IMDb Controller
In the ImdbController we have 2 functions
searchByName and getDetails

### API call Return top X results based on a pathVariable.
A GET request to search the IMDb database.
- For this api call we use the searchByName function, it is accessible by a client using this url
`localhost:8080/search/{value}/{top?}`
- Value is a name of movie, series... and top is an optional parameter to limit the count of the result
- If the top is not provided or is less than 1, all values are returned.

For Example:
`localhost:8080/search/vikings/`

If we want to return only the top 3 results, we use `localhost:8080/search/vikings/3`


### API call to return the details of a particular movie.
A GET request to get all information related to a movie/series.
- For this api call we use the getDetails function, it is accessible by a client using this url
`localhost:8080/search/info/{value}`
- Value is the id of a specific movie/series (starts with tt).

For Example:
The id of vikings is tt2306299 so the url is
`localhost:8080/search/info/tt2306299/`

## Auth Controller

In the AuthController we have 2 functions
register and login

### A simple Signup POST request and save the user in a json file.
A POST request that accepts a json body containing a username and a password and stores them in a json file credentials.json.
- For this api call we use the register function, it is accessible by a client using this url
  `localhost:8080/auth/register`
- The body is converted to a LoginDTO (String username and password).


### A simple Signin POST request and authenticate the user from the same json file.
A POST request to get the credentials stored in the json file credentials.json.
- For this api call we use the login function, it is accessible by a client using this url
  `localhost:8080/auth/login`
- The body is converted to a LoginDTO (String username and password).
- If the credentials are correct the response is 200 OK, if they are wrong the response is 400 Bad Request.
