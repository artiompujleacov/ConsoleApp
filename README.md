#  Social Media CLI Application

A modular, Java-based command-line social network simulator that models key features of modern social media platforms—including user management, post publishing, comments, likes, follower networks, and analytics ranking.

---

##  Overview & Architecture

The application uses an object-oriented and command-driven approach to simulate a multi-user social network. It persists platform data (users, posts, comments, likes, and followers) across execution cycles using structured CSV data files.

###  Package Structure (`TemaTest`)

* **`TemaTest.User`**: Manages user accounts, login validation, and social graph actions (`CreateUser`, `FollowByUsername`, `UnfollowByUsername`, `GetFollowers`, `GetFollowing`, `GetMostFollowedUsers`, `GetMostLikedUsers`).
* **`TemaTest.Posts`**: Handles content creation and interactions (`CreatePost`, `DeletePost`, `GetPostsDetails`, `LikePost`, `UnlikePost`, `GetMostLikedPosts`, `GetMostCommentedPosts`).
* **`TemaTest.Comment`**: Controls post comments and thread interactions (`CommentPost`, `DeleteComment`, `LikeComm`, `UnlikeComm`).
* **`TemaTest.CommonUse`**: Defines system-wide interfaces, error handlers, input identifiers, and utility helpers (`Command`, `Identifier`, `Likeable`, `Cleanup`, `RewriteFile`).
* **`TemaTest.DataFiles`**: Local CSV storage files (`users.csv`, `posts.csv`, `comments.csv`, `following.csv`, `liked.csv`, `likecomments.csv`).

---

##  Features

* **User Management:** Create accounts, log in, and query follower / following statistics.
* **Social Network Interactions:** Follow or unfollow other users, view feeds, and fetch posts from followed accounts.
* **Post Lifecycle:** Create and delete text posts, fetch post details, and view posts by user.
* **Comments & Likes:** Comment on posts, delete comments, and like/unlike both posts and comments.
* **Analytics & Rankings:**
  * Get most liked posts / most commented posts.
  * Get most followed users / most liked users.
* **Data Persistence:** File-based data storage (`.csv`) for state persistence between runs.

---

##  Tech Stack & Requirements

* **Language:** Java (JDK 11+)
* **Build System:** Gradle / Java CLI
* **Persistence Format:** CSV (`DataFiles/`)
* **IDE Support:** IntelliJ IDEA, Eclipse, VS Code

---

##  Getting Started

### Prerequisites

Ensure you have **Java 11 or higher** installed:

```bash
java -version
