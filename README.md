# Board Porject
## Purpose  
This is my first personal project after taking Software Engineering class. There was a team project to build an application by following SE order in the class. I didn't have any skills to write down simple code before the project although I took several computer science classes. However, our team stayed up several nights and eventually made an application. After that, I got confidence in building a simple project. Even though this board system is not complicated at all, the project helps me to practice to plan and complete the project. 

## Breif  
I refered basic source code of sign up, and memebership management from [Javaking's blog](https://blog.naver.com/javaking75/140190272629).  
Sign up panel is modified to check for existing ID and confirm password. I limited input values as well to remain consistency and reduce error in database.
Sign up panel and board are added. Also manager account has exporting function with excel.

## Before start
Add all jar file from the root folder into java build path.  
Make guest account with follwing id and password into your sql server.  
id: guest
pwd: 00001111
Change directory in memberList.java line 190 to directory where you want to export excel file.

## Functions
### 1. **Sign in**   
![image](https://user-images.githubusercontent.com/36698150/50143180-4cc61500-02ef-11e9-873a-3068ac8ade9c.png)  
User can sign up and sign in here.  
![image](https://user-images.githubusercontent.com/36698150/50143259-872fb200-02ef-11e9-873f-13d1878d29c6.png)  
 If fails, it shows up error message.  
 
### 2. **Sign up**  
![image](https://user-images.githubusercontent.com/36698150/50143557-4dab7680-02f0-11e9-8ebb-8ee3754b9d84.png)  
Sign up panel has several textfield and combobox to input information. If user didn't check ID or passwordfields are not matched and didn't fill out textfields, message boxes will pop up to infrom user.

### 3. **Membership management**  
![image](https://user-images.githubusercontent.com/36698150/50143329-ad555200-02ef-11e9-8dbb-ebad9490d5c8.png)  
When User sign in with manager ID, program shows up membership management panel.  
Manager can sign up manually or export memeberlist to xlsx file.  

![image](https://user-images.githubusercontent.com/36698150/50143836-fce84d80-02f0-11e9-90ae-60f6668647b2.png)  
![image](https://user-images.githubusercontent.com/36698150/50143904-20ab9380-02f1-11e9-888c-dc2dc2b2b705.png)  
Excel file will be exported into desktop with filename 'memberList_currentdate'.  

### 4. **Board**  
![image](https://user-images.githubusercontent.com/36698150/50144073-99aaeb00-02f1-11e9-8e2e-038d4adce699.png)  
![image](https://user-images.githubusercontent.com/36698150/50144028-7da74980-02f1-11e9-8f1f-5a4525502efc.png)  
![image](https://user-images.githubusercontent.com/36698150/50144143-c65f0280-02f1-11e9-80d6-c4a78afe7dd5.png)  
View count will automatically increase when user click the post.  
![image](https://user-images.githubusercontent.com/36698150/50144394-7a608d80-02f2-11e9-8738-d14192bb4508.png)  
User can modify or delete the post which they uploaded.  
Clicking modify button once will enable to write the textarea and it will save when user click modify button again.  
![image](https://user-images.githubusercontent.com/36698150/50144896-9f093500-02f3-11e9-97df-e7a72f2d05b9.png)  
User can recommend the post, and the count will automatically increase. Recommend button is restricted to user recommend once by each post.  

### 5. **Database**  
![image](https://user-images.githubusercontent.com/36698150/50145097-176ff600-02f4-11e9-8b7d-30d93fc458f8.png)  
Database is built by MySQL. Table schema is on the above. tb_board for post data and tb_member for member data. tb_recCheck is for checking user id whether they recommend the specific post.

## Need to add
Manager account should be able to delete any posts.
Adding Comment function would be amazing if it is possible.