# APITestFramework

Test Scenario: As an existing authorized user, I retrieve a list of books available for me in the library. I will assign a book to myself and later on return it.

We will divide the test scenario into below steps for simplicity:

1. Test will start from generating Token for Authorization - First, we have the username and password of a registered user. Using these credentials, we will generate a token. Additionally, we will send this token into the Requests instead of the username and password. The reason we follow this practice is to have specific resources allocated in a time-bound manner. This step involves making a POST Request call in Rest Assured by passing username and password. Kindly follow this POST Request tutorial to learn about how to send a POST Request.

Note: The below-mentioned User won't work, please create your user for practice.

2. Get List of available books in the library - Secondly, it is a GET Request call. It gets us, the list of available books. Visit the GET Request tutorial to understand the logic of how we make a GET Request call in Rest-Assured.

3. Add a book from the list to the user - The third is a POST Request call. We will send the user and book details in the Request.

4. Delete the added book from the list of books - Fourth is a DELETE Request call. We will delete the added book from the list. The DELETE Request tutorial will help you with it.

5. Confirm if the book removal happens successfully - Last but not least, as a verification step, we will verify if the book has got removed from the user. Therefore, we will send user details in a GET Request call to get details of the user.
