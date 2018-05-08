# Team building booking

## Requirements
* [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Setup (First time)
1. Clone the repository: `git clone git@bitbucket.org:{yourUsernameGoesHere}/team-building-booking.git`
2. Run './gradlew clean build' in **/team-building-booking** folder

### Set Up Postgres database
1. open **pgAdmin III**
2. Create database named **tulipani** (if it doesn't exist)
3. Create new login role. Right-click on **Login Roles** and choose **Add New Login Role**
4. On **Properties** tab type **dbuser** in **Role name**
5. On **Definition** tab type **secret** in **Password** and **Password (again)** fields
6. On *Variables* tab type **search_path** in **Variable Name** field. Type **teambuildingbooking** in **Variable Value** field. Choose **tulipani** from **Database** dropdown. (- is not supported in value of the **Variable name**)
7. Click **Add/Change** button
8. Click **OK**
9. Right click on **Schemas**, choose **New Schema**...
10. Go to **Properties** tab
11. Under Name put the value **teambuildingbooking**
12. Under Owner put the **dbuser**


### Import project to IntelliJ
1. Start **IntelliJ**
2. **File**>**New**>**Project From Existing Sources** - navigate to **/team-building-booking** folder
3. In the **Import project** window choose **Import project from external name** and choose **Gradle** from the list below. Click **Next**
4. Make sure that there is **.gradle** file at the end under the Gradle project: field. Put a check next to **Use auto-import**. **Use default grade wrapper** should be selected. Grade JVM should point to **Java 1.8 folder**. Click **Finish**

### Run Spring Boot Application

If you have **IntelliJ IDEA Ultimate** version, do the following:
 
1. Select dropdown next to the **Build Project** icon in the toolbar
2. Click on **Edit Configurations**
3. Add a new **Spring Boot Run / Debug Configuration**
4. Select **TeamBuildingBookingApplication**
5. In **Configuration** tab, under **Spring Boot Settings > Active Profiles** add the **dev** profile

If you are using **Intellij IDEA Community Edition**, do the following:
 
Navigate to: 
  ```bash
  src/main/resources/application.properties
  ```
  
Add the following line:
  ```bash
  spring.boot.profiles.active=dev
  ```
  
Then run the **Spring Boot** application.

#### Credits:    
* Ana Dedić
* Maja Puljić
* Filip Blažević
* Antonio Barić
* Marko Štuban