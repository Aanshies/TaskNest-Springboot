# ✅ TaskNest — Organize. Track. Achieve.

**TaskNest** is a clean, intuitive Spring Boot + Thymeleaf + PostgreSQL based Todo application to help you organize daily tasks, track completion, and build productive habits seamlessly.

🔗 Live GitHub Repo: [TaskNest — Organize. Track. Achieve.]
(https://github.com/Aanshies/TaskNest-Springboot)

---

## ✨ Features

-🔐 User Authentication (Spring Security, BCrypt hashing)

-🗂️ Create, Read, Update, Delete Todos

-⏰ Mark Tasks as Completed

-📅 Due Dates and Priorities

-🎨 Clean, responsive UI with Thymeleaf

-📊 Profile page showing user-specific tasks

-🗄️ Persistent storage with PostgreSQL

---

## 🖼️ App Screenshots

These give a quick visual walkthrough of what XpenseEase offers.

<img width="513" height="560" alt="Image" src="https://github.com/user-attachments/assets/c1fd069a-f31e-4f66-9952-0bede098e10e" />
<img width="502" height="496" alt="Image" src="https://github.com/user-attachments/assets/001b026d-88f3-4fd7-89ba-4ec216795c49" />
<img width="1895" height="900" alt="Image" src="https://github.com/user-attachments/assets/44894345-a6b7-43da-9f81-e7f380e68576" />
<img width="623" height="602" alt="Image" src="https://github.com/user-attachments/assets/d475c4eb-fd75-455f-9115-cc145bdceb57" />
<img width="614" height="763" alt="Image" src="https://github.com/user-attachments/assets/52699322-6a89-4a88-98ab-9a93aac88e08" />
<img width="1919" height="909" alt="Image" src="https://github.com/user-attachments/assets/b7cb1319-8f5d-477f-b9f7-4cf87fffe8d2" />
<img width="1919" height="920" alt="Image" src="https://github.com/user-attachments/assets/1fd92ce2-52d5-4ac8-a7fe-b9a5b981d627" />
<img width="539" height="648" alt="Image" src="https://github.com/user-attachments/assets/02370c1a-c58f-4720-8d89-86aba37592e3" />


## 🛠️ Tech Stack

| Part       | Tech Used                        |
|------------|----------------------------------|
| Frontend   | Thymeleaf, Bootstrap             |
| Backend    | Spring Boot, Spring Security     |
| Database   | PostgreSQL                       |
| ORM        | (Spring Data JPA)                |
| Security   | BCrypt Password Hashing          |

---

## 🚀 Getting Started Locally

### 📦 1. Clone the Repository

```bash
git clone https://github.com/Aanshies/TaskNest-Springboot
cd TaskNest-Springboot
```
### 🐘 2. Set up PostgreSQL

-Create a database named todo_app (or your preferred name).

-Update your application.properties:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_app
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```
### ☕ 3. Run the Application

-You can run using:

```bash
./mvnw spring-boot:run
```
or
```bash
mvn spring-boot:run
```
-The app will be available at:
```arduino
http://localhost:8080
```
---

## 🌐 Upcoming Enhancements
✅ Deploy on Render / Fly.io

✅ Add email notifications for due tasks

✅ Mobile-friendly improvements

---

## 🤝 Contributing
Contributions, feature suggestions, and issue reports are welcome! Fork the repository, make your changes, and open a pull request.

---

## 💬 Connect with Me
Feel free to reach out on LinkedIn if you have questions or want to collaborate!

---

## 🪪 License
MIT License © 2025
