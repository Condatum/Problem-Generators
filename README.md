  <img width="603" height="418" alt="Image" src="https://github.com/user-attachments/assets/f982cc59-7091-4204-bc4f-a787933e148a" />

---

# 🧥 DaoMing Fit - Virtual Wardrobe Manager

**DaoMing Fit** is a Java-based desktop application designed to help users digitally manage their wardrobe, create outfits, and organize clothing collections. Built with a custom retro aesthetic reminiscent of Windows 95/98, it combines nostalgia with modern functionality like API-driven background removal and file persistence.

---

## 📋 Table of Contents

* [Overview](https://www.google.com/search?q=%23-overview)
* [Key Features](https://www.google.com/search?q=%23-key-features)
* [Project Architecture](https://www.google.com/search?q=%23-project-architecture)
* [Installation & Setup](https://www.google.com/search?q=%23-installation--setup)
* [Usage](https://www.google.com/search?q=%23-usage)
* [Technologies Used](https://www.google.com/search?q=%23-technologies-used)

---

## 🔭 Overview

DaoMing Fit solves the "I have nothing to wear" dilemma by allowing users to import photos of their real clothes, automatically categorize them, and mix-and-match them into outfits. The application handles data persistence through a custom CSV file handling system and utilizes a Singleton pattern for efficient memory management of loaded assets.

---

## ✨ Key Features

| Feature | Description |
| --- | --- |
| **Virtual Wardrobe** | Automatically scans and loads clothing items from a local directory into Tops, Bottoms, and Footwear categories. |
| **Outfit Creator** | Drag-and-drop or select items to form a complete outfit. |
| **Smart Image Processing** | Integrated **RemoveBG API** to strip backgrounds from clothing photos automatically. |
| **Data Persistence** | Saves created outfits to a local CSV database (`outfit_data.csv`) ensuring data remains after closing the app. |
| **Retro UI System** | A custom UI factory that forces pixel fonts (`PixelifySans`), bevel borders, and specific color palettes to mimic vintage OS interfaces. |
| **Toast Notifications** | Custom non-intrusive pop-up notifications for user feedback. |

---

## 🏗 Project Architecture

The project is organized into three distinct packages to maintain separation of concerns:

| Package | Class | Responsibility |
| --- | --- | --- |
| **Classes** | `ClothingManager` | **Singleton.** Manages the runtime lists of clothing items (Tops, Bottoms, Shoes). |
|  | `Outfit` | **Model.** Encapsulated data class representing a combination of clothes. |
|  | `ClothingItem` | **Model.** Represents a single piece of clothing with an image and category. |
|  | `RemoveBGAPI` | **Service.** Handles HTTP requests to the background removal API. |
| **FileHandling** | `OutfitDataService` | **IO.** Handles reading/writing outfits to CSV and validating file integrity. |
| **FrontEnd** | `Menu` | **View.** The main entry point and dashboard of the application. |
|  | `RetroFactory` | **Utility.** Provides static methods for styling buttons, panels, and fonts globally. |
|  | `Toast` | **Component.** Custom JWindow implementation for system messages. |

---

## ⚙️ Installation & Setup

### Prerequisites

* **Java Development Kit (JDK) 11** or higher.
* An IDE (IntelliJ IDEA recommended for `.form` file compatibility).

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/DaoMingFit.git

```

### 2. Directory Setup

The application relies on specific local directories to function. Ensure the following folder structure exists in the project root:

```text
DaoMingFit/
├── liveImages/          <-- Place raw clothing images here
├── DaomingFit/
│   └── saved_data/      <-- Application will auto-generate CSV files here
└── src/

```

### 3. API Configuration

To use the background removal feature, you must configure the API key:

1. Open `src/Classes/RemoveBGAPI.java`.
2. Locate the `API_KEY` constant.
3. Replace the placeholder with your valid key from `withoutbg.com` or `remove.bg`.

```java
private static final String API_KEY = "YOUR_API_KEY_HERE";

```

---

## 🚀 Usage

1. **Run the Application:** Execute the `main` method in `FrontEnd/Menu.java`.
2. **Add Clothes:** Drop images into the `liveImages` folder with the naming convention `name#CATEGORY.png` (e.g., `redshirt#TOP.png`).
3. **Create Outfits:** Navigate to the Wardrobe section to combine items.
4. **Save:** Click save to write the outfit to disk.
5. **Validation:** If you delete an image file, the `OutfitDataService` will automatically detect the missing file on the next load and clean the database.

---

## 🛠 Technologies Used

* **Language:** Java
* **GUI Framework:** Swing (javax.swing)
* **Networking:** `java.net.http.HttpClient`
* **File I/O:** `java.nio.file` (NIO)
* **Font:** PixelifySans-Regular (Custom TrueType Font)

---

**© 2025 ProblemGenerators**
