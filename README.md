# API_Final_CSI460_MM

  - Matthew Mahar


This week was about creating an application that could call on an API hosted on a virtual machine. For our virtual machine, we used virtualbox, with Ubuntu for our desktop. We also learned how we could use Django to help set up a simple API, and ngrok to use as a secure tunnel for hosting our API and testing the interaction between itself and our Android Studio application. The API that our application is talking to is a very simple API that holds student information, but nothing too in-depth. We are talking first and last name, address, phone, roll number, and ID as a unique identifier.

When we first start the Android application, we see a pretty basic homepage. The main points I was trying to meet with this application were having it connect and interact with the API without issue, being able to perform CRUD functionality, and having a smooth and easy flow between activities that is user-friendly. The way I have the app set up is by having a scroll view in the center to show the API data that is being received. I was going to do a recycler view but I didn't have time, so that is something I will have to add on later. There are 5 buttons, two for fetching a single data item or all, a button for editing data, one for deleting it, and one more for creating.

![HomePage](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/c33f4109-d444-4d24-8924-0d0300393b7a)


Clicking on Create brings us to a new activity where we can add student information. The only one I left out was the ID because I'm using it as a primary key, so it shouldn't be changed.

![addNew](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/8f576ad9-f188-43be-902c-f7d6de5bcb38)


As you can imagine, adding the data is pretty easy. There are a couple properties that are expecting a specific type, like a number, but there are some validation checks, including checking for missing items. If you leave any rows blank, it will let you know. After correctly entering data for a new student and clicking "post", you get a message at the bottom saying that it went through. You can then add more, or click the back button to return to the main menu.

![CaptureNewSTu](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/2fbc8a1c-1009-430e-8d07-ff9e69406185)


I can check that my data is being saved correctly by reading or fetching it. You just need to enter the ID of the student, in this case, the one I entered was 8, and click the "single fetch" button. After doing this, we are shown JSON data reflects exactly what I created, meaning that it's working. 

![fetchSing](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/c5ac3321-a418-4334-8f90-006f44c35b91)


To make edits to a student, I again need to enter the ID number because we need to know who we want to update, otherwise, a message appears.

![EnterID](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/22131e1e-413d-4b2c-9a37-23f732980fbd)


The layout of the edit/update activity is not much different from create, however as you might notice, we can see the JSON data at the bottom of the person we are editing.

![editText](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/d3c2b24e-c507-4b38-b215-dac9ef09c2c5)


One of the things I mentioned earlier was some of the validation checks. I intentionally left a few rows blank and clicked "patch" and it pulled up an error telling me the problem.

![editMissingSpace](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/8dfbd3e8-e418-4e0b-8dac-fd4df158d7e4)


The student that I am editing is in these pics is me, "Matt Mahar". But I am going to change the values to John Doe and click patch, to which we notice that the values at the bottom have also changed and are saved. 

![EditMade](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/17596426-fd0a-46b1-8489-0bcb4fb456d2)


We can also delete a student from the main activity, but it will, of course, ask for the ID number. In this case, I am going to delete the person I created, named "New Student" with an ID of 8. After deleting the data, I get a message letting me know it's been successful.

![RecordDeleted](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/6c5c502c-4919-44da-90d0-7f59d53c6c37)


I can double-check whether it's been deleted by selecting the "fetch all" button. By observing who the last student is in the data, we see that it ends at ID #7, meaning it works.

![fetchAll](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/3b902ac2-ced7-48ef-aeed-6f8565c8f8c1)


I can also go back and actually look at the API. This came really in handy when developing and testing your application, especially when trying to verify certain changes or checking the communication between devices.

![Capture](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/79c77f19-22f7-4ec5-9d39-ffe599dfc8e7)


The overall project structure was fairly simple, although the code in some of the files is pretty in-depth and something I want to try and simplify or break up. Two areas on the assignment that I could not get around to, were converting the JSON for data being shown to the user, and using a recycler view for displaying the students in a list. I just didn't have enough time but I plan on updating this app when I can as I am enjoying and looking forward to getting more experience with virtual machines and APIs.

![structure](https://github.com/MMahar5/API_Final_CSI460_MM/assets/97249776/f0f2b0b5-5f85-4e26-aa64-736d5f75272e)
